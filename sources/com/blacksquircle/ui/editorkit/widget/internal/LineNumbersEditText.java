package com.blacksquircle.ui.editorkit.widget.internal;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.widget.Toast;
import com.blacksquircle.ui.language.base.model.TextStructure;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LineNumbersEditText.kt */
@Metadata(d1 = {"\u0000K\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\r\n\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f*\u0001\u001c\b&\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0007H\u0014J\u0012\u0010\"\u001a\u00020\u001f2\b\u0010#\u001a\u0004\u0018\u00010$H\u0014J*\u0010%\u001a\u00020\u001f2\b\u0010#\u001a\u0004\u0018\u00010\u001a2\u0006\u0010&\u001a\u00020\u00072\u0006\u0010'\u001a\u00020\u00072\u0006\u0010(\u001a\u00020\u0007H\u0014J*\u0010)\u001a\u00020\u001f2\b\u0010#\u001a\u0004\u0018\u00010\u001a2\u0006\u0010&\u001a\u00020\u00072\u0006\u0010*\u001a\u00020\u00072\u0006\u0010'\u001a\u00020\u0007H\u0014J \u0010+\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u00072\u0006\u0010,\u001a\u00020\u0007H\u0014J\u0010\u0010-\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0007H\u0014J \u0010.\u001a\u00020\u001f2\u0006\u0010/\u001a\u00020\u00072\u0006\u00100\u001a\u00020\u00072\u0006\u00101\u001a\u00020\u001aH\u0002J\u0010\u00102\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u001aH\u0016R$\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR$\u0010\u0010\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000fR\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001d¨\u00063"}, d2 = {"Lcom/blacksquircle/ui/editorkit/widget/internal/LineNumbersEditText;", "Lcom/blacksquircle/ui/editorkit/widget/internal/ScrollableEditText;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "value", "", "readOnly", "getReadOnly", "()Z", "setReadOnly", "(Z)V", "softKeyboard", "getSoftKeyboard", "setSoftKeyboard", "structure", "Lcom/blacksquircle/ui/language/base/model/TextStructure;", "getStructure", "()Lcom/blacksquircle/ui/language/base/model/TextStructure;", "textChangeEnd", "textChangeStart", "textChangedNewText", "", "textWatcher", "com/blacksquircle/ui/editorkit/widget/internal/LineNumbersEditText$textWatcher$1", "Lcom/blacksquircle/ui/editorkit/widget/internal/LineNumbersEditText$textWatcher$1;", "addLine", "", "lineNumber", "lineStart", "doAfterTextChanged", "text", "Landroid/text/Editable;", "doBeforeTextChanged", "start", "count", "after", "doOnTextChanged", "before", "processLine", "lineEnd", "removeLine", "replaceText", "newStart", "newEnd", "newText", "setTextContent", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public abstract class LineNumbersEditText extends ScrollableEditText {
    private boolean readOnly;
    private boolean softKeyboard;

    @NotNull
    private final TextStructure structure;
    private int textChangeEnd;
    private int textChangeStart;

    @NotNull
    private CharSequence textChangedNewText;

    @NotNull
    private final LineNumbersEditText$textWatcher$1 textWatcher;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public LineNumbersEditText(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public LineNumbersEditText(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    protected void doAfterTextChanged(@Nullable Editable text) {
    }

    protected void processLine(int lineNumber, int lineStart, int lineEnd) {
    }

    public /* synthetic */ LineNumbersEditText(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 16842859 : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public LineNumbersEditText(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.structure = new TextStructure(new SpannableStringBuilder());
        this.textWatcher = new LineNumbersEditText$textWatcher$1(this);
        this.textChangedNewText = "";
        setGravity(8388659);
        setInputType(655361);
    }

    public final boolean getSoftKeyboard() {
        return this.softKeyboard;
    }

    public final void setSoftKeyboard(boolean z) {
        this.softKeyboard = z;
        setImeOptions(z ? 0 : 268435456);
    }

    public final boolean getReadOnly() {
        return this.readOnly;
    }

    public final void setReadOnly(boolean z) {
        this.readOnly = z;
        setFocusable(!z);
        setFocusableInTouchMode(!z);
    }

    @NotNull
    public final TextStructure getStructure() {
        return this.structure;
    }

    protected void doBeforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
        this.textChangeStart = start;
        this.textChangeEnd = start + count;
    }

    protected void doOnTextChanged(@Nullable CharSequence text, int start, int before, int count) {
        CharSequence charSequence;
        if (text == null || (charSequence = text.subSequence(start, count + start)) == null) {
        }
        this.textChangedNewText = charSequence;
        replaceText(this.textChangeStart, this.textChangeEnd, charSequence);
        int lineForIndex = this.structure.getLineForIndex(this.textChangeStart);
        int lineForIndex2 = this.structure.getLineForIndex(this.textChangeStart + this.textChangedNewText.length());
        if (lineForIndex > lineForIndex2) {
            return;
        }
        while (true) {
            int indexForStartOfLine = this.structure.getIndexForStartOfLine(lineForIndex);
            int indexForEndOfLine = this.structure.getIndexForEndOfLine(lineForIndex);
            if (indexForStartOfLine <= indexForEndOfLine) {
                processLine(lineForIndex, indexForStartOfLine, indexForEndOfLine);
            }
            if (lineForIndex == lineForIndex2) {
                return;
            } else {
                lineForIndex++;
            }
        }
    }

    protected void addLine(int lineNumber, int lineStart) {
        this.structure.add(lineNumber, lineStart);
    }

    protected void removeLine(int lineNumber) {
        this.structure.remove(lineNumber);
    }

    public void setTextContent(@NotNull CharSequence text) {
        Intrinsics.checkNotNullParameter(text, "text");
        removeTextChangedListener(this.textWatcher);
        try {
            setText(text);
            replaceText(0, this.structure.getText().length(), text);
        } catch (Throwable th) {
            th.printStackTrace();
            setText("");
            replaceText(0, this.structure.getText().length(), "");
            Toast.makeText(getContext(), th.getMessage(), 1).show();
        }
        addTextChangedListener(this.textWatcher);
    }

    private final void replaceText(int newStart, int newEnd, CharSequence newText) {
        if (newStart < 0) {
            newStart = 0;
        }
        if (newEnd > this.structure.getText().length()) {
            newEnd = this.structure.getText().length();
        }
        int length = newText.length() - (newEnd - newStart);
        int lineForIndex = this.structure.getLineForIndex(newStart);
        for (int i = newStart; i < newEnd; i++) {
            if (this.structure.getText().charAt(i) == '\n') {
                removeLine(lineForIndex + 1);
            }
        }
        TextStructure textStructure = this.structure;
        textStructure.shiftIndexes(textStructure.getLineForIndex(newStart) + 1, length);
        int length2 = newText.length();
        for (int i2 = 0; i2 < length2; i2++) {
            if (newText.charAt(i2) == '\n') {
                int i3 = newStart + i2;
                addLine(this.structure.getLineForIndex(i3) + 1, i3 + 1);
            }
        }
        Editable text = this.structure.getText();
        if (text instanceof Editable) {
            text.replace(newStart, newEnd, newText);
        }
    }
}
