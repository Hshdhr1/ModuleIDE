package sun.nio.ch;

import java.net.ProtocolFamily;
import java.net.SocketOption;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.util.HashMap;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class SocketOptionRegistry {
    private SocketOptionRegistry() {
    }

    private static class RegistryKey {
        private final ProtocolFamily family;
        private final SocketOption name;

        RegistryKey(SocketOption socketOption, ProtocolFamily protocolFamily) {
            this.name = socketOption;
            this.family = protocolFamily;
        }

        public int hashCode() {
            return this.name.hashCode() + this.family.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof RegistryKey)) {
                return false;
            }
            RegistryKey registryKey = (RegistryKey) obj;
            return this.name == registryKey.name && this.family == registryKey.family;
        }
    }

    private static class LazyInitialization {
        static final Map options = options();

        private LazyInitialization() {
        }

        private static Map options() {
            HashMap hashMap = new HashMap();
            hashMap.put(new RegistryKey(StandardSocketOptions.SO_BROADCAST, Net.UNSPEC), new OptionKey(1, 6));
            hashMap.put(new RegistryKey(StandardSocketOptions.SO_KEEPALIVE, Net.UNSPEC), new OptionKey(1, 9));
            hashMap.put(new RegistryKey(StandardSocketOptions.SO_LINGER, Net.UNSPEC), new OptionKey(1, 13));
            hashMap.put(new RegistryKey(StandardSocketOptions.SO_SNDBUF, Net.UNSPEC), new OptionKey(1, 7));
            hashMap.put(new RegistryKey(StandardSocketOptions.SO_RCVBUF, Net.UNSPEC), new OptionKey(1, 8));
            hashMap.put(new RegistryKey(StandardSocketOptions.SO_REUSEADDR, Net.UNSPEC), new OptionKey(1, 2));
            hashMap.put(new RegistryKey(StandardSocketOptions.SO_REUSEPORT, Net.UNSPEC), new OptionKey(1, 15));
            hashMap.put(new RegistryKey(StandardSocketOptions.TCP_NODELAY, Net.UNSPEC), new OptionKey(6, 1));
            hashMap.put(new RegistryKey(StandardSocketOptions.IP_TOS, StandardProtocolFamily.INET), new OptionKey(0, 1));
            hashMap.put(new RegistryKey(StandardSocketOptions.IP_MULTICAST_IF, StandardProtocolFamily.INET), new OptionKey(0, 32));
            hashMap.put(new RegistryKey(StandardSocketOptions.IP_MULTICAST_TTL, StandardProtocolFamily.INET), new OptionKey(0, 33));
            hashMap.put(new RegistryKey(StandardSocketOptions.IP_MULTICAST_LOOP, StandardProtocolFamily.INET), new OptionKey(0, 34));
            hashMap.put(new RegistryKey(StandardSocketOptions.IP_TOS, StandardProtocolFamily.INET6), new OptionKey(41, 67));
            hashMap.put(new RegistryKey(StandardSocketOptions.IP_MULTICAST_IF, StandardProtocolFamily.INET6), new OptionKey(41, 17));
            hashMap.put(new RegistryKey(StandardSocketOptions.IP_MULTICAST_TTL, StandardProtocolFamily.INET6), new OptionKey(41, 18));
            hashMap.put(new RegistryKey(StandardSocketOptions.IP_MULTICAST_LOOP, StandardProtocolFamily.INET6), new OptionKey(41, 19));
            hashMap.put(new RegistryKey(ExtendedSocketOption.SO_OOBINLINE, Net.UNSPEC), new OptionKey(1, 10));
            return hashMap;
        }
    }

    public static OptionKey findOption(SocketOption socketOption, ProtocolFamily protocolFamily) {
        return (OptionKey) LazyInitialization.options.get(new RegistryKey(socketOption, protocolFamily));
    }
}
