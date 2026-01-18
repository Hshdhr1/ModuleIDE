package com.github.difflib.algorithm;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Arrays;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public interface DiffAlgorithmI {
    List computeDiff(List list, List list2, DiffAlgorithmListener diffAlgorithmListener);

    List computeDiff(Object[] objArr, Object[] objArr2, DiffAlgorithmListener diffAlgorithmListener);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static List $default$computeDiff(DiffAlgorithmI _this, Object[] objArr, Object[] objArr2, DiffAlgorithmListener diffAlgorithmListener) {
            return _this.computeDiff(Arrays.asList(objArr), Arrays.asList(objArr2), diffAlgorithmListener);
        }
    }
}
