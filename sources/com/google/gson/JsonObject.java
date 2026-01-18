package com.google.gson;

import com.google.gson.internal.LinkedTreeMap;
import java.util.Map;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public final class JsonObject extends JsonElement {
    private final LinkedTreeMap members = new LinkedTreeMap();

    public JsonObject deepCopy() {
        JsonObject result = new JsonObject();
        for (Map.Entry<String, JsonElement> entry : this.members.entrySet()) {
            result.add((String) entry.getKey(), ((JsonElement) entry.getValue()).deepCopy());
        }
        return result;
    }

    public void add(String property, JsonElement value) {
        LinkedTreeMap linkedTreeMap = this.members;
        if (value == null) {
            value = JsonNull.INSTANCE;
        }
        linkedTreeMap.put(property, value);
    }

    public JsonElement remove(String property) {
        return (JsonElement) this.members.remove(property);
    }

    public void addProperty(String property, String value) {
        add(property, value == null ? JsonNull.INSTANCE : new JsonPrimitive(value));
    }

    public void addProperty(String property, Number value) {
        add(property, value == null ? JsonNull.INSTANCE : new JsonPrimitive(value));
    }

    public void addProperty(String property, Boolean value) {
        add(property, value == null ? JsonNull.INSTANCE : new JsonPrimitive(value));
    }

    public void addProperty(String property, Character value) {
        add(property, value == null ? JsonNull.INSTANCE : new JsonPrimitive(value));
    }

    public Set entrySet() {
        return this.members.entrySet();
    }

    public Set keySet() {
        return this.members.keySet();
    }

    public int size() {
        return this.members.size();
    }

    public boolean has(String memberName) {
        return this.members.containsKey(memberName);
    }

    public JsonElement get(String memberName) {
        return (JsonElement) this.members.get(memberName);
    }

    public JsonPrimitive getAsJsonPrimitive(String memberName) {
        return (JsonPrimitive) this.members.get(memberName);
    }

    public JsonArray getAsJsonArray(String memberName) {
        return (JsonArray) this.members.get(memberName);
    }

    public JsonObject getAsJsonObject(String memberName) {
        return (JsonObject) this.members.get(memberName);
    }

    public boolean equals(Object o) {
        return o == this || ((o instanceof JsonObject) && ((JsonObject) o).members.equals(this.members));
    }

    public int hashCode() {
        return this.members.hashCode();
    }
}
