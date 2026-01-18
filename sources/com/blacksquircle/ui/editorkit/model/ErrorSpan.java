package com.blacksquircle.ui.editorkit.model;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: ErrorSpan.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\r\n\u0002\b\u0004\u0018\u00002\u00020\u0001B#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J`\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/blacksquircle/ui/editorkit/model/ErrorSpan;", "Landroid/text/style/LineBackgroundSpan;", "lineWidth", "", "waveSize", "color", "", "(FFI)V", "drawBackground", "", "canvas", "Landroid/graphics/Canvas;", "paint", "Landroid/graphics/Paint;", "left", "right", "top", "baseline", "bottom", "text", "", "start", "end", "lineNumber", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class ErrorSpan implements LineBackgroundSpan {
    private final int color;
    private final float lineWidth;
    private final float waveSize;

    public ErrorSpan() {
        this(0.0f, 0.0f, 0, 7, null);
    }

    public ErrorSpan(float f, float f2, int i) {
        this.lineWidth = f;
        this.waveSize = f2;
        this.color = i;
    }

    public /* synthetic */ ErrorSpan(float f, float f2, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? (1 * Resources.getSystem().getDisplayMetrics().density) + 0.5f : f, (i2 & 2) != 0 ? (3 * Resources.getSystem().getDisplayMetrics().density) + 0.5f : f2, (i2 & 4) != 0 ? -65536 : i);
    }

    public void drawBackground(@NotNull Canvas canvas, @NotNull Paint paint, int left, int right, int top, int baseline, int bottom, @NotNull CharSequence text, int start, int end, int lineNumber) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(paint, "paint");
        Intrinsics.checkNotNullParameter(text, "text");
        float measureText = paint.measureText(text, start, end);
        Paint paint2 = new Paint(paint);
        paint2.setColor(this.color);
        paint2.setStrokeWidth(this.lineWidth);
        float f = this.waveSize * 2;
        float f2 = left;
        float f3 = f2;
        while (f3 < f2 + measureText) {
            float f4 = bottom;
            float f5 = this.waveSize;
            canvas.drawLine(f3, f4, f3 + f5, f4 - f5, paint2);
            float f6 = this.waveSize;
            float f7 = f3;
            float f8 = f7 + f;
            canvas.drawLine(f7 + f6, f4 - f6, f8, f4, paint2);
            f3 = f8;
        }
    }
}
