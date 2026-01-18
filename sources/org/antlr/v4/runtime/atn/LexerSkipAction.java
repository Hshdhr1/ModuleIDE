package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.misc.MurmurHash;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public final class LexerSkipAction implements LexerAction {
    public static final LexerSkipAction INSTANCE = new LexerSkipAction();

    private LexerSkipAction() {
    }

    public LexerActionType getActionType() {
        return LexerActionType.SKIP;
    }

    public boolean isPositionDependent() {
        return false;
    }

    public void execute(Lexer lexer) {
        lexer.skip();
    }

    public int hashCode() {
        int hash = MurmurHash.initialize();
        return MurmurHash.finish(MurmurHash.update(hash, getActionType().ordinal()), 1);
    }

    public boolean equals(Object obj) {
        return obj == this;
    }

    public String toString() {
        return "skip";
    }
}
