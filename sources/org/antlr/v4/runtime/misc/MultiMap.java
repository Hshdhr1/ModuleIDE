package org.antlr.v4.runtime.misc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class MultiMap extends LinkedHashMap {
    public void map(Object obj, Object obj2) {
        ArrayList arrayList = (List) get(obj);
        if (arrayList == null) {
            arrayList = new ArrayList();
            super.put(obj, arrayList);
        }
        arrayList.add(obj2);
    }

    public List getPairs() {
        ArrayList arrayList = new ArrayList();
        for (Object obj : keySet()) {
            Iterator i$ = ((List) get(obj)).iterator();
            while (i$.hasNext()) {
                arrayList.add(new Pair(obj, i$.next()));
            }
        }
        return arrayList;
    }
}
