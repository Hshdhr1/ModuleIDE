package androidx.core.content.pm;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public class ShortcutXmlParser {
    private static final String ATTR_SHORTCUT_ID = "shortcutId";
    private static final Object GET_INSTANCE_LOCK = new Object();
    private static final String META_DATA_APP_SHORTCUTS = "android.app.shortcuts";
    private static final String TAG = "ShortcutXmlParser";
    private static final String TAG_SHORTCUT = "shortcut";
    private static volatile ArrayList sShortcutIds;

    public static List getShortcutIds(Context context) {
        if (sShortcutIds == null) {
            synchronized (GET_INSTANCE_LOCK) {
                if (sShortcutIds == null) {
                    sShortcutIds = new ArrayList();
                    sShortcutIds.addAll(parseShortcutIds(context));
                }
            }
        }
        return sShortcutIds;
    }

    private ShortcutXmlParser() {
    }

    private static Set parseShortcutIds(Context context) {
        HashSet hashSet = new HashSet();
        Intent mainIntent = new Intent("android.intent.action.MAIN");
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        mainIntent.setPackage(context.getPackageName());
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(mainIntent, 128);
        if (resolveInfos == null || resolveInfos.size() == 0) {
            return hashSet;
        }
        try {
            for (ResolveInfo info : resolveInfos) {
                ActivityInfo activityInfo = info.activityInfo;
                Bundle metaData = activityInfo.metaData;
                if (metaData != null && metaData.containsKey("android.app.shortcuts")) {
                    XmlResourceParser parser = getXmlResourceParser(context, activityInfo);
                    try {
                        hashSet.addAll(parseShortcutIds((XmlPullParser) parser));
                        if (parser != null) {
                            parser.close();
                        }
                    } finally {
                    }
                }
            }
        } catch (Exception e) {
            Log.e("ShortcutXmlParser", "Failed to parse the Xml resource: ", e);
        }
        return hashSet;
    }

    private static XmlResourceParser getXmlResourceParser(Context context, ActivityInfo info) {
        XmlResourceParser parser = info.loadXmlMetaData(context.getPackageManager(), "android.app.shortcuts");
        if (parser == null) {
            throw new IllegalArgumentException("Failed to open android.app.shortcuts meta-data resource of " + info.name);
        }
        return parser;
    }

    public static List parseShortcutIds(XmlPullParser parser) throws IOException, XmlPullParserException {
        String shortcutId;
        ArrayList arrayList = new ArrayList(1);
        while (true) {
            int type = parser.next();
            if (type == 1 || (type == 3 && parser.getDepth() <= 0)) {
                break;
            }
            int depth = parser.getDepth();
            String tag = parser.getName();
            if (type == 2 && depth == 2 && "shortcut".equals(tag) && (shortcutId = getAttributeValue(parser, "shortcutId")) != null) {
                arrayList.add(shortcutId);
            }
        }
        return arrayList;
    }

    private static String getAttributeValue(XmlPullParser parser, String attribute) {
        String value = parser.getAttributeValue("http://schemas.android.com/apk/res/android", attribute);
        if (value == null) {
            return parser.getAttributeValue((String) null, attribute);
        }
        return value;
    }
}
