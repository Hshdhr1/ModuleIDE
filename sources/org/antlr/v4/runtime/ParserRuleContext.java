package org.antlr.v4.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ErrorNodeImpl;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ParserRuleContext extends RuleContext {
    public List children;
    public RecognitionException exception;
    public Token start;
    public Token stop;

    public ParserRuleContext() {
    }

    public void copyFrom(ParserRuleContext ctx) {
        this.parent = ctx.parent;
        this.invokingState = ctx.invokingState;
        this.start = ctx.start;
        this.stop = ctx.stop;
        if (ctx.children != null) {
            this.children = new ArrayList();
            for (ParseTree child : ctx.children) {
                if (child instanceof ErrorNode) {
                    addChild((ErrorNode) child);
                }
            }
        }
    }

    public ParserRuleContext(ParserRuleContext parent, int invokingStateNumber) {
        super(parent, invokingStateNumber);
    }

    public void enterRule(ParseTreeListener listener) {
    }

    public void exitRule(ParseTreeListener listener) {
    }

    public ParseTree addAnyChild(ParseTree parseTree) {
        if (this.children == null) {
            this.children = new ArrayList();
        }
        this.children.add(parseTree);
        return parseTree;
    }

    public RuleContext addChild(RuleContext ruleInvocation) {
        return (RuleContext) addAnyChild(ruleInvocation);
    }

    public TerminalNode addChild(TerminalNode t) {
        t.setParent(this);
        return (TerminalNode) addAnyChild(t);
    }

    public ErrorNode addErrorNode(ErrorNode errorNode) {
        errorNode.setParent(this);
        return (ErrorNode) addAnyChild(errorNode);
    }

    @Deprecated
    public TerminalNode addChild(Token matchedToken) {
        TerminalNodeImpl t = new TerminalNodeImpl(matchedToken);
        addAnyChild(t);
        t.setParent(this);
        return t;
    }

    @Deprecated
    public ErrorNode addErrorNode(Token badToken) {
        ErrorNodeImpl t = new ErrorNodeImpl(badToken);
        addAnyChild(t);
        t.setParent(this);
        return t;
    }

    public void removeLastChild() {
        if (this.children != null) {
            this.children.remove(this.children.size() - 1);
        }
    }

    public ParserRuleContext getParent() {
        return (ParserRuleContext) super.getParent();
    }

    public ParseTree getChild(int i) {
        if (this.children == null || i < 0 || i >= this.children.size()) {
            return null;
        }
        return (ParseTree) this.children.get(i);
    }

    public ParseTree getChild(Class cls, int i) {
        if (this.children == null || i < 0 || i >= this.children.size()) {
            return null;
        }
        int j = -1;
        for (ParseTree o : this.children) {
            if (cls.isInstance(o) && (j = j + 1) == i) {
                return (ParseTree) cls.cast(o);
            }
        }
        return null;
    }

    public TerminalNode getToken(int ttype, int i) {
        if (this.children == null || i < 0 || i >= this.children.size()) {
            return null;
        }
        int j = -1;
        for (ParseTree o : this.children) {
            if (o instanceof TerminalNode) {
                TerminalNode tnode = (TerminalNode) o;
                Token symbol = tnode.getSymbol();
                if (symbol.getType() == ttype && (j = j + 1) == i) {
                    return tnode;
                }
            }
        }
        return null;
    }

    public List getTokens(int ttype) {
        if (this.children == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = null;
        for (ParseTree o : this.children) {
            if (o instanceof TerminalNode) {
                TerminalNode tnode = (TerminalNode) o;
                Token symbol = tnode.getSymbol();
                if (symbol.getType() == ttype) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(tnode);
                }
            }
        }
        if (arrayList == null) {
            List<TerminalNode> tokens = Collections.emptyList();
            return tokens;
        }
        return arrayList;
    }

    public ParserRuleContext getRuleContext(Class cls, int i) {
        return (ParserRuleContext) getChild(cls, i);
    }

    public List getRuleContexts(Class cls) {
        if (this.children == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = null;
        for (ParseTree o : this.children) {
            if (cls.isInstance(o)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(cls.cast(o));
            }
        }
        if (arrayList == null) {
            return Collections.emptyList();
        }
        return arrayList;
    }

    public int getChildCount() {
        if (this.children != null) {
            return this.children.size();
        }
        return 0;
    }

    public Interval getSourceInterval() {
        if (this.start == null) {
            return Interval.INVALID;
        }
        if (this.stop == null || this.stop.getTokenIndex() < this.start.getTokenIndex()) {
            return Interval.of(this.start.getTokenIndex(), this.start.getTokenIndex() - 1);
        }
        return Interval.of(this.start.getTokenIndex(), this.stop.getTokenIndex());
    }

    public Token getStart() {
        return this.start;
    }

    public Token getStop() {
        return this.stop;
    }

    public String toInfoString(Parser recognizer) {
        List<String> rules = recognizer.getRuleInvocationStack(this);
        Collections.reverse(rules);
        return "ParserRuleContext" + rules + "{start=" + this.start + ", stop=" + this.stop + '}';
    }
}
