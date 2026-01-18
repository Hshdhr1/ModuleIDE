package androidx.startup;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import androidx.tracing.Trace;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes29.dex */
public final class AppInitializer {
    private static final String SECTION_NAME = "Startup";
    private static volatile AppInitializer sInstance;
    private static final Object sLock = new Object();
    final Context mContext;
    final Set mDiscovered = new HashSet();
    final Map mInitialized = new HashMap();

    AppInitializer(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static AppInitializer getInstance(Context context) {
        if (sInstance == null) {
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new AppInitializer(context);
                }
            }
        }
        return sInstance;
    }

    public Object initializeComponent(Class cls) {
        return doInitialize(cls, new HashSet());
    }

    public boolean isEagerlyInitialized(Class cls) {
        return this.mDiscovered.contains(cls);
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x007b A[Catch: all -> 0x0090, TRY_ENTER, TryCatch #0 {all -> 0x0090, blocks: (B:41:0x0009, B:6:0x0010, B:8:0x0017, B:10:0x001f, B:31:0x0068, B:32:0x006d, B:36:0x006e, B:38:0x007b, B:39:0x008f, B:12:0x0022, B:14:0x003c, B:15:0x0040, B:17:0x0046, B:19:0x0054, B:24:0x0058), top: B:40:0x0009, outer: #1, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0017 A[Catch: all -> 0x0090, TryCatch #0 {all -> 0x0090, blocks: (B:41:0x0009, B:6:0x0010, B:8:0x0017, B:10:0x001f, B:31:0x0068, B:32:0x006d, B:36:0x006e, B:38:0x007b, B:39:0x008f, B:12:0x0022, B:14:0x003c, B:15:0x0040, B:17:0x0046, B:19:0x0054, B:24:0x0058), top: B:40:0x0009, outer: #1, inners: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    java.lang.Object doInitialize(java.lang.Class r9, java.util.Set r10) {
        /*
            r8 = this;
            java.lang.Object r0 = androidx.startup.AppInitializer.sLock
            monitor-enter(r0)
            boolean r1 = androidx.tracing.Trace.isEnabled()     // Catch: java.lang.Throwable -> L96
            if (r1 == 0) goto L10
            java.lang.String r2 = r9.getSimpleName()     // Catch: java.lang.Throwable -> L90
            androidx.tracing.Trace.beginSection(r2)     // Catch: java.lang.Throwable -> L90
        L10:
            boolean r2 = r10.contains(r9)     // Catch: java.lang.Throwable -> L90
            r3 = 0
            if (r2 != 0) goto L7b
            java.util.Map r2 = r8.mInitialized     // Catch: java.lang.Throwable -> L90
            boolean r2 = r2.containsKey(r9)     // Catch: java.lang.Throwable -> L90
            if (r2 != 0) goto L6e
            r10.add(r9)     // Catch: java.lang.Throwable -> L90
            java.lang.Class[] r2 = new java.lang.Class[r3]     // Catch: java.lang.Throwable -> L67
            java.lang.reflect.Constructor r2 = r9.getDeclaredConstructor(r2)     // Catch: java.lang.Throwable -> L67
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.Throwable -> L67
            java.lang.Object r2 = r2.newInstance(r3)     // Catch: java.lang.Throwable -> L67
            r3 = r2
            androidx.startup.Initializer r3 = (androidx.startup.Initializer) r3     // Catch: java.lang.Throwable -> L67
            java.util.List r4 = r3.dependencies()     // Catch: java.lang.Throwable -> L67
            boolean r5 = r4.isEmpty()     // Catch: java.lang.Throwable -> L67
            if (r5 != 0) goto L58
            java.util.Iterator r5 = r4.iterator()     // Catch: java.lang.Throwable -> L67
        L40:
            boolean r6 = r5.hasNext()     // Catch: java.lang.Throwable -> L67
            if (r6 == 0) goto L58
            java.lang.Object r6 = r5.next()     // Catch: java.lang.Throwable -> L67
            java.lang.Class r6 = (java.lang.Class) r6     // Catch: java.lang.Throwable -> L67
            java.util.Map r7 = r8.mInitialized     // Catch: java.lang.Throwable -> L67
            boolean r7 = r7.containsKey(r6)     // Catch: java.lang.Throwable -> L67
            if (r7 != 0) goto L57
            r8.doInitialize(r6, r10)     // Catch: java.lang.Throwable -> L67
        L57:
            goto L40
        L58:
            android.content.Context r5 = r8.mContext     // Catch: java.lang.Throwable -> L67
            java.lang.Object r5 = r3.create(r5)     // Catch: java.lang.Throwable -> L67
            r10.remove(r9)     // Catch: java.lang.Throwable -> L67
            java.util.Map r6 = r8.mInitialized     // Catch: java.lang.Throwable -> L67
            r6.put(r9, r5)     // Catch: java.lang.Throwable -> L67
            goto L75
        L67:
            r2 = move-exception
            androidx.startup.StartupException r3 = new androidx.startup.StartupException     // Catch: java.lang.Throwable -> L90
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L90
            throw r3     // Catch: java.lang.Throwable -> L90
        L6e:
            java.util.Map r2 = r8.mInitialized     // Catch: java.lang.Throwable -> L90
            java.lang.Object r2 = r2.get(r9)     // Catch: java.lang.Throwable -> L90
            r5 = r2
        L75:
            androidx.tracing.Trace.endSection()     // Catch: java.lang.Throwable -> L96
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L96
            return r5
        L7b:
            java.lang.String r2 = "Cannot initialize %s. Cycle detected."
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> L90
            java.lang.String r5 = r9.getName()     // Catch: java.lang.Throwable -> L90
            r4[r3] = r5     // Catch: java.lang.Throwable -> L90
            java.lang.String r2 = java.lang.String.format(r2, r4)     // Catch: java.lang.Throwable -> L90
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L90
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L90
            throw r3     // Catch: java.lang.Throwable -> L90
        L90:
            r2 = move-exception
            androidx.tracing.Trace.endSection()     // Catch: java.lang.Throwable -> L96
            throw r2     // Catch: java.lang.Throwable -> L96
        L96:
            r1 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L96
            goto L9a
        L99:
            throw r1
        L9a:
            goto L99
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.startup.AppInitializer.doInitialize(java.lang.Class, java.util.Set):java.lang.Object");
    }

    void discoverAndInitialize() {
        try {
            try {
                Trace.beginSection("Startup");
                ComponentName provider = new ComponentName(this.mContext.getPackageName(), InitializationProvider.class.getName());
                ProviderInfo providerInfo = this.mContext.getPackageManager().getProviderInfo(provider, 128);
                Bundle metadata = providerInfo.metaData;
                String startup = this.mContext.getString(R.string.androidx_startup);
                if (metadata != null) {
                    HashSet hashSet = new HashSet();
                    Set<String> keys = metadata.keySet();
                    for (String key : keys) {
                        String value = metadata.getString(key, (String) null);
                        if (startup.equals(value)) {
                            Class<?> clazz = Class.forName(key);
                            if (Initializer.class.isAssignableFrom(clazz)) {
                                this.mDiscovered.add(clazz);
                                doInitialize(clazz, hashSet);
                            }
                        }
                    }
                }
            } finally {
                Trace.endSection();
            }
        } catch (PackageManager.NameNotFoundException | ClassNotFoundException e) {
            throw new StartupException((Throwable) e);
        }
    }
}
