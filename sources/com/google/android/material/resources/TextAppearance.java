package com.google.android.material.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.text.TextPaint;
import android.util.Log;
import androidx.core.content.res.ResourcesCompat;
import com.google.android.material.R;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class TextAppearance {
    private static final String TAG = "TextAppearance";
    private static final int TYPEFACE_MONOSPACE = 3;
    private static final int TYPEFACE_SANS = 1;
    private static final int TYPEFACE_SERIF = 2;
    private Typeface font;
    public final String fontFamily;
    private final int fontFamilyResourceId;
    private boolean fontResolved = false;
    public final boolean hasLetterSpacing;
    public final float letterSpacing;
    public final ColorStateList shadowColor;
    public final float shadowDx;
    public final float shadowDy;
    public final float shadowRadius;
    public final boolean textAllCaps;
    private ColorStateList textColor;
    public final ColorStateList textColorHint;
    public final ColorStateList textColorLink;
    private float textSize;
    public final int textStyle;
    public final int typeface;

    static /* synthetic */ Typeface access$000(TextAppearance x0) {
        return x0.font;
    }

    static /* synthetic */ Typeface access$002(TextAppearance x0, Typeface x1) {
        x0.font = x1;
        return x1;
    }

    static /* synthetic */ boolean access$102(TextAppearance x0, boolean x1) {
        x0.fontResolved = x1;
        return x1;
    }

    public TextAppearance(Context context, int id) {
        TypedArray a = context.obtainStyledAttributes(id, R.styleable.TextAppearance);
        setTextSize(a.getDimension(R.styleable.TextAppearance_android_textSize, 0.0f));
        setTextColor(MaterialResources.getColorStateList(context, a, R.styleable.TextAppearance_android_textColor));
        this.textColorHint = MaterialResources.getColorStateList(context, a, R.styleable.TextAppearance_android_textColorHint);
        this.textColorLink = MaterialResources.getColorStateList(context, a, R.styleable.TextAppearance_android_textColorLink);
        this.textStyle = a.getInt(R.styleable.TextAppearance_android_textStyle, 0);
        this.typeface = a.getInt(R.styleable.TextAppearance_android_typeface, 1);
        int fontFamilyIndex = MaterialResources.getIndexWithValue(a, R.styleable.TextAppearance_fontFamily, R.styleable.TextAppearance_android_fontFamily);
        this.fontFamilyResourceId = a.getResourceId(fontFamilyIndex, 0);
        this.fontFamily = a.getString(fontFamilyIndex);
        this.textAllCaps = a.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
        this.shadowColor = MaterialResources.getColorStateList(context, a, R.styleable.TextAppearance_android_shadowColor);
        this.shadowDx = a.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0f);
        this.shadowDy = a.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0f);
        this.shadowRadius = a.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0f);
        a.recycle();
        if (Build.VERSION.SDK_INT >= 21) {
            TypedArray a2 = context.obtainStyledAttributes(id, R.styleable.MaterialTextAppearance);
            this.hasLetterSpacing = a2.hasValue(R.styleable.MaterialTextAppearance_android_letterSpacing);
            this.letterSpacing = a2.getFloat(R.styleable.MaterialTextAppearance_android_letterSpacing, 0.0f);
            a2.recycle();
            return;
        }
        this.hasLetterSpacing = false;
        this.letterSpacing = 0.0f;
    }

    public Typeface getFont(Context context) {
        if (this.fontResolved) {
            return this.font;
        }
        if (!context.isRestricted()) {
            try {
                Typeface font = ResourcesCompat.getFont(context, this.fontFamilyResourceId);
                this.font = font;
                if (font != null) {
                    this.font = Typeface.create(font, this.textStyle);
                }
            } catch (UnsupportedOperationException e) {
            } catch (Exception e2) {
                Log.d("TextAppearance", "Error loading font " + this.fontFamily, e2);
            } catch (Resources.NotFoundException e3) {
            }
        }
        createFallbackFont();
        this.fontResolved = true;
        return this.font;
    }

    public void getFontAsync(Context context, TextAppearanceFontCallback callback) {
        if (shouldLoadFontSynchronously(context)) {
            getFont(context);
        } else {
            createFallbackFont();
        }
        int i = this.fontFamilyResourceId;
        if (i == 0) {
            this.fontResolved = true;
        }
        if (this.fontResolved) {
            callback.onFontRetrieved(this.font, true);
            return;
        }
        try {
            ResourcesCompat.getFont(context, i, new 1(callback), (Handler) null);
        } catch (Resources.NotFoundException e) {
            this.fontResolved = true;
            callback.onFontRetrievalFailed(1);
        } catch (Exception e2) {
            Log.d("TextAppearance", "Error loading font " + this.fontFamily, e2);
            this.fontResolved = true;
            callback.onFontRetrievalFailed(-3);
        }
    }

    class 1 extends ResourcesCompat.FontCallback {
        final /* synthetic */ TextAppearanceFontCallback val$callback;

        1(TextAppearanceFontCallback textAppearanceFontCallback) {
            this.val$callback = textAppearanceFontCallback;
        }

        public void onFontRetrieved(Typeface typeface) {
            TextAppearance textAppearance = TextAppearance.this;
            TextAppearance.access$002(textAppearance, Typeface.create(typeface, textAppearance.textStyle));
            TextAppearance.access$102(TextAppearance.this, true);
            this.val$callback.onFontRetrieved(TextAppearance.access$000(TextAppearance.this), false);
        }

        public void onFontRetrievalFailed(int reason) {
            TextAppearance.access$102(TextAppearance.this, true);
            this.val$callback.onFontRetrievalFailed(reason);
        }
    }

    public void getFontAsync(Context context, TextPaint textPaint, TextAppearanceFontCallback callback) {
        updateTextPaintMeasureState(context, textPaint, getFallbackFont());
        getFontAsync(context, new 2(context, textPaint, callback));
    }

    class 2 extends TextAppearanceFontCallback {
        final /* synthetic */ TextAppearanceFontCallback val$callback;
        final /* synthetic */ Context val$context;
        final /* synthetic */ TextPaint val$textPaint;

        2(Context context, TextPaint textPaint, TextAppearanceFontCallback textAppearanceFontCallback) {
            this.val$context = context;
            this.val$textPaint = textPaint;
            this.val$callback = textAppearanceFontCallback;
        }

        public void onFontRetrieved(Typeface typeface, boolean fontResolvedSynchronously) {
            TextAppearance.this.updateTextPaintMeasureState(this.val$context, this.val$textPaint, typeface);
            this.val$callback.onFontRetrieved(typeface, fontResolvedSynchronously);
        }

        public void onFontRetrievalFailed(int i) {
            this.val$callback.onFontRetrievalFailed(i);
        }
    }

    public Typeface getFallbackFont() {
        createFallbackFont();
        return this.font;
    }

    private void createFallbackFont() {
        String str;
        if (this.font == null && (str = this.fontFamily) != null) {
            this.font = Typeface.create(str, this.textStyle);
        }
        if (this.font == null) {
            switch (this.typeface) {
                case 1:
                    this.font = Typeface.SANS_SERIF;
                    break;
                case 2:
                    this.font = Typeface.SERIF;
                    break;
                case 3:
                    this.font = Typeface.MONOSPACE;
                    break;
                default:
                    this.font = Typeface.DEFAULT;
                    break;
            }
            this.font = Typeface.create(this.font, this.textStyle);
        }
    }

    public void updateDrawState(Context context, TextPaint textPaint, TextAppearanceFontCallback callback) {
        int i;
        int i2;
        updateMeasureState(context, textPaint, callback);
        ColorStateList colorStateList = this.textColor;
        if (colorStateList != null) {
            i = colorStateList.getColorForState(textPaint.drawableState, this.textColor.getDefaultColor());
        } else {
            i = -16777216;
        }
        textPaint.setColor(i);
        float f = this.shadowRadius;
        float f2 = this.shadowDx;
        float f3 = this.shadowDy;
        ColorStateList colorStateList2 = this.shadowColor;
        if (colorStateList2 != null) {
            i2 = colorStateList2.getColorForState(textPaint.drawableState, this.shadowColor.getDefaultColor());
        } else {
            i2 = 0;
        }
        textPaint.setShadowLayer(f, f2, f3, i2);
    }

    public void updateMeasureState(Context context, TextPaint textPaint, TextAppearanceFontCallback callback) {
        if (shouldLoadFontSynchronously(context)) {
            updateTextPaintMeasureState(context, textPaint, getFont(context));
        } else {
            getFontAsync(context, textPaint, callback);
        }
    }

    public void updateTextPaintMeasureState(Context context, TextPaint textPaint, Typeface typeface) {
        Typeface boldTypeface = TypefaceUtils.maybeCopyWithFontWeightAdjustment(context, typeface);
        if (boldTypeface != null) {
            typeface = boldTypeface;
        }
        textPaint.setTypeface(typeface);
        int fake = this.textStyle & (typeface.getStyle() ^ (-1));
        textPaint.setFakeBoldText((fake & 1) != 0);
        textPaint.setTextSkewX((fake & 2) != 0 ? -0.25f : 0.0f);
        textPaint.setTextSize(this.textSize);
        if (Build.VERSION.SDK_INT >= 21 && this.hasLetterSpacing) {
            textPaint.setLetterSpacing(this.letterSpacing);
        }
    }

    public ColorStateList getTextColor() {
        return this.textColor;
    }

    public void setTextColor(ColorStateList textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return this.textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    private boolean shouldLoadFontSynchronously(Context context) {
        Typeface typeface;
        if (TextAppearanceConfig.shouldLoadFontSynchronously()) {
            return true;
        }
        int i = this.fontFamilyResourceId;
        if (i != 0) {
            typeface = ResourcesCompat.getCachedFont(context, i);
        } else {
            typeface = null;
        }
        return typeface != null;
    }
}
