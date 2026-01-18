package org.antlr.v4.runtime.misc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class OrderedHashSet extends LinkedHashSet {
    protected ArrayList elements = new ArrayList();

    public Object get(int i) {
        return this.elements.get(i);
    }

    public Object set(int i, Object obj) {
        Object obj2 = this.elements.get(i);
        this.elements.set(i, obj);
        super.remove(obj2);
        super.add(obj);
        return obj2;
    }

    public boolean remove(int i) {
        return super.remove(this.elements.remove(i));
    }

    public boolean add(Object obj) {
        boolean result = super.add(obj);
        if (result) {
            this.elements.add(obj);
        }
        return result;
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        this.elements.clear();
        super.clear();
    }

    public int hashCode() {
        return this.elements.hashCode();
    }

    public boolean equals(Object o) {
        return (o instanceof OrderedHashSet) && this.elements != null && this.elements.equals(((OrderedHashSet) o).elements);
    }

    public Iterator iterator() {
        return this.elements.iterator();
    }

    public List elements() {
        return this.elements;
    }

    public Object clone() {
        OrderedHashSet orderedHashSet = (OrderedHashSet) super.clone();
        orderedHashSet.elements = new ArrayList(this.elements);
        return orderedHashSet;
    }

    public Object[] toArray() {
        return this.elements.toArray();
    }

    public String toString() {
        return this.elements.toString();
    }
}
