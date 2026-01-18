package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: Sequences.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010(\n\u0002\b\u0004\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00020\u0003B)\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00010\nH\u0096\u0002J3\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\f0\u0003\"\u0004\b\u0002\u0010\f2\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\f0\n0\u0006H\u0000¢\u0006\u0002\b\rR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lkotlin/sequences/TransformingSequence;", "T", "R", "Lkotlin/sequences/Sequence;", "sequence", "transformer", "Lkotlin/Function1;", "<init>", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function1;)V", "iterator", "", "flatten", "E", "flatten$kotlin_stdlib", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final class TransformingSequence implements Sequence {

    @NotNull
    private final Sequence sequence;

    @NotNull
    private final Function1 transformer;

    public static final /* synthetic */ Sequence access$getSequence$p(TransformingSequence transformingSequence) {
        return transformingSequence.sequence;
    }

    public static final /* synthetic */ Function1 access$getTransformer$p(TransformingSequence transformingSequence) {
        return transformingSequence.transformer;
    }

    /* compiled from: Sequences.kt */
    @Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0010(\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u000e\u0010\u0005\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0006J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004¨\u0006\t"}, d2 = {"kotlin/sequences/TransformingSequence$iterator$1", "", "iterator", "getIterator", "()Ljava/util/Iterator;", "next", "()Ljava/lang/Object;", "hasNext", "", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class 1 implements Iterator, KMappedMarker {
        private final Iterator iterator;

        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        1() {
            this.iterator = TransformingSequence.access$getSequence$p(TransformingSequence.this).iterator();
        }

        public final Iterator getIterator() {
            return this.iterator;
        }

        public Object next() {
            return TransformingSequence.access$getTransformer$p(TransformingSequence.this).invoke(this.iterator.next());
        }

        public boolean hasNext() {
            return this.iterator.hasNext();
        }
    }

    public TransformingSequence(@NotNull Sequence sequence, @NotNull Function1 transformer) {
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        this.sequence = sequence;
        this.transformer = transformer;
    }

    @NotNull
    public Iterator iterator() {
        return new 1();
    }

    @NotNull
    public final Sequence flatten$kotlin_stdlib(@NotNull Function1 iterator) {
        Intrinsics.checkNotNullParameter(iterator, "iterator");
        return new FlatteningSequence(this.sequence, this.transformer, iterator);
    }
}
