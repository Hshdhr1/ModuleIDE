package java.util;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface NavigableSet extends SortedSet {
    Object ceiling(Object obj);

    Iterator descendingIterator();

    NavigableSet descendingSet();

    Object floor(Object obj);

    NavigableSet headSet(Object obj, boolean z);

    SortedSet headSet(Object obj);

    Object higher(Object obj);

    Iterator iterator();

    Object lower(Object obj);

    Object pollFirst();

    Object pollLast();

    NavigableSet subSet(Object obj, boolean z, Object obj2, boolean z2);

    SortedSet subSet(Object obj, Object obj2);

    NavigableSet tailSet(Object obj, boolean z);

    SortedSet tailSet(Object obj);
}
