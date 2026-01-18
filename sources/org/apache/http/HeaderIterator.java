package org.apache.http;

import java.util.Iterator;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes.dex */
public interface HeaderIterator extends Iterator {
    boolean hasNext();

    Header nextHeader();
}
