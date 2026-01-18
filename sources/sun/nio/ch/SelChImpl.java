package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.channels.Channel;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface SelChImpl extends Channel {
    FileDescriptor getFD();

    int getFDVal();

    void kill() throws IOException;

    boolean translateAndSetReadyOps(int i, SelectionKeyImpl selectionKeyImpl);

    boolean translateAndUpdateReadyOps(int i, SelectionKeyImpl selectionKeyImpl);

    int translateInterestOps(int i);
}
