package java.nio.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.spi.CharsetProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Consumer;
import jdk.internal.misc.VM;
import sun.nio.cs.ThreadLocalCoders;
import sun.nio.cs.UTF_8;
import sun.security.action.GetPropertyAction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class Charset implements Comparable {
    private static volatile Object[] cache1;
    private static volatile Object[] cache2;
    private static volatile Charset defaultCharset;
    private Set aliasSet = null;
    private final String[] aliases;
    private final String name;
    private static final CharsetProvider standardProvider = new sun.nio.cs.StandardCharsets();
    private static final String[] zeroAliases = new String[0];
    private static ThreadLocal gate = new ThreadLocal();

    static /* bridge */ /* synthetic */ CharsetProvider -$$Nest$sfgetstandardProvider() {
        return standardProvider;
    }

    static /* bridge */ /* synthetic */ Iterator -$$Nest$smproviders() {
        return providers();
    }

    static /* bridge */ /* synthetic */ void -$$Nest$smput(Iterator it, Map map) {
        put(it, map);
    }

    public boolean canEncode() {
        return true;
    }

    public abstract boolean contains(Charset charset);

    public abstract CharsetDecoder newDecoder();

    public abstract CharsetEncoder newEncoder();

    private static void checkName(String str) {
        int length = str.length();
        if (length == 0) {
            throw new IllegalCharsetNameException(str);
        }
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if ((charAt < 'A' || charAt > 'Z') && ((charAt < 'a' || charAt > 'z') && ((charAt < '0' || charAt > '9') && ((charAt != '-' || i == 0) && ((charAt != '+' || i == 0) && ((charAt != ':' || i == 0) && ((charAt != '_' || i == 0) && (charAt != '.' || i == 0)))))))) {
                throw new IllegalCharsetNameException(str);
            }
        }
    }

    private static void cache(String str, Charset charset) {
        cache2 = cache1;
        cache1 = new Object[]{str, charset};
    }

    class 1 implements Iterator {
        ClassLoader cl;
        Iterator i;
        CharsetProvider next;
        ServiceLoader sl;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.-CC.$default$forEachRemaining(this, consumer);
        }

        1() {
            ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
            this.cl = systemClassLoader;
            ServiceLoader load = ServiceLoader.load(CharsetProvider.class, systemClassLoader);
            this.sl = load;
            this.i = load.iterator();
            this.next = null;
        }

        private boolean getNext() {
            while (this.next == null) {
                try {
                } catch (ServiceConfigurationError e) {
                    if (!(e.getCause() instanceof SecurityException)) {
                        throw e;
                    }
                }
                if (!this.i.hasNext()) {
                    return false;
                }
                this.next = (CharsetProvider) this.i.next();
            }
            return true;
        }

        public boolean hasNext() {
            return getNext();
        }

        public CharsetProvider next() {
            if (!getNext()) {
                throw new NoSuchElementException();
            }
            CharsetProvider charsetProvider = this.next;
            this.next = null;
            return charsetProvider;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static Iterator providers() {
        return new 1();
    }

    private static Charset lookupViaProviders(String str) {
        if (!VM.isBooted() || gate.get() != null) {
            return null;
        }
        try {
            ThreadLocal threadLocal = gate;
            threadLocal.set(threadLocal);
            return (Charset) AccessController.doPrivileged(new 2(str));
        } finally {
            gate.set((Object) null);
        }
    }

    class 2 implements PrivilegedAction {
        final /* synthetic */ String val$charsetName;

        2(String str) {
            this.val$charsetName = str;
        }

        public Charset run() {
            Iterator it = Charset.-$$Nest$smproviders();
            while (it.hasNext()) {
                Charset charsetForName = ((CharsetProvider) it.next()).charsetForName(this.val$charsetName);
                if (charsetForName != null) {
                    return charsetForName;
                }
            }
            return null;
        }
    }

    private static class ExtendedProviderHolder {
        static final CharsetProvider[] extendedProviders = extendedProviders();

        private ExtendedProviderHolder() {
        }

        class 1 implements PrivilegedAction {
            1() {
            }

            public CharsetProvider[] run() {
                CharsetProvider[] charsetProviderArr = new CharsetProvider[1];
                Iterator it = ServiceLoader.loadInstalled(CharsetProvider.class).iterator();
                int i = 0;
                while (it.hasNext()) {
                    CharsetProvider charsetProvider = (CharsetProvider) it.next();
                    int i2 = i + 1;
                    if (i2 > charsetProviderArr.length) {
                        charsetProviderArr = (CharsetProvider[]) Arrays.copyOf(charsetProviderArr, charsetProviderArr.length << 1);
                    }
                    charsetProviderArr[i] = charsetProvider;
                    i = i2;
                }
                return i == charsetProviderArr.length ? charsetProviderArr : (CharsetProvider[]) Arrays.copyOf(charsetProviderArr, i);
            }
        }

        private static CharsetProvider[] extendedProviders() {
            return (CharsetProvider[]) AccessController.doPrivileged(new 1());
        }
    }

    private static Charset lookupExtendedCharset(String str) {
        if (!VM.isBooted()) {
            return null;
        }
        for (CharsetProvider charsetProvider : ExtendedProviderHolder.extendedProviders) {
            Charset charsetForName = charsetProvider.charsetForName(str);
            if (charsetForName != null) {
                return charsetForName;
            }
        }
        return null;
    }

    private static Charset lookup(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Null charset name");
        }
        Object[] objArr = cache1;
        if (objArr != null && str.equals(objArr[0])) {
            return (Charset) objArr[1];
        }
        return lookup2(str);
    }

    private static Charset lookup2(String str) {
        Object[] objArr = cache2;
        if (objArr != null && str.equals(objArr[0])) {
            cache2 = cache1;
            cache1 = objArr;
            return (Charset) objArr[1];
        }
        Charset charsetForName = standardProvider.charsetForName(str);
        if (charsetForName != null || (charsetForName = lookupExtendedCharset(str)) != null || (charsetForName = lookupViaProviders(str)) != null) {
            cache(str, charsetForName);
            return charsetForName;
        }
        checkName(str);
        return null;
    }

    public static boolean isSupported(String str) {
        return lookup(str) != null;
    }

    public static Charset forName(String str) {
        Charset lookup = lookup(str);
        if (lookup != null) {
            return lookup;
        }
        throw new UnsupportedCharsetException(str);
    }

    private static void put(Iterator it, Map map) {
        while (it.hasNext()) {
            Charset charset = (Charset) it.next();
            if (!map.containsKey(charset.name())) {
                map.put(charset.name(), charset);
            }
        }
    }

    class 3 implements PrivilegedAction {
        3() {
        }

        public SortedMap run() {
            TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            Charset.-$$Nest$smput(Charset.-$$Nest$sfgetstandardProvider().charsets(), treeMap);
            for (CharsetProvider charsetProvider : ExtendedProviderHolder.extendedProviders) {
                Charset.-$$Nest$smput(charsetProvider.charsets(), treeMap);
            }
            Iterator it = Charset.-$$Nest$smproviders();
            while (it.hasNext()) {
                Charset.-$$Nest$smput(((CharsetProvider) it.next()).charsets(), treeMap);
            }
            return Collections.unmodifiableSortedMap(treeMap);
        }
    }

    public static SortedMap availableCharsets() {
        return (SortedMap) AccessController.doPrivileged(new 3());
    }

    public static Charset defaultCharset() {
        if (defaultCharset == null) {
            synchronized (Charset.class) {
                Charset lookup = lookup(GetPropertyAction.privilegedGetProperty("file.encoding"));
                if (lookup != null) {
                    defaultCharset = lookup;
                } else {
                    defaultCharset = UTF_8.INSTANCE;
                }
            }
        }
        return defaultCharset;
    }

    protected Charset(String str, String[] strArr) {
        String[] strArr2 = (String[]) Charset$$ExternalSyntheticBackport1.m(strArr, zeroAliases);
        if (str != "ISO-8859-1" && str != "US-ASCII" && str != "UTF-8") {
            checkName(str);
            for (String str2 : strArr2) {
                checkName(str2);
            }
        }
        this.name = str;
        this.aliases = strArr2;
    }

    public final String name() {
        return this.name;
    }

    public final Set aliases() {
        Set set = this.aliasSet;
        if (set != null) {
            return set;
        }
        int length = this.aliases.length;
        HashSet hashSet = new HashSet(length);
        for (int i = 0; i < length; i++) {
            hashSet.add(this.aliases[i]);
        }
        Set unmodifiableSet = Collections.unmodifiableSet(hashSet);
        this.aliasSet = unmodifiableSet;
        return unmodifiableSet;
    }

    public String displayName() {
        return this.name;
    }

    public final boolean isRegistered() {
        return (this.name.startsWith("X-") || this.name.startsWith("x-")) ? false : true;
    }

    public String displayName(Locale locale) {
        return this.name;
    }

    public final CharBuffer decode(ByteBuffer byteBuffer) {
        try {
            return ThreadLocalCoders.decoderFor(this).onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).decode(byteBuffer);
        } catch (CharacterCodingException e) {
            throw new Error(e);
        }
    }

    public final ByteBuffer encode(CharBuffer charBuffer) {
        try {
            return ThreadLocalCoders.encoderFor(this).onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).encode(charBuffer);
        } catch (CharacterCodingException e) {
            throw new Error(e);
        }
    }

    public final ByteBuffer encode(String str) {
        return encode(CharBuffer.wrap(str));
    }

    public final int compareTo(Charset charset) {
        return name().compareToIgnoreCase(charset.name());
    }

    public final int hashCode() {
        return name().hashCode();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Charset)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return this.name.equals(((Charset) obj).name());
    }

    public final String toString() {
        return name();
    }
}
