package org.antlr.v4.runtime.atn;

import java.util.Arrays;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ArrayPredictionContext extends PredictionContext {
    static final /* synthetic */ boolean $assertionsDisabled;
    public final PredictionContext[] parents;
    public final int[] returnStates;

    static {
        $assertionsDisabled = !ArrayPredictionContext.class.desiredAssertionStatus();
    }

    public ArrayPredictionContext(SingletonPredictionContext a) {
        this(new PredictionContext[]{a.parent}, new int[]{a.returnState});
    }

    public ArrayPredictionContext(PredictionContext[] parents, int[] returnStates) {
        super(calculateHashCode(parents, returnStates));
        if (!$assertionsDisabled && (parents == null || parents.length <= 0)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && (returnStates == null || returnStates.length <= 0)) {
            throw new AssertionError();
        }
        this.parents = parents;
        this.returnStates = returnStates;
    }

    public boolean isEmpty() {
        return this.returnStates[0] == Integer.MAX_VALUE;
    }

    public int size() {
        return this.returnStates.length;
    }

    public PredictionContext getParent(int index) {
        return this.parents[index];
    }

    public int getReturnState(int index) {
        return this.returnStates[index];
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o instanceof ArrayPredictionContext) && hashCode() == o.hashCode()) {
            ArrayPredictionContext a = (ArrayPredictionContext) o;
            return Arrays.equals(this.returnStates, a.returnStates) && Arrays.equals(this.parents, a.parents);
        }
        return false;
    }

    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for (int i = 0; i < this.returnStates.length; i++) {
            if (i > 0) {
                buf.append(", ");
            }
            if (this.returnStates[i] == Integer.MAX_VALUE) {
                buf.append("$");
            } else {
                buf.append(this.returnStates[i]);
                if (this.parents[i] != null) {
                    buf.append(' ');
                    buf.append(this.parents[i].toString());
                } else {
                    buf.append("null");
                }
            }
        }
        buf.append("]");
        return buf.toString();
    }
}
