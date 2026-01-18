package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public final class CollectionTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;

    public CollectionTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    public TypeAdapter create(Gson gson, TypeToken typeToken) {
        Type type = typeToken.getType();
        Class rawType = typeToken.getRawType();
        if (!Collection.class.isAssignableFrom(rawType)) {
            return null;
        }
        Type elementType = $Gson$Types.getCollectionElementType(type, rawType);
        TypeAdapter<?> elementTypeAdapter = gson.getAdapter(TypeToken.get(elementType));
        return new Adapter(gson, elementType, elementTypeAdapter, this.constructorConstructor.get(typeToken));
    }

    private static final class Adapter extends TypeAdapter {
        private final ObjectConstructor constructor;
        private final TypeAdapter elementTypeAdapter;

        public Adapter(Gson context, Type elementType, TypeAdapter typeAdapter, ObjectConstructor objectConstructor) {
            this.elementTypeAdapter = new TypeAdapterRuntimeTypeWrapper(context, typeAdapter, elementType);
            this.constructor = objectConstructor;
        }

        public Collection read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            Collection collection = (Collection) this.constructor.construct();
            in.beginArray();
            while (in.hasNext()) {
                collection.add(this.elementTypeAdapter.read(in));
            }
            in.endArray();
            return collection;
        }

        public void write(JsonWriter out, Collection collection) throws IOException {
            if (collection == null) {
                out.nullValue();
                return;
            }
            out.beginArray();
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                this.elementTypeAdapter.write(out, it.next());
            }
            out.endArray();
        }
    }
}
