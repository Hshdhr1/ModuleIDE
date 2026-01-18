package libcore.content.type;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class MimeMap {
    private static volatile MemoizingSupplier instanceSupplier = new MemoizingSupplier(new MimeMap$$ExternalSyntheticLambda0());
    private final Map extToMime;
    private volatile int hashCode;
    private final Map mimeToExt;

    static /* bridge */ /* synthetic */ String -$$Nest$smtoLowerCase(String str) {
        return toLowerCase(str);
    }

    /* synthetic */ MimeMap(Map map, Map map2, MimeMap-IA r3) {
        this(map, map2);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder buildUpon() {
        return new Builder(this.mimeToExt, this.extToMime);
    }

    static /* synthetic */ MimeMap lambda$static$0() {
        return builder().addMimeMapping("application/pdf", "pdf").addMimeMapping("image/jpeg", "jpg").addMimeMapping("image/x-ms-bmp", "bmp").addMimeMapping("text/html", Arrays.asList(new String[]{"htm", "html"})).addMimeMapping("text/plain", Arrays.asList(new String[]{"text", "txt"})).addMimeMapping("text/x-java", "java").build();
    }

    private MimeMap(Map mimeToExt, Map extToMime) {
        this.hashCode = 0;
        mimeToExt.getClass();
        this.mimeToExt = mimeToExt;
        extToMime.getClass();
        this.extToMime = extToMime;
        for (Map.Entry entry : mimeToExt.entrySet()) {
            checkValidMimeType((String) entry.getKey());
            checkValidExtension((String) entry.getValue());
        }
        for (Map.Entry entry2 : this.extToMime.entrySet()) {
            checkValidExtension((String) entry2.getKey());
            checkValidMimeType((String) entry2.getValue());
        }
    }

    public static MimeMap getDefault() {
        MimeMap mimeMap = (MimeMap) instanceSupplier.get();
        mimeMap.getClass();
        return mimeMap;
    }

    public static void setDefaultSupplier(Supplier mimeMapSupplier) {
        mimeMapSupplier.getClass();
        instanceSupplier = new MemoizingSupplier(mimeMapSupplier);
    }

    public final boolean hasExtension(String extension) {
        return guessMimeTypeFromExtension(extension) != null;
    }

    public final String guessMimeTypeFromExtension(String extension) {
        if (extension == null) {
            return null;
        }
        return (String) this.extToMime.get(toLowerCase(extension));
    }

    public final boolean hasMimeType(String mimeType) {
        return guessExtensionFromMimeType(mimeType) != null;
    }

    public final String guessExtensionFromMimeType(String mimeType) {
        if (mimeType == null) {
            return null;
        }
        return (String) this.mimeToExt.get(toLowerCase(mimeType));
    }

    public Set mimeTypes() {
        return Collections.unmodifiableSet(this.mimeToExt.keySet());
    }

    public Set extensions() {
        return Collections.unmodifiableSet(this.extToMime.keySet());
    }

    private static String toLowerCase(String s) {
        return s.toLowerCase(Locale.ROOT);
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = this.mimeToExt.hashCode() + (this.extToMime.hashCode() * 31);
        }
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof MimeMap)) {
            return false;
        }
        MimeMap mimeMap = (MimeMap) obj;
        return hashCode() == mimeMap.hashCode() && this.mimeToExt.equals(mimeMap.mimeToExt) && this.extToMime.equals(mimeMap.extToMime);
    }

    public String toString() {
        return "MimeMap[" + this.mimeToExt + ", " + this.extToMime + "]";
    }

    public static final class Builder {
        private final Map extToMime;
        private final Map mimeToExt;

        Builder() {
            this.mimeToExt = new HashMap();
            this.extToMime = new HashMap();
        }

        Builder(Map mimeToExt, Map extToMime) {
            this.mimeToExt = new HashMap(mimeToExt);
            this.extToMime = new HashMap(extToMime);
        }

        static class Element {
            final boolean keepExisting;
            final String mimeOrExt;

            private Element(String spec, boolean isMimeSpec) {
                if (spec.startsWith("?")) {
                    this.keepExisting = true;
                    this.mimeOrExt = MimeMap.-$$Nest$smtoLowerCase(spec.substring(1));
                } else {
                    this.keepExisting = false;
                    this.mimeOrExt = MimeMap.-$$Nest$smtoLowerCase(spec);
                }
                if (isMimeSpec) {
                    MimeMap.checkValidMimeType(this.mimeOrExt);
                } else {
                    MimeMap.checkValidExtension(this.mimeOrExt);
                }
            }

            public static Element ofMimeSpec(String s) {
                return new Element(s, true);
            }

            public static Element ofExtensionSpec(String s) {
                return new Element(s, false);
            }
        }

        private static String maybePut(Map map, Element keyElement, String value) {
            if (keyElement.keepExisting) {
                return (String) map.putIfAbsent(keyElement.mimeOrExt, value);
            }
            return (String) map.put(keyElement.mimeOrExt, value);
        }

        public Builder addMimeMapping(String mimeSpec, List extensionSpecs) {
            Element ofMimeSpec = Element.ofMimeSpec(mimeSpec);
            if (!extensionSpecs.isEmpty()) {
                Element ofExtensionSpec = Element.ofExtensionSpec((String) extensionSpecs.get(0));
                maybePut(this.mimeToExt, ofMimeSpec, ofExtensionSpec.mimeOrExt);
                maybePut(this.extToMime, ofExtensionSpec, ofMimeSpec.mimeOrExt);
                Iterator it = extensionSpecs.subList(1, extensionSpecs.size()).iterator();
                while (it.hasNext()) {
                    maybePut(this.extToMime, Element.ofExtensionSpec((String) it.next()), ofMimeSpec.mimeOrExt);
                }
            }
            return this;
        }

        public Builder addMimeMapping(String mimeSpec, String extensionSpec) {
            return addMimeMapping(mimeSpec, Collections.singletonList(extensionSpec));
        }

        public MimeMap build() {
            return new MimeMap(this.mimeToExt, this.extToMime, null);
        }

        public String toString() {
            return "MimeMap.Builder[" + this.mimeToExt + ", " + this.extToMime + "]";
        }
    }

    private static boolean isValidMimeTypeOrExtension(String s) {
        return s != null && !s.isEmpty() && s.indexOf(63) < 0 && s.indexOf(32) < 0 && s.indexOf(9) < 0 && s.equals(toLowerCase(s));
    }

    static void checkValidMimeType(String s) {
        if (!isValidMimeTypeOrExtension(s) || s.indexOf(47) < 0) {
            throw new IllegalArgumentException("Invalid MIME type: " + s);
        }
    }

    static void checkValidExtension(String s) {
        if (!isValidMimeTypeOrExtension(s) || s.indexOf(47) >= 0) {
            throw new IllegalArgumentException("Invalid extension: " + s);
        }
    }

    private static final class MemoizingSupplier implements Supplier {
        private volatile Supplier mDelegate;
        private volatile boolean mInitialized = false;
        private volatile Object mInstance;

        public MemoizingSupplier(Supplier delegate) {
            this.mDelegate = delegate;
        }

        public Object get() {
            if (!this.mInitialized) {
                synchronized (this) {
                    if (!this.mInitialized) {
                        this.mInstance = this.mDelegate.get();
                        this.mDelegate = null;
                        this.mInitialized = true;
                    }
                }
            }
            return this.mInstance;
        }
    }
}
