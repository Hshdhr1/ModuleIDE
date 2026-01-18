package okhttp3;

import java.io.IOException;
import javax.annotation.Nullable;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes54.dex */
public interface Authenticator {
    public static final Authenticator NONE = new 1();

    @Nullable
    Request authenticate(Route route, Response response) throws IOException;

    final class 1 implements Authenticator {
        1() {
        }

        public Request authenticate(Route route, Response response) {
            return null;
        }
    }
}
