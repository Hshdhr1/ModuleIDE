package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.misc.IntervalSet;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public abstract class CodePointTransitions {
    public static Transition createWithCodePoint(ATNState target, int codePoint) {
        return Character.isSupplementaryCodePoint(codePoint) ? new SetTransition(target, IntervalSet.of(codePoint)) : new AtomTransition(target, codePoint);
    }

    public static Transition createWithCodePointRange(ATNState target, int codePointFrom, int codePointTo) {
        return (Character.isSupplementaryCodePoint(codePointFrom) || Character.isSupplementaryCodePoint(codePointTo)) ? new SetTransition(target, IntervalSet.of(codePointFrom, codePointTo)) : new RangeTransition(target, codePointFrom, codePointTo);
    }
}
