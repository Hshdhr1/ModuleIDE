package androidx.core.content;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public abstract class UnusedAppRestrictionsBackportService extends Service {
    public static final String ACTION_UNUSED_APP_RESTRICTIONS_BACKPORT_CONNECTION = "android.support.unusedapprestrictions.action.CustomUnusedAppRestrictionsBackportService";
    private IUnusedAppRestrictionsBackportService.Stub mBinder = new 1();

    protected abstract void isPermissionRevocationEnabled(UnusedAppRestrictionsBackportCallback unusedAppRestrictionsBackportCallback);

    class 1 extends IUnusedAppRestrictionsBackportService.Stub {
        1() {
        }

        public void isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback callback) throws RemoteException {
            if (callback == null) {
                return;
            }
            UnusedAppRestrictionsBackportCallback backportCallback = new UnusedAppRestrictionsBackportCallback(callback);
            UnusedAppRestrictionsBackportService.this.isPermissionRevocationEnabled(backportCallback);
        }
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }
}
