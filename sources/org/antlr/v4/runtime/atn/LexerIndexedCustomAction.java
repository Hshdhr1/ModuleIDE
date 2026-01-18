package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.misc.MurmurHash;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public final class LexerIndexedCustomAction implements LexerAction {
    private final LexerAction action;
    private final int offset;

    public LexerIndexedCustomAction(int offset, LexerAction action) {
        this.offset = offset;
        this.action = action;
    }

    public int getOffset() {
        return this.offset;
    }

    public LexerAction getAction() {
        return this.action;
    }

    public LexerActionType getActionType() {
        return this.action.getActionType();
    }

    public boolean isPositionDependent() {
        return true;
    }

    public void execute(Lexer lexer) {
        this.action.execute(lexer);
    }

    public int hashCode() {
        int hash = MurmurHash.initialize();
        return MurmurHash.finish(MurmurHash.update(MurmurHash.update(hash, this.offset), this.action), 2);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LexerIndexedCustomAction)) {
            return false;
        }
        LexerIndexedCustomAction other = (LexerIndexedCustomAction) obj;
        return this.offset == other.offset && this.action.equals(other.action);
    }
}
