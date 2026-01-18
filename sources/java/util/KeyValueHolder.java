package java.util;

import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class KeyValueHolder implements Map.Entry {
    final Object key;
    final Object value;

    KeyValueHolder(Object obj, Object obj2) {
        obj.getClass();
        this.key = obj;
        obj2.getClass();
        this.value = obj2;
    }

    public Object getKey() {
        return this.key;
    }

    public Object getValue() {
        return this.value;
    }

    public Object setValue(Object obj) {
        throw new UnsupportedOperationException("not supported");
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return this.key.equals(entry.getKey()) && this.value.equals(entry.getValue());
    }

    public int hashCode() {
        return this.key.hashCode() ^ this.value.hashCode();
    }

    public String toString() {
        return this.key + "=" + this.value;
    }
}
