package com.github.difflib.patch;

import com.github.difflib.algorithm.Change;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public final class Patch implements Serializable {
    public static final ConflictOutput CONFLICT_PRODUCES_MERGE_CONFLICT = new Patch$$ExternalSyntheticLambda1();
    public final ConflictOutput CONFLICT_PRODUCES_EXCEPTION;
    private ConflictOutput conflictOutput;
    private final List deltas;

    public Patch() {
        this(10);
    }

    public Patch(int i) {
        Patch$$ExternalSyntheticLambda3 patch$$ExternalSyntheticLambda3 = new Patch$$ExternalSyntheticLambda3();
        this.CONFLICT_PRODUCES_EXCEPTION = patch$$ExternalSyntheticLambda3;
        this.conflictOutput = patch$$ExternalSyntheticLambda3;
        this.deltas = new ArrayList(i);
    }

    public List applyTo(List list) throws PatchFailedException {
        ArrayList arrayList = new ArrayList(list);
        applyToExisting(arrayList);
        return arrayList;
    }

    public void applyToExisting(List list) throws PatchFailedException {
        ListIterator listIterator = getDeltas().listIterator(this.deltas.size());
        while (listIterator.hasPrevious()) {
            AbstractDelta abstractDelta = (AbstractDelta) listIterator.previous();
            VerifyChunk verifyAndApplyTo = abstractDelta.verifyAndApplyTo(list);
            if (verifyAndApplyTo != VerifyChunk.OK) {
                this.conflictOutput.processConflict(verifyAndApplyTo, abstractDelta, list);
            }
        }
    }

    private static class PatchApplyingContext {
        public boolean afterOutRange;
        public boolean beforeOutRange;
        public int currentFuzz;
        public int defaultPosition;
        public int lastPatchEnd;
        public final int maxFuzz;
        public final List result;

        /* synthetic */ PatchApplyingContext(List list, int i, 1 r3) {
            this(list, i);
        }

        private PatchApplyingContext(List list, int i) {
            this.lastPatchEnd = -1;
            this.currentFuzz = 0;
            this.beforeOutRange = false;
            this.afterOutRange = false;
            this.result = list;
            this.maxFuzz = i;
        }
    }

    public List applyFuzzy(List list, int i) throws PatchFailedException {
        PatchApplyingContext patchApplyingContext = new PatchApplyingContext(new ArrayList(list), i, null);
        int i2 = 0;
        for (AbstractDelta abstractDelta : getDeltas()) {
            patchApplyingContext.defaultPosition = abstractDelta.getSource().getPosition() + i2;
            int findPositionFuzzy = findPositionFuzzy(patchApplyingContext, abstractDelta);
            if (findPositionFuzzy >= 0) {
                abstractDelta.applyFuzzyToAt(patchApplyingContext.result, patchApplyingContext.currentFuzz, findPositionFuzzy);
                int position = findPositionFuzzy - abstractDelta.getSource().getPosition();
                patchApplyingContext.lastPatchEnd = abstractDelta.getSource().last() + position;
                i2 = position;
            } else {
                this.conflictOutput.processConflict(VerifyChunk.CONTENT_DOES_NOT_MATCH_TARGET, abstractDelta, patchApplyingContext.result);
            }
        }
        return patchApplyingContext.result;
    }

    private int findPositionFuzzy(PatchApplyingContext patchApplyingContext, AbstractDelta abstractDelta) throws PatchFailedException {
        for (int i = 0; i <= patchApplyingContext.maxFuzz; i++) {
            patchApplyingContext.currentFuzz = i;
            int findPositionWithFuzz = findPositionWithFuzz(patchApplyingContext, abstractDelta, i);
            if (findPositionWithFuzz >= 0) {
                return findPositionWithFuzz;
            }
        }
        return -1;
    }

    private int findPositionWithFuzz(PatchApplyingContext patchApplyingContext, AbstractDelta abstractDelta, int i) throws PatchFailedException {
        if (abstractDelta.getSource().verifyChunk(patchApplyingContext.result, i, patchApplyingContext.defaultPosition) == VerifyChunk.OK) {
            return patchApplyingContext.defaultPosition;
        }
        patchApplyingContext.beforeOutRange = false;
        patchApplyingContext.afterOutRange = false;
        for (int i2 = 0; i2 >= 0; i2++) {
            int findPositionWithFuzzAndMoreDelta = findPositionWithFuzzAndMoreDelta(patchApplyingContext, abstractDelta, i, i2);
            if (findPositionWithFuzzAndMoreDelta >= 0) {
                return findPositionWithFuzzAndMoreDelta;
            }
            if (patchApplyingContext.beforeOutRange && patchApplyingContext.afterOutRange) {
                return -1;
            }
        }
        return -1;
    }

    private int findPositionWithFuzzAndMoreDelta(PatchApplyingContext patchApplyingContext, AbstractDelta abstractDelta, int i, int i2) throws PatchFailedException {
        if (!patchApplyingContext.beforeOutRange && (patchApplyingContext.defaultPosition - i2) + i <= patchApplyingContext.lastPatchEnd) {
            patchApplyingContext.beforeOutRange = true;
        }
        if (!patchApplyingContext.afterOutRange) {
            if (patchApplyingContext.result.size() < ((patchApplyingContext.defaultPosition + i2) + abstractDelta.getSource().size()) - i) {
                patchApplyingContext.afterOutRange = true;
            }
        }
        if (!patchApplyingContext.beforeOutRange && abstractDelta.getSource().verifyChunk(patchApplyingContext.result, i, patchApplyingContext.defaultPosition - i2) == VerifyChunk.OK) {
            return patchApplyingContext.defaultPosition - i2;
        }
        if (patchApplyingContext.afterOutRange || abstractDelta.getSource().verifyChunk(patchApplyingContext.result, i, patchApplyingContext.defaultPosition + i2) != VerifyChunk.OK) {
            return -1;
        }
        return patchApplyingContext.defaultPosition + i2;
    }

    static /* synthetic */ void lambda$new$e23a5734$1(VerifyChunk verifyChunk, AbstractDelta abstractDelta, List list) throws PatchFailedException {
        throw new PatchFailedException("could not apply patch due to " + verifyChunk.toString());
    }

    static /* synthetic */ void lambda$static$5696245$1(VerifyChunk verifyChunk, AbstractDelta abstractDelta, List list) throws PatchFailedException {
        if (list.size() > abstractDelta.getSource().getPosition()) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < abstractDelta.getSource().size(); i++) {
                arrayList.add(list.get(abstractDelta.getSource().getPosition()));
                list.remove(abstractDelta.getSource().getPosition());
            }
            arrayList.add(0, "<<<<<< HEAD");
            arrayList.add("======");
            arrayList.addAll(abstractDelta.getSource().getLines());
            arrayList.add(">>>>>>> PATCH");
            list.addAll(abstractDelta.getSource().getPosition(), arrayList);
            return;
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Patch withConflictOutput(ConflictOutput conflictOutput) {
        this.conflictOutput = conflictOutput;
        return this;
    }

    public List restore(List list) {
        ArrayList arrayList = new ArrayList(list);
        restoreToExisting(arrayList);
        return arrayList;
    }

    public void restoreToExisting(List list) {
        ListIterator listIterator = getDeltas().listIterator(this.deltas.size());
        while (listIterator.hasPrevious()) {
            ((AbstractDelta) listIterator.previous()).restore(list);
        }
    }

    public void addDelta(AbstractDelta abstractDelta) {
        this.deltas.add(abstractDelta);
    }

    static /* synthetic */ Integer lambda$getDeltas$0(AbstractDelta abstractDelta) {
        return Integer.valueOf(abstractDelta.getSource().getPosition());
    }

    public List getDeltas() {
        this.deltas.sort(Patch$$ExternalSyntheticStaticInterfaceCall0.m(new Patch$$ExternalSyntheticLambda4()));
        return this.deltas;
    }

    public String toString() {
        return "Patch{deltas=" + this.deltas + '}';
    }

    public static Patch generate(List list, List list2, List list3) {
        return generate(list, list2, list3, false);
    }

    private static Chunk buildChunk(int i, int i2, List list) {
        return new Chunk(i, (List) new ArrayList(list.subList(i, i2)));
    }

    public static Patch generate(List list, List list2, List list3, boolean z) {
        Patch patch = new Patch(list3.size());
        if (z) {
            List arrayList = new ArrayList(list3);
            Collections.sort(arrayList, Patch$$ExternalSyntheticStaticInterfaceCall0.m(new Patch$$ExternalSyntheticLambda2()));
            list3 = arrayList;
        }
        int i = 0;
        int i2 = 0;
        for (Change change : list3) {
            if (z && i < change.startOriginal) {
                patch.addDelta(new EqualDelta(buildChunk(i, change.startOriginal, list), buildChunk(i2, change.startRevised, list2)));
            }
            Chunk buildChunk = buildChunk(change.startOriginal, change.endOriginal, list);
            Chunk buildChunk2 = buildChunk(change.startRevised, change.endRevised, list2);
            int i3 = 1.$SwitchMap$com$github$difflib$patch$DeltaType[change.deltaType.ordinal()];
            if (i3 == 1) {
                patch.addDelta(new DeleteDelta(buildChunk, buildChunk2));
            } else if (i3 == 2) {
                patch.addDelta(new InsertDelta(buildChunk, buildChunk2));
            } else if (i3 == 3) {
                patch.addDelta(new ChangeDelta(buildChunk, buildChunk2));
            }
            i = change.endOriginal;
            i2 = change.endRevised;
        }
        if (z && i < list.size()) {
            patch.addDelta(new EqualDelta(buildChunk(i, list.size(), list), buildChunk(i2, list2.size(), list2)));
        }
        return patch;
    }

    static /* synthetic */ Integer lambda$generate$1(Change change) {
        return Integer.valueOf(change.startOriginal);
    }

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$com$github$difflib$patch$DeltaType;

        static {
            int[] iArr = new int[DeltaType.values().length];
            $SwitchMap$com$github$difflib$patch$DeltaType = iArr;
            try {
                iArr[DeltaType.DELETE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$github$difflib$patch$DeltaType[DeltaType.INSERT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$github$difflib$patch$DeltaType[DeltaType.CHANGE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }
}
