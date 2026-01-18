package org.antlr.v4.runtime.misc;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class DoubleKeyMap {
    Map data = new LinkedHashMap();

    public Object put(Object obj, Object obj2, Object obj3) {
        LinkedHashMap linkedHashMap = (Map) this.data.get(obj);
        Object obj4 = null;
        if (linkedHashMap == null) {
            linkedHashMap = new LinkedHashMap();
            this.data.put(obj, linkedHashMap);
        } else {
            obj4 = linkedHashMap.get(obj2);
        }
        linkedHashMap.put(obj2, obj3);
        return obj4;
    }

    public Object get(Object obj, Object obj2) {
        Map map = (Map) this.data.get(obj);
        if (map == null) {
            return null;
        }
        return map.get(obj2);
    }

    public Map get(Object obj) {
        return (Map) this.data.get(obj);
    }

    public Collection values(Object obj) {
        Map map = (Map) this.data.get(obj);
        if (map == null) {
            return null;
        }
        return map.values();
    }

    public Set keySet() {
        return this.data.keySet();
    }

    public Set keySet(Object obj) {
        Map map = (Map) this.data.get(obj);
        if (map == null) {
            return null;
        }
        return map.keySet();
    }
}
