package androidx.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes5.dex */
abstract class IndexBasedArrayIterator implements Iterator {
    private boolean mCanRemove;
    private int mIndex;
    private int mSize;

    protected abstract Object elementAt(int i);

    protected abstract void removeAt(int i);

    IndexBasedArrayIterator(int startingSize) {
        this.mSize = startingSize;
    }

    public final boolean hasNext() {
        return this.mIndex < this.mSize;
    }

    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Object elementAt = elementAt(this.mIndex);
        this.mIndex++;
        this.mCanRemove = true;
        return elementAt;
    }

    public void remove() {
        if (!this.mCanRemove) {
            throw new IllegalStateException();
        }
        int i = this.mIndex - 1;
        this.mIndex = i;
        removeAt(i);
        this.mSize--;
        this.mCanRemove = false;
    }
}
