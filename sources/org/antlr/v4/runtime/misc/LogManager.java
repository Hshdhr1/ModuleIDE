package org.antlr.v4.runtime.misc;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class LogManager {
    protected List records;

    protected static class Record {
        String component;
        String msg;
        long timestamp = System.currentTimeMillis();
        StackTraceElement location = new Throwable().getStackTrace()[0];

        public String toString() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(this.timestamp)) + " " + this.component + " " + this.location.getFileName() + ":" + this.location.getLineNumber() + " " + this.msg;
        }
    }

    public void log(String component, String msg) {
        Record r = new Record();
        r.component = component;
        r.msg = msg;
        if (this.records == null) {
            this.records = new ArrayList();
        }
        this.records.add(r);
    }

    public void log(String msg) {
        log(null, msg);
    }

    public void save(String filename) throws IOException {
        FileWriter fw = new FileWriter(filename);
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            bw.write(toString());
        } finally {
            bw.close();
        }
    }

    public String save() throws IOException {
        String defaultFilename = "./antlr-" + new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss").format(new Date()) + ".log";
        save(defaultFilename);
        return defaultFilename;
    }

    public String toString() {
        if (this.records == null) {
            return "";
        }
        String nl = System.getProperty("line.separator");
        StringBuilder buf = new StringBuilder();
        for (Record r : this.records) {
            buf.append(r);
            buf.append(nl);
        }
        return buf.toString();
    }

    public static void main(String[] args) throws IOException {
        LogManager mgr = new LogManager();
        mgr.log("atn", "test msg");
        mgr.log("dfa", "test msg 2");
        System.out.println(mgr);
        mgr.save();
    }
}
