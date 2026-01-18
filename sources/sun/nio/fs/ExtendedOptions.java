package sun.nio.fs;

import java.nio.file.CopyOption;
import java.nio.file.OpenOption;
import java.nio.file.WatchEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class ExtendedOptions {
    private static final Map internalToExternal = new ConcurrentHashMap();
    public static final InternalOption INTERRUPTIBLE = new InternalOption();
    public static final InternalOption NOSHARE_READ = new InternalOption();
    public static final InternalOption NOSHARE_WRITE = new InternalOption();
    public static final InternalOption NOSHARE_DELETE = new InternalOption();
    public static final InternalOption FILE_TREE = new InternalOption();
    public static final InternalOption DIRECT = new InternalOption();
    public static final InternalOption SENSITIVITY_HIGH = new InternalOption();
    public static final InternalOption SENSITIVITY_MEDIUM = new InternalOption();
    public static final InternalOption SENSITIVITY_LOW = new InternalOption();

    static /* bridge */ /* synthetic */ Map -$$Nest$sfgetinternalToExternal() {
        return internalToExternal;
    }

    private static final class Wrapper {
        private final Object option;
        private final Object param;

        static /* bridge */ /* synthetic */ Object -$$Nest$fgetoption(Wrapper wrapper) {
            return wrapper.option;
        }

        Wrapper(Object obj, Object obj2) {
            this.option = obj;
            this.param = obj2;
        }

        Object parameter() {
            return this.param;
        }
    }

    public static final class InternalOption {
        InternalOption() {
        }

        private void registerInternal(Object obj, Object obj2) {
            ExtendedOptions.-$$Nest$sfgetinternalToExternal().put(this, new Wrapper(obj, obj2));
        }

        public void register(OpenOption openOption) {
            registerInternal(openOption, null);
        }

        public void register(CopyOption copyOption) {
            registerInternal(copyOption, null);
        }

        public void register(WatchEvent.Modifier modifier) {
            registerInternal(modifier, null);
        }

        public void register(WatchEvent.Modifier modifier, Object obj) {
            registerInternal(modifier, obj);
        }

        public boolean matches(Object obj) {
            Wrapper wrapper = (Wrapper) ExtendedOptions.-$$Nest$sfgetinternalToExternal().get(this);
            return wrapper != null && obj == Wrapper.-$$Nest$fgetoption(wrapper);
        }

        public Object parameter() {
            Wrapper wrapper = (Wrapper) ExtendedOptions.-$$Nest$sfgetinternalToExternal().get(this);
            if (wrapper == null) {
                return null;
            }
            return wrapper.parameter();
        }
    }
}
