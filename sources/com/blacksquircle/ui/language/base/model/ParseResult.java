package com.blacksquircle.ui.language.base.model;

import com.blacksquircle.ui.language.base.exception.ParseException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ParseResult.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000b\u0010\u0007\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0015\u0010\b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/blacksquircle/ui/language/base/model/ParseResult;", "", "exception", "Lcom/blacksquircle/ui/language/base/exception/ParseException;", "(Lcom/blacksquircle/ui/language/base/exception/ParseException;)V", "getException", "()Lcom/blacksquircle/ui/language/base/exception/ParseException;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "language-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes60.dex */
public final /* data */ class ParseResult {

    @Nullable
    private final ParseException exception;

    public static /* synthetic */ ParseResult copy$default(ParseResult parseResult, ParseException parseException, int i, Object obj) {
        if ((i & 1) != 0) {
            parseException = parseResult.exception;
        }
        return parseResult.copy(parseException);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final ParseException getException() {
        return this.exception;
    }

    @NotNull
    public final ParseResult copy(@Nullable ParseException exception) {
        return new ParseResult(exception);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ParseResult) && Intrinsics.areEqual(this.exception, ((ParseResult) other).exception);
    }

    public int hashCode() {
        ParseException parseException = this.exception;
        if (parseException == null) {
            return 0;
        }
        return parseException.hashCode();
    }

    @NotNull
    public String toString() {
        return "ParseResult(exception=" + this.exception + ")";
    }

    public ParseResult(@Nullable ParseException parseException) {
        this.exception = parseException;
    }

    @Nullable
    public final ParseException getException() {
        return this.exception;
    }
}
