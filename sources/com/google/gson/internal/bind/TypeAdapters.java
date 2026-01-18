package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public final class TypeAdapters {
    public static final TypeAdapter CLASS = new 1().nullSafe();
    public static final TypeAdapterFactory CLASS_FACTORY = newFactory(Class.class, CLASS);
    public static final TypeAdapter BIT_SET = new 2().nullSafe();
    public static final TypeAdapterFactory BIT_SET_FACTORY = newFactory(BitSet.class, BIT_SET);
    public static final TypeAdapter BOOLEAN = new 3();
    public static final TypeAdapter BOOLEAN_AS_STRING = new 4();
    public static final TypeAdapterFactory BOOLEAN_FACTORY = newFactory(Boolean.TYPE, Boolean.class, BOOLEAN);
    public static final TypeAdapter BYTE = new 5();
    public static final TypeAdapterFactory BYTE_FACTORY = newFactory(Byte.TYPE, Byte.class, BYTE);
    public static final TypeAdapter SHORT = new 6();
    public static final TypeAdapterFactory SHORT_FACTORY = newFactory(Short.TYPE, Short.class, SHORT);
    public static final TypeAdapter INTEGER = new 7();
    public static final TypeAdapterFactory INTEGER_FACTORY = newFactory(Integer.TYPE, Integer.class, INTEGER);
    public static final TypeAdapter ATOMIC_INTEGER = new 8().nullSafe();
    public static final TypeAdapterFactory ATOMIC_INTEGER_FACTORY = newFactory(AtomicInteger.class, ATOMIC_INTEGER);
    public static final TypeAdapter ATOMIC_BOOLEAN = new 9().nullSafe();
    public static final TypeAdapterFactory ATOMIC_BOOLEAN_FACTORY = newFactory(AtomicBoolean.class, ATOMIC_BOOLEAN);
    public static final TypeAdapter ATOMIC_INTEGER_ARRAY = new 10().nullSafe();
    public static final TypeAdapterFactory ATOMIC_INTEGER_ARRAY_FACTORY = newFactory(AtomicIntegerArray.class, ATOMIC_INTEGER_ARRAY);
    public static final TypeAdapter LONG = new 11();
    public static final TypeAdapter FLOAT = new 12();
    public static final TypeAdapter DOUBLE = new 13();
    public static final TypeAdapter NUMBER = new 14();
    public static final TypeAdapterFactory NUMBER_FACTORY = newFactory(Number.class, NUMBER);
    public static final TypeAdapter CHARACTER = new 15();
    public static final TypeAdapterFactory CHARACTER_FACTORY = newFactory(Character.TYPE, Character.class, CHARACTER);
    public static final TypeAdapter STRING = new 16();
    public static final TypeAdapter BIG_DECIMAL = new 17();
    public static final TypeAdapter BIG_INTEGER = new 18();
    public static final TypeAdapterFactory STRING_FACTORY = newFactory(String.class, STRING);
    public static final TypeAdapter STRING_BUILDER = new 19();
    public static final TypeAdapterFactory STRING_BUILDER_FACTORY = newFactory(StringBuilder.class, STRING_BUILDER);
    public static final TypeAdapter STRING_BUFFER = new 20();
    public static final TypeAdapterFactory STRING_BUFFER_FACTORY = newFactory(StringBuffer.class, STRING_BUFFER);
    public static final TypeAdapter URL = new 21();
    public static final TypeAdapterFactory URL_FACTORY = newFactory(URL.class, URL);
    public static final TypeAdapter URI = new 22();
    public static final TypeAdapterFactory URI_FACTORY = newFactory(URI.class, URI);
    public static final TypeAdapter INET_ADDRESS = new 23();
    public static final TypeAdapterFactory INET_ADDRESS_FACTORY = newTypeHierarchyFactory(InetAddress.class, INET_ADDRESS);
    public static final TypeAdapter UUID = new 24();
    public static final TypeAdapterFactory UUID_FACTORY = newFactory(UUID.class, UUID);
    public static final TypeAdapter CURRENCY = new 25().nullSafe();
    public static final TypeAdapterFactory CURRENCY_FACTORY = newFactory(Currency.class, CURRENCY);
    public static final TypeAdapterFactory TIMESTAMP_FACTORY = new 26();
    public static final TypeAdapter CALENDAR = new 27();
    public static final TypeAdapterFactory CALENDAR_FACTORY = newFactoryForMultipleTypes(Calendar.class, GregorianCalendar.class, CALENDAR);
    public static final TypeAdapter LOCALE = new 28();
    public static final TypeAdapterFactory LOCALE_FACTORY = newFactory(Locale.class, LOCALE);
    public static final TypeAdapter JSON_ELEMENT = new 29();
    public static final TypeAdapterFactory JSON_ELEMENT_FACTORY = newTypeHierarchyFactory(JsonElement.class, JSON_ELEMENT);
    public static final TypeAdapterFactory ENUM_FACTORY = new 30();

    private TypeAdapters() {
        throw new UnsupportedOperationException();
    }

    class 1 extends TypeAdapter {
        1() {
        }

        public void write(JsonWriter out, Class value) throws IOException {
            throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + value.getName() + ". Forgot to register a type adapter?");
        }

        public Class read(JsonReader in) throws IOException {
            throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
        }
    }

    class 2 extends TypeAdapter {
        2() {
        }

        public BitSet read(JsonReader in) throws IOException {
            boolean set;
            BitSet bitset = new BitSet();
            in.beginArray();
            int i = 0;
            JsonToken tokenType = in.peek();
            while (tokenType != JsonToken.END_ARRAY) {
                switch (36.$SwitchMap$com$google$gson$stream$JsonToken[tokenType.ordinal()]) {
                    case 1:
                        if (in.nextInt() == 0) {
                            set = false;
                            break;
                        } else {
                            set = true;
                            break;
                        }
                    case 2:
                        set = in.nextBoolean();
                        break;
                    case 3:
                        String stringValue = in.nextString();
                        try {
                            if (Integer.parseInt(stringValue) == 0) {
                                set = false;
                                break;
                            } else {
                                set = true;
                                break;
                            }
                        } catch (NumberFormatException e) {
                            throw new JsonSyntaxException("Error: Expecting: bitset number value (1, 0), Found: " + stringValue);
                        }
                    default:
                        throw new JsonSyntaxException("Invalid bitset value type: " + tokenType);
                }
                if (set) {
                    bitset.set(i);
                }
                i++;
                tokenType = in.peek();
            }
            in.endArray();
            return bitset;
        }

        public void write(JsonWriter out, BitSet src) throws IOException {
            out.beginArray();
            int length = src.length();
            for (int i = 0; i < length; i++) {
                int value = src.get(i) ? 1 : 0;
                out.value(value);
            }
            out.endArray();
        }
    }

    static /* synthetic */ class 36 {
        static final /* synthetic */ int[] $SwitchMap$com$google$gson$stream$JsonToken = new int[JsonToken.values().length];

        static {
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.BOOLEAN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.NULL.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.BEGIN_ARRAY.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.BEGIN_OBJECT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.END_DOCUMENT.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.NAME.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.END_OBJECT.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.END_ARRAY.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    class 3 extends TypeAdapter {
        3() {
        }

        public Boolean read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            if (peek == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            if (peek == JsonToken.STRING) {
                return Boolean.valueOf(Boolean.parseBoolean(in.nextString()));
            }
            return Boolean.valueOf(in.nextBoolean());
        }

        public void write(JsonWriter out, Boolean value) throws IOException {
            out.value(value);
        }
    }

    class 4 extends TypeAdapter {
        4() {
        }

        public Boolean read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                return Boolean.valueOf(in.nextString());
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, Boolean value) throws IOException {
            out.value(value == null ? "null" : value.toString());
        }
    }

    class 5 extends TypeAdapter {
        5() {
        }

        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                int intValue = in.nextInt();
                return Byte.valueOf((byte) intValue);
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException((Throwable) e);
            }
        }

        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    }

    class 6 extends TypeAdapter {
        6() {
        }

        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                return Short.valueOf((short) in.nextInt());
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException((Throwable) e);
            }
        }

        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    }

    class 7 extends TypeAdapter {
        7() {
        }

        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                return Integer.valueOf(in.nextInt());
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException((Throwable) e);
            }
        }

        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    }

    class 8 extends TypeAdapter {
        8() {
        }

        public AtomicInteger read(JsonReader in) throws IOException {
            try {
                return new AtomicInteger(in.nextInt());
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException((Throwable) e);
            }
        }

        public void write(JsonWriter out, AtomicInteger value) throws IOException {
            out.value(value.get());
        }
    }

    class 9 extends TypeAdapter {
        9() {
        }

        public AtomicBoolean read(JsonReader in) throws IOException {
            return new AtomicBoolean(in.nextBoolean());
        }

        public void write(JsonWriter out, AtomicBoolean value) throws IOException {
            out.value(value.get());
        }
    }

    class 10 extends TypeAdapter {
        10() {
        }

        public AtomicIntegerArray read(JsonReader in) throws IOException {
            ArrayList arrayList = new ArrayList();
            in.beginArray();
            while (in.hasNext()) {
                try {
                    int integer = in.nextInt();
                    arrayList.add(Integer.valueOf(integer));
                } catch (NumberFormatException e) {
                    throw new JsonSyntaxException((Throwable) e);
                }
            }
            in.endArray();
            int length = arrayList.size();
            AtomicIntegerArray array = new AtomicIntegerArray(length);
            for (int i = 0; i < length; i++) {
                array.set(i, ((Integer) arrayList.get(i)).intValue());
            }
            return array;
        }

        public void write(JsonWriter out, AtomicIntegerArray value) throws IOException {
            out.beginArray();
            int length = value.length();
            for (int i = 0; i < length; i++) {
                out.value(value.get(i));
            }
            out.endArray();
        }
    }

    class 11 extends TypeAdapter {
        11() {
        }

        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                return Long.valueOf(in.nextLong());
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException((Throwable) e);
            }
        }

        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    }

    class 12 extends TypeAdapter {
        12() {
        }

        public Number read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                return Float.valueOf((float) in.nextDouble());
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    }

    class 13 extends TypeAdapter {
        13() {
        }

        public Number read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                return Double.valueOf(in.nextDouble());
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    }

    class 14 extends TypeAdapter {
        14() {
        }

        public Number read(JsonReader in) throws IOException {
            JsonToken jsonToken = in.peek();
            switch (36.$SwitchMap$com$google$gson$stream$JsonToken[jsonToken.ordinal()]) {
                case 1:
                case 3:
                    return new LazilyParsedNumber(in.nextString());
                case 2:
                default:
                    throw new JsonSyntaxException("Expecting number, got: " + jsonToken);
                case 4:
                    in.nextNull();
                    return null;
            }
        }

        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    }

    class 15 extends TypeAdapter {
        15() {
        }

        public Character read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            String str = in.nextString();
            if (str.length() != 1) {
                throw new JsonSyntaxException("Expecting character, got: " + str);
            }
            return Character.valueOf(str.charAt(0));
        }

        public void write(JsonWriter out, Character value) throws IOException {
            out.value(value == null ? null : String.valueOf(value));
        }
    }

    class 16 extends TypeAdapter {
        16() {
        }

        public String read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            if (peek == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            if (peek == JsonToken.BOOLEAN) {
                return Boolean.toString(in.nextBoolean());
            }
            return in.nextString();
        }

        public void write(JsonWriter out, String value) throws IOException {
            out.value(value);
        }
    }

    class 17 extends TypeAdapter {
        17() {
        }

        public BigDecimal read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                return new BigDecimal(in.nextString());
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException((Throwable) e);
            }
        }

        public void write(JsonWriter out, BigDecimal value) throws IOException {
            out.value((Number) value);
        }
    }

    class 18 extends TypeAdapter {
        18() {
        }

        public BigInteger read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                return new BigInteger(in.nextString());
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException((Throwable) e);
            }
        }

        public void write(JsonWriter out, BigInteger value) throws IOException {
            out.value((Number) value);
        }
    }

    class 19 extends TypeAdapter {
        19() {
        }

        public StringBuilder read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                return new StringBuilder(in.nextString());
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, StringBuilder value) throws IOException {
            out.value(value == null ? null : value.toString());
        }
    }

    class 20 extends TypeAdapter {
        20() {
        }

        public StringBuffer read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                return new StringBuffer(in.nextString());
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, StringBuffer value) throws IOException {
            out.value(value == null ? null : value.toString());
        }
    }

    class 21 extends TypeAdapter {
        21() {
        }

        public URL read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            String nextString = in.nextString();
            if ("null".equals(nextString)) {
                return null;
            }
            return new URL(nextString);
        }

        public void write(JsonWriter out, URL value) throws IOException {
            out.value(value == null ? null : value.toExternalForm());
        }
    }

    class 22 extends TypeAdapter {
        22() {
        }

        public URI read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                String nextString = in.nextString();
                if ("null".equals(nextString)) {
                    return null;
                }
                return new URI(nextString);
            } catch (URISyntaxException e) {
                throw new JsonIOException((Throwable) e);
            }
        }

        public void write(JsonWriter out, URI value) throws IOException {
            out.value(value == null ? null : value.toASCIIString());
        }
    }

    class 23 extends TypeAdapter {
        23() {
        }

        public InetAddress read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                return InetAddress.getByName(in.nextString());
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, InetAddress value) throws IOException {
            out.value(value == null ? null : value.getHostAddress());
        }
    }

    class 24 extends TypeAdapter {
        24() {
        }

        public UUID read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                return UUID.fromString(in.nextString());
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, UUID value) throws IOException {
            out.value(value == null ? null : value.toString());
        }
    }

    class 25 extends TypeAdapter {
        25() {
        }

        public Currency read(JsonReader in) throws IOException {
            return Currency.getInstance(in.nextString());
        }

        public void write(JsonWriter out, Currency value) throws IOException {
            out.value(value.getCurrencyCode());
        }
    }

    class 26 implements TypeAdapterFactory {
        26() {
        }

        public TypeAdapter create(Gson gson, TypeToken typeToken) {
            if (typeToken.getRawType() != Timestamp.class) {
                return null;
            }
            TypeAdapter<Date> dateTypeAdapter = gson.getAdapter(Date.class);
            return new 1(dateTypeAdapter);
        }

        class 1 extends TypeAdapter {
            final /* synthetic */ TypeAdapter val$dateTypeAdapter;

            1(TypeAdapter typeAdapter) {
                this.val$dateTypeAdapter = typeAdapter;
            }

            public Timestamp read(JsonReader in) throws IOException {
                Date date = (Date) this.val$dateTypeAdapter.read(in);
                if (date != null) {
                    return new Timestamp(date.getTime());
                }
                return null;
            }

            public void write(JsonWriter out, Timestamp value) throws IOException {
                this.val$dateTypeAdapter.write(out, value);
            }
        }
    }

    class 27 extends TypeAdapter {
        private static final String DAY_OF_MONTH = "dayOfMonth";
        private static final String HOUR_OF_DAY = "hourOfDay";
        private static final String MINUTE = "minute";
        private static final String MONTH = "month";
        private static final String SECOND = "second";
        private static final String YEAR = "year";

        27() {
        }

        public Calendar read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            in.beginObject();
            int year = 0;
            int month = 0;
            int dayOfMonth = 0;
            int hourOfDay = 0;
            int minute = 0;
            int second = 0;
            while (in.peek() != JsonToken.END_OBJECT) {
                String name = in.nextName();
                int value = in.nextInt();
                if ("year".equals(name)) {
                    year = value;
                } else if ("month".equals(name)) {
                    month = value;
                } else if ("dayOfMonth".equals(name)) {
                    dayOfMonth = value;
                } else if ("hourOfDay".equals(name)) {
                    hourOfDay = value;
                } else if ("minute".equals(name)) {
                    minute = value;
                } else if ("second".equals(name)) {
                    second = value;
                }
            }
            in.endObject();
            return new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second);
        }

        public void write(JsonWriter out, Calendar value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.beginObject();
            out.name("year");
            out.value(value.get(1));
            out.name("month");
            out.value(value.get(2));
            out.name("dayOfMonth");
            out.value(value.get(5));
            out.name("hourOfDay");
            out.value(value.get(11));
            out.name("minute");
            out.value(value.get(12));
            out.name("second");
            out.value(value.get(13));
            out.endObject();
        }
    }

    class 28 extends TypeAdapter {
        28() {
        }

        public Locale read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            String locale = in.nextString();
            StringTokenizer tokenizer = new StringTokenizer(locale, "_");
            String language = null;
            String country = null;
            String variant = null;
            if (tokenizer.hasMoreElements()) {
                language = tokenizer.nextToken();
            }
            if (tokenizer.hasMoreElements()) {
                country = tokenizer.nextToken();
            }
            if (tokenizer.hasMoreElements()) {
                variant = tokenizer.nextToken();
            }
            if (country == null && variant == null) {
                return new Locale(language);
            }
            if (variant == null) {
                return new Locale(language, country);
            }
            return new Locale(language, country, variant);
        }

        public void write(JsonWriter out, Locale value) throws IOException {
            out.value(value == null ? null : value.toString());
        }
    }

    class 29 extends TypeAdapter {
        29() {
        }

        public JsonElement read(JsonReader in) throws IOException {
            switch (36.$SwitchMap$com$google$gson$stream$JsonToken[in.peek().ordinal()]) {
                case 1:
                    String number = in.nextString();
                    return new JsonPrimitive(new LazilyParsedNumber(number));
                case 2:
                    return new JsonPrimitive(Boolean.valueOf(in.nextBoolean()));
                case 3:
                    return new JsonPrimitive(in.nextString());
                case 4:
                    in.nextNull();
                    return JsonNull.INSTANCE;
                case 5:
                    JsonArray array = new JsonArray();
                    in.beginArray();
                    while (in.hasNext()) {
                        array.add(read(in));
                    }
                    in.endArray();
                    return array;
                case 6:
                    JsonObject object = new JsonObject();
                    in.beginObject();
                    while (in.hasNext()) {
                        object.add(in.nextName(), read(in));
                    }
                    in.endObject();
                    return object;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public void write(JsonWriter out, JsonElement value) throws IOException {
            if (value == null || value.isJsonNull()) {
                out.nullValue();
                return;
            }
            if (value.isJsonPrimitive()) {
                JsonPrimitive primitive = value.getAsJsonPrimitive();
                if (primitive.isNumber()) {
                    out.value(primitive.getAsNumber());
                    return;
                } else if (primitive.isBoolean()) {
                    out.value(primitive.getAsBoolean());
                    return;
                } else {
                    out.value(primitive.getAsString());
                    return;
                }
            }
            if (value.isJsonArray()) {
                out.beginArray();
                Iterator it = value.getAsJsonArray().iterator();
                while (it.hasNext()) {
                    write(out, (JsonElement) it.next());
                }
                out.endArray();
                return;
            }
            if (value.isJsonObject()) {
                out.beginObject();
                for (Map.Entry<String, JsonElement> e : value.getAsJsonObject().entrySet()) {
                    out.name((String) e.getKey());
                    write(out, (JsonElement) e.getValue());
                }
                out.endObject();
                return;
            }
            throw new IllegalArgumentException("Couldn't write " + value.getClass());
        }
    }

    private static final class EnumTypeAdapter extends TypeAdapter {
        private final Map nameToConstant = new HashMap();
        private final Map constantToName = new HashMap();

        public EnumTypeAdapter(Class cls) {
            try {
                for (Enum r2 : (Enum[]) cls.getEnumConstants()) {
                    String name = r2.name();
                    SerializedName annotation = (SerializedName) cls.getField(name).getAnnotation(SerializedName.class);
                    if (annotation != null) {
                        name = annotation.value();
                        for (String alternate : annotation.alternate()) {
                            this.nameToConstant.put(alternate, r2);
                        }
                    }
                    this.nameToConstant.put(name, r2);
                    this.constantToName.put(r2, name);
                }
            } catch (NoSuchFieldException e) {
                throw new AssertionError(e);
            }
        }

        public Enum read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                return (Enum) this.nameToConstant.get(in.nextString());
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, Enum r3) throws IOException {
            out.value(r3 == null ? null : (String) this.constantToName.get(r3));
        }
    }

    class 30 implements TypeAdapterFactory {
        30() {
        }

        public TypeAdapter create(Gson gson, TypeToken typeToken) {
            Class rawType = typeToken.getRawType();
            if (!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class) {
                return null;
            }
            if (!rawType.isEnum()) {
                rawType = rawType.getSuperclass();
            }
            return new EnumTypeAdapter(rawType);
        }
    }

    class 31 implements TypeAdapterFactory {
        final /* synthetic */ TypeToken val$type;
        final /* synthetic */ TypeAdapter val$typeAdapter;

        31(TypeToken typeToken, TypeAdapter typeAdapter) {
            this.val$type = typeToken;
            this.val$typeAdapter = typeAdapter;
        }

        public TypeAdapter create(Gson gson, TypeToken typeToken) {
            if (typeToken.equals(this.val$type)) {
                return this.val$typeAdapter;
            }
            return null;
        }
    }

    public static TypeAdapterFactory newFactory(TypeToken typeToken, TypeAdapter typeAdapter) {
        return new 31(typeToken, typeAdapter);
    }

    class 32 implements TypeAdapterFactory {
        final /* synthetic */ Class val$type;
        final /* synthetic */ TypeAdapter val$typeAdapter;

        32(Class cls, TypeAdapter typeAdapter) {
            this.val$type = cls;
            this.val$typeAdapter = typeAdapter;
        }

        public TypeAdapter create(Gson gson, TypeToken typeToken) {
            if (typeToken.getRawType() == this.val$type) {
                return this.val$typeAdapter;
            }
            return null;
        }

        public String toString() {
            return "Factory[type=" + this.val$type.getName() + ",adapter=" + this.val$typeAdapter + "]";
        }
    }

    public static TypeAdapterFactory newFactory(Class cls, TypeAdapter typeAdapter) {
        return new 32(cls, typeAdapter);
    }

    class 33 implements TypeAdapterFactory {
        final /* synthetic */ Class val$boxed;
        final /* synthetic */ TypeAdapter val$typeAdapter;
        final /* synthetic */ Class val$unboxed;

        33(Class cls, Class cls2, TypeAdapter typeAdapter) {
            this.val$unboxed = cls;
            this.val$boxed = cls2;
            this.val$typeAdapter = typeAdapter;
        }

        public TypeAdapter create(Gson gson, TypeToken typeToken) {
            Class rawType = typeToken.getRawType();
            if (rawType == this.val$unboxed || rawType == this.val$boxed) {
                return this.val$typeAdapter;
            }
            return null;
        }

        public String toString() {
            return "Factory[type=" + this.val$boxed.getName() + "+" + this.val$unboxed.getName() + ",adapter=" + this.val$typeAdapter + "]";
        }
    }

    public static TypeAdapterFactory newFactory(Class cls, Class cls2, TypeAdapter typeAdapter) {
        return new 33(cls, cls2, typeAdapter);
    }

    class 34 implements TypeAdapterFactory {
        final /* synthetic */ Class val$base;
        final /* synthetic */ Class val$sub;
        final /* synthetic */ TypeAdapter val$typeAdapter;

        34(Class cls, Class cls2, TypeAdapter typeAdapter) {
            this.val$base = cls;
            this.val$sub = cls2;
            this.val$typeAdapter = typeAdapter;
        }

        public TypeAdapter create(Gson gson, TypeToken typeToken) {
            Class rawType = typeToken.getRawType();
            if (rawType == this.val$base || rawType == this.val$sub) {
                return this.val$typeAdapter;
            }
            return null;
        }

        public String toString() {
            return "Factory[type=" + this.val$base.getName() + "+" + this.val$sub.getName() + ",adapter=" + this.val$typeAdapter + "]";
        }
    }

    public static TypeAdapterFactory newFactoryForMultipleTypes(Class cls, Class cls2, TypeAdapter typeAdapter) {
        return new 34(cls, cls2, typeAdapter);
    }

    class 35 implements TypeAdapterFactory {
        final /* synthetic */ Class val$clazz;
        final /* synthetic */ TypeAdapter val$typeAdapter;

        35(Class cls, TypeAdapter typeAdapter) {
            this.val$clazz = cls;
            this.val$typeAdapter = typeAdapter;
        }

        public TypeAdapter create(Gson gson, TypeToken typeToken) {
            Class rawType = typeToken.getRawType();
            if (this.val$clazz.isAssignableFrom(rawType)) {
                return new 1(rawType);
            }
            return null;
        }

        class 1 extends TypeAdapter {
            final /* synthetic */ Class val$requestedType;

            1(Class cls) {
                this.val$requestedType = cls;
            }

            public void write(JsonWriter out, Object obj) throws IOException {
                35.this.val$typeAdapter.write(out, obj);
            }

            public Object read(JsonReader in) throws IOException {
                Object read = 35.this.val$typeAdapter.read(in);
                if (read != null && !this.val$requestedType.isInstance(read)) {
                    throw new JsonSyntaxException("Expected a " + this.val$requestedType.getName() + " but was " + read.getClass().getName());
                }
                return read;
            }
        }

        public String toString() {
            return "Factory[typeHierarchy=" + this.val$clazz.getName() + ",adapter=" + this.val$typeAdapter + "]";
        }
    }

    public static TypeAdapterFactory newTypeHierarchyFactory(Class cls, TypeAdapter typeAdapter) {
        return new 35(cls, typeAdapter);
    }
}
