package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.misc.MurmurHash;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public final class LexerPushModeAction implements LexerAction {
    private final int mode;

    public LexerPushModeAction(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return this.mode;
    }

    public LexerActionType getActionType() {
        return LexerActionType.PUSH_MODE;
    }

    public boolean isPositionDependent() {
        return false;
    }

    public void execute(Lexer lexer) {
        lexer.pushMode(this.mode);
    }

    public int hashCode() {
        int hash = MurmurHash.initialize();
        return MurmurHash.finish(MurmurHash.update(MurmurHash.update(hash, getActionType().ordinal()), this.mode), 2);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof LexerPushModeAction) && this.mode == ((LexerPushModeAction) obj).mode;
    }

    public String toString() {
        return String.format("pushMode(%d)", new Object[]{Integer.valueOf(this.mode)});
    }
}
