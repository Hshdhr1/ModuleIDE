package androidx.legacy.content;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.util.SparseArray;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes45.dex */
public abstract class WakefulBroadcastReceiver extends BroadcastReceiver {
    private static final String EXTRA_WAKE_LOCK_ID = "androidx.contentpager.content.wakelockid";
    private static final SparseArray sActiveWakeLocks = new SparseArray();
    private static int mNextId = 1;

    public static ComponentName startWakefulService(Context context, Intent intent) {
        ComponentName comp;
        synchronized (sActiveWakeLocks) {
            int id = mNextId;
            mNextId++;
            if (mNextId <= 0) {
                mNextId = 1;
            }
            intent.putExtra("androidx.contentpager.content.wakelockid", id);
            comp = context.startService(intent);
            if (comp == null) {
                comp = null;
            } else {
                PowerManager pm = (PowerManager) context.getSystemService("power");
                PowerManager.WakeLock wl = pm.newWakeLock(1, "androidx.core:wake:" + comp.flattenToShortString());
                wl.setReferenceCounted(false);
                wl.acquire(60000L);
                sActiveWakeLocks.put(id, wl);
            }
        }
        return comp;
    }

    public static boolean completeWakefulIntent(Intent intent) {
        boolean z = false;
        int id = intent.getIntExtra("androidx.contentpager.content.wakelockid", 0);
        if (id != 0) {
            synchronized (sActiveWakeLocks) {
                PowerManager.WakeLock wl = (PowerManager.WakeLock) sActiveWakeLocks.get(id);
                if (wl != null) {
                    wl.release();
                    sActiveWakeLocks.remove(id);
                    z = true;
                } else {
                    Log.w("WakefulBroadcastReceiv.", "No active wake lock id #" + id);
                    z = true;
                }
            }
        }
        return z;
    }
}
