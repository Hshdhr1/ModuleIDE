package androidx.core.provider;

import android.graphics.Typeface;
import android.os.Handler;
import androidx.core.provider.FontRequestWorker;
import androidx.core.provider.FontsContractCompat;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
class CallbackWithHandler {
    private final FontsContractCompat.FontRequestCallback mCallback;
    private final Handler mCallbackHandler;

    CallbackWithHandler(FontsContractCompat.FontRequestCallback callback, Handler callbackHandler) {
        this.mCallback = callback;
        this.mCallbackHandler = callbackHandler;
    }

    CallbackWithHandler(FontsContractCompat.FontRequestCallback callback) {
        this.mCallback = callback;
        this.mCallbackHandler = CalleeHandler.create();
    }

    class 1 implements Runnable {
        final /* synthetic */ FontsContractCompat.FontRequestCallback val$callback;
        final /* synthetic */ Typeface val$typeface;

        1(FontsContractCompat.FontRequestCallback fontRequestCallback, Typeface typeface) {
            this.val$callback = fontRequestCallback;
            this.val$typeface = typeface;
        }

        public void run() {
            this.val$callback.onTypefaceRetrieved(this.val$typeface);
        }
    }

    private void onTypefaceRetrieved(Typeface typeface) {
        FontsContractCompat.FontRequestCallback callback = this.mCallback;
        this.mCallbackHandler.post(new 1(callback, typeface));
    }

    class 2 implements Runnable {
        final /* synthetic */ FontsContractCompat.FontRequestCallback val$callback;
        final /* synthetic */ int val$reason;

        2(FontsContractCompat.FontRequestCallback fontRequestCallback, int i) {
            this.val$callback = fontRequestCallback;
            this.val$reason = i;
        }

        public void run() {
            this.val$callback.onTypefaceRequestFailed(this.val$reason);
        }
    }

    private void onTypefaceRequestFailed(int reason) {
        FontsContractCompat.FontRequestCallback callback = this.mCallback;
        this.mCallbackHandler.post(new 2(callback, reason));
    }

    void onTypefaceResult(FontRequestWorker.TypefaceResult typefaceResult) {
        if (typefaceResult.isSuccess()) {
            onTypefaceRetrieved(typefaceResult.mTypeface);
        } else {
            onTypefaceRequestFailed(typefaceResult.mResult);
        }
    }
}
