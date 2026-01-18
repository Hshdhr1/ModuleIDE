package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.reflect.TypeToken;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public final class JsonAdapterAnnotationTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;

    public JsonAdapterAnnotationTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    public TypeAdapter create(Gson gson, TypeToken typeToken) {
        JsonAdapter annotation = (JsonAdapter) typeToken.getRawType().getAnnotation(JsonAdapter.class);
        if (annotation == null) {
            return null;
        }
        return getTypeAdapter(this.constructorConstructor, gson, typeToken, annotation);
    }

    TypeAdapter getTypeAdapter(ConstructorConstructor constructorConstructor, Gson gson, TypeToken typeToken, JsonAdapter annotation) {
        TypeAdapter<?> typeAdapter;
        Object instance = constructorConstructor.get(TypeToken.get(annotation.value())).construct();
        if (instance instanceof TypeAdapter) {
            typeAdapter = (TypeAdapter) instance;
        } else if (instance instanceof TypeAdapterFactory) {
            typeAdapter = ((TypeAdapterFactory) instance).create(gson, typeToken);
        } else if ((instance instanceof JsonSerializer) || (instance instanceof JsonDeserializer)) {
            JsonSerializer<?> serializer = instance instanceof JsonSerializer ? (JsonSerializer) instance : null;
            JsonDeserializer<?> deserializer = instance instanceof JsonDeserializer ? (JsonDeserializer) instance : null;
            typeAdapter = new TreeTypeAdapter(serializer, deserializer, gson, typeToken, null);
        } else {
            throw new IllegalArgumentException("Invalid attempt to bind an instance of " + instance.getClass().getName() + " as a @JsonAdapter for " + typeToken.toString() + ". @JsonAdapter value must be a TypeAdapter, TypeAdapterFactory, JsonSerializer or JsonDeserializer.");
        }
        if (typeAdapter != null && annotation.nullSafe()) {
            return typeAdapter.nullSafe();
        }
        return typeAdapter;
    }
}
