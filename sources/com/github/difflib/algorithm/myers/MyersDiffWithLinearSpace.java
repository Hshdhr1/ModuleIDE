package com.github.difflib.algorithm.myers;

import com.github.difflib.algorithm.Change;
import com.github.difflib.algorithm.DiffAlgorithmFactory;
import com.github.difflib.algorithm.DiffAlgorithmI;
import com.github.difflib.algorithm.DiffAlgorithmListener;
import com.github.difflib.patch.DeltaType;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public class MyersDiffWithLinearSpace implements DiffAlgorithmI {
    private final BiPredicate equalizer;

    public /* synthetic */ List computeDiff(Object[] objArr, Object[] objArr2, DiffAlgorithmListener diffAlgorithmListener) {
        return DiffAlgorithmI.-CC.$default$computeDiff(this, objArr, objArr2, diffAlgorithmListener);
    }

    public MyersDiffWithLinearSpace() {
        this.equalizer = new MyersDiffWithLinearSpace$$ExternalSyntheticLambda1();
    }

    public MyersDiffWithLinearSpace(BiPredicate biPredicate) {
        MyersDiffWithLinearSpace$$ExternalSyntheticBackport0.m(biPredicate, "equalizer must not be null");
        this.equalizer = biPredicate;
    }

    public List computeDiff(List list, List list2, DiffAlgorithmListener diffAlgorithmListener) {
        MyersDiffWithLinearSpace$$ExternalSyntheticBackport0.m(list, "source list must not be null");
        MyersDiffWithLinearSpace$$ExternalSyntheticBackport0.m(list2, "target list must not be null");
        if (diffAlgorithmListener != null) {
            diffAlgorithmListener.diffStart();
        }
        DiffData diffData = new DiffData(list, list2);
        buildScript(diffData, 0, list.size(), 0, list2.size(), new MyersDiffWithLinearSpace$$ExternalSyntheticLambda2(diffAlgorithmListener, list.size() + list2.size()));
        if (diffAlgorithmListener != null) {
            diffAlgorithmListener.diffEnd();
        }
        return diffData.script;
    }

    static /* synthetic */ void lambda$computeDiff$0(DiffAlgorithmListener diffAlgorithmListener, int i, Integer num) {
        if (diffAlgorithmListener != null) {
            diffAlgorithmListener.diffStep(num.intValue(), i);
        }
    }

    private void buildScript(DiffData diffData, int i, int i2, int i3, int i4, Consumer consumer) {
        if (consumer != null) {
            consumer.accept(Integer.valueOf(((i2 - i) / 2) + ((i4 - i3) / 2)));
        }
        Snake middleSnake = getMiddleSnake(diffData, i, i2, i3, i4);
        if (middleSnake != null && ((middleSnake.start != i2 || middleSnake.diag != i2 - i4) && (middleSnake.end != i || middleSnake.diag != i - i3))) {
            buildScript(diffData, i, middleSnake.start, i3, middleSnake.start - middleSnake.diag, consumer);
            buildScript(diffData, middleSnake.end, i2, middleSnake.end - middleSnake.diag, i4, consumer);
            return;
        }
        int i5 = i;
        int i6 = i3;
        while (true) {
            if (i5 >= i2 && i6 >= i4) {
                return;
            }
            if (i5 < i2 && i6 < i4 && this.equalizer.test(diffData.source.get(i5), diffData.target.get(i6))) {
                i5++;
            } else if (i2 - i > i4 - i3) {
                if (diffData.script.isEmpty() || ((Change) diffData.script.get(diffData.script.size() - 1)).endOriginal != i5 || ((Change) diffData.script.get(diffData.script.size() - 1)).deltaType != DeltaType.DELETE) {
                    diffData.script.add(new Change(DeltaType.DELETE, i5, i5 + 1, i6, i6));
                } else {
                    diffData.script.set(diffData.script.size() - 1, ((Change) diffData.script.get(diffData.script.size() - 1)).withEndOriginal(i5 + 1));
                }
                i5++;
            } else if (diffData.script.isEmpty() || ((Change) diffData.script.get(diffData.script.size() - 1)).endRevised != i6 || ((Change) diffData.script.get(diffData.script.size() - 1)).deltaType != DeltaType.INSERT) {
                diffData.script.add(new Change(DeltaType.INSERT, i5, i5, i6, i6 + 1));
            } else {
                diffData.script.set(diffData.script.size() - 1, ((Change) diffData.script.get(diffData.script.size() - 1)).withEndRevised(i6 + 1));
            }
            i6++;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:79:0x014a, code lost:
    
        r6 = r6 + 1;
        r0 = r16;
        r1 = r17;
        r4 = r19;
        r5 = r21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.github.difflib.algorithm.myers.MyersDiffWithLinearSpace.Snake getMiddleSnake(com.github.difflib.algorithm.myers.MyersDiffWithLinearSpace.DiffData r17, int r18, int r19, int r20, int r21) {
        /*
            Method dump skipped, instructions count: 352
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.difflib.algorithm.myers.MyersDiffWithLinearSpace.getMiddleSnake(com.github.difflib.algorithm.myers.MyersDiffWithLinearSpace$DiffData, int, int, int, int):com.github.difflib.algorithm.myers.MyersDiffWithLinearSpace$Snake");
    }

    private Snake buildSnake(DiffData diffData, int i, int i2, int i3, int i4) {
        int i5 = i;
        while (true) {
            int i6 = i5 - i2;
            if (i6 >= i4 || i5 >= i3 || !this.equalizer.test(diffData.source.get(i5), diffData.target.get(i6))) {
                break;
            }
            i5++;
        }
        return new Snake(i, i5, i2);
    }

    private class DiffData {
        final List script;
        final int size;
        final List source;
        final List target;
        final int[] vDown;
        final int[] vUp;

        public DiffData(List list, List list2) {
            this.source = list;
            this.target = list2;
            int size = list.size() + list2.size() + 2;
            this.size = size;
            this.vDown = new int[size];
            this.vUp = new int[size];
            this.script = new ArrayList();
        }
    }

    private class Snake {
        final int diag;
        final int end;
        final int start;

        public Snake(int i, int i2, int i3) {
            this.start = i;
            this.end = i2;
            this.diag = i3;
        }
    }

    static class 1 implements DiffAlgorithmFactory {
        1() {
        }

        public DiffAlgorithmI create() {
            return new MyersDiffWithLinearSpace();
        }

        public DiffAlgorithmI create(BiPredicate biPredicate) {
            return new MyersDiffWithLinearSpace(biPredicate);
        }
    }

    public static DiffAlgorithmFactory factory() {
        return new 1();
    }
}
