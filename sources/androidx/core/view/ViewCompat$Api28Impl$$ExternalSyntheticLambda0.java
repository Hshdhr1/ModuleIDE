package androidx.core.view;

import android.view.KeyEvent;
import android.view.View;
import androidx.core.view.ViewCompat;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public final /* synthetic */ class ViewCompat$Api28Impl$$ExternalSyntheticLambda0 implements View.OnUnhandledKeyEventListener {
    public final /* synthetic */ ViewCompat.OnUnhandledKeyEventListenerCompat f$0;

    public /* synthetic */ ViewCompat$Api28Impl$$ExternalSyntheticLambda0(ViewCompat.OnUnhandledKeyEventListenerCompat onUnhandledKeyEventListenerCompat) {
        this.f$0 = onUnhandledKeyEventListenerCompat;
    }

    public final boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent) {
        return this.f$0.onUnhandledKeyEvent(view, keyEvent);
    }
}
