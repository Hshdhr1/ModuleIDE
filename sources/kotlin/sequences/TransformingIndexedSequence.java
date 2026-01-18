package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: Sequences.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010(\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00020\u0003B/\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\u0012\u0018\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006¢\u0006\u0004\b\b\u0010\tJ\u000f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u000bH\u0096\u0002R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lkotlin/sequences/TransformingIndexedSequence;", "T", "R", "Lkotlin/sequences/Sequence;", "sequence", "transformer", "Lkotlin/Function2;", "", "<init>", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function2;)V", "iterator", "", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final class TransformingIndexedSequence implements Sequence {

    @NotNull
    private final Sequence sequence;

    @NotNull
    private final Function2 transformer;

    public static final /* synthetic */ Sequence access$getSequence$p(TransformingIndexedSequence transformingIndexedSequence) {
        return transformingIndexedSequence.sequence;
    }

    public static final /* synthetic */ Function2 access$getTransformer$p(TransformingIndexedSequence transformingIndexedSequence) {
        return transformingIndexedSequence.transformer;
    }

    /* compiled from: Sequences.kt */
    @Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0010(\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u000e\u0010\u000b\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\fJ\t\u0010\r\u001a\u00020\u000eH\u0096\u0002R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"kotlin/sequences/TransformingIndexedSequence$iterator$1", "", "iterator", "getIterator", "()Ljava/util/Iterator;", "index", "", "getIndex", "()I", "setIndex", "(I)V", "next", "()Ljava/lang/Object;", "hasNext", "", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class 1 implements Iterator, KMappedMarker {
        private int index;
        private final Iterator iterator;

        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        1() {
            this.iterator = TransformingIndexedSequence.access$getSequence$p(TransformingIndexedSequence.this).iterator();
        }

        public final Iterator getIterator() {
            return this.iterator;
        }

        public final int getIndex() {
            return this.index;
        }

        public final void setIndex(int i) {
            this.index = i;
        }

        public Object next() {
            Function2 access$getTransformer$p = TransformingIndexedSequence.access$getTransformer$p(TransformingIndexedSequence.this);
            int i = this.index;
            this.index = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            return access$getTransformer$p.invoke(Integer.valueOf(i), this.iterator.next());
        }

        public boolean hasNext() {
            return this.iterator.hasNext();
        }
    }

    public TransformingIndexedSequence(@NotNull Sequence sequence, @NotNull Function2 transformer) {
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        this.sequence = sequence;
        this.transformer = transformer;
    }

    @NotNull
    public Iterator iterator() {
        return new 1();
    }
}
