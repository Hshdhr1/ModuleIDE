package androidx.core.location;

import android.location.Location;
import androidx.core.util.Consumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public final /* synthetic */ class LocationManagerCompat$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ Consumer f$0;
    public final /* synthetic */ Location f$1;

    public /* synthetic */ LocationManagerCompat$$ExternalSyntheticLambda0(Consumer consumer, Location location) {
        this.f$0 = consumer;
        this.f$1 = location;
    }

    public final void run() {
        LocationManagerCompat.lambda$getCurrentLocation$0(this.f$0, this.f$1);
    }
}
