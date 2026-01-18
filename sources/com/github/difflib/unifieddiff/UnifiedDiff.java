package com.github.difflib.unifieddiff;

import com.github.difflib.patch.PatchFailedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public final class UnifiedDiff {
    private final List files = new ArrayList();
    private String header;
    private String tail;

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String str) {
        this.header = str;
    }

    void addFile(UnifiedDiffFile unifiedDiffFile) {
        this.files.add(unifiedDiffFile);
    }

    public List getFiles() {
        return Collections.unmodifiableList(this.files);
    }

    void setTailTxt(String str) {
        this.tail = str;
    }

    public String getTail() {
        return this.tail;
    }

    public List applyPatchTo(Predicate predicate, List list) throws PatchFailedException {
        UnifiedDiffFile unifiedDiffFile = (UnifiedDiffFile) this.files.stream().filter(new UnifiedDiff$$ExternalSyntheticLambda0(predicate)).findFirst().orElse((Object) null);
        return unifiedDiffFile != null ? unifiedDiffFile.getPatch().applyTo(list) : list;
    }

    static /* synthetic */ boolean lambda$applyPatchTo$0(Predicate predicate, UnifiedDiffFile unifiedDiffFile) {
        return predicate.test(unifiedDiffFile.getFromFile());
    }

    public static UnifiedDiff from(String str, String str2, UnifiedDiffFile... unifiedDiffFileArr) {
        UnifiedDiff unifiedDiff = new UnifiedDiff();
        unifiedDiff.setHeader(str);
        unifiedDiff.setTailTxt(str2);
        for (UnifiedDiffFile unifiedDiffFile : unifiedDiffFileArr) {
            unifiedDiff.addFile(unifiedDiffFile);
        }
        return unifiedDiff;
    }
}
