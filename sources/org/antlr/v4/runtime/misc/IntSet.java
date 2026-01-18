package org.antlr.v4.runtime.misc;

import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public interface IntSet {
    void add(int i);

    IntSet addAll(IntSet intSet);

    IntSet and(IntSet intSet);

    IntSet complement(IntSet intSet);

    boolean contains(int i);

    boolean equals(Object obj);

    boolean isNil();

    IntSet or(IntSet intSet);

    void remove(int i);

    int size();

    IntSet subtract(IntSet intSet);

    List toList();

    String toString();
}
