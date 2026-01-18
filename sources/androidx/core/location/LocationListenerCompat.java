package androidx.core.location;

import android.location.LocationListener;
import android.os.Bundle;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public interface LocationListenerCompat extends LocationListener {
    void onProviderDisabled(String str);

    void onProviderEnabled(String str);

    void onStatusChanged(String str, int i, Bundle bundle);

    public final /* synthetic */ class -CC {
        public static void $default$onStatusChanged(LocationListenerCompat _this, String provider, int status, Bundle extras) {
        }

        public static void $default$onProviderEnabled(LocationListenerCompat _this, String provider) {
        }

        public static void $default$onProviderDisabled(LocationListenerCompat _this, String provider) {
        }
    }
}
