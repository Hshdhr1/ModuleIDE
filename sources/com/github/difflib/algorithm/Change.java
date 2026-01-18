package com.github.difflib.algorithm;

import com.github.difflib.patch.DeltaType;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public class Change {
    public final DeltaType deltaType;
    public final int endOriginal;
    public final int endRevised;
    public final int startOriginal;
    public final int startRevised;

    public Change(DeltaType deltaType, int i, int i2, int i3, int i4) {
        this.deltaType = deltaType;
        this.startOriginal = i;
        this.endOriginal = i2;
        this.startRevised = i3;
        this.endRevised = i4;
    }

    public Change withEndOriginal(int i) {
        return new Change(this.deltaType, this.startOriginal, i, this.startRevised, this.endRevised);
    }

    public Change withEndRevised(int i) {
        return new Change(this.deltaType, this.startOriginal, this.endOriginal, this.startRevised, i);
    }
}
