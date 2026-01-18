package org.antlr.v4.runtime.atn;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class SingletonPredictionContext extends PredictionContext {
    static final /* synthetic */ boolean $assertionsDisabled;
    public final PredictionContext parent;
    public final int returnState;

    static {
        $assertionsDisabled = !SingletonPredictionContext.class.desiredAssertionStatus();
    }

    SingletonPredictionContext(PredictionContext parent, int returnState) {
        super(parent != null ? calculateHashCode(parent, returnState) : calculateEmptyHashCode());
        if (!$assertionsDisabled && returnState == -1) {
            throw new AssertionError();
        }
        this.parent = parent;
        this.returnState = returnState;
    }

    public static SingletonPredictionContext create(PredictionContext parent, int returnState) {
        return (returnState == Integer.MAX_VALUE && parent == null) ? EMPTY : new SingletonPredictionContext(parent, returnState);
    }

    public int size() {
        return 1;
    }

    public PredictionContext getParent(int index) {
        if ($assertionsDisabled || index == 0) {
            return this.parent;
        }
        throw new AssertionError();
    }

    public int getReturnState(int index) {
        if ($assertionsDisabled || index == 0) {
            return this.returnState;
        }
        throw new AssertionError();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o instanceof SingletonPredictionContext) && hashCode() == o.hashCode()) {
            SingletonPredictionContext s = (SingletonPredictionContext) o;
            return this.returnState == s.returnState && this.parent != null && this.parent.equals(s.parent);
        }
        return false;
    }

    public String toString() {
        String up = this.parent != null ? this.parent.toString() : "";
        if (up.length() == 0) {
            if (this.returnState == Integer.MAX_VALUE) {
                return "$";
            }
            return String.valueOf(this.returnState);
        }
        return String.valueOf(this.returnState) + " " + up;
    }
}
