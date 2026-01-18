package org.antlr.v4.runtime.misc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class IntervalSet implements IntSet {
    public static final IntervalSet COMPLETE_CHAR_SET = of(0, 1114111);
    public static final IntervalSet EMPTY_SET;
    protected List intervals;
    protected boolean readonly;

    static {
        COMPLETE_CHAR_SET.setReadonly(true);
        EMPTY_SET = new IntervalSet(new int[0]);
        EMPTY_SET.setReadonly(true);
    }

    public IntervalSet(List list) {
        this.intervals = list;
    }

    public IntervalSet(IntervalSet set) {
        this(new int[0]);
        addAll((IntSet) set);
    }

    public IntervalSet(int... els) {
        if (els == null) {
            this.intervals = new ArrayList(2);
            return;
        }
        this.intervals = new ArrayList(els.length);
        for (int e : els) {
            add(e);
        }
    }

    public static IntervalSet of(int a) {
        IntervalSet s = new IntervalSet(new int[0]);
        s.add(a);
        return s;
    }

    public static IntervalSet of(int a, int b) {
        IntervalSet s = new IntervalSet(new int[0]);
        s.add(a, b);
        return s;
    }

    public void clear() {
        if (this.readonly) {
            throw new IllegalStateException("can't alter readonly IntervalSet");
        }
        this.intervals.clear();
    }

    public void add(int el) {
        if (this.readonly) {
            throw new IllegalStateException("can't alter readonly IntervalSet");
        }
        add(el, el);
    }

    public void add(int a, int b) {
        add(Interval.of(a, b));
    }

    protected void add(Interval addition) {
        if (this.readonly) {
            throw new IllegalStateException("can't alter readonly IntervalSet");
        }
        if (addition.b >= addition.a) {
            ListIterator<Interval> iter = this.intervals.listIterator();
            while (iter.hasNext()) {
                Interval r = (Interval) iter.next();
                if (!addition.equals(r)) {
                    if (addition.adjacent(r) || !addition.disjoint(r)) {
                        Interval bigger = addition.union(r);
                        iter.set(bigger);
                        while (iter.hasNext()) {
                            Interval next = (Interval) iter.next();
                            if (bigger.adjacent(next) || !bigger.disjoint(next)) {
                                iter.remove();
                                iter.previous();
                                iter.set(bigger.union(next));
                                iter.next();
                            } else {
                                return;
                            }
                        }
                        return;
                    }
                    if (addition.startsBeforeDisjoint(r)) {
                        iter.previous();
                        iter.add(addition);
                        return;
                    }
                } else {
                    return;
                }
            }
            this.intervals.add(addition);
        }
    }

    public static IntervalSet or(IntervalSet[] sets) {
        IntervalSet r = new IntervalSet(new int[0]);
        for (IntervalSet s : sets) {
            r.addAll((IntSet) s);
        }
        return r;
    }

    public IntervalSet addAll(IntSet set) {
        if (set != null) {
            if (set instanceof IntervalSet) {
                IntervalSet other = (IntervalSet) set;
                int n = other.intervals.size();
                for (int i = 0; i < n; i++) {
                    Interval I = (Interval) other.intervals.get(i);
                    add(I.a, I.b);
                }
            } else {
                Iterator i$ = set.toList().iterator();
                while (i$.hasNext()) {
                    int value = ((Integer) i$.next()).intValue();
                    add(value);
                }
            }
        }
        return this;
    }

    public IntervalSet complement(int minElement, int maxElement) {
        return complement((IntSet) of(minElement, maxElement));
    }

    public IntervalSet complement(IntSet vocabulary) {
        IntervalSet vocabularyIS;
        if (vocabulary == null || vocabulary.isNil()) {
            return null;
        }
        if (vocabulary instanceof IntervalSet) {
            vocabularyIS = (IntervalSet) vocabulary;
        } else {
            vocabularyIS = new IntervalSet(new int[0]);
            vocabularyIS.addAll(vocabulary);
        }
        return vocabularyIS.subtract((IntSet) this);
    }

    public IntervalSet subtract(IntSet a) {
        if (a == null || a.isNil()) {
            return new IntervalSet(this);
        }
        if (a instanceof IntervalSet) {
            return subtract(this, (IntervalSet) a);
        }
        IntervalSet other = new IntervalSet(new int[0]);
        other.addAll(a);
        return subtract(this, other);
    }

    public static IntervalSet subtract(IntervalSet left, IntervalSet right) {
        if (left == null || left.isNil()) {
            return new IntervalSet(new int[0]);
        }
        IntervalSet result = new IntervalSet(left);
        if (right != null && !right.isNil()) {
            int resultI = 0;
            int rightI = 0;
            while (resultI < result.intervals.size() && rightI < right.intervals.size()) {
                Interval resultInterval = (Interval) result.intervals.get(resultI);
                Interval rightInterval = (Interval) right.intervals.get(rightI);
                if (rightInterval.b < resultInterval.a) {
                    rightI++;
                } else if (rightInterval.a > resultInterval.b) {
                    resultI++;
                } else {
                    Interval beforeCurrent = null;
                    Interval afterCurrent = null;
                    if (rightInterval.a > resultInterval.a) {
                        beforeCurrent = new Interval(resultInterval.a, rightInterval.a - 1);
                    }
                    if (rightInterval.b < resultInterval.b) {
                        afterCurrent = new Interval(rightInterval.b + 1, resultInterval.b);
                    }
                    if (beforeCurrent != null) {
                        if (afterCurrent != null) {
                            result.intervals.set(resultI, beforeCurrent);
                            result.intervals.add(resultI + 1, afterCurrent);
                            resultI++;
                            rightI++;
                        } else {
                            result.intervals.set(resultI, beforeCurrent);
                            resultI++;
                        }
                    } else if (afterCurrent != null) {
                        result.intervals.set(resultI, afterCurrent);
                        rightI++;
                    } else {
                        result.intervals.remove(resultI);
                    }
                }
            }
            return result;
        }
        return result;
    }

    public IntervalSet or(IntSet a) {
        IntervalSet o = new IntervalSet(new int[0]);
        o.addAll((IntSet) this);
        o.addAll(a);
        return o;
    }

    public IntervalSet and(IntSet other) {
        if (other == null) {
            return null;
        }
        List<Interval> myIntervals = this.intervals;
        List<Interval> theirIntervals = ((IntervalSet) other).intervals;
        IntervalSet intersection = null;
        int mySize = myIntervals.size();
        int theirSize = theirIntervals.size();
        int i = 0;
        int j = 0;
        while (i < mySize && j < theirSize) {
            Interval mine = (Interval) myIntervals.get(i);
            Interval theirs = (Interval) theirIntervals.get(j);
            if (mine.startsBeforeDisjoint(theirs)) {
                i++;
            } else if (theirs.startsBeforeDisjoint(mine)) {
                j++;
            } else if (mine.properlyContains(theirs)) {
                if (intersection == null) {
                    intersection = new IntervalSet(new int[0]);
                }
                intersection.add(mine.intersection(theirs));
                j++;
            } else if (theirs.properlyContains(mine)) {
                if (intersection == null) {
                    intersection = new IntervalSet(new int[0]);
                }
                intersection.add(mine.intersection(theirs));
                i++;
            } else if (!mine.disjoint(theirs)) {
                if (intersection == null) {
                    intersection = new IntervalSet(new int[0]);
                }
                intersection.add(mine.intersection(theirs));
                if (mine.startsAfterNonDisjoint(theirs)) {
                    j++;
                } else if (theirs.startsAfterNonDisjoint(mine)) {
                    i++;
                }
            }
        }
        if (intersection == null) {
            return new IntervalSet(new int[0]);
        }
        return intersection;
    }

    public boolean contains(int el) {
        int n = this.intervals.size();
        int l = 0;
        int r = n - 1;
        while (l <= r) {
            int m = (l + r) / 2;
            Interval I = (Interval) this.intervals.get(m);
            int a = I.a;
            int b = I.b;
            if (b < el) {
                l = m + 1;
            } else if (a > el) {
                r = m - 1;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean isNil() {
        return this.intervals == null || this.intervals.isEmpty();
    }

    public int getMaxElement() {
        if (isNil()) {
            throw new RuntimeException("set is empty");
        }
        Interval last = (Interval) this.intervals.get(this.intervals.size() - 1);
        return last.b;
    }

    public int getMinElement() {
        if (isNil()) {
            throw new RuntimeException("set is empty");
        }
        return ((Interval) this.intervals.get(0)).a;
    }

    public List getIntervals() {
        return this.intervals;
    }

    public int hashCode() {
        int hash = MurmurHash.initialize();
        for (Interval I : this.intervals) {
            hash = MurmurHash.update(MurmurHash.update(hash, I.a), I.b);
        }
        return MurmurHash.finish(hash, this.intervals.size() * 2);
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof IntervalSet)) {
            return false;
        }
        IntervalSet other = (IntervalSet) obj;
        return this.intervals.equals(other.intervals);
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean elemAreChar) {
        StringBuilder buf = new StringBuilder();
        if (this.intervals == null || this.intervals.isEmpty()) {
            return "{}";
        }
        if (size() > 1) {
            buf.append("{");
        }
        Iterator<Interval> iter = this.intervals.iterator();
        while (iter.hasNext()) {
            Interval I = (Interval) iter.next();
            int a = I.a;
            int b = I.b;
            if (a == b) {
                if (a == -1) {
                    buf.append("<EOF>");
                } else if (elemAreChar) {
                    buf.append("'").appendCodePoint(a).append("'");
                } else {
                    buf.append(a);
                }
            } else if (elemAreChar) {
                buf.append("'").appendCodePoint(a).append("'..'").appendCodePoint(b).append("'");
            } else {
                buf.append(a).append("..").append(b);
            }
            if (iter.hasNext()) {
                buf.append(", ");
            }
        }
        if (size() > 1) {
            buf.append("}");
        }
        return buf.toString();
    }

    @Deprecated
    public String toString(String[] tokenNames) {
        return toString(VocabularyImpl.fromTokenNames(tokenNames));
    }

    public String toString(Vocabulary vocabulary) {
        StringBuilder buf = new StringBuilder();
        if (this.intervals == null || this.intervals.isEmpty()) {
            return "{}";
        }
        if (size() > 1) {
            buf.append("{");
        }
        Iterator<Interval> iter = this.intervals.iterator();
        while (iter.hasNext()) {
            Interval I = (Interval) iter.next();
            int a = I.a;
            int b = I.b;
            if (a == b) {
                buf.append(elementName(vocabulary, a));
            } else {
                for (int i = a; i <= b; i++) {
                    if (i > a) {
                        buf.append(", ");
                    }
                    buf.append(elementName(vocabulary, i));
                }
            }
            if (iter.hasNext()) {
                buf.append(", ");
            }
        }
        if (size() > 1) {
            buf.append("}");
        }
        return buf.toString();
    }

    @Deprecated
    protected String elementName(String[] tokenNames, int a) {
        return elementName(VocabularyImpl.fromTokenNames(tokenNames), a);
    }

    protected String elementName(Vocabulary vocabulary, int a) {
        if (a == -1) {
            return "<EOF>";
        }
        if (a == -2) {
            return "<EPSILON>";
        }
        return vocabulary.getDisplayName(a);
    }

    public int size() {
        int numIntervals = this.intervals.size();
        if (numIntervals == 1) {
            Interval firstInterval = (Interval) this.intervals.get(0);
            return (firstInterval.b - firstInterval.a) + 1;
        }
        int i = 0;
        int n = 0;
        while (i < numIntervals) {
            Interval I = (Interval) this.intervals.get(i);
            i++;
            n += (I.b - I.a) + 1;
        }
        return n;
    }

    public IntegerList toIntegerList() {
        IntegerList values = new IntegerList(size());
        int n = this.intervals.size();
        for (int i = 0; i < n; i++) {
            Interval I = (Interval) this.intervals.get(i);
            int a = I.a;
            int b = I.b;
            for (int v = a; v <= b; v++) {
                values.add(v);
            }
        }
        return values;
    }

    public List toList() {
        ArrayList arrayList = new ArrayList();
        int n = this.intervals.size();
        for (int i = 0; i < n; i++) {
            Interval I = (Interval) this.intervals.get(i);
            int a = I.a;
            int b = I.b;
            for (int v = a; v <= b; v++) {
                arrayList.add(Integer.valueOf(v));
            }
        }
        return arrayList;
    }

    public Set toSet() {
        HashSet hashSet = new HashSet();
        for (Interval I : this.intervals) {
            int a = I.a;
            int b = I.b;
            for (int v = a; v <= b; v++) {
                hashSet.add(Integer.valueOf(v));
            }
        }
        return hashSet;
    }

    public int get(int i) {
        int n = this.intervals.size();
        int index = 0;
        for (int j = 0; j < n; j++) {
            Interval I = (Interval) this.intervals.get(j);
            int a = I.a;
            int b = I.b;
            for (int v = a; v <= b; v++) {
                if (index != i) {
                    index++;
                } else {
                    return v;
                }
            }
        }
        return -1;
    }

    public int[] toArray() {
        return toIntegerList().toArray();
    }

    public void remove(int el) {
        if (this.readonly) {
            throw new IllegalStateException("can't alter readonly IntervalSet");
        }
        int n = this.intervals.size();
        for (int i = 0; i < n; i++) {
            Interval I = (Interval) this.intervals.get(i);
            int a = I.a;
            int b = I.b;
            if (el >= a) {
                if (el == a && el == b) {
                    this.intervals.remove(i);
                    return;
                }
                if (el == a) {
                    I.a++;
                    return;
                }
                if (el == b) {
                    I.b--;
                    return;
                }
                if (el > a && el < b) {
                    int oldb = I.b;
                    I.b = el - 1;
                    add(el + 1, oldb);
                }
            } else {
                return;
            }
        }
    }

    public boolean isReadonly() {
        return this.readonly;
    }

    public void setReadonly(boolean readonly) {
        if (this.readonly && !readonly) {
            throw new IllegalStateException("can't alter readonly IntervalSet");
        }
        this.readonly = readonly;
    }
}
