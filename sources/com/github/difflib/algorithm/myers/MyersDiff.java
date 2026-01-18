package com.github.difflib.algorithm.myers;

import com.github.difflib.algorithm.Change;
import com.github.difflib.algorithm.DiffAlgorithmFactory;
import com.github.difflib.algorithm.DiffAlgorithmI;
import com.github.difflib.algorithm.DiffAlgorithmListener;
import com.github.difflib.patch.DeltaType;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public final class MyersDiff implements DiffAlgorithmI {
    private final BiPredicate equalizer;

    public /* synthetic */ List computeDiff(Object[] objArr, Object[] objArr2, DiffAlgorithmListener diffAlgorithmListener) {
        return DiffAlgorithmI.-CC.$default$computeDiff(this, objArr, objArr2, diffAlgorithmListener);
    }

    public MyersDiff() {
        this.equalizer = new MyersDiff$$ExternalSyntheticLambda1();
    }

    public MyersDiff(BiPredicate biPredicate) {
        MyersDiff$$ExternalSyntheticBackport0.m(biPredicate, "equalizer must not be null");
        this.equalizer = biPredicate;
    }

    public List computeDiff(List list, List list2, DiffAlgorithmListener diffAlgorithmListener) {
        MyersDiff$$ExternalSyntheticBackport0.m(list, "source list must not be null");
        MyersDiff$$ExternalSyntheticBackport0.m(list2, "target list must not be null");
        if (diffAlgorithmListener != null) {
            diffAlgorithmListener.diffStart();
        }
        List buildRevision = buildRevision(buildPath(list, list2, diffAlgorithmListener), list, list2);
        if (diffAlgorithmListener != null) {
            diffAlgorithmListener.diffEnd();
        }
        return buildRevision;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0083 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0099 A[LOOP:2: B:14:0x0081->B:18:0x0099, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00a8 A[EDGE_INSN: B:19:0x00a8->B:20:0x00a8 BREAK  A[LOOP:2: B:14:0x0081->B:18:0x0099], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c4 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00c7 A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.github.difflib.algorithm.myers.PathNode buildPath(java.util.List r30, java.util.List r31, com.github.difflib.algorithm.DiffAlgorithmListener r32) {
        /*
            Method dump skipped, instructions count: 241
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.difflib.algorithm.myers.MyersDiff.buildPath(java.util.List, java.util.List, com.github.difflib.algorithm.DiffAlgorithmListener):com.github.difflib.algorithm.myers.PathNode");
    }

    private List buildRevision(PathNode pathNode, List list, List list2) {
        MyersDiff$$ExternalSyntheticBackport0.m(pathNode, "path is null");
        MyersDiff$$ExternalSyntheticBackport0.m(list, "original sequence is null");
        MyersDiff$$ExternalSyntheticBackport0.m(list2, "revised sequence is null");
        ArrayList arrayList = new ArrayList();
        if (pathNode.isSnake()) {
            pathNode = pathNode.prev;
        }
        while (pathNode != null && pathNode.prev != null && pathNode.prev.j >= 0) {
            if (pathNode.isSnake()) {
                throw new IllegalStateException("bad diffpath: found snake when looking for diff");
            }
            int i = pathNode.i;
            int i2 = pathNode.j;
            pathNode = pathNode.prev;
            int i3 = pathNode.i;
            int i4 = pathNode.j;
            if (i3 == i && i4 != i2) {
                arrayList.add(new Change(DeltaType.INSERT, i3, i, i4, i2));
            } else if (i3 != i && i4 == i2) {
                arrayList.add(new Change(DeltaType.DELETE, i3, i, i4, i2));
            } else {
                arrayList.add(new Change(DeltaType.CHANGE, i3, i, i4, i2));
            }
            if (pathNode.isSnake()) {
                pathNode = pathNode.prev;
            }
        }
        return arrayList;
    }

    static class 1 implements DiffAlgorithmFactory {
        1() {
        }

        public DiffAlgorithmI create() {
            return new MyersDiff();
        }

        public DiffAlgorithmI create(BiPredicate biPredicate) {
            return new MyersDiff(biPredicate);
        }
    }

    public static DiffAlgorithmFactory factory() {
        return new 1();
    }
}
