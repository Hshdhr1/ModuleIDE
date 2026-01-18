package com.github.difflib.algorithm.myers;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import com.github.difflib.algorithm.DiffAlgorithmListener;
import java.util.function.Consumer;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public final /* synthetic */ class MyersDiffWithLinearSpace$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ DiffAlgorithmListener f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ MyersDiffWithLinearSpace$$ExternalSyntheticLambda2(DiffAlgorithmListener diffAlgorithmListener, int i) {
        this.f$0 = diffAlgorithmListener;
        this.f$1 = i;
    }

    public final void accept(Object obj) {
        MyersDiffWithLinearSpace.lambda$computeDiff$0(this.f$0, this.f$1, (Integer) obj);
    }
}
