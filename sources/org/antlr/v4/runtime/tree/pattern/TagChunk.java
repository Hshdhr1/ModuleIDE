package org.antlr.v4.runtime.tree.pattern;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
class TagChunk extends Chunk {
    private final String label;
    private final String tag;

    public TagChunk(String tag) {
        this(null, tag);
    }

    public TagChunk(String label, String tag) {
        if (tag == null || tag.isEmpty()) {
            throw new IllegalArgumentException("tag cannot be null or empty");
        }
        this.label = label;
        this.tag = tag;
    }

    public final String getTag() {
        return this.tag;
    }

    public final String getLabel() {
        return this.label;
    }

    public String toString() {
        return this.label != null ? this.label + ":" + this.tag : this.tag;
    }
}
