package androidx.recyclerview.widget;

import android.util.SparseArray;
import android.util.SparseIntArray;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes42.dex */
interface ViewTypeStorage {

    public interface ViewTypeLookup {
        void dispose();

        int globalToLocal(int i);

        int localToGlobal(int i);
    }

    @NonNull
    ViewTypeLookup createViewTypeWrapper(@NonNull NestedAdapterWrapper nestedAdapterWrapper);

    @NonNull
    NestedAdapterWrapper getWrapperForGlobalType(int i);

    public static class SharedIdRangeViewTypeStorage implements ViewTypeStorage {
        SparseArray mGlobalTypeToWrapper = new SparseArray();

        @NonNull
        public NestedAdapterWrapper getWrapperForGlobalType(int globalViewType) {
            List<NestedAdapterWrapper> nestedAdapterWrappers = (List) this.mGlobalTypeToWrapper.get(globalViewType);
            if (nestedAdapterWrappers == null || nestedAdapterWrappers.isEmpty()) {
                throw new IllegalArgumentException("Cannot find the wrapper for global view type " + globalViewType);
            }
            return (NestedAdapterWrapper) nestedAdapterWrappers.get(0);
        }

        @NonNull
        public ViewTypeLookup createViewTypeWrapper(@NonNull NestedAdapterWrapper wrapper) {
            return new WrapperViewTypeLookup(wrapper);
        }

        void removeWrapper(@NonNull NestedAdapterWrapper wrapper) {
            for (int i = this.mGlobalTypeToWrapper.size() - 1; i >= 0; i--) {
                List<NestedAdapterWrapper> wrappers = (List) this.mGlobalTypeToWrapper.valueAt(i);
                if (wrappers.remove(wrapper) && wrappers.isEmpty()) {
                    this.mGlobalTypeToWrapper.removeAt(i);
                }
            }
        }

        class WrapperViewTypeLookup implements ViewTypeLookup {
            final NestedAdapterWrapper mWrapper;

            WrapperViewTypeLookup(NestedAdapterWrapper wrapper) {
                this.mWrapper = wrapper;
            }

            public int localToGlobal(int localType) {
                ArrayList arrayList = (List) SharedIdRangeViewTypeStorage.this.mGlobalTypeToWrapper.get(localType);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    SharedIdRangeViewTypeStorage.this.mGlobalTypeToWrapper.put(localType, arrayList);
                }
                if (!arrayList.contains(this.mWrapper)) {
                    arrayList.add(this.mWrapper);
                }
                return localType;
            }

            public int globalToLocal(int globalType) {
                return globalType;
            }

            public void dispose() {
                SharedIdRangeViewTypeStorage.this.removeWrapper(this.mWrapper);
            }
        }
    }

    public static class IsolatedViewTypeStorage implements ViewTypeStorage {
        SparseArray mGlobalTypeToWrapper = new SparseArray();
        int mNextViewType = 0;

        int obtainViewType(NestedAdapterWrapper wrapper) {
            int nextId = this.mNextViewType;
            this.mNextViewType = nextId + 1;
            this.mGlobalTypeToWrapper.put(nextId, wrapper);
            return nextId;
        }

        @NonNull
        public NestedAdapterWrapper getWrapperForGlobalType(int globalViewType) {
            NestedAdapterWrapper wrapper = (NestedAdapterWrapper) this.mGlobalTypeToWrapper.get(globalViewType);
            if (wrapper == null) {
                throw new IllegalArgumentException("Cannot find the wrapper for global view type " + globalViewType);
            }
            return wrapper;
        }

        @NonNull
        public ViewTypeLookup createViewTypeWrapper(@NonNull NestedAdapterWrapper wrapper) {
            return new WrapperViewTypeLookup(wrapper);
        }

        void removeWrapper(@NonNull NestedAdapterWrapper wrapper) {
            for (int i = this.mGlobalTypeToWrapper.size() - 1; i >= 0; i--) {
                NestedAdapterWrapper existingWrapper = (NestedAdapterWrapper) this.mGlobalTypeToWrapper.valueAt(i);
                if (existingWrapper == wrapper) {
                    this.mGlobalTypeToWrapper.removeAt(i);
                }
            }
        }

        class WrapperViewTypeLookup implements ViewTypeLookup {
            final NestedAdapterWrapper mWrapper;
            private SparseIntArray mLocalToGlobalMapping = new SparseIntArray(1);
            private SparseIntArray mGlobalToLocalMapping = new SparseIntArray(1);

            WrapperViewTypeLookup(NestedAdapterWrapper wrapper) {
                this.mWrapper = wrapper;
            }

            public int localToGlobal(int localType) {
                int index = this.mLocalToGlobalMapping.indexOfKey(localType);
                if (index > -1) {
                    return this.mLocalToGlobalMapping.valueAt(index);
                }
                int globalType = IsolatedViewTypeStorage.this.obtainViewType(this.mWrapper);
                this.mLocalToGlobalMapping.put(localType, globalType);
                this.mGlobalToLocalMapping.put(globalType, localType);
                return globalType;
            }

            public int globalToLocal(int globalType) {
                int index = this.mGlobalToLocalMapping.indexOfKey(globalType);
                if (index < 0) {
                    throw new IllegalStateException("requested global type " + globalType + " does not belong to the adapter:" + this.mWrapper.adapter);
                }
                return this.mGlobalToLocalMapping.valueAt(index);
            }

            public void dispose() {
                IsolatedViewTypeStorage.this.removeWrapper(this.mWrapper);
            }
        }
    }
}
