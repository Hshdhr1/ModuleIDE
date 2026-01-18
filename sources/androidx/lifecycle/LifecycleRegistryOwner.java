package androidx.lifecycle;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes10.dex */
public interface LifecycleRegistryOwner extends LifecycleOwner {
    LifecycleRegistry getLifecycle();

    public final /* synthetic */ class -CC {
        public static /* bridge */ /* synthetic */ Lifecycle $default$getLifecycle(LifecycleRegistryOwner _this) {
            return _this.getLifecycle();
        }
    }
}
