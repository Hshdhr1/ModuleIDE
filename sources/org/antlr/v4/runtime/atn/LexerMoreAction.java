package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.misc.MurmurHash;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public final class LexerMoreAction implements LexerAction {
    public static final LexerMoreAction INSTANCE = new LexerMoreAction();

    private LexerMoreAction() {
    }

    public LexerActionType getActionType() {
        return LexerActionType.MORE;
    }

    public boolean isPositionDependent() {
        return false;
    }

    public void execute(Lexer lexer) {
        lexer.more();
    }

    public int hashCode() {
        int hash = MurmurHash.initialize();
        return MurmurHash.finish(MurmurHash.update(hash, getActionType().ordinal()), 1);
    }

    public boolean equals(Object obj) {
        return obj == this;
    }

    public String toString() {
        return "more";
    }
}
