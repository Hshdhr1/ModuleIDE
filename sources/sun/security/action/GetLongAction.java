package sun.security.action;

import java.security.PrivilegedAction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class GetLongAction implements PrivilegedAction {
    private boolean defaultSet = false;
    private long defaultVal;
    private String theProp;

    public GetLongAction(String str) {
        this.theProp = str;
    }

    public GetLongAction(String str, long j) {
        this.theProp = str;
        this.defaultVal = j;
    }

    public Long run() {
        Long l = Long.getLong(this.theProp);
        return (l == null && this.defaultSet) ? Long.valueOf(this.defaultVal) : l;
    }
}
