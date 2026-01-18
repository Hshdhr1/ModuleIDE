package com.blacksquircle.ui.editorkit.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TextChange.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0006HÆ\u0003J'\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u0006HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u001b"}, d2 = {"Lcom/blacksquircle/ui/editorkit/model/TextChange;", "", "newText", "", "oldText", "start", "", "(Ljava/lang/String;Ljava/lang/String;I)V", "getNewText", "()Ljava/lang/String;", "setNewText", "(Ljava/lang/String;)V", "getOldText", "setOldText", "getStart", "()I", "setStart", "(I)V", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final /* data */ class TextChange {

    @NotNull
    private String newText;

    @NotNull
    private String oldText;
    private int start;

    public static /* synthetic */ TextChange copy$default(TextChange textChange, String str, String str2, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = textChange.newText;
        }
        if ((i2 & 2) != 0) {
            str2 = textChange.oldText;
        }
        if ((i2 & 4) != 0) {
            i = textChange.start;
        }
        return textChange.copy(str, str2, i);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getNewText() {
        return this.newText;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getOldText() {
        return this.oldText;
    }

    /* renamed from: component3, reason: from getter */
    public final int getStart() {
        return this.start;
    }

    @NotNull
    public final TextChange copy(@NotNull String newText, @NotNull String oldText, int start) {
        Intrinsics.checkNotNullParameter(newText, "newText");
        Intrinsics.checkNotNullParameter(oldText, "oldText");
        return new TextChange(newText, oldText, start);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TextChange)) {
            return false;
        }
        TextChange textChange = (TextChange) other;
        return Intrinsics.areEqual(this.newText, textChange.newText) && Intrinsics.areEqual(this.oldText, textChange.oldText) && this.start == textChange.start;
    }

    public int hashCode() {
        return (((this.newText.hashCode() * 31) + this.oldText.hashCode()) * 31) + this.start;
    }

    @NotNull
    public String toString() {
        return "TextChange(newText=" + this.newText + ", oldText=" + this.oldText + ", start=" + this.start + ")";
    }

    public TextChange(@NotNull String str, @NotNull String str2, int i) {
        Intrinsics.checkNotNullParameter(str, "newText");
        Intrinsics.checkNotNullParameter(str2, "oldText");
        this.newText = str;
        this.oldText = str2;
        this.start = i;
    }

    @NotNull
    public final String getNewText() {
        return this.newText;
    }

    public final void setNewText(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.newText = str;
    }

    @NotNull
    public final String getOldText() {
        return this.oldText;
    }

    public final void setOldText(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.oldText = str;
    }

    public final int getStart() {
        return this.start;
    }

    public final void setStart(int i) {
        this.start = i;
    }
}
