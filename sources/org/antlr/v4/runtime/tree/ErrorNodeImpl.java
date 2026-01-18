package org.antlr.v4.runtime.tree;

import org.antlr.v4.runtime.Token;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ErrorNodeImpl extends TerminalNodeImpl implements ErrorNode {
    public ErrorNodeImpl(Token token) {
        super(token);
    }

    public Object accept(ParseTreeVisitor parseTreeVisitor) {
        return parseTreeVisitor.visitErrorNode(this);
    }
}
