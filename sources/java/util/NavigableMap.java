package java.util;

import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface NavigableMap extends SortedMap {
    Map.Entry ceilingEntry(Object obj);

    Object ceilingKey(Object obj);

    NavigableSet descendingKeySet();

    NavigableMap descendingMap();

    Map.Entry firstEntry();

    Map.Entry floorEntry(Object obj);

    Object floorKey(Object obj);

    NavigableMap headMap(Object obj, boolean z);

    SortedMap headMap(Object obj);

    Map.Entry higherEntry(Object obj);

    Object higherKey(Object obj);

    Map.Entry lastEntry();

    Map.Entry lowerEntry(Object obj);

    Object lowerKey(Object obj);

    NavigableSet navigableKeySet();

    Map.Entry pollFirstEntry();

    Map.Entry pollLastEntry();

    NavigableMap subMap(Object obj, boolean z, Object obj2, boolean z2);

    SortedMap subMap(Object obj, Object obj2);

    NavigableMap tailMap(Object obj, boolean z);

    SortedMap tailMap(Object obj);
}
