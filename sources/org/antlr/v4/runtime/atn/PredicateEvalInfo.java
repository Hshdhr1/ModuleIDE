package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.TokenStream;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class PredicateEvalInfo extends DecisionEventInfo {
    public final boolean evalResult;
    public final int predictedAlt;
    public final SemanticContext semctx;

    public PredicateEvalInfo(int decision, TokenStream input, int startIndex, int stopIndex, SemanticContext semctx, boolean evalResult, int predictedAlt, boolean fullCtx) {
        super(decision, new ATNConfigSet(), input, startIndex, stopIndex, fullCtx);
        this.semctx = semctx;
        this.evalResult = evalResult;
        this.predictedAlt = predictedAlt;
    }
}
