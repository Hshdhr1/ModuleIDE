package com.blacksquircle.ui.language.base.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SyntaxHighlightResult.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J'\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001a"}, d2 = {"Lcom/blacksquircle/ui/language/base/model/SyntaxHighlightResult;", "", "tokenType", "Lcom/blacksquircle/ui/language/base/model/TokenType;", "start", "", "end", "(Lcom/blacksquircle/ui/language/base/model/TokenType;II)V", "getEnd", "()I", "setEnd", "(I)V", "getStart", "setStart", "getTokenType", "()Lcom/blacksquircle/ui/language/base/model/TokenType;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "", "language-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes60.dex */
public final /* data */ class SyntaxHighlightResult {
    private int end;
    private int start;

    @NotNull
    private final TokenType tokenType;

    public static /* synthetic */ SyntaxHighlightResult copy$default(SyntaxHighlightResult syntaxHighlightResult, TokenType tokenType, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            tokenType = syntaxHighlightResult.tokenType;
        }
        if ((i3 & 2) != 0) {
            i = syntaxHighlightResult.start;
        }
        if ((i3 & 4) != 0) {
            i2 = syntaxHighlightResult.end;
        }
        return syntaxHighlightResult.copy(tokenType, i, i2);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final TokenType getTokenType() {
        return this.tokenType;
    }

    /* renamed from: component2, reason: from getter */
    public final int getStart() {
        return this.start;
    }

    /* renamed from: component3, reason: from getter */
    public final int getEnd() {
        return this.end;
    }

    @NotNull
    public final SyntaxHighlightResult copy(@NotNull TokenType tokenType, int start, int end) {
        Intrinsics.checkNotNullParameter(tokenType, "tokenType");
        return new SyntaxHighlightResult(tokenType, start, end);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SyntaxHighlightResult)) {
            return false;
        }
        SyntaxHighlightResult syntaxHighlightResult = (SyntaxHighlightResult) other;
        return this.tokenType == syntaxHighlightResult.tokenType && this.start == syntaxHighlightResult.start && this.end == syntaxHighlightResult.end;
    }

    public int hashCode() {
        return (((this.tokenType.hashCode() * 31) + this.start) * 31) + this.end;
    }

    @NotNull
    public String toString() {
        return "SyntaxHighlightResult(tokenType=" + this.tokenType + ", start=" + this.start + ", end=" + this.end + ")";
    }

    public SyntaxHighlightResult(@NotNull TokenType tokenType, int i, int i2) {
        Intrinsics.checkNotNullParameter(tokenType, "tokenType");
        this.tokenType = tokenType;
        this.start = i;
        this.end = i2;
    }

    @NotNull
    public final TokenType getTokenType() {
        return this.tokenType;
    }

    public final int getStart() {
        return this.start;
    }

    public final void setStart(int i) {
        this.start = i;
    }

    public final int getEnd() {
        return this.end;
    }

    public final void setEnd(int i) {
        this.end = i;
    }
}
