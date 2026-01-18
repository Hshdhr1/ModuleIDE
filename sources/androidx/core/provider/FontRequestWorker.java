package androidx.core.provider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.provider.FontsContractCompat;
import androidx.core.util.Consumer;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
class FontRequestWorker {
    static final LruCache sTypefaceCache = new LruCache(16);
    private static final ExecutorService DEFAULT_EXECUTOR_SERVICE = RequestExecutor.createDefaultExecutor("fonts-androidx", 10, 10000);
    static final Object LOCK = new Object();
    static final SimpleArrayMap PENDING_REPLIES = new SimpleArrayMap();

    private FontRequestWorker() {
    }

    static void resetTypefaceCache() {
        sTypefaceCache.evictAll();
    }

    static Typeface requestFontSync(Context context, FontRequest request, CallbackWithHandler callback, int style, int timeoutInMillis) {
        String id = createCacheId(request, style);
        Typeface cached = (Typeface) sTypefaceCache.get(id);
        if (cached != null) {
            callback.onTypefaceResult(new TypefaceResult(cached));
            return cached;
        }
        if (timeoutInMillis == -1) {
            TypefaceResult typefaceResult = getFontSync(id, context, request, style);
            callback.onTypefaceResult(typefaceResult);
            return typefaceResult.mTypeface;
        }
        Callable<TypefaceResult> fetcher = new 1(id, context, request, style);
        try {
            TypefaceResult typefaceResult2 = (TypefaceResult) RequestExecutor.submit(DEFAULT_EXECUTOR_SERVICE, fetcher, timeoutInMillis);
            callback.onTypefaceResult(typefaceResult2);
            return typefaceResult2.mTypeface;
        } catch (InterruptedException e) {
            callback.onTypefaceResult(new TypefaceResult(-3));
            return null;
        }
    }

    class 1 implements Callable {
        final /* synthetic */ Context val$context;
        final /* synthetic */ String val$id;
        final /* synthetic */ FontRequest val$request;
        final /* synthetic */ int val$style;

        1(String str, Context context, FontRequest fontRequest, int i) {
            this.val$id = str;
            this.val$context = context;
            this.val$request = fontRequest;
            this.val$style = i;
        }

        public TypefaceResult call() {
            return FontRequestWorker.getFontSync(this.val$id, this.val$context, this.val$request, this.val$style);
        }
    }

    static Typeface requestFontAsync(Context context, FontRequest request, int style, Executor executor, CallbackWithHandler callback) {
        String id = createCacheId(request, style);
        Typeface cached = (Typeface) sTypefaceCache.get(id);
        if (cached != null) {
            callback.onTypefaceResult(new TypefaceResult(cached));
            return cached;
        }
        Consumer<TypefaceResult> reply = new 2(callback);
        synchronized (LOCK) {
            SimpleArrayMap simpleArrayMap = PENDING_REPLIES;
            ArrayList<Consumer<TypefaceResult>> pendingReplies = (ArrayList) simpleArrayMap.get(id);
            if (pendingReplies != null) {
                pendingReplies.add(reply);
                return null;
            }
            ArrayList<Consumer<TypefaceResult>> pendingReplies2 = new ArrayList<>();
            pendingReplies2.add(reply);
            simpleArrayMap.put(id, pendingReplies2);
            Callable<TypefaceResult> fetcher = new 3(id, context, request, style);
            Executor finalExecutor = executor == null ? DEFAULT_EXECUTOR_SERVICE : executor;
            RequestExecutor.execute(finalExecutor, fetcher, new 4(id));
            return null;
        }
    }

    class 2 implements Consumer {
        final /* synthetic */ CallbackWithHandler val$callback;

        2(CallbackWithHandler callbackWithHandler) {
            this.val$callback = callbackWithHandler;
        }

        public void accept(TypefaceResult typefaceResult) {
            if (typefaceResult == null) {
                typefaceResult = new TypefaceResult(-3);
            }
            this.val$callback.onTypefaceResult(typefaceResult);
        }
    }

    class 3 implements Callable {
        final /* synthetic */ Context val$context;
        final /* synthetic */ String val$id;
        final /* synthetic */ FontRequest val$request;
        final /* synthetic */ int val$style;

        3(String str, Context context, FontRequest fontRequest, int i) {
            this.val$id = str;
            this.val$context = context;
            this.val$request = fontRequest;
            this.val$style = i;
        }

        public TypefaceResult call() {
            try {
                return FontRequestWorker.getFontSync(this.val$id, this.val$context, this.val$request, this.val$style);
            } catch (Throwable th) {
                return new TypefaceResult(-3);
            }
        }
    }

    class 4 implements Consumer {
        final /* synthetic */ String val$id;

        4(String str) {
            this.val$id = str;
        }

        public void accept(TypefaceResult typefaceResult) {
            synchronized (FontRequestWorker.LOCK) {
                ArrayList<Consumer<TypefaceResult>> replies = (ArrayList) FontRequestWorker.PENDING_REPLIES.get(this.val$id);
                if (replies == null) {
                    return;
                }
                FontRequestWorker.PENDING_REPLIES.remove(this.val$id);
                for (int i = 0; i < replies.size(); i++) {
                    ((Consumer) replies.get(i)).accept(typefaceResult);
                }
            }
        }
    }

    private static String createCacheId(FontRequest request, int style) {
        return request.getId() + "-" + style;
    }

    static TypefaceResult getFontSync(String cacheId, Context context, FontRequest request, int style) {
        LruCache lruCache = sTypefaceCache;
        Typeface cached = (Typeface) lruCache.get(cacheId);
        if (cached != null) {
            return new TypefaceResult(cached);
        }
        try {
            FontsContractCompat.FontFamilyResult result = FontProvider.getFontFamilyResult(context, request, null);
            int fontFamilyResultStatus = getFontFamilyResultStatus(result);
            if (fontFamilyResultStatus != 0) {
                return new TypefaceResult(fontFamilyResultStatus);
            }
            Typeface typeface = TypefaceCompat.createFromFontInfo(context, null, result.getFonts(), style);
            if (typeface != null) {
                lruCache.put(cacheId, typeface);
                return new TypefaceResult(typeface);
            }
            return new TypefaceResult(-3);
        } catch (PackageManager.NameNotFoundException e) {
            return new TypefaceResult(-1);
        }
    }

    private static int getFontFamilyResultStatus(FontsContractCompat.FontFamilyResult fontFamilyResult) {
        if (fontFamilyResult.getStatusCode() != 0) {
            switch (fontFamilyResult.getStatusCode()) {
                case 1:
                    return -2;
                default:
                    return -3;
            }
        }
        FontsContractCompat.FontInfo[] fonts = fontFamilyResult.getFonts();
        if (fonts == null || fonts.length == 0) {
            return 1;
        }
        for (FontsContractCompat.FontInfo font : fonts) {
            int resultCode = font.getResultCode();
            if (resultCode != 0) {
                if (resultCode < 0) {
                    return -3;
                }
                return resultCode;
            }
        }
        return 0;
    }

    static final class TypefaceResult {
        final int mResult;
        final Typeface mTypeface;

        TypefaceResult(int result) {
            this.mTypeface = null;
            this.mResult = result;
        }

        TypefaceResult(Typeface typeface) {
            this.mTypeface = typeface;
            this.mResult = 0;
        }

        boolean isSuccess() {
            return this.mResult == 0;
        }
    }
}
