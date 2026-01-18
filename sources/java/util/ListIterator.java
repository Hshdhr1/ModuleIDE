package java.util;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface ListIterator extends Iterator {
    void add(Object obj);

    boolean hasNext();

    boolean hasPrevious();

    Object next();

    int nextIndex();

    Object previous();

    int previousIndex();

    void remove();

    void set(Object obj);
}
