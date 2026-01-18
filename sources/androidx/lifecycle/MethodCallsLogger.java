package androidx.lifecycle;

import java.util.HashMap;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes13.dex */
public class MethodCallsLogger {
    private Map mCalledMethods = new HashMap();

    public boolean approveCall(String name, int type) {
        Integer nullableMask = (Integer) this.mCalledMethods.get(name);
        int mask = nullableMask != null ? nullableMask.intValue() : 0;
        boolean wasCalled = (mask & type) != 0;
        this.mCalledMethods.put(name, Integer.valueOf(mask | type));
        return !wasCalled;
    }
}
