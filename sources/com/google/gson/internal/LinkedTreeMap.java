package com.google.gson.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public final class LinkedTreeMap extends AbstractMap implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Comparator NATURAL_ORDER;
    Comparator comparator;
    private EntrySet entrySet;
    final Node header;
    private KeySet keySet;
    int modCount;
    Node root;
    int size;

    static {
        $assertionsDisabled = !LinkedTreeMap.class.desiredAssertionStatus();
        NATURAL_ORDER = new 1();
    }

    class 1 implements Comparator {
        1() {
        }

        public int compare(Comparable a, Comparable b) {
            return a.compareTo(b);
        }
    }

    public LinkedTreeMap() {
        this(NATURAL_ORDER);
    }

    public LinkedTreeMap(Comparator comparator) {
        this.size = 0;
        this.modCount = 0;
        this.header = new Node();
        this.comparator = comparator == null ? NATURAL_ORDER : comparator;
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
        this.root = null;
        this.size = 0;
        this.modCount++;
        Node node = this.header;
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
        Comparator comparator = this.comparator;
        Node node2 = this.root;
        int comparison = 0;
        if (node2 != null) {
            Comparable<Object> comparableKey = comparator == NATURAL_ORDER ? (Comparable) obj : null;
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
            node = new Node(node2, obj, node5, node5.prev);
            this.root = node;
        } else {
            node = new Node(node2, obj, node5, node5.prev);
            if (comparison < 0) {
                node2.left = node;
            } else {
                node2.right = node;
            }
            rebalance(node2, true);
        }
        this.size++;
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

    void removeInternal(Node node, boolean unlink) {
        if (unlink) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
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
        this.root = node2;
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
        LinkedTreeMap<K, V>.EntrySet result = this.entrySet;
        if (result != null) {
            return result;
        }
        LinkedTreeMap<K, V>.EntrySet result2 = new EntrySet();
        this.entrySet = result2;
        return result2;
    }

    public Set keySet() {
        LinkedTreeMap<K, V>.KeySet result = this.keySet;
        if (result != null) {
            return result;
        }
        LinkedTreeMap<K, V>.KeySet result2 = new KeySet();
        this.keySet = result2;
        return result2;
    }

    static final class Node implements Map.Entry {
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
            this.prev = this;
            this.next = this;
        }

        Node(Node node, Object obj, Node node2, Node node3) {
            this.parent = node;
            this.key = obj;
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

    private abstract class LinkedTreeMapIterator implements Iterator {
        int expectedModCount;
        Node lastReturned = null;
        Node next;

        LinkedTreeMapIterator() {
            this.next = LinkedTreeMap.this.header.next;
            this.expectedModCount = LinkedTreeMap.this.modCount;
        }

        public final boolean hasNext() {
            return this.next != LinkedTreeMap.this.header;
        }

        final Node nextNode() {
            Node node = this.next;
            if (node == LinkedTreeMap.this.header) {
                throw new NoSuchElementException();
            }
            if (LinkedTreeMap.this.modCount != this.expectedModCount) {
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
            LinkedTreeMap.this.removeInternal(this.lastReturned, true);
            this.lastReturned = null;
            this.expectedModCount = LinkedTreeMap.this.modCount;
        }
    }

    class EntrySet extends AbstractSet {
        EntrySet() {
        }

        public int size() {
            return LinkedTreeMap.this.size;
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
            return (o instanceof Map.Entry) && LinkedTreeMap.this.findByEntry((Map.Entry) o) != null;
        }

        public boolean remove(Object o) {
            Node findByEntry;
            if (!(o instanceof Map.Entry) || (findByEntry = LinkedTreeMap.this.findByEntry((Map.Entry) o)) == null) {
                return false;
            }
            LinkedTreeMap.this.removeInternal(findByEntry, true);
            return true;
        }

        public void clear() {
            LinkedTreeMap.this.clear();
        }
    }

    final class KeySet extends AbstractSet {
        KeySet() {
        }

        public int size() {
            return LinkedTreeMap.this.size;
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
            return LinkedTreeMap.this.containsKey(o);
        }

        public boolean remove(Object key) {
            return LinkedTreeMap.this.removeInternalByKey(key) != null;
        }

        public void clear() {
            LinkedTreeMap.this.clear();
        }
    }

    private Object writeReplace() throws ObjectStreamException {
        return new LinkedHashMap(this);
    }
}
