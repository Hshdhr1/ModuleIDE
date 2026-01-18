package com.google.gson;

import java.lang.reflect.Type;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public interface JsonDeserializationContext {
    Object deserialize(JsonElement jsonElement, Type type) throws JsonParseException;
}
