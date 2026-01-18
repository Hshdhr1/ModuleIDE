package androidx.recyclerview.widget;

import androidx.annotation.NonNull;
import androidx.collection.LongSparseArray;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes42.dex */
interface StableIdStorage {

    public interface StableIdLookup {
        long localToGlobal(long j);
    }

    @NonNull
    StableIdLookup createStableIdLookup();

    public static class NoStableIdStorage implements StableIdStorage {
        private final StableIdLookup mNoIdLookup = new 1();

        class 1 implements StableIdLookup {
            1() {
            }

            public long localToGlobal(long localId) {
                return -1L;
            }
        }

        @NonNull
        public StableIdLookup createStableIdLookup() {
            return this.mNoIdLookup;
        }
    }

    public static class SharedPoolStableIdStorage implements StableIdStorage {
        private final StableIdLookup mSameIdLookup = new 1();

        class 1 implements StableIdLookup {
            1() {
            }

            public long localToGlobal(long localId) {
                return localId;
            }
        }

        @NonNull
        public StableIdLookup createStableIdLookup() {
            return this.mSameIdLookup;
        }
    }

    public static class IsolatedStableIdStorage implements StableIdStorage {
        long mNextStableId = 0;

        long obtainId() {
            long j = this.mNextStableId;
            this.mNextStableId = 1 + j;
            return j;
        }

        @NonNull
        public StableIdLookup createStableIdLookup() {
            return new WrapperStableIdLookup();
        }

        class WrapperStableIdLookup implements StableIdLookup {
            private final LongSparseArray mLocalToGlobalLookup = new LongSparseArray();

            WrapperStableIdLookup() {
            }

            public long localToGlobal(long localId) {
                Long globalId = (Long) this.mLocalToGlobalLookup.get(localId);
                if (globalId == null) {
                    globalId = Long.valueOf(IsolatedStableIdStorage.this.obtainId());
                    this.mLocalToGlobalLookup.put(localId, globalId);
                }
                return globalId.longValue();
            }
        }
    }
}
