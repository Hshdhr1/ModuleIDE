package androidx.core.location;

import android.content.Context;
import android.location.GnssStatus;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.collection.SimpleArrayMap;
import androidx.core.location.GnssStatusCompat;
import androidx.core.os.CancellationSignal;
import androidx.core.os.ExecutorCompat;
import androidx.core.util.Consumer;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public final class LocationManagerCompat {
    private static final long GET_CURRENT_LOCATION_TIMEOUT_MS = 30000;
    private static final long MAX_CURRENT_LOCATION_AGE_MS = 10000;
    private static final long PRE_N_LOOPER_TIMEOUT_S = 5;
    private static Field sContextField;
    static final WeakHashMap sLocationListeners = new WeakHashMap();
    private static Method sRequestLocationUpdatesExecutorMethod;
    private static Method sRequestLocationUpdatesLooperMethod;

    public static boolean isLocationEnabled(LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.isLocationEnabled(locationManager);
        }
        if (Build.VERSION.SDK_INT <= 19) {
            try {
                if (sContextField == null) {
                    Field declaredField = LocationManager.class.getDeclaredField("mContext");
                    sContextField = declaredField;
                    declaredField.setAccessible(true);
                }
                Context context = (Context) sContextField.get(locationManager);
                if (context != null) {
                    if (Build.VERSION.SDK_INT == 19) {
                        return Settings.Secure.getInt(context.getContentResolver(), "location_mode", 0) != 0;
                    }
                    return !TextUtils.isEmpty(Settings.Secure.getString(context.getContentResolver(), "location_providers_allowed"));
                }
            } catch (SecurityException e) {
            } catch (ClassCastException e2) {
            } catch (IllegalAccessException e3) {
            } catch (NoSuchFieldException e4) {
            }
        }
        return locationManager.isProviderEnabled("network") || locationManager.isProviderEnabled("gps");
    }

    public static boolean hasProvider(LocationManager locationManager, String provider) {
        if (Build.VERSION.SDK_INT >= 31) {
            return Api31Impl.hasProvider(locationManager, provider);
        }
        if (locationManager.getAllProviders().contains(provider)) {
            return true;
        }
        try {
            return locationManager.getProvider(provider) != null;
        } catch (SecurityException e) {
            return false;
        }
    }

    public static void getCurrentLocation(LocationManager locationManager, String provider, CancellationSignal cancellationSignal, Executor executor, Consumer consumer) {
        if (Build.VERSION.SDK_INT >= 30) {
            Api30Impl.getCurrentLocation(locationManager, provider, cancellationSignal, executor, consumer);
            return;
        }
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            long locationAgeMs = SystemClock.elapsedRealtime() - LocationCompat.getElapsedRealtimeMillis(location);
            if (locationAgeMs < 10000) {
                executor.execute(new LocationManagerCompat$$ExternalSyntheticLambda0(consumer, location));
                return;
            }
        }
        CancellableLocationListener listener = new CancellableLocationListener(locationManager, executor, consumer);
        locationManager.requestLocationUpdates(provider, 0L, 0.0f, listener, Looper.getMainLooper());
        if (cancellationSignal != null) {
            cancellationSignal.setOnCancelListener(new 1(listener));
        }
        listener.startTimeout(30000L);
    }

    static /* synthetic */ void lambda$getCurrentLocation$0(Consumer consumer, Location location) {
        consumer.accept(location);
    }

    class 1 implements CancellationSignal.OnCancelListener {
        final /* synthetic */ CancellableLocationListener val$listener;

        1(CancellableLocationListener cancellableLocationListener) {
            this.val$listener = cancellableLocationListener;
        }

        public void onCancel() {
            this.val$listener.cancel();
        }
    }

    public static void requestLocationUpdates(LocationManager locationManager, String provider, LocationRequestCompat locationRequest, Executor executor, LocationListenerCompat listener) {
        if (Build.VERSION.SDK_INT >= 31) {
            Api31Impl.requestLocationUpdates(locationManager, provider, locationRequest.toLocationRequest(), executor, listener);
            return;
        }
        if (Build.VERSION.SDK_INT >= 30) {
            try {
                if (sRequestLocationUpdatesExecutorMethod == null) {
                    Method declaredMethod = LocationManager.class.getDeclaredMethod("requestLocationUpdates", new Class[]{LocationRequest.class, Executor.class, LocationListener.class});
                    sRequestLocationUpdatesExecutorMethod = declaredMethod;
                    declaredMethod.setAccessible(true);
                }
                LocationRequest request = locationRequest.toLocationRequest(provider);
                if (request != null) {
                    sRequestLocationUpdatesExecutorMethod.invoke(locationManager, new Object[]{request, executor, listener});
                    return;
                }
            } catch (UnsupportedOperationException e) {
            } catch (NoSuchMethodException e2) {
            } catch (IllegalAccessException e3) {
            } catch (InvocationTargetException e4) {
            }
        }
        LocationListenerTransport transport = new LocationListenerTransport(listener, executor);
        if (Build.VERSION.SDK_INT >= 19) {
            try {
                if (sRequestLocationUpdatesLooperMethod == null) {
                    Method declaredMethod2 = LocationManager.class.getDeclaredMethod("requestLocationUpdates", new Class[]{LocationRequest.class, LocationListener.class, Looper.class});
                    sRequestLocationUpdatesLooperMethod = declaredMethod2;
                    declaredMethod2.setAccessible(true);
                }
                LocationRequest request2 = locationRequest.toLocationRequest(provider);
                if (request2 != null) {
                    synchronized (sLocationListeners) {
                        sRequestLocationUpdatesLooperMethod.invoke(locationManager, new Object[]{request2, transport, Looper.getMainLooper()});
                        transport.register();
                    }
                    return;
                }
            } catch (InvocationTargetException e5) {
            } catch (UnsupportedOperationException e6) {
            } catch (IllegalAccessException e7) {
            } catch (NoSuchMethodException e8) {
            }
        }
        synchronized (sLocationListeners) {
            locationManager.requestLocationUpdates(provider, locationRequest.getIntervalMillis(), locationRequest.getMinUpdateDistanceMeters(), transport, Looper.getMainLooper());
            transport.register();
        }
    }

    public static void requestLocationUpdates(LocationManager locationManager, String provider, LocationRequestCompat locationRequest, LocationListenerCompat listener, Looper looper) {
        if (Build.VERSION.SDK_INT >= 31) {
            Api31Impl.requestLocationUpdates(locationManager, provider, locationRequest.toLocationRequest(), ExecutorCompat.create(new Handler(looper)), listener);
            return;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            try {
                if (sRequestLocationUpdatesLooperMethod == null) {
                    Method declaredMethod = LocationManager.class.getDeclaredMethod("requestLocationUpdates", new Class[]{LocationRequest.class, LocationListener.class, Looper.class});
                    sRequestLocationUpdatesLooperMethod = declaredMethod;
                    declaredMethod.setAccessible(true);
                }
                LocationRequest request = locationRequest.toLocationRequest(provider);
                if (request != null) {
                    sRequestLocationUpdatesLooperMethod.invoke(locationManager, new Object[]{request, listener, looper});
                    return;
                }
            } catch (UnsupportedOperationException e) {
            } catch (NoSuchMethodException e2) {
            } catch (IllegalAccessException e3) {
            } catch (InvocationTargetException e4) {
            }
        }
        locationManager.requestLocationUpdates(provider, locationRequest.getIntervalMillis(), locationRequest.getMinUpdateDistanceMeters(), listener, looper);
    }

    public static void removeUpdates(LocationManager locationManager, LocationListenerCompat listener) {
        WeakHashMap weakHashMap = sLocationListeners;
        synchronized (weakHashMap) {
            List<WeakReference<LocationListenerTransport>> transports = (List) weakHashMap.remove(listener);
            if (transports != null) {
                for (WeakReference<LocationListenerTransport> reference : transports) {
                    LocationListenerTransport transport = (LocationListenerTransport) reference.get();
                    if (transport != null && transport.unregister()) {
                        locationManager.removeUpdates(transport);
                    }
                }
            }
        }
        locationManager.removeUpdates(listener);
    }

    public static String getGnssHardwareModelName(LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.getGnssHardwareModelName(locationManager);
        }
        return null;
    }

    public static int getGnssYearOfHardware(LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.getGnssYearOfHardware(locationManager);
        }
        return 0;
    }

    private static class GnssLazyLoader {
        static final SimpleArrayMap sGnssStatusListeners = new SimpleArrayMap();

        private GnssLazyLoader() {
        }
    }

    public static boolean registerGnssStatusCallback(LocationManager locationManager, GnssStatusCompat.Callback callback, Handler handler) {
        if (Build.VERSION.SDK_INT >= 30) {
            return registerGnssStatusCallback(locationManager, ExecutorCompat.create(handler), callback);
        }
        return registerGnssStatusCallback(locationManager, new InlineHandlerExecutor(handler), callback);
    }

    public static boolean registerGnssStatusCallback(LocationManager locationManager, Executor executor, GnssStatusCompat.Callback callback) {
        if (Build.VERSION.SDK_INT >= 30) {
            return registerGnssStatusCallback(locationManager, null, executor, callback);
        }
        Looper looper = Looper.myLooper();
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        return registerGnssStatusCallback(locationManager, new Handler(looper), executor, callback);
    }

    private static boolean registerGnssStatusCallback(LocationManager locationManager, Handler baseHandler, Executor executor, GnssStatusCompat.Callback callback) {
        GpsStatusTransport transport;
        if (Build.VERSION.SDK_INT >= 30) {
            synchronized (GnssLazyLoader.sGnssStatusListeners) {
                GnssStatusTransport transport2 = (GnssStatusTransport) GnssLazyLoader.sGnssStatusListeners.get(callback);
                if (transport2 == null) {
                    transport2 = new GnssStatusTransport(callback);
                }
                if (!locationManager.registerGnssStatusCallback(executor, transport2)) {
                    return false;
                }
                GnssLazyLoader.sGnssStatusListeners.put(callback, transport2);
                return true;
            }
        }
        if (Build.VERSION.SDK_INT >= 24) {
            Preconditions.checkArgument(baseHandler != null);
            synchronized (GnssLazyLoader.sGnssStatusListeners) {
                PreRGnssStatusTransport transport3 = (PreRGnssStatusTransport) GnssLazyLoader.sGnssStatusListeners.get(callback);
                if (transport3 == null) {
                    transport3 = new PreRGnssStatusTransport(callback);
                } else {
                    transport3.unregister();
                }
                transport3.register(executor);
                if (!locationManager.registerGnssStatusCallback(transport3, baseHandler)) {
                    return false;
                }
                GnssLazyLoader.sGnssStatusListeners.put(callback, transport3);
                return true;
            }
        }
        Preconditions.checkArgument(baseHandler != null);
        synchronized (GnssLazyLoader.sGnssStatusListeners) {
            GpsStatusTransport transport4 = (GpsStatusTransport) GnssLazyLoader.sGnssStatusListeners.get(callback);
            if (transport4 == null) {
                transport = new GpsStatusTransport(locationManager, callback);
            } else {
                transport4.unregister();
                transport = transport4;
            }
            transport.register(executor);
            GpsStatusTransport myTransport = transport;
            FutureTask<Boolean> task = new FutureTask<>(new LocationManagerCompat$$ExternalSyntheticLambda1(locationManager, myTransport));
            if (Looper.myLooper() == baseHandler.getLooper()) {
                task.run();
            } else if (!baseHandler.post(task)) {
                throw new IllegalStateException(baseHandler + " is shutting down");
            }
            boolean interrupted = false;
            try {
                try {
                    long remainingNanos = TimeUnit.SECONDS.toNanos(5L);
                    long end = System.nanoTime() + remainingNanos;
                    while (((Boolean) task.get(remainingNanos, TimeUnit.NANOSECONDS)).booleanValue()) {
                        try {
                            GnssLazyLoader.sGnssStatusListeners.put(callback, myTransport);
                            return true;
                        } catch (InterruptedException e) {
                            interrupted = true;
                            remainingNanos = end - System.nanoTime();
                        }
                    }
                    if (interrupted) {
                        Thread.currentThread().interrupt();
                    }
                    return false;
                } finally {
                    if (interrupted) {
                        Thread.currentThread().interrupt();
                    }
                }
            } catch (TimeoutException e2) {
                throw new IllegalStateException(baseHandler + " appears to be blocked, please run registerGnssStatusCallback() directly on a Looper thread or ensure the main Looper is not blocked by this thread", e2);
            } catch (ExecutionException e3) {
                if (e3.getCause() instanceof RuntimeException) {
                    throw e3.getCause();
                }
                if (e3.getCause() instanceof Error) {
                    throw e3.getCause();
                }
                throw new IllegalStateException(e3);
            }
        }
    }

    static /* synthetic */ Boolean lambda$registerGnssStatusCallback$1(LocationManager locationManager, GpsStatusTransport myTransport) throws Exception {
        return Boolean.valueOf(locationManager.addGpsStatusListener(myTransport));
    }

    public static void unregisterGnssStatusCallback(LocationManager locationManager, GnssStatusCompat.Callback callback) {
        if (Build.VERSION.SDK_INT >= 30) {
            synchronized (GnssLazyLoader.sGnssStatusListeners) {
                GnssStatusTransport transport = (GnssStatusTransport) GnssLazyLoader.sGnssStatusListeners.remove(callback);
                if (transport != null) {
                    locationManager.unregisterGnssStatusCallback(transport);
                }
            }
            return;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            synchronized (GnssLazyLoader.sGnssStatusListeners) {
                PreRGnssStatusTransport transport2 = (PreRGnssStatusTransport) GnssLazyLoader.sGnssStatusListeners.remove(callback);
                if (transport2 != null) {
                    transport2.unregister();
                    locationManager.unregisterGnssStatusCallback(transport2);
                }
            }
            return;
        }
        synchronized (GnssLazyLoader.sGnssStatusListeners) {
            GpsStatusTransport transport3 = (GpsStatusTransport) GnssLazyLoader.sGnssStatusListeners.remove(callback);
            if (transport3 != null) {
                transport3.unregister();
                locationManager.removeGpsStatusListener(transport3);
            }
        }
    }

    private LocationManagerCompat() {
    }

    private static class LocationListenerTransport implements LocationListener {
        final Executor mExecutor;
        volatile LocationListenerCompat mListener;

        LocationListenerTransport(LocationListenerCompat listener, Executor executor) {
            this.mListener = (LocationListenerCompat) ObjectsCompat.requireNonNull(listener, "invalid null listener");
            this.mExecutor = executor;
        }

        public void register() {
            List<WeakReference<LocationListenerTransport>> transports = (List) LocationManagerCompat.sLocationListeners.get(this.mListener);
            if (transports == null) {
                transports = new ArrayList<>(1);
                LocationManagerCompat.sLocationListeners.put(this.mListener, transports);
            } else if (Build.VERSION.SDK_INT >= 24) {
                transports.removeIf(LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda6.INSTANCE);
            } else {
                Iterator<WeakReference<LocationListenerTransport>> it = transports.iterator();
                while (it.hasNext()) {
                    if (((WeakReference) it.next()).get() == null) {
                        it.remove();
                    }
                }
            }
            transports.add(new WeakReference(this));
        }

        static /* synthetic */ boolean lambda$register$0(WeakReference reference) {
            return reference.get() == null;
        }

        public boolean unregister() {
            LocationListenerCompat listener = this.mListener;
            if (listener == null) {
                return false;
            }
            this.mListener = null;
            List<WeakReference<LocationListenerTransport>> transports = (List) LocationManagerCompat.sLocationListeners.get(listener);
            if (transports != null) {
                if (Build.VERSION.SDK_INT >= 24) {
                    transports.removeIf(LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda7.INSTANCE);
                } else {
                    Iterator<WeakReference<LocationListenerTransport>> it = transports.iterator();
                    while (it.hasNext()) {
                        if (((WeakReference) it.next()).get() == null) {
                            it.remove();
                        }
                    }
                }
                if (transports.isEmpty()) {
                    LocationManagerCompat.sLocationListeners.remove(listener);
                    return true;
                }
                return true;
            }
            return true;
        }

        static /* synthetic */ boolean lambda$unregister$1(WeakReference reference) {
            return reference.get() == null;
        }

        public void onLocationChanged(Location location) {
            LocationListenerCompat listener = this.mListener;
            if (listener == null) {
                return;
            }
            this.mExecutor.execute(new LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda1(this, listener, location));
        }

        public /* synthetic */ void lambda$onLocationChanged$2$LocationManagerCompat$LocationListenerTransport(LocationListenerCompat listener, Location location) {
            if (this.mListener != listener) {
                return;
            }
            listener.onLocationChanged(location);
        }

        public void onLocationChanged(List list) {
            LocationListenerCompat listener = this.mListener;
            if (listener == null) {
                return;
            }
            this.mExecutor.execute(new LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda5(this, listener, list));
        }

        public /* synthetic */ void lambda$onLocationChanged$3$LocationManagerCompat$LocationListenerTransport(LocationListenerCompat listener, List locations) {
            if (this.mListener != listener) {
                return;
            }
            listener.onLocationChanged(locations);
        }

        public void onFlushComplete(int requestCode) {
            LocationListenerCompat listener = this.mListener;
            if (listener == null) {
                return;
            }
            this.mExecutor.execute(new LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda0(this, listener, requestCode));
        }

        public /* synthetic */ void lambda$onFlushComplete$4$LocationManagerCompat$LocationListenerTransport(LocationListenerCompat listener, int requestCode) {
            if (this.mListener != listener) {
                return;
            }
            listener.onFlushComplete(requestCode);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            LocationListenerCompat listener = this.mListener;
            if (listener == null) {
                return;
            }
            this.mExecutor.execute(new LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda4(this, listener, provider, status, extras));
        }

        public /* synthetic */ void lambda$onStatusChanged$5$LocationManagerCompat$LocationListenerTransport(LocationListenerCompat listener, String provider, int status, Bundle extras) {
            if (this.mListener != listener) {
                return;
            }
            listener.onStatusChanged(provider, status, extras);
        }

        public void onProviderEnabled(String provider) {
            LocationListenerCompat listener = this.mListener;
            if (listener == null) {
                return;
            }
            this.mExecutor.execute(new LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda3(this, listener, provider));
        }

        public /* synthetic */ void lambda$onProviderEnabled$6$LocationManagerCompat$LocationListenerTransport(LocationListenerCompat listener, String provider) {
            if (this.mListener != listener) {
                return;
            }
            listener.onProviderEnabled(provider);
        }

        public void onProviderDisabled(String provider) {
            LocationListenerCompat listener = this.mListener;
            if (listener == null) {
                return;
            }
            this.mExecutor.execute(new LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda2(this, listener, provider));
        }

        public /* synthetic */ void lambda$onProviderDisabled$7$LocationManagerCompat$LocationListenerTransport(LocationListenerCompat listener, String provider) {
            if (this.mListener != listener) {
                return;
            }
            listener.onProviderDisabled(provider);
        }
    }

    private static class GnssStatusTransport extends GnssStatus.Callback {
        final GnssStatusCompat.Callback mCallback;

        GnssStatusTransport(GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.mCallback = callback;
        }

        public void onStarted() {
            this.mCallback.onStarted();
        }

        public void onStopped() {
            this.mCallback.onStopped();
        }

        public void onFirstFix(int ttffMillis) {
            this.mCallback.onFirstFix(ttffMillis);
        }

        public void onSatelliteStatusChanged(GnssStatus status) {
            this.mCallback.onSatelliteStatusChanged(GnssStatusCompat.wrap(status));
        }
    }

    private static class PreRGnssStatusTransport extends GnssStatus.Callback {
        final GnssStatusCompat.Callback mCallback;
        volatile Executor mExecutor;

        PreRGnssStatusTransport(GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.mCallback = callback;
        }

        public void register(Executor executor) {
            Preconditions.checkArgument(executor != null, "invalid null executor");
            Preconditions.checkState(this.mExecutor == null);
            this.mExecutor = executor;
        }

        public void unregister() {
            this.mExecutor = null;
        }

        public void onStarted() {
            Executor executor = this.mExecutor;
            if (executor == null) {
                return;
            }
            executor.execute(new LocationManagerCompat$PreRGnssStatusTransport$$ExternalSyntheticLambda0(this, executor));
        }

        public /* synthetic */ void lambda$onStarted$0$LocationManagerCompat$PreRGnssStatusTransport(Executor executor) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onStarted();
        }

        public void onStopped() {
            Executor executor = this.mExecutor;
            if (executor == null) {
                return;
            }
            executor.execute(new LocationManagerCompat$PreRGnssStatusTransport$$ExternalSyntheticLambda1(this, executor));
        }

        public /* synthetic */ void lambda$onStopped$1$LocationManagerCompat$PreRGnssStatusTransport(Executor executor) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onStopped();
        }

        public void onFirstFix(int ttffMillis) {
            Executor executor = this.mExecutor;
            if (executor == null) {
                return;
            }
            executor.execute(new LocationManagerCompat$PreRGnssStatusTransport$$ExternalSyntheticLambda2(this, executor, ttffMillis));
        }

        public /* synthetic */ void lambda$onFirstFix$2$LocationManagerCompat$PreRGnssStatusTransport(Executor executor, int ttffMillis) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onFirstFix(ttffMillis);
        }

        public void onSatelliteStatusChanged(GnssStatus status) {
            Executor executor = this.mExecutor;
            if (executor == null) {
                return;
            }
            executor.execute(new LocationManagerCompat$PreRGnssStatusTransport$$ExternalSyntheticLambda3(this, executor, status));
        }

        public /* synthetic */ void lambda$onSatelliteStatusChanged$3$LocationManagerCompat$PreRGnssStatusTransport(Executor executor, GnssStatus status) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onSatelliteStatusChanged(GnssStatusCompat.wrap(status));
        }
    }

    private static class GpsStatusTransport implements GpsStatus.Listener {
        final GnssStatusCompat.Callback mCallback;
        volatile Executor mExecutor;
        private final LocationManager mLocationManager;

        GpsStatusTransport(LocationManager locationManager, GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.mLocationManager = locationManager;
            this.mCallback = callback;
        }

        public void register(Executor executor) {
            Preconditions.checkState(this.mExecutor == null);
            this.mExecutor = executor;
        }

        public void unregister() {
            this.mExecutor = null;
        }

        public void onGpsStatusChanged(int event) {
            Executor executor = this.mExecutor;
            if (executor == null) {
            }
            switch (event) {
                case 1:
                    executor.execute(new LocationManagerCompat$GpsStatusTransport$$ExternalSyntheticLambda0(this, executor));
                    break;
                case 2:
                    executor.execute(new LocationManagerCompat$GpsStatusTransport$$ExternalSyntheticLambda1(this, executor));
                    break;
                case 3:
                    GpsStatus gpsStatus = this.mLocationManager.getGpsStatus((GpsStatus) null);
                    if (gpsStatus != null) {
                        int ttff = gpsStatus.getTimeToFirstFix();
                        executor.execute(new LocationManagerCompat$GpsStatusTransport$$ExternalSyntheticLambda2(this, executor, ttff));
                        break;
                    }
                    break;
                case 4:
                    GpsStatus gpsStatus2 = this.mLocationManager.getGpsStatus((GpsStatus) null);
                    if (gpsStatus2 != null) {
                        GnssStatusCompat gnssStatus = GnssStatusCompat.wrap(gpsStatus2);
                        executor.execute(new LocationManagerCompat$GpsStatusTransport$$ExternalSyntheticLambda3(this, executor, gnssStatus));
                        break;
                    }
                    break;
            }
        }

        public /* synthetic */ void lambda$onGpsStatusChanged$0$LocationManagerCompat$GpsStatusTransport(Executor executor) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onStarted();
        }

        public /* synthetic */ void lambda$onGpsStatusChanged$1$LocationManagerCompat$GpsStatusTransport(Executor executor) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onStopped();
        }

        public /* synthetic */ void lambda$onGpsStatusChanged$2$LocationManagerCompat$GpsStatusTransport(Executor executor, int ttff) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onFirstFix(ttff);
        }

        public /* synthetic */ void lambda$onGpsStatusChanged$3$LocationManagerCompat$GpsStatusTransport(Executor executor, GnssStatusCompat gnssStatus) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onSatelliteStatusChanged(gnssStatus);
        }
    }

    private static class Api31Impl {
        private Api31Impl() {
        }

        static boolean hasProvider(LocationManager locationManager, String provider) {
            return locationManager.hasProvider(provider);
        }

        static void requestLocationUpdates(LocationManager locationManager, String provider, LocationRequest locationRequest, Executor executor, LocationListener listener) {
            locationManager.requestLocationUpdates(provider, locationRequest, executor, listener);
        }
    }

    private static class Api30Impl {
        private Api30Impl() {
        }

        static void getCurrentLocation(LocationManager locationManager, String provider, CancellationSignal cancellationSignal, Executor executor, Consumer consumer) {
            android.os.CancellationSignal cancellationSignal2;
            if (cancellationSignal != null) {
                cancellationSignal2 = (android.os.CancellationSignal) cancellationSignal.getCancellationSignalObject();
            } else {
                cancellationSignal2 = null;
            }
            consumer.getClass();
            locationManager.getCurrentLocation(provider, cancellationSignal2, executor, new LocationManagerCompat$Api30Impl$$ExternalSyntheticLambda0(consumer));
        }
    }

    private static class Api28Impl {
        private Api28Impl() {
        }

        static boolean isLocationEnabled(LocationManager locationManager) {
            return locationManager.isLocationEnabled();
        }

        static String getGnssHardwareModelName(LocationManager locationManager) {
            return locationManager.getGnssHardwareModelName();
        }

        static int getGnssYearOfHardware(LocationManager locationManager) {
            return locationManager.getGnssYearOfHardware();
        }
    }

    private static final class CancellableLocationListener implements LocationListener {
        private Consumer mConsumer;
        private final Executor mExecutor;
        private final LocationManager mLocationManager;
        private final Handler mTimeoutHandler = new Handler(Looper.getMainLooper());
        Runnable mTimeoutRunnable;
        private boolean mTriggered;

        CancellableLocationListener(LocationManager locationManager, Executor executor, Consumer consumer) {
            this.mLocationManager = locationManager;
            this.mExecutor = executor;
            this.mConsumer = consumer;
        }

        public void cancel() {
            synchronized (this) {
                if (this.mTriggered) {
                    return;
                }
                this.mTriggered = true;
                cleanup();
            }
        }

        public void startTimeout(long timeoutMs) {
            synchronized (this) {
                if (this.mTriggered) {
                    return;
                }
                1 r0 = new 1();
                this.mTimeoutRunnable = r0;
                this.mTimeoutHandler.postDelayed(r0, timeoutMs);
            }
        }

        class 1 implements Runnable {
            1() {
            }

            public void run() {
                CancellableLocationListener.this.mTimeoutRunnable = null;
                CancellableLocationListener.this.onLocationChanged((Location) null);
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String p) {
            onLocationChanged((Location) null);
        }

        public void onLocationChanged(Location location) {
            synchronized (this) {
                if (this.mTriggered) {
                    return;
                }
                this.mTriggered = true;
                Consumer<Location> consumer = this.mConsumer;
                this.mExecutor.execute(new LocationManagerCompat$CancellableLocationListener$$ExternalSyntheticLambda0(consumer, location));
                cleanup();
            }
        }

        static /* synthetic */ void lambda$onLocationChanged$0(Consumer consumer, Location location) {
            consumer.accept(location);
        }

        private void cleanup() {
            this.mConsumer = null;
            this.mLocationManager.removeUpdates(this);
            Runnable runnable = this.mTimeoutRunnable;
            if (runnable != null) {
                this.mTimeoutHandler.removeCallbacks(runnable);
                this.mTimeoutRunnable = null;
            }
        }
    }

    private static final class InlineHandlerExecutor implements Executor {
        private final Handler mHandler;

        InlineHandlerExecutor(Handler handler) {
            this.mHandler = (Handler) Preconditions.checkNotNull(handler);
        }

        public void execute(Runnable command) {
            if (Looper.myLooper() == this.mHandler.getLooper()) {
                command.run();
            } else if (!this.mHandler.post((Runnable) Preconditions.checkNotNull(command))) {
                throw new RejectedExecutionException(this.mHandler + " is shutting down");
            }
        }
    }
}
