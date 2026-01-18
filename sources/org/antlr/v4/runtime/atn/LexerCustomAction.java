package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.misc.MurmurHash;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public final class LexerCustomAction implements LexerAction {
    private final int actionIndex;
    private final int ruleIndex;

    public LexerCustomAction(int ruleIndex, int actionIndex) {
        this.ruleIndex = ruleIndex;
        this.actionIndex = actionIndex;
    }

    public int getRuleIndex() {
        return this.ruleIndex;
    }

    public int getActionIndex() {
        return this.actionIndex;
    }

    public LexerActionType getActionType() {
        return LexerActionType.CUSTOM;
    }

    public boolean isPositionDependent() {
        return true;
    }

    public void execute(Lexer lexer) {
        lexer.action(null, this.ruleIndex, this.actionIndex);
    }

    public int hashCode() {
        int hash = MurmurHash.initialize();
        return MurmurHash.finish(MurmurHash.update(MurmurHash.update(MurmurHash.update(hash, getActionType().ordinal()), this.ruleIndex), this.actionIndex), 3);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LexerCustomAction)) {
            return false;
        }
        LexerCustomAction other = (LexerCustomAction) obj;
        return this.ruleIndex == other.ruleIndex && this.actionIndex == other.actionIndex;
    }
}
