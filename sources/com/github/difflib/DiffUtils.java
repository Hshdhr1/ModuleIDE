package com.github.difflib;

import com.github.difflib.algorithm.DiffAlgorithmFactory;
import com.github.difflib.algorithm.DiffAlgorithmI;
import com.github.difflib.algorithm.DiffAlgorithmListener;
import com.github.difflib.algorithm.myers.MyersDiff;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import com.github.difflib.patch.PatchFailedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public final class DiffUtils {
    static DiffAlgorithmFactory DEFAULT_DIFF = MyersDiff.factory();

    public static void withDefaultDiffAlgorithmFactory(DiffAlgorithmFactory diffAlgorithmFactory) {
        DEFAULT_DIFF = diffAlgorithmFactory;
    }

    public static Patch diff(List list, List list2, DiffAlgorithmListener diffAlgorithmListener) {
        return diff(list, list2, DEFAULT_DIFF.create(), diffAlgorithmListener);
    }

    public static Patch diff(List list, List list2) {
        return diff(list, list2, DEFAULT_DIFF.create(), null);
    }

    public static Patch diff(List list, List list2, boolean z) {
        return diff(list, list2, DEFAULT_DIFF.create(), null, z);
    }

    public static Patch diff(String str, String str2, DiffAlgorithmListener diffAlgorithmListener) {
        return diff(Arrays.asList(str.split("\n")), Arrays.asList(str2.split("\n")), diffAlgorithmListener);
    }

    public static Patch diff(List list, List list2, BiPredicate biPredicate) {
        if (biPredicate != null) {
            return diff(list, list2, DEFAULT_DIFF.create(biPredicate));
        }
        return diff(list, list2, new MyersDiff());
    }

    public static Patch diff(List list, List list2, DiffAlgorithmI diffAlgorithmI, DiffAlgorithmListener diffAlgorithmListener) {
        return diff(list, list2, diffAlgorithmI, diffAlgorithmListener, false);
    }

    public static Patch diff(List list, List list2, DiffAlgorithmI diffAlgorithmI, DiffAlgorithmListener diffAlgorithmListener, boolean z) {
        DiffUtils$$ExternalSyntheticBackport1.m(list, "original must not be null");
        DiffUtils$$ExternalSyntheticBackport1.m(list2, "revised must not be null");
        DiffUtils$$ExternalSyntheticBackport1.m(diffAlgorithmI, "algorithm must not be null");
        return Patch.generate(list, list2, diffAlgorithmI.computeDiff(list, list2, diffAlgorithmListener), z);
    }

    public static Patch diff(List list, List list2, DiffAlgorithmI diffAlgorithmI) {
        return diff(list, list2, diffAlgorithmI, null);
    }

    public static Patch diffInline(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (char c : str.toCharArray()) {
            arrayList.add(Character.valueOf(c).toString());
        }
        for (char c2 : str2.toCharArray()) {
            arrayList2.add(Character.valueOf(c2).toString());
        }
        Patch diff = diff(arrayList, arrayList2);
        for (AbstractDelta abstractDelta : diff.getDeltas()) {
            abstractDelta.getSource().setLines(compressLines(abstractDelta.getSource().getLines(), ""));
            abstractDelta.getTarget().setLines(compressLines(abstractDelta.getTarget().getLines(), ""));
        }
        return diff;
    }

    public static List patch(List list, Patch patch) throws PatchFailedException {
        return patch.applyTo(list);
    }

    public static List unpatch(List list, Patch patch) {
        return patch.restore(list);
    }

    private static List compressLines(List list, String str) {
        if (list.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return Collections.singletonList(DiffUtils$$ExternalSyntheticBackport0.m(str, list));
    }

    private DiffUtils() {
    }
}
