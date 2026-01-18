package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.misc.IntervalSet;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public final class RangeTransition extends Transition {
    public final int from;
    public final int to;

    public RangeTransition(ATNState target, int from, int to) {
        super(target);
        this.from = from;
        this.to = to;
    }

    public int getSerializationType() {
        return 2;
    }

    public IntervalSet label() {
        return IntervalSet.of(this.from, this.to);
    }

    public boolean matches(int symbol, int minVocabSymbol, int maxVocabSymbol) {
        return symbol >= this.from && symbol <= this.to;
    }

    public String toString() {
        return new StringBuilder("'").appendCodePoint(this.from).append("'..'").appendCodePoint(this.to).append("'").toString();
    }
}
