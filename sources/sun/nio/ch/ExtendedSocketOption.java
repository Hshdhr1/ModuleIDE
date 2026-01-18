package sun.nio.ch;

import java.net.SocketOption;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class ExtendedSocketOption {
    static final SocketOption SO_OOBINLINE = new 1();

    private ExtendedSocketOption() {
    }

    class 1 implements SocketOption {
        1() {
        }

        public String name() {
            return "SO_OOBINLINE";
        }

        public Class type() {
            return Boolean.class;
        }

        public String toString() {
            return name();
        }
    }
}
