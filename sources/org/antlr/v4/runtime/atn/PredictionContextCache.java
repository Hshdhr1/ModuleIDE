package org.antlr.v4.runtime.atn;

import java.util.HashMap;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class PredictionContextCache {
    protected final Map cache = new HashMap();

    public PredictionContext add(PredictionContext ctx) {
        if (ctx == PredictionContext.EMPTY) {
            return PredictionContext.EMPTY;
        }
        PredictionContext existing = (PredictionContext) this.cache.get(ctx);
        if (existing == null) {
            this.cache.put(ctx, ctx);
            return ctx;
        }
        return existing;
    }

    public PredictionContext get(PredictionContext ctx) {
        return (PredictionContext) this.cache.get(ctx);
    }

    public int size() {
        return this.cache.size();
    }
}
