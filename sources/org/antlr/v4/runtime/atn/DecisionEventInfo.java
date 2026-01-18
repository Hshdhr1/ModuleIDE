package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.TokenStream;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class DecisionEventInfo {
    public final ATNConfigSet configs;
    public final int decision;
    public final boolean fullCtx;
    public final TokenStream input;
    public final int startIndex;
    public final int stopIndex;

    public DecisionEventInfo(int decision, ATNConfigSet configs, TokenStream input, int startIndex, int stopIndex, boolean fullCtx) {
        this.decision = decision;
        this.fullCtx = fullCtx;
        this.stopIndex = stopIndex;
        this.input = input;
        this.startIndex = startIndex;
        this.configs = configs;
    }
}
