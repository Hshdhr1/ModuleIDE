package androidx.core.graphics;

import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.os.Build;
import androidx.core.util.Pair;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public final class PaintCompat {
    private static final String EM_STRING = "m";
    private static final String TOFU_STRING = "\udfffd";
    private static final ThreadLocal sRectThreadLocal = new ThreadLocal();

    public static boolean hasGlyph(Paint paint, String string) {
        if (Build.VERSION.SDK_INT >= 23) {
            return paint.hasGlyph(string);
        }
        int length = string.length();
        if (length == 1 && Character.isWhitespace(string.charAt(0))) {
            return true;
        }
        float missingGlyphWidth = paint.measureText("\udfffd");
        float emGlyphWidth = paint.measureText("m");
        float width = paint.measureText(string);
        if (width == 0.0f) {
            return false;
        }
        if (string.codePointCount(0, string.length()) > 1) {
            if (width > 2.0f * emGlyphWidth) {
                return false;
            }
            float sumWidth = 0.0f;
            int i = 0;
            while (i < length) {
                int charCount = Character.charCount(string.codePointAt(i));
                sumWidth += paint.measureText(string, i, i + charCount);
                i += charCount;
            }
            if (width >= sumWidth) {
                return false;
            }
        }
        if (width != missingGlyphWidth) {
            return true;
        }
        Pair<Rect, Rect> rects = obtainEmptyRects();
        paint.getTextBounds("\udfffd", 0, "\udfffd".length(), (Rect) rects.first);
        paint.getTextBounds(string, 0, length, (Rect) rects.second);
        return true ^ ((Rect) rects.first).equals(rects.second);
    }

    public static boolean setBlendMode(Paint paint, BlendModeCompat blendMode) {
        if (Build.VERSION.SDK_INT >= 29) {
            paint.setBlendMode(blendMode != null ? BlendModeUtils.obtainBlendModeFromCompat(blendMode) : null);
            return true;
        }
        if (blendMode != null) {
            PorterDuff.Mode mode = BlendModeUtils.obtainPorterDuffFromCompat(blendMode);
            paint.setXfermode(mode != null ? new PorterDuffXfermode(mode) : null);
            return mode != null;
        }
        paint.setXfermode((Xfermode) null);
        return true;
    }

    private static Pair obtainEmptyRects() {
        ThreadLocal threadLocal = sRectThreadLocal;
        Pair<Rect, Rect> rects = (Pair) threadLocal.get();
        if (rects == null) {
            Pair<Rect, Rect> rects2 = new Pair(new Rect(), new Rect());
            threadLocal.set(rects2);
            return rects2;
        }
        ((Rect) rects.first).setEmpty();
        ((Rect) rects.second).setEmpty();
        return rects;
    }

    private PaintCompat() {
    }
}
