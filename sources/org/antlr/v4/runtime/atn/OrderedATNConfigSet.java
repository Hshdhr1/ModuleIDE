package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.misc.ObjectEqualityComparator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class OrderedATNConfigSet extends ATNConfigSet {
    public OrderedATNConfigSet() {
        this.configLookup = new LexerConfigHashSet();
    }

    public static class LexerConfigHashSet extends ATNConfigSet.AbstractConfigHashSet {
        public LexerConfigHashSet() {
            super(ObjectEqualityComparator.INSTANCE);
        }
    }
}
