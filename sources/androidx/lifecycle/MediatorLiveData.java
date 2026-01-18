package androidx.lifecycle;

import androidx.arch.core.internal.SafeIterableMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes33.dex */
public class MediatorLiveData extends MutableLiveData {
    private SafeIterableMap mSources = new SafeIterableMap();

    public void addSource(LiveData liveData, Observer observer) {
        Source source = new Source(liveData, observer);
        Source<?> existing = (Source) this.mSources.putIfAbsent(liveData, source);
        if (existing != null && existing.mObserver != observer) {
            throw new IllegalArgumentException("This source was already added with the different observer");
        }
        if (existing == null && hasActiveObservers()) {
            source.plug();
        }
    }

    public void removeSource(LiveData liveData) {
        Source<?> source = (Source) this.mSources.remove(liveData);
        if (source != null) {
            source.unplug();
        }
    }

    protected void onActive() {
        Iterator it = this.mSources.iterator();
        while (it.hasNext()) {
            Map.Entry<LiveData<?>, Source<?>> source = (Map.Entry) it.next();
            ((Source) source.getValue()).plug();
        }
    }

    protected void onInactive() {
        Iterator it = this.mSources.iterator();
        while (it.hasNext()) {
            Map.Entry<LiveData<?>, Source<?>> source = (Map.Entry) it.next();
            ((Source) source.getValue()).unplug();
        }
    }

    private static class Source implements Observer {
        final LiveData mLiveData;
        final Observer mObserver;
        int mVersion = -1;

        Source(LiveData liveData, Observer observer) {
            this.mLiveData = liveData;
            this.mObserver = observer;
        }

        void plug() {
            this.mLiveData.observeForever(this);
        }

        void unplug() {
            this.mLiveData.removeObserver(this);
        }

        public void onChanged(Object obj) {
            if (this.mVersion != this.mLiveData.getVersion()) {
                this.mVersion = this.mLiveData.getVersion();
                this.mObserver.onChanged(obj);
            }
        }
    }
}
