package androidx.lifecycle;

import androidx.arch.core.util.Function;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes33.dex */
public class Transformations {
    private Transformations() {
    }

    class 1 implements Observer {
        final /* synthetic */ Function val$mapFunction;
        final /* synthetic */ MediatorLiveData val$result;

        1(MediatorLiveData mediatorLiveData, Function function) {
            this.val$result = mediatorLiveData;
            this.val$mapFunction = function;
        }

        public void onChanged(Object obj) {
            this.val$result.setValue(this.val$mapFunction.apply(obj));
        }
    }

    public static LiveData map(LiveData liveData, Function function) {
        MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(liveData, new 1(mediatorLiveData, function));
        return mediatorLiveData;
    }

    class 2 implements Observer {
        LiveData mSource;
        final /* synthetic */ MediatorLiveData val$result;
        final /* synthetic */ Function val$switchMapFunction;

        2(Function function, MediatorLiveData mediatorLiveData) {
            this.val$switchMapFunction = function;
            this.val$result = mediatorLiveData;
        }

        public void onChanged(Object obj) {
            LiveData liveData = (LiveData) this.val$switchMapFunction.apply(obj);
            LiveData liveData2 = this.mSource;
            if (liveData2 == liveData) {
                return;
            }
            if (liveData2 != null) {
                this.val$result.removeSource(liveData2);
            }
            this.mSource = liveData;
            if (liveData != null) {
                this.val$result.addSource(liveData, new 1());
            }
        }

        class 1 implements Observer {
            1() {
            }

            public void onChanged(Object obj) {
                2.this.val$result.setValue(obj);
            }
        }
    }

    public static LiveData switchMap(LiveData liveData, Function function) {
        MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(liveData, new 2(function, mediatorLiveData));
        return mediatorLiveData;
    }

    class 3 implements Observer {
        boolean mFirstTime = true;
        final /* synthetic */ MediatorLiveData val$outputLiveData;

        3(MediatorLiveData mediatorLiveData) {
            this.val$outputLiveData = mediatorLiveData;
        }

        public void onChanged(Object obj) {
            Object value = this.val$outputLiveData.getValue();
            if (this.mFirstTime || ((value == null && obj != null) || (value != null && !value.equals(obj)))) {
                this.mFirstTime = false;
                this.val$outputLiveData.setValue(obj);
            }
        }
    }

    public static LiveData distinctUntilChanged(LiveData liveData) {
        MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(liveData, new 3(mediatorLiveData));
        return mediatorLiveData;
    }
}
