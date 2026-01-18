package kotlin.collections.builders;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractMutableList;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.markers.KMutableList;
import kotlin.jvm.internal.markers.KMutableListIterator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ListBuilder.kt */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0000\n\u0002\b\u000e\n\u0002\u0010)\n\u0000\n\u0002\u0010+\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0011\n\u0002\u0010\u000e\n\u0002\b\u0015\b\u0000\u0018\u0000 Q*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u00042\b\u0012\u0004\u0012\u0002H\u00010\u00052\u00060\u0006j\u0002`\u0007:\u0003QRSB\u0011\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u0013J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0019\u001a\u00020\u0011H\u0016J\u0016\u0010\u001a\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\tH\u0096\u0002¢\u0006\u0002\u0010\u001cJ\u001e\u0010\u001d\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u001fJ\u0015\u0010 \u001a\u00020\t2\u0006\u0010\u001e\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010!J\u0015\u0010\"\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010!J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000$H\u0096\u0002J\u000e\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000&H\u0016J\u0016\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000&2\u0006\u0010\u001b\u001a\u00020\tH\u0016J\u0015\u0010'\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010(J\u001d\u0010'\u001a\u00020)2\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010*J\u0016\u0010+\u001a\u00020\u00112\f\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00000-H\u0016J\u001e\u0010+\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\t2\f\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00000-H\u0016J\b\u0010.\u001a\u00020)H\u0016J\u0015\u0010/\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\tH\u0016¢\u0006\u0002\u0010\u001cJ\u0015\u00100\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010(J\u0016\u00101\u001a\u00020\u00112\f\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00000-H\u0016J\u0016\u00102\u001a\u00020\u00112\f\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00000-H\u0016J\u001e\u00103\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u00104\u001a\u00020\t2\u0006\u00105\u001a\u00020\tH\u0016J'\u00106\u001a\b\u0012\u0004\u0012\u0002H70\r\"\u0004\b\u0001\u001072\f\u00108\u001a\b\u0012\u0004\u0012\u0002H70\rH\u0016¢\u0006\u0002\u00109J\u0015\u00106\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\rH\u0016¢\u0006\u0002\u0010:J\u0013\u0010;\u001a\u00020\u00112\b\u0010<\u001a\u0004\u0018\u00010\u0015H\u0096\u0002J\b\u0010=\u001a\u00020\tH\u0016J\b\u0010>\u001a\u00020?H\u0016J\b\u0010@\u001a\u00020)H\u0002J\b\u0010A\u001a\u00020)H\u0002J\u0010\u0010B\u001a\u00020)2\u0006\u0010C\u001a\u00020\tH\u0002J\u0010\u0010D\u001a\u00020)2\u0006\u0010E\u001a\u00020\tH\u0002J\u0014\u0010F\u001a\u00020\u00112\n\u0010<\u001a\u0006\u0012\u0002\b\u00030\u0013H\u0002J\u0018\u0010G\u001a\u00020)2\u0006\u0010H\u001a\u00020\t2\u0006\u0010C\u001a\u00020\tH\u0002J\u001d\u0010I\u001a\u00020)2\u0006\u0010H\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010*J&\u0010J\u001a\u00020)2\u0006\u0010H\u001a\u00020\t2\f\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00000-2\u0006\u0010C\u001a\u00020\tH\u0002J\u0015\u0010K\u001a\u00028\u00002\u0006\u0010H\u001a\u00020\tH\u0002¢\u0006\u0002\u0010\u001cJ\u0018\u0010L\u001a\u00020)2\u0006\u0010M\u001a\u00020\t2\u0006\u0010N\u001a\u00020\tH\u0002J.\u0010O\u001a\u00020\t2\u0006\u0010M\u001a\u00020\t2\u0006\u0010N\u001a\u00020\t2\f\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00000-2\u0006\u0010P\u001a\u00020\u0011H\u0002R\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\rX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006T"}, d2 = {"Lkotlin/collections/builders/ListBuilder;", "E", "", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "Lkotlin/collections/AbstractMutableList;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "initialCapacity", "", "<init>", "(I)V", "backing", "", "[Ljava/lang/Object;", "length", "isReadOnly", "", "build", "", "writeReplace", "", "size", "getSize", "()I", "isEmpty", "get", "index", "(I)Ljava/lang/Object;", "set", "element", "(ILjava/lang/Object;)Ljava/lang/Object;", "indexOf", "(Ljava/lang/Object;)I", "lastIndexOf", "iterator", "", "listIterator", "", "add", "(Ljava/lang/Object;)Z", "", "(ILjava/lang/Object;)V", "addAll", "elements", "", "clear", "removeAt", "remove", "removeAll", "retainAll", "subList", "fromIndex", "toIndex", "toArray", "T", "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "()[Ljava/lang/Object;", "equals", "other", "hashCode", "toString", "", "registerModification", "checkIsMutable", "ensureExtraCapacity", "n", "ensureCapacityInternal", "minCapacity", "contentEquals", "insertAtInternal", "i", "addAtInternal", "addAllInternal", "removeAtInternal", "removeRangeInternal", "rangeOffset", "rangeLength", "retainOrRemoveAllInternal", "retain", "Companion", "Itr", "BuilderSubList", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nListBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ListBuilder.kt\nkotlin/collections/builders/ListBuilder\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,718:1\n1#2:719\n*E\n"})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final class ListBuilder extends AbstractMutableList implements List, RandomAccess, Serializable, KMutableList {

    @NotNull
    private static final Companion Companion = new Companion(null);

    @NotNull
    private static final ListBuilder Empty;

    @NotNull
    private Object[] backing;
    private boolean isReadOnly;
    private int length;

    public ListBuilder() {
        this(0, 1, null);
    }

    public ListBuilder(int i) {
        this.backing = ListBuilderKt.arrayOfUninitializedElements(i);
    }

    public /* synthetic */ ListBuilder(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 10 : i);
    }

    public static final /* synthetic */ void access$addAllInternal(ListBuilder listBuilder, int i, Collection collection, int i2) {
        listBuilder.addAllInternal(i, collection, i2);
    }

    public static final /* synthetic */ void access$addAtInternal(ListBuilder listBuilder, int i, Object obj) {
        listBuilder.addAtInternal(i, obj);
    }

    public static final /* synthetic */ Object[] access$getBacking$p(ListBuilder listBuilder) {
        return listBuilder.backing;
    }

    public static final /* synthetic */ int access$getLength$p(ListBuilder listBuilder) {
        return listBuilder.length;
    }

    public static final /* synthetic */ int access$getModCount$p$s-2084097795(ListBuilder listBuilder) {
        return listBuilder.modCount;
    }

    public static final /* synthetic */ boolean access$isReadOnly$p(ListBuilder listBuilder) {
        return listBuilder.isReadOnly;
    }

    public static final /* synthetic */ Object access$removeAtInternal(ListBuilder listBuilder, int i) {
        return listBuilder.removeAtInternal(i);
    }

    public static final /* synthetic */ void access$removeRangeInternal(ListBuilder listBuilder, int i, int i2) {
        listBuilder.removeRangeInternal(i, i2);
    }

    public static final /* synthetic */ int access$retainOrRemoveAllInternal(ListBuilder listBuilder, int i, int i2, Collection collection, boolean z) {
        return listBuilder.retainOrRemoveAllInternal(i, i2, collection, z);
    }

    /* compiled from: ListBuilder.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lkotlin/collections/builders/ListBuilder$Companion;", "", "<init>", "()V", "Empty", "Lkotlin/collections/builders/ListBuilder;", "", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        ListBuilder listBuilder = new ListBuilder(0);
        listBuilder.isReadOnly = true;
        Empty = listBuilder;
    }

    @NotNull
    public final List build() {
        checkIsMutable();
        this.isReadOnly = true;
        return this.length > 0 ? this : Empty;
    }

    private final Object writeReplace() {
        if (this.isReadOnly) {
            return new SerializedCollection((Collection) this, 0);
        }
        throw new NotSerializableException("The list cannot be serialized while it is being built.");
    }

    /* renamed from: getSize, reason: from getter */
    public int getLength() {
        return this.length;
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public Object get(int index) {
        AbstractList.INSTANCE.checkElementIndex$kotlin_stdlib(index, this.length);
        return this.backing[index];
    }

    public Object set(int index, Object element) {
        checkIsMutable();
        AbstractList.INSTANCE.checkElementIndex$kotlin_stdlib(index, this.length);
        Object[] objArr = this.backing;
        Object obj = objArr[index];
        objArr[index] = element;
        return obj;
    }

    public int indexOf(Object element) {
        for (int i = 0; i < this.length; i++) {
            if (Intrinsics.areEqual(this.backing[i], element)) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object element) {
        for (int i = this.length - 1; i >= 0; i--) {
            if (Intrinsics.areEqual(this.backing[i], element)) {
                return i;
            }
        }
        return -1;
    }

    @NotNull
    public Iterator iterator() {
        return listIterator(0);
    }

    @NotNull
    public ListIterator listIterator() {
        return listIterator(0);
    }

    @NotNull
    public ListIterator listIterator(int index) {
        AbstractList.INSTANCE.checkPositionIndex$kotlin_stdlib(index, this.length);
        return new Itr(this, index);
    }

    public boolean add(Object element) {
        checkIsMutable();
        addAtInternal(this.length, element);
        return true;
    }

    public void add(int index, Object element) {
        checkIsMutable();
        AbstractList.INSTANCE.checkPositionIndex$kotlin_stdlib(index, this.length);
        addAtInternal(index, element);
    }

    public boolean addAll(@NotNull Collection elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        checkIsMutable();
        int size = elements.size();
        addAllInternal(this.length, elements, size);
        return size > 0;
    }

    public boolean addAll(int index, @NotNull Collection elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        checkIsMutable();
        AbstractList.INSTANCE.checkPositionIndex$kotlin_stdlib(index, this.length);
        int size = elements.size();
        addAllInternal(index, elements, size);
        return size > 0;
    }

    public void clear() {
        checkIsMutable();
        removeRangeInternal(0, this.length);
    }

    public Object removeAt(int index) {
        checkIsMutable();
        AbstractList.INSTANCE.checkElementIndex$kotlin_stdlib(index, this.length);
        return removeAtInternal(index);
    }

    public boolean remove(Object element) {
        checkIsMutable();
        int indexOf = indexOf(element);
        if (indexOf >= 0) {
            remove(indexOf);
        }
        return indexOf >= 0;
    }

    public boolean removeAll(@NotNull Collection elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        checkIsMutable();
        return retainOrRemoveAllInternal(0, this.length, elements, false) > 0;
    }

    public boolean retainAll(@NotNull Collection elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        checkIsMutable();
        return retainOrRemoveAllInternal(0, this.length, elements, true) > 0;
    }

    @NotNull
    public List subList(int fromIndex, int toIndex) {
        AbstractList.INSTANCE.checkRangeIndexes$kotlin_stdlib(fromIndex, toIndex, this.length);
        return new BuilderSubList(this.backing, fromIndex, toIndex - fromIndex, null, this);
    }

    @NotNull
    public Object[] toArray(@NotNull Object[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        int length = array.length;
        int i = this.length;
        if (length < i) {
            Object[] copyOfRange = Arrays.copyOfRange(this.backing, 0, i, array.getClass());
            Intrinsics.checkNotNullExpressionValue(copyOfRange, "copyOfRange(...)");
            return copyOfRange;
        }
        ArraysKt.copyInto(this.backing, array, 0, 0, i);
        return CollectionsKt.terminateCollectionToArray(this.length, array);
    }

    @NotNull
    public Object[] toArray() {
        return ArraysKt.copyOfRange(this.backing, 0, this.length);
    }

    public boolean equals(@Nullable Object other) {
        if (other != this) {
            return (other instanceof List) && contentEquals((List) other);
        }
        return true;
    }

    public int hashCode() {
        return ListBuilderKt.access$subarrayContentHashCode(this.backing, 0, this.length);
    }

    @NotNull
    public String toString() {
        return ListBuilderKt.access$subarrayContentToString(this.backing, 0, this.length, (Collection) this);
    }

    private final void registerModification() {
        this.modCount++;
    }

    private final void checkIsMutable() {
        if (this.isReadOnly) {
            throw new UnsupportedOperationException();
        }
    }

    private final void ensureExtraCapacity(int n) {
        ensureCapacityInternal(this.length + n);
    }

    private final void ensureCapacityInternal(int minCapacity) {
        if (minCapacity < 0) {
            throw new OutOfMemoryError();
        }
        if (minCapacity > this.backing.length) {
            this.backing = ListBuilderKt.copyOfUninitializedElements(this.backing, AbstractList.INSTANCE.newCapacity$kotlin_stdlib(this.backing.length, minCapacity));
        }
    }

    private final boolean contentEquals(List other) {
        return ListBuilderKt.access$subarrayContentEquals(this.backing, 0, this.length, other);
    }

    private final void insertAtInternal(int i, int n) {
        ensureExtraCapacity(n);
        Object[] objArr = this.backing;
        ArraysKt.copyInto(objArr, objArr, i + n, i, this.length);
        this.length += n;
    }

    private final void addAtInternal(int i, Object element) {
        registerModification();
        insertAtInternal(i, 1);
        this.backing[i] = element;
    }

    private final void addAllInternal(int i, Collection elements, int n) {
        registerModification();
        insertAtInternal(i, n);
        Iterator it = elements.iterator();
        for (int i2 = 0; i2 < n; i2++) {
            this.backing[i + i2] = it.next();
        }
    }

    private final Object removeAtInternal(int i) {
        registerModification();
        Object[] objArr = this.backing;
        Object obj = objArr[i];
        ArraysKt.copyInto(objArr, objArr, i, i + 1, this.length);
        ListBuilderKt.resetAt(this.backing, this.length - 1);
        this.length--;
        return obj;
    }

    private final void removeRangeInternal(int rangeOffset, int rangeLength) {
        if (rangeLength > 0) {
            registerModification();
        }
        Object[] objArr = this.backing;
        ArraysKt.copyInto(objArr, objArr, rangeOffset, rangeOffset + rangeLength, this.length);
        Object[] objArr2 = this.backing;
        int i = this.length;
        ListBuilderKt.resetRange(objArr2, i - rangeLength, i);
        this.length -= rangeLength;
    }

    private final int retainOrRemoveAllInternal(int rangeOffset, int rangeLength, Collection elements, boolean retain) {
        int i = 0;
        int i2 = 0;
        while (i < rangeLength) {
            int i3 = rangeOffset + i;
            if (elements.contains(this.backing[i3]) == retain) {
                Object[] objArr = this.backing;
                i++;
                objArr[i2 + rangeOffset] = objArr[i3];
                i2++;
            } else {
                i++;
            }
        }
        int i4 = rangeLength - i2;
        Object[] objArr2 = this.backing;
        ArraysKt.copyInto(objArr2, objArr2, rangeOffset + i2, rangeLength + rangeOffset, this.length);
        Object[] objArr3 = this.backing;
        int i5 = this.length;
        ListBuilderKt.resetRange(objArr3, i5 - i4, i5);
        if (i4 > 0) {
            registerModification();
        }
        this.length -= i4;
        return i4;
    }

    /* compiled from: ListBuilder.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010+\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001d\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\b\u0010\u000b\u001a\u00020\fH\u0016J\t\u0010\r\u001a\u00020\fH\u0096\u0002J\b\u0010\u000e\u001a\u00020\u0006H\u0016J\b\u0010\u000f\u001a\u00020\u0006H\u0016J\r\u0010\u0010\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0011J\u000e\u0010\u0012\u001a\u00028\u0001H\u0096\u0002¢\u0006\u0002\u0010\u0011J\u0015\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0016J\u0015\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0016J\b\u0010\u0018\u001a\u00020\u0014H\u0016J\b\u0010\u0019\u001a\u00020\u0014H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lkotlin/collections/builders/ListBuilder$Itr;", "E", "", "list", "Lkotlin/collections/builders/ListBuilder;", "index", "", "<init>", "(Lkotlin/collections/builders/ListBuilder;I)V", "lastIndex", "expectedModCount", "hasPrevious", "", "hasNext", "previousIndex", "nextIndex", "previous", "()Ljava/lang/Object;", "next", "set", "", "element", "(Ljava/lang/Object;)V", "add", "remove", "checkForComodification", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nListBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ListBuilder.kt\nkotlin/collections/builders/ListBuilder$Itr\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,718:1\n1#2:719\n*E\n"})
    private static final class Itr implements ListIterator, KMutableListIterator {
        private int expectedModCount;
        private int index;
        private int lastIndex;

        @NotNull
        private final ListBuilder list;

        public Itr(@NotNull ListBuilder list, int i) {
            Intrinsics.checkNotNullParameter(list, "list");
            this.list = list;
            this.index = i;
            this.lastIndex = -1;
            this.expectedModCount = ListBuilder.access$getModCount$p$s-2084097795(list);
        }

        public boolean hasPrevious() {
            return this.index > 0;
        }

        public boolean hasNext() {
            return this.index < ListBuilder.access$getLength$p(this.list);
        }

        public int previousIndex() {
            return this.index - 1;
        }

        /* renamed from: nextIndex, reason: from getter */
        public int getIndex() {
            return this.index;
        }

        public Object previous() {
            checkForComodification();
            int i = this.index;
            if (i <= 0) {
                throw new NoSuchElementException();
            }
            int i2 = i - 1;
            this.index = i2;
            this.lastIndex = i2;
            return ListBuilder.access$getBacking$p(this.list)[this.lastIndex];
        }

        public Object next() {
            checkForComodification();
            if (this.index >= ListBuilder.access$getLength$p(this.list)) {
                throw new NoSuchElementException();
            }
            int i = this.index;
            this.index = i + 1;
            this.lastIndex = i;
            return ListBuilder.access$getBacking$p(this.list)[this.lastIndex];
        }

        public void set(Object element) {
            checkForComodification();
            int i = this.lastIndex;
            if (i == -1) {
                throw new IllegalStateException("Call next() or previous() before replacing element from the iterator.".toString());
            }
            this.list.set(i, element);
        }

        public void add(Object element) {
            checkForComodification();
            ListBuilder listBuilder = this.list;
            int i = this.index;
            this.index = i + 1;
            listBuilder.add(i, element);
            this.lastIndex = -1;
            this.expectedModCount = ListBuilder.access$getModCount$p$s-2084097795(this.list);
        }

        public void remove() {
            checkForComodification();
            int i = this.lastIndex;
            if (i == -1) {
                throw new IllegalStateException("Call next() or previous() before removing element from the iterator.".toString());
            }
            this.list.remove(i);
            this.index = this.lastIndex;
            this.lastIndex = -1;
            this.expectedModCount = ListBuilder.access$getModCount$p$s-2084097795(this.list);
        }

        private final void checkForComodification() {
            if (ListBuilder.access$getModCount$p$s-2084097795(this.list) != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    /* compiled from: ListBuilder.kt */
    @Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010)\n\u0000\n\u0002\u0010+\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0011\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\f\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u00042\b\u0012\u0004\u0012\u0002H\u00010\u00052\u00060\u0006j\u0002`\u0007:\u0001QBC\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u000e\u0010\r\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u0000\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00010\u000f¢\u0006\u0004\b\u0010\u0010\u0011J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0016\u0010\u001a\u001a\u00028\u00012\u0006\u0010\u001b\u001a\u00020\u000bH\u0096\u0002¢\u0006\u0002\u0010\u001cJ\u001e\u0010\u001d\u001a\u00028\u00012\u0006\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00028\u0001H\u0096\u0002¢\u0006\u0002\u0010\u001fJ\u0015\u0010 \u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010!J\u0015\u0010\"\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010!J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00010$H\u0096\u0002J\u000e\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00010&H\u0016J\u0016\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00010&2\u0006\u0010\u001b\u001a\u00020\u000bH\u0016J\u0015\u0010'\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010(J\u001d\u0010'\u001a\u00020)2\u0006\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010*J\u0016\u0010+\u001a\u00020\u00192\f\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00010-H\u0016J\u001e\u0010+\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u000b2\f\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00010-H\u0016J\b\u0010.\u001a\u00020)H\u0016J\u0015\u0010/\u001a\u00028\u00012\u0006\u0010\u001b\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\u001cJ\u0015\u00100\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010(J\u0016\u00101\u001a\u00020\u00192\f\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00010-H\u0016J\u0016\u00102\u001a\u00020\u00192\f\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00010-H\u0016J\u001e\u00103\u001a\b\u0012\u0004\u0012\u00028\u00010\u00022\u0006\u00104\u001a\u00020\u000b2\u0006\u00105\u001a\u00020\u000bH\u0016J'\u00106\u001a\b\u0012\u0004\u0012\u0002H70\t\"\u0004\b\u0002\u001072\f\u00108\u001a\b\u0012\u0004\u0012\u0002H70\tH\u0016¢\u0006\u0002\u00109J\u0015\u00106\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140\tH\u0016¢\u0006\u0002\u0010:J\u0013\u0010;\u001a\u00020\u00192\b\u0010<\u001a\u0004\u0018\u00010\u0014H\u0096\u0002J\b\u0010=\u001a\u00020\u000bH\u0016J\b\u0010>\u001a\u00020?H\u0016J\b\u0010@\u001a\u00020)H\u0002J\b\u0010A\u001a\u00020)H\u0002J\b\u0010B\u001a\u00020)H\u0002J\u0014\u0010E\u001a\u00020\u00192\n\u0010<\u001a\u0006\u0012\u0002\b\u00030FH\u0002J\u001d\u0010G\u001a\u00020)2\u0006\u0010H\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00028\u0001H\u0002¢\u0006\u0002\u0010*J&\u0010I\u001a\u00020)2\u0006\u0010H\u001a\u00020\u000b2\f\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00010-2\u0006\u0010J\u001a\u00020\u000bH\u0002J\u0015\u0010K\u001a\u00028\u00012\u0006\u0010H\u001a\u00020\u000bH\u0002¢\u0006\u0002\u0010\u001cJ\u0018\u0010L\u001a\u00020)2\u0006\u0010M\u001a\u00020\u000b2\u0006\u0010N\u001a\u00020\u000bH\u0002J.\u0010O\u001a\u00020\u000b2\u0006\u0010M\u001a\u00020\u000b2\u0006\u0010N\u001a\u00020\u000b2\f\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00010-2\u0006\u0010P\u001a\u00020\u0019H\u0002R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00010\tX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0012R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00010\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010C\u001a\u00020\u00198BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bC\u0010D¨\u0006R"}, d2 = {"Lkotlin/collections/builders/ListBuilder$BuilderSubList;", "E", "", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "Lkotlin/collections/AbstractMutableList;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "backing", "", "offset", "", "length", "parent", "root", "Lkotlin/collections/builders/ListBuilder;", "<init>", "([Ljava/lang/Object;IILkotlin/collections/builders/ListBuilder$BuilderSubList;Lkotlin/collections/builders/ListBuilder;)V", "[Ljava/lang/Object;", "writeReplace", "", "size", "getSize", "()I", "isEmpty", "", "get", "index", "(I)Ljava/lang/Object;", "set", "element", "(ILjava/lang/Object;)Ljava/lang/Object;", "indexOf", "(Ljava/lang/Object;)I", "lastIndexOf", "iterator", "", "listIterator", "", "add", "(Ljava/lang/Object;)Z", "", "(ILjava/lang/Object;)V", "addAll", "elements", "", "clear", "removeAt", "remove", "removeAll", "retainAll", "subList", "fromIndex", "toIndex", "toArray", "T", "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "()[Ljava/lang/Object;", "equals", "other", "hashCode", "toString", "", "registerModification", "checkForComodification", "checkIsMutable", "isReadOnly", "()Z", "contentEquals", "", "addAtInternal", "i", "addAllInternal", "n", "removeAtInternal", "removeRangeInternal", "rangeOffset", "rangeLength", "retainOrRemoveAllInternal", "retain", "Itr", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class BuilderSubList extends AbstractMutableList implements List, RandomAccess, Serializable, KMutableList {

        @NotNull
        private Object[] backing;
        private int length;
        private final int offset;

        @Nullable
        private final BuilderSubList parent;

        @NotNull
        private final ListBuilder root;

        public static final /* synthetic */ Object[] access$getBacking$p(BuilderSubList builderSubList) {
            return builderSubList.backing;
        }

        public static final /* synthetic */ int access$getLength$p(BuilderSubList builderSubList) {
            return builderSubList.length;
        }

        public static final /* synthetic */ int access$getModCount$p$s1462993667(BuilderSubList builderSubList) {
            return builderSubList.modCount;
        }

        public static final /* synthetic */ int access$getOffset$p(BuilderSubList builderSubList) {
            return builderSubList.offset;
        }

        public static final /* synthetic */ ListBuilder access$getRoot$p(BuilderSubList builderSubList) {
            return builderSubList.root;
        }

        public BuilderSubList(@NotNull Object[] backing, int i, int i2, @Nullable BuilderSubList builderSubList, @NotNull ListBuilder root) {
            Intrinsics.checkNotNullParameter(backing, "backing");
            Intrinsics.checkNotNullParameter(root, "root");
            this.backing = backing;
            this.offset = i;
            this.length = i2;
            this.parent = builderSubList;
            this.root = root;
            this.modCount = ListBuilder.access$getModCount$p$s-2084097795(root);
        }

        private final Object writeReplace() {
            if (isReadOnly()) {
                return new SerializedCollection((Collection) this, 0);
            }
            throw new NotSerializableException("The list cannot be serialized while it is being built.");
        }

        public int getSize() {
            checkForComodification();
            return this.length;
        }

        public boolean isEmpty() {
            checkForComodification();
            return this.length == 0;
        }

        public Object get(int index) {
            checkForComodification();
            AbstractList.INSTANCE.checkElementIndex$kotlin_stdlib(index, this.length);
            return this.backing[this.offset + index];
        }

        public Object set(int index, Object element) {
            checkIsMutable();
            checkForComodification();
            AbstractList.INSTANCE.checkElementIndex$kotlin_stdlib(index, this.length);
            Object[] objArr = this.backing;
            int i = this.offset;
            Object obj = objArr[i + index];
            objArr[i + index] = element;
            return obj;
        }

        public int indexOf(Object element) {
            checkForComodification();
            for (int i = 0; i < this.length; i++) {
                if (Intrinsics.areEqual(this.backing[this.offset + i], element)) {
                    return i;
                }
            }
            return -1;
        }

        public int lastIndexOf(Object element) {
            checkForComodification();
            for (int i = this.length - 1; i >= 0; i--) {
                if (Intrinsics.areEqual(this.backing[this.offset + i], element)) {
                    return i;
                }
            }
            return -1;
        }

        @NotNull
        public Iterator iterator() {
            return listIterator(0);
        }

        @NotNull
        public ListIterator listIterator() {
            return listIterator(0);
        }

        @NotNull
        public ListIterator listIterator(int index) {
            checkForComodification();
            AbstractList.INSTANCE.checkPositionIndex$kotlin_stdlib(index, this.length);
            return new Itr(this, index);
        }

        public boolean add(Object element) {
            checkIsMutable();
            checkForComodification();
            addAtInternal(this.offset + this.length, element);
            return true;
        }

        public void add(int index, Object element) {
            checkIsMutable();
            checkForComodification();
            AbstractList.INSTANCE.checkPositionIndex$kotlin_stdlib(index, this.length);
            addAtInternal(this.offset + index, element);
        }

        public boolean addAll(@NotNull Collection elements) {
            Intrinsics.checkNotNullParameter(elements, "elements");
            checkIsMutable();
            checkForComodification();
            int size = elements.size();
            addAllInternal(this.offset + this.length, elements, size);
            return size > 0;
        }

        public boolean addAll(int index, @NotNull Collection elements) {
            Intrinsics.checkNotNullParameter(elements, "elements");
            checkIsMutable();
            checkForComodification();
            AbstractList.INSTANCE.checkPositionIndex$kotlin_stdlib(index, this.length);
            int size = elements.size();
            addAllInternal(this.offset + index, elements, size);
            return size > 0;
        }

        public void clear() {
            checkIsMutable();
            checkForComodification();
            removeRangeInternal(this.offset, this.length);
        }

        public Object removeAt(int index) {
            checkIsMutable();
            checkForComodification();
            AbstractList.INSTANCE.checkElementIndex$kotlin_stdlib(index, this.length);
            return removeAtInternal(this.offset + index);
        }

        public boolean remove(Object element) {
            checkIsMutable();
            checkForComodification();
            int indexOf = indexOf(element);
            if (indexOf >= 0) {
                remove(indexOf);
            }
            return indexOf >= 0;
        }

        public boolean removeAll(@NotNull Collection elements) {
            Intrinsics.checkNotNullParameter(elements, "elements");
            checkIsMutable();
            checkForComodification();
            return retainOrRemoveAllInternal(this.offset, this.length, elements, false) > 0;
        }

        public boolean retainAll(@NotNull Collection elements) {
            Intrinsics.checkNotNullParameter(elements, "elements");
            checkIsMutable();
            checkForComodification();
            return retainOrRemoveAllInternal(this.offset, this.length, elements, true) > 0;
        }

        @NotNull
        public List subList(int fromIndex, int toIndex) {
            AbstractList.INSTANCE.checkRangeIndexes$kotlin_stdlib(fromIndex, toIndex, this.length);
            return new BuilderSubList(this.backing, this.offset + fromIndex, toIndex - fromIndex, this, this.root);
        }

        @NotNull
        public Object[] toArray(@NotNull Object[] array) {
            Intrinsics.checkNotNullParameter(array, "array");
            checkForComodification();
            int length = array.length;
            int i = this.length;
            if (length < i) {
                Object[] objArr = this.backing;
                int i2 = this.offset;
                Object[] copyOfRange = Arrays.copyOfRange(objArr, i2, i + i2, array.getClass());
                Intrinsics.checkNotNullExpressionValue(copyOfRange, "copyOfRange(...)");
                return copyOfRange;
            }
            Object[] objArr2 = this.backing;
            int i3 = this.offset;
            ArraysKt.copyInto(objArr2, array, 0, i3, i + i3);
            return CollectionsKt.terminateCollectionToArray(this.length, array);
        }

        @NotNull
        public Object[] toArray() {
            checkForComodification();
            Object[] objArr = this.backing;
            int i = this.offset;
            return ArraysKt.copyOfRange(objArr, i, this.length + i);
        }

        public boolean equals(@Nullable Object other) {
            checkForComodification();
            if (other != this) {
                return (other instanceof List) && contentEquals((List) other);
            }
            return true;
        }

        public int hashCode() {
            checkForComodification();
            return ListBuilderKt.access$subarrayContentHashCode(this.backing, this.offset, this.length);
        }

        @NotNull
        public String toString() {
            checkForComodification();
            return ListBuilderKt.access$subarrayContentToString(this.backing, this.offset, this.length, (Collection) this);
        }

        private final void registerModification() {
            this.modCount++;
        }

        private final void checkForComodification() {
            if (ListBuilder.access$getModCount$p$s-2084097795(this.root) != this.modCount) {
                throw new ConcurrentModificationException();
            }
        }

        private final void checkIsMutable() {
            if (isReadOnly()) {
                throw new UnsupportedOperationException();
            }
        }

        private final boolean isReadOnly() {
            return ListBuilder.access$isReadOnly$p(this.root);
        }

        private final boolean contentEquals(List other) {
            return ListBuilderKt.access$subarrayContentEquals(this.backing, this.offset, this.length, other);
        }

        private final void addAtInternal(int i, Object element) {
            registerModification();
            BuilderSubList builderSubList = this.parent;
            if (builderSubList != null) {
                builderSubList.addAtInternal(i, element);
            } else {
                ListBuilder.access$addAtInternal(this.root, i, element);
            }
            this.backing = ListBuilder.access$getBacking$p(this.root);
            this.length++;
        }

        private final void addAllInternal(int i, Collection elements, int n) {
            registerModification();
            BuilderSubList builderSubList = this.parent;
            if (builderSubList != null) {
                builderSubList.addAllInternal(i, elements, n);
            } else {
                ListBuilder.access$addAllInternal(this.root, i, elements, n);
            }
            this.backing = ListBuilder.access$getBacking$p(this.root);
            this.length += n;
        }

        private final Object removeAtInternal(int i) {
            Object access$removeAtInternal;
            registerModification();
            BuilderSubList builderSubList = this.parent;
            if (builderSubList != null) {
                access$removeAtInternal = builderSubList.removeAtInternal(i);
            } else {
                access$removeAtInternal = ListBuilder.access$removeAtInternal(this.root, i);
            }
            this.length--;
            return access$removeAtInternal;
        }

        private final void removeRangeInternal(int rangeOffset, int rangeLength) {
            if (rangeLength > 0) {
                registerModification();
            }
            BuilderSubList builderSubList = this.parent;
            if (builderSubList != null) {
                builderSubList.removeRangeInternal(rangeOffset, rangeLength);
            } else {
                ListBuilder.access$removeRangeInternal(this.root, rangeOffset, rangeLength);
            }
            this.length -= rangeLength;
        }

        private final int retainOrRemoveAllInternal(int rangeOffset, int rangeLength, Collection elements, boolean retain) {
            int access$retainOrRemoveAllInternal;
            BuilderSubList builderSubList = this.parent;
            if (builderSubList != null) {
                access$retainOrRemoveAllInternal = builderSubList.retainOrRemoveAllInternal(rangeOffset, rangeLength, elements, retain);
            } else {
                access$retainOrRemoveAllInternal = ListBuilder.access$retainOrRemoveAllInternal(this.root, rangeOffset, rangeLength, elements, retain);
            }
            if (access$retainOrRemoveAllInternal > 0) {
                registerModification();
            }
            this.length -= access$retainOrRemoveAllInternal;
            return access$retainOrRemoveAllInternal;
        }

        /* compiled from: ListBuilder.kt */
        @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010+\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0002\u0018\u0000*\u0004\b\u0002\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001d\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\b\u0010\u000b\u001a\u00020\fH\u0016J\t\u0010\r\u001a\u00020\fH\u0096\u0002J\b\u0010\u000e\u001a\u00020\u0006H\u0016J\b\u0010\u000f\u001a\u00020\u0006H\u0016J\r\u0010\u0010\u001a\u00028\u0002H\u0016¢\u0006\u0002\u0010\u0011J\u000e\u0010\u0012\u001a\u00028\u0002H\u0096\u0002¢\u0006\u0002\u0010\u0011J\u0015\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0002H\u0016¢\u0006\u0002\u0010\u0016J\u0015\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0002H\u0016¢\u0006\u0002\u0010\u0016J\b\u0010\u0018\u001a\u00020\u0014H\u0016J\b\u0010\u0019\u001a\u00020\u0014H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lkotlin/collections/builders/ListBuilder$BuilderSubList$Itr;", "E", "", "list", "Lkotlin/collections/builders/ListBuilder$BuilderSubList;", "index", "", "<init>", "(Lkotlin/collections/builders/ListBuilder$BuilderSubList;I)V", "lastIndex", "expectedModCount", "hasPrevious", "", "hasNext", "previousIndex", "nextIndex", "previous", "()Ljava/lang/Object;", "next", "set", "", "element", "(Ljava/lang/Object;)V", "add", "remove", "checkForComodification", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
        @SourceDebugExtension({"SMAP\nListBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ListBuilder.kt\nkotlin/collections/builders/ListBuilder$BuilderSubList$Itr\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,718:1\n1#2:719\n*E\n"})
        private static final class Itr implements ListIterator, KMutableListIterator {
            private int expectedModCount;
            private int index;
            private int lastIndex;

            @NotNull
            private final BuilderSubList list;

            public Itr(@NotNull BuilderSubList list, int i) {
                Intrinsics.checkNotNullParameter(list, "list");
                this.list = list;
                this.index = i;
                this.lastIndex = -1;
                this.expectedModCount = BuilderSubList.access$getModCount$p$s1462993667(list);
            }

            public boolean hasPrevious() {
                return this.index > 0;
            }

            public boolean hasNext() {
                return this.index < BuilderSubList.access$getLength$p(this.list);
            }

            public int previousIndex() {
                return this.index - 1;
            }

            /* renamed from: nextIndex, reason: from getter */
            public int getIndex() {
                return this.index;
            }

            public Object previous() {
                checkForComodification();
                int i = this.index;
                if (i <= 0) {
                    throw new NoSuchElementException();
                }
                int i2 = i - 1;
                this.index = i2;
                this.lastIndex = i2;
                return BuilderSubList.access$getBacking$p(this.list)[BuilderSubList.access$getOffset$p(this.list) + this.lastIndex];
            }

            public Object next() {
                checkForComodification();
                if (this.index >= BuilderSubList.access$getLength$p(this.list)) {
                    throw new NoSuchElementException();
                }
                int i = this.index;
                this.index = i + 1;
                this.lastIndex = i;
                return BuilderSubList.access$getBacking$p(this.list)[BuilderSubList.access$getOffset$p(this.list) + this.lastIndex];
            }

            public void set(Object element) {
                checkForComodification();
                int i = this.lastIndex;
                if (i == -1) {
                    throw new IllegalStateException("Call next() or previous() before replacing element from the iterator.".toString());
                }
                this.list.set(i, element);
            }

            public void add(Object element) {
                checkForComodification();
                BuilderSubList builderSubList = this.list;
                int i = this.index;
                this.index = i + 1;
                builderSubList.add(i, element);
                this.lastIndex = -1;
                this.expectedModCount = BuilderSubList.access$getModCount$p$s1462993667(this.list);
            }

            public void remove() {
                checkForComodification();
                int i = this.lastIndex;
                if (i == -1) {
                    throw new IllegalStateException("Call next() or previous() before removing element from the iterator.".toString());
                }
                this.list.remove(i);
                this.index = this.lastIndex;
                this.lastIndex = -1;
                this.expectedModCount = BuilderSubList.access$getModCount$p$s1462993667(this.list);
            }

            private final void checkForComodification() {
                if (ListBuilder.access$getModCount$p$s-2084097795(BuilderSubList.access$getRoot$p(this.list)) != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        }
    }
}
