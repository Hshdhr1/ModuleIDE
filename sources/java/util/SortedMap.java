package java.util;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface SortedMap extends Map {
    Comparator comparator();

    Set entrySet();

    Object firstKey();

    SortedMap headMap(Object obj);

    Set keySet();

    Object lastKey();

    SortedMap subMap(Object obj, Object obj2);

    SortedMap tailMap(Object obj);

    Collection values();
}
