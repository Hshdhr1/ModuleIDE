package com.blacksquircle.ui.language.base.model;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Suggestion.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001:\u0001\u0019B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\b\u0010\u0018\u001a\u00020\u0007H\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lcom/blacksquircle/ui/language/base/model/Suggestion;", "", "type", "Lcom/blacksquircle/ui/language/base/model/Suggestion$Type;", "text", "", "returnType", "", "(Lcom/blacksquircle/ui/language/base/model/Suggestion$Type;Ljava/lang/CharSequence;Ljava/lang/String;)V", "getReturnType", "()Ljava/lang/String;", "getText", "()Ljava/lang/CharSequence;", "getType", "()Lcom/blacksquircle/ui/language/base/model/Suggestion$Type;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "Type", "language-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes60.dex */
public final /* data */ class Suggestion {

    @NotNull
    private final String returnType;

    @NotNull
    private final CharSequence text;

    @NotNull
    private final Type type;

    public static /* synthetic */ Suggestion copy$default(Suggestion suggestion, Type type, CharSequence charSequence, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            type = suggestion.type;
        }
        if ((i & 2) != 0) {
            charSequence = suggestion.text;
        }
        if ((i & 4) != 0) {
            str = suggestion.returnType;
        }
        return suggestion.copy(type, charSequence, str);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final Type getType() {
        return this.type;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final CharSequence getText() {
        return this.text;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getReturnType() {
        return this.returnType;
    }

    @NotNull
    public final Suggestion copy(@NotNull Type type, @NotNull CharSequence text, @NotNull String returnType) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        return new Suggestion(type, text, returnType);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Suggestion)) {
            return false;
        }
        Suggestion suggestion = (Suggestion) other;
        return this.type == suggestion.type && Intrinsics.areEqual(this.text, suggestion.text) && Intrinsics.areEqual(this.returnType, suggestion.returnType);
    }

    public int hashCode() {
        return (((this.type.hashCode() * 31) + this.text.hashCode()) * 31) + this.returnType.hashCode();
    }

    public Suggestion(@NotNull Type type, @NotNull CharSequence charSequence, @NotNull String str) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(charSequence, "text");
        Intrinsics.checkNotNullParameter(str, "returnType");
        this.type = type;
        this.text = charSequence;
        this.returnType = str;
    }

    @NotNull
    public final Type getType() {
        return this.type;
    }

    @NotNull
    public final CharSequence getText() {
        return this.text;
    }

    @NotNull
    public final String getReturnType() {
        return this.returnType;
    }

    @NotNull
    public String toString() {
        return this.text.toString();
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: Suggestion.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lcom/blacksquircle/ui/language/base/model/Suggestion$Type;", "", "value", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getValue", "()Ljava/lang/String;", "FIELD", "METHOD", "WORD", "NONE", "language-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Type {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ Type[] $VALUES;

        @NotNull
        private final String value;
        public static final Type FIELD = new Type("FIELD", 0, "v");
        public static final Type METHOD = new Type("METHOD", 1, "m");
        public static final Type WORD = new Type("WORD", 2, "w");
        public static final Type NONE = new Type("NONE", 3, "none");

        private static final /* synthetic */ Type[] $values() {
            return new Type[]{FIELD, METHOD, WORD, NONE};
        }

        @NotNull
        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static Type valueOf(String str) {
            return (Type) Enum.valueOf(Type.class, str);
        }

        public static Type[] values() {
            return (Type[]) $VALUES.clone();
        }

        private Type(String str, int i, String str2) {
            this.value = str2;
        }

        @NotNull
        public final String getValue() {
            return this.value;
        }

        static {
            Type[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }
    }
}
