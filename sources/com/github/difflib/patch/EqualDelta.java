package com.github.difflib.patch;

import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public class EqualDelta extends AbstractDelta {
    protected void applyFuzzyToAt(List list, int i, int i2) {
    }

    protected void applyTo(List list) throws PatchFailedException {
    }

    protected void restore(List list) {
    }

    public EqualDelta(Chunk chunk, Chunk chunk2) {
        super(DeltaType.EQUAL, chunk, chunk2);
    }

    public String toString() {
        return "[EqualDelta, position: " + getSource().getPosition() + ", lines: " + getSource().getLines() + "]";
    }

    public AbstractDelta withChunks(Chunk chunk, Chunk chunk2) {
        return new EqualDelta(chunk, chunk2);
    }
}
