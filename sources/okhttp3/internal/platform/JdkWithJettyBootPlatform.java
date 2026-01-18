package okhttp3.internal.platform;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import javax.net.ssl.SSLSocket;
import okhttp3.internal.Util;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes54.dex */
class JdkWithJettyBootPlatform extends Platform {
    private final Class clientProviderClass;
    private final Method getMethod;
    private final Method putMethod;
    private final Method removeMethod;
    private final Class serverProviderClass;

    JdkWithJettyBootPlatform(Method putMethod, Method getMethod, Method removeMethod, Class cls, Class cls2) {
        this.putMethod = putMethod;
        this.getMethod = getMethod;
        this.removeMethod = removeMethod;
        this.clientProviderClass = cls;
        this.serverProviderClass = cls2;
    }

    public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List list) {
        List<String> names = alpnProtocolNames(list);
        try {
            Object provider = Proxy.newProxyInstance(Platform.class.getClassLoader(), new Class[]{this.clientProviderClass, this.serverProviderClass}, new JettyNegoProvider(names));
            this.putMethod.invoke((Object) null, new Object[]{sslSocket, provider});
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw Util.assertionError("unable to set alpn", e);
        }
    }

    public void afterHandshake(SSLSocket sslSocket) {
        try {
            this.removeMethod.invoke((Object) null, new Object[]{sslSocket});
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw Util.assertionError("unable to remove alpn", e);
        }
    }

    public String getSelectedProtocol(SSLSocket socket) {
        try {
            JettyNegoProvider provider = (JettyNegoProvider) Proxy.getInvocationHandler(this.getMethod.invoke((Object) null, new Object[]{socket}));
            if (!provider.unsupported && provider.selected == null) {
                Platform.get().log(4, "ALPN callback dropped: HTTP/2 is disabled. Is alpn-boot on the boot class path?", null);
                return null;
            }
            if (provider.unsupported) {
                return null;
            }
            return provider.selected;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw Util.assertionError("unable to get selected protocol", e);
        }
    }

    public static Platform buildIfSupported() {
        try {
            Class<?> negoClass = Class.forName("org.eclipse.jetty.alpn.ALPN");
            Class<?> providerClass = Class.forName("org.eclipse.jetty.alpn.ALPN$Provider");
            Class<?> clientProviderClass = Class.forName("org.eclipse.jetty.alpn.ALPN$ClientProvider");
            Class<?> serverProviderClass = Class.forName("org.eclipse.jetty.alpn.ALPN$ServerProvider");
            Method putMethod = negoClass.getMethod("put", new Class[]{SSLSocket.class, providerClass});
            Method getMethod = negoClass.getMethod("get", new Class[]{SSLSocket.class});
            Method removeMethod = negoClass.getMethod("remove", new Class[]{SSLSocket.class});
            return new JdkWithJettyBootPlatform(putMethod, getMethod, removeMethod, clientProviderClass, serverProviderClass);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return null;
        }
    }

    private static class JettyNegoProvider implements InvocationHandler {
        private final List protocols;
        String selected;
        boolean unsupported;

        JettyNegoProvider(List list) {
            this.protocols = list;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            Class<?> returnType = method.getReturnType();
            if (args == null) {
                args = Util.EMPTY_STRING_ARRAY;
            }
            if (methodName.equals("supports") && Boolean.TYPE == returnType) {
                return true;
            }
            if (methodName.equals("unsupported") && Void.TYPE == returnType) {
                this.unsupported = true;
                return null;
            }
            if (methodName.equals("protocols") && args.length == 0) {
                return this.protocols;
            }
            if ((methodName.equals("selectProtocol") || methodName.equals("select")) && String.class == returnType && args.length == 1 && (args[0] instanceof List)) {
                List<String> peerProtocols = (List) args[0];
                int size = peerProtocols.size();
                for (int i = 0; i < size; i++) {
                    if (this.protocols.contains(peerProtocols.get(i))) {
                        String str = (String) peerProtocols.get(i);
                        this.selected = str;
                        return str;
                    }
                }
                String str2 = (String) this.protocols.get(0);
                this.selected = str2;
                return str2;
            }
            if ((methodName.equals("protocolSelected") || methodName.equals("selected")) && args.length == 1) {
                this.selected = (String) args[0];
                return null;
            }
            return method.invoke(this, args);
        }
    }
}
