package okhttp3.internal.http2;

import java.io.IOException;
import java.util.List;
import okio.BufferedSource;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes54.dex */
public interface PushObserver {
    public static final PushObserver CANCEL = new 1();

    boolean onData(int i, BufferedSource bufferedSource, int i2, boolean z) throws IOException;

    boolean onHeaders(int i, List list, boolean z);

    boolean onRequest(int i, List list);

    void onReset(int i, ErrorCode errorCode);

    final class 1 implements PushObserver {
        1() {
        }

        public boolean onRequest(int streamId, List list) {
            return true;
        }

        public boolean onHeaders(int streamId, List list, boolean last) {
            return true;
        }

        public boolean onData(int streamId, BufferedSource source, int byteCount, boolean last) throws IOException {
            source.skip(byteCount);
            return true;
        }

        public void onReset(int streamId, ErrorCode errorCode) {
        }
    }
}
