package com.blacksquircle.ui.language.base.model;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.annotations.NotNull;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: TokenType.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0011\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011¨\u0006\u0012"}, d2 = {"Lcom/blacksquircle/ui/language/base/model/TokenType;", "", "(Ljava/lang/String;I)V", "NUMBER", "OPERATOR", "KEYWORD", "TYPE", "LANG_CONST", "PREPROCESSOR", "VARIABLE", "METHOD", "STRING", "COMMENT", "TAG", "TAG_NAME", "ATTR_NAME", "ATTR_VALUE", "ENTITY_REF", "language-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes60.dex */
public final class TokenType {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ TokenType[] $VALUES;
    public static final TokenType NUMBER = new TokenType("NUMBER", 0);
    public static final TokenType OPERATOR = new TokenType("OPERATOR", 1);
    public static final TokenType KEYWORD = new TokenType("KEYWORD", 2);
    public static final TokenType TYPE = new TokenType("TYPE", 3);
    public static final TokenType LANG_CONST = new TokenType("LANG_CONST", 4);
    public static final TokenType PREPROCESSOR = new TokenType("PREPROCESSOR", 5);
    public static final TokenType VARIABLE = new TokenType("VARIABLE", 6);
    public static final TokenType METHOD = new TokenType("METHOD", 7);
    public static final TokenType STRING = new TokenType("STRING", 8);
    public static final TokenType COMMENT = new TokenType("COMMENT", 9);
    public static final TokenType TAG = new TokenType("TAG", 10);
    public static final TokenType TAG_NAME = new TokenType("TAG_NAME", 11);
    public static final TokenType ATTR_NAME = new TokenType("ATTR_NAME", 12);
    public static final TokenType ATTR_VALUE = new TokenType("ATTR_VALUE", 13);
    public static final TokenType ENTITY_REF = new TokenType("ENTITY_REF", 14);

    private static final /* synthetic */ TokenType[] $values() {
        return new TokenType[]{NUMBER, OPERATOR, KEYWORD, TYPE, LANG_CONST, PREPROCESSOR, VARIABLE, METHOD, STRING, COMMENT, TAG, TAG_NAME, ATTR_NAME, ATTR_VALUE, ENTITY_REF};
    }

    @NotNull
    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static TokenType valueOf(String str) {
        return (TokenType) Enum.valueOf(TokenType.class, str);
    }

    public static TokenType[] values() {
        return (TokenType[]) $VALUES.clone();
    }

    private TokenType(String str, int i) {
    }

    static {
        TokenType[] $values = $values();
        $VALUES = $values;
        $ENTRIES = EnumEntriesKt.enumEntries($values);
    }
}
