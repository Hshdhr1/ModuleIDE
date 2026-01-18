package org.antlr.v4.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.misc.Interval;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class TokenStreamRewriter {
    public static final String DEFAULT_PROGRAM_NAME = "default";
    public static final int MIN_TOKEN_INDEX = 0;
    public static final int PROGRAM_INIT_SIZE = 100;
    protected final Map lastRewriteTokenIndexes;
    protected final Map programs = new HashMap();
    protected final TokenStream tokens;

    public class RewriteOperation {
        protected int index;
        protected int instructionIndex;
        protected Object text;

        protected RewriteOperation(int index) {
            this.index = index;
        }

        protected RewriteOperation(int index, Object text) {
            this.index = index;
            this.text = text;
        }

        public int execute(StringBuilder buf) {
            return this.index;
        }

        public String toString() {
            String opName = getClass().getName();
            int $index = opName.indexOf(36);
            return "<" + opName.substring($index + 1, opName.length()) + "@" + TokenStreamRewriter.this.tokens.get(this.index) + ":\"" + this.text + "\">";
        }
    }

    class InsertBeforeOp extends RewriteOperation {
        public InsertBeforeOp(int index, Object text) {
            super(index, text);
        }

        public int execute(StringBuilder buf) {
            buf.append(this.text);
            if (TokenStreamRewriter.this.tokens.get(this.index).getType() != -1) {
                buf.append(TokenStreamRewriter.this.tokens.get(this.index).getText());
            }
            return this.index + 1;
        }
    }

    class InsertAfterOp extends InsertBeforeOp {
        public InsertAfterOp(int index, Object text) {
            super(index + 1, text);
        }
    }

    class ReplaceOp extends RewriteOperation {
        protected int lastIndex;

        public ReplaceOp(int from, int to, Object text) {
            super(from, text);
            this.lastIndex = to;
        }

        public int execute(StringBuilder buf) {
            if (this.text != null) {
                buf.append(this.text);
            }
            return this.lastIndex + 1;
        }

        public String toString() {
            return this.text == null ? "<DeleteOp@" + TokenStreamRewriter.this.tokens.get(this.index) + ".." + TokenStreamRewriter.this.tokens.get(this.lastIndex) + ">" : "<ReplaceOp@" + TokenStreamRewriter.this.tokens.get(this.index) + ".." + TokenStreamRewriter.this.tokens.get(this.lastIndex) + ":\"" + this.text + "\">";
        }
    }

    public TokenStreamRewriter(TokenStream tokens) {
        this.tokens = tokens;
        this.programs.put("default", new ArrayList(100));
        this.lastRewriteTokenIndexes = new HashMap();
    }

    public final TokenStream getTokenStream() {
        return this.tokens;
    }

    public void rollback(int instructionIndex) {
        rollback("default", instructionIndex);
    }

    public void rollback(String programName, int instructionIndex) {
        List<RewriteOperation> is = (List) this.programs.get(programName);
        if (is != null) {
            this.programs.put(programName, is.subList(0, instructionIndex));
        }
    }

    public void deleteProgram() {
        deleteProgram("default");
    }

    public void deleteProgram(String programName) {
        rollback(programName, 0);
    }

    public void insertAfter(Token t, Object text) {
        insertAfter("default", t, text);
    }

    public void insertAfter(int index, Object text) {
        insertAfter("default", index, text);
    }

    public void insertAfter(String programName, Token t, Object text) {
        insertAfter(programName, t.getTokenIndex(), text);
    }

    public void insertAfter(String programName, int index, Object text) {
        RewriteOperation op = new InsertAfterOp(index, text);
        List<RewriteOperation> rewrites = getProgram(programName);
        op.instructionIndex = rewrites.size();
        rewrites.add(op);
    }

    public void insertBefore(Token t, Object text) {
        insertBefore("default", t, text);
    }

    public void insertBefore(int index, Object text) {
        insertBefore("default", index, text);
    }

    public void insertBefore(String programName, Token t, Object text) {
        insertBefore(programName, t.getTokenIndex(), text);
    }

    public void insertBefore(String programName, int index, Object text) {
        RewriteOperation op = new InsertBeforeOp(index, text);
        List<RewriteOperation> rewrites = getProgram(programName);
        op.instructionIndex = rewrites.size();
        rewrites.add(op);
    }

    public void replace(int index, Object text) {
        replace("default", index, index, text);
    }

    public void replace(int from, int to, Object text) {
        replace("default", from, to, text);
    }

    public void replace(Token indexT, Object text) {
        replace("default", indexT, indexT, text);
    }

    public void replace(Token from, Token to, Object text) {
        replace("default", from, to, text);
    }

    public void replace(String programName, int from, int to, Object text) {
        if (from > to || from < 0 || to < 0 || to >= this.tokens.size()) {
            throw new IllegalArgumentException("replace: range invalid: " + from + ".." + to + "(size=" + this.tokens.size() + ")");
        }
        RewriteOperation op = new ReplaceOp(from, to, text);
        List<RewriteOperation> rewrites = getProgram(programName);
        op.instructionIndex = rewrites.size();
        rewrites.add(op);
    }

    public void replace(String programName, Token from, Token to, Object text) {
        replace(programName, from.getTokenIndex(), to.getTokenIndex(), text);
    }

    public void delete(int index) {
        delete("default", index, index);
    }

    public void delete(int from, int to) {
        delete("default", from, to);
    }

    public void delete(Token indexT) {
        delete("default", indexT, indexT);
    }

    public void delete(Token from, Token to) {
        delete("default", from, to);
    }

    public void delete(String programName, int from, int to) {
        replace(programName, from, to, (Object) null);
    }

    public void delete(String programName, Token from, Token to) {
        replace(programName, from, to, (Object) null);
    }

    public int getLastRewriteTokenIndex() {
        return getLastRewriteTokenIndex("default");
    }

    protected int getLastRewriteTokenIndex(String programName) {
        Integer I = (Integer) this.lastRewriteTokenIndexes.get(programName);
        if (I == null) {
            return -1;
        }
        return I.intValue();
    }

    protected void setLastRewriteTokenIndex(String programName, int i) {
        this.lastRewriteTokenIndexes.put(programName, Integer.valueOf(i));
    }

    protected List getProgram(String name) {
        List<RewriteOperation> is = (List) this.programs.get(name);
        if (is == null) {
            return initializeProgram(name);
        }
        return is;
    }

    private List initializeProgram(String name) {
        ArrayList arrayList = new ArrayList(100);
        this.programs.put(name, arrayList);
        return arrayList;
    }

    public String getText() {
        return getText("default", Interval.of(0, this.tokens.size() - 1));
    }

    public String getText(String programName) {
        return getText(programName, Interval.of(0, this.tokens.size() - 1));
    }

    public String getText(Interval interval) {
        return getText("default", interval);
    }

    public String getText(String programName, Interval interval) {
        List<RewriteOperation> rewrites = (List) this.programs.get(programName);
        int start = interval.a;
        int stop = interval.b;
        if (stop > this.tokens.size() - 1) {
            stop = this.tokens.size() - 1;
        }
        if (start < 0) {
            start = 0;
        }
        if (rewrites == null || rewrites.isEmpty()) {
            return this.tokens.getText(interval);
        }
        StringBuilder buf = new StringBuilder();
        Map<Integer, RewriteOperation> indexToOp = reduceToSingleOperationPerIndex(rewrites);
        int i = start;
        while (i <= stop && i < this.tokens.size()) {
            RewriteOperation op = (RewriteOperation) indexToOp.get(Integer.valueOf(i));
            indexToOp.remove(Integer.valueOf(i));
            Token t = this.tokens.get(i);
            if (op == null) {
                if (t.getType() != -1) {
                    buf.append(t.getText());
                }
                i++;
            } else {
                i = op.execute(buf);
            }
        }
        if (stop == this.tokens.size() - 1) {
            for (RewriteOperation op2 : indexToOp.values()) {
                if (op2.index >= this.tokens.size() - 1) {
                    buf.append(op2.text);
                }
            }
        }
        return buf.toString();
    }

    protected Map reduceToSingleOperationPerIndex(List list) {
        for (int i = 0; i < list.size(); i++) {
            RewriteOperation op = (RewriteOperation) list.get(i);
            if (op != null && (op instanceof ReplaceOp)) {
                ReplaceOp rop = (ReplaceOp) list.get(i);
                List<? extends InsertBeforeOp> inserts = getKindOfOps(list, InsertBeforeOp.class, i);
                for (InsertBeforeOp iop : inserts) {
                    if (iop.index == rop.index) {
                        list.set(iop.instructionIndex, (Object) null);
                        rop.text = iop.text.toString() + (rop.text != null ? rop.text.toString() : "");
                    } else if (iop.index > rop.index && iop.index <= rop.lastIndex) {
                        list.set(iop.instructionIndex, (Object) null);
                    }
                }
                List<? extends ReplaceOp> prevReplaces = getKindOfOps(list, ReplaceOp.class, i);
                for (ReplaceOp prevRop : prevReplaces) {
                    if (prevRop.index >= rop.index && prevRop.lastIndex <= rop.lastIndex) {
                        list.set(prevRop.instructionIndex, (Object) null);
                    } else {
                        boolean disjoint = prevRop.lastIndex < rop.index || prevRop.index > rop.lastIndex;
                        if (prevRop.text == null && rop.text == null && !disjoint) {
                            list.set(prevRop.instructionIndex, (Object) null);
                            rop.index = Math.min(prevRop.index, rop.index);
                            rop.lastIndex = Math.max(prevRop.lastIndex, rop.lastIndex);
                            System.out.println("new rop " + rop);
                        } else if (!disjoint) {
                            throw new IllegalArgumentException("replace op boundaries of " + rop + " overlap with previous " + prevRop);
                        }
                    }
                }
            }
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            RewriteOperation op2 = (RewriteOperation) list.get(i2);
            if (op2 != null && (op2 instanceof InsertBeforeOp)) {
                InsertBeforeOp iop2 = (InsertBeforeOp) list.get(i2);
                List<? extends InsertBeforeOp> prevInserts = getKindOfOps(list, InsertBeforeOp.class, i2);
                for (InsertBeforeOp prevIop : prevInserts) {
                    if (prevIop.index == iop2.index) {
                        if (InsertAfterOp.class.isInstance(prevIop)) {
                            iop2.text = catOpText(prevIop.text, iop2.text);
                            list.set(prevIop.instructionIndex, (Object) null);
                        } else if (InsertBeforeOp.class.isInstance(prevIop)) {
                            iop2.text = catOpText(iop2.text, prevIop.text);
                            list.set(prevIop.instructionIndex, (Object) null);
                        }
                    }
                }
                List<? extends ReplaceOp> prevReplaces2 = getKindOfOps(list, ReplaceOp.class, i2);
                for (ReplaceOp rop2 : prevReplaces2) {
                    if (iop2.index == rop2.index) {
                        rop2.text = catOpText(iop2.text, rop2.text);
                        list.set(i2, (Object) null);
                    } else if (iop2.index >= rop2.index && iop2.index <= rop2.lastIndex) {
                        throw new IllegalArgumentException("insert op " + iop2 + " within boundaries of previous " + rop2);
                    }
                }
            }
        }
        HashMap hashMap = new HashMap();
        for (int i3 = 0; i3 < list.size(); i3++) {
            RewriteOperation op3 = (RewriteOperation) list.get(i3);
            if (op3 != null) {
                if (hashMap.get(Integer.valueOf(op3.index)) != null) {
                    throw new Error("should only be one op per index");
                }
                hashMap.put(Integer.valueOf(op3.index), op3);
            }
        }
        return hashMap;
    }

    protected String catOpText(Object a, Object b) {
        String x = a != null ? a.toString() : "";
        String y = b != null ? b.toString() : "";
        return x + y;
    }

    protected List getKindOfOps(List list, Class cls, int before) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < before && i < list.size(); i++) {
            RewriteOperation op = (RewriteOperation) list.get(i);
            if (op != null && cls.isInstance(op)) {
                arrayList.add(cls.cast(op));
            }
        }
        return arrayList;
    }
}
