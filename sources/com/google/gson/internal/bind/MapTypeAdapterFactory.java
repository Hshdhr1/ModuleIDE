package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public final class MapTypeAdapterFactory implements TypeAdapterFactory {
    final boolean complexMapKeySerialization;
    private final ConstructorConstructor constructorConstructor;

    public MapTypeAdapterFactory(ConstructorConstructor constructorConstructor, boolean complexMapKeySerialization) {
        this.constructorConstructor = constructorConstructor;
        this.complexMapKeySerialization = complexMapKeySerialization;
    }

    public TypeAdapter create(Gson gson, TypeToken typeToken) {
        Type type = typeToken.getType();
        if (!Map.class.isAssignableFrom(typeToken.getRawType())) {
            return null;
        }
        Class<?> rawTypeOfSrc = $Gson$Types.getRawType(type);
        Type[] keyAndValueTypes = $Gson$Types.getMapKeyAndValueTypes(type, rawTypeOfSrc);
        TypeAdapter<?> keyAdapter = getKeyAdapter(gson, keyAndValueTypes[0]);
        TypeAdapter<?> valueAdapter = gson.getAdapter(TypeToken.get(keyAndValueTypes[1]));
        return new Adapter(gson, keyAndValueTypes[0], keyAdapter, keyAndValueTypes[1], valueAdapter, this.constructorConstructor.get(typeToken));
    }

    private TypeAdapter getKeyAdapter(Gson context, Type keyType) {
        if (keyType == Boolean.TYPE || keyType == Boolean.class) {
            return TypeAdapters.BOOLEAN_AS_STRING;
        }
        return context.getAdapter(TypeToken.get(keyType));
    }

    private final class Adapter extends TypeAdapter {
        private final ObjectConstructor constructor;
        private final TypeAdapter keyTypeAdapter;
        private final TypeAdapter valueTypeAdapter;

        public Adapter(Gson context, Type keyType, TypeAdapter typeAdapter, Type valueType, TypeAdapter typeAdapter2, ObjectConstructor objectConstructor) {
            this.keyTypeAdapter = new TypeAdapterRuntimeTypeWrapper(context, typeAdapter, keyType);
            this.valueTypeAdapter = new TypeAdapterRuntimeTypeWrapper(context, typeAdapter2, valueType);
            this.constructor = objectConstructor;
        }

        public Map read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            if (peek == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            Map map = (Map) this.constructor.construct();
            if (peek == JsonToken.BEGIN_ARRAY) {
                in.beginArray();
                while (in.hasNext()) {
                    in.beginArray();
                    Object read = this.keyTypeAdapter.read(in);
                    if (map.put(read, this.valueTypeAdapter.read(in)) != null) {
                        throw new JsonSyntaxException("duplicate key: " + read);
                    }
                    in.endArray();
                }
                in.endArray();
                return map;
            }
            in.beginObject();
            while (in.hasNext()) {
                JsonReaderInternalAccess.INSTANCE.promoteNameToValue(in);
                Object read2 = this.keyTypeAdapter.read(in);
                if (map.put(read2, this.valueTypeAdapter.read(in)) != null) {
                    throw new JsonSyntaxException("duplicate key: " + read2);
                }
            }
            in.endObject();
            return map;
        }

        public void write(JsonWriter out, Map map) throws IOException {
            if (map == null) {
                out.nullValue();
                return;
            }
            if (!MapTypeAdapterFactory.this.complexMapKeySerialization) {
                out.beginObject();
                for (Map.Entry entry : map.entrySet()) {
                    out.name(String.valueOf(entry.getKey()));
                    this.valueTypeAdapter.write(out, entry.getValue());
                }
                out.endObject();
                return;
            }
            boolean hasComplexKeys = false;
            ArrayList arrayList = new ArrayList(map.size());
            ArrayList arrayList2 = new ArrayList(map.size());
            for (Map.Entry entry2 : map.entrySet()) {
                JsonElement keyElement = this.keyTypeAdapter.toJsonTree(entry2.getKey());
                arrayList.add(keyElement);
                arrayList2.add(entry2.getValue());
                hasComplexKeys |= keyElement.isJsonArray() || keyElement.isJsonObject();
            }
            if (hasComplexKeys) {
                out.beginArray();
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    out.beginArray();
                    Streams.write((JsonElement) arrayList.get(i), out);
                    this.valueTypeAdapter.write(out, arrayList2.get(i));
                    out.endArray();
                }
                out.endArray();
                return;
            }
            out.beginObject();
            int size2 = arrayList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                out.name(keyToString((JsonElement) arrayList.get(i2)));
                this.valueTypeAdapter.write(out, arrayList2.get(i2));
            }
            out.endObject();
        }

        private String keyToString(JsonElement keyElement) {
            if (keyElement.isJsonPrimitive()) {
                JsonPrimitive primitive = keyElement.getAsJsonPrimitive();
                if (primitive.isNumber()) {
                    return String.valueOf(primitive.getAsNumber());
                }
                if (primitive.isBoolean()) {
                    return Boolean.toString(primitive.getAsBoolean());
                }
                if (primitive.isString()) {
                    return primitive.getAsString();
                }
                throw new AssertionError();
            }
            if (keyElement.isJsonNull()) {
                return "null";
            }
            throw new AssertionError();
        }
    }
}
