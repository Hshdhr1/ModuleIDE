package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TextAppearanceFontCallback;
import java.lang.ref.WeakReference;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class TextDrawableHelper {
    private TextAppearance textAppearance;
    private float textWidth;
    private final TextPaint textPaint = new TextPaint(1);
    private final TextAppearanceFontCallback fontCallback = new 1();
    private boolean textWidthDirty = true;
    private WeakReference delegate = new WeakReference((Object) null);

    public interface TextDrawableDelegate {
        int[] getState();

        boolean onStateChange(int[] iArr);

        void onTextSizeChange();
    }

    static /* synthetic */ boolean access$002(TextDrawableHelper x0, boolean x1) {
        x0.textWidthDirty = x1;
        return x1;
    }

    static /* synthetic */ WeakReference access$100(TextDrawableHelper x0) {
        return x0.delegate;
    }

    class 1 extends TextAppearanceFontCallback {
        1() {
        }

        public void onFontRetrieved(Typeface typeface, boolean fontResolvedSynchronously) {
            if (fontResolvedSynchronously) {
                return;
            }
            TextDrawableHelper.access$002(TextDrawableHelper.this, true);
            TextDrawableDelegate textDrawableDelegate = (TextDrawableDelegate) TextDrawableHelper.access$100(TextDrawableHelper.this).get();
            if (textDrawableDelegate != null) {
                textDrawableDelegate.onTextSizeChange();
            }
        }

        public void onFontRetrievalFailed(int reason) {
            TextDrawableHelper.access$002(TextDrawableHelper.this, true);
            TextDrawableDelegate textDrawableDelegate = (TextDrawableDelegate) TextDrawableHelper.access$100(TextDrawableHelper.this).get();
            if (textDrawableDelegate != null) {
                textDrawableDelegate.onTextSizeChange();
            }
        }
    }

    public TextDrawableHelper(TextDrawableDelegate delegate) {
        setDelegate(delegate);
    }

    public void setDelegate(TextDrawableDelegate delegate) {
        this.delegate = new WeakReference(delegate);
    }

    public TextPaint getTextPaint() {
        return this.textPaint;
    }

    public void setTextWidthDirty(boolean dirty) {
        this.textWidthDirty = dirty;
    }

    public boolean isTextWidthDirty() {
        return this.textWidthDirty;
    }

    public float getTextWidth(String text) {
        if (!this.textWidthDirty) {
            return this.textWidth;
        }
        float calculateTextWidth = calculateTextWidth(text);
        this.textWidth = calculateTextWidth;
        this.textWidthDirty = false;
        return calculateTextWidth;
    }

    private float calculateTextWidth(CharSequence charSequence) {
        if (charSequence == null) {
            return 0.0f;
        }
        return this.textPaint.measureText(charSequence, 0, charSequence.length());
    }

    public TextAppearance getTextAppearance() {
        return this.textAppearance;
    }

    public void setTextAppearance(TextAppearance textAppearance, Context context) {
        if (this.textAppearance != textAppearance) {
            this.textAppearance = textAppearance;
            if (textAppearance != null) {
                textAppearance.updateMeasureState(context, this.textPaint, this.fontCallback);
                TextDrawableDelegate textDrawableDelegate = (TextDrawableDelegate) this.delegate.get();
                if (textDrawableDelegate != null) {
                    this.textPaint.drawableState = textDrawableDelegate.getState();
                }
                textAppearance.updateDrawState(context, this.textPaint, this.fontCallback);
                this.textWidthDirty = true;
            }
            TextDrawableDelegate textDrawableDelegate2 = (TextDrawableDelegate) this.delegate.get();
            if (textDrawableDelegate2 != null) {
                textDrawableDelegate2.onTextSizeChange();
                textDrawableDelegate2.onStateChange(textDrawableDelegate2.getState());
            }
        }
    }

    public void updateTextPaintDrawState(Context context) {
        this.textAppearance.updateDrawState(context, this.textPaint, this.fontCallback);
    }
}
