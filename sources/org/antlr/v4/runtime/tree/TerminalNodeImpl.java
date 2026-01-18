package org.antlr.v4.runtime.tree;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class TerminalNodeImpl implements TerminalNode {
    public ParseTree parent;
    public Token symbol;

    public TerminalNodeImpl(Token symbol) {
        this.symbol = symbol;
    }

    public ParseTree getChild(int i) {
        return null;
    }

    public Token getSymbol() {
        return this.symbol;
    }

    public ParseTree getParent() {
        return this.parent;
    }

    public void setParent(RuleContext parent) {
        this.parent = parent;
    }

    public Token getPayload() {
        return this.symbol;
    }

    public Interval getSourceInterval() {
        if (this.symbol == null) {
            return Interval.INVALID;
        }
        int tokenIndex = this.symbol.getTokenIndex();
        return new Interval(tokenIndex, tokenIndex);
    }

    public int getChildCount() {
        return 0;
    }

    public Object accept(ParseTreeVisitor parseTreeVisitor) {
        return parseTreeVisitor.visitTerminal(this);
    }

    public String getText() {
        return this.symbol.getText();
    }

    public String toStringTree(Parser parser) {
        return toString();
    }

    public String toString() {
        return this.symbol.getType() == -1 ? "<EOF>" : this.symbol.getText();
    }

    public String toStringTree() {
        return toString();
    }
}
