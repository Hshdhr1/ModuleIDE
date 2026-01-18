package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.JavaVersion;
import com.google.gson.internal.PreJava9DateFormatProvider;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public final class DateTypeAdapter extends TypeAdapter {
    public static final TypeAdapterFactory FACTORY = new 1();
    private final List dateFormats = new ArrayList();

    class 1 implements TypeAdapterFactory {
        1() {
        }

        public TypeAdapter create(Gson gson, TypeToken typeToken) {
            if (typeToken.getRawType() == Date.class) {
                return new DateTypeAdapter();
            }
            return null;
        }
    }

    public DateTypeAdapter() {
        this.dateFormats.add(DateFormat.getDateTimeInstance(2, 2, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.dateFormats.add(DateFormat.getDateTimeInstance(2, 2));
        }
        if (JavaVersion.isJava9OrLater()) {
            this.dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(2, 2));
        }
    }

    public Date read(JsonReader in) throws IOException {
        if (in.peek() != JsonToken.NULL) {
            return deserializeToDate(in.nextString());
        }
        in.nextNull();
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0019, code lost:
    
        r2 = com.google.gson.internal.bind.util.ISO8601Utils.parse(r5, new java.text.ParsePosition(0));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized java.util.Date deserializeToDate(java.lang.String r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.util.List r2 = r4.dateFormats     // Catch: java.lang.Throwable -> L2b
            java.util.Iterator r2 = r2.iterator()     // Catch: java.lang.Throwable -> L2b
        L7:
            boolean r3 = r2.hasNext()     // Catch: java.lang.Throwable -> L2b
            if (r3 == 0) goto L19
            java.lang.Object r0 = r2.next()     // Catch: java.lang.Throwable -> L2b
            java.text.DateFormat r0 = (java.text.DateFormat) r0     // Catch: java.lang.Throwable -> L2b
            java.util.Date r2 = r0.parse(r5)     // Catch: java.lang.Throwable -> L2b java.text.ParseException -> L2e
        L17:
            monitor-exit(r4)
            return r2
        L19:
            java.text.ParsePosition r2 = new java.text.ParsePosition     // Catch: java.text.ParseException -> L24 java.lang.Throwable -> L2b
            r3 = 0
            r2.<init>(r3)     // Catch: java.text.ParseException -> L24 java.lang.Throwable -> L2b
            java.util.Date r2 = com.google.gson.internal.bind.util.ISO8601Utils.parse(r5, r2)     // Catch: java.text.ParseException -> L24 java.lang.Throwable -> L2b
            goto L17
        L24:
            r1 = move-exception
            com.google.gson.JsonSyntaxException r2 = new com.google.gson.JsonSyntaxException     // Catch: java.lang.Throwable -> L2b
            r2.<init>(r5, r1)     // Catch: java.lang.Throwable -> L2b
            throw r2     // Catch: java.lang.Throwable -> L2b
        L2b:
            r2 = move-exception
            monitor-exit(r4)
            throw r2
        L2e:
            r3 = move-exception
            goto L7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.DateTypeAdapter.deserializeToDate(java.lang.String):java.util.Date");
    }

    public synchronized void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            String dateFormatAsString = ((DateFormat) this.dateFormats.get(0)).format(value);
            out.value(dateFormatAsString);
        }
    }
}
