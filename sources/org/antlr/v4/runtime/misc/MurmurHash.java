package org.antlr.v4.runtime.misc;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public final class MurmurHash {
    private static final int DEFAULT_SEED = 0;

    public static int initialize() {
        return initialize(0);
    }

    public static int initialize(int seed) {
        return seed;
    }

    public static int update(int hash, int value) {
        int k = value * (-862048943);
        int hash2 = hash ^ (((k << 15) | (k >>> 17)) * 461845907);
        return (((hash2 << 13) | (hash2 >>> 19)) * 5) - 430675100;
    }

    public static int update(int hash, Object value) {
        return update(hash, value != null ? value.hashCode() : 0);
    }

    public static int finish(int hash, int numberOfWords) {
        int hash2 = hash ^ (numberOfWords * 4);
        int hash3 = (hash2 ^ (hash2 >>> 16)) * (-2048144789);
        int hash4 = (hash3 ^ (hash3 >>> 13)) * (-1028477387);
        return hash4 ^ (hash4 >>> 16);
    }

    public static int hashCode(Object[] objArr, int seed) {
        int hash = initialize(seed);
        for (Object obj : objArr) {
            hash = update(hash, obj);
        }
        return finish(hash, objArr.length);
    }

    private MurmurHash() {
    }
}
