package androidx.activity.result.contract;

import android.content.Context;
import android.content.Intent;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes3.dex */
public abstract class ActivityResultContract {
    public abstract Intent createIntent(Context context, Object input);

    public abstract Object parseResult(int resultCode, Intent intent);

    public SynchronousResult getSynchronousResult(Context context, Object input) {
        return null;
    }

    public static final class SynchronousResult {
        private final Object mValue;

        public SynchronousResult(Object value) {
            this.mValue = value;
        }

        public Object getValue() {
            return this.mValue;
        }
    }
}
