package com.github.difflib.text;

import java.io.Serializable;
import java.util.Arrays;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public final class DiffRow implements Serializable {
    private final String newLine;
    private final String oldLine;
    private Tag tag;

    public enum Tag {
        INSERT,
        DELETE,
        CHANGE,
        EQUAL
    }

    public DiffRow(Tag tag, String str, String str2) {
        this.tag = tag;
        this.oldLine = str;
        this.newLine = str2;
    }

    public Tag getTag() {
        return this.tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getOldLine() {
        return this.oldLine;
    }

    public String getNewLine() {
        return this.newLine;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.newLine, this.oldLine, this.tag});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DiffRow diffRow = (DiffRow) obj;
        String str = this.newLine;
        if (str == null) {
            if (diffRow.newLine != null) {
                return false;
            }
        } else if (!str.equals(diffRow.newLine)) {
            return false;
        }
        String str2 = this.oldLine;
        if (str2 == null) {
            if (diffRow.oldLine != null) {
                return false;
            }
        } else if (!str2.equals(diffRow.oldLine)) {
            return false;
        }
        Tag tag = this.tag;
        if (tag == null) {
            if (diffRow.tag != null) {
                return false;
            }
        } else if (!tag.equals(diffRow.tag)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "[" + this.tag + "," + this.oldLine + "," + this.newLine + "]";
    }
}
