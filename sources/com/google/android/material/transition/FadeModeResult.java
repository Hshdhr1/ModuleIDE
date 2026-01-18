package com.google.android.material.transition;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
class FadeModeResult {
    final int endAlpha;
    final boolean endOnTop;
    final int startAlpha;

    static FadeModeResult startOnTop(int startAlpha, int endAlpha) {
        return new FadeModeResult(startAlpha, endAlpha, false);
    }

    static FadeModeResult endOnTop(int startAlpha, int endAlpha) {
        return new FadeModeResult(startAlpha, endAlpha, true);
    }

    private FadeModeResult(int startAlpha, int endAlpha, boolean endOnTop) {
        this.startAlpha = startAlpha;
        this.endAlpha = endAlpha;
        this.endOnTop = endOnTop;
    }
}
