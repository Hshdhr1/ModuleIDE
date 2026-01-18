package okhttp3.internal.tls;

import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.X509TrustManager;
import okhttp3.internal.platform.Platform;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes54.dex */
public abstract class CertificateChainCleaner {
    public abstract List clean(List list, String str) throws SSLPeerUnverifiedException;

    public static CertificateChainCleaner get(X509TrustManager trustManager) {
        return Platform.get().buildCertificateChainCleaner(trustManager);
    }

    public static CertificateChainCleaner get(X509Certificate... caCerts) {
        return new BasicCertificateChainCleaner(new BasicTrustRootIndex(caCerts));
    }
}
