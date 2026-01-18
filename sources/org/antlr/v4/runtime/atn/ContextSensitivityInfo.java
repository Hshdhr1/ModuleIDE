package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.TokenStream;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ContextSensitivityInfo extends DecisionEventInfo {
    public ContextSensitivityInfo(int decision, ATNConfigSet configs, TokenStream input, int startIndex, int stopIndex) {
        super(decision, configs, input, startIndex, stopIndex, true);
    }
}
