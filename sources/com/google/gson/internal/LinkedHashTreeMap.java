package com.google.gson.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public final class LinkedHashTreeMap extends AbstractMap implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Comparator NATURAL_ORDER;
    Comparator comparator;
    private EntrySet entrySet;
    final Node header;
    private KeySet keySet;
    int modCount;
    int size;
    Node[] table;
    int threshold;

    static {
        $assertionsDisabled = !LinkedHashTreeMap.class.desiredAssertionStatus();
        NATURAL_ORDER = new 1();
    }

    class 1 implements Comparator {
        1() {
        }

        public int compare(Comparable a, Comparable b) {
            return a.compareTo(b);
        }
    }

    public LinkedHashTreeMap() {
        this(NATURAL_ORDER);
    }

    public LinkedHashTreeMap(Comparator comparator) {
        this.size = 0;
        this.modCount = 0;
        this.comparator = comparator == null ? NATURAL_ORDER : comparator;
        this.header = new Node();
        this.table = new Node[16];
        this.threshold = (this.table.length / 2) + (this.table.length / 4);
    }

    public int size() {
        return this.size;
    }

    public Object get(Object key) {
        Node findByObject = findByObject(key);
        if (findByObject != null) {
            return findByObject.value;
        }
        return null;
    }

    public boolean containsKey(Object key) {
        return findByObject(key) != null;
    }

    public Object put(Object obj, Object obj2) {
        if (obj == null) {
            throw new NullPointerException("key == null");
        }
        Node find = find(obj, true);
        Object obj3 = find.value;
        find.value = obj2;
        return obj3;
    }

    public void clear() {
        Arrays.fill(this.table, (Object) null);
        this.size = 0;
        this.modCount++;
        Node node = this.header;
        Node node2 = node.next;
        while (node2 != node) {
            Node node3 = node2.next;
            node2.prev = null;
            node2.next = null;
            node2 = node3;
        }
        node.prev = node;
        node.next = node;
    }

    public Object remove(Object key) {
        Node removeInternalByKey = removeInternalByKey(key);
        if (removeInternalByKey != null) {
            return removeInternalByKey.value;
        }
        return null;
    }

    Node find(Object obj, boolean create) {
        Node node;
        Comparable<Object> comparableKey;
        Comparator comparator = this.comparator;
        Node[] nodeArr = this.table;
        int hash = secondaryHash(obj.hashCode());
        int index = hash & (nodeArr.length - 1);
        Node node2 = nodeArr[index];
        int comparison = 0;
        if (node2 != null) {
            if (comparator == NATURAL_ORDER) {
                comparableKey = (Comparable) obj;
            } else {
                comparableKey = null;
            }
            Node node3 = node2;
            while (true) {
                if (comparableKey != null) {
                    comparison = comparableKey.compareTo(node3.key);
                } else {
                    comparison = comparator.compare(obj, node3.key);
                }
                if (comparison == 0) {
                    return node3;
                }
                Node node4 = comparison < 0 ? node3.left : node3.right;
                if (node4 == null) {
                    node2 = node3;
                    break;
                }
                node3 = node4;
            }
        }
        if (!create) {
            return null;
        }
        Node node5 = this.header;
        if (node2 == null) {
            if (comparator == NATURAL_ORDER && !(obj instanceof Comparable)) {
                throw new ClassCastException(obj.getClass().getName() + " is not Comparable");
            }
            node = new Node(node2, obj, hash, node5, node5.prev);
            nodeArr[index] = node;
        } else {
            node = new Node(node2, obj, hash, node5, node5.prev);
            if (comparison < 0) {
                node2.left = node;
            } else {
                node2.right = node;
            }
            rebalance(node2, true);
        }
        int i = this.size;
        this.size = i + 1;
        if (i > this.threshold) {
            doubleCapacity();
        }
        this.modCount++;
        return node;
    }

    Node findByObject(Object key) {
        if (key == null) {
            return null;
        }
        try {
            return find(key, false);
        } catch (ClassCastException e) {
            return null;
        }
    }

    Node findByEntry(Map.Entry entry) {
        Node findByObject = findByObject(entry.getKey());
        boolean valuesEqual = findByObject != null && equal(findByObject.value, entry.getValue());
        if (valuesEqual) {
            return findByObject;
        }
        return null;
    }

    private boolean equal(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    private static int secondaryHash(int h) {
        int h2 = h ^ ((h >>> 20) ^ (h >>> 12));
        return ((h2 >>> 7) ^ h2) ^ (h2 >>> 4);
    }

    void removeInternal(Node node, boolean unlink) {
        if (unlink) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = null;
            node.next = null;
        }
        Node node2 = node.left;
        Node node3 = node.right;
        Node node4 = node.parent;
        if (node2 != null && node3 != null) {
            Node last = node2.height > node3.height ? node2.last() : node3.first();
            removeInternal(last, false);
            int leftHeight = 0;
            Node node5 = node.left;
            if (node5 != null) {
                leftHeight = node5.height;
                last.left = node5;
                node5.parent = last;
                node.left = null;
            }
            int rightHeight = 0;
            Node node6 = node.right;
            if (node6 != null) {
                rightHeight = node6.height;
                last.right = node6;
                node6.parent = last;
                node.right = null;
            }
            last.height = Math.max(leftHeight, rightHeight) + 1;
            replaceInParent(node, last);
            return;
        }
        if (node2 != null) {
            replaceInParent(node, node2);
            node.left = null;
        } else if (node3 != null) {
            replaceInParent(node, node3);
            node.right = null;
        } else {
            replaceInParent(node, null);
        }
        rebalance(node4, false);
        this.size--;
        this.modCount++;
    }

    Node removeInternalByKey(Object key) {
        Node findByObject = findByObject(key);
        if (findByObject != null) {
            removeInternal(findByObject, true);
        }
        return findByObject;
    }

    private void replaceInParent(Node node, Node node2) {
        Node node3 = node.parent;
        node.parent = null;
        if (node2 != null) {
            node2.parent = node3;
        }
        if (node3 != null) {
            if (node3.left == node) {
                node3.left = node2;
                return;
            } else {
                if (!$assertionsDisabled && node3.right != node) {
                    throw new AssertionError();
                }
                node3.right = node2;
                return;
            }
        }
        int index = node.hash & (this.table.length - 1);
        this.table[index] = node2;
    }

    private void rebalance(Node node, boolean insert) {
        for (Node node2 = node; node2 != null; node2 = node2.parent) {
            Node node3 = node2.left;
            Node node4 = node2.right;
            int leftHeight = node3 != null ? node3.height : 0;
            int rightHeight = node4 != null ? node4.height : 0;
            int delta = leftHeight - rightHeight;
            if (delta == -2) {
                Node node5 = node4.left;
                Node node6 = node4.right;
                int rightRightHeight = node6 != null ? node6.height : 0;
                int rightLeftHeight = node5 != null ? node5.height : 0;
                int rightDelta = rightLeftHeight - rightRightHeight;
                if (rightDelta == -1 || (rightDelta == 0 && !insert)) {
                    rotateLeft(node2);
                } else {
                    if (!$assertionsDisabled && rightDelta != 1) {
                        throw new AssertionError();
                    }
                    rotateRight(node4);
                    rotateLeft(node2);
                }
                if (insert) {
                    return;
                }
            } else if (delta == 2) {
                Node node7 = node3.left;
                Node node8 = node3.right;
                int leftRightHeight = node8 != null ? node8.height : 0;
                int leftLeftHeight = node7 != null ? node7.height : 0;
                int leftDelta = leftLeftHeight - leftRightHeight;
                if (leftDelta == 1 || (leftDelta == 0 && !insert)) {
                    rotateRight(node2);
                } else {
                    if (!$assertionsDisabled && leftDelta != -1) {
                        throw new AssertionError();
                    }
                    rotateLeft(node3);
                    rotateRight(node2);
                }
                if (insert) {
                    return;
                }
            } else if (delta == 0) {
                node2.height = leftHeight + 1;
                if (insert) {
                    return;
                }
            } else {
                if (!$assertionsDisabled && delta != -1 && delta != 1) {
                    throw new AssertionError();
                }
                node2.height = Math.max(leftHeight, rightHeight) + 1;
                if (!insert) {
                    return;
                }
            }
        }
    }

    private void rotateLeft(Node node) {
        Node node2 = node.left;
        Node node3 = node.right;
        Node node4 = node3.left;
        Node node5 = node3.right;
        node.right = node4;
        if (node4 != null) {
            node4.parent = node;
        }
        replaceInParent(node, node3);
        node3.left = node;
        node.parent = node3;
        node.height = Math.max(node2 != null ? node2.height : 0, node4 != null ? node4.height : 0) + 1;
        node3.height = Math.max(node.height, node5 != null ? node5.height : 0) + 1;
    }

    private void rotateRight(Node node) {
        Node node2 = node.left;
        Node node3 = node.right;
        Node node4 = node2.left;
        Node node5 = node2.right;
        node.left = node5;
        if (node5 != null) {
            node5.parent = node;
        }
        replaceInParent(node, node2);
        node2.right = node;
        node.parent = node2;
        node.height = Math.max(node3 != null ? node3.height : 0, node5 != null ? node5.height : 0) + 1;
        node2.height = Math.max(node.height, node4 != null ? node4.height : 0) + 1;
    }

    public Set entrySet() {
        LinkedHashTreeMap<K, V>.EntrySet result = this.entrySet;
        if (result != null) {
            return result;
        }
        LinkedHashTreeMap<K, V>.EntrySet result2 = new EntrySet();
        this.entrySet = result2;
        return result2;
    }

    public Set keySet() {
        LinkedHashTreeMap<K, V>.KeySet result = this.keySet;
        if (result != null) {
            return result;
        }
        LinkedHashTreeMap<K, V>.KeySet result2 = new KeySet();
        this.keySet = result2;
        return result2;
    }

    static final class Node implements Map.Entry {
        final int hash;
        int height;
        final Object key;
        Node left;
        Node next;
        Node parent;
        Node prev;
        Node right;
        Object value;

        Node() {
            this.key = null;
            this.hash = -1;
            this.prev = this;
            this.next = this;
        }

        Node(Node node, Object obj, int hash, Node node2, Node node3) {
            this.parent = node;
            this.key = obj;
            this.hash = hash;
            this.height = 1;
            this.next = node2;
            this.prev = node3;
            node3.next = this;
            node2.prev = this;
        }

        public Object getKey() {
            return this.key;
        }

        public Object getValue() {
            return this.value;
        }

        public Object setValue(Object obj) {
            Object obj2 = this.value;
            this.value = obj;
            return obj2;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry other = (Map.Entry) o;
            if (this.key == null) {
                if (other.getKey() != null) {
                    return false;
                }
            } else if (!this.key.equals(other.getKey())) {
                return false;
            }
            if (this.value == null) {
                if (other.getValue() != null) {
                    return false;
                }
            } else if (!this.value.equals(other.getValue())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value != null ? this.value.hashCode() : 0);
        }

        public String toString() {
            return this.key + "=" + this.value;
        }

        public Node first() {
            Node node = this;
            Node node2 = node.left;
            while (node2 != null) {
                node = node2;
                node2 = node.left;
            }
            return node;
        }

        public Node last() {
            Node node = this;
            Node node2 = node.right;
            while (node2 != null) {
                node = node2;
                node2 = node.right;
            }
            return node;
        }
    }

    private void doubleCapacity() {
        this.table = doubleCapacity(this.table);
        this.threshold = (this.table.length / 2) + (this.table.length / 4);
    }

    static Node[] doubleCapacity(Node[] nodeArr) {
        int oldCapacity = nodeArr.length;
        Node[] nodeArr2 = new Node[oldCapacity * 2];
        AvlIterator avlIterator = new AvlIterator();
        AvlBuilder avlBuilder = new AvlBuilder();
        AvlBuilder avlBuilder2 = new AvlBuilder();
        for (int i = 0; i < oldCapacity; i++) {
            Node node = nodeArr[i];
            if (node != null) {
                avlIterator.reset(node);
                int leftSize = 0;
                int rightSize = 0;
                while (true) {
                    Node next = avlIterator.next();
                    if (next == null) {
                        break;
                    }
                    if ((next.hash & oldCapacity) == 0) {
                        leftSize++;
                    } else {
                        rightSize++;
                    }
                }
                avlBuilder.reset(leftSize);
                avlBuilder2.reset(rightSize);
                avlIterator.reset(node);
                while (true) {
                    Node next2 = avlIterator.next();
                    if (next2 == null) {
                        break;
                    }
                    if ((next2.hash & oldCapacity) == 0) {
                        avlBuilder.add(next2);
                    } else {
                        avlBuilder2.add(next2);
                    }
                }
                nodeArr2[i] = leftSize > 0 ? avlBuilder.root() : null;
                nodeArr2[i + oldCapacity] = rightSize > 0 ? avlBuilder2.root() : null;
            }
        }
        return nodeArr2;
    }

    static class AvlIterator {
        private Node stackTop;

        AvlIterator() {
        }

        void reset(Node node) {
            Node node2 = null;
            for (Node node3 = node; node3 != null; node3 = node3.left) {
                node3.parent = node2;
                node2 = node3;
            }
            this.stackTop = node2;
        }

        public Node next() {
            Node node = this.stackTop;
            if (node == null) {
                return null;
            }
            Node node2 = node.parent;
            node.parent = null;
            for (Node node3 = node.right; node3 != null; node3 = node3.left) {
                node3.parent = node2;
                node2 = node3;
            }
            this.stackTop = node2;
            return node;
        }
    }

    static final class AvlBuilder {
        private int leavesSkipped;
        private int leavesToSkip;
        private int size;
        private Node stack;

        AvlBuilder() {
        }

        void reset(int targetSize) {
            int treeCapacity = (Integer.highestOneBit(targetSize) * 2) - 1;
            this.leavesToSkip = treeCapacity - targetSize;
            this.size = 0;
            this.leavesSkipped = 0;
            this.stack = null;
        }

        void add(Node node) {
            node.right = null;
            node.parent = null;
            node.left = null;
            node.height = 1;
            if (this.leavesToSkip > 0 && (this.size & 1) == 0) {
                this.size++;
                this.leavesToSkip--;
                this.leavesSkipped++;
            }
            node.parent = this.stack;
            this.stack = node;
            this.size++;
            if (this.leavesToSkip > 0 && (this.size & 1) == 0) {
                this.size++;
                this.leavesToSkip--;
                this.leavesSkipped++;
            }
            for (int scale = 4; (this.size & (scale - 1)) == scale - 1; scale *= 2) {
                if (this.leavesSkipped == 0) {
                    Node node2 = this.stack;
                    Node node3 = node2.parent;
                    Node node4 = node3.parent;
                    node3.parent = node4.parent;
                    this.stack = node3;
                    node3.left = node4;
                    node3.right = node2;
                    node3.height = node2.height + 1;
                    node4.parent = node3;
                    node2.parent = node3;
                } else if (this.leavesSkipped == 1) {
                    Node node5 = this.stack;
                    Node node6 = node5.parent;
                    this.stack = node6;
                    node6.right = node5;
                    node6.height = node5.height + 1;
                    node5.parent = node6;
                    this.leavesSkipped = 0;
                } else if (this.leavesSkipped == 2) {
                    this.leavesSkipped = 0;
                }
            }
        }

        Node root() {
            Node node = this.stack;
            if (node.parent != null) {
                throw new IllegalStateException();
            }
            return node;
        }
    }

    private abstract class LinkedTreeMapIterator implements Iterator {
        int expectedModCount;
        Node lastReturned = null;
        Node next;

        LinkedTreeMapIterator() {
            this.next = LinkedHashTreeMap.this.header.next;
            this.expectedModCount = LinkedHashTreeMap.this.modCount;
        }

        public final boolean hasNext() {
            return this.next != LinkedHashTreeMap.this.header;
        }

        final Node nextNode() {
            Node node = this.next;
            if (node == LinkedHashTreeMap.this.header) {
                throw new NoSuchElementException();
            }
            if (LinkedHashTreeMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            this.next = node.next;
            this.lastReturned = node;
            return node;
        }

        public final void remove() {
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            }
            LinkedHashTreeMap.this.removeInternal(this.lastReturned, true);
            this.lastReturned = null;
            this.expectedModCount = LinkedHashTreeMap.this.modCount;
        }
    }

    final class EntrySet extends AbstractSet {
        EntrySet() {
        }

        public int size() {
            return LinkedHashTreeMap.this.size;
        }

        class 1 extends LinkedTreeMapIterator {
            1() {
                super();
            }

            public Map.Entry next() {
                return nextNode();
            }
        }

        public Iterator iterator() {
            return new 1();
        }

        public boolean contains(Object o) {
            return (o instanceof Map.Entry) && LinkedHashTreeMap.this.findByEntry((Map.Entry) o) != null;
        }

        public boolean remove(Object o) {
            Node findByEntry;
            if (!(o instanceof Map.Entry) || (findByEntry = LinkedHashTreeMap.this.findByEntry((Map.Entry) o)) == null) {
                return false;
            }
            LinkedHashTreeMap.this.removeInternal(findByEntry, true);
            return true;
        }

        public void clear() {
            LinkedHashTreeMap.this.clear();
        }
    }

    final class KeySet extends AbstractSet {
        KeySet() {
        }

        public int size() {
            return LinkedHashTreeMap.this.size;
        }

        class 1 extends LinkedTreeMapIterator {
            1() {
                super();
            }

            public Object next() {
                return nextNode().key;
            }
        }

        public Iterator iterator() {
            return new 1();
        }

        public boolean contains(Object o) {
            return LinkedHashTreeMap.this.containsKey(o);
        }

        public boolean remove(Object key) {
            return LinkedHashTreeMap.this.removeInternalByKey(key) != null;
        }

        public void clear() {
            LinkedHashTreeMap.this.clear();
        }
    }

    private Object writeReplace() throws ObjectStreamException {
        return new LinkedHashMap(this);
    }
}
