package com.github.difflib.patch;

import java.util.Iterator;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public final class ChangeDelta extends AbstractDelta {
    public ChangeDelta(Chunk chunk, Chunk chunk2) {
        super(DeltaType.CHANGE, chunk, chunk2);
        ChangeDelta$$ExternalSyntheticBackport0.m(chunk, "source must not be null");
        ChangeDelta$$ExternalSyntheticBackport0.m(chunk2, "target must not be null");
    }

    protected void applyTo(List list) throws PatchFailedException {
        int position = getSource().getPosition();
        int size = getSource().size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            list.remove(position);
        }
        Iterator it = getTarget().getLines().iterator();
        while (it.hasNext()) {
            list.add(position + i, it.next());
            i++;
        }
    }

    protected void restore(List list) {
        int position = getTarget().getPosition();
        int size = getTarget().size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            list.remove(position);
        }
        Iterator it = getSource().getLines().iterator();
        while (it.hasNext()) {
            list.add(position + i, it.next());
            i++;
        }
    }

    protected void applyFuzzyToAt(List list, int i, int i2) throws PatchFailedException {
        int size = getSource().size();
        for (int i3 = i; i3 < size - i; i3++) {
            list.remove(i2 + i);
        }
        Iterator it = getTarget().getLines().subList(i, getTarget().size() - i).iterator();
        while (it.hasNext()) {
            list.add(i2 + i, it.next());
            i++;
        }
    }

    public String toString() {
        return "[ChangeDelta, position: " + getSource().getPosition() + ", lines: " + getSource().getLines() + " to " + getTarget().getLines() + "]";
    }

    public AbstractDelta withChunks(Chunk chunk, Chunk chunk2) {
        return new ChangeDelta(chunk, chunk2);
    }
}
