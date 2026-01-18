package com.google.android.material.resources;

import android.graphics.Typeface;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public final class CancelableFontCallback extends TextAppearanceFontCallback {
    private final ApplyFont applyFont;
    private boolean cancelled;
    private final Typeface fallbackFont;

    public interface ApplyFont {
        void apply(Typeface typeface);
    }

    public CancelableFontCallback(ApplyFont applyFont, Typeface fallbackFont) {
        this.fallbackFont = fallbackFont;
        this.applyFont = applyFont;
    }

    public void onFontRetrieved(Typeface font, boolean fontResolvedSynchronously) {
        updateIfNotCancelled(font);
    }

    public void onFontRetrievalFailed(int reason) {
        updateIfNotCancelled(this.fallbackFont);
    }

    public void cancel() {
        this.cancelled = true;
    }

    private void updateIfNotCancelled(Typeface updatedFont) {
        if (!this.cancelled) {
            this.applyFont.apply(updatedFont);
        }
    }
}
