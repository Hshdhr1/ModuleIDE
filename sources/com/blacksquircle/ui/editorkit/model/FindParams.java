package com.blacksquircle.ui.editorkit.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FindParams.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J1\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00052\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u0019"}, d2 = {"Lcom/blacksquircle/ui/editorkit/model/FindParams;", "", "query", "", "regex", "", "matchCase", "wordsOnly", "(Ljava/lang/String;ZZZ)V", "getMatchCase", "()Z", "getQuery", "()Ljava/lang/String;", "getRegex", "getWordsOnly", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final /* data */ class FindParams {
    private final boolean matchCase;

    @NotNull
    private final String query;
    private final boolean regex;
    private final boolean wordsOnly;

    public FindParams() {
        this(null, false, false, false, 15, null);
    }

    public static /* synthetic */ FindParams copy$default(FindParams findParams, String str, boolean z, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = findParams.query;
        }
        if ((i & 2) != 0) {
            z = findParams.regex;
        }
        if ((i & 4) != 0) {
            z2 = findParams.matchCase;
        }
        if ((i & 8) != 0) {
            z3 = findParams.wordsOnly;
        }
        return findParams.copy(str, z, z2, z3);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getQuery() {
        return this.query;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getRegex() {
        return this.regex;
    }

    /* renamed from: component3, reason: from getter */
    public final boolean getMatchCase() {
        return this.matchCase;
    }

    /* renamed from: component4, reason: from getter */
    public final boolean getWordsOnly() {
        return this.wordsOnly;
    }

    @NotNull
    public final FindParams copy(@NotNull String query, boolean regex, boolean matchCase, boolean wordsOnly) {
        Intrinsics.checkNotNullParameter(query, "query");
        return new FindParams(query, regex, matchCase, wordsOnly);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FindParams)) {
            return false;
        }
        FindParams findParams = (FindParams) other;
        return Intrinsics.areEqual(this.query, findParams.query) && this.regex == findParams.regex && this.matchCase == findParams.matchCase && this.wordsOnly == findParams.wordsOnly;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int hashCode = this.query.hashCode() * 31;
        boolean z = this.regex;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (hashCode + i) * 31;
        boolean z2 = this.matchCase;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.wordsOnly;
        return i4 + (z3 ? 1 : z3 ? 1 : 0);
    }

    @NotNull
    public String toString() {
        return "FindParams(query=" + this.query + ", regex=" + this.regex + ", matchCase=" + this.matchCase + ", wordsOnly=" + this.wordsOnly + ")";
    }

    public FindParams(@NotNull String str, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(str, "query");
        this.query = str;
        this.regex = z;
        this.matchCase = z2;
        this.wordsOnly = z3;
    }

    public /* synthetic */ FindParams(String str, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? false : z, (i & 4) != 0 ? false : z2, (i & 8) != 0 ? false : z3);
    }

    @NotNull
    public final String getQuery() {
        return this.query;
    }

    public final boolean getRegex() {
        return this.regex;
    }

    public final boolean getMatchCase() {
        return this.matchCase;
    }

    public final boolean getWordsOnly() {
        return this.wordsOnly;
    }
}
