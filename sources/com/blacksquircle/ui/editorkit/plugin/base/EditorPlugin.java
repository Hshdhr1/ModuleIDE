package com.blacksquircle.ui.editorkit.plugin.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.blacksquircle.ui.editorkit.model.ColorScheme;
import com.blacksquircle.ui.editorkit.model.UndoStack;
import com.blacksquircle.ui.editorkit.widget.TextProcessor;
import com.blacksquircle.ui.language.base.Language;
import com.blacksquircle.ui.language.base.model.TextStructure;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: EditorPlugin.kt */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$H\u0016J\u0012\u0010&\u001a\u00020\"2\b\u0010'\u001a\u0004\u0018\u00010(H\u0016J*\u0010)\u001a\u00020\"2\b\u0010'\u001a\u0004\u0018\u00010*2\u0006\u0010+\u001a\u00020$2\u0006\u0010,\u001a\u00020$2\u0006\u0010-\u001a\u00020$H\u0016J\u0010\u0010.\u001a\u00020\"2\u0006\u0010/\u001a\u000200H\u0016J\u0010\u00101\u001a\u00020\"2\u0006\u0010\u000b\u001a\u00020\u0006H\u0016J\u0010\u00102\u001a\u00020\"2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u00103\u001a\u00020\"2\u0006\u0010\u000b\u001a\u00020\u0006H\u0016J\u0010\u00104\u001a\u00020\"2\u0006\u0010/\u001a\u000200H\u0016J\u001a\u00105\u001a\u00020\u000f2\u0006\u00106\u001a\u00020$2\b\u00107\u001a\u0004\u0018\u000108H\u0016J\u001a\u00109\u001a\u00020\u000f2\u0006\u00106\u001a\u00020$2\b\u00107\u001a\u0004\u0018\u000108H\u0016J\u0012\u0010:\u001a\u00020\"2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J0\u0010;\u001a\u00020\"2\u0006\u0010<\u001a\u00020\u000f2\u0006\u0010=\u001a\u00020$2\u0006\u0010>\u001a\u00020$2\u0006\u0010?\u001a\u00020$2\u0006\u0010@\u001a\u00020$H\u0016J\u0018\u0010A\u001a\u00020\"2\u0006\u0010B\u001a\u00020$2\u0006\u0010C\u001a\u00020$H\u0016J(\u0010D\u001a\u00020\"2\u0006\u0010E\u001a\u00020$2\u0006\u0010F\u001a\u00020$2\u0006\u0010G\u001a\u00020$2\u0006\u0010H\u001a\u00020$H\u0016J\u0018\u0010I\u001a\u00020\"2\u0006\u0010J\u001a\u00020$2\u0006\u0010K\u001a\u00020$H\u0016J(\u0010L\u001a\u00020\"2\u0006\u0010M\u001a\u00020$2\u0006\u0010N\u001a\u00020$2\u0006\u0010O\u001a\u00020$2\u0006\u0010P\u001a\u00020$H\u0016J*\u0010Q\u001a\u00020\"2\b\u0010'\u001a\u0004\u0018\u00010*2\u0006\u0010+\u001a\u00020$2\u0006\u0010R\u001a\u00020$2\u0006\u0010,\u001a\u00020$H\u0016J\u0010\u0010S\u001a\u00020\u000f2\u0006\u00107\u001a\u00020TH\u0016J \u0010U\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$2\u0006\u0010V\u001a\u00020$H\u0016J\u0010\u0010W\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0016J\b\u0010X\u001a\u00020YH\u0004J\u0010\u0010Z\u001a\u00020\"2\u0006\u0010'\u001a\u00020*H\u0016J\u0010\u0010[\u001a\u00020\"2\u0006\u0010\\\u001a\u00020]H\u0016J\u0012\u0010^\u001a\u00020\"2\b\u0010_\u001a\u0004\u0018\u00010`H\u0016J\b\u0010a\u001a\u00020\"H\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00068DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0010R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u00128DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00188DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u001c8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020\u00188DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b \u0010\u001a¨\u0006b"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/base/EditorPlugin;", "", "pluginId", "", "(Ljava/lang/String;)V", "_editText", "Lcom/blacksquircle/ui/editorkit/widget/TextProcessor;", "colorScheme", "Lcom/blacksquircle/ui/editorkit/model/ColorScheme;", "getColorScheme", "()Lcom/blacksquircle/ui/editorkit/model/ColorScheme;", "editText", "getEditText", "()Lcom/blacksquircle/ui/editorkit/widget/TextProcessor;", "isAttached", "", "()Z", "language", "Lcom/blacksquircle/ui/language/base/Language;", "getLanguage", "()Lcom/blacksquircle/ui/language/base/Language;", "getPluginId", "()Ljava/lang/String;", "redoStack", "Lcom/blacksquircle/ui/editorkit/model/UndoStack;", "getRedoStack", "()Lcom/blacksquircle/ui/editorkit/model/UndoStack;", "structure", "Lcom/blacksquircle/ui/language/base/model/TextStructure;", "getStructure", "()Lcom/blacksquircle/ui/language/base/model/TextStructure;", "undoStack", "getUndoStack", "addLine", "", "lineNumber", "", "lineStart", "afterTextChanged", "text", "Landroid/text/Editable;", "beforeTextChanged", "", "start", "count", "after", "drawBehind", "canvas", "Landroid/graphics/Canvas;", "onAttached", "onColorSchemeChanged", "onDetached", "onDraw", "onKeyDown", "keyCode", "event", "Landroid/view/KeyEvent;", "onKeyUp", "onLanguageChanged", "onLayout", "changed", "left", "top", "right", "bottom", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "onScrollChanged", "horiz", "vert", "oldHoriz", "oldVert", "onSelectionChanged", "selStart", "selEnd", "onSizeChanged", "w", "h", "oldw", "oldh", "onTextChanged", "before", "onTouchEvent", "Landroid/view/MotionEvent;", "processLine", "lineEnd", "removeLine", "requireContext", "Landroid/content/Context;", "setTextContent", "setTextSize", "size", "", "setTypeface", "tf", "Landroid/graphics/Typeface;", "showDropDown", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public abstract class EditorPlugin {

    @Nullable
    private TextProcessor _editText;

    @NotNull
    private final String pluginId;

    public void addLine(int lineNumber, int lineStart) {
    }

    public void afterTextChanged(@Nullable Editable text) {
    }

    public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
    }

    public void drawBehind(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
    }

    public void onColorSchemeChanged(@NotNull ColorScheme colorScheme) {
        Intrinsics.checkNotNullParameter(colorScheme, "colorScheme");
    }

    public void onDraw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
    }

    public boolean onKeyDown(int keyCode, @Nullable KeyEvent event) {
        return false;
    }

    public boolean onKeyUp(int keyCode, @Nullable KeyEvent event) {
        return false;
    }

    public void onLanguageChanged(@Nullable Language language) {
    }

    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    }

    public void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
    }

    public void onSelectionChanged(int selStart, int selEnd) {
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
    }

    public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
    }

    public boolean onTouchEvent(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        return false;
    }

    public void processLine(int lineNumber, int lineStart, int lineEnd) {
    }

    public void removeLine(int lineNumber) {
    }

    public void setTextContent(@NotNull CharSequence text) {
        Intrinsics.checkNotNullParameter(text, "text");
    }

    public void setTextSize(float size) {
    }

    public void setTypeface(@Nullable Typeface tf) {
    }

    public void showDropDown() {
    }

    public EditorPlugin(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "pluginId");
        this.pluginId = str;
    }

    @NotNull
    public final String getPluginId() {
        return this.pluginId;
    }

    @NotNull
    protected final TextProcessor getEditText() {
        TextProcessor textProcessor = this._editText;
        Intrinsics.checkNotNull(textProcessor);
        return textProcessor;
    }

    protected final boolean isAttached() {
        return this._editText != null;
    }

    @Nullable
    protected final Language getLanguage() {
        return getEditText().getLanguage();
    }

    @NotNull
    protected final ColorScheme getColorScheme() {
        return getEditText().getColorScheme();
    }

    @NotNull
    protected final TextStructure getStructure() {
        return getEditText().getStructure();
    }

    @NotNull
    protected final UndoStack getUndoStack() {
        return getEditText().getUndoStack();
    }

    @NotNull
    protected final UndoStack getRedoStack() {
        return getEditText().getRedoStack();
    }

    public void onAttached(@NotNull TextProcessor editText) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        this._editText = editText;
        onColorSchemeChanged(getColorScheme());
        onLanguageChanged(getLanguage());
    }

    public void onDetached(@NotNull TextProcessor editText) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        this._editText = null;
    }

    @NotNull
    protected final Context requireContext() {
        TextProcessor textProcessor = this._editText;
        Context context = textProcessor != null ? textProcessor.getContext() : null;
        if (context != null) {
            return context;
        }
        throw new IllegalStateException("EditorPlugin " + this + " not attached to a context.");
    }
}
