package androidx.lifecycle;

import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import androidx.savedstate.SavedStateRegistry;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes16.dex */
public final class SavedStateHandle {
    private static final Class[] ACCEPTABLE_CLASSES;
    private static final String KEYS = "keys";
    private static final String VALUES = "values";
    private final Map mLiveDatas;
    final Map mRegular;
    private final SavedStateRegistry.SavedStateProvider mSavedStateProvider;
    final Map mSavedStateProviders;

    class 1 implements SavedStateRegistry.SavedStateProvider {
        1() {
        }

        public Bundle saveState() {
            for (Map.Entry<String, SavedStateRegistry.SavedStateProvider> entry : new HashMap(SavedStateHandle.this.mSavedStateProviders).entrySet()) {
                Bundle savedState = ((SavedStateRegistry.SavedStateProvider) entry.getValue()).saveState();
                SavedStateHandle.this.set((String) entry.getKey(), savedState);
            }
            Set<String> keySet = SavedStateHandle.this.mRegular.keySet();
            ArrayList keys = new ArrayList(keySet.size());
            ArrayList value = new ArrayList(keys.size());
            for (String key : keySet) {
                keys.add(key);
                value.add(SavedStateHandle.this.mRegular.get(key));
            }
            Bundle res = new Bundle();
            res.putParcelableArrayList("keys", keys);
            res.putParcelableArrayList("values", value);
            return res;
        }
    }

    public SavedStateHandle(Map initialState) {
        this.mSavedStateProviders = new HashMap();
        this.mLiveDatas = new HashMap();
        this.mSavedStateProvider = new 1();
        this.mRegular = new HashMap(initialState);
    }

    public SavedStateHandle() {
        this.mSavedStateProviders = new HashMap();
        this.mLiveDatas = new HashMap();
        this.mSavedStateProvider = new 1();
        this.mRegular = new HashMap();
    }

    static SavedStateHandle createHandle(Bundle restoredState, Bundle defaultState) {
        if (restoredState == null && defaultState == null) {
            return new SavedStateHandle();
        }
        HashMap hashMap = new HashMap();
        if (defaultState != null) {
            for (String key : defaultState.keySet()) {
                hashMap.put(key, defaultState.get(key));
            }
        }
        if (restoredState == null) {
            return new SavedStateHandle(hashMap);
        }
        ArrayList keys = restoredState.getParcelableArrayList("keys");
        ArrayList values = restoredState.getParcelableArrayList("values");
        if (keys == null || values == null || keys.size() != values.size()) {
            throw new IllegalStateException("Invalid bundle passed as restored state");
        }
        for (int i = 0; i < keys.size(); i++) {
            hashMap.put((String) keys.get(i), values.get(i));
        }
        return new SavedStateHandle(hashMap);
    }

    SavedStateRegistry.SavedStateProvider savedStateProvider() {
        return this.mSavedStateProvider;
    }

    public boolean contains(String key) {
        return this.mRegular.containsKey(key);
    }

    public MutableLiveData getLiveData(String key) {
        return getLiveDataInternal(key, false, null);
    }

    public MutableLiveData getLiveData(String key, Object initialValue) {
        return getLiveDataInternal(key, true, initialValue);
    }

    private MutableLiveData getLiveDataInternal(String key, boolean hasInitialValue, Object initialValue) {
        SavingStateLiveData savingStateLiveData;
        MutableLiveData mutableLiveData = (MutableLiveData) this.mLiveDatas.get(key);
        if (mutableLiveData != null) {
            return mutableLiveData;
        }
        if (this.mRegular.containsKey(key)) {
            savingStateLiveData = new SavingStateLiveData(this, key, this.mRegular.get(key));
        } else if (hasInitialValue) {
            savingStateLiveData = new SavingStateLiveData(this, key, initialValue);
        } else {
            savingStateLiveData = new SavingStateLiveData(this, key);
        }
        this.mLiveDatas.put(key, savingStateLiveData);
        return savingStateLiveData;
    }

    public Set keys() {
        HashSet<String> allKeys = new HashSet<>(this.mRegular.keySet());
        allKeys.addAll(this.mSavedStateProviders.keySet());
        allKeys.addAll(this.mLiveDatas.keySet());
        return allKeys;
    }

    public Object get(String key) {
        return this.mRegular.get(key);
    }

    public void set(String key, Object value) {
        validateValue(value);
        MutableLiveData mutableLiveData = (MutableLiveData) this.mLiveDatas.get(key);
        if (mutableLiveData != null) {
            mutableLiveData.setValue(value);
        } else {
            this.mRegular.put(key, value);
        }
    }

    private static void validateValue(Object value) {
        if (value == null) {
            return;
        }
        for (Class<?> cl : ACCEPTABLE_CLASSES) {
            if (cl.isInstance(value)) {
                return;
            }
        }
        throw new IllegalArgumentException("Can't put value with type " + value.getClass() + " into saved state");
    }

    public Object remove(String key) {
        Object remove = this.mRegular.remove(key);
        SavingStateLiveData<?> liveData = (SavingStateLiveData) this.mLiveDatas.remove(key);
        if (liveData != null) {
            liveData.detach();
        }
        return remove;
    }

    public void setSavedStateProvider(String key, SavedStateRegistry.SavedStateProvider provider) {
        this.mSavedStateProviders.put(key, provider);
    }

    public void clearSavedStateProvider(String key) {
        this.mSavedStateProviders.remove(key);
    }

    static class SavingStateLiveData extends MutableLiveData {
        private SavedStateHandle mHandle;
        private String mKey;

        SavingStateLiveData(SavedStateHandle handle, String key, Object value) {
            super(value);
            this.mKey = key;
            this.mHandle = handle;
        }

        SavingStateLiveData(SavedStateHandle handle, String key) {
            this.mKey = key;
            this.mHandle = handle;
        }

        public void setValue(Object value) {
            SavedStateHandle savedStateHandle = this.mHandle;
            if (savedStateHandle != null) {
                savedStateHandle.mRegular.put(this.mKey, value);
            }
            super.setValue(value);
        }

        void detach() {
            this.mHandle = null;
        }
    }

    static {
        Class[] clsArr = new Class[29];
        clsArr[0] = Boolean.TYPE;
        clsArr[1] = boolean[].class;
        clsArr[2] = Double.TYPE;
        clsArr[3] = double[].class;
        clsArr[4] = Integer.TYPE;
        clsArr[5] = int[].class;
        clsArr[6] = Long.TYPE;
        clsArr[7] = long[].class;
        clsArr[8] = String.class;
        clsArr[9] = String[].class;
        clsArr[10] = Binder.class;
        clsArr[11] = Bundle.class;
        clsArr[12] = Byte.TYPE;
        clsArr[13] = byte[].class;
        clsArr[14] = Character.TYPE;
        clsArr[15] = char[].class;
        clsArr[16] = CharSequence.class;
        clsArr[17] = CharSequence[].class;
        clsArr[18] = ArrayList.class;
        clsArr[19] = Float.TYPE;
        clsArr[20] = float[].class;
        clsArr[21] = Parcelable.class;
        clsArr[22] = Parcelable[].class;
        clsArr[23] = Serializable.class;
        clsArr[24] = Short.TYPE;
        clsArr[25] = short[].class;
        clsArr[26] = SparseArray.class;
        clsArr[27] = Build.VERSION.SDK_INT >= 21 ? Size.class : Integer.TYPE;
        clsArr[28] = Build.VERSION.SDK_INT >= 21 ? SizeF.class : Integer.TYPE;
        ACCEPTABLE_CLASSES = clsArr;
    }
}
