package com.github.difflib.patch;

import java.io.Serializable;
import java.util.List;

@FunctionalInterface
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public interface ConflictOutput extends Serializable {
    void processConflict(VerifyChunk verifyChunk, AbstractDelta abstractDelta, List list) throws PatchFailedException;
}
