package java.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class AbstractList extends AbstractCollection implements List {
    protected transient int modCount = 0;

    public /* synthetic */ void forEach(Consumer consumer) {
        Collection.-CC.$default$forEach(this, consumer);
    }

    public abstract Object get(int i);

    public /* synthetic */ Stream parallelStream() {
        return Collection.-CC.$default$parallelStream(this);
    }

    public /* synthetic */ boolean removeIf(Predicate predicate) {
        return Collection.-CC.$default$removeIf(this, predicate);
    }

    public /* synthetic */ void replaceAll(UnaryOperator unaryOperator) {
        List.-CC.$default$replaceAll(this, unaryOperator);
    }

    public /* synthetic */ void sort(Comparator comparator) {
        List.-CC.$default$sort(this, comparator);
    }

    public /* synthetic */ Spliterator spliterator() {
        return List.-CC.$default$spliterator(this);
    }

    public /* synthetic */ Stream stream() {
        return Collection.-CC.$default$stream(this);
    }

    public /* synthetic */ Object[] toArray(IntFunction intFunction) {
        return Collection.-CC.$default$toArray(this, intFunction);
    }

    protected AbstractList() {
    }

    public boolean add(Object obj) {
        add(size(), obj);
        return true;
    }

    public Object set(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    public void add(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    public Object remove(int i) {
        throw new UnsupportedOperationException();
    }

    public int indexOf(Object obj) {
        ListIterator listIterator = listIterator();
        if (obj == null) {
            while (listIterator.hasNext()) {
                if (listIterator.next() == null) {
                    return listIterator.previousIndex();
                }
            }
            return -1;
        }
        while (listIterator.hasNext()) {
            if (obj.equals(listIterator.next())) {
                return listIterator.previousIndex();
            }
        }
        return -1;
    }

    public int lastIndexOf(Object obj) {
        ListIterator listIterator = listIterator(size());
        if (obj == null) {
            while (listIterator.hasPrevious()) {
                if (listIterator.previous() == null) {
                    return listIterator.nextIndex();
                }
            }
            return -1;
        }
        while (listIterator.hasPrevious()) {
            if (obj.equals(listIterator.previous())) {
                return listIterator.nextIndex();
            }
        }
        return -1;
    }

    public void clear() {
        removeRange(0, size());
    }

    public boolean addAll(int i, Collection collection) {
        rangeCheckForAdd(i);
        Iterator it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            add(i, it.next());
            z = true;
            i++;
        }
        return z;
    }

    public Iterator iterator() {
        return new Itr(this, null);
    }

    public ListIterator listIterator() {
        return listIterator(0);
    }

    public ListIterator listIterator(int i) {
        rangeCheckForAdd(i);
        return new ListItr(i);
    }

    private class Itr implements Iterator {
        int cursor;
        int expectedModCount;
        int lastRet;

        /* synthetic */ Itr(AbstractList abstractList, AbstractList-IA r2) {
            this();
        }

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.-CC.$default$forEachRemaining(this, consumer);
        }

        private Itr() {
            this.cursor = 0;
            this.lastRet = -1;
            this.expectedModCount = AbstractList.this.modCount;
        }

        public boolean hasNext() {
            return this.cursor != AbstractList.this.size();
        }

        public Object next() {
            checkForComodification();
            try {
                int i = this.cursor;
                Object obj = AbstractList.this.get(i);
                this.lastRet = i;
                this.cursor = i + 1;
                return obj;
            } catch (IndexOutOfBoundsException unused) {
                checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            }
            checkForComodification();
            try {
                AbstractList.this.remove(this.lastRet);
                int i = this.lastRet;
                int i2 = this.cursor;
                if (i < i2) {
                    this.cursor = i2 - 1;
                }
                this.lastRet = -1;
                this.expectedModCount = AbstractList.this.modCount;
            } catch (IndexOutOfBoundsException unused) {
                throw new ConcurrentModificationException();
            }
        }

        final void checkForComodification() {
            if (AbstractList.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private class ListItr extends Itr implements ListIterator {
        ListItr(int i) {
            super(AbstractList.this, null);
            this.cursor = i;
        }

        public boolean hasPrevious() {
            return this.cursor != 0;
        }

        public Object previous() {
            checkForComodification();
            try {
                int i = this.cursor - 1;
                Object obj = AbstractList.this.get(i);
                this.cursor = i;
                this.lastRet = i;
                return obj;
            } catch (IndexOutOfBoundsException unused) {
                checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public int nextIndex() {
            return this.cursor;
        }

        public int previousIndex() {
            return this.cursor - 1;
        }

        public void set(Object obj) {
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            }
            checkForComodification();
            try {
                AbstractList.this.set(this.lastRet, obj);
                this.expectedModCount = AbstractList.this.modCount;
            } catch (IndexOutOfBoundsException unused) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(Object obj) {
            checkForComodification();
            try {
                int i = this.cursor;
                AbstractList.this.add(i, obj);
                this.lastRet = -1;
                this.cursor = i + 1;
                this.expectedModCount = AbstractList.this.modCount;
            } catch (IndexOutOfBoundsException unused) {
                throw new ConcurrentModificationException();
            }
        }
    }

    public List subList(int i, int i2) {
        subListRangeCheck(i, i2, size());
        if (this instanceof RandomAccess) {
            return new RandomAccessSubList(this, i, i2);
        }
        return new SubList(this, i, i2);
    }

    static void subListRangeCheck(int i, int i2, int i3) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("fromIndex = " + i);
        }
        if (i2 > i3) {
            throw new IndexOutOfBoundsException("toIndex = " + i2);
        }
        if (i <= i2) {
            return;
        }
        throw new IllegalArgumentException("fromIndex(" + i + ") > toIndex(" + i2 + ")");
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        ListIterator listIterator = listIterator();
        ListIterator listIterator2 = ((List) obj).listIterator();
        while (listIterator.hasNext() && listIterator2.hasNext()) {
            Object next = listIterator.next();
            Object next2 = listIterator2.next();
            if (next == null) {
                if (next2 != null) {
                    return false;
                }
            } else if (!next.equals(next2)) {
                return false;
            }
        }
        return (listIterator.hasNext() || listIterator2.hasNext()) ? false : true;
    }

    public int hashCode() {
        Iterator it = iterator();
        int i = 1;
        while (it.hasNext()) {
            Object next = it.next();
            i = (i * 31) + (next == null ? 0 : next.hashCode());
        }
        return i;
    }

    protected void removeRange(int i, int i2) {
        ListIterator listIterator = listIterator(i);
        int i3 = i2 - i;
        for (int i4 = 0; i4 < i3; i4++) {
            listIterator.next();
            listIterator.remove();
        }
    }

    private void rangeCheckForAdd(int i) {
        if (i < 0 || i > size()) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(i));
        }
    }

    private String outOfBoundsMsg(int i) {
        return "Index: " + i + ", Size: " + size();
    }

    static final class RandomAccessSpliterator implements Spliterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final AbstractList alist;
        private int expectedModCount;
        private int fence;
        private int index;
        private final List list;

        public int characteristics() {
            return 16464;
        }

        public /* synthetic */ Comparator getComparator() {
            return Spliterator.-CC.$default$getComparator(this);
        }

        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.-CC.$default$getExactSizeIfKnown(this);
        }

        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.-CC.$default$hasCharacteristics(this, i);
        }

        RandomAccessSpliterator(List list) {
            this.list = list;
            this.index = 0;
            this.fence = -1;
            AbstractList abstractList = list instanceof AbstractList ? (AbstractList) list : null;
            this.alist = abstractList;
            this.expectedModCount = abstractList != null ? abstractList.modCount : 0;
        }

        private RandomAccessSpliterator(RandomAccessSpliterator randomAccessSpliterator, int i, int i2) {
            this.list = randomAccessSpliterator.list;
            this.index = i;
            this.fence = i2;
            this.alist = randomAccessSpliterator.alist;
            this.expectedModCount = randomAccessSpliterator.expectedModCount;
        }

        private int getFence() {
            List list = this.list;
            int i = this.fence;
            if (i >= 0) {
                return i;
            }
            AbstractList abstractList = this.alist;
            if (abstractList != null) {
                this.expectedModCount = abstractList.modCount;
            }
            int size = list.size();
            this.fence = size;
            return size;
        }

        public Spliterator trySplit() {
            int fence = getFence();
            int i = this.index;
            int i2 = (fence + i) >>> 1;
            if (i >= i2) {
                return null;
            }
            this.index = i2;
            return new RandomAccessSpliterator(this, i, i2);
        }

        public boolean tryAdvance(Consumer consumer) {
            consumer.getClass();
            int fence = getFence();
            int i = this.index;
            if (i >= fence) {
                return false;
            }
            this.index = i + 1;
            consumer.accept(get(this.list, i));
            checkAbstractListModCount(this.alist, this.expectedModCount);
            return true;
        }

        public void forEachRemaining(Consumer consumer) {
            consumer.getClass();
            List list = this.list;
            int fence = getFence();
            this.index = fence;
            for (int i = this.index; i < fence; i++) {
                consumer.accept(get(list, i));
            }
            checkAbstractListModCount(this.alist, this.expectedModCount);
        }

        public long estimateSize() {
            return getFence() - this.index;
        }

        private static Object get(List list, int i) {
            try {
                return list.get(i);
            } catch (IndexOutOfBoundsException unused) {
                throw new ConcurrentModificationException();
            }
        }

        static void checkAbstractListModCount(AbstractList abstractList, int i) {
            if (abstractList != null && abstractList.modCount != i) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private static class SubList extends AbstractList {
        private final int offset;
        private final SubList parent;
        private final AbstractList root;
        protected int size;

        static /* bridge */ /* synthetic */ int -$$Nest$fgetoffset(SubList subList) {
            return subList.offset;
        }

        static /* bridge */ /* synthetic */ AbstractList -$$Nest$fgetroot(SubList subList) {
            return subList.root;
        }

        static /* bridge */ /* synthetic */ void -$$Nest$mupdateSizeAndModCount(SubList subList, int i) {
            subList.updateSizeAndModCount(i);
        }

        public SubList(AbstractList abstractList, int i, int i2) {
            this.root = abstractList;
            this.parent = null;
            this.offset = i;
            this.size = i2 - i;
            this.modCount = abstractList.modCount;
        }

        protected SubList(SubList subList, int i, int i2) {
            AbstractList abstractList = subList.root;
            this.root = abstractList;
            this.parent = subList;
            this.offset = subList.offset + i;
            this.size = i2 - i;
            this.modCount = abstractList.modCount;
        }

        public Object set(int i, Object obj) {
            AbstractList$SubList$$ExternalSyntheticBackport0.m(i, this.size);
            checkForComodification();
            return this.root.set(this.offset + i, obj);
        }

        public Object get(int i) {
            AbstractList$SubList$$ExternalSyntheticBackport0.m(i, this.size);
            checkForComodification();
            return this.root.get(this.offset + i);
        }

        public int size() {
            checkForComodification();
            return this.size;
        }

        public void add(int i, Object obj) {
            rangeCheckForAdd(i);
            checkForComodification();
            this.root.add(this.offset + i, obj);
            updateSizeAndModCount(1);
        }

        public Object remove(int i) {
            AbstractList$SubList$$ExternalSyntheticBackport0.m(i, this.size);
            checkForComodification();
            Object remove = this.root.remove(this.offset + i);
            updateSizeAndModCount(-1);
            return remove;
        }

        protected void removeRange(int i, int i2) {
            checkForComodification();
            AbstractList abstractList = this.root;
            int i3 = this.offset;
            abstractList.removeRange(i3 + i, i3 + i2);
            updateSizeAndModCount(i - i2);
        }

        public boolean addAll(Collection collection) {
            return addAll(this.size, collection);
        }

        public boolean addAll(int i, Collection collection) {
            rangeCheckForAdd(i);
            int size = collection.size();
            if (size == 0) {
                return false;
            }
            checkForComodification();
            this.root.addAll(this.offset + i, collection);
            updateSizeAndModCount(size);
            return true;
        }

        public Iterator iterator() {
            return listIterator();
        }

        public ListIterator listIterator(int i) {
            checkForComodification();
            rangeCheckForAdd(i);
            return new 1(i);
        }

        class 1 implements ListIterator {
            private final ListIterator i;
            final /* synthetic */ int val$index;

            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.-CC.$default$forEachRemaining(this, consumer);
            }

            1(int i) {
                this.val$index = i;
                this.i = SubList.-$$Nest$fgetroot(SubList.this).listIterator(SubList.-$$Nest$fgetoffset(SubList.this) + i);
            }

            public boolean hasNext() {
                return nextIndex() < SubList.this.size;
            }

            public Object next() {
                if (hasNext()) {
                    return this.i.next();
                }
                throw new NoSuchElementException();
            }

            public boolean hasPrevious() {
                return previousIndex() >= 0;
            }

            public Object previous() {
                if (hasPrevious()) {
                    return this.i.previous();
                }
                throw new NoSuchElementException();
            }

            public int nextIndex() {
                return this.i.nextIndex() - SubList.-$$Nest$fgetoffset(SubList.this);
            }

            public int previousIndex() {
                return this.i.previousIndex() - SubList.-$$Nest$fgetoffset(SubList.this);
            }

            public void remove() {
                this.i.remove();
                SubList.-$$Nest$mupdateSizeAndModCount(SubList.this, -1);
            }

            public void set(Object obj) {
                this.i.set(obj);
            }

            public void add(Object obj) {
                this.i.add(obj);
                SubList.-$$Nest$mupdateSizeAndModCount(SubList.this, 1);
            }
        }

        public List subList(int i, int i2) {
            subListRangeCheck(i, i2, this.size);
            return new SubList(this, i, i2);
        }

        private void rangeCheckForAdd(int i) {
            if (i < 0 || i > this.size) {
                throw new IndexOutOfBoundsException(outOfBoundsMsg(i));
            }
        }

        private String outOfBoundsMsg(int i) {
            return "Index: " + i + ", Size: " + this.size;
        }

        private void checkForComodification() {
            if (this.root.modCount != this.modCount) {
                throw new ConcurrentModificationException();
            }
        }

        private void updateSizeAndModCount(int i) {
            SubList subList = this;
            do {
                subList.size += i;
                subList.modCount = this.root.modCount;
                subList = subList.parent;
            } while (subList != null);
        }
    }

    private static class RandomAccessSubList extends SubList implements RandomAccess {
        RandomAccessSubList(AbstractList abstractList, int i, int i2) {
            super(abstractList, i, i2);
        }

        RandomAccessSubList(RandomAccessSubList randomAccessSubList, int i, int i2) {
            super((SubList) randomAccessSubList, i, i2);
        }

        public List subList(int i, int i2) {
            subListRangeCheck(i, i2, this.size);
            return new RandomAccessSubList(this, i, i2);
        }
    }
}
