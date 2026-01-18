package okhttp3;

import java.util.Collections;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes54.dex */
public interface CookieJar {
    public static final CookieJar NO_COOKIES = new 1();

    List loadForRequest(HttpUrl httpUrl);

    void saveFromResponse(HttpUrl httpUrl, List list);

    final class 1 implements CookieJar {
        1() {
        }

        public void saveFromResponse(HttpUrl url, List list) {
        }

        public List loadForRequest(HttpUrl url) {
            return Collections.emptyList();
        }
    }
}
