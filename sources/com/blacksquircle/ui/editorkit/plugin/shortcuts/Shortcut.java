package com.blacksquircle.ui.editorkit.plugin.shortcuts;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Shortcut.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0007HÆ\u0003J1\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00032\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0007HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u0019"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/shortcuts/Shortcut;", "", "ctrl", "", "shift", "alt", "keyCode", "", "(ZZZI)V", "getAlt", "()Z", "getCtrl", "getKeyCode", "()I", "getShift", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "toString", "", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final /* data */ class Shortcut {
    private final boolean alt;
    private final boolean ctrl;
    private final int keyCode;
    private final boolean shift;

    public static /* synthetic */ Shortcut copy$default(Shortcut shortcut, boolean z, boolean z2, boolean z3, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z = shortcut.ctrl;
        }
        if ((i2 & 2) != 0) {
            z2 = shortcut.shift;
        }
        if ((i2 & 4) != 0) {
            z3 = shortcut.alt;
        }
        if ((i2 & 8) != 0) {
            i = shortcut.keyCode;
        }
        return shortcut.copy(z, z2, z3, i);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getCtrl() {
        return this.ctrl;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getShift() {
        return this.shift;
    }

    /* renamed from: component3, reason: from getter */
    public final boolean getAlt() {
        return this.alt;
    }

    /* renamed from: component4, reason: from getter */
    public final int getKeyCode() {
        return this.keyCode;
    }

    @NotNull
    public final Shortcut copy(boolean ctrl, boolean shift, boolean alt, int keyCode) {
        return new Shortcut(ctrl, shift, alt, keyCode);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Shortcut)) {
            return false;
        }
        Shortcut shortcut = (Shortcut) other;
        return this.ctrl == shortcut.ctrl && this.shift == shortcut.shift && this.alt == shortcut.alt && this.keyCode == shortcut.keyCode;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r2v0, types: [boolean] */
    public int hashCode() {
        boolean z = this.ctrl;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int i = r0 * 31;
        ?? r2 = this.shift;
        int i2 = r2;
        if (r2 != 0) {
            i2 = 1;
        }
        int i3 = (i + i2) * 31;
        boolean z2 = this.alt;
        return ((i3 + (z2 ? 1 : z2 ? 1 : 0)) * 31) + this.keyCode;
    }

    @NotNull
    public String toString() {
        return "Shortcut(ctrl=" + this.ctrl + ", shift=" + this.shift + ", alt=" + this.alt + ", keyCode=" + this.keyCode + ")";
    }

    public Shortcut(boolean z, boolean z2, boolean z3, int i) {
        this.ctrl = z;
        this.shift = z2;
        this.alt = z3;
        this.keyCode = i;
    }

    public final boolean getCtrl() {
        return this.ctrl;
    }

    public final boolean getShift() {
        return this.shift;
    }

    public final boolean getAlt() {
        return this.alt;
    }

    public final int getKeyCode() {
        return this.keyCode;
    }
}
