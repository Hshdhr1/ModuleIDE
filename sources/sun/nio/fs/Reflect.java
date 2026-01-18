package sun.nio.fs;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class Reflect {
    private Reflect() {
    }

    class 1 implements PrivilegedAction {
        final /* synthetic */ AccessibleObject val$ao;

        1(AccessibleObject accessibleObject) {
            this.val$ao = accessibleObject;
        }

        public Object run() {
            this.val$ao.setAccessible(true);
            return null;
        }
    }

    private static void setAccessible(AccessibleObject accessibleObject) {
        AccessController.doPrivileged(new 1(accessibleObject));
    }

    static Field lookupField(String str, String str2) {
        try {
            Field declaredField = Class.forName(str).getDeclaredField(str2);
            setAccessible(declaredField);
            return declaredField;
        } catch (ClassNotFoundException e) {
            throw new AssertionError(e);
        } catch (NoSuchFieldException e2) {
            throw new AssertionError(e2);
        }
    }
}
