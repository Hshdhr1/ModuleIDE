package org.antlr.v4.runtime.tree;

import org.antlr.v4.runtime.ParserRuleContext;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public interface ParseTreeListener {
    void enterEveryRule(ParserRuleContext parserRuleContext);

    void exitEveryRule(ParserRuleContext parserRuleContext);

    void visitErrorNode(ErrorNode errorNode);

    void visitTerminal(TerminalNode terminalNode);
}
