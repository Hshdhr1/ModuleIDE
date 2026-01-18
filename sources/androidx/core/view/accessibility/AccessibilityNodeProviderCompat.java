package androidx.core.view.accessibility;

import android.os.Build;
import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.ArrayList;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public class AccessibilityNodeProviderCompat {
    public static final int HOST_VIEW_ID = -1;
    private final Object mProvider;

    static class AccessibilityNodeProviderApi16 extends AccessibilityNodeProvider {
        final AccessibilityNodeProviderCompat mCompat;

        AccessibilityNodeProviderApi16(AccessibilityNodeProviderCompat compat) {
            this.mCompat = compat;
        }

        public AccessibilityNodeInfo createAccessibilityNodeInfo(int virtualViewId) {
            AccessibilityNodeInfoCompat compatInfo = this.mCompat.createAccessibilityNodeInfo(virtualViewId);
            if (compatInfo == null) {
                return null;
            }
            return compatInfo.unwrap();
        }

        public List findAccessibilityNodeInfosByText(String text, int virtualViewId) {
            List<AccessibilityNodeInfoCompat> compatInfos = this.mCompat.findAccessibilityNodeInfosByText(text, virtualViewId);
            if (compatInfos == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            int infoCount = compatInfos.size();
            for (int i = 0; i < infoCount; i++) {
                AccessibilityNodeInfoCompat infoCompat = (AccessibilityNodeInfoCompat) compatInfos.get(i);
                arrayList.add(infoCompat.unwrap());
            }
            return arrayList;
        }

        public boolean performAction(int virtualViewId, int action, Bundle arguments) {
            return this.mCompat.performAction(virtualViewId, action, arguments);
        }
    }

    static class AccessibilityNodeProviderApi19 extends AccessibilityNodeProviderApi16 {
        AccessibilityNodeProviderApi19(AccessibilityNodeProviderCompat compat) {
            super(compat);
        }

        public AccessibilityNodeInfo findFocus(int focus) {
            AccessibilityNodeInfoCompat compatInfo = this.mCompat.findFocus(focus);
            if (compatInfo == null) {
                return null;
            }
            return compatInfo.unwrap();
        }
    }

    static class AccessibilityNodeProviderApi26 extends AccessibilityNodeProviderApi19 {
        AccessibilityNodeProviderApi26(AccessibilityNodeProviderCompat compat) {
            super(compat);
        }

        public void addExtraDataToAccessibilityNodeInfo(int virtualViewId, AccessibilityNodeInfo info, String extraDataKey, Bundle arguments) {
            this.mCompat.addExtraDataToAccessibilityNodeInfo(virtualViewId, AccessibilityNodeInfoCompat.wrap(info), extraDataKey, arguments);
        }
    }

    public AccessibilityNodeProviderCompat() {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mProvider = new AccessibilityNodeProviderApi26(this);
            return;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            this.mProvider = new AccessibilityNodeProviderApi19(this);
        } else if (Build.VERSION.SDK_INT >= 16) {
            this.mProvider = new AccessibilityNodeProviderApi16(this);
        } else {
            this.mProvider = null;
        }
    }

    public AccessibilityNodeProviderCompat(Object provider) {
        this.mProvider = provider;
    }

    public Object getProvider() {
        return this.mProvider;
    }

    public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int virtualViewId) {
        return null;
    }

    public boolean performAction(int virtualViewId, int action, Bundle arguments) {
        return false;
    }

    public List findAccessibilityNodeInfosByText(String text, int virtualViewId) {
        return null;
    }

    public AccessibilityNodeInfoCompat findFocus(int focus) {
        return null;
    }

    public void addExtraDataToAccessibilityNodeInfo(int virtualViewId, AccessibilityNodeInfoCompat info, String extraDataKey, Bundle arguments) {
    }
}
