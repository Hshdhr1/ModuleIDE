package org.antlr.v4.runtime.tree;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public abstract class AbstractParseTreeVisitor implements ParseTreeVisitor {
    public Object visit(ParseTree tree) {
        return tree.accept(this);
    }

    public Object visitChildren(RuleNode node) {
        Object defaultResult = defaultResult();
        int n = node.getChildCount();
        for (int i = 0; i < n && shouldVisitNextChild(node, defaultResult); i++) {
            ParseTree c = node.getChild(i);
            defaultResult = aggregateResult(defaultResult, c.accept(this));
        }
        return defaultResult;
    }

    public Object visitTerminal(TerminalNode node) {
        return defaultResult();
    }

    public Object visitErrorNode(ErrorNode node) {
        return defaultResult();
    }

    protected Object defaultResult() {
        return null;
    }

    protected Object aggregateResult(Object obj, Object obj2) {
        return obj2;
    }

    protected boolean shouldVisitNextChild(RuleNode node, Object obj) {
        return true;
    }
}
