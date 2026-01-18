package com.github.difflib.patch;

import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public final class DeleteDelta extends AbstractDelta {
    public DeleteDelta(Chunk chunk, Chunk chunk2) {
        super(DeltaType.DELETE, chunk, chunk2);
    }

    protected void applyTo(List list) throws PatchFailedException {
        int position = getSource().getPosition();
        int size = getSource().size();
        for (int i = 0; i < size; i++) {
            list.remove(position);
        }
    }

    protected void restore(List list) {
        int position = getTarget().getPosition();
        List lines = getSource().getLines();
        for (int i = 0; i < lines.size(); i++) {
            list.add(position + i, lines.get(i));
        }
    }

    public String toString() {
        return "[DeleteDelta, position: " + getSource().getPosition() + ", lines: " + getSource().getLines() + "]";
    }

    public AbstractDelta withChunks(Chunk chunk, Chunk chunk2) {
        return new DeleteDelta(chunk, chunk2);
    }
}
