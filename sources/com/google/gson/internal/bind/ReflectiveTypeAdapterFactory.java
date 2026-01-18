package com.google.gson.internal.bind;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.reflect.ReflectionAccessor;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
    private final ReflectionAccessor accessor = ReflectionAccessor.getInstance();
    private final ConstructorConstructor constructorConstructor;
    private final Excluder excluder;
    private final FieldNamingStrategy fieldNamingPolicy;
    private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;

    public ReflectiveTypeAdapterFactory(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingPolicy, Excluder excluder, JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory) {
        this.constructorConstructor = constructorConstructor;
        this.fieldNamingPolicy = fieldNamingPolicy;
        this.excluder = excluder;
        this.jsonAdapterFactory = jsonAdapterFactory;
    }

    public boolean excludeField(Field f, boolean serialize) {
        return excludeField(f, serialize, this.excluder);
    }

    static boolean excludeField(Field f, boolean serialize, Excluder excluder) {
        return (excluder.excludeClass(f.getType(), serialize) || excluder.excludeField(f, serialize)) ? false : true;
    }

    private List getFieldNames(Field f) {
        SerializedName annotation = (SerializedName) f.getAnnotation(SerializedName.class);
        if (annotation == null) {
            String name = this.fieldNamingPolicy.translateName(f);
            return Collections.singletonList(name);
        }
        String serializedName = annotation.value();
        String[] alternates = annotation.alternate();
        if (alternates.length == 0) {
            return Collections.singletonList(serializedName);
        }
        ArrayList arrayList = new ArrayList(alternates.length + 1);
        arrayList.add(serializedName);
        for (String alternate : alternates) {
            arrayList.add(alternate);
        }
        return arrayList;
    }

    public TypeAdapter create(Gson gson, TypeToken typeToken) {
        Class rawType = typeToken.getRawType();
        if (!Object.class.isAssignableFrom(rawType)) {
            return null;
        }
        return new Adapter(this.constructorConstructor.get(typeToken), getBoundFields(gson, typeToken, rawType));
    }

    private BoundField createBoundField(Gson context, Field field, String name, TypeToken typeToken, boolean serialize, boolean deserialize) {
        boolean isPrimitive = Primitives.isPrimitive(typeToken.getRawType());
        JsonAdapter annotation = (JsonAdapter) field.getAnnotation(JsonAdapter.class);
        TypeAdapter<?> mapped = null;
        if (annotation != null) {
            mapped = this.jsonAdapterFactory.getTypeAdapter(this.constructorConstructor, context, typeToken, annotation);
        }
        boolean jsonAdapterPresent = mapped != null;
        if (mapped == null) {
            mapped = context.getAdapter(typeToken);
        }
        TypeAdapter<?> typeAdapter = mapped;
        return new 1(name, serialize, deserialize, field, jsonAdapterPresent, typeAdapter, context, typeToken, isPrimitive);
    }

    class 1 extends BoundField {
        final /* synthetic */ Gson val$context;
        final /* synthetic */ Field val$field;
        final /* synthetic */ TypeToken val$fieldType;
        final /* synthetic */ boolean val$isPrimitive;
        final /* synthetic */ boolean val$jsonAdapterPresent;
        final /* synthetic */ TypeAdapter val$typeAdapter;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(String name, boolean serialized, boolean deserialized, Field field, boolean z, TypeAdapter typeAdapter, Gson gson, TypeToken typeToken, boolean z2) {
            super(name, serialized, deserialized);
            this.val$field = field;
            this.val$jsonAdapterPresent = z;
            this.val$typeAdapter = typeAdapter;
            this.val$context = gson;
            this.val$fieldType = typeToken;
            this.val$isPrimitive = z2;
        }

        void write(JsonWriter writer, Object value) throws IOException, IllegalAccessException {
            Object fieldValue = this.val$field.get(value);
            TypeAdapter t = this.val$jsonAdapterPresent ? this.val$typeAdapter : new TypeAdapterRuntimeTypeWrapper(this.val$context, this.val$typeAdapter, this.val$fieldType.getType());
            t.write(writer, fieldValue);
        }

        void read(JsonReader reader, Object value) throws IOException, IllegalAccessException {
            Object fieldValue = this.val$typeAdapter.read(reader);
            if (fieldValue != null || !this.val$isPrimitive) {
                this.val$field.set(value, fieldValue);
            }
        }

        public boolean writeField(Object value) throws IOException, IllegalAccessException {
            if (!this.serialized) {
                return false;
            }
            Object fieldValue = this.val$field.get(value);
            return fieldValue != value;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00a9, code lost:
    
        r23 = com.google.gson.reflect.TypeToken.get(com.google.gson.internal.$Gson$Types.resolve(r23.getType(), r24, r24.getGenericSuperclass()));
        r24 = r23.getRawType();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.Map getBoundFields(com.google.gson.Gson r22, com.google.gson.reflect.TypeToken r23, java.lang.Class r24) {
        /*
            r21 = this;
            java.util.LinkedHashMap r17 = new java.util.LinkedHashMap
            r17.<init>()
            boolean r2 = r24.isInterface()
            if (r2 == 0) goto Lc
        Lb:
            return r17
        Lc:
            java.lang.reflect.Type r10 = r23.getType()
        L10:
            java.lang.Class<java.lang.Object> r2 = java.lang.Object.class
            r0 = r24
            if (r0 == r2) goto Lb
            java.lang.reflect.Field[] r13 = r24.getDeclaredFields()
            int r0 = r13.length
            r20 = r0
            r2 = 0
            r19 = r2
        L20:
            r0 = r19
            r1 = r20
            if (r0 >= r1) goto La9
            r4 = r13[r19]
            r2 = 1
            r0 = r21
            boolean r7 = r0.excludeField(r4, r2)
            r2 = 0
            r0 = r21
            boolean r8 = r0.excludeField(r4, r2)
            if (r7 != 0) goto L3f
            if (r8 != 0) goto L3f
        L3a:
            int r2 = r19 + 1
            r19 = r2
            goto L20
        L3f:
            r0 = r21
            com.google.gson.internal.reflect.ReflectionAccessor r2 = r0.accessor
            r2.makeAccessible(r4)
            java.lang.reflect.Type r2 = r23.getType()
            java.lang.reflect.Type r3 = r4.getGenericType()
            r0 = r24
            java.lang.reflect.Type r12 = com.google.gson.internal.$Gson$Types.resolve(r2, r0, r3)
            r0 = r21
            java.util.List r11 = r0.getFieldNames(r4)
            r15 = 0
            r14 = 0
            int r18 = r11.size()
        L60:
            r0 = r18
            if (r14 >= r0) goto L88
            java.lang.Object r5 = r11.get(r14)
            java.lang.String r5 = (java.lang.String) r5
            if (r14 == 0) goto L6d
            r7 = 0
        L6d:
            com.google.gson.reflect.TypeToken r6 = com.google.gson.reflect.TypeToken.get(r12)
            r2 = r21
            r3 = r22
            com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$BoundField r9 = r2.createBoundField(r3, r4, r5, r6, r7, r8)
            r0 = r17
            java.lang.Object r16 = r0.put(r5, r9)
            com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$BoundField r16 = (com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField) r16
            if (r15 != 0) goto L85
            r15 = r16
        L85:
            int r14 = r14 + 1
            goto L60
        L88:
            if (r15 == 0) goto L3a
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r3 = r3.append(r10)
            java.lang.String r6 = " declares multiple JSON fields named "
            java.lang.StringBuilder r3 = r3.append(r6)
            java.lang.String r6 = r15.name
            java.lang.StringBuilder r3 = r3.append(r6)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        La9:
            java.lang.reflect.Type r2 = r23.getType()
            java.lang.reflect.Type r3 = r24.getGenericSuperclass()
            r0 = r24
            java.lang.reflect.Type r2 = com.google.gson.internal.$Gson$Types.resolve(r2, r0, r3)
            com.google.gson.reflect.TypeToken r23 = com.google.gson.reflect.TypeToken.get(r2)
            java.lang.Class r24 = r23.getRawType()
            goto L10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.getBoundFields(com.google.gson.Gson, com.google.gson.reflect.TypeToken, java.lang.Class):java.util.Map");
    }

    static abstract class BoundField {
        final boolean deserialized;
        final String name;
        final boolean serialized;

        abstract void read(JsonReader jsonReader, Object obj) throws IOException, IllegalAccessException;

        abstract void write(JsonWriter jsonWriter, Object obj) throws IOException, IllegalAccessException;

        abstract boolean writeField(Object obj) throws IOException, IllegalAccessException;

        protected BoundField(String name, boolean serialized, boolean deserialized) {
            this.name = name;
            this.serialized = serialized;
            this.deserialized = deserialized;
        }
    }

    public static final class Adapter extends TypeAdapter {
        private final Map boundFields;
        private final ObjectConstructor constructor;

        Adapter(ObjectConstructor objectConstructor, Map map) {
            this.constructor = objectConstructor;
            this.boundFields = map;
        }

        public Object read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            Object construct = this.constructor.construct();
            try {
                in.beginObject();
                while (in.hasNext()) {
                    String name = in.nextName();
                    BoundField field = (BoundField) this.boundFields.get(name);
                    if (field == null || !field.deserialized) {
                        in.skipValue();
                    } else {
                        field.read(in, construct);
                    }
                }
                in.endObject();
                return construct;
            } catch (IllegalStateException e) {
                throw new JsonSyntaxException((Throwable) e);
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }

        public void write(JsonWriter out, Object obj) throws IOException {
            if (obj == null) {
                out.nullValue();
                return;
            }
            out.beginObject();
            try {
                for (BoundField boundField : this.boundFields.values()) {
                    if (boundField.writeField(obj)) {
                        out.name(boundField.name);
                        boundField.write(out, obj);
                    }
                }
                out.endObject();
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }
    }
}
