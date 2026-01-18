package androidx.versionedparcelable;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes14.dex */
public class ParcelImpl implements Parcelable {
    public static final Parcelable.Creator CREATOR = new 1();
    private final VersionedParcelable mParcel;

    public ParcelImpl(VersionedParcelable parcel) {
        this.mParcel = parcel;
    }

    protected ParcelImpl(Parcel in) {
        this.mParcel = new VersionedParcelParcel(in).readVersionedParcelable();
    }

    public VersionedParcelable getVersionedParcel() {
        return this.mParcel;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        VersionedParcelParcel parcel = new VersionedParcelParcel(dest);
        parcel.writeVersionedParcelable(this.mParcel);
    }

    static class 1 implements Parcelable.Creator {
        1() {
        }

        public ParcelImpl createFromParcel(Parcel in) {
            return new ParcelImpl(in);
        }

        public ParcelImpl[] newArray(int size) {
            return new ParcelImpl[size];
        }
    }
}
