package com.google.gson;

import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public abstract class TypeAdapter {
    public abstract Object read(JsonReader jsonReader) throws IOException;

    public abstract void write(JsonWriter jsonWriter, Object obj) throws IOException;

    public final void toJson(Writer out, Object obj) throws IOException {
        JsonWriter writer = new JsonWriter(out);
        write(writer, obj);
    }

    class 1 extends TypeAdapter {
        1() {
        }

        public void write(JsonWriter out, Object obj) throws IOException {
            if (obj == null) {
                out.nullValue();
            } else {
                TypeAdapter.this.write(out, obj);
            }
        }

        public Object read(JsonReader reader) throws IOException {
            if (reader.peek() != JsonToken.NULL) {
                return TypeAdapter.this.read(reader);
            }
            reader.nextNull();
            return null;
        }
    }

    public final TypeAdapter nullSafe() {
        return new 1();
    }

    public final String toJson(Object obj) {
        StringWriter stringWriter = new StringWriter();
        try {
            toJson(stringWriter, obj);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public final JsonElement toJsonTree(Object obj) {
        try {
            JsonTreeWriter jsonWriter = new JsonTreeWriter();
            write(jsonWriter, obj);
            return jsonWriter.get();
        } catch (IOException e) {
            throw new JsonIOException((Throwable) e);
        }
    }

    public final Object fromJson(Reader in) throws IOException {
        JsonReader reader = new JsonReader(in);
        return read(reader);
    }

    public final Object fromJson(String json) throws IOException {
        return fromJson((Reader) new StringReader(json));
    }

    public final Object fromJsonTree(JsonElement jsonTree) {
        try {
            JsonReader jsonReader = new JsonTreeReader(jsonTree);
            return read(jsonReader);
        } catch (IOException e) {
            throw new JsonIOException((Throwable) e);
        }
    }
}
