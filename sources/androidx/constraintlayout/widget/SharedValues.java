package androidx.constraintlayout.widget;

import android.util.SparseIntArray;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes39.dex */
public class SharedValues {
    public static final int UNSET = -1;
    private SparseIntArray mValues = new SparseIntArray();
    private HashMap mValuesListeners = new HashMap();

    public interface SharedValuesListener {
        void onNewValue(int key, int newValue, int oldValue);
    }

    public void addListener(int key, SharedValuesListener listener) {
        HashSet<WeakReference<SharedValuesListener>> listeners = (HashSet) this.mValuesListeners.get(Integer.valueOf(key));
        if (listeners == null) {
            listeners = new HashSet<>();
            this.mValuesListeners.put(Integer.valueOf(key), listeners);
        }
        listeners.add(new WeakReference(listener));
    }

    public void removeListener(int key, SharedValuesListener listener) {
        HashSet<WeakReference<SharedValuesListener>> listeners = (HashSet) this.mValuesListeners.get(Integer.valueOf(key));
        if (listeners == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = listeners.iterator();
        while (it.hasNext()) {
            WeakReference<SharedValuesListener> listenerWeakReference = (WeakReference) it.next();
            SharedValuesListener l = (SharedValuesListener) listenerWeakReference.get();
            if (l == null || l == listener) {
                arrayList.add(listenerWeakReference);
            }
        }
        listeners.removeAll(arrayList);
    }

    public void removeListener(SharedValuesListener listener) {
        for (Integer key : this.mValuesListeners.keySet()) {
            removeListener(key.intValue(), listener);
        }
    }

    public void clearListeners() {
        this.mValuesListeners.clear();
    }

    public int getValue(int key) {
        return this.mValues.get(key, -1);
    }

    public void fireNewValue(int key, int value) {
        boolean needsCleanup = false;
        int previousValue = this.mValues.get(key, -1);
        if (previousValue == value) {
            return;
        }
        this.mValues.put(key, value);
        HashSet<WeakReference<SharedValuesListener>> listeners = (HashSet) this.mValuesListeners.get(Integer.valueOf(key));
        if (listeners == null) {
            return;
        }
        Iterator it = listeners.iterator();
        while (it.hasNext()) {
            SharedValuesListener l = (SharedValuesListener) ((WeakReference) it.next()).get();
            if (l != null) {
                l.onNewValue(key, value, previousValue);
            } else {
                needsCleanup = true;
            }
        }
        if (needsCleanup) {
            ArrayList arrayList = new ArrayList();
            Iterator it2 = listeners.iterator();
            while (it2.hasNext()) {
                WeakReference<SharedValuesListener> listenerWeakReference = (WeakReference) it2.next();
                SharedValuesListener listener = (SharedValuesListener) listenerWeakReference.get();
                if (listener == null) {
                    arrayList.add(listenerWeakReference);
                }
            }
            listeners.removeAll(arrayList);
        }
    }
}
