package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.lang.ref.WeakReference;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes20.dex */
class TintResources extends ResourcesWrapper {
    private final WeakReference mContextRef;

    public TintResources(Context context, Resources res) {
        super(res);
        this.mContextRef = new WeakReference(context);
    }

    public Drawable getDrawable(int id) throws Resources.NotFoundException {
        Drawable d = getDrawableCanonical(id);
        Context context = (Context) this.mContextRef.get();
        if (d != null && context != null) {
            ResourceManagerInternal.get().tintDrawableUsingColorFilter(context, id, d);
        }
        return d;
    }
}
