package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.misc.MurmurHash;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class LexerTypeAction implements LexerAction {
    private final int type;

    public LexerTypeAction(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public LexerActionType getActionType() {
        return LexerActionType.TYPE;
    }

    public boolean isPositionDependent() {
        return false;
    }

    public void execute(Lexer lexer) {
        lexer.setType(this.type);
    }

    public int hashCode() {
        int hash = MurmurHash.initialize();
        return MurmurHash.finish(MurmurHash.update(MurmurHash.update(hash, getActionType().ordinal()), this.type), 2);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof LexerTypeAction) && this.type == ((LexerTypeAction) obj).type;
    }

    public String toString() {
        return String.format("type(%d)", new Object[]{Integer.valueOf(this.type)});
    }
}
