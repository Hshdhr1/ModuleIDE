package com.blacksquircle.ui.editorkit.utils;

import android.widget.TextView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: Extensions.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004¨\u0006\u0007"}, d2 = {"bottomVisibleLine", "", "Landroid/widget/TextView;", "getBottomVisibleLine", "(Landroid/widget/TextView;)I", "topVisibleLine", "getTopVisibleLine", "editorkit_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class ExtensionsKt {
    public static final int getTopVisibleLine(@NotNull TextView textView) {
        int lineForVertical;
        Intrinsics.checkNotNullParameter(textView, "<this>");
        if (textView.getLayout() == null || textView.getLineHeight() == 0 || (lineForVertical = textView.getLayout().getLineForVertical(textView.getScrollY())) < 0) {
            return 0;
        }
        return lineForVertical >= textView.getLineCount() ? textView.getLineCount() - 1 : lineForVertical;
    }

    public static final int getBottomVisibleLine(@NotNull TextView textView) {
        int lineForVertical;
        Intrinsics.checkNotNullParameter(textView, "<this>");
        if (textView.getLayout() == null || textView.getLineHeight() == 0 || (lineForVertical = textView.getLayout().getLineForVertical(textView.getScrollY() + textView.getHeight())) < 0) {
            return 0;
        }
        return lineForVertical >= textView.getLineCount() ? textView.getLineCount() - 1 : lineForVertical;
    }
}
