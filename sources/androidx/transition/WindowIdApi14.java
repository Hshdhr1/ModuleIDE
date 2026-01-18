package androidx.transition;

import android.os.IBinder;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes51.dex */
class WindowIdApi14 implements WindowIdImpl {
    private final IBinder mToken;

    WindowIdApi14(IBinder token) {
        this.mToken = token;
    }

    public boolean equals(Object o) {
        return (o instanceof WindowIdApi14) && ((WindowIdApi14) o).mToken.equals(this.mToken);
    }

    public int hashCode() {
        return this.mToken.hashCode();
    }
}
