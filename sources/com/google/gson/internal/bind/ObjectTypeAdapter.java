package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public final class ObjectTypeAdapter extends TypeAdapter {
    public static final TypeAdapterFactory FACTORY = new 1();
    private final Gson gson;

    class 1 implements TypeAdapterFactory {
        1() {
        }

        public TypeAdapter create(Gson gson, TypeToken typeToken) {
            if (typeToken.getRawType() == Object.class) {
                return new ObjectTypeAdapter(gson);
            }
            return null;
        }
    }

    ObjectTypeAdapter(Gson gson) {
        this.gson = gson;
    }

    public Object read(JsonReader in) throws IOException {
        JsonToken token = in.peek();
        switch (token) {
            case BEGIN_ARRAY:
                ArrayList arrayList = new ArrayList();
                in.beginArray();
                while (in.hasNext()) {
                    arrayList.add(read(in));
                }
                in.endArray();
                return arrayList;
            case BEGIN_OBJECT:
                LinkedTreeMap linkedTreeMap = new LinkedTreeMap();
                in.beginObject();
                while (in.hasNext()) {
                    linkedTreeMap.put(in.nextName(), read(in));
                }
                in.endObject();
                return linkedTreeMap;
            case STRING:
                return in.nextString();
            case NUMBER:
                return Double.valueOf(in.nextDouble());
            case BOOLEAN:
                return Boolean.valueOf(in.nextBoolean());
            case NULL:
                in.nextNull();
                return null;
            default:
                throw new IllegalStateException();
        }
    }

    public void write(JsonWriter out, Object value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        TypeAdapter<Object> typeAdapter = this.gson.getAdapter(value.getClass());
        if (typeAdapter instanceof ObjectTypeAdapter) {
            out.beginObject();
            out.endObject();
        } else {
            typeAdapter.write(out, value);
        }
    }
}
