package androidx.core.os;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public final class HandlerCompat {
    private static final String TAG = "HandlerCompat";

    public static Handler createAsync(Looper looper) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.createAsync(looper);
        }
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                return (Handler) Handler.class.getDeclaredConstructor(new Class[]{Looper.class, Handler.Callback.class, Boolean.TYPE}).newInstance(new Object[]{looper, null, true});
            } catch (NoSuchMethodException e) {
                e = e;
                Log.w("HandlerCompat", "Unable to invoke Handler(Looper, Callback, boolean) constructor", e);
                return new Handler(looper);
            } catch (IllegalAccessException e2) {
                e = e2;
                Log.w("HandlerCompat", "Unable to invoke Handler(Looper, Callback, boolean) constructor", e);
                return new Handler(looper);
            } catch (InvocationTargetException e3) {
                Error cause = e3.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                if (cause instanceof Error) {
                    throw cause;
                }
                throw new RuntimeException(cause);
            } catch (InstantiationException e4) {
                e = e4;
                Log.w("HandlerCompat", "Unable to invoke Handler(Looper, Callback, boolean) constructor", e);
                return new Handler(looper);
            }
        }
        return new Handler(looper);
    }

    public static Handler createAsync(Looper looper, Handler.Callback callback) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.createAsync(looper, callback);
        }
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                return (Handler) Handler.class.getDeclaredConstructor(new Class[]{Looper.class, Handler.Callback.class, Boolean.TYPE}).newInstance(new Object[]{looper, callback, true});
            } catch (InvocationTargetException e) {
                Error cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                if (cause instanceof Error) {
                    throw cause;
                }
                throw new RuntimeException(cause);
            } catch (IllegalAccessException e2) {
                e = e2;
                Log.w("HandlerCompat", "Unable to invoke Handler(Looper, Callback, boolean) constructor", e);
                return new Handler(looper, callback);
            } catch (InstantiationException e3) {
                e = e3;
                Log.w("HandlerCompat", "Unable to invoke Handler(Looper, Callback, boolean) constructor", e);
                return new Handler(looper, callback);
            } catch (NoSuchMethodException e4) {
                e = e4;
                Log.w("HandlerCompat", "Unable to invoke Handler(Looper, Callback, boolean) constructor", e);
                return new Handler(looper, callback);
            }
        }
        return new Handler(looper, callback);
    }

    public static boolean postDelayed(Handler handler, Runnable r, Object token, long delayMillis) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.postDelayed(handler, r, token, delayMillis);
        }
        Message message = Message.obtain(handler, r);
        message.obj = token;
        return handler.sendMessageDelayed(message, delayMillis);
    }

    public static boolean hasCallbacks(Handler handler, Runnable r) {
        Throwable th = null;
        if (Build.VERSION.SDK_INT >= 29) {
            return Api29Impl.hasCallbacks(handler, r);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            try {
                Method hasCallbacksMethod = Handler.class.getMethod("hasCallbacks", new Class[]{Runnable.class});
                return ((Boolean) hasCallbacksMethod.invoke(handler, new Object[]{r})).booleanValue();
            } catch (NullPointerException e) {
                th = e;
            } catch (IllegalAccessException e2) {
                th = e2;
            } catch (NoSuchMethodException e3) {
                th = e3;
            } catch (InvocationTargetException e4) {
                Error cause = e4.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                if (cause instanceof Error) {
                    throw cause;
                }
                throw new RuntimeException(cause);
            }
        }
        throw new UnsupportedOperationException("Failed to call Handler.hasCallbacks(), but there is no safe failure mode for this method. Raising exception.", th);
    }

    private HandlerCompat() {
    }

    private static class Api29Impl {
        private Api29Impl() {
        }

        public static boolean hasCallbacks(Handler handler, Runnable r) {
            return handler.hasCallbacks(r);
        }
    }

    private static class Api28Impl {
        private Api28Impl() {
        }

        public static Handler createAsync(Looper looper) {
            return Handler.createAsync(looper);
        }

        public static Handler createAsync(Looper looper, Handler.Callback callback) {
            return Handler.createAsync(looper, callback);
        }

        public static boolean postDelayed(Handler handler, Runnable r, Object token, long delayMillis) {
            return handler.postDelayed(r, token, delayMillis);
        }
    }
}
