package com.github.difflib.patch;

import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public final class InsertDelta extends AbstractDelta {
    public InsertDelta(Chunk chunk, Chunk chunk2) {
        super(DeltaType.INSERT, chunk, chunk2);
    }

    protected void applyTo(List list) throws PatchFailedException {
        int position = getSource().getPosition();
        List lines = getTarget().getLines();
        for (int i = 0; i < lines.size(); i++) {
            list.add(position + i, lines.get(i));
        }
    }

    protected void restore(List list) {
        int position = getTarget().getPosition();
        int size = getTarget().size();
        for (int i = 0; i < size; i++) {
            list.remove(position);
        }
    }

    public String toString() {
        return "[InsertDelta, position: " + getSource().getPosition() + ", lines: " + getTarget().getLines() + "]";
    }

    public AbstractDelta withChunks(Chunk chunk, Chunk chunk2) {
        return new InsertDelta(chunk, chunk2);
    }
}
