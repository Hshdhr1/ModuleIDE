package androidx.versionedparcelable;

import android.os.Bundle;
import android.os.Parcelable;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes14.dex */
public class ParcelUtils {
    private static final String INNER_BUNDLE_KEY = "a";

    private ParcelUtils() {
    }

    public static Parcelable toParcelable(VersionedParcelable obj) {
        return new ParcelImpl(obj);
    }

    public static VersionedParcelable fromParcelable(Parcelable p) {
        if (!(p instanceof ParcelImpl)) {
            throw new IllegalArgumentException("Invalid parcel");
        }
        return ((ParcelImpl) p).getVersionedParcel();
    }

    public static void toOutputStream(VersionedParcelable obj, OutputStream output) {
        VersionedParcelStream stream = new VersionedParcelStream(null, output);
        stream.writeVersionedParcelable(obj);
        stream.closeField();
    }

    public static VersionedParcelable fromInputStream(InputStream input) {
        VersionedParcelStream stream = new VersionedParcelStream(input, null);
        return stream.readVersionedParcelable();
    }

    public static void putVersionedParcelable(Bundle b, String key, VersionedParcelable obj) {
        if (obj == null) {
            return;
        }
        Bundle innerBundle = new Bundle();
        innerBundle.putParcelable("a", toParcelable(obj));
        b.putParcelable(key, innerBundle);
    }

    public static VersionedParcelable getVersionedParcelable(Bundle bundle, String key) {
        try {
            Bundle innerBundle = bundle.getParcelable(key);
            if (innerBundle == null) {
                return null;
            }
            innerBundle.setClassLoader(ParcelUtils.class.getClassLoader());
            return fromParcelable(innerBundle.getParcelable("a"));
        } catch (RuntimeException e) {
            return null;
        }
    }

    public static void putVersionedParcelableList(Bundle b, String key, List list) {
        Bundle innerBundle = new Bundle();
        ArrayList<Parcelable> toWrite = new ArrayList<>();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            VersionedParcelable obj = (VersionedParcelable) it.next();
            toWrite.add(toParcelable(obj));
        }
        innerBundle.putParcelableArrayList("a", toWrite);
        b.putParcelable(key, innerBundle);
    }

    public static List getVersionedParcelableList(Bundle bundle, String key) {
        ArrayList arrayList = new ArrayList();
        try {
            Bundle innerBundle = bundle.getParcelable(key);
            innerBundle.setClassLoader(ParcelUtils.class.getClassLoader());
            ArrayList<Parcelable> parcelableArrayList = innerBundle.getParcelableArrayList("a");
            Iterator it = parcelableArrayList.iterator();
            while (it.hasNext()) {
                Parcelable parcelable = (Parcelable) it.next();
                arrayList.add(fromParcelable(parcelable));
            }
            return arrayList;
        } catch (RuntimeException e) {
            return null;
        }
    }
}
