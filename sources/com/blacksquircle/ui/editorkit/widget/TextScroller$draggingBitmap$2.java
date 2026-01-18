package com.blacksquircle.ui.editorkit.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: TextScroller.kt */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
final class TextScroller$draggingBitmap$2 extends Lambda implements Function0 {
    final /* synthetic */ TextScroller this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    TextScroller$draggingBitmap$2(TextScroller textScroller) {
        super(0);
        this.this$0 = textScroller;
    }

    @NotNull
    public final Bitmap invoke() {
        Bitmap createBitmap = Bitmap.createBitmap(this.this$0.getWidth(), TextScroller.access$getThumbHeight$p(this.this$0), Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(createBitmap, "createBitmap(...)");
        TextScroller.access$getThumbDragging$p(this.this$0).setBounds(new Rect(0, 0, this.this$0.getWidth(), TextScroller.access$getThumbHeight$p(this.this$0)));
        TextScroller.access$getThumbDragging$p(this.this$0).draw(new Canvas(createBitmap));
        return createBitmap;
    }
}
