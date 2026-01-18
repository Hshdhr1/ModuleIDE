package com.blacksquircle.ui.editorkit.widget.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import com.blacksquircle.ui.editorkit.ExtensionsKt;
import com.blacksquircle.ui.editorkit.model.TextChange;
import com.blacksquircle.ui.editorkit.model.UndoStack;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: UndoRedoEditText.kt */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\n\b&\u0018\u00002\u00020\u0001:\u0001+B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0006\u0010\u001c\u001a\u00020\nJ\u0006\u0010\u001d\u001a\u00020\nJ\u0006\u0010\u001e\u001a\u00020\u001fJ*\u0010 \u001a\u00020\u001f2\b\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u00072\u0006\u0010%\u001a\u00020\u0007H\u0014J*\u0010&\u001a\u00020\u001f2\b\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020\u00072\u0006\u0010'\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u0007H\u0014J\u0006\u0010(\u001a\u00020\u001fJ\u0010\u0010)\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\"H\u0016J\u0006\u0010*\u001a\u00020\u001fR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0014\"\u0004\b\u001b\u0010\u0016¨\u0006,"}, d2 = {"Lcom/blacksquircle/ui/editorkit/widget/internal/UndoRedoEditText;", "Lcom/blacksquircle/ui/editorkit/widget/internal/LineNumbersEditText;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "isDoingUndoRedo", "", "onUndoRedoChangedListener", "Lcom/blacksquircle/ui/editorkit/widget/internal/UndoRedoEditText$OnUndoRedoChangedListener;", "getOnUndoRedoChangedListener", "()Lcom/blacksquircle/ui/editorkit/widget/internal/UndoRedoEditText$OnUndoRedoChangedListener;", "setOnUndoRedoChangedListener", "(Lcom/blacksquircle/ui/editorkit/widget/internal/UndoRedoEditText$OnUndoRedoChangedListener;)V", "redoStack", "Lcom/blacksquircle/ui/editorkit/model/UndoStack;", "getRedoStack", "()Lcom/blacksquircle/ui/editorkit/model/UndoStack;", "setRedoStack", "(Lcom/blacksquircle/ui/editorkit/model/UndoStack;)V", "textLastChange", "Lcom/blacksquircle/ui/editorkit/model/TextChange;", "undoStack", "getUndoStack", "setUndoStack", "canRedo", "canUndo", "clearUndoHistory", "", "doBeforeTextChanged", "text", "", "start", "count", "after", "doOnTextChanged", "before", "redo", "setTextContent", "undo", "OnUndoRedoChangedListener", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public abstract class UndoRedoEditText extends LineNumbersEditText {
    private boolean isDoingUndoRedo;

    @Nullable
    private OnUndoRedoChangedListener onUndoRedoChangedListener;

    @NotNull
    private UndoStack redoStack;

    @Nullable
    private TextChange textLastChange;

    @NotNull
    private UndoStack undoStack;

    /* compiled from: UndoRedoEditText.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lcom/blacksquircle/ui/editorkit/widget/internal/UndoRedoEditText$OnUndoRedoChangedListener;", "", "onUndoRedoChanged", "", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface OnUndoRedoChangedListener {
        void onUndoRedoChanged();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public UndoRedoEditText(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public UndoRedoEditText(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ UndoRedoEditText(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 16842859 : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public UndoRedoEditText(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.undoStack = new UndoStack();
        this.redoStack = new UndoStack();
    }

    @NotNull
    public final UndoStack getUndoStack() {
        return this.undoStack;
    }

    public final void setUndoStack(@NotNull UndoStack undoStack) {
        Intrinsics.checkNotNullParameter(undoStack, "<set-?>");
        this.undoStack = undoStack;
    }

    @NotNull
    public final UndoStack getRedoStack() {
        return this.redoStack;
    }

    public final void setRedoStack(@NotNull UndoStack undoStack) {
        Intrinsics.checkNotNullParameter(undoStack, "<set-?>");
        this.redoStack = undoStack;
    }

    @Nullable
    public final OnUndoRedoChangedListener getOnUndoRedoChangedListener() {
        return this.onUndoRedoChangedListener;
    }

    public final void setOnUndoRedoChangedListener(@Nullable OnUndoRedoChangedListener onUndoRedoChangedListener) {
        this.onUndoRedoChangedListener = onUndoRedoChangedListener;
    }

    protected void doBeforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
        super.doBeforeTextChanged(text, start, count, after);
        if (this.isDoingUndoRedo) {
            return;
        }
        if (count < Integer.MAX_VALUE) {
            r0 = new TextChange("", String.valueOf(text != null ? text.subSequence(start, count + start) : null), start);
        } else {
            this.undoStack.removeAll();
            this.redoStack.removeAll();
        }
        this.textLastChange = r0;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0077  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void doOnTextChanged(@org.jetbrains.annotations.Nullable java.lang.CharSequence r3, int r4, int r5, int r6) {
        /*
            r2 = this;
            super.doOnTextChanged(r3, r4, r5, r6)
            boolean r5 = r2.isDoingUndoRedo
            if (r5 != 0) goto Lb4
            com.blacksquircle.ui.editorkit.model.TextChange r5 = r2.textLastChange
            if (r5 == 0) goto Lb4
            r0 = 2147483647(0x7fffffff, float:NaN)
            r1 = 0
            if (r6 >= r0) goto La1
            if (r5 != 0) goto L14
            goto L24
        L14:
            if (r3 == 0) goto L1c
            int r6 = r6 + r4
            java.lang.CharSequence r3 = r3.subSequence(r4, r6)
            goto L1d
        L1c:
            r3 = r1
        L1d:
            java.lang.String r3 = java.lang.String.valueOf(r3)
            r5.setNewText(r3)
        L24:
            com.blacksquircle.ui.editorkit.model.TextChange r3 = r2.textLastChange
            if (r3 == 0) goto Lab
            int r3 = r3.getStart()
            if (r4 != r3) goto Lab
            com.blacksquircle.ui.editorkit.model.TextChange r3 = r2.textLastChange
            r4 = 1
            r5 = 0
            if (r3 == 0) goto L4a
            java.lang.String r3 = r3.getOldText()
            if (r3 == 0) goto L4a
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3
            int r3 = r3.length()
            if (r3 <= 0) goto L44
            r3 = 1
            goto L45
        L44:
            r3 = 0
        L45:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
            goto L4b
        L4a:
            r3 = r1
        L4b:
            kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
            boolean r3 = r3.booleanValue()
            if (r3 != 0) goto L77
            com.blacksquircle.ui.editorkit.model.TextChange r3 = r2.textLastChange
            if (r3 == 0) goto L6d
            java.lang.String r3 = r3.getNewText()
            if (r3 == 0) goto L6d
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3
            int r3 = r3.length()
            if (r3 <= 0) goto L67
            goto L68
        L67:
            r4 = 0
        L68:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r4)
            goto L6e
        L6d:
            r3 = r1
        L6e:
            kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
            boolean r3 = r3.booleanValue()
            if (r3 == 0) goto Lab
        L77:
            com.blacksquircle.ui.editorkit.model.TextChange r3 = r2.textLastChange
            if (r3 == 0) goto L80
            java.lang.String r3 = r3.getOldText()
            goto L81
        L80:
            r3 = r1
        L81:
            com.blacksquircle.ui.editorkit.model.TextChange r4 = r2.textLastChange
            if (r4 == 0) goto L8a
            java.lang.String r4 = r4.getNewText()
            goto L8b
        L8a:
            r4 = r1
        L8b:
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r4)
            if (r3 != 0) goto Lab
            com.blacksquircle.ui.editorkit.model.UndoStack r3 = r2.undoStack
            com.blacksquircle.ui.editorkit.model.TextChange r4 = r2.textLastChange
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            r3.push(r4)
            com.blacksquircle.ui.editorkit.model.UndoStack r3 = r2.redoStack
            r3.removeAll()
            goto Lab
        La1:
            com.blacksquircle.ui.editorkit.model.UndoStack r3 = r2.undoStack
            r3.removeAll()
            com.blacksquircle.ui.editorkit.model.UndoStack r3 = r2.redoStack
            r3.removeAll()
        Lab:
            r2.textLastChange = r1
            com.blacksquircle.ui.editorkit.widget.internal.UndoRedoEditText$OnUndoRedoChangedListener r3 = r2.onUndoRedoChangedListener
            if (r3 == 0) goto Lb4
            r3.onUndoRedoChanged()
        Lb4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blacksquircle.ui.editorkit.widget.internal.UndoRedoEditText.doOnTextChanged(java.lang.CharSequence, int, int, int):void");
    }

    public void setTextContent(@NotNull CharSequence text) {
        Intrinsics.checkNotNullParameter(text, "text");
        super.setTextContent(text);
        OnUndoRedoChangedListener onUndoRedoChangedListener = this.onUndoRedoChangedListener;
        if (onUndoRedoChangedListener != null) {
            onUndoRedoChangedListener.onUndoRedoChanged();
        }
    }

    public final void clearUndoHistory() {
        this.undoStack.removeAll();
        this.redoStack.removeAll();
        OnUndoRedoChangedListener onUndoRedoChangedListener = this.onUndoRedoChangedListener;
        if (onUndoRedoChangedListener != null) {
            onUndoRedoChangedListener.onUndoRedoChanged();
        }
    }

    public final boolean canUndo() {
        return this.undoStack.canUndo();
    }

    public final boolean canRedo() {
        return this.redoStack.canUndo();
    }

    public final void undo() {
        TextChange pop = this.undoStack.pop();
        if (pop.getStart() >= 0) {
            this.isDoingUndoRedo = true;
            if (pop.getStart() > getText().length()) {
                pop.setStart(getText().length());
            }
            int start = pop.getStart() + pop.getNewText().length();
            if (start < 0) {
                start = 0;
            }
            if (start > getText().length()) {
                start = getText().length();
            }
            this.redoStack.push(pop);
            getText().replace(pop.getStart(), start, pop.getOldText());
            ExtensionsKt.setSelectionIndex((EditText) this, pop.getStart() + pop.getOldText().length());
            this.isDoingUndoRedo = false;
        } else {
            this.undoStack.removeAll();
        }
        OnUndoRedoChangedListener onUndoRedoChangedListener = this.onUndoRedoChangedListener;
        if (onUndoRedoChangedListener != null) {
            onUndoRedoChangedListener.onUndoRedoChanged();
        }
    }

    public final void redo() {
        TextChange pop = this.redoStack.pop();
        if (pop.getStart() >= 0) {
            this.isDoingUndoRedo = true;
            this.undoStack.push(pop);
            getText().replace(pop.getStart(), pop.getStart() + pop.getOldText().length(), pop.getNewText());
            ExtensionsKt.setSelectionIndex((EditText) this, pop.getStart() + pop.getNewText().length());
            this.isDoingUndoRedo = false;
        } else {
            this.undoStack.removeAll();
        }
        OnUndoRedoChangedListener onUndoRedoChangedListener = this.onUndoRedoChangedListener;
        if (onUndoRedoChangedListener != null) {
            onUndoRedoChangedListener.onUndoRedoChanged();
        }
    }
}
