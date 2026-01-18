package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public final class TreeTypeAdapter extends TypeAdapter {
    private final GsonContextImpl context = new GsonContextImpl(this, null);
    private TypeAdapter delegate;
    private final JsonDeserializer deserializer;
    final Gson gson;
    private final JsonSerializer serializer;
    private final TypeAdapterFactory skipPast;
    private final TypeToken typeToken;

    public TreeTypeAdapter(JsonSerializer jsonSerializer, JsonDeserializer jsonDeserializer, Gson gson, TypeToken typeToken, TypeAdapterFactory skipPast) {
        this.serializer = jsonSerializer;
        this.deserializer = jsonDeserializer;
        this.gson = gson;
        this.typeToken = typeToken;
        this.skipPast = skipPast;
    }

    public Object read(JsonReader in) throws IOException {
        if (this.deserializer == null) {
            return delegate().read(in);
        }
        JsonElement value = Streams.parse(in);
        if (value.isJsonNull()) {
            return null;
        }
        return this.deserializer.deserialize(value, this.typeToken.getType(), this.context);
    }

    public void write(JsonWriter out, Object obj) throws IOException {
        if (this.serializer == null) {
            delegate().write(out, obj);
        } else if (obj == null) {
            out.nullValue();
        } else {
            JsonElement tree = this.serializer.serialize(obj, this.typeToken.getType(), this.context);
            Streams.write(tree, out);
        }
    }

    private TypeAdapter delegate() {
        TypeAdapter typeAdapter = this.delegate;
        if (typeAdapter != null) {
            return typeAdapter;
        }
        TypeAdapter delegateAdapter = this.gson.getDelegateAdapter(this.skipPast, this.typeToken);
        this.delegate = delegateAdapter;
        return delegateAdapter;
    }

    public static TypeAdapterFactory newFactory(TypeToken typeToken, Object typeAdapter) {
        return new SingleTypeFactory(typeAdapter, typeToken, false, null);
    }

    public static TypeAdapterFactory newFactoryWithMatchRawType(TypeToken typeToken, Object typeAdapter) {
        boolean matchRawType = typeToken.getType() == typeToken.getRawType();
        return new SingleTypeFactory(typeAdapter, typeToken, matchRawType, null);
    }

    public static TypeAdapterFactory newTypeHierarchyFactory(Class cls, Object typeAdapter) {
        return new SingleTypeFactory(typeAdapter, null, false, cls);
    }

    private static final class SingleTypeFactory implements TypeAdapterFactory {
        private final JsonDeserializer deserializer;
        private final TypeToken exactType;
        private final Class hierarchyType;
        private final boolean matchRawType;
        private final JsonSerializer serializer;

        SingleTypeFactory(Object typeAdapter, TypeToken typeToken, boolean matchRawType, Class cls) {
            this.serializer = typeAdapter instanceof JsonSerializer ? (JsonSerializer) typeAdapter : null;
            this.deserializer = typeAdapter instanceof JsonDeserializer ? (JsonDeserializer) typeAdapter : null;
            $Gson$Preconditions.checkArgument((this.serializer == null && this.deserializer == null) ? false : true);
            this.exactType = typeToken;
            this.matchRawType = matchRawType;
            this.hierarchyType = cls;
        }

        public TypeAdapter create(Gson gson, TypeToken typeToken) {
            boolean matches;
            if (this.exactType != null) {
                matches = this.exactType.equals(typeToken) || (this.matchRawType && this.exactType.getType() == typeToken.getRawType());
            } else {
                matches = this.hierarchyType.isAssignableFrom(typeToken.getRawType());
            }
            if (matches) {
                return new TreeTypeAdapter(this.serializer, this.deserializer, gson, typeToken, this);
            }
            return null;
        }
    }

    private final class GsonContextImpl implements JsonSerializationContext, JsonDeserializationContext {
        private GsonContextImpl() {
        }

        /* synthetic */ GsonContextImpl(TreeTypeAdapter x0, 1 x1) {
            this();
        }

        public JsonElement serialize(Object src) {
            return TreeTypeAdapter.this.gson.toJsonTree(src);
        }

        public JsonElement serialize(Object src, Type typeOfSrc) {
            return TreeTypeAdapter.this.gson.toJsonTree(src, typeOfSrc);
        }

        public Object deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
            return TreeTypeAdapter.this.gson.fromJson(json, typeOfT);
        }
    }
}
