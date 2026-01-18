package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes39.dex */
public class KeyFrames {
    private static final String CUSTOM_ATTRIBUTE = "CustomAttribute";
    private static final String CUSTOM_METHOD = "CustomMethod";
    private static final String TAG = "KeyFrames";
    public static final int UNSET = -1;
    static HashMap sKeyMakers;
    private HashMap mFramesMap = new HashMap();

    static {
        HashMap hashMap = new HashMap();
        sKeyMakers = hashMap;
        try {
            hashMap.put("KeyAttribute", KeyAttributes.class.getConstructor(new Class[0]));
            sKeyMakers.put("KeyPosition", KeyPosition.class.getConstructor(new Class[0]));
            sKeyMakers.put("KeyCycle", KeyCycle.class.getConstructor(new Class[0]));
            sKeyMakers.put("KeyTimeCycle", KeyTimeCycle.class.getConstructor(new Class[0]));
            sKeyMakers.put("KeyTrigger", KeyTrigger.class.getConstructor(new Class[0]));
        } catch (NoSuchMethodException e) {
            Log.e("KeyFrames", "unable to load", e);
        }
    }

    public void addKey(Key key) {
        if (!this.mFramesMap.containsKey(Integer.valueOf(key.mTargetId))) {
            this.mFramesMap.put(Integer.valueOf(key.mTargetId), new ArrayList());
        }
        ArrayList<Key> frames = (ArrayList) this.mFramesMap.get(Integer.valueOf(key.mTargetId));
        if (frames != null) {
            frames.add(key);
        }
    }

    public KeyFrames() {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public KeyFrames(Context context, XmlPullParser parser) {
        Key key = null;
        try {
            int eventType = parser.getEventType();
            while (eventType != 1) {
                switch (eventType) {
                    case 0:
                        eventType = parser.next();
                    case 1:
                    default:
                        eventType = parser.next();
                    case 2:
                        String tagName = parser.getName();
                        if (sKeyMakers.containsKey(tagName)) {
                            try {
                                Constructor<? extends Key> keyMaker = (Constructor) sKeyMakers.get(tagName);
                                if (keyMaker != null) {
                                    key = (Key) keyMaker.newInstance(new Object[0]);
                                    key.load(context, Xml.asAttributeSet(parser));
                                    addKey(key);
                                } else {
                                    StringBuilder sb = new StringBuilder(String.valueOf(tagName).length() + 23);
                                    sb.append("Keymaker for ");
                                    sb.append(tagName);
                                    sb.append(" not found");
                                    throw new NullPointerException(sb.toString());
                                    break;
                                }
                            } catch (Exception e) {
                                Log.e("KeyFrames", "unable to create ", e);
                            }
                        } else if (tagName.equalsIgnoreCase("CustomAttribute")) {
                            if (key != null && key.mCustomConstraints != null) {
                                ConstraintAttribute.parse(context, parser, key.mCustomConstraints);
                            }
                        } else if (tagName.equalsIgnoreCase("CustomMethod") && key != null && key.mCustomConstraints != null) {
                            ConstraintAttribute.parse(context, parser, key.mCustomConstraints);
                        }
                        eventType = parser.next();
                        break;
                    case 3:
                        if ("KeyFrameSet".equals(parser.getName())) {
                            return;
                        }
                        eventType = parser.next();
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (XmlPullParserException e3) {
            e3.printStackTrace();
        }
    }

    public void addAllFrames(MotionController motionController) {
        ArrayList<Key> list = (ArrayList) this.mFramesMap.get(-1);
        if (list != null) {
            motionController.addKeys(list);
        }
    }

    public void addFrames(MotionController motionController) {
        ArrayList<Key> list = (ArrayList) this.mFramesMap.get(Integer.valueOf(motionController.mId));
        if (list != null) {
            motionController.addKeys(list);
        }
        ArrayList<Key> list2 = (ArrayList) this.mFramesMap.get(-1);
        if (list2 != null) {
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                Key key = (Key) it.next();
                String tag = motionController.mView.getLayoutParams().constraintTag;
                if (key.matches(tag)) {
                    motionController.addKey(key);
                }
            }
        }
    }

    static String name(int viewId, Context context) {
        return context.getResources().getResourceEntryName(viewId);
    }

    public Set getKeys() {
        return this.mFramesMap.keySet();
    }

    public ArrayList getKeyFramesForView(int id) {
        return (ArrayList) this.mFramesMap.get(Integer.valueOf(id));
    }
}
