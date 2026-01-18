package androidx.core.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.IconCompat;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
class NotificationCompatJellybean {
    static final String EXTRA_ALLOW_GENERATED_REPLIES = "android.support.allowGeneratedReplies";
    static final String EXTRA_DATA_ONLY_REMOTE_INPUTS = "android.support.dataRemoteInputs";
    private static final String KEY_ACTION_INTENT = "actionIntent";
    private static final String KEY_ALLOWED_DATA_TYPES = "allowedDataTypes";
    private static final String KEY_ALLOW_FREE_FORM_INPUT = "allowFreeFormInput";
    private static final String KEY_CHOICES = "choices";
    private static final String KEY_DATA_ONLY_REMOTE_INPUTS = "dataOnlyRemoteInputs";
    private static final String KEY_EXTRAS = "extras";
    private static final String KEY_ICON = "icon";
    private static final String KEY_LABEL = "label";
    private static final String KEY_REMOTE_INPUTS = "remoteInputs";
    private static final String KEY_RESULT_KEY = "resultKey";
    private static final String KEY_SEMANTIC_ACTION = "semanticAction";
    private static final String KEY_SHOWS_USER_INTERFACE = "showsUserInterface";
    private static final String KEY_TITLE = "title";
    public static final String TAG = "NotificationCompat";
    private static Field sActionIconField;
    private static Field sActionIntentField;
    private static Field sActionTitleField;
    private static boolean sActionsAccessFailed;
    private static Field sActionsField;
    private static Field sExtrasField;
    private static boolean sExtrasFieldAccessFailed;
    private static final Object sExtrasLock = new Object();
    private static final Object sActionsLock = new Object();

    public static SparseArray buildActionExtrasMap(List list) {
        SparseArray<Bundle> actionExtrasMap = null;
        int count = list.size();
        for (int i = 0; i < count; i++) {
            Bundle actionExtras = (Bundle) list.get(i);
            if (actionExtras != null) {
                if (actionExtrasMap == null) {
                    actionExtrasMap = new SparseArray<>();
                }
                actionExtrasMap.put(i, actionExtras);
            }
        }
        return actionExtrasMap;
    }

    public static Bundle getExtras(Notification notif) {
        synchronized (sExtrasLock) {
            if (sExtrasFieldAccessFailed) {
                return null;
            }
            try {
                if (sExtrasField == null) {
                    Field extrasField = Notification.class.getDeclaredField("extras");
                    if (!Bundle.class.isAssignableFrom(extrasField.getType())) {
                        Log.e("NotificationCompat", "Notification.extras field is not of type Bundle");
                        sExtrasFieldAccessFailed = true;
                        return null;
                    }
                    extrasField.setAccessible(true);
                    sExtrasField = extrasField;
                }
                Bundle extras = (Bundle) sExtrasField.get(notif);
                if (extras == null) {
                    extras = new Bundle();
                    sExtrasField.set(notif, extras);
                }
                return extras;
            } catch (NoSuchFieldException e) {
                Log.e("NotificationCompat", "Unable to access notification extras", e);
                sExtrasFieldAccessFailed = true;
                return null;
            } catch (IllegalAccessException e2) {
                Log.e("NotificationCompat", "Unable to access notification extras", e2);
                sExtrasFieldAccessFailed = true;
                return null;
            }
        }
    }

    public static NotificationCompat.Action readAction(int icon, CharSequence title, PendingIntent actionIntent, Bundle extras) {
        RemoteInput[] remoteInputs;
        RemoteInput[] dataOnlyRemoteInputs;
        boolean allowGeneratedReplies;
        if (extras == null) {
            remoteInputs = null;
            dataOnlyRemoteInputs = null;
            allowGeneratedReplies = false;
        } else {
            RemoteInput[] remoteInputs2 = fromBundleArray(getBundleArrayFromBundle(extras, "android.support.remoteInputs"));
            RemoteInput[] dataOnlyRemoteInputs2 = fromBundleArray(getBundleArrayFromBundle(extras, "android.support.dataRemoteInputs"));
            boolean allowGeneratedReplies2 = extras.getBoolean("android.support.allowGeneratedReplies");
            remoteInputs = remoteInputs2;
            dataOnlyRemoteInputs = dataOnlyRemoteInputs2;
            allowGeneratedReplies = allowGeneratedReplies2;
        }
        return new NotificationCompat.Action(icon, title, actionIntent, extras, remoteInputs, dataOnlyRemoteInputs, allowGeneratedReplies, 0, true, false);
    }

    public static Bundle writeActionAndGetExtras(Notification.Builder builder, NotificationCompat.Action action) {
        IconCompat iconCompat = action.getIconCompat();
        builder.addAction(iconCompat != null ? iconCompat.getResId() : 0, action.getTitle(), action.getActionIntent());
        Bundle actionExtras = new Bundle(action.getExtras());
        if (action.getRemoteInputs() != null) {
            actionExtras.putParcelableArray("android.support.remoteInputs", toBundleArray(action.getRemoteInputs()));
        }
        if (action.getDataOnlyRemoteInputs() != null) {
            actionExtras.putParcelableArray("android.support.dataRemoteInputs", toBundleArray(action.getDataOnlyRemoteInputs()));
        }
        actionExtras.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
        return actionExtras;
    }

    public static int getActionCount(Notification notif) {
        int length;
        synchronized (sActionsLock) {
            Object[] actionObjects = getActionObjectsLocked(notif);
            length = actionObjects != null ? actionObjects.length : 0;
        }
        return length;
    }

    public static NotificationCompat.Action getAction(Notification notif, int actionIndex) {
        Object[] actionObjects;
        SparseArray<Bundle> actionExtrasMap;
        synchronized (sActionsLock) {
            try {
                try {
                    actionObjects = getActionObjectsLocked(notif);
                } catch (IllegalAccessException e) {
                    Log.e("NotificationCompat", "Unable to access notification actions", e);
                    sActionsAccessFailed = true;
                }
                if (actionObjects != null) {
                    Object actionObject = actionObjects[actionIndex];
                    Bundle actionExtras = null;
                    Bundle extras = getExtras(notif);
                    if (extras != null && (actionExtrasMap = extras.getSparseParcelableArray("android.support.actionExtras")) != null) {
                        actionExtras = (Bundle) actionExtrasMap.get(actionIndex);
                    }
                    return readAction(sActionIconField.getInt(actionObject), (CharSequence) sActionTitleField.get(actionObject), (PendingIntent) sActionIntentField.get(actionObject), actionExtras);
                }
                return null;
            } finally {
            }
        }
    }

    private static Object[] getActionObjectsLocked(Notification notif) {
        synchronized (sActionsLock) {
            if (!ensureActionReflectionReadyLocked()) {
                return null;
            }
            try {
                return (Object[]) sActionsField.get(notif);
            } catch (IllegalAccessException e) {
                Log.e("NotificationCompat", "Unable to access notification actions", e);
                sActionsAccessFailed = true;
                return null;
            }
        }
    }

    private static boolean ensureActionReflectionReadyLocked() {
        if (sActionsAccessFailed) {
            return false;
        }
        try {
            if (sActionsField == null) {
                Class<?> sActionClass = Class.forName("android.app.Notification$Action");
                sActionIconField = sActionClass.getDeclaredField("icon");
                sActionTitleField = sActionClass.getDeclaredField("title");
                sActionIntentField = sActionClass.getDeclaredField("actionIntent");
                Field declaredField = Notification.class.getDeclaredField("actions");
                sActionsField = declaredField;
                declaredField.setAccessible(true);
            }
        } catch (ClassNotFoundException e) {
            Log.e("NotificationCompat", "Unable to access notification actions", e);
            sActionsAccessFailed = true;
        } catch (NoSuchFieldException e2) {
            Log.e("NotificationCompat", "Unable to access notification actions", e2);
            sActionsAccessFailed = true;
        }
        return !sActionsAccessFailed;
    }

    static NotificationCompat.Action getActionFromBundle(Bundle bundle) {
        Bundle extras = bundle.getBundle("extras");
        boolean allowGeneratedReplies = false;
        if (extras != null) {
            allowGeneratedReplies = extras.getBoolean("android.support.allowGeneratedReplies", false);
        }
        return new NotificationCompat.Action(bundle.getInt("icon"), bundle.getCharSequence("title"), bundle.getParcelable("actionIntent"), bundle.getBundle("extras"), fromBundleArray(getBundleArrayFromBundle(bundle, "remoteInputs")), fromBundleArray(getBundleArrayFromBundle(bundle, "dataOnlyRemoteInputs")), allowGeneratedReplies, bundle.getInt("semanticAction"), bundle.getBoolean("showsUserInterface"), false);
    }

    static Bundle getBundleForAction(NotificationCompat.Action action) {
        Bundle actionExtras;
        Bundle bundle = new Bundle();
        IconCompat icon = action.getIconCompat();
        bundle.putInt("icon", icon != null ? icon.getResId() : 0);
        bundle.putCharSequence("title", action.getTitle());
        bundle.putParcelable("actionIntent", action.getActionIntent());
        if (action.getExtras() != null) {
            actionExtras = new Bundle(action.getExtras());
        } else {
            actionExtras = new Bundle();
        }
        actionExtras.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
        bundle.putBundle("extras", actionExtras);
        bundle.putParcelableArray("remoteInputs", toBundleArray(action.getRemoteInputs()));
        bundle.putBoolean("showsUserInterface", action.getShowsUserInterface());
        bundle.putInt("semanticAction", action.getSemanticAction());
        return bundle;
    }

    private static RemoteInput fromBundle(Bundle data) {
        ArrayList<String> allowedDataTypesAsList = data.getStringArrayList("allowedDataTypes");
        HashSet hashSet = new HashSet();
        if (allowedDataTypesAsList != null) {
            Iterator it = allowedDataTypesAsList.iterator();
            while (it.hasNext()) {
                String type = (String) it.next();
                hashSet.add(type);
            }
        }
        return new RemoteInput(data.getString("resultKey"), data.getCharSequence("label"), data.getCharSequenceArray("choices"), data.getBoolean("allowFreeFormInput"), 0, data.getBundle("extras"), hashSet);
    }

    private static Bundle toBundle(RemoteInput remoteInput) {
        Bundle data = new Bundle();
        data.putString("resultKey", remoteInput.getResultKey());
        data.putCharSequence("label", remoteInput.getLabel());
        data.putCharSequenceArray("choices", remoteInput.getChoices());
        data.putBoolean("allowFreeFormInput", remoteInput.getAllowFreeFormInput());
        data.putBundle("extras", remoteInput.getExtras());
        Set<String> allowedDataTypes = remoteInput.getAllowedDataTypes();
        if (allowedDataTypes != null && !allowedDataTypes.isEmpty()) {
            ArrayList<String> allowedDataTypesAsList = new ArrayList<>(allowedDataTypes.size());
            for (String type : allowedDataTypes) {
                allowedDataTypesAsList.add(type);
            }
            data.putStringArrayList("allowedDataTypes", allowedDataTypesAsList);
        }
        return data;
    }

    private static RemoteInput[] fromBundleArray(Bundle[] bundles) {
        if (bundles == null) {
            return null;
        }
        RemoteInput[] remoteInputs = new RemoteInput[bundles.length];
        for (int i = 0; i < bundles.length; i++) {
            remoteInputs[i] = fromBundle(bundles[i]);
        }
        return remoteInputs;
    }

    private static Bundle[] toBundleArray(RemoteInput[] remoteInputs) {
        if (remoteInputs == null) {
            return null;
        }
        Bundle[] bundles = new Bundle[remoteInputs.length];
        for (int i = 0; i < remoteInputs.length; i++) {
            bundles[i] = toBundle(remoteInputs[i]);
        }
        return bundles;
    }

    private static Bundle[] getBundleArrayFromBundle(Bundle bundle, String key) {
        Bundle[] parcelableArray = bundle.getParcelableArray(key);
        if ((parcelableArray instanceof Bundle[]) || parcelableArray == null) {
            return parcelableArray;
        }
        Bundle[] typedArray = (Bundle[]) Arrays.copyOf(parcelableArray, parcelableArray.length, Bundle[].class);
        bundle.putParcelableArray(key, typedArray);
        return typedArray;
    }

    private NotificationCompatJellybean() {
    }
}
