package org.antlr.v4.runtime.tree;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public interface ParseTreeVisitor {
    Object visit(ParseTree parseTree);

    Object visitChildren(RuleNode ruleNode);

    Object visitErrorNode(ErrorNode errorNode);

    Object visitTerminal(TerminalNode terminalNode);
}
