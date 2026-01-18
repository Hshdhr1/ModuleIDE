package com.blacksquircle.ui.editorkit.model;

import android.text.TextPaint;
import android.text.style.CharacterStyle;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SyntaxHighlightSpan.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/blacksquircle/ui/editorkit/model/SyntaxHighlightSpan;", "Landroid/text/style/CharacterStyle;", "span", "Lcom/blacksquircle/ui/editorkit/model/StyleSpan;", "(Lcom/blacksquircle/ui/editorkit/model/StyleSpan;)V", "updateDrawState", "", "textPaint", "Landroid/text/TextPaint;", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class SyntaxHighlightSpan extends CharacterStyle {

    @NotNull
    private final StyleSpan span;

    public SyntaxHighlightSpan(@NotNull StyleSpan styleSpan) {
        Intrinsics.checkNotNullParameter(styleSpan, "span");
        this.span = styleSpan;
    }

    public void updateDrawState(@Nullable TextPaint textPaint) {
        if (textPaint != null) {
            textPaint.setColor(this.span.getColor());
        }
        if (textPaint != null) {
            textPaint.setFakeBoldText(this.span.getBold());
        }
        if (textPaint != null) {
            textPaint.setUnderlineText(this.span.getUnderline());
        }
        if (this.span.getItalic() && textPaint != null) {
            textPaint.setTextSkewX(-0.1f);
        }
        if (!this.span.getStrikethrough() || textPaint == null) {
            return;
        }
        textPaint.setFlags(16);
    }
}
