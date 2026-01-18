package org.antlr.v4.runtime.atn;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.UUID;
import org.antlr.v4.runtime.dfa.DFAState;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public abstract class ATNSimulator {
    public final ATN atn;
    protected final PredictionContextCache sharedContextCache;

    @Deprecated
    public static final int SERIALIZED_VERSION = ATNDeserializer.SERIALIZED_VERSION;

    @Deprecated
    public static final UUID SERIALIZED_UUID = ATNDeserializer.SERIALIZED_UUID;
    public static final DFAState ERROR = new DFAState(new ATNConfigSet());

    public abstract void reset();

    static {
        ERROR.stateNumber = Integer.MAX_VALUE;
    }

    public ATNSimulator(ATN atn, PredictionContextCache sharedContextCache) {
        this.atn = atn;
        this.sharedContextCache = sharedContextCache;
    }

    public void clearDFA() {
        throw new UnsupportedOperationException("This ATN simulator does not support clearing the DFA.");
    }

    public PredictionContextCache getSharedContextCache() {
        return this.sharedContextCache;
    }

    public PredictionContext getCachedContext(PredictionContext context) {
        if (this.sharedContextCache != null) {
            synchronized (this.sharedContextCache) {
                IdentityHashMap<PredictionContext, PredictionContext> visited = new IdentityHashMap<>();
                context = PredictionContext.getCachedContext(context, this.sharedContextCache, visited);
            }
        }
        return context;
    }

    @Deprecated
    public static ATN deserialize(char[] data) {
        return new ATNDeserializer().deserialize(data);
    }

    @Deprecated
    public static void checkCondition(boolean condition) {
        new ATNDeserializer().checkCondition(condition);
    }

    @Deprecated
    public static void checkCondition(boolean condition, String message) {
        new ATNDeserializer().checkCondition(condition, message);
    }

    @Deprecated
    public static int toInt(char c) {
        return ATNDeserializer.toInt(c);
    }

    @Deprecated
    public static int toInt32(char[] data, int offset) {
        return ATNDeserializer.toInt32(data, offset);
    }

    @Deprecated
    public static long toLong(char[] data, int offset) {
        return ATNDeserializer.toLong(data, offset);
    }

    @Deprecated
    public static UUID toUUID(char[] data, int offset) {
        return ATNDeserializer.toUUID(data, offset);
    }

    @Deprecated
    public static Transition edgeFactory(ATN atn, int type, int src, int trg, int arg1, int arg2, int arg3, List list) {
        return new ATNDeserializer().edgeFactory(atn, type, src, trg, arg1, arg2, arg3, list);
    }

    @Deprecated
    public static ATNState stateFactory(int type, int ruleIndex) {
        return new ATNDeserializer().stateFactory(type, ruleIndex);
    }
}
