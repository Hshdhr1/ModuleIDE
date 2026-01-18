package androidx.core.location;

import androidx.core.location.LocationManagerCompat;
import java.lang.ref.WeakReference;
import java.util.function.Predicate;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public final /* synthetic */ class LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda7 implements Predicate {
    public static final /* synthetic */ LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda7 INSTANCE = new LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda7();

    private /* synthetic */ LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda7() {
    }

    public final boolean test(Object obj) {
        return LocationManagerCompat.LocationListenerTransport.lambda$unregister$1((WeakReference) obj);
    }
}
