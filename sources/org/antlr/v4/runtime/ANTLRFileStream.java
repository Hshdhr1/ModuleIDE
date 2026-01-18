package org.antlr.v4.runtime;

import java.io.IOException;
import org.antlr.v4.runtime.misc.Utils;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ANTLRFileStream extends ANTLRInputStream {
    protected String fileName;

    public ANTLRFileStream(String fileName) throws IOException {
        this(fileName, null);
    }

    public ANTLRFileStream(String fileName, String encoding) throws IOException {
        this.fileName = fileName;
        load(fileName, encoding);
    }

    public void load(String fileName, String encoding) throws IOException {
        this.data = Utils.readFile(fileName, encoding);
        this.n = this.data.length;
    }

    public String getSourceName() {
        return this.fileName;
    }
}
