package org.apache.http.client;

import java.io.IOException;
import org.apache.http.HttpResponse;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes.dex */
public interface ResponseHandler {
    Object handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException;
}
