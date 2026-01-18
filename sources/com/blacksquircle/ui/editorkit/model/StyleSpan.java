package com.blacksquircle.ui.editorkit.model;

import androidx.annotation.ColorInt;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StyleSpan.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0015\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\b\b\u0003\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0005HÆ\u0003J;\u0010\u0016\u001a\u00020\u00002\b\b\u0003\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00052\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u001c"}, d2 = {"Lcom/blacksquircle/ui/editorkit/model/StyleSpan;", "", "color", "", "bold", "", "italic", "underline", "strikethrough", "(IZZZZ)V", "getBold", "()Z", "getColor", "()I", "getItalic", "getStrikethrough", "getUnderline", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "toString", "", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final /* data */ class StyleSpan {
    private final boolean bold;
    private final int color;
    private final boolean italic;
    private final boolean strikethrough;
    private final boolean underline;

    public StyleSpan() {
        this(0, false, false, false, false, 31, null);
    }

    public static /* synthetic */ StyleSpan copy$default(StyleSpan styleSpan, int i, boolean z, boolean z2, boolean z3, boolean z4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = styleSpan.color;
        }
        if ((i2 & 2) != 0) {
            z = styleSpan.bold;
        }
        if ((i2 & 4) != 0) {
            z2 = styleSpan.italic;
        }
        if ((i2 & 8) != 0) {
            z3 = styleSpan.underline;
        }
        if ((i2 & 16) != 0) {
            z4 = styleSpan.strikethrough;
        }
        boolean z5 = z4;
        boolean z6 = z2;
        return styleSpan.copy(i, z, z6, z3, z5);
    }

    /* renamed from: component1, reason: from getter */
    public final int getColor() {
        return this.color;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getBold() {
        return this.bold;
    }

    /* renamed from: component3, reason: from getter */
    public final boolean getItalic() {
        return this.italic;
    }

    /* renamed from: component4, reason: from getter */
    public final boolean getUnderline() {
        return this.underline;
    }

    /* renamed from: component5, reason: from getter */
    public final boolean getStrikethrough() {
        return this.strikethrough;
    }

    @NotNull
    public final StyleSpan copy(@ColorInt int color, boolean bold, boolean italic, boolean underline, boolean strikethrough) {
        return new StyleSpan(color, bold, italic, underline, strikethrough);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StyleSpan)) {
            return false;
        }
        StyleSpan styleSpan = (StyleSpan) other;
        return this.color == styleSpan.color && this.bold == styleSpan.bold && this.italic == styleSpan.italic && this.underline == styleSpan.underline && this.strikethrough == styleSpan.strikethrough;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int i = this.color * 31;
        boolean z = this.bold;
        int i2 = z;
        if (z != 0) {
            i2 = 1;
        }
        int i3 = (i + i2) * 31;
        boolean z2 = this.italic;
        int i4 = z2;
        if (z2 != 0) {
            i4 = 1;
        }
        int i5 = (i3 + i4) * 31;
        boolean z3 = this.underline;
        int i6 = z3;
        if (z3 != 0) {
            i6 = 1;
        }
        int i7 = (i5 + i6) * 31;
        boolean z4 = this.strikethrough;
        return i7 + (z4 ? 1 : z4 ? 1 : 0);
    }

    @NotNull
    public String toString() {
        return "StyleSpan(color=" + this.color + ", bold=" + this.bold + ", italic=" + this.italic + ", underline=" + this.underline + ", strikethrough=" + this.strikethrough + ")";
    }

    public StyleSpan(@ColorInt int i, boolean z, boolean z2, boolean z3, boolean z4) {
        this.color = i;
        this.bold = z;
        this.italic = z2;
        this.underline = z3;
        this.strikethrough = z4;
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException: Attempt to invoke virtual method 'java.util.List jadx.core.dex.instructions.args.SSAVar.getPhiList()' on a null object reference
        	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(Unknown Source:37)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(Unknown Source:18)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(SourceFile:12)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(Unknown Source:53)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(Unknown Source:0)
        */
    public /* synthetic */ StyleSpan(int r2, boolean r3, boolean r4, boolean r5, boolean r6, int r7, kotlin.jvm.internal.DefaultConstructorMarker r8) {
        /*
            r1 = this;
            r8 = r7 & 1
            if (r8 == 0) goto L5
            r2 = -1
        L5:
            r8 = r7 & 2
            r0 = 0
            if (r8 == 0) goto Lb
            r3 = 0
        Lb:
            r8 = r7 & 4
            if (r8 == 0) goto L10
            r4 = 0
        L10:
            r8 = r7 & 8
            if (r8 == 0) goto L15
            r5 = 0
        L15:
            r7 = r7 & 16
            if (r7 == 0) goto L20
            r8 = 0
            r6 = r4
            r7 = r5
            r4 = r2
            r5 = r3
            r3 = r1
            goto L26
        L20:
            r8 = r6
            r7 = r5
            r5 = r3
            r6 = r4
            r3 = r1
            r4 = r2
        L26:
            r3.<init>(r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blacksquircle.ui.editorkit.model.StyleSpan.<init>(int, boolean, boolean, boolean, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final int getColor() {
        return this.color;
    }

    public final boolean getBold() {
        return this.bold;
    }

    public final boolean getItalic() {
        return this.italic;
    }

    public final boolean getUnderline() {
        return this.underline;
    }

    public final boolean getStrikethrough() {
        return this.strikethrough;
    }
}
