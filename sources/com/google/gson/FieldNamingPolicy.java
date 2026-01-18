package com.google.gson;

import java.lang.reflect.Field;
import java.util.Locale;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public abstract class FieldNamingPolicy implements FieldNamingStrategy {
    public static final FieldNamingPolicy IDENTITY = new 1("IDENTITY", 0);
    public static final FieldNamingPolicy UPPER_CAMEL_CASE = new 2("UPPER_CAMEL_CASE", 1);
    public static final FieldNamingPolicy UPPER_CAMEL_CASE_WITH_SPACES = new 3("UPPER_CAMEL_CASE_WITH_SPACES", 2);
    public static final FieldNamingPolicy LOWER_CASE_WITH_UNDERSCORES = new 4("LOWER_CASE_WITH_UNDERSCORES", 3);
    public static final FieldNamingPolicy LOWER_CASE_WITH_DASHES = new 5("LOWER_CASE_WITH_DASHES", 4);
    public static final FieldNamingPolicy LOWER_CASE_WITH_DOTS = new 6("LOWER_CASE_WITH_DOTS", 5);
    private static final /* synthetic */ FieldNamingPolicy[] $VALUES = {IDENTITY, UPPER_CAMEL_CASE, UPPER_CAMEL_CASE_WITH_SPACES, LOWER_CASE_WITH_UNDERSCORES, LOWER_CASE_WITH_DASHES, LOWER_CASE_WITH_DOTS};

    private FieldNamingPolicy(String str, int i) {
    }

    /* synthetic */ FieldNamingPolicy(String x0, int x1, 1 x2) {
        this(x0, x1);
    }

    public static FieldNamingPolicy valueOf(String name) {
        return (FieldNamingPolicy) Enum.valueOf(FieldNamingPolicy.class, name);
    }

    public static FieldNamingPolicy[] values() {
        return (FieldNamingPolicy[]) $VALUES.clone();
    }

    enum 1 extends FieldNamingPolicy {
        1(String str, int i) {
            super(str, i, null);
        }

        public String translateName(Field f) {
            return f.getName();
        }
    }

    enum 2 extends FieldNamingPolicy {
        2(String str, int i) {
            super(str, i, null);
        }

        public String translateName(Field f) {
            return upperCaseFirstLetter(f.getName());
        }
    }

    enum 3 extends FieldNamingPolicy {
        3(String str, int i) {
            super(str, i, null);
        }

        public String translateName(Field f) {
            return upperCaseFirstLetter(separateCamelCase(f.getName(), " "));
        }
    }

    enum 4 extends FieldNamingPolicy {
        4(String str, int i) {
            super(str, i, null);
        }

        public String translateName(Field f) {
            return separateCamelCase(f.getName(), "_").toLowerCase(Locale.ENGLISH);
        }
    }

    enum 5 extends FieldNamingPolicy {
        5(String str, int i) {
            super(str, i, null);
        }

        public String translateName(Field f) {
            return separateCamelCase(f.getName(), "-").toLowerCase(Locale.ENGLISH);
        }
    }

    enum 6 extends FieldNamingPolicy {
        6(String str, int i) {
            super(str, i, null);
        }

        public String translateName(Field f) {
            return separateCamelCase(f.getName(), ".").toLowerCase(Locale.ENGLISH);
        }
    }

    static String separateCamelCase(String name, String separator) {
        StringBuilder translation = new StringBuilder();
        int length = name.length();
        for (int i = 0; i < length; i++) {
            char character = name.charAt(i);
            if (Character.isUpperCase(character) && translation.length() != 0) {
                translation.append(separator);
            }
            translation.append(character);
        }
        return translation.toString();
    }

    static String upperCaseFirstLetter(String name) {
        int firstLetterIndex = 0;
        int limit = name.length() - 1;
        while (!Character.isLetter(name.charAt(firstLetterIndex)) && firstLetterIndex < limit) {
            firstLetterIndex++;
        }
        char firstLetter = name.charAt(firstLetterIndex);
        if (!Character.isUpperCase(firstLetter)) {
            char uppercased = Character.toUpperCase(firstLetter);
            if (firstLetterIndex == 0) {
                return uppercased + name.substring(1);
            }
            return name.substring(0, firstLetterIndex) + uppercased + name.substring(firstLetterIndex + 1);
        }
        return name;
    }
}
