package com.blacksquircle.ui.editorkit.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import org.jetbrains.annotations.NotNull;

/* compiled from: UndoStack.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0015\b\u0012\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005B\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u000f\u001a\u00020\u0000J\u0011\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\bH\u0086\u0002J\u0006\u0010\u0012\u001a\u00020\u0004J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0004J\u0006\u0010\u0016\u001a\u00020\u0014J\b\u0010\u0017\u001a\u00020\u000eH\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/blacksquircle/ui/editorkit/model/UndoStack;", "", "stack", "", "Lcom/blacksquircle/ui/editorkit/model/TextChange;", "(Ljava/util/List;)V", "()V", "currentSize", "", "size", "getSize", "()I", "", "canUndo", "", "clone", "get", "index", "pop", "push", "", "textChange", "removeAll", "removeLast", "Companion", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class UndoStack {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    public static final int MAX_SIZE = Integer.MAX_VALUE;
    private int currentSize;

    @NotNull
    private List stack;

    public UndoStack() {
        this.stack = new ArrayList();
    }

    /* compiled from: UndoStack.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/blacksquircle/ui/editorkit/model/UndoStack$Companion;", "", "()V", "MAX_SIZE", "", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final int getSize() {
        return this.stack.size();
    }

    private UndoStack(List list) {
        this();
        this.stack = CollectionsKt.toMutableList((Collection) list);
    }

    @NotNull
    public final TextChange get(int index) {
        return (TextChange) this.stack.get(index);
    }

    @NotNull
    public final TextChange pop() {
        TextChange textChange = (TextChange) this.stack.get(getSize() - 1);
        this.stack.remove(getSize() - 1);
        this.currentSize -= textChange.getNewText().length() + textChange.getOldText().length();
        return textChange;
    }

    public final void push(@NotNull TextChange textChange) {
        Intrinsics.checkNotNullParameter(textChange, "textChange");
        int length = textChange.getNewText().length() + textChange.getOldText().length();
        if (length < Integer.MAX_VALUE) {
            if (getSize() > 0) {
                boolean z = true;
                TextChange textChange2 = (TextChange) this.stack.get(getSize() - 1);
                if (textChange.getOldText().length() == 0 && textChange.getNewText().length() == 1 && textChange2.getOldText().length() == 0) {
                    if (textChange2.getStart() + textChange2.getNewText().length() != textChange.getStart()) {
                        this.stack.add(textChange);
                    } else if (CharsKt.isWhitespace(textChange.getNewText().charAt(0))) {
                        char[] charArray = textChange2.getNewText().toCharArray();
                        Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
                        for (char c : charArray) {
                            if (!CharsKt.isWhitespace(c)) {
                                z = false;
                            }
                        }
                        if (z) {
                            textChange2.setNewText(textChange2.getNewText() + textChange.getNewText());
                        } else {
                            this.stack.add(textChange);
                        }
                    } else if (Character.isLetterOrDigit(textChange.getNewText().charAt(0))) {
                        char[] charArray2 = textChange2.getNewText().toCharArray();
                        Intrinsics.checkNotNullExpressionValue(charArray2, "this as java.lang.String).toCharArray()");
                        for (char c2 : charArray2) {
                            if (!Character.isLetterOrDigit(c2)) {
                                z = false;
                            }
                        }
                        if (z) {
                            textChange2.setNewText(textChange2.getNewText() + textChange.getNewText());
                        } else {
                            this.stack.add(textChange);
                        }
                    } else {
                        this.stack.add(textChange);
                    }
                } else if (textChange.getOldText().length() != 1 || textChange.getNewText().length() > 0 || textChange2.getNewText().length() > 0) {
                    this.stack.add(textChange);
                } else if (textChange2.getStart() - 1 != textChange.getStart()) {
                    this.stack.add(textChange);
                } else if (CharsKt.isWhitespace(textChange.getOldText().charAt(0))) {
                    char[] charArray3 = textChange2.getOldText().toCharArray();
                    Intrinsics.checkNotNullExpressionValue(charArray3, "this as java.lang.String).toCharArray()");
                    for (char c3 : charArray3) {
                        if (!CharsKt.isWhitespace(c3)) {
                            z = false;
                        }
                    }
                    if (z) {
                        textChange2.setOldText(textChange.getOldText() + textChange2.getOldText());
                        textChange2.setStart(textChange2.getStart() - textChange.getOldText().length());
                    } else {
                        this.stack.add(textChange);
                    }
                } else if (Character.isLetterOrDigit(textChange.getOldText().charAt(0))) {
                    char[] charArray4 = textChange2.getOldText().toCharArray();
                    Intrinsics.checkNotNullExpressionValue(charArray4, "this as java.lang.String).toCharArray()");
                    for (char c4 : charArray4) {
                        if (!Character.isLetterOrDigit(c4)) {
                            z = false;
                        }
                    }
                    if (z) {
                        textChange2.setOldText(textChange.getOldText() + textChange2.getOldText());
                        textChange2.setStart(textChange2.getStart() - textChange.getOldText().length());
                    } else {
                        this.stack.add(textChange);
                    }
                } else {
                    this.stack.add(textChange);
                }
            } else {
                this.stack.add(textChange);
            }
            this.currentSize += length;
            while (this.currentSize > Integer.MAX_VALUE && removeLast()) {
            }
            return;
        }
        removeAll();
    }

    public final void removeAll() {
        this.currentSize = 0;
        this.stack.clear();
    }

    public final boolean canUndo() {
        return getSize() > 0;
    }

    @NotNull
    public final UndoStack clone() {
        return new UndoStack(this.stack);
    }

    private final boolean removeLast() {
        if (getSize() <= 0) {
            return false;
        }
        TextChange textChange = (TextChange) this.stack.get(0);
        this.stack.remove(0);
        this.currentSize -= textChange.getNewText().length() + textChange.getOldText().length();
        return true;
    }
}
