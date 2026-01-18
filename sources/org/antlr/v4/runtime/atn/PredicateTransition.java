package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.atn.SemanticContext;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public final class PredicateTransition extends AbstractPredicateTransition {
    public final boolean isCtxDependent;
    public final int predIndex;
    public final int ruleIndex;

    public PredicateTransition(ATNState target, int ruleIndex, int predIndex, boolean isCtxDependent) {
        super(target);
        this.ruleIndex = ruleIndex;
        this.predIndex = predIndex;
        this.isCtxDependent = isCtxDependent;
    }

    public int getSerializationType() {
        return 4;
    }

    public boolean isEpsilon() {
        return true;
    }

    public boolean matches(int symbol, int minVocabSymbol, int maxVocabSymbol) {
        return false;
    }

    public SemanticContext.Predicate getPredicate() {
        return new SemanticContext.Predicate(this.ruleIndex, this.predIndex, this.isCtxDependent);
    }

    public String toString() {
        return "pred_" + this.ruleIndex + ":" + this.predIndex;
    }
}
