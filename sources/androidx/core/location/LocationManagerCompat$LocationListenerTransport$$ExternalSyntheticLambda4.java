package androidx.core.location;

import android.os.Bundle;
import androidx.core.location.LocationManagerCompat;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public final /* synthetic */ class LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ LocationManagerCompat.LocationListenerTransport f$0;
    public final /* synthetic */ LocationListenerCompat f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ Bundle f$4;

    public /* synthetic */ LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda4(LocationManagerCompat.LocationListenerTransport locationListenerTransport, LocationListenerCompat locationListenerCompat, String str, int i, Bundle bundle) {
        this.f$0 = locationListenerTransport;
        this.f$1 = locationListenerCompat;
        this.f$2 = str;
        this.f$3 = i;
        this.f$4 = bundle;
    }

    public final void run() {
        this.f$0.lambda$onStatusChanged$5$LocationManagerCompat$LocationListenerTransport(this.f$1, this.f$2, this.f$3, this.f$4);
    }
}
