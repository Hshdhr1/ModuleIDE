package org.antlr.v4.runtime.tree;

import java.util.IdentityHashMap;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ParseTreeProperty {
    protected Map annotations = new IdentityHashMap();

    public Object get(ParseTree node) {
        return this.annotations.get(node);
    }

    public void put(ParseTree node, Object obj) {
        this.annotations.put(node, obj);
    }

    public Object removeFrom(ParseTree node) {
        return this.annotations.remove(node);
    }
}
