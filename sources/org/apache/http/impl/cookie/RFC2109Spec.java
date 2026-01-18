package org.apache.http.impl.cookie;

import java.util.List;
import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.util.CharArrayBuffer;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes.dex */
public class RFC2109Spec extends CookieSpecBase {
    public RFC2109Spec(String[] datepatterns, boolean oneHeader) {
        throw new RuntimeException("Stub!");
    }

    public RFC2109Spec() {
        throw new RuntimeException("Stub!");
    }

    public List parse(Header header, CookieOrigin origin) throws MalformedCookieException {
        throw new RuntimeException("Stub!");
    }

    public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
        throw new RuntimeException("Stub!");
    }

    public List formatCookies(List list) {
        throw new RuntimeException("Stub!");
    }

    protected void formatParamAsVer(CharArrayBuffer buffer, String name, String value, int version) {
        throw new RuntimeException("Stub!");
    }

    protected void formatCookieAsVer(CharArrayBuffer buffer, Cookie cookie, int version) {
        throw new RuntimeException("Stub!");
    }

    public int getVersion() {
        throw new RuntimeException("Stub!");
    }

    public Header getVersionHeader() {
        throw new RuntimeException("Stub!");
    }
}
