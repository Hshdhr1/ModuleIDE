package org.antlr.v4.runtime.atn;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.misc.IntervalSet;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public abstract class Transition {
    public static final int ACTION = 6;
    public static final int ATOM = 5;
    public static final int EPSILON = 1;
    public static final int NOT_SET = 8;
    public static final int PRECEDENCE = 10;
    public static final int PREDICATE = 4;
    public static final int RANGE = 2;
    public static final int RULE = 3;
    public static final int SET = 7;
    public static final int WILDCARD = 9;
    public static final List serializationNames = Collections.unmodifiableList(Arrays.asList(new String[]{"INVALID", "EPSILON", "RANGE", "RULE", "PREDICATE", "ATOM", "ACTION", "SET", "NOT_SET", "WILDCARD", "PRECEDENCE"}));
    public static final Map serializationTypes = Collections.unmodifiableMap(new 1());
    public ATNState target;

    public abstract int getSerializationType();

    public abstract boolean matches(int i, int i2, int i3);

    static class 1 extends HashMap {
        1() {
            put(EpsilonTransition.class, 1);
            put(RangeTransition.class, 2);
            put(RuleTransition.class, 3);
            put(PredicateTransition.class, 4);
            put(AtomTransition.class, 5);
            put(ActionTransition.class, 6);
            put(SetTransition.class, 7);
            put(NotSetTransition.class, 8);
            put(WildcardTransition.class, 9);
            put(PrecedencePredicateTransition.class, 10);
        }
    }

    protected Transition(ATNState target) {
        if (target == null) {
            throw new NullPointerException("target cannot be null.");
        }
        this.target = target;
    }

    public boolean isEpsilon() {
        return false;
    }

    public IntervalSet label() {
        return null;
    }
}
