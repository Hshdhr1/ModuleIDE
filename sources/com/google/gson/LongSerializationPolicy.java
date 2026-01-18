package com.google.gson;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public abstract class LongSerializationPolicy {
    public static final LongSerializationPolicy DEFAULT = new 1("DEFAULT", 0);
    public static final LongSerializationPolicy STRING = new 2("STRING", 1);
    private static final /* synthetic */ LongSerializationPolicy[] $VALUES = {DEFAULT, STRING};

    public abstract JsonElement serialize(Long l);

    private LongSerializationPolicy(String str, int i) {
    }

    /* synthetic */ LongSerializationPolicy(String x0, int x1, 1 x2) {
        this(x0, x1);
    }

    public static LongSerializationPolicy valueOf(String name) {
        return (LongSerializationPolicy) Enum.valueOf(LongSerializationPolicy.class, name);
    }

    public static LongSerializationPolicy[] values() {
        return (LongSerializationPolicy[]) $VALUES.clone();
    }

    enum 1 extends LongSerializationPolicy {
        1(String str, int i) {
            super(str, i, null);
        }

        public JsonElement serialize(Long value) {
            return new JsonPrimitive((Number) value);
        }
    }

    enum 2 extends LongSerializationPolicy {
        2(String str, int i) {
            super(str, i, null);
        }

        public JsonElement serialize(Long value) {
            return new JsonPrimitive(String.valueOf(value));
        }
    }
}
