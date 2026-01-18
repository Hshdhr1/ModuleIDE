package org.antlr.v4.runtime.misc;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public final class ObjectEqualityComparator extends AbstractEqualityComparator {
    public static final ObjectEqualityComparator INSTANCE = new ObjectEqualityComparator();

    public int hashCode(Object obj) {
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public boolean equals(Object a, Object b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }
}
