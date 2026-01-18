package okhttp3;

import javax.annotation.Nullable;
import okio.ByteString;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes54.dex */
public abstract class WebSocketListener {
    public void onOpen(WebSocket webSocket, Response response) {
    }

    public void onMessage(WebSocket webSocket, String text) {
    }

    public void onMessage(WebSocket webSocket, ByteString bytes) {
    }

    public void onClosing(WebSocket webSocket, int code, String reason) {
    }

    public void onClosed(WebSocket webSocket, int code, String reason) {
    }

    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
    }
}
