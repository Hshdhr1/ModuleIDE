package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.misc.IntervalSet;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class SetTransition extends Transition {
    public final IntervalSet set;

    public SetTransition(ATNState target, IntervalSet set) {
        super(target);
        this.set = set == null ? IntervalSet.of(0) : set;
    }

    public int getSerializationType() {
        return 7;
    }

    public IntervalSet label() {
        return this.set;
    }

    public boolean matches(int symbol, int minVocabSymbol, int maxVocabSymbol) {
        return this.set.contains(symbol);
    }

    public String toString() {
        return this.set.toString();
    }
}
