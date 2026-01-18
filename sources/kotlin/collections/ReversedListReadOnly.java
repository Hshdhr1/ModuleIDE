package kotlin.collections;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReversedViews.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010(\n\u0000\n\u0002\u0010*\n\u0000\b\u0012\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0015\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\u0016\u0010\u000b\u001a\u00028\u00002\u0006\u0010\f\u001a\u00020\bH\u0096\u0002¢\u0006\u0002\u0010\rJ\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000fH\u0096\u0002J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u0011H\u0016J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u00112\u0006\u0010\f\u001a\u00020\bH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0012"}, d2 = {"Lkotlin/collections/ReversedListReadOnly;", "T", "Lkotlin/collections/AbstractList;", "delegate", "", "<init>", "(Ljava/util/List;)V", "size", "", "getSize", "()I", "get", "index", "(I)Ljava/lang/Object;", "iterator", "", "listIterator", "", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
class ReversedListReadOnly extends AbstractList {

    @NotNull
    private final List delegate;

    public ReversedListReadOnly(@NotNull List delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    public static final /* synthetic */ List access$getDelegate$p(ReversedListReadOnly reversedListReadOnly) {
        return reversedListReadOnly.delegate;
    }

    public int getSize() {
        return this.delegate.size();
    }

    public Object get(int index) {
        return this.delegate.get(CollectionsKt__ReversedViewsKt.access$reverseElementIndex(this, index));
    }

    @NotNull
    public Iterator iterator() {
        return listIterator(0);
    }

    @NotNull
    public ListIterator listIterator() {
        return listIterator(0);
    }

    /* compiled from: ReversedViews.kt */
    @Metadata(d1 = {"\u0000\u001d\n\u0000\n\u0002\u0010*\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\t\u0010\u0005\u001a\u00020\u0006H\u0096\u0002J\b\u0010\u0007\u001a\u00020\u0006H\u0016J\u000e\u0010\b\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u000bH\u0016J\r\u0010\f\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\tJ\b\u0010\r\u001a\u00020\u000bH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u000e"}, d2 = {"kotlin/collections/ReversedListReadOnly$listIterator$1", "", "delegateIterator", "getDelegateIterator", "()Ljava/util/ListIterator;", "hasNext", "", "hasPrevious", "next", "()Ljava/lang/Object;", "nextIndex", "", "previous", "previousIndex", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class 1 implements ListIterator, KMappedMarker {
        private final ListIterator delegateIterator;

        public void add(Object obj) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        public void set(Object obj) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        1(int i) {
            this.delegateIterator = ReversedListReadOnly.access$getDelegate$p(ReversedListReadOnly.this).listIterator(CollectionsKt__ReversedViewsKt.access$reversePositionIndex(ReversedListReadOnly.this, i));
        }

        public final ListIterator getDelegateIterator() {
            return this.delegateIterator;
        }

        public boolean hasNext() {
            return this.delegateIterator.hasPrevious();
        }

        public boolean hasPrevious() {
            return this.delegateIterator.hasNext();
        }

        public Object next() {
            return this.delegateIterator.previous();
        }

        public int nextIndex() {
            return CollectionsKt__ReversedViewsKt.access$reverseIteratorIndex(ReversedListReadOnly.this, this.delegateIterator.previousIndex());
        }

        public Object previous() {
            return this.delegateIterator.next();
        }

        public int previousIndex() {
            return CollectionsKt__ReversedViewsKt.access$reverseIteratorIndex(ReversedListReadOnly.this, this.delegateIterator.nextIndex());
        }
    }

    @NotNull
    public ListIterator listIterator(int index) {
        return new 1(index);
    }
}
