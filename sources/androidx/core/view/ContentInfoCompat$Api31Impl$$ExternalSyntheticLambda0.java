package androidx.core.view;

import android.content.ClipData;
import androidx.core.util.Predicate;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public final /* synthetic */ class ContentInfoCompat$Api31Impl$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ java.util.function.Predicate f$0;

    public /* synthetic */ ContentInfoCompat$Api31Impl$$ExternalSyntheticLambda0(java.util.function.Predicate predicate) {
        this.f$0 = predicate;
    }

    public final boolean test(Object obj) {
        return this.f$0.test((ClipData.Item) obj);
    }
}
