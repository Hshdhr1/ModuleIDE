package sun.security.action;

import java.security.PrivilegedAction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class GetBooleanAction implements PrivilegedAction {
    private String theProp;

    public GetBooleanAction(String str) {
        this.theProp = str;
    }

    public Boolean run() {
        return Boolean.valueOf(Boolean.getBoolean(this.theProp));
    }
}
