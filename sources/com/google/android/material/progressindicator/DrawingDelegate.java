package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
abstract class DrawingDelegate {
    protected DrawableWithAnimatedVisibilityChange drawable;
    BaseProgressIndicatorSpec spec;

    abstract void adjustCanvas(Canvas canvas, float f);

    abstract void fillIndicator(Canvas canvas, Paint paint, float f, float f2, int i);

    abstract void fillTrack(Canvas canvas, Paint paint);

    abstract int getPreferredHeight();

    abstract int getPreferredWidth();

    public DrawingDelegate(BaseProgressIndicatorSpec baseProgressIndicatorSpec) {
        this.spec = baseProgressIndicatorSpec;
    }

    protected void registerDrawable(DrawableWithAnimatedVisibilityChange drawable) {
        this.drawable = drawable;
    }

    void validateSpecAndAdjustCanvas(Canvas canvas, float trackThicknessFraction) {
        this.spec.validateSpec();
        adjustCanvas(canvas, trackThicknessFraction);
    }
}
