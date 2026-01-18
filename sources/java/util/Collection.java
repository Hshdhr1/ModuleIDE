package java.util;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Collection extends Iterable {
    boolean add(Object obj);

    boolean addAll(Collection collection);

    void clear();

    boolean contains(Object obj);

    boolean containsAll(Collection collection);

    boolean equals(Object obj);

    void forEach(Consumer consumer);

    int hashCode();

    boolean isEmpty();

    Iterator iterator();

    Stream parallelStream();

    boolean remove(Object obj);

    boolean removeAll(Collection collection);

    boolean removeIf(Predicate predicate);

    boolean retainAll(Collection collection);

    int size();

    Spliterator spliterator();

    Stream stream();

    Object[] toArray();

    Object[] toArray(IntFunction intFunction);

    Object[] toArray(Object[] objArr);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static Object[] $default$toArray(Collection _this, IntFunction intFunction) {
            return _this.toArray((Object[]) intFunction.apply(0));
        }

        public static boolean $default$removeIf(Collection _this, Predicate predicate) {
            if (DesugarCollections.SYNCHRONIZED_COLLECTION.isInstance(_this)) {
                return DesugarCollections.removeIf(_this, predicate);
            }
            predicate.getClass();
            Iterator it = _this.iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (predicate.test(it.next())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        public static void $default$forEach(Collection _this, Consumer consumer) {
            consumer.getClass();
            Iterator it = _this.iterator();
            while (it.hasNext()) {
                consumer.accept(it.next());
            }
        }

        public static Spliterator $default$spliterator(Collection _this) {
            return Spliterators.spliterator(_this, 0);
        }

        public static Stream $default$stream(Collection _this) {
            return StreamSupport.stream(_this.spliterator(), false);
        }

        public static Stream $default$parallelStream(Collection _this) {
            return StreamSupport.stream(_this.spliterator(), true);
        }
    }
}
