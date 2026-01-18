package com.github.difflib.patch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public final class Chunk implements Serializable {
    private final List changePosition;
    private List lines;
    private final int position;

    public Chunk(int i, List list, List list2) {
        this.position = i;
        this.lines = new ArrayList(list);
        this.changePosition = list2 != null ? new ArrayList(list2) : null;
    }

    public Chunk(int i, List list) {
        this(i, list, (List) null);
    }

    public Chunk(int i, Object[] objArr, List list) {
        this.position = i;
        this.lines = Arrays.asList(objArr);
        this.changePosition = list != null ? new ArrayList(list) : null;
    }

    public Chunk(int i, Object[] objArr) {
        this(i, objArr, (List) null);
    }

    public VerifyChunk verifyChunk(List list) throws PatchFailedException {
        return verifyChunk(list, 0, getPosition());
    }

    public VerifyChunk verifyChunk(List list, int i, int i2) throws PatchFailedException {
        int size = size() - i;
        int size2 = (size() + i2) - 1;
        if (i2 + i > list.size() || size2 - i > list.size()) {
            return VerifyChunk.POSITION_OUT_OF_TARGET;
        }
        while (i < size) {
            if (!list.get(i2 + i).equals(this.lines.get(i))) {
                return VerifyChunk.CONTENT_DOES_NOT_MATCH_TARGET;
            }
            i++;
        }
        return VerifyChunk.OK;
    }

    public int getPosition() {
        return this.position;
    }

    public void setLines(List list) {
        this.lines = list;
    }

    public List getLines() {
        return this.lines;
    }

    public List getChangePosition() {
        return this.changePosition;
    }

    public int size() {
        return this.lines.size();
    }

    public int last() {
        return (getPosition() + size()) - 1;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.lines, Integer.valueOf(this.position), Integer.valueOf(size())});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Chunk chunk = (Chunk) obj;
        List list = this.lines;
        if (list == null) {
            if (chunk.lines != null) {
                return false;
            }
        } else if (!list.equals(chunk.lines)) {
            return false;
        }
        return this.position == chunk.position;
    }

    public String toString() {
        return "[position: " + this.position + ", size: " + size() + ", lines: " + this.lines + "]";
    }
}
