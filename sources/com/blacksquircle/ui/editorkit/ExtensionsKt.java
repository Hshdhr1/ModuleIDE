package com.blacksquircle.ui.editorkit;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.EditText;
import androidx.core.content.ContextCompat;
import com.blacksquircle.ui.editorkit.exception.LineException;
import com.blacksquircle.ui.editorkit.widget.TextProcessor;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

/* compiled from: Extensions.kt */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\r\u001a\n\u0010\n\u001a\u00020\u000b*\u00020\u0002\u001a\n\u0010\f\u001a\u00020\u000b*\u00020\u0002\u001a\n\u0010\r\u001a\u00020\u000b*\u00020\u000e\u001a\n\u0010\u000f\u001a\u00020\u000b*\u00020\u000e\u001a\u0012\u0010\u0010\u001a\u00020\u000b*\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0007\u001a\n\u0010\u0012\u001a\u00020\u0013*\u00020\u000e\u001a\u0012\u0010\u0014\u001a\u00020\u000b*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0016\u001a\n\u0010\u0017\u001a\u00020\u000b*\u00020\u000e\u001a\n\u0010\u0018\u001a\u00020\u0013*\u00020\u000e\u001a\n\u0010\u0019\u001a\u00020\u0013*\u00020\u000e\u001a\n\u0010\u001a\u001a\u00020\u000b*\u00020\u000e\u001a\n\u0010\u001b\u001a\u00020\u000b*\u00020\u0002\u001a\n\u0010\u001c\u001a\u00020\u000b*\u00020\u000e\u001a\u0012\u0010\u001d\u001a\u00020\u000b*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u0007\u001a\u001a\u0010\u001f\u001a\u00020\u000b*\u00020\u00022\u0006\u0010 \u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0007\u001a\n\u0010\"\u001a\u00020\u000b*\u00020\u000e\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"!\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006#"}, d2 = {"selectedText", "", "Landroid/widget/EditText;", "getSelectedText", "(Landroid/widget/EditText;)Ljava/lang/String;", "selectionPair", "Lkotlin/Pair;", "", "getSelectionPair", "(Landroid/widget/EditText;)Lkotlin/Pair;", "copy", "", "cut", "deleteLine", "Lcom/blacksquircle/ui/editorkit/widget/TextProcessor;", "duplicateLine", "gotoLine", "lineNumber", "hasPrimaryClip", "", "insert", "delta", "", "moveCaretToEndOfLine", "moveCaretToNextWord", "moveCaretToPrevWord", "moveCaretToStartOfLine", "paste", "selectLine", "setSelectionIndex", "index", "setSelectionRange", "start", "end", "toggleCase", "editorkit_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Extensions.kt\ncom/blacksquircle/ui/editorkit/ExtensionsKt\n+ 2 Context.kt\nandroidx/core/content/ContextKt\n+ 3 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,205:1\n31#2:206\n31#2:207\n31#2:208\n31#2:211\n1064#3,2:209\n*S KotlinDebug\n*F\n+ 1 Extensions.kt\ncom/blacksquircle/ui/editorkit/ExtensionsKt\n*L\n46#1:206\n59#1:207\n69#1:208\n203#1:211\n123#1:209,2\n*E\n"})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class ExtensionsKt {
    @NotNull
    public static final Pair getSelectionPair(@NotNull EditText editText) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        return selectionStart > selectionEnd ? TuplesKt.to(Integer.valueOf(selectionEnd), Integer.valueOf(selectionStart)) : TuplesKt.to(Integer.valueOf(selectionStart), Integer.valueOf(selectionEnd));
    }

    @NotNull
    public static final String getSelectedText(@NotNull EditText editText) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        Pair selectionPair = getSelectionPair(editText);
        int intValue = ((Number) selectionPair.component1()).intValue();
        int intValue2 = ((Number) selectionPair.component2()).intValue();
        CharSequence text = editText.getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        return text.subSequence(intValue, intValue2).toString();
    }

    public static final void insert(@NotNull EditText editText, @NotNull CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        Intrinsics.checkNotNullParameter(charSequence, "delta");
        Pair selectionPair = getSelectionPair(editText);
        editText.getText().replace(((Number) selectionPair.component1()).intValue(), ((Number) selectionPair.component2()).intValue(), charSequence);
    }

    public static final void cut(@NotNull EditText editText) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        try {
            Context context = editText.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            ClipboardManager clipboardManager = (ClipboardManager) ContextCompat.getSystemService(context, ClipboardManager.class);
            ClipData newPlainText = ClipData.newPlainText((CharSequence) null, getSelectedText(editText));
            if (clipboardManager != null) {
                clipboardManager.setPrimaryClip(newPlainText);
            }
            Pair selectionPair = getSelectionPair(editText);
            editText.getText().replace(((Number) selectionPair.component1()).intValue(), ((Number) selectionPair.component2()).intValue(), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void copy(@NotNull EditText editText) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        try {
            Context context = editText.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            ClipboardManager clipboardManager = (ClipboardManager) ContextCompat.getSystemService(context, ClipboardManager.class);
            ClipData newPlainText = ClipData.newPlainText((CharSequence) null, getSelectedText(editText));
            if (clipboardManager != null) {
                clipboardManager.setPrimaryClip(newPlainText);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void paste(@NotNull EditText editText) {
        ClipData primaryClip;
        Intrinsics.checkNotNullParameter(editText, "<this>");
        try {
            Context context = editText.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            ClipboardManager clipboardManager = (ClipboardManager) ContextCompat.getSystemService(context, ClipboardManager.class);
            ClipData.Item itemAt = (clipboardManager == null || (primaryClip = clipboardManager.getPrimaryClip()) == null) ? null : primaryClip.getItemAt(0);
            CharSequence coerceToText = itemAt != null ? itemAt.coerceToText(editText.getContext()) : null;
            Pair selectionPair = getSelectionPair(editText);
            editText.getText().replace(((Number) selectionPair.component1()).intValue(), ((Number) selectionPair.component2()).intValue(), coerceToText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void setSelectionRange(@NotNull EditText editText, int i, int i2) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        if (i > editText.getText().length()) {
            i = editText.getText().length();
        }
        if (i2 > editText.getText().length()) {
            i2 = editText.getText().length();
        }
        editText.setSelection(i, i2);
    }

    public static final void setSelectionIndex(@NotNull EditText editText, int i) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        if (i > editText.getText().length()) {
            i = editText.getText().length();
        }
        editText.setSelection(i);
    }

    public static final void selectLine(@NotNull TextProcessor textProcessor) {
        Intrinsics.checkNotNullParameter(textProcessor, "<this>");
        int lineForIndex = textProcessor.getStructure().getLineForIndex(textProcessor.getSelectionStart());
        setSelectionRange((EditText) textProcessor, textProcessor.getStructure().getIndexForStartOfLine(lineForIndex), textProcessor.getStructure().getIndexForEndOfLine(lineForIndex));
    }

    public static final void deleteLine(@NotNull TextProcessor textProcessor) {
        Intrinsics.checkNotNullParameter(textProcessor, "<this>");
        int lineForIndex = textProcessor.getStructure().getLineForIndex(textProcessor.getSelectionStart());
        textProcessor.getText().delete(textProcessor.getStructure().getIndexForStartOfLine(lineForIndex), textProcessor.getStructure().getIndexForEndOfLine(lineForIndex));
    }

    public static final void duplicateLine(@NotNull TextProcessor textProcessor) {
        Intrinsics.checkNotNullParameter(textProcessor, "<this>");
        if (textProcessor.hasSelection()) {
            EditText editText = (EditText) textProcessor;
            Pair selectionPair = getSelectionPair(editText);
            int intValue = ((Number) selectionPair.component1()).intValue();
            int intValue2 = ((Number) selectionPair.component2()).intValue();
            textProcessor.getText().replace(intValue, intValue2, getSelectedText(editText) + getSelectedText(editText));
            setSelectionRange(editText, intValue2, getSelectedText(editText).length() + intValue2);
            return;
        }
        int lineForIndex = textProcessor.getStructure().getLineForIndex(textProcessor.getSelectionStart());
        int indexForStartOfLine = textProcessor.getStructure().getIndexForStartOfLine(lineForIndex);
        int indexForEndOfLine = textProcessor.getStructure().getIndexForEndOfLine(lineForIndex);
        CharSequence subSequence = textProcessor.getText().subSequence(indexForStartOfLine, indexForEndOfLine);
        textProcessor.getText().insert(indexForEndOfLine, "\n" + subSequence);
    }

    public static final void toggleCase(@NotNull TextProcessor textProcessor) {
        String lowerCase;
        Intrinsics.checkNotNullParameter(textProcessor, "<this>");
        EditText editText = (EditText) textProcessor;
        Pair selectionPair = getSelectionPair(editText);
        int intValue = ((Number) selectionPair.component1()).intValue();
        int intValue2 = ((Number) selectionPair.component2()).intValue();
        CharSequence selectedText = getSelectedText(editText);
        int i = 0;
        while (true) {
            if (i < selectedText.length()) {
                if (!Character.isUpperCase(selectedText.charAt(i))) {
                    lowerCase = getSelectedText(editText).toUpperCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toUpperCase(Locale.ROOT)");
                    break;
                }
                i++;
            } else {
                lowerCase = getSelectedText(editText).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                break;
            }
        }
        textProcessor.getText().replace(intValue, intValue2, (CharSequence) lowerCase);
        setSelectionRange(editText, intValue, lowerCase.length() + intValue);
    }

    public static final void moveCaretToStartOfLine(@NotNull TextProcessor textProcessor) {
        Intrinsics.checkNotNullParameter(textProcessor, "<this>");
        setSelectionIndex((EditText) textProcessor, textProcessor.getStructure().getIndexForStartOfLine(textProcessor.getStructure().getLineForIndex(textProcessor.getSelectionStart())));
    }

    public static final void moveCaretToEndOfLine(@NotNull TextProcessor textProcessor) {
        Intrinsics.checkNotNullParameter(textProcessor, "<this>");
        setSelectionIndex((EditText) textProcessor, textProcessor.getStructure().getIndexForEndOfLine(textProcessor.getStructure().getLineForIndex(textProcessor.getSelectionEnd())));
    }

    public static final boolean moveCaretToPrevWord(@NotNull TextProcessor textProcessor) {
        Intrinsics.checkNotNullParameter(textProcessor, "<this>");
        if (textProcessor.getSelectionStart() > 0) {
            char charAt = textProcessor.getText().charAt(textProcessor.getSelectionStart() - 1);
            if (Character.isLetterOrDigit(charAt) || charAt == '_') {
                int selectionStart = textProcessor.getSelectionStart();
                while (true) {
                    if (-1 >= selectionStart) {
                        break;
                    }
                    char charAt2 = textProcessor.getText().charAt(selectionStart - 1);
                    if (!Character.isLetterOrDigit(charAt2) && charAt2 != '_') {
                        setSelectionIndex((EditText) textProcessor, selectionStart);
                        break;
                    }
                    selectionStart--;
                }
            } else {
                for (int selectionStart2 = textProcessor.getSelectionStart(); -1 < selectionStart2; selectionStart2--) {
                    char charAt3 = textProcessor.getText().charAt(selectionStart2 - 1);
                    if (Character.isLetterOrDigit(charAt3) || charAt3 == '_') {
                        setSelectionIndex((EditText) textProcessor, selectionStart2);
                        break;
                    }
                }
            }
        }
        return true;
    }

    public static final boolean moveCaretToNextWord(@NotNull TextProcessor textProcessor) {
        Intrinsics.checkNotNullParameter(textProcessor, "<this>");
        if (textProcessor.getSelectionStart() >= textProcessor.getText().length()) {
            return true;
        }
        char charAt = textProcessor.getText().charAt(textProcessor.getSelectionStart());
        if (Character.isLetterOrDigit(charAt) || charAt == '_') {
            int length = textProcessor.getText().length();
            for (int selectionStart = textProcessor.getSelectionStart(); selectionStart < length; selectionStart++) {
                char charAt2 = textProcessor.getText().charAt(selectionStart);
                if (!Character.isLetterOrDigit(charAt2) && charAt2 != '_') {
                    setSelectionIndex((EditText) textProcessor, selectionStart);
                    return true;
                }
            }
            return true;
        }
        int length2 = textProcessor.getText().length();
        for (int selectionStart2 = textProcessor.getSelectionStart(); selectionStart2 < length2; selectionStart2++) {
            char charAt3 = textProcessor.getText().charAt(selectionStart2);
            if (Character.isLetterOrDigit(charAt3) || charAt3 == '_') {
                setSelectionIndex((EditText) textProcessor, selectionStart2);
                return true;
            }
        }
        return true;
    }

    public static final void gotoLine(@NotNull TextProcessor textProcessor, int i) {
        Intrinsics.checkNotNullParameter(textProcessor, "<this>");
        int i2 = i - 1;
        if (i2 < 0 || i2 >= textProcessor.getStructure().getLineCount() - 1) {
            throw new LineException(i);
        }
        setSelectionIndex((EditText) textProcessor, textProcessor.getStructure().getIndexForLine(i2));
    }

    public static final boolean hasPrimaryClip(@NotNull TextProcessor textProcessor) {
        Intrinsics.checkNotNullParameter(textProcessor, "<this>");
        Context context = textProcessor.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        ClipboardManager clipboardManager = (ClipboardManager) ContextCompat.getSystemService(context, ClipboardManager.class);
        if (clipboardManager != null) {
            return clipboardManager.hasPrimaryClip();
        }
        return false;
    }
}
