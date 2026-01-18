package com.google.gson;

import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.Streams;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.google.gson.internal.bind.TimeTypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public final class Gson {
    static final boolean DEFAULT_COMPLEX_MAP_KEYS = false;
    static final boolean DEFAULT_ESCAPE_HTML = true;
    static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;
    static final boolean DEFAULT_LENIENT = false;
    static final boolean DEFAULT_PRETTY_PRINT = false;
    static final boolean DEFAULT_SERIALIZE_NULLS = false;
    static final boolean DEFAULT_SPECIALIZE_FLOAT_VALUES = false;
    private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
    private static final TypeToken NULL_KEY_SURROGATE = TypeToken.get(Object.class);
    final List builderFactories;
    final List builderHierarchyFactories;
    private final ThreadLocal calls;
    final boolean complexMapKeySerialization;
    private final ConstructorConstructor constructorConstructor;
    final String datePattern;
    final int dateStyle;
    final Excluder excluder;
    final List factories;
    final FieldNamingStrategy fieldNamingStrategy;
    final boolean generateNonExecutableJson;
    final boolean htmlSafe;
    final Map instanceCreators;
    private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
    final boolean lenient;
    final LongSerializationPolicy longSerializationPolicy;
    final boolean prettyPrinting;
    final boolean serializeNulls;
    final boolean serializeSpecialFloatingPointValues;
    final int timeStyle;
    private final Map typeTokenCache;

    public Gson() {
        this(Excluder.DEFAULT, FieldNamingPolicy.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, false, LongSerializationPolicy.DEFAULT, null, 2, 2, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    Gson(Excluder excluder, FieldNamingStrategy fieldNamingStrategy, Map map, boolean serializeNulls, boolean complexMapKeySerialization, boolean generateNonExecutableGson, boolean htmlSafe, boolean prettyPrinting, boolean lenient, boolean serializeSpecialFloatingPointValues, LongSerializationPolicy longSerializationPolicy, String datePattern, int dateStyle, int timeStyle, List list, List list2, List list3) {
        this.calls = new ThreadLocal();
        this.typeTokenCache = new ConcurrentHashMap();
        this.excluder = excluder;
        this.fieldNamingStrategy = fieldNamingStrategy;
        this.instanceCreators = map;
        this.constructorConstructor = new ConstructorConstructor(map);
        this.serializeNulls = serializeNulls;
        this.complexMapKeySerialization = complexMapKeySerialization;
        this.generateNonExecutableJson = generateNonExecutableGson;
        this.htmlSafe = htmlSafe;
        this.prettyPrinting = prettyPrinting;
        this.lenient = lenient;
        this.serializeSpecialFloatingPointValues = serializeSpecialFloatingPointValues;
        this.longSerializationPolicy = longSerializationPolicy;
        this.datePattern = datePattern;
        this.dateStyle = dateStyle;
        this.timeStyle = timeStyle;
        this.builderFactories = list;
        this.builderHierarchyFactories = list2;
        ArrayList arrayList = new ArrayList();
        arrayList.add(TypeAdapters.JSON_ELEMENT_FACTORY);
        arrayList.add(ObjectTypeAdapter.FACTORY);
        arrayList.add(excluder);
        arrayList.addAll(list3);
        arrayList.add(TypeAdapters.STRING_FACTORY);
        arrayList.add(TypeAdapters.INTEGER_FACTORY);
        arrayList.add(TypeAdapters.BOOLEAN_FACTORY);
        arrayList.add(TypeAdapters.BYTE_FACTORY);
        arrayList.add(TypeAdapters.SHORT_FACTORY);
        TypeAdapter<Number> longAdapter = longAdapter(longSerializationPolicy);
        arrayList.add(TypeAdapters.newFactory(Long.TYPE, Long.class, longAdapter));
        arrayList.add(TypeAdapters.newFactory(Double.TYPE, Double.class, doubleAdapter(serializeSpecialFloatingPointValues)));
        arrayList.add(TypeAdapters.newFactory(Float.TYPE, Float.class, floatAdapter(serializeSpecialFloatingPointValues)));
        arrayList.add(TypeAdapters.NUMBER_FACTORY);
        arrayList.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
        arrayList.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
        arrayList.add(TypeAdapters.newFactory(AtomicLong.class, atomicLongAdapter(longAdapter)));
        arrayList.add(TypeAdapters.newFactory(AtomicLongArray.class, atomicLongArrayAdapter(longAdapter)));
        arrayList.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
        arrayList.add(TypeAdapters.CHARACTER_FACTORY);
        arrayList.add(TypeAdapters.STRING_BUILDER_FACTORY);
        arrayList.add(TypeAdapters.STRING_BUFFER_FACTORY);
        arrayList.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
        arrayList.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
        arrayList.add(TypeAdapters.URL_FACTORY);
        arrayList.add(TypeAdapters.URI_FACTORY);
        arrayList.add(TypeAdapters.UUID_FACTORY);
        arrayList.add(TypeAdapters.CURRENCY_FACTORY);
        arrayList.add(TypeAdapters.LOCALE_FACTORY);
        arrayList.add(TypeAdapters.INET_ADDRESS_FACTORY);
        arrayList.add(TypeAdapters.BIT_SET_FACTORY);
        arrayList.add(DateTypeAdapter.FACTORY);
        arrayList.add(TypeAdapters.CALENDAR_FACTORY);
        arrayList.add(TimeTypeAdapter.FACTORY);
        arrayList.add(SqlDateTypeAdapter.FACTORY);
        arrayList.add(TypeAdapters.TIMESTAMP_FACTORY);
        arrayList.add(ArrayTypeAdapter.FACTORY);
        arrayList.add(TypeAdapters.CLASS_FACTORY);
        arrayList.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
        arrayList.add(new MapTypeAdapterFactory(this.constructorConstructor, complexMapKeySerialization));
        this.jsonAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(this.constructorConstructor);
        arrayList.add(this.jsonAdapterFactory);
        arrayList.add(TypeAdapters.ENUM_FACTORY);
        arrayList.add(new ReflectiveTypeAdapterFactory(this.constructorConstructor, fieldNamingStrategy, excluder, this.jsonAdapterFactory));
        this.factories = Collections.unmodifiableList(arrayList);
    }

    public GsonBuilder newBuilder() {
        return new GsonBuilder(this);
    }

    public Excluder excluder() {
        return this.excluder;
    }

    public FieldNamingStrategy fieldNamingStrategy() {
        return this.fieldNamingStrategy;
    }

    public boolean serializeNulls() {
        return this.serializeNulls;
    }

    public boolean htmlSafe() {
        return this.htmlSafe;
    }

    private TypeAdapter doubleAdapter(boolean serializeSpecialFloatingPointValues) {
        return serializeSpecialFloatingPointValues ? TypeAdapters.DOUBLE : new 1();
    }

    class 1 extends TypeAdapter {
        1() {
        }

        public Double read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                return Double.valueOf(in.nextDouble());
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, Number value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            double doubleValue = value.doubleValue();
            Gson.checkValidFloatingPoint(doubleValue);
            out.value(value);
        }
    }

    private TypeAdapter floatAdapter(boolean serializeSpecialFloatingPointValues) {
        return serializeSpecialFloatingPointValues ? TypeAdapters.FLOAT : new 2();
    }

    class 2 extends TypeAdapter {
        2() {
        }

        public Float read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                return Float.valueOf((float) in.nextDouble());
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, Number value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            float floatValue = value.floatValue();
            Gson.checkValidFloatingPoint(floatValue);
            out.value(value);
        }
    }

    static void checkValidFloatingPoint(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException(value + " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    private static TypeAdapter longAdapter(LongSerializationPolicy longSerializationPolicy) {
        return longSerializationPolicy == LongSerializationPolicy.DEFAULT ? TypeAdapters.LONG : new 3();
    }

    class 3 extends TypeAdapter {
        3() {
        }

        public Number read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                return Long.valueOf(in.nextLong());
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, Number value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value.toString());
            }
        }
    }

    class 4 extends TypeAdapter {
        final /* synthetic */ TypeAdapter val$longAdapter;

        4(TypeAdapter typeAdapter) {
            this.val$longAdapter = typeAdapter;
        }

        public void write(JsonWriter out, AtomicLong value) throws IOException {
            this.val$longAdapter.write(out, Long.valueOf(value.get()));
        }

        public AtomicLong read(JsonReader in) throws IOException {
            Number value = (Number) this.val$longAdapter.read(in);
            return new AtomicLong(value.longValue());
        }
    }

    private static TypeAdapter atomicLongAdapter(TypeAdapter typeAdapter) {
        return new 4(typeAdapter).nullSafe();
    }

    class 5 extends TypeAdapter {
        final /* synthetic */ TypeAdapter val$longAdapter;

        5(TypeAdapter typeAdapter) {
            this.val$longAdapter = typeAdapter;
        }

        public void write(JsonWriter out, AtomicLongArray value) throws IOException {
            out.beginArray();
            int length = value.length();
            for (int i = 0; i < length; i++) {
                this.val$longAdapter.write(out, Long.valueOf(value.get(i)));
            }
            out.endArray();
        }

        public AtomicLongArray read(JsonReader in) throws IOException {
            ArrayList arrayList = new ArrayList();
            in.beginArray();
            while (in.hasNext()) {
                long value = ((Number) this.val$longAdapter.read(in)).longValue();
                arrayList.add(Long.valueOf(value));
            }
            in.endArray();
            int length = arrayList.size();
            AtomicLongArray array = new AtomicLongArray(length);
            for (int i = 0; i < length; i++) {
                array.set(i, ((Long) arrayList.get(i)).longValue());
            }
            return array;
        }
    }

    private static TypeAdapter atomicLongArrayAdapter(TypeAdapter typeAdapter) {
        return new 5(typeAdapter).nullSafe();
    }

    public TypeAdapter getAdapter(TypeToken typeToken) {
        TypeAdapter<?> cached = (TypeAdapter) this.typeTokenCache.get(typeToken == null ? NULL_KEY_SURROGATE : typeToken);
        if (cached == null) {
            HashMap hashMap = (Map) this.calls.get();
            boolean requiresThreadLocalCleanup = false;
            if (hashMap == null) {
                hashMap = new HashMap();
                this.calls.set(hashMap);
                requiresThreadLocalCleanup = true;
            }
            FutureTypeAdapter futureTypeAdapter = (FutureTypeAdapter) hashMap.get(typeToken);
            if (futureTypeAdapter != null) {
                return futureTypeAdapter;
            }
            try {
                FutureTypeAdapter futureTypeAdapter2 = new FutureTypeAdapter();
                hashMap.put(typeToken, futureTypeAdapter2);
                for (TypeAdapterFactory factory : this.factories) {
                    TypeAdapter create = factory.create(this, typeToken);
                    if (create != null) {
                        futureTypeAdapter2.setDelegate(create);
                        this.typeTokenCache.put(typeToken, create);
                        return create;
                    }
                }
                throw new IllegalArgumentException("GSON (2.8.7) cannot handle " + typeToken);
            } finally {
                hashMap.remove(typeToken);
                if (requiresThreadLocalCleanup) {
                    this.calls.remove();
                }
            }
        }
        return cached;
    }

    public TypeAdapter getDelegateAdapter(TypeAdapterFactory skipPast, TypeToken typeToken) {
        if (!this.factories.contains(skipPast)) {
            skipPast = this.jsonAdapterFactory;
        }
        boolean skipPastFound = false;
        for (TypeAdapterFactory factory : this.factories) {
            if (!skipPastFound) {
                if (factory == skipPast) {
                    skipPastFound = true;
                }
            } else {
                TypeAdapter create = factory.create(this, typeToken);
                if (create != null) {
                    return create;
                }
            }
        }
        throw new IllegalArgumentException("GSON cannot serialize " + typeToken);
    }

    public TypeAdapter getAdapter(Class cls) {
        return getAdapter(TypeToken.get(cls));
    }

    public JsonElement toJsonTree(Object src) {
        return src == null ? JsonNull.INSTANCE : toJsonTree(src, src.getClass());
    }

    public JsonElement toJsonTree(Object src, Type typeOfSrc) {
        JsonTreeWriter writer = new JsonTreeWriter();
        toJson(src, typeOfSrc, writer);
        return writer.get();
    }

    public String toJson(Object src) {
        return src == null ? toJson((JsonElement) JsonNull.INSTANCE) : toJson(src, (Type) src.getClass());
    }

    public String toJson(Object src, Type typeOfSrc) {
        StringWriter writer = new StringWriter();
        toJson(src, typeOfSrc, (Appendable) writer);
        return writer.toString();
    }

    public void toJson(Object src, Appendable writer) throws JsonIOException {
        if (src != null) {
            toJson(src, (Type) src.getClass(), writer);
        } else {
            toJson((JsonElement) JsonNull.INSTANCE, writer);
        }
    }

    public void toJson(Object src, Type typeOfSrc, Appendable writer) throws JsonIOException {
        try {
            JsonWriter jsonWriter = newJsonWriter(Streams.writerForAppendable(writer));
            toJson(src, typeOfSrc, jsonWriter);
        } catch (IOException e) {
            throw new JsonIOException((Throwable) e);
        }
    }

    public void toJson(Object src, Type typeOfSrc, JsonWriter writer) throws JsonIOException {
        TypeAdapter<?> adapter = getAdapter(TypeToken.get(typeOfSrc));
        boolean oldLenient = writer.isLenient();
        writer.setLenient(true);
        boolean oldHtmlSafe = writer.isHtmlSafe();
        writer.setHtmlSafe(this.htmlSafe);
        boolean oldSerializeNulls = writer.getSerializeNulls();
        writer.setSerializeNulls(this.serializeNulls);
        try {
            try {
                try {
                    adapter.write(writer, src);
                } catch (IOException e) {
                    throw new JsonIOException((Throwable) e);
                }
            } catch (AssertionError e2) {
                AssertionError error = new AssertionError("AssertionError (GSON 2.8.7): " + e2.getMessage());
                error.initCause(e2);
                throw error;
            }
        } finally {
            writer.setLenient(oldLenient);
            writer.setHtmlSafe(oldHtmlSafe);
            writer.setSerializeNulls(oldSerializeNulls);
        }
    }

    public String toJson(JsonElement jsonElement) {
        StringWriter writer = new StringWriter();
        toJson(jsonElement, (Appendable) writer);
        return writer.toString();
    }

    public void toJson(JsonElement jsonElement, Appendable writer) throws JsonIOException {
        try {
            JsonWriter jsonWriter = newJsonWriter(Streams.writerForAppendable(writer));
            toJson(jsonElement, jsonWriter);
        } catch (IOException e) {
            throw new JsonIOException((Throwable) e);
        }
    }

    public JsonWriter newJsonWriter(Writer writer) throws IOException {
        if (this.generateNonExecutableJson) {
            writer.write(")]}'\n");
        }
        JsonWriter jsonWriter = new JsonWriter(writer);
        if (this.prettyPrinting) {
            jsonWriter.setIndent("  ");
        }
        jsonWriter.setSerializeNulls(this.serializeNulls);
        return jsonWriter;
    }

    public JsonReader newJsonReader(Reader reader) {
        JsonReader jsonReader = new JsonReader(reader);
        jsonReader.setLenient(this.lenient);
        return jsonReader;
    }

    public void toJson(JsonElement jsonElement, JsonWriter writer) throws JsonIOException {
        boolean oldLenient = writer.isLenient();
        writer.setLenient(true);
        boolean oldHtmlSafe = writer.isHtmlSafe();
        writer.setHtmlSafe(this.htmlSafe);
        boolean oldSerializeNulls = writer.getSerializeNulls();
        writer.setSerializeNulls(this.serializeNulls);
        try {
            try {
                Streams.write(jsonElement, writer);
            } catch (IOException e) {
                throw new JsonIOException((Throwable) e);
            } catch (AssertionError e2) {
                AssertionError error = new AssertionError("AssertionError (GSON 2.8.7): " + e2.getMessage());
                error.initCause(e2);
                throw error;
            }
        } finally {
            writer.setLenient(oldLenient);
            writer.setHtmlSafe(oldHtmlSafe);
            writer.setSerializeNulls(oldSerializeNulls);
        }
    }

    public Object fromJson(String json, Class cls) throws JsonSyntaxException {
        Object object = fromJson(json, (Type) cls);
        return Primitives.wrap(cls).cast(object);
    }

    public Object fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        if (json == null) {
            return null;
        }
        StringReader reader = new StringReader(json);
        return fromJson((Reader) reader, typeOfT);
    }

    public Object fromJson(Reader json, Class cls) throws JsonSyntaxException, JsonIOException {
        JsonReader jsonReader = newJsonReader(json);
        Object object = fromJson(jsonReader, (Type) cls);
        assertFullConsumption(object, jsonReader);
        return Primitives.wrap(cls).cast(object);
    }

    public Object fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        JsonReader jsonReader = newJsonReader(json);
        Object fromJson = fromJson(jsonReader, typeOfT);
        assertFullConsumption(fromJson, jsonReader);
        return fromJson;
    }

    private static void assertFullConsumption(Object obj, JsonReader reader) {
        if (obj != null) {
            try {
                if (reader.peek() != JsonToken.END_DOCUMENT) {
                    throw new JsonIOException("JSON document was not fully consumed.");
                }
            } catch (MalformedJsonException e) {
                throw new JsonSyntaxException((Throwable) e);
            } catch (IOException e2) {
                throw new JsonIOException((Throwable) e2);
            }
        }
    }

    public Object fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        boolean isEmpty = true;
        boolean oldLenient = reader.isLenient();
        reader.setLenient(true);
        try {
            try {
                try {
                    reader.peek();
                    isEmpty = false;
                    Object read = getAdapter(TypeToken.get(typeOfT)).read(reader);
                    reader.setLenient(oldLenient);
                    return read;
                } catch (IOException e) {
                    throw new JsonSyntaxException((Throwable) e);
                } catch (IllegalStateException e2) {
                    throw new JsonSyntaxException((Throwable) e2);
                }
            } catch (AssertionError e3) {
                AssertionError error = new AssertionError("AssertionError (GSON 2.8.7): " + e3.getMessage());
                error.initCause(e3);
                throw error;
            } catch (EOFException e4) {
                if (!isEmpty) {
                    throw new JsonSyntaxException((Throwable) e4);
                }
                reader.setLenient(oldLenient);
                return null;
            }
        } catch (Throwable th) {
            reader.setLenient(oldLenient);
            throw th;
        }
    }

    public Object fromJson(JsonElement json, Class cls) throws JsonSyntaxException {
        Object object = fromJson(json, (Type) cls);
        return Primitives.wrap(cls).cast(object);
    }

    public Object fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException {
        if (json == null) {
            return null;
        }
        return fromJson(new JsonTreeReader(json), typeOfT);
    }

    static class FutureTypeAdapter extends TypeAdapter {
        private TypeAdapter delegate;

        FutureTypeAdapter() {
        }

        public void setDelegate(TypeAdapter typeAdapter) {
            if (this.delegate != null) {
                throw new AssertionError();
            }
            this.delegate = typeAdapter;
        }

        public Object read(JsonReader in) throws IOException {
            if (this.delegate == null) {
                throw new IllegalStateException();
            }
            return this.delegate.read(in);
        }

        public void write(JsonWriter out, Object obj) throws IOException {
            if (this.delegate == null) {
                throw new IllegalStateException();
            }
            this.delegate.write(out, obj);
        }
    }

    public String toString() {
        return "{serializeNulls:" + this.serializeNulls + ",factories:" + this.factories + ",instanceCreators:" + this.constructorConstructor + "}";
    }
}
