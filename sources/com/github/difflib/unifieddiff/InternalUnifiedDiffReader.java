package com.github.difflib.unifieddiff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/* compiled from: UnifiedDiffReader.java */
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
class InternalUnifiedDiffReader extends BufferedReader {
    private String lastLine;

    public InternalUnifiedDiffReader(Reader reader) {
        super(reader);
    }

    public String readLine() throws IOException {
        this.lastLine = super.readLine();
        return lastLine();
    }

    String lastLine() {
        return this.lastLine;
    }
}
