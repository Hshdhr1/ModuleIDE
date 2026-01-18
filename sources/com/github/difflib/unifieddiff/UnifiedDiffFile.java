package com.github.difflib.unifieddiff;

import com.github.difflib.patch.Patch;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public final class UnifiedDiffFile {
    private String binaryAdded;
    private String binaryDeleted;
    private String binaryEdited;
    private String copyFrom;
    private String copyTo;
    private String deletedFileMode;
    private String diffCommand;
    private String fromFile;
    private String fromTimestamp;
    private String index;
    private String newFileMode;
    private String newMode;
    private String oldMode;
    private String renameFrom;
    private String renameTo;
    private Integer similarityIndex;
    private String toFile;
    private String toTimestamp;
    private Patch patch = new Patch();
    private boolean noNewLineAtTheEndOfTheFile = false;

    public String getDiffCommand() {
        return this.diffCommand;
    }

    public void setDiffCommand(String str) {
        this.diffCommand = str;
    }

    public String getFromFile() {
        return this.fromFile;
    }

    public void setFromFile(String str) {
        this.fromFile = str;
    }

    public String getToFile() {
        return this.toFile;
    }

    public void setToFile(String str) {
        this.toFile = str;
    }

    public void setIndex(String str) {
        this.index = str;
    }

    public String getIndex() {
        return this.index;
    }

    public Patch getPatch() {
        return this.patch;
    }

    public String getFromTimestamp() {
        return this.fromTimestamp;
    }

    public void setFromTimestamp(String str) {
        this.fromTimestamp = str;
    }

    public String getToTimestamp() {
        return this.toTimestamp;
    }

    public void setToTimestamp(String str) {
        this.toTimestamp = str;
    }

    public Integer getSimilarityIndex() {
        return this.similarityIndex;
    }

    public void setSimilarityIndex(Integer num) {
        this.similarityIndex = num;
    }

    public String getRenameFrom() {
        return this.renameFrom;
    }

    public void setRenameFrom(String str) {
        this.renameFrom = str;
    }

    public String getRenameTo() {
        return this.renameTo;
    }

    public void setRenameTo(String str) {
        this.renameTo = str;
    }

    public String getCopyFrom() {
        return this.copyFrom;
    }

    public void setCopyFrom(String str) {
        this.copyFrom = str;
    }

    public String getCopyTo() {
        return this.copyTo;
    }

    public void setCopyTo(String str) {
        this.copyTo = str;
    }

    public static UnifiedDiffFile from(String str, String str2, Patch patch) {
        UnifiedDiffFile unifiedDiffFile = new UnifiedDiffFile();
        unifiedDiffFile.setFromFile(str);
        unifiedDiffFile.setToFile(str2);
        unifiedDiffFile.patch = patch;
        return unifiedDiffFile;
    }

    public void setNewFileMode(String str) {
        this.newFileMode = str;
    }

    public String getNewFileMode() {
        return this.newFileMode;
    }

    public String getDeletedFileMode() {
        return this.deletedFileMode;
    }

    public void setDeletedFileMode(String str) {
        this.deletedFileMode = str;
    }

    public String getOldMode() {
        return this.oldMode;
    }

    public void setOldMode(String str) {
        this.oldMode = str;
    }

    public String getNewMode() {
        return this.newMode;
    }

    public void setNewMode(String str) {
        this.newMode = str;
    }

    public String getBinaryAdded() {
        return this.binaryAdded;
    }

    public void setBinaryAdded(String str) {
        this.binaryAdded = str;
    }

    public String getBinaryDeleted() {
        return this.binaryDeleted;
    }

    public void setBinaryDeleted(String str) {
        this.binaryDeleted = str;
    }

    public String getBinaryEdited() {
        return this.binaryEdited;
    }

    public void setBinaryEdited(String str) {
        this.binaryEdited = str;
    }

    public boolean isNoNewLineAtTheEndOfTheFile() {
        return this.noNewLineAtTheEndOfTheFile;
    }

    public void setNoNewLineAtTheEndOfTheFile(boolean z) {
        this.noNewLineAtTheEndOfTheFile = z;
    }
}
