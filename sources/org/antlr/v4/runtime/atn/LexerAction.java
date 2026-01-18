package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.Lexer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public interface LexerAction {
    void execute(Lexer lexer);

    LexerActionType getActionType();

    boolean isPositionDependent();
}
