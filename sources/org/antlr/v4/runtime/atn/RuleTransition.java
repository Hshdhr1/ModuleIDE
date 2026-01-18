package org.antlr.v4.runtime.atn;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public final class RuleTransition extends Transition {
    public ATNState followState;
    public final int precedence;
    public final int ruleIndex;

    @Deprecated
    public RuleTransition(RuleStartState ruleStart, int ruleIndex, ATNState followState) {
        this(ruleStart, ruleIndex, 0, followState);
    }

    public RuleTransition(RuleStartState ruleStart, int ruleIndex, int precedence, ATNState followState) {
        super(ruleStart);
        this.ruleIndex = ruleIndex;
        this.precedence = precedence;
        this.followState = followState;
    }

    public int getSerializationType() {
        return 3;
    }

    public boolean isEpsilon() {
        return true;
    }

    public boolean matches(int symbol, int minVocabSymbol, int maxVocabSymbol) {
        return false;
    }
}
