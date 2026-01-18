package com.google.android.material.stateful;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.collection.SimpleArrayMap;
import androidx.customview.view.AbsSavedState;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class ExtendableSavedState extends AbsSavedState {
    public static final Parcelable.Creator CREATOR = new 1();
    public final SimpleArrayMap extendableStates;

    /* synthetic */ ExtendableSavedState(Parcel x0, ClassLoader x1, 1 x2) {
        this(x0, x1);
    }

    public ExtendableSavedState(Parcelable superState) {
        super(superState);
        this.extendableStates = new SimpleArrayMap();
    }

    private ExtendableSavedState(Parcel in, ClassLoader loader) {
        super(in, loader);
        int size = in.readInt();
        String[] keys = new String[size];
        in.readStringArray(keys);
        Bundle[] states = new Bundle[size];
        in.readTypedArray(states, Bundle.CREATOR);
        this.extendableStates = new SimpleArrayMap(size);
        for (int i = 0; i < size; i++) {
            this.extendableStates.put(keys[i], states[i]);
        }
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        int size = this.extendableStates.size();
        out.writeInt(size);
        String[] keys = new String[size];
        Bundle[] states = new Bundle[size];
        for (int i = 0; i < size; i++) {
            keys[i] = (String) this.extendableStates.keyAt(i);
            states[i] = (Bundle) this.extendableStates.valueAt(i);
        }
        out.writeStringArray(keys);
        out.writeTypedArray(states, 0);
    }

    public String toString() {
        return "ExtendableSavedState{" + Integer.toHexString(System.identityHashCode(this)) + " states=" + this.extendableStates + "}";
    }

    class 1 implements Parcelable.ClassLoaderCreator {
        1() {
        }

        public ExtendableSavedState createFromParcel(Parcel in, ClassLoader loader) {
            return new ExtendableSavedState(in, loader, null);
        }

        public ExtendableSavedState createFromParcel(Parcel in) {
            return new ExtendableSavedState(in, null, null);
        }

        public ExtendableSavedState[] newArray(int size) {
            return new ExtendableSavedState[size];
        }
    }
}
