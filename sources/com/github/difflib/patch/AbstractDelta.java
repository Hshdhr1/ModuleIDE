package com.github.difflib.patch;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public abstract class AbstractDelta implements Serializable {
    private final Chunk source;
    private final Chunk target;
    private final DeltaType type;

    protected abstract void applyTo(List list) throws PatchFailedException;

    protected abstract void restore(List list);

    public abstract AbstractDelta withChunks(Chunk chunk, Chunk chunk2);

    public AbstractDelta(DeltaType deltaType, Chunk chunk, Chunk chunk2) {
        chunk.getClass();
        chunk2.getClass();
        deltaType.getClass();
        this.type = deltaType;
        this.source = chunk;
        this.target = chunk2;
    }

    public Chunk getSource() {
        return this.source;
    }

    public Chunk getTarget() {
        return this.target;
    }

    public DeltaType getType() {
        return this.type;
    }

    protected VerifyChunk verifyChunkToFitTarget(List list) throws PatchFailedException {
        return getSource().verifyChunk(list);
    }

    protected VerifyChunk verifyAndApplyTo(List list) throws PatchFailedException {
        VerifyChunk verifyChunkToFitTarget = verifyChunkToFitTarget(list);
        if (verifyChunkToFitTarget == VerifyChunk.OK) {
            applyTo(list);
        }
        return verifyChunkToFitTarget;
    }

    protected void applyFuzzyToAt(List list, int i, int i2) throws PatchFailedException {
        throw new UnsupportedOperationException(getClass().getSimpleName() + " does not supports applying patch fuzzy");
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.source, this.target, this.type});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AbstractDelta abstractDelta = (AbstractDelta) obj;
        return AbstractDelta$$ExternalSyntheticBackport0.m(this.source, abstractDelta.source) && AbstractDelta$$ExternalSyntheticBackport0.m(this.target, abstractDelta.target) && this.type == abstractDelta.type;
    }
}
