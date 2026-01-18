package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public final class ArrayTypeAdapter extends TypeAdapter {
    public static final TypeAdapterFactory FACTORY = new 1();
    private final Class componentType;
    private final TypeAdapter componentTypeAdapter;

    class 1 implements TypeAdapterFactory {
        1() {
        }

        public TypeAdapter create(Gson gson, TypeToken typeToken) {
            Class type = typeToken.getType();
            if (!(type instanceof GenericArrayType) && (!(type instanceof Class) || !type.isArray())) {
                return null;
            }
            Type componentType = $Gson$Types.getArrayComponentType(type);
            TypeAdapter<?> componentTypeAdapter = gson.getAdapter(TypeToken.get(componentType));
            return new ArrayTypeAdapter(gson, componentTypeAdapter, $Gson$Types.getRawType(componentType));
        }
    }

    public ArrayTypeAdapter(Gson context, TypeAdapter typeAdapter, Class cls) {
        this.componentTypeAdapter = new TypeAdapterRuntimeTypeWrapper(context, typeAdapter, cls);
        this.componentType = cls;
    }

    public Object read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        ArrayList arrayList = new ArrayList();
        in.beginArray();
        while (in.hasNext()) {
            arrayList.add(this.componentTypeAdapter.read(in));
        }
        in.endArray();
        int size = arrayList.size();
        Object array = Array.newInstance(this.componentType, size);
        for (int i = 0; i < size; i++) {
            Array.set(array, i, arrayList.get(i));
        }
        return array;
    }

    public void write(JsonWriter out, Object array) throws IOException {
        if (array == null) {
            out.nullValue();
            return;
        }
        out.beginArray();
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            this.componentTypeAdapter.write(out, Array.get(array, i));
        }
        out.endArray();
    }
}
