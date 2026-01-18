package org.antlr.v4.runtime.tree;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RuleContext;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public interface ParseTree extends SyntaxTree {
    Object accept(ParseTreeVisitor parseTreeVisitor);

    ParseTree getChild(int i);

    ParseTree getParent();

    String getText();

    void setParent(RuleContext ruleContext);

    String toStringTree(Parser parser);
}
