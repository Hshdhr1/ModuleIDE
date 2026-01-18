package androidx.fragment.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.fragment.app.FragmentManager;
import java.util.ArrayList;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes31.dex */
final class FragmentManagerState implements Parcelable {
    public static final Parcelable.Creator CREATOR = new 1();
    ArrayList mActive;
    ArrayList mAdded;
    BackStackState[] mBackStack;
    int mBackStackIndex;
    ArrayList mLaunchedFragments;
    String mPrimaryNavActiveWho;
    ArrayList mResultKeys;
    ArrayList mResults;

    public FragmentManagerState() {
        this.mPrimaryNavActiveWho = null;
        this.mResultKeys = new ArrayList();
        this.mResults = new ArrayList();
    }

    public FragmentManagerState(Parcel in) {
        this.mPrimaryNavActiveWho = null;
        this.mResultKeys = new ArrayList();
        this.mResults = new ArrayList();
        this.mActive = in.createTypedArrayList(FragmentState.CREATOR);
        this.mAdded = in.createStringArrayList();
        this.mBackStack = (BackStackState[]) in.createTypedArray(BackStackState.CREATOR);
        this.mBackStackIndex = in.readInt();
        this.mPrimaryNavActiveWho = in.readString();
        this.mResultKeys = in.createStringArrayList();
        this.mResults = in.createTypedArrayList(Bundle.CREATOR);
        this.mLaunchedFragments = in.createTypedArrayList(FragmentManager.LaunchedFragmentInfo.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mActive);
        dest.writeStringList(this.mAdded);
        dest.writeTypedArray(this.mBackStack, flags);
        dest.writeInt(this.mBackStackIndex);
        dest.writeString(this.mPrimaryNavActiveWho);
        dest.writeStringList(this.mResultKeys);
        dest.writeTypedList(this.mResults);
        dest.writeTypedList(this.mLaunchedFragments);
    }

    class 1 implements Parcelable.Creator {
        1() {
        }

        public FragmentManagerState createFromParcel(Parcel in) {
            return new FragmentManagerState(in);
        }

        public FragmentManagerState[] newArray(int size) {
            return new FragmentManagerState[size];
        }
    }
}
