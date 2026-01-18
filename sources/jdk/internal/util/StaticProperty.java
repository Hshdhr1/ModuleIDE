package jdk.internal.util;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class StaticProperty {
    private static final String JAVA_HOME = initProperty("java.home");
    private static final String USER_HOME = initProperty("user.home");
    private static final String USER_DIR = initProperty("user.dir");
    private static final String USER_NAME = initProperty("user.name");
    private static final String JDK_SERIAL_FILTER = System.getProperty("jdk.serialFilter");

    private StaticProperty() {
    }

    private static String initProperty(String str) {
        String property = System.getProperty(str);
        if (property != null) {
            return property;
        }
        throw new InternalError("null property: " + str);
    }

    public static String javaHome() {
        return JAVA_HOME;
    }

    public static String userHome() {
        return USER_HOME;
    }

    public static String userDir() {
        return USER_DIR;
    }

    public static String userName() {
        return USER_NAME;
    }

    public static String jdkSerialFilter() {
        return JDK_SERIAL_FILTER;
    }
}
