package com.blacksquircle.ui.editorkit.plugin.linenumbers;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Editable;
import android.util.Log;
import android.util.TypedValue;
import com.blacksquircle.ui.editorkit.model.ColorScheme;
import com.blacksquircle.ui.editorkit.plugin.base.EditorPlugin;
import com.blacksquircle.ui.editorkit.utils.ExtensionsKt;
import com.blacksquircle.ui.editorkit.widget.TextProcessor;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LineNumbersPlugin.kt */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 -2\u00020\u0001:\u0001-B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u00192\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010&\u001a\u00020\u00192\u0006\u0010'\u001a\u00020(H\u0016J\u0012\u0010)\u001a\u00020\u00192\b\u0010*\u001a\u0004\u0018\u00010+H\u0016J\b\u0010,\u001a\u00020\u0019H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0011\"\u0004\b\u0016\u0010\u0013R\u000e\u0010\u0017\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/linenumbers/LineNumbersPlugin;", "Lcom/blacksquircle/ui/editorkit/plugin/base/EditorPlugin;", "()V", "gutterCurrentLineNumberPaint", "Landroid/graphics/Paint;", "gutterDigitCount", "", "gutterDividerPaint", "gutterMargin", "getGutterMargin", "()I", "gutterPaint", "gutterTextPaint", "gutterWidth", "highlightCurrentLine", "", "getHighlightCurrentLine", "()Z", "setHighlightCurrentLine", "(Z)V", "lineNumbers", "getLineNumbers", "setLineNumbers", "selectedLinePaint", "afterTextChanged", "", "text", "Landroid/text/Editable;", "drawBehind", "canvas", "Landroid/graphics/Canvas;", "onAttached", "editText", "Lcom/blacksquircle/ui/editorkit/widget/TextProcessor;", "onColorSchemeChanged", "colorScheme", "Lcom/blacksquircle/ui/editorkit/model/ColorScheme;", "onDraw", "setTextSize", "size", "", "setTypeface", "tf", "Landroid/graphics/Typeface;", "updateGutter", "Companion", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class LineNumbersPlugin extends EditorPlugin {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    public static final String PLUGIN_ID = "line-numbers-1141";

    @NotNull
    private final Paint gutterCurrentLineNumberPaint;
    private int gutterDigitCount;

    @NotNull
    private final Paint gutterDividerPaint;

    @NotNull
    private final Paint gutterPaint;

    @NotNull
    private final Paint gutterTextPaint;
    private int gutterWidth;
    private boolean highlightCurrentLine;
    private boolean lineNumbers;

    @NotNull
    private final Paint selectedLinePaint;

    public LineNumbersPlugin() {
        super("line-numbers-1141");
        this.lineNumbers = true;
        this.highlightCurrentLine = true;
        this.selectedLinePaint = new Paint();
        this.gutterPaint = new Paint();
        this.gutterDividerPaint = new Paint();
        this.gutterCurrentLineNumberPaint = new Paint();
        this.gutterTextPaint = new Paint();
    }

    public final boolean getLineNumbers() {
        return this.lineNumbers;
    }

    public final void setLineNumbers(boolean z) {
        this.lineNumbers = z;
    }

    public final boolean getHighlightCurrentLine() {
        return this.highlightCurrentLine;
    }

    public final void setHighlightCurrentLine(boolean z) {
        this.highlightCurrentLine = z;
    }

    private final int getGutterMargin() {
        return (int) (4 * getEditText().getResources().getDisplayMetrics().density);
    }

    public void onAttached(@NotNull TextProcessor editText) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        super.onAttached(editText);
        this.gutterCurrentLineNumberPaint.setTextSize(editText.getTextSize());
        this.gutterTextPaint.setTextSize(editText.getTextSize());
        this.gutterCurrentLineNumberPaint.setTypeface(editText.getTypeface());
        this.gutterTextPaint.setTypeface(editText.getTypeface());
        Log.d("line-numbers-1141", "LineNumbers plugin loaded successfully!");
    }

    public void onColorSchemeChanged(@NotNull ColorScheme colorScheme) {
        Intrinsics.checkNotNullParameter(colorScheme, "colorScheme");
        super.onColorSchemeChanged(colorScheme);
        this.selectedLinePaint.setColor(colorScheme.getSelectedLineColor());
        this.selectedLinePaint.setAntiAlias(false);
        this.selectedLinePaint.setDither(false);
        this.gutterPaint.setColor(colorScheme.getGutterColor());
        this.gutterPaint.setAntiAlias(false);
        this.gutterPaint.setDither(false);
        this.gutterDividerPaint.setColor(colorScheme.getGutterDividerColor());
        this.gutterDividerPaint.setAntiAlias(false);
        this.gutterDividerPaint.setDither(false);
        this.gutterDividerPaint.setStyle(Paint.Style.STROKE);
        this.gutterDividerPaint.setStrokeWidth(2.6f);
        this.gutterCurrentLineNumberPaint.setColor(colorScheme.getGutterCurrentLineNumberColor());
        this.gutterCurrentLineNumberPaint.setAntiAlias(true);
        this.gutterCurrentLineNumberPaint.setDither(false);
        this.gutterCurrentLineNumberPaint.setTextAlign(Paint.Align.RIGHT);
        this.gutterTextPaint.setColor(colorScheme.getGutterTextColor());
        this.gutterTextPaint.setAntiAlias(true);
        this.gutterTextPaint.setDither(false);
        this.gutterTextPaint.setTextAlign(Paint.Align.RIGHT);
    }

    public void drawBehind(@NotNull Canvas canvas) {
        int lineForIndex;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.drawBehind(canvas);
        if (this.highlightCurrentLine && (lineForIndex = getStructure().getLineForIndex(getEditText().getSelectionStart())) == getStructure().getLineForIndex(getEditText().getSelectionEnd())) {
            if (getEditText().getLayout() == null) {
                return;
            }
            int indexForStartOfLine = getStructure().getIndexForStartOfLine(lineForIndex);
            int indexForEndOfLine = getStructure().getIndexForEndOfLine(lineForIndex);
            int lineForOffset = getEditText().getLayout().getLineForOffset(indexForStartOfLine);
            int lineForOffset2 = getEditText().getLayout().getLineForOffset(indexForEndOfLine);
            canvas.drawRect(this.gutterWidth, getEditText().getLayout().getLineTop(lineForOffset) + getEditText().getPaddingTop(), getEditText().getLayout().getWidth() + getEditText().getPaddingLeft() + getEditText().getPaddingRight(), getEditText().getLayout().getLineBottom(lineForOffset2) + getEditText().getPaddingTop(), this.selectedLinePaint);
        }
        updateGutter();
    }

    public void onDraw(@NotNull Canvas canvas) {
        Paint paint;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        if (this.lineNumbers) {
            int lineForIndex = getStructure().getLineForIndex(getEditText().getSelectionStart());
            canvas.drawRect(getEditText().getScrollX(), getEditText().getScrollY(), this.gutterWidth + getEditText().getScrollX(), getEditText().getScrollY() + getEditText().getHeight(), this.gutterPaint);
            int topVisibleLine = ExtensionsKt.getTopVisibleLine(getEditText());
            int i = topVisibleLine >= 2 ? topVisibleLine - 2 : 0;
            int gutterMargin = (this.gutterWidth - (getGutterMargin() / 2)) + getEditText().getScrollX();
            int i2 = -1;
            while (i <= ExtensionsKt.getBottomVisibleLine(getEditText())) {
                if (getEditText().getLayout() == null) {
                    return;
                }
                int lineForIndex2 = getStructure().getLineForIndex(getEditText().getLayout().getLineStart(i));
                if (lineForIndex2 != i2) {
                    String valueOf = String.valueOf(lineForIndex2 + 1);
                    float f = gutterMargin;
                    float lineBaseline = getEditText().getLayout().getLineBaseline(i) + getEditText().getPaddingTop();
                    if (lineForIndex2 == lineForIndex && this.highlightCurrentLine) {
                        paint = this.gutterCurrentLineNumberPaint;
                    } else {
                        paint = this.gutterTextPaint;
                    }
                    canvas.drawText(valueOf, f, lineBaseline, paint);
                }
                i++;
                i2 = lineForIndex2;
            }
            canvas.drawLine(this.gutterWidth + getEditText().getScrollX(), getEditText().getScrollY(), this.gutterWidth + getEditText().getScrollX(), getEditText().getScrollY() + getEditText().getHeight(), this.gutterDividerPaint);
        }
    }

    public void afterTextChanged(@Nullable Editable text) {
        super.afterTextChanged(text);
        updateGutter();
    }

    public void setTextSize(float size) {
        super.setTextSize(size);
        float applyDimension = TypedValue.applyDimension(2, size, getEditText().getResources().getDisplayMetrics());
        this.gutterCurrentLineNumberPaint.setTextSize(applyDimension);
        this.gutterTextPaint.setTextSize(applyDimension);
    }

    public void setTypeface(@Nullable Typeface tf) {
        super.setTypeface(tf);
        this.gutterCurrentLineNumberPaint.setTypeface(tf);
        this.gutterTextPaint.setTypeface(tf);
    }

    private final void updateGutter() {
        if (this.lineNumbers) {
            this.gutterDigitCount = String.valueOf(getStructure().getLineCount()).length();
            float f = 0.0f;
            int i = 0;
            for (int i2 = 0; i2 < 10; i2++) {
                float measureText = getEditText().getPaint().measureText(String.valueOf(i2));
                if (measureText > f) {
                    i = i2;
                    f = measureText;
                }
            }
            int i3 = this.gutterDigitCount;
            if (i3 < 3) {
                i3 = 3;
            }
            StringBuilder sb = new StringBuilder();
            for (int i4 = 0; i4 < i3; i4++) {
                sb.append(String.valueOf(i));
            }
            int measureText2 = (int) getEditText().getPaint().measureText(sb.toString());
            this.gutterWidth = measureText2;
            this.gutterWidth = measureText2 + getGutterMargin();
        }
        if (getEditText().getPaddingStart() != this.gutterWidth + getGutterMargin()) {
            getEditText().setPadding(this.gutterWidth + getGutterMargin(), getGutterMargin(), getEditText().getPaddingEnd(), getEditText().getPaddingBottom());
        }
    }

    /* compiled from: LineNumbersPlugin.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/linenumbers/LineNumbersPlugin$Companion;", "", "()V", "PLUGIN_ID", "", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
