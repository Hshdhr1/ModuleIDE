package org.antlr.v4.runtime;

import java.util.Arrays;
import java.util.List;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.Trees;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class RuleContext implements RuleNode {
    public static final ParserRuleContext EMPTY = new ParserRuleContext();
    public int invokingState;
    public RuleContext parent;

    public RuleContext() {
        this.invokingState = -1;
    }

    public RuleContext(RuleContext parent, int invokingState) {
        this.invokingState = -1;
        this.parent = parent;
        this.invokingState = invokingState;
    }

    public int depth() {
        int n = 0;
        RuleContext p = this;
        while (p != null) {
            p = p.parent;
            n++;
        }
        return n;
    }

    public boolean isEmpty() {
        return this.invokingState == -1;
    }

    public Interval getSourceInterval() {
        return Interval.INVALID;
    }

    public RuleContext getRuleContext() {
        return this;
    }

    public RuleContext getParent() {
        return this.parent;
    }

    public RuleContext getPayload() {
        return this;
    }

    public String getText() {
        if (getChildCount() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getChildCount(); i++) {
            builder.append(getChild(i).getText());
        }
        return builder.toString();
    }

    public int getRuleIndex() {
        return -1;
    }

    public int getAltNumber() {
        return 0;
    }

    public void setAltNumber(int altNumber) {
    }

    public void setParent(RuleContext parent) {
        this.parent = parent;
    }

    public ParseTree getChild(int i) {
        return null;
    }

    public int getChildCount() {
        return 0;
    }

    public Object accept(ParseTreeVisitor parseTreeVisitor) {
        return parseTreeVisitor.visitChildren(this);
    }

    public String toStringTree(Parser recog) {
        return Trees.toStringTree(this, recog);
    }

    public String toStringTree(List list) {
        return Trees.toStringTree(this, list);
    }

    public String toStringTree() {
        return toStringTree((List) null);
    }

    public String toString() {
        return toString((List) null, (RuleContext) null);
    }

    public final String toString(Recognizer recognizer) {
        return toString(recognizer, ParserRuleContext.EMPTY);
    }

    public final String toString(List list) {
        return toString(list, (RuleContext) null);
    }

    public String toString(Recognizer recognizer, RuleContext stop) {
        String[] ruleNames = recognizer != null ? recognizer.getRuleNames() : null;
        List<String> ruleNamesList = ruleNames != null ? Arrays.asList(ruleNames) : null;
        return toString(ruleNamesList, stop);
    }

    public String toString(List list, RuleContext stop) {
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for (RuleContext p = this; p != null && p != stop; p = p.parent) {
            if (list == null) {
                if (!p.isEmpty()) {
                    buf.append(p.invokingState);
                }
            } else {
                int ruleIndex = p.getRuleIndex();
                String ruleName = (ruleIndex < 0 || ruleIndex >= list.size()) ? Integer.toString(ruleIndex) : (String) list.get(ruleIndex);
                buf.append(ruleName);
            }
            if (p.parent != null && (list != null || !p.parent.isEmpty())) {
                buf.append(" ");
            }
        }
        buf.append("]");
        return buf.toString();
    }
}
