package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.atn.SemanticContext;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public final class PrecedencePredicateTransition extends AbstractPredicateTransition {
    public final int precedence;

    public PrecedencePredicateTransition(ATNState target, int precedence) {
        super(target);
        this.precedence = precedence;
    }

    public int getSerializationType() {
        return 10;
    }

    public boolean isEpsilon() {
        return true;
    }

    public boolean matches(int symbol, int minVocabSymbol, int maxVocabSymbol) {
        return false;
    }

    public SemanticContext.PrecedencePredicate getPredicate() {
        return new SemanticContext.PrecedencePredicate(this.precedence);
    }

    public String toString() {
        return this.precedence + " >= _p";
    }
}
