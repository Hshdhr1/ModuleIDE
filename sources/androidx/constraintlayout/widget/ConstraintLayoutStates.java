package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes39.dex */
public class ConstraintLayoutStates {
    private static final boolean DEBUG = false;
    public static final String TAG = "ConstraintLayoutStates";
    private final ConstraintLayout mConstraintLayout;
    ConstraintSet mDefaultConstraintSet;
    int mCurrentStateId = -1;
    int mCurrentConstraintNumber = -1;
    private SparseArray mStateList = new SparseArray();
    private SparseArray mConstraintSetMap = new SparseArray();
    private ConstraintsChangedListener mConstraintsChangedListener = null;

    ConstraintLayoutStates(Context context, ConstraintLayout layout, int resourceID) {
        this.mConstraintLayout = layout;
        load(context, resourceID);
    }

    public boolean needsToChange(int id, float width, float height) {
        int i = this.mCurrentStateId;
        if (i != id) {
            return true;
        }
        State state = (State) (id == -1 ? this.mStateList.valueAt(0) : this.mStateList.get(i));
        return (this.mCurrentConstraintNumber == -1 || !((Variant) state.mVariants.get(this.mCurrentConstraintNumber)).match(width, height)) && this.mCurrentConstraintNumber != state.findMatch(width, height);
    }

    public void updateConstraints(int id, float width, float height) {
        State state;
        int match;
        int i = this.mCurrentStateId;
        if (i == id) {
            if (id == -1) {
                state = (State) this.mStateList.valueAt(0);
            } else {
                state = (State) this.mStateList.get(i);
            }
            if ((this.mCurrentConstraintNumber != -1 && ((Variant) state.mVariants.get(this.mCurrentConstraintNumber)).match(width, height)) || this.mCurrentConstraintNumber == (match = state.findMatch(width, height))) {
                return;
            }
            ConstraintSet constraintSet = match == -1 ? this.mDefaultConstraintSet : ((Variant) state.mVariants.get(match)).mConstraintSet;
            int cid = match == -1 ? state.mConstraintID : ((Variant) state.mVariants.get(match)).mConstraintID;
            if (constraintSet == null) {
                return;
            }
            this.mCurrentConstraintNumber = match;
            ConstraintsChangedListener constraintsChangedListener = this.mConstraintsChangedListener;
            if (constraintsChangedListener != null) {
                constraintsChangedListener.preLayoutChange(-1, cid);
            }
            constraintSet.applyTo(this.mConstraintLayout);
            ConstraintsChangedListener constraintsChangedListener2 = this.mConstraintsChangedListener;
            if (constraintsChangedListener2 != null) {
                constraintsChangedListener2.postLayoutChange(-1, cid);
                return;
            }
            return;
        }
        this.mCurrentStateId = id;
        State state2 = (State) this.mStateList.get(id);
        int match2 = state2.findMatch(width, height);
        ConstraintSet constraintSet2 = match2 == -1 ? state2.mConstraintSet : ((Variant) state2.mVariants.get(match2)).mConstraintSet;
        int cid2 = match2 == -1 ? state2.mConstraintID : ((Variant) state2.mVariants.get(match2)).mConstraintID;
        if (constraintSet2 == null) {
            StringBuilder sb = new StringBuilder(79);
            sb.append("NO Constraint set found ! id=");
            sb.append(id);
            sb.append(", dim =");
            sb.append(width);
            sb.append(", ");
            sb.append(height);
            Log.v("ConstraintLayoutStates", sb.toString());
            return;
        }
        this.mCurrentConstraintNumber = match2;
        ConstraintsChangedListener constraintsChangedListener3 = this.mConstraintsChangedListener;
        if (constraintsChangedListener3 != null) {
            constraintsChangedListener3.preLayoutChange(id, cid2);
        }
        constraintSet2.applyTo(this.mConstraintLayout);
        ConstraintsChangedListener constraintsChangedListener4 = this.mConstraintsChangedListener;
        if (constraintsChangedListener4 != null) {
            constraintsChangedListener4.postLayoutChange(id, cid2);
        }
    }

    public void setOnConstraintsChanged(ConstraintsChangedListener constraintsChangedListener) {
        this.mConstraintsChangedListener = constraintsChangedListener;
    }

    static class State {
        int mConstraintID;
        ConstraintSet mConstraintSet;
        int mId;
        ArrayList mVariants = new ArrayList();

        public State(Context context, XmlPullParser parser) {
            this.mConstraintID = -1;
            AttributeSet attrs = Xml.asAttributeSet(parser);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.State);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.State_android_id) {
                    this.mId = a.getResourceId(attr, this.mId);
                } else if (attr == R.styleable.State_constraints) {
                    this.mConstraintID = a.getResourceId(attr, this.mConstraintID);
                    String type = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    if ("layout".equals(type)) {
                        ConstraintSet constraintSet = new ConstraintSet();
                        this.mConstraintSet = constraintSet;
                        constraintSet.clone(context, this.mConstraintID);
                    }
                }
            }
            a.recycle();
        }

        void add(Variant size) {
            this.mVariants.add(size);
        }

        public int findMatch(float width, float height) {
            for (int i = 0; i < this.mVariants.size(); i++) {
                if (((Variant) this.mVariants.get(i)).match(width, height)) {
                    return i;
                }
            }
            return -1;
        }
    }

    static class Variant {
        int mConstraintID;
        ConstraintSet mConstraintSet;
        int mId;
        float mMaxHeight;
        float mMaxWidth;
        float mMinHeight;
        float mMinWidth;

        public Variant(Context context, XmlPullParser parser) {
            this.mMinWidth = Float.NaN;
            this.mMinHeight = Float.NaN;
            this.mMaxWidth = Float.NaN;
            this.mMaxHeight = Float.NaN;
            this.mConstraintID = -1;
            AttributeSet attrs = Xml.asAttributeSet(parser);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Variant);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.Variant_constraints) {
                    this.mConstraintID = a.getResourceId(attr, this.mConstraintID);
                    String type = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    if ("layout".equals(type)) {
                        ConstraintSet constraintSet = new ConstraintSet();
                        this.mConstraintSet = constraintSet;
                        constraintSet.clone(context, this.mConstraintID);
                    }
                } else if (attr == R.styleable.Variant_region_heightLessThan) {
                    this.mMaxHeight = a.getDimension(attr, this.mMaxHeight);
                } else if (attr == R.styleable.Variant_region_heightMoreThan) {
                    this.mMinHeight = a.getDimension(attr, this.mMinHeight);
                } else if (attr == R.styleable.Variant_region_widthLessThan) {
                    this.mMaxWidth = a.getDimension(attr, this.mMaxWidth);
                } else if (attr == R.styleable.Variant_region_widthMoreThan) {
                    this.mMinWidth = a.getDimension(attr, this.mMinWidth);
                } else {
                    Log.v("ConstraintLayoutStates", "Unknown tag");
                }
            }
            a.recycle();
        }

        boolean match(float widthDp, float heightDp) {
            if (!Float.isNaN(this.mMinWidth) && widthDp < this.mMinWidth) {
                return false;
            }
            if (!Float.isNaN(this.mMinHeight) && heightDp < this.mMinHeight) {
                return false;
            }
            if (Float.isNaN(this.mMaxWidth) || widthDp <= this.mMaxWidth) {
                return Float.isNaN(this.mMaxHeight) || heightDp <= this.mMaxHeight;
            }
            return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void load(android.content.Context r10, int r11) {
        /*
            r9 = this;
            android.content.res.Resources r0 = r10.getResources()
            android.content.res.XmlResourceParser r1 = r0.getXml(r11)
            r2 = 0
            r3 = 0
            r4 = 0
            int r5 = r1.getEventType()     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
        Lf:
            r6 = 1
            if (r5 == r6) goto L8a
            switch(r5) {
                case 0: goto L7e;
                case 1: goto L15;
                case 2: goto L1a;
                case 3: goto L17;
                default: goto L15;
            }     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
        L15:
            goto L84
        L17:
            r3 = 0
            goto L84
        L1a:
            java.lang.String r7 = r1.getName()     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            r3 = r7
            r7 = -1
            int r8 = r3.hashCode()     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            switch(r8) {
                case -1349929691: goto L4f;
                case 80204913: goto L45;
                case 1382829617: goto L3c;
                case 1657696882: goto L32;
                case 1901439077: goto L28;
                default: goto L27;
            }     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
        L27:
            goto L59
        L28:
            java.lang.String r6 = "Variant"
            boolean r6 = r3.equals(r6)     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            if (r6 == 0) goto L27
            r6 = 3
            goto L5a
        L32:
            java.lang.String r6 = "layoutDescription"
            boolean r6 = r3.equals(r6)     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            if (r6 == 0) goto L27
            r6 = 0
            goto L5a
        L3c:
            java.lang.String r8 = "StateSet"
            boolean r8 = r3.equals(r8)     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            if (r8 == 0) goto L27
            goto L5a
        L45:
            java.lang.String r6 = "State"
            boolean r6 = r3.equals(r6)     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            if (r6 == 0) goto L27
            r6 = 2
            goto L5a
        L4f:
            java.lang.String r6 = "ConstraintSet"
            boolean r6 = r3.equals(r6)     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            if (r6 == 0) goto L27
            r6 = 4
            goto L5a
        L59:
            r6 = -1
        L5a:
            switch(r6) {
                case 0: goto L7c;
                case 1: goto L7b;
                case 2: goto L6d;
                case 3: goto L62;
                case 4: goto L5e;
                default: goto L5d;
            }     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
        L5d:
            goto L7d
        L5e:
            r9.parseConstraintSet(r10, r1)     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            goto L7d
        L62:
            androidx.constraintlayout.widget.ConstraintLayoutStates$Variant r6 = new androidx.constraintlayout.widget.ConstraintLayoutStates$Variant     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            r6.<init>(r10, r1)     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            if (r4 == 0) goto L7d
            r4.add(r6)     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            goto L7d
        L6d:
            androidx.constraintlayout.widget.ConstraintLayoutStates$State r6 = new androidx.constraintlayout.widget.ConstraintLayoutStates$State     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            r6.<init>(r10, r1)     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            r4 = r6
            android.util.SparseArray r6 = r9.mStateList     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            int r7 = r4.mId     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            r6.put(r7, r4)     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            goto L7d
        L7b:
            goto L7d
        L7c:
        L7d:
            goto L84
        L7e:
            java.lang.String r6 = r1.getName()     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            r2 = r6
        L84:
            int r6 = r1.next()     // Catch: java.io.IOException -> L8b org.xmlpull.v1.XmlPullParserException -> L90
            r5 = r6
            goto Lf
        L8a:
            goto L94
        L8b:
            r4 = move-exception
            r4.printStackTrace()
            goto L95
        L90:
            r4 = move-exception
            r4.printStackTrace()
        L94:
        L95:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintLayoutStates.load(android.content.Context, int):void");
    }

    private void parseConstraintSet(Context context, XmlPullParser parser) {
        ConstraintSet set = new ConstraintSet();
        int count = parser.getAttributeCount();
        for (int i = 0; i < count; i++) {
            String name = parser.getAttributeName(i);
            String s = parser.getAttributeValue(i);
            if (name != null && s != null && "id".equals(name)) {
                int id = -1;
                if (s.contains("/")) {
                    String tmp = s.substring(s.indexOf(47) + 1);
                    id = context.getResources().getIdentifier(tmp, "id", context.getPackageName());
                }
                if (id == -1) {
                    if (s.length() > 1) {
                        id = Integer.parseInt(s.substring(1));
                    } else {
                        Log.e("ConstraintLayoutStates", "error in parsing id");
                    }
                }
                set.load(context, parser);
                this.mConstraintSetMap.put(id, set);
                return;
            }
        }
    }
}
