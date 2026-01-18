package androidx.core.content.pm;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import androidx.core.content.ContextCompat;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutInfoCompatSaver;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.util.Preconditions;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public class ShortcutManagerCompat {
    static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    private static final int DEFAULT_MAX_ICON_DIMENSION_DP = 96;
    private static final int DEFAULT_MAX_ICON_DIMENSION_LOWRAM_DP = 48;
    public static final String EXTRA_SHORTCUT_ID = "android.intent.extra.shortcut.ID";
    public static final int FLAG_MATCH_CACHED = 8;
    public static final int FLAG_MATCH_DYNAMIC = 2;
    public static final int FLAG_MATCH_MANIFEST = 1;
    public static final int FLAG_MATCH_PINNED = 4;
    static final String INSTALL_SHORTCUT_PERMISSION = "com.android.launcher.permission.INSTALL_SHORTCUT";
    private static final String SHORTCUT_LISTENER_INTENT_FILTER_ACTION = "androidx.core.content.pm.SHORTCUT_LISTENER";
    private static final String SHORTCUT_LISTENER_META_DATA_KEY = "androidx.core.content.pm.shortcut_listener_impl";
    private static volatile ShortcutInfoCompatSaver sShortcutInfoCompatSaver = null;
    private static volatile List sShortcutInfoChangeListeners = null;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShortcutMatchFlags {
    }

    private ShortcutManagerCompat() {
    }

    public static boolean isRequestPinShortcutSupported(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).isRequestPinShortcutSupported();
        }
        if (ContextCompat.checkSelfPermission(context, "com.android.launcher.permission.INSTALL_SHORTCUT") != 0) {
            return false;
        }
        for (ResolveInfo info : context.getPackageManager().queryBroadcastReceivers(new Intent("com.android.launcher.action.INSTALL_SHORTCUT"), 0)) {
            String permission = info.activityInfo.permission;
            if (TextUtils.isEmpty(permission) || "com.android.launcher.permission.INSTALL_SHORTCUT".equals(permission)) {
                return true;
            }
        }
        return false;
    }

    public static boolean requestPinShortcut(Context context, ShortcutInfoCompat shortcut, IntentSender callback) {
        if (Build.VERSION.SDK_INT >= 26) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).requestPinShortcut(shortcut.toShortcutInfo(), callback);
        }
        if (!isRequestPinShortcutSupported(context)) {
            return false;
        }
        Intent intent = shortcut.addToIntent(new Intent("com.android.launcher.action.INSTALL_SHORTCUT"));
        if (callback == null) {
            context.sendBroadcast(intent);
            return true;
        }
        context.sendOrderedBroadcast(intent, (String) null, new 1(callback), (Handler) null, -1, (String) null, (Bundle) null);
        return true;
    }

    class 1 extends BroadcastReceiver {
        final /* synthetic */ IntentSender val$callback;

        1(IntentSender intentSender) {
            this.val$callback = intentSender;
        }

        public void onReceive(Context context, Intent intent) {
            try {
                this.val$callback.sendIntent(context, 0, (Intent) null, (IntentSender.OnFinished) null, (Handler) null);
            } catch (IntentSender.SendIntentException e) {
            }
        }
    }

    public static Intent createShortcutResultIntent(Context context, ShortcutInfoCompat shortcut) {
        Intent result = null;
        if (Build.VERSION.SDK_INT >= 26) {
            result = ((ShortcutManager) context.getSystemService(ShortcutManager.class)).createShortcutResultIntent(shortcut.toShortcutInfo());
        }
        if (result == null) {
            result = new Intent();
        }
        return shortcut.addToIntent(result);
    }

    public static List getShortcuts(Context context, int matchFlags) {
        if (Build.VERSION.SDK_INT >= 30) {
            List<ShortcutInfo> shortcuts = ((ShortcutManager) context.getSystemService(ShortcutManager.class)).getShortcuts(matchFlags);
            return ShortcutInfoCompat.fromShortcuts(context, shortcuts);
        }
        if (Build.VERSION.SDK_INT >= 25) {
            ShortcutManager manager = (ShortcutManager) context.getSystemService(ShortcutManager.class);
            ArrayList arrayList = new ArrayList();
            if ((matchFlags & 1) != 0) {
                arrayList.addAll(manager.getManifestShortcuts());
            }
            if ((matchFlags & 2) != 0) {
                arrayList.addAll(manager.getDynamicShortcuts());
            }
            if ((matchFlags & 4) != 0) {
                arrayList.addAll(manager.getPinnedShortcuts());
            }
            return ShortcutInfoCompat.fromShortcuts(context, arrayList);
        }
        if ((matchFlags & 2) != 0) {
            try {
                return getShortcutInfoSaverInstance(context).getShortcuts();
            } catch (Exception e) {
            }
        }
        return Collections.emptyList();
    }

    public static boolean addDynamicShortcuts(Context context, List list) {
        if (Build.VERSION.SDK_INT <= 29) {
            convertUriIconsToBitmapIcons(context, list);
        }
        if (Build.VERSION.SDK_INT >= 25) {
            ArrayList<ShortcutInfo> shortcuts = new ArrayList<>();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ShortcutInfoCompat item = (ShortcutInfoCompat) it.next();
                shortcuts.add(item.toShortcutInfo());
            }
            if (!((ShortcutManager) context.getSystemService(ShortcutManager.class)).addDynamicShortcuts(shortcuts)) {
                return false;
            }
        }
        getShortcutInfoSaverInstance(context).addShortcuts(list);
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onShortcutAdded(list);
        }
        return true;
    }

    public static int getMaxShortcutCountPerActivity(Context context) {
        Preconditions.checkNotNull(context);
        if (Build.VERSION.SDK_INT >= 25) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).getMaxShortcutCountPerActivity();
        }
        return 5;
    }

    public static boolean isRateLimitingActive(Context context) {
        Preconditions.checkNotNull(context);
        if (Build.VERSION.SDK_INT >= 25) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).isRateLimitingActive();
        }
        return getShortcuts(context, 3).size() == getMaxShortcutCountPerActivity(context);
    }

    public static int getIconMaxWidth(Context context) {
        Preconditions.checkNotNull(context);
        if (Build.VERSION.SDK_INT >= 25) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).getIconMaxWidth();
        }
        return getIconDimensionInternal(context, true);
    }

    public static int getIconMaxHeight(Context context) {
        Preconditions.checkNotNull(context);
        if (Build.VERSION.SDK_INT >= 25) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).getIconMaxHeight();
        }
        return getIconDimensionInternal(context, false);
    }

    public static void reportShortcutUsed(Context context, String shortcutId) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(shortcutId);
        if (Build.VERSION.SDK_INT >= 25) {
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).reportShortcutUsed(shortcutId);
        }
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onShortcutUsageReported(Collections.singletonList(shortcutId));
        }
    }

    public static boolean setDynamicShortcuts(Context context, List list) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(list);
        if (Build.VERSION.SDK_INT >= 25) {
            ArrayList arrayList = new ArrayList(list.size());
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ShortcutInfoCompat compat = (ShortcutInfoCompat) it.next();
                arrayList.add(compat.toShortcutInfo());
            }
            if (!((ShortcutManager) context.getSystemService(ShortcutManager.class)).setDynamicShortcuts(arrayList)) {
                return false;
            }
        }
        getShortcutInfoSaverInstance(context).removeAllShortcuts();
        getShortcutInfoSaverInstance(context).addShortcuts(list);
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onAllShortcutsRemoved();
            listener.onShortcutAdded(list);
        }
        return true;
    }

    public static List getDynamicShortcuts(Context context) {
        if (Build.VERSION.SDK_INT >= 25) {
            List<ShortcutInfo> shortcuts = ((ShortcutManager) context.getSystemService(ShortcutManager.class)).getDynamicShortcuts();
            ArrayList arrayList = new ArrayList(shortcuts.size());
            for (ShortcutInfo item : shortcuts) {
                arrayList.add(new ShortcutInfoCompat.Builder(context, item).build());
            }
            return arrayList;
        }
        try {
            return getShortcutInfoSaverInstance(context).getShortcuts();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static boolean updateShortcuts(Context context, List list) {
        if (Build.VERSION.SDK_INT <= 29) {
            convertUriIconsToBitmapIcons(context, list);
        }
        if (Build.VERSION.SDK_INT >= 25) {
            ArrayList<ShortcutInfo> shortcuts = new ArrayList<>();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ShortcutInfoCompat item = (ShortcutInfoCompat) it.next();
                shortcuts.add(item.toShortcutInfo());
            }
            if (!((ShortcutManager) context.getSystemService(ShortcutManager.class)).updateShortcuts(shortcuts)) {
                return false;
            }
        }
        getShortcutInfoSaverInstance(context).addShortcuts(list);
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onShortcutUpdated(list);
        }
        return true;
    }

    static boolean convertUriIconToBitmapIcon(Context context, ShortcutInfoCompat info) {
        Bitmap bitmap;
        IconCompat createWithBitmap;
        if (info.mIcon == null) {
            return false;
        }
        int type = info.mIcon.mType;
        if (type != 6 && type != 4) {
            return true;
        }
        InputStream is = info.mIcon.getUriInputStream(context);
        if (is == null || (bitmap = BitmapFactory.decodeStream(is)) == null) {
            return false;
        }
        if (type == 6) {
            createWithBitmap = IconCompat.createWithAdaptiveBitmap(bitmap);
        } else {
            createWithBitmap = IconCompat.createWithBitmap(bitmap);
        }
        info.mIcon = createWithBitmap;
        return true;
    }

    static void convertUriIconsToBitmapIcons(Context context, List list) {
        for (ShortcutInfoCompat info : new ArrayList(list)) {
            if (!convertUriIconToBitmapIcon(context, info)) {
                list.remove(info);
            }
        }
    }

    public static void disableShortcuts(Context context, List list, CharSequence disabledMessage) {
        if (Build.VERSION.SDK_INT >= 25) {
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).disableShortcuts(list, disabledMessage);
        }
        getShortcutInfoSaverInstance(context).removeShortcuts(list);
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onShortcutRemoved(list);
        }
    }

    public static void enableShortcuts(Context context, List list) {
        if (Build.VERSION.SDK_INT >= 25) {
            ArrayList<String> shortcutIds = new ArrayList<>(list.size());
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ShortcutInfoCompat shortcut = (ShortcutInfoCompat) it.next();
                shortcutIds.add(shortcut.mId);
            }
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).enableShortcuts(shortcutIds);
        }
        getShortcutInfoSaverInstance(context).addShortcuts(list);
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onShortcutAdded(list);
        }
    }

    public static void removeDynamicShortcuts(Context context, List list) {
        if (Build.VERSION.SDK_INT >= 25) {
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).removeDynamicShortcuts(list);
        }
        getShortcutInfoSaverInstance(context).removeShortcuts(list);
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onShortcutRemoved(list);
        }
    }

    public static void removeAllDynamicShortcuts(Context context) {
        if (Build.VERSION.SDK_INT >= 25) {
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).removeAllDynamicShortcuts();
        }
        getShortcutInfoSaverInstance(context).removeAllShortcuts();
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onAllShortcutsRemoved();
        }
    }

    public static void removeLongLivedShortcuts(Context context, List list) {
        if (Build.VERSION.SDK_INT < 30) {
            removeDynamicShortcuts(context, list);
            return;
        }
        ((ShortcutManager) context.getSystemService(ShortcutManager.class)).removeLongLivedShortcuts(list);
        getShortcutInfoSaverInstance(context).removeShortcuts(list);
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onShortcutRemoved(list);
        }
    }

    public static boolean pushDynamicShortcut(Context context, ShortcutInfoCompat shortcut) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(shortcut);
        int maxShortcutCount = getMaxShortcutCountPerActivity(context);
        if (maxShortcutCount == 0) {
            return false;
        }
        if (Build.VERSION.SDK_INT <= 29) {
            convertUriIconToBitmapIcon(context, shortcut);
        }
        if (Build.VERSION.SDK_INT >= 30) {
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).pushDynamicShortcut(shortcut.toShortcutInfo());
        } else if (Build.VERSION.SDK_INT >= 25) {
            ShortcutManager sm = (ShortcutManager) context.getSystemService(ShortcutManager.class);
            if (sm.isRateLimitingActive()) {
                return false;
            }
            List<ShortcutInfo> shortcuts = sm.getDynamicShortcuts();
            if (shortcuts.size() >= maxShortcutCount) {
                sm.removeDynamicShortcuts(Arrays.asList(new String[]{Api25Impl.getShortcutInfoWithLowestRank(shortcuts)}));
            }
            sm.addDynamicShortcuts(Arrays.asList(new ShortcutInfo[]{shortcut.toShortcutInfo()}));
        }
        ShortcutInfoCompatSaver<?> saver = getShortcutInfoSaverInstance(context);
        try {
            List<ShortcutInfoCompat> oldShortcuts = saver.getShortcuts();
            if (oldShortcuts.size() >= maxShortcutCount) {
                saver.removeShortcuts(Arrays.asList(new String[]{getShortcutInfoCompatWithLowestRank(oldShortcuts)}));
            }
            saver.addShortcuts(Arrays.asList(new ShortcutInfoCompat[]{shortcut}));
            for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
                listener.onShortcutAdded(Collections.singletonList(shortcut));
            }
            reportShortcutUsed(context, shortcut.getId());
            return true;
        } catch (Exception e) {
            for (ShortcutInfoChangeListener listener2 : getShortcutInfoListeners(context)) {
                listener2.onShortcutAdded(Collections.singletonList(shortcut));
            }
            reportShortcutUsed(context, shortcut.getId());
            return false;
        } catch (Throwable th) {
            for (ShortcutInfoChangeListener listener3 : getShortcutInfoListeners(context)) {
                listener3.onShortcutAdded(Collections.singletonList(shortcut));
            }
            reportShortcutUsed(context, shortcut.getId());
            throw th;
        }
    }

    private static String getShortcutInfoCompatWithLowestRank(List list) {
        int rank = -1;
        String target = null;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ShortcutInfoCompat s = (ShortcutInfoCompat) it.next();
            if (s.getRank() > rank) {
                target = s.getId();
                rank = s.getRank();
            }
        }
        return target;
    }

    static void setShortcutInfoCompatSaver(ShortcutInfoCompatSaver shortcutInfoCompatSaver) {
        sShortcutInfoCompatSaver = shortcutInfoCompatSaver;
    }

    static void setShortcutInfoChangeListeners(List list) {
        sShortcutInfoChangeListeners = list;
    }

    static List getShortcutInfoChangeListeners() {
        return sShortcutInfoChangeListeners;
    }

    private static int getIconDimensionInternal(Context context, boolean isHorizontal) {
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        boolean isLowRamDevice = Build.VERSION.SDK_INT < 19 || am == null || am.isLowRamDevice();
        int iconDimensionDp = Math.max(1, isLowRamDevice ? 48 : 96);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float density = (isHorizontal ? displayMetrics.xdpi : displayMetrics.ydpi) / 160.0f;
        return (int) (iconDimensionDp * density);
    }

    private static ShortcutInfoCompatSaver getShortcutInfoSaverInstance(Context context) {
        if (sShortcutInfoCompatSaver == null) {
            if (Build.VERSION.SDK_INT >= 23) {
                try {
                    ClassLoader loader = ShortcutManagerCompat.class.getClassLoader();
                    Class<?> saver = Class.forName("androidx.sharetarget.ShortcutInfoCompatSaverImpl", false, loader);
                    Method getInstanceMethod = saver.getMethod("getInstance", new Class[]{Context.class});
                    sShortcutInfoCompatSaver = (ShortcutInfoCompatSaver) getInstanceMethod.invoke((Object) null, new Object[]{context});
                } catch (Exception e) {
                }
            }
            if (sShortcutInfoCompatSaver == null) {
                sShortcutInfoCompatSaver = new ShortcutInfoCompatSaver.NoopImpl();
            }
        }
        return sShortcutInfoCompatSaver;
    }

    private static List getShortcutInfoListeners(Context context) {
        Bundle metaData;
        String shortcutListenerImplName;
        if (sShortcutInfoChangeListeners == null) {
            ArrayList arrayList = new ArrayList();
            if (Build.VERSION.SDK_INT >= 21) {
                PackageManager packageManager = context.getPackageManager();
                Intent activityIntent = new Intent("androidx.core.content.pm.SHORTCUT_LISTENER");
                activityIntent.setPackage(context.getPackageName());
                List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(activityIntent, 128);
                for (ResolveInfo resolveInfo : resolveInfos) {
                    ActivityInfo activityInfo = resolveInfo.activityInfo;
                    if (activityInfo != null && (metaData = activityInfo.metaData) != null && (shortcutListenerImplName = metaData.getString("androidx.core.content.pm.shortcut_listener_impl")) != null) {
                        try {
                            ClassLoader loader = ShortcutManagerCompat.class.getClassLoader();
                            Class<?> listener = Class.forName(shortcutListenerImplName, false, loader);
                            Method getInstanceMethod = listener.getMethod("getInstance", new Class[]{Context.class});
                            arrayList.add((ShortcutInfoChangeListener) getInstanceMethod.invoke((Object) null, new Object[]{context}));
                        } catch (Exception e) {
                        }
                    }
                }
            }
            if (sShortcutInfoChangeListeners == null) {
                sShortcutInfoChangeListeners = arrayList;
            }
        }
        return sShortcutInfoChangeListeners;
    }

    private static class Api25Impl {
        private Api25Impl() {
        }

        static String getShortcutInfoWithLowestRank(List list) {
            int rank = -1;
            String target = null;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ShortcutInfo s = (ShortcutInfo) it.next();
                if (s.getRank() > rank) {
                    target = s.getId();
                    rank = s.getRank();
                }
            }
            return target;
        }
    }
}
