package com.blacksquircle.ui.editorkit.model;

import android.text.style.BackgroundColorSpan;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: FindResultSpan.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lcom/blacksquircle/ui/editorkit/model/FindResultSpan;", "Landroid/text/style/BackgroundColorSpan;", "span", "Lcom/blacksquircle/ui/editorkit/model/StyleSpan;", "(Lcom/blacksquircle/ui/editorkit/model/StyleSpan;)V", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class FindResultSpan extends BackgroundColorSpan {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FindResultSpan(@NotNull StyleSpan styleSpan) {
        super(styleSpan.getColor());
        Intrinsics.checkNotNullParameter(styleSpan, "span");
    }
}
