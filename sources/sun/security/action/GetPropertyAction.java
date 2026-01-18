package sun.security.action;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class GetPropertyAction implements PrivilegedAction {
    private String defaultVal;
    private String theProp;

    public GetPropertyAction(String str) {
        this.theProp = str;
    }

    public GetPropertyAction(String str, String str2) {
        this.theProp = str;
        this.defaultVal = str2;
    }

    public String run() {
        String property = System.getProperty(this.theProp);
        return property == null ? this.defaultVal : property;
    }

    public static String privilegedGetProperty(String str) {
        if (System.getSecurityManager() == null) {
            return System.getProperty(str);
        }
        return (String) AccessController.doPrivileged(new GetPropertyAction(str));
    }

    public static String privilegedGetProperty(String str, String str2) {
        if (System.getSecurityManager() == null) {
            return System.getProperty(str, str2);
        }
        return (String) AccessController.doPrivileged(new GetPropertyAction(str, str2));
    }

    public static Properties privilegedGetProperties() {
        if (System.getSecurityManager() == null) {
            return System.getProperties();
        }
        return (Properties) AccessController.doPrivileged(new 1());
    }

    class 1 implements PrivilegedAction {
        1() {
        }

        public Properties run() {
            return System.getProperties();
        }
    }
}
