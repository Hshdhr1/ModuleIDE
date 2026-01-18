package sun.security.action;

import java.security.AccessController;
import java.security.PrivilegedAction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class GetIntegerAction implements PrivilegedAction {
    private boolean defaultSet;
    private int defaultVal;
    private String theProp;

    public GetIntegerAction(String str) {
        this.theProp = str;
    }

    public GetIntegerAction(String str, int i) {
        this.theProp = str;
        this.defaultVal = i;
        this.defaultSet = true;
    }

    public Integer run() {
        Integer integer = Integer.getInteger(this.theProp);
        return (integer == null && this.defaultSet) ? Integer.valueOf(this.defaultVal) : integer;
    }

    public static Integer privilegedGetProperty(String str) {
        if (System.getSecurityManager() == null) {
            return Integer.getInteger(str);
        }
        return (Integer) AccessController.doPrivileged(new GetIntegerAction(str));
    }

    public static Integer privilegedGetProperty(String str, int i) {
        Integer num;
        if (System.getSecurityManager() == null) {
            num = Integer.getInteger(str);
        } else {
            num = (Integer) AccessController.doPrivileged(new GetIntegerAction(str));
        }
        if (num != null) {
            i = num.intValue();
        }
        return Integer.valueOf(i);
    }
}
