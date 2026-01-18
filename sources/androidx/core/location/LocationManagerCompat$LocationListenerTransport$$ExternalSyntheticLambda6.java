package androidx.core.location;

import androidx.core.location.LocationManagerCompat;
import java.lang.ref.WeakReference;
import java.util.function.Predicate;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public final /* synthetic */ class LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda6 implements Predicate {
    public static final /* synthetic */ LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda6 INSTANCE = new LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda6();

    private /* synthetic */ LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda6() {
    }

    public final boolean test(Object obj) {
        return LocationManagerCompat.LocationListenerTransport.lambda$register$0((WeakReference) obj);
    }
}
