package java.util.stream;

import java.security.AccessController;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class Tripwire {
    static final boolean ENABLED = ((Boolean) AccessController.doPrivileged(new Tripwire$$ExternalSyntheticLambda0())).booleanValue();
    private static final String TRIPWIRE_PROPERTY = "org.openjdk.java.util.stream.tripwire";

    static /* synthetic */ Boolean lambda$static$0() {
        return Boolean.valueOf(Boolean.getBoolean("org.openjdk.java.util.stream.tripwire"));
    }

    private Tripwire() {
    }

    static void trip(Class cls, String str) {
        throw new UnsupportedOperationException(cls + " tripwire tripped but logging not supported: " + str);
    }
}
