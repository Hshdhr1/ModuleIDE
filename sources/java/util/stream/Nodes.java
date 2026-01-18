package java.util.stream;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.CountedCompleter;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.stream.Node;
import java.util.stream.Sink;
import java.util.stream.SpinedBuffer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class Nodes {
    static final String BAD_SIZE = "Stream size exceeds max array size";
    static final long MAX_ARRAY_SIZE = 2147483639;
    private static final Node EMPTY_NODE = new EmptyNode.OfRef(null);
    private static final Node.OfInt EMPTY_INT_NODE = new EmptyNode.OfInt();
    private static final Node.OfLong EMPTY_LONG_NODE = new EmptyNode.OfLong();
    private static final Node.OfDouble EMPTY_DOUBLE_NODE = new EmptyNode.OfDouble();
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final long[] EMPTY_LONG_ARRAY = new long[0];
    private static final double[] EMPTY_DOUBLE_ARRAY = new double[0];

    static /* bridge */ /* synthetic */ double[] -$$Nest$sfgetEMPTY_DOUBLE_ARRAY() {
        return EMPTY_DOUBLE_ARRAY;
    }

    static /* bridge */ /* synthetic */ int[] -$$Nest$sfgetEMPTY_INT_ARRAY() {
        return EMPTY_INT_ARRAY;
    }

    static /* bridge */ /* synthetic */ long[] -$$Nest$sfgetEMPTY_LONG_ARRAY() {
        return EMPTY_LONG_ARRAY;
    }

    private Nodes() {
        throw new Error("no instances");
    }

    static IntFunction castingArray() {
        return new Nodes$$ExternalSyntheticLambda0();
    }

    static /* synthetic */ Object[] lambda$castingArray$0(int i) {
        return new Object[i];
    }

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$java$util$stream$StreamShape;

        static {
            int[] iArr = new int[StreamShape.values().length];
            $SwitchMap$java$util$stream$StreamShape = iArr;
            try {
                iArr[StreamShape.REFERENCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$util$stream$StreamShape[StreamShape.INT_VALUE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$util$stream$StreamShape[StreamShape.LONG_VALUE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$util$stream$StreamShape[StreamShape.DOUBLE_VALUE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    static Node emptyNode(StreamShape streamShape) {
        int i = 1.$SwitchMap$java$util$stream$StreamShape[streamShape.ordinal()];
        if (i == 1) {
            return EMPTY_NODE;
        }
        if (i == 2) {
            return EMPTY_INT_NODE;
        }
        if (i == 3) {
            return EMPTY_LONG_NODE;
        }
        if (i == 4) {
            return EMPTY_DOUBLE_NODE;
        }
        throw new IllegalStateException("Unknown shape " + streamShape);
    }

    static Node conc(StreamShape streamShape, Node node, Node node2) {
        int i = 1.$SwitchMap$java$util$stream$StreamShape[streamShape.ordinal()];
        if (i == 1) {
            return new ConcNode(node, node2);
        }
        if (i == 2) {
            return new ConcNode.OfInt((Node.OfInt) node, (Node.OfInt) node2);
        }
        if (i == 3) {
            return new ConcNode.OfLong((Node.OfLong) node, (Node.OfLong) node2);
        }
        if (i == 4) {
            return new ConcNode.OfDouble((Node.OfDouble) node, (Node.OfDouble) node2);
        }
        throw new IllegalStateException("Unknown shape " + streamShape);
    }

    static Node node(Object[] objArr) {
        return new ArrayNode(objArr);
    }

    static Node node(Collection collection) {
        return new CollectionNode(collection);
    }

    static Node.Builder builder(long j, IntFunction intFunction) {
        if (j >= 0 && j < 2147483639) {
            return new FixedNodeBuilder(j, intFunction);
        }
        return builder();
    }

    static Node.Builder builder() {
        return new SpinedNodeBuilder();
    }

    static Node.OfInt node(int[] iArr) {
        return new IntArrayNode(iArr);
    }

    static Node.Builder.OfInt intBuilder(long j) {
        if (j >= 0 && j < 2147483639) {
            return new IntFixedNodeBuilder(j);
        }
        return intBuilder();
    }

    static Node.Builder.OfInt intBuilder() {
        return new IntSpinedNodeBuilder();
    }

    static Node.OfLong node(long[] jArr) {
        return new LongArrayNode(jArr);
    }

    static Node.Builder.OfLong longBuilder(long j) {
        if (j >= 0 && j < 2147483639) {
            return new LongFixedNodeBuilder(j);
        }
        return longBuilder();
    }

    static Node.Builder.OfLong longBuilder() {
        return new LongSpinedNodeBuilder();
    }

    static Node.OfDouble node(double[] dArr) {
        return new DoubleArrayNode(dArr);
    }

    static Node.Builder.OfDouble doubleBuilder(long j) {
        if (j >= 0 && j < 2147483639) {
            return new DoubleFixedNodeBuilder(j);
        }
        return doubleBuilder();
    }

    static Node.Builder.OfDouble doubleBuilder() {
        return new DoubleSpinedNodeBuilder();
    }

    public static Node collect(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z, IntFunction intFunction) {
        long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
        if (exactOutputSizeIfKnown < 0 || !spliterator.hasCharacteristics(16384)) {
            Node node = (Node) new CollectorTask.OfRef(pipelineHelper, intFunction, spliterator).invoke();
            return z ? flatten(node, intFunction) : node;
        }
        if (exactOutputSizeIfKnown >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object[] objArr = (Object[]) intFunction.apply((int) exactOutputSizeIfKnown);
        new SizedCollectorTask.OfRef(spliterator, pipelineHelper, objArr).invoke();
        return node(objArr);
    }

    public static Node.OfInt collectInt(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z) {
        long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
        if (exactOutputSizeIfKnown < 0 || !spliterator.hasCharacteristics(16384)) {
            Node.OfInt ofInt = (Node.OfInt) new CollectorTask.OfInt(pipelineHelper, spliterator).invoke();
            return z ? flattenInt(ofInt) : ofInt;
        }
        if (exactOutputSizeIfKnown >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        int[] iArr = new int[(int) exactOutputSizeIfKnown];
        new SizedCollectorTask.OfInt(spliterator, pipelineHelper, iArr).invoke();
        return node(iArr);
    }

    public static Node.OfLong collectLong(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z) {
        long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
        if (exactOutputSizeIfKnown < 0 || !spliterator.hasCharacteristics(16384)) {
            Node.OfLong ofLong = (Node.OfLong) new CollectorTask.OfLong(pipelineHelper, spliterator).invoke();
            return z ? flattenLong(ofLong) : ofLong;
        }
        if (exactOutputSizeIfKnown >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        long[] jArr = new long[(int) exactOutputSizeIfKnown];
        new SizedCollectorTask.OfLong(spliterator, pipelineHelper, jArr).invoke();
        return node(jArr);
    }

    public static Node.OfDouble collectDouble(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z) {
        long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
        if (exactOutputSizeIfKnown < 0 || !spliterator.hasCharacteristics(16384)) {
            Node.OfDouble ofDouble = (Node.OfDouble) new CollectorTask.OfDouble(pipelineHelper, spliterator).invoke();
            return z ? flattenDouble(ofDouble) : ofDouble;
        }
        if (exactOutputSizeIfKnown >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        double[] dArr = new double[(int) exactOutputSizeIfKnown];
        new SizedCollectorTask.OfDouble(spliterator, pipelineHelper, dArr).invoke();
        return node(dArr);
    }

    public static Node flatten(Node node, IntFunction intFunction) {
        if (node.getChildCount() <= 0) {
            return node;
        }
        long count = node.count();
        if (count >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object[] objArr = (Object[]) intFunction.apply((int) count);
        new ToArrayTask.OfRef(node, objArr, 0, null).invoke();
        return node(objArr);
    }

    public static Node.OfInt flattenInt(Node.OfInt ofInt) {
        if (ofInt.getChildCount() <= 0) {
            return ofInt;
        }
        long count = ofInt.count();
        if (count >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        int[] iArr = new int[(int) count];
        new ToArrayTask.OfInt(ofInt, iArr, 0, null).invoke();
        return node(iArr);
    }

    public static Node.OfLong flattenLong(Node.OfLong ofLong) {
        if (ofLong.getChildCount() <= 0) {
            return ofLong;
        }
        long count = ofLong.count();
        if (count >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        long[] jArr = new long[(int) count];
        new ToArrayTask.OfLong(ofLong, jArr, 0, null).invoke();
        return node(jArr);
    }

    public static Node.OfDouble flattenDouble(Node.OfDouble ofDouble) {
        if (ofDouble.getChildCount() <= 0) {
            return ofDouble;
        }
        long count = ofDouble.count();
        if (count >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        double[] dArr = new double[(int) count];
        new ToArrayTask.OfDouble(ofDouble, dArr, 0, null).invoke();
        return node(dArr);
    }

    private static abstract class EmptyNode implements Node {
        public void copyInto(Object obj, int i) {
        }

        public long count() {
            return 0L;
        }

        public void forEach(Object obj) {
        }

        public /* synthetic */ Node getChild(int i) {
            return Node.-CC.$default$getChild(this, i);
        }

        public /* synthetic */ int getChildCount() {
            return Node.-CC.$default$getChildCount(this);
        }

        public /* synthetic */ StreamShape getShape() {
            return Node.-CC.$default$getShape(this);
        }

        public /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            return Node.-CC.$default$truncate(this, j, j2, intFunction);
        }

        EmptyNode() {
        }

        public Object[] asArray(IntFunction intFunction) {
            return (Object[]) intFunction.apply(0);
        }

        private static class OfRef extends EmptyNode {
            /* synthetic */ OfRef(Nodes-IA r1) {
                this();
            }

            public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
                super.copyInto((Object) objArr, i);
            }

            public /* bridge */ /* synthetic */ void forEach(Consumer consumer) {
                super.forEach((Object) consumer);
            }

            private OfRef() {
            }

            public Spliterator spliterator() {
                return Spliterators.emptySpliterator();
            }
        }

        private static final class OfInt extends EmptyNode implements Node.OfInt {
            public /* synthetic */ void copyInto(Integer[] numArr, int i) {
                Node.OfInt.-CC.$default$copyInto((Node.OfInt) this, numArr, i);
            }

            public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
                Node.OfInt.-CC.$default$copyInto(this, objArr, i);
            }

            public /* synthetic */ void forEach(Consumer consumer) {
                Node.OfInt.-CC.$default$forEach(this, consumer);
            }

            public /* synthetic */ Node.OfPrimitive getChild(int i) {
                return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
            }

            public /* bridge */ /* synthetic */ Node getChild(int i) {
                return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
            }

            public /* synthetic */ StreamShape getShape() {
                return Node.OfInt.-CC.$default$getShape(this);
            }

            public /* bridge */ /* synthetic */ Object newArray(int i) {
                return Node.OfInt.-CC.$default$newArray((Node.OfInt) this, i);
            }

            public /* synthetic */ int[] newArray(int i) {
                return Node.OfInt.-CC.$default$newArray((Node.OfInt) this, i);
            }

            public /* synthetic */ Node.OfInt truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfInt.-CC.$default$truncate((Node.OfInt) this, j, j2, intFunction);
            }

            public /* bridge */ /* synthetic */ Node.OfPrimitive truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfInt.-CC.$default$truncate((Node.OfInt) this, j, j2, intFunction);
            }

            public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfInt.-CC.$default$truncate((Node.OfInt) this, j, j2, intFunction);
            }

            OfInt() {
            }

            public Spliterator.OfInt spliterator() {
                return Spliterators.emptyIntSpliterator();
            }

            public int[] asPrimitiveArray() {
                return Nodes.-$$Nest$sfgetEMPTY_INT_ARRAY();
            }
        }

        private static final class OfLong extends EmptyNode implements Node.OfLong {
            public /* synthetic */ void copyInto(Long[] lArr, int i) {
                Node.OfLong.-CC.$default$copyInto((Node.OfLong) this, lArr, i);
            }

            public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
                Node.OfLong.-CC.$default$copyInto(this, objArr, i);
            }

            public /* synthetic */ void forEach(Consumer consumer) {
                Node.OfLong.-CC.$default$forEach(this, consumer);
            }

            public /* synthetic */ Node.OfPrimitive getChild(int i) {
                return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
            }

            public /* bridge */ /* synthetic */ Node getChild(int i) {
                return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
            }

            public /* synthetic */ StreamShape getShape() {
                return Node.OfLong.-CC.$default$getShape(this);
            }

            public /* bridge */ /* synthetic */ Object newArray(int i) {
                return Node.OfLong.-CC.$default$newArray((Node.OfLong) this, i);
            }

            public /* synthetic */ long[] newArray(int i) {
                return Node.OfLong.-CC.$default$newArray((Node.OfLong) this, i);
            }

            public /* synthetic */ Node.OfLong truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfLong.-CC.$default$truncate((Node.OfLong) this, j, j2, intFunction);
            }

            public /* bridge */ /* synthetic */ Node.OfPrimitive truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfLong.-CC.$default$truncate((Node.OfLong) this, j, j2, intFunction);
            }

            public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfLong.-CC.$default$truncate((Node.OfLong) this, j, j2, intFunction);
            }

            OfLong() {
            }

            public Spliterator.OfLong spliterator() {
                return Spliterators.emptyLongSpliterator();
            }

            public long[] asPrimitiveArray() {
                return Nodes.-$$Nest$sfgetEMPTY_LONG_ARRAY();
            }
        }

        private static final class OfDouble extends EmptyNode implements Node.OfDouble {
            public /* synthetic */ void copyInto(Double[] dArr, int i) {
                Node.OfDouble.-CC.$default$copyInto((Node.OfDouble) this, dArr, i);
            }

            public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
                Node.OfDouble.-CC.$default$copyInto(this, objArr, i);
            }

            public /* synthetic */ void forEach(Consumer consumer) {
                Node.OfDouble.-CC.$default$forEach(this, consumer);
            }

            public /* synthetic */ Node.OfPrimitive getChild(int i) {
                return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
            }

            public /* bridge */ /* synthetic */ Node getChild(int i) {
                return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
            }

            public /* synthetic */ StreamShape getShape() {
                return Node.OfDouble.-CC.$default$getShape(this);
            }

            public /* bridge */ /* synthetic */ Object newArray(int i) {
                return Node.OfDouble.-CC.$default$newArray((Node.OfDouble) this, i);
            }

            public /* synthetic */ double[] newArray(int i) {
                return Node.OfDouble.-CC.$default$newArray((Node.OfDouble) this, i);
            }

            public /* synthetic */ Node.OfDouble truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfDouble.-CC.$default$truncate((Node.OfDouble) this, j, j2, intFunction);
            }

            public /* bridge */ /* synthetic */ Node.OfPrimitive truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfDouble.-CC.$default$truncate((Node.OfDouble) this, j, j2, intFunction);
            }

            public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfDouble.-CC.$default$truncate((Node.OfDouble) this, j, j2, intFunction);
            }

            OfDouble() {
            }

            public Spliterator.OfDouble spliterator() {
                return Spliterators.emptyDoubleSpliterator();
            }

            public double[] asPrimitiveArray() {
                return Nodes.-$$Nest$sfgetEMPTY_DOUBLE_ARRAY();
            }
        }
    }

    private static class ArrayNode implements Node {
        final Object[] array;
        int curSize;

        public /* synthetic */ Node getChild(int i) {
            return Node.-CC.$default$getChild(this, i);
        }

        public /* synthetic */ int getChildCount() {
            return Node.-CC.$default$getChildCount(this);
        }

        public /* synthetic */ StreamShape getShape() {
            return Node.-CC.$default$getShape(this);
        }

        public /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            return Node.-CC.$default$truncate(this, j, j2, intFunction);
        }

        ArrayNode(long j, IntFunction intFunction) {
            if (j >= 2147483639) {
                throw new IllegalArgumentException("Stream size exceeds max array size");
            }
            this.array = (Object[]) intFunction.apply((int) j);
            this.curSize = 0;
        }

        ArrayNode(Object[] objArr) {
            this.array = objArr;
            this.curSize = objArr.length;
        }

        public Spliterator spliterator() {
            return Arrays.spliterator(this.array, 0, this.curSize);
        }

        public void copyInto(Object[] objArr, int i) {
            System.arraycopy(this.array, 0, objArr, i, this.curSize);
        }

        public Object[] asArray(IntFunction intFunction) {
            Object[] objArr = this.array;
            if (objArr.length == this.curSize) {
                return objArr;
            }
            throw new IllegalStateException();
        }

        public long count() {
            return this.curSize;
        }

        public void forEach(Consumer consumer) {
            for (int i = 0; i < this.curSize; i++) {
                consumer.accept(this.array[i]);
            }
        }

        public String toString() {
            return String.format("ArrayNode[%d][%s]", Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array));
        }
    }

    private static final class CollectionNode implements Node {
        private final Collection c;

        public /* synthetic */ Node getChild(int i) {
            return Node.-CC.$default$getChild(this, i);
        }

        public /* synthetic */ int getChildCount() {
            return Node.-CC.$default$getChildCount(this);
        }

        public /* synthetic */ StreamShape getShape() {
            return Node.-CC.$default$getShape(this);
        }

        public /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            return Node.-CC.$default$truncate(this, j, j2, intFunction);
        }

        CollectionNode(Collection collection) {
            this.c = collection;
        }

        public Spliterator spliterator() {
            return this.c.stream().spliterator();
        }

        public void copyInto(Object[] objArr, int i) {
            Iterator it = this.c.iterator();
            while (it.hasNext()) {
                objArr[i] = it.next();
                i++;
            }
        }

        public Object[] asArray(IntFunction intFunction) {
            Collection collection = this.c;
            return collection.toArray((Object[]) intFunction.apply(collection.size()));
        }

        public long count() {
            return this.c.size();
        }

        public void forEach(Consumer consumer) {
            this.c.forEach(consumer);
        }

        public String toString() {
            return String.format("CollectionNode[%d][%s]", Integer.valueOf(this.c.size()), this.c);
        }
    }

    private static abstract class AbstractConcNode implements Node {
        protected final Node left;
        protected final Node right;
        private final long size;

        public int getChildCount() {
            return 2;
        }

        public /* synthetic */ StreamShape getShape() {
            return Node.-CC.$default$getShape(this);
        }

        public /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            return Node.-CC.$default$truncate(this, j, j2, intFunction);
        }

        AbstractConcNode(Node node, Node node2) {
            this.left = node;
            this.right = node2;
            this.size = node.count() + node2.count();
        }

        public Node getChild(int i) {
            if (i == 0) {
                return this.left;
            }
            if (i == 1) {
                return this.right;
            }
            throw new IndexOutOfBoundsException();
        }

        public long count() {
            return this.size;
        }
    }

    static final class ConcNode extends AbstractConcNode implements Node {
        ConcNode(Node node, Node node2) {
            super(node, node2);
        }

        public Spliterator spliterator() {
            return new InternalNodeSpliterator.OfRef(this);
        }

        public void copyInto(Object[] objArr, int i) {
            objArr.getClass();
            this.left.copyInto(objArr, i);
            this.right.copyInto(objArr, i + ((int) this.left.count()));
        }

        public Object[] asArray(IntFunction intFunction) {
            long count = count();
            if (count >= 2147483639) {
                throw new IllegalArgumentException("Stream size exceeds max array size");
            }
            Object[] objArr = (Object[]) intFunction.apply((int) count);
            copyInto(objArr, 0);
            return objArr;
        }

        public void forEach(Consumer consumer) {
            this.left.forEach(consumer);
            this.right.forEach(consumer);
        }

        public Node truncate(long j, long j2, IntFunction intFunction) {
            if (j == 0 && j2 == count()) {
                return this;
            }
            long count = this.left.count();
            if (j >= count) {
                return this.right.truncate(j - count, j2 - count, intFunction);
            }
            if (j2 <= count) {
                return this.left.truncate(j, j2, intFunction);
            }
            return Nodes.conc(getShape(), this.left.truncate(j, count, intFunction), this.right.truncate(0L, j2 - count, intFunction));
        }

        public String toString() {
            return count() < 32 ? String.format("ConcNode[%s.%s]", this.left, this.right) : String.format("ConcNode[size=%d]", Long.valueOf(count()));
        }

        private static abstract class OfPrimitive extends AbstractConcNode implements Node.OfPrimitive {
            public /* synthetic */ Object[] asArray(IntFunction intFunction) {
                return Node.OfPrimitive.-CC.$default$asArray(this, intFunction);
            }

            public /* bridge */ /* synthetic */ Spliterator spliterator() {
                return Node.OfPrimitive.-CC.$default$spliterator(this);
            }

            public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfPrimitive.-CC.$default$truncate(this, j, j2, intFunction);
            }

            public /* bridge */ /* synthetic */ Node.OfPrimitive getChild(int i) {
                return (Node.OfPrimitive) super.getChild(i);
            }

            OfPrimitive(Node.OfPrimitive ofPrimitive, Node.OfPrimitive ofPrimitive2) {
                super(ofPrimitive, ofPrimitive2);
            }

            public void forEach(Object obj) {
                ((Node.OfPrimitive) this.left).forEach(obj);
                ((Node.OfPrimitive) this.right).forEach(obj);
            }

            public void copyInto(Object obj, int i) {
                ((Node.OfPrimitive) this.left).copyInto(obj, i);
                ((Node.OfPrimitive) this.right).copyInto(obj, i + ((int) ((Node.OfPrimitive) this.left).count()));
            }

            public Object asPrimitiveArray() {
                long count = count();
                if (count >= 2147483639) {
                    throw new IllegalArgumentException("Stream size exceeds max array size");
                }
                Object newArray = newArray((int) count);
                copyInto(newArray, 0);
                return newArray;
            }

            public String toString() {
                return count() < 32 ? String.format("%s[%s.%s]", getClass().getName(), this.left, this.right) : String.format("%s[size=%d]", getClass().getName(), Long.valueOf(count()));
            }
        }

        static final class OfInt extends OfPrimitive implements Node.OfInt {
            public /* synthetic */ void copyInto(Integer[] numArr, int i) {
                Node.OfInt.-CC.$default$copyInto((Node.OfInt) this, numArr, i);
            }

            public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
                Node.OfInt.-CC.$default$copyInto(this, objArr, i);
            }

            public /* synthetic */ void forEach(Consumer consumer) {
                Node.OfInt.-CC.$default$forEach(this, consumer);
            }

            public /* synthetic */ StreamShape getShape() {
                return Node.OfInt.-CC.$default$getShape(this);
            }

            public /* bridge */ /* synthetic */ Object newArray(int i) {
                return Node.OfInt.-CC.$default$newArray((Node.OfInt) this, i);
            }

            public /* synthetic */ int[] newArray(int i) {
                return Node.OfInt.-CC.$default$newArray((Node.OfInt) this, i);
            }

            public /* synthetic */ Node.OfInt truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfInt.-CC.$default$truncate((Node.OfInt) this, j, j2, intFunction);
            }

            public /* bridge */ /* synthetic */ Node.OfPrimitive truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfInt.-CC.$default$truncate((Node.OfInt) this, j, j2, intFunction);
            }

            public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfInt.-CC.$default$truncate((Node.OfInt) this, j, j2, intFunction);
            }

            OfInt(Node.OfInt ofInt, Node.OfInt ofInt2) {
                super(ofInt, ofInt2);
            }

            public Spliterator.OfInt spliterator() {
                return new InternalNodeSpliterator.OfInt(this);
            }
        }

        static final class OfLong extends OfPrimitive implements Node.OfLong {
            public /* synthetic */ void copyInto(Long[] lArr, int i) {
                Node.OfLong.-CC.$default$copyInto((Node.OfLong) this, lArr, i);
            }

            public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
                Node.OfLong.-CC.$default$copyInto(this, objArr, i);
            }

            public /* synthetic */ void forEach(Consumer consumer) {
                Node.OfLong.-CC.$default$forEach(this, consumer);
            }

            public /* synthetic */ StreamShape getShape() {
                return Node.OfLong.-CC.$default$getShape(this);
            }

            public /* bridge */ /* synthetic */ Object newArray(int i) {
                return Node.OfLong.-CC.$default$newArray((Node.OfLong) this, i);
            }

            public /* synthetic */ long[] newArray(int i) {
                return Node.OfLong.-CC.$default$newArray((Node.OfLong) this, i);
            }

            public /* synthetic */ Node.OfLong truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfLong.-CC.$default$truncate((Node.OfLong) this, j, j2, intFunction);
            }

            public /* bridge */ /* synthetic */ Node.OfPrimitive truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfLong.-CC.$default$truncate((Node.OfLong) this, j, j2, intFunction);
            }

            public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfLong.-CC.$default$truncate((Node.OfLong) this, j, j2, intFunction);
            }

            OfLong(Node.OfLong ofLong, Node.OfLong ofLong2) {
                super(ofLong, ofLong2);
            }

            public Spliterator.OfLong spliterator() {
                return new InternalNodeSpliterator.OfLong(this);
            }
        }

        static final class OfDouble extends OfPrimitive implements Node.OfDouble {
            public /* synthetic */ void copyInto(Double[] dArr, int i) {
                Node.OfDouble.-CC.$default$copyInto((Node.OfDouble) this, dArr, i);
            }

            public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
                Node.OfDouble.-CC.$default$copyInto(this, objArr, i);
            }

            public /* synthetic */ void forEach(Consumer consumer) {
                Node.OfDouble.-CC.$default$forEach(this, consumer);
            }

            public /* synthetic */ StreamShape getShape() {
                return Node.OfDouble.-CC.$default$getShape(this);
            }

            public /* bridge */ /* synthetic */ Object newArray(int i) {
                return Node.OfDouble.-CC.$default$newArray((Node.OfDouble) this, i);
            }

            public /* synthetic */ double[] newArray(int i) {
                return Node.OfDouble.-CC.$default$newArray((Node.OfDouble) this, i);
            }

            public /* synthetic */ Node.OfDouble truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfDouble.-CC.$default$truncate((Node.OfDouble) this, j, j2, intFunction);
            }

            public /* bridge */ /* synthetic */ Node.OfPrimitive truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfDouble.-CC.$default$truncate((Node.OfDouble) this, j, j2, intFunction);
            }

            public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfDouble.-CC.$default$truncate((Node.OfDouble) this, j, j2, intFunction);
            }

            OfDouble(Node.OfDouble ofDouble, Node.OfDouble ofDouble2) {
                super(ofDouble, ofDouble2);
            }

            public Spliterator.OfDouble spliterator() {
                return new InternalNodeSpliterator.OfDouble(this);
            }
        }
    }

    private static abstract class InternalNodeSpliterator implements Spliterator {
        int curChildIndex;
        Node curNode;
        Spliterator lastNodeSpliterator;
        Spliterator tryAdvanceSpliterator;
        Deque tryAdvanceStack;

        public final int characteristics() {
            return 64;
        }

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.-CC.$default$forEachRemaining(this, consumer);
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

        InternalNodeSpliterator(Node node) {
            this.curNode = node;
        }

        protected final Deque initStack() {
            ArrayDeque arrayDeque = new ArrayDeque(8);
            int childCount = this.curNode.getChildCount();
            while (true) {
                childCount--;
                if (childCount < this.curChildIndex) {
                    return arrayDeque;
                }
                arrayDeque.addFirst(this.curNode.getChild(childCount));
            }
        }

        protected final Node findNextLeafNode(Deque deque) {
            while (true) {
                Node node = (Node) deque.pollFirst();
                if (node == null) {
                    return null;
                }
                if (node.getChildCount() != 0) {
                    for (int childCount = node.getChildCount() - 1; childCount >= 0; childCount--) {
                        deque.addFirst(node.getChild(childCount));
                    }
                } else if (node.count() > 0) {
                    return node;
                }
            }
        }

        protected final boolean initTryAdvance() {
            if (this.curNode == null) {
                return false;
            }
            if (this.tryAdvanceSpliterator != null) {
                return true;
            }
            Spliterator spliterator = this.lastNodeSpliterator;
            if (spliterator == null) {
                Deque initStack = initStack();
                this.tryAdvanceStack = initStack;
                Node findNextLeafNode = findNextLeafNode(initStack);
                if (findNextLeafNode != null) {
                    this.tryAdvanceSpliterator = findNextLeafNode.spliterator();
                    return true;
                }
                this.curNode = null;
                return false;
            }
            this.tryAdvanceSpliterator = spliterator;
            return true;
        }

        public final Spliterator trySplit() {
            Node node = this.curNode;
            if (node == null || this.tryAdvanceSpliterator != null) {
                return null;
            }
            Spliterator spliterator = this.lastNodeSpliterator;
            if (spliterator != null) {
                return spliterator.trySplit();
            }
            if (this.curChildIndex < node.getChildCount() - 1) {
                Node node2 = this.curNode;
                int i = this.curChildIndex;
                this.curChildIndex = i + 1;
                return node2.getChild(i).spliterator();
            }
            Node child = this.curNode.getChild(this.curChildIndex);
            this.curNode = child;
            if (child.getChildCount() == 0) {
                Spliterator spliterator2 = this.curNode.spliterator();
                this.lastNodeSpliterator = spliterator2;
                return spliterator2.trySplit();
            }
            Node node3 = this.curNode;
            this.curChildIndex = 1;
            return node3.getChild(0).spliterator();
        }

        public final long estimateSize() {
            long j = 0;
            if (this.curNode == null) {
                return 0L;
            }
            Spliterator spliterator = this.lastNodeSpliterator;
            if (spliterator != null) {
                return spliterator.estimateSize();
            }
            for (int i = this.curChildIndex; i < this.curNode.getChildCount(); i++) {
                j += this.curNode.getChild(i).count();
            }
            return j;
        }

        private static final class OfRef extends InternalNodeSpliterator {
            OfRef(Node node) {
                super(node);
            }

            public boolean tryAdvance(Consumer consumer) {
                Node findNextLeafNode;
                if (!initTryAdvance()) {
                    return false;
                }
                boolean tryAdvance = this.tryAdvanceSpliterator.tryAdvance(consumer);
                if (!tryAdvance) {
                    if (this.lastNodeSpliterator == null && (findNextLeafNode = findNextLeafNode(this.tryAdvanceStack)) != null) {
                        this.tryAdvanceSpliterator = findNextLeafNode.spliterator();
                        return this.tryAdvanceSpliterator.tryAdvance(consumer);
                    }
                    this.curNode = null;
                }
                return tryAdvance;
            }

            public void forEachRemaining(Consumer consumer) {
                if (this.curNode == null) {
                    return;
                }
                if (this.tryAdvanceSpliterator == null) {
                    if (this.lastNodeSpliterator == null) {
                        Deque initStack = initStack();
                        while (true) {
                            Node findNextLeafNode = findNextLeafNode(initStack);
                            if (findNextLeafNode != null) {
                                findNextLeafNode.forEach(consumer);
                            } else {
                                this.curNode = null;
                                return;
                            }
                        }
                    } else {
                        this.lastNodeSpliterator.forEachRemaining(consumer);
                    }
                } else {
                    while (tryAdvance(consumer)) {
                    }
                }
            }
        }

        private static abstract class OfPrimitive extends InternalNodeSpliterator implements Spliterator.OfPrimitive {
            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            OfPrimitive(Node.OfPrimitive ofPrimitive) {
                super(ofPrimitive);
            }

            public boolean tryAdvance(Object obj) {
                Node.OfPrimitive ofPrimitive;
                if (!initTryAdvance()) {
                    return false;
                }
                boolean tryAdvance = ((Spliterator.OfPrimitive) this.tryAdvanceSpliterator).tryAdvance(obj);
                if (!tryAdvance) {
                    if (this.lastNodeSpliterator == null && (ofPrimitive = (Node.OfPrimitive) findNextLeafNode(this.tryAdvanceStack)) != null) {
                        this.tryAdvanceSpliterator = ofPrimitive.spliterator();
                        return ((Spliterator.OfPrimitive) this.tryAdvanceSpliterator).tryAdvance(obj);
                    }
                    this.curNode = null;
                }
                return tryAdvance;
            }

            public void forEachRemaining(Object obj) {
                if (this.curNode == null) {
                    return;
                }
                if (this.tryAdvanceSpliterator == null) {
                    if (this.lastNodeSpliterator == null) {
                        Deque initStack = initStack();
                        while (true) {
                            Node.OfPrimitive ofPrimitive = (Node.OfPrimitive) findNextLeafNode(initStack);
                            if (ofPrimitive != null) {
                                ofPrimitive.forEach(obj);
                            } else {
                                this.curNode = null;
                                return;
                            }
                        }
                    } else {
                        ((Spliterator.OfPrimitive) this.lastNodeSpliterator).forEachRemaining(obj);
                    }
                } else {
                    while (tryAdvance(obj)) {
                    }
                }
            }
        }

        private static final class OfInt extends OfPrimitive implements Spliterator.OfInt {
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfInt.-CC.$default$forEachRemaining((Spliterator.OfInt) this, consumer);
            }

            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfInt.-CC.$default$tryAdvance((Spliterator.OfInt) this, consumer);
            }

            public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                super.forEachRemaining((Object) intConsumer);
            }

            public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
                return super.tryAdvance((Object) intConsumer);
            }

            public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
                return (Spliterator.OfInt) super.trySplit();
            }

            OfInt(Node.OfInt ofInt) {
                super(ofInt);
            }
        }

        private static final class OfLong extends OfPrimitive implements Spliterator.OfLong {
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfLong.-CC.$default$forEachRemaining((Spliterator.OfLong) this, consumer);
            }

            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfLong.-CC.$default$tryAdvance((Spliterator.OfLong) this, consumer);
            }

            public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                super.forEachRemaining((Object) longConsumer);
            }

            public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
                return super.tryAdvance((Object) longConsumer);
            }

            public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
                return (Spliterator.OfLong) super.trySplit();
            }

            OfLong(Node.OfLong ofLong) {
                super(ofLong);
            }
        }

        private static final class OfDouble extends OfPrimitive implements Spliterator.OfDouble {
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfDouble.-CC.$default$forEachRemaining((Spliterator.OfDouble) this, consumer);
            }

            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfDouble.-CC.$default$tryAdvance((Spliterator.OfDouble) this, consumer);
            }

            public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                super.forEachRemaining((Object) doubleConsumer);
            }

            public /* bridge */ /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
                return super.tryAdvance((Object) doubleConsumer);
            }

            public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
                return (Spliterator.OfDouble) super.trySplit();
            }

            OfDouble(Node.OfDouble ofDouble) {
                super(ofDouble);
            }
        }
    }

    private static final class FixedNodeBuilder extends ArrayNode implements Node.Builder {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        public /* synthetic */ void accept(double d) {
            Sink.-CC.$default$accept(this, d);
        }

        public /* synthetic */ void accept(int i) {
            Sink.-CC.$default$accept((Sink) this, i);
        }

        public /* synthetic */ void accept(long j) {
            Sink.-CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        FixedNodeBuilder(long j, IntFunction intFunction) {
            super(j, intFunction);
        }

        public Node build() {
            if (this.curSize >= this.array.length) {
                return this;
            }
            throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)));
        }

        public void begin(long j) {
            if (j != this.array.length) {
                throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", Long.valueOf(j), Integer.valueOf(this.array.length)));
            }
            this.curSize = 0;
        }

        public void accept(Object obj) {
            if (this.curSize < this.array.length) {
                Object[] objArr = this.array;
                int i = this.curSize;
                this.curSize = i + 1;
                objArr[i] = obj;
                return;
            }
            throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", Integer.valueOf(this.array.length)));
        }

        public void end() {
            if (this.curSize < this.array.length) {
                throw new IllegalStateException(String.format("End size %d is less than fixed size %d", Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)));
            }
        }

        public String toString() {
            return String.format("FixedNodeBuilder[%d][%s]", Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array));
        }
    }

    private static final class SpinedNodeBuilder extends SpinedBuffer implements Node, Node.Builder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean building = false;

        public /* synthetic */ void accept(double d) {
            Sink.-CC.$default$accept(this, d);
        }

        public /* synthetic */ void accept(int i) {
            Sink.-CC.$default$accept((Sink) this, i);
        }

        public /* synthetic */ void accept(long j) {
            Sink.-CC.$default$accept((Sink) this, j);
        }

        public Node build() {
            return this;
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ Node getChild(int i) {
            return Node.-CC.$default$getChild(this, i);
        }

        public /* synthetic */ int getChildCount() {
            return Node.-CC.$default$getChildCount(this);
        }

        public /* synthetic */ StreamShape getShape() {
            return Node.-CC.$default$getShape(this);
        }

        public /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            return Node.-CC.$default$truncate(this, j, j2, intFunction);
        }

        SpinedNodeBuilder() {
        }

        public Spliterator spliterator() {
            return super.spliterator();
        }

        public void forEach(Consumer consumer) {
            super.forEach(consumer);
        }

        public void begin(long j) {
            this.building = true;
            clear();
            ensureCapacity(j);
        }

        public void accept(Object obj) {
            super.accept(obj);
        }

        public void end() {
            this.building = false;
        }

        public void copyInto(Object[] objArr, int i) {
            super.copyInto(objArr, i);
        }

        public Object[] asArray(IntFunction intFunction) {
            return super.asArray(intFunction);
        }
    }

    private static class IntArrayNode implements Node.OfInt {
        final int[] array;
        int curSize;

        public /* synthetic */ Object[] asArray(IntFunction intFunction) {
            return Node.OfPrimitive.-CC.$default$asArray(this, intFunction);
        }

        public /* synthetic */ void copyInto(Integer[] numArr, int i) {
            Node.OfInt.-CC.$default$copyInto((Node.OfInt) this, numArr, i);
        }

        public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
            Node.OfInt.-CC.$default$copyInto(this, objArr, i);
        }

        public /* synthetic */ void forEach(Consumer consumer) {
            Node.OfInt.-CC.$default$forEach(this, consumer);
        }

        public /* synthetic */ Node.OfPrimitive getChild(int i) {
            return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        public /* bridge */ /* synthetic */ Node getChild(int i) {
            return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        public /* synthetic */ int getChildCount() {
            return Node.-CC.$default$getChildCount(this);
        }

        public /* synthetic */ StreamShape getShape() {
            return Node.OfInt.-CC.$default$getShape(this);
        }

        public /* bridge */ /* synthetic */ Object newArray(int i) {
            return Node.OfInt.-CC.$default$newArray((Node.OfInt) this, i);
        }

        public /* synthetic */ int[] newArray(int i) {
            return Node.OfInt.-CC.$default$newArray((Node.OfInt) this, i);
        }

        public /* synthetic */ Node.OfInt truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfInt.-CC.$default$truncate((Node.OfInt) this, j, j2, intFunction);
        }

        public /* bridge */ /* synthetic */ Node.OfPrimitive truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfInt.-CC.$default$truncate((Node.OfInt) this, j, j2, intFunction);
        }

        public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfInt.-CC.$default$truncate((Node.OfInt) this, j, j2, intFunction);
        }

        IntArrayNode(long j) {
            if (j >= 2147483639) {
                throw new IllegalArgumentException("Stream size exceeds max array size");
            }
            this.array = new int[(int) j];
            this.curSize = 0;
        }

        IntArrayNode(int[] iArr) {
            this.array = iArr;
            this.curSize = iArr.length;
        }

        public Spliterator.OfInt spliterator() {
            return Arrays.spliterator(this.array, 0, this.curSize);
        }

        public int[] asPrimitiveArray() {
            int[] iArr = this.array;
            int length = iArr.length;
            int i = this.curSize;
            return length == i ? iArr : Arrays.copyOf(iArr, i);
        }

        public void copyInto(int[] iArr, int i) {
            System.arraycopy(this.array, 0, iArr, i, this.curSize);
        }

        public long count() {
            return this.curSize;
        }

        public void forEach(IntConsumer intConsumer) {
            for (int i = 0; i < this.curSize; i++) {
                intConsumer.accept(this.array[i]);
            }
        }

        public String toString() {
            return String.format("IntArrayNode[%d][%s]", Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array));
        }
    }

    private static class LongArrayNode implements Node.OfLong {
        final long[] array;
        int curSize;

        public /* synthetic */ Object[] asArray(IntFunction intFunction) {
            return Node.OfPrimitive.-CC.$default$asArray(this, intFunction);
        }

        public /* synthetic */ void copyInto(Long[] lArr, int i) {
            Node.OfLong.-CC.$default$copyInto((Node.OfLong) this, lArr, i);
        }

        public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
            Node.OfLong.-CC.$default$copyInto(this, objArr, i);
        }

        public /* synthetic */ void forEach(Consumer consumer) {
            Node.OfLong.-CC.$default$forEach(this, consumer);
        }

        public /* synthetic */ Node.OfPrimitive getChild(int i) {
            return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        public /* bridge */ /* synthetic */ Node getChild(int i) {
            return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        public /* synthetic */ int getChildCount() {
            return Node.-CC.$default$getChildCount(this);
        }

        public /* synthetic */ StreamShape getShape() {
            return Node.OfLong.-CC.$default$getShape(this);
        }

        public /* bridge */ /* synthetic */ Object newArray(int i) {
            return Node.OfLong.-CC.$default$newArray((Node.OfLong) this, i);
        }

        public /* synthetic */ long[] newArray(int i) {
            return Node.OfLong.-CC.$default$newArray((Node.OfLong) this, i);
        }

        public /* synthetic */ Node.OfLong truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfLong.-CC.$default$truncate((Node.OfLong) this, j, j2, intFunction);
        }

        public /* bridge */ /* synthetic */ Node.OfPrimitive truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfLong.-CC.$default$truncate((Node.OfLong) this, j, j2, intFunction);
        }

        public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfLong.-CC.$default$truncate((Node.OfLong) this, j, j2, intFunction);
        }

        LongArrayNode(long j) {
            if (j >= 2147483639) {
                throw new IllegalArgumentException("Stream size exceeds max array size");
            }
            this.array = new long[(int) j];
            this.curSize = 0;
        }

        LongArrayNode(long[] jArr) {
            this.array = jArr;
            this.curSize = jArr.length;
        }

        public Spliterator.OfLong spliterator() {
            return Arrays.spliterator(this.array, 0, this.curSize);
        }

        public long[] asPrimitiveArray() {
            long[] jArr = this.array;
            int length = jArr.length;
            int i = this.curSize;
            return length == i ? jArr : Arrays.copyOf(jArr, i);
        }

        public void copyInto(long[] jArr, int i) {
            System.arraycopy(this.array, 0, jArr, i, this.curSize);
        }

        public long count() {
            return this.curSize;
        }

        public void forEach(LongConsumer longConsumer) {
            for (int i = 0; i < this.curSize; i++) {
                longConsumer.accept(this.array[i]);
            }
        }

        public String toString() {
            return String.format("LongArrayNode[%d][%s]", Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array));
        }
    }

    private static class DoubleArrayNode implements Node.OfDouble {
        final double[] array;
        int curSize;

        public /* synthetic */ Object[] asArray(IntFunction intFunction) {
            return Node.OfPrimitive.-CC.$default$asArray(this, intFunction);
        }

        public /* synthetic */ void copyInto(Double[] dArr, int i) {
            Node.OfDouble.-CC.$default$copyInto((Node.OfDouble) this, dArr, i);
        }

        public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
            Node.OfDouble.-CC.$default$copyInto(this, objArr, i);
        }

        public /* synthetic */ void forEach(Consumer consumer) {
            Node.OfDouble.-CC.$default$forEach(this, consumer);
        }

        public /* synthetic */ Node.OfPrimitive getChild(int i) {
            return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        public /* bridge */ /* synthetic */ Node getChild(int i) {
            return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        public /* synthetic */ int getChildCount() {
            return Node.-CC.$default$getChildCount(this);
        }

        public /* synthetic */ StreamShape getShape() {
            return Node.OfDouble.-CC.$default$getShape(this);
        }

        public /* bridge */ /* synthetic */ Object newArray(int i) {
            return Node.OfDouble.-CC.$default$newArray((Node.OfDouble) this, i);
        }

        public /* synthetic */ double[] newArray(int i) {
            return Node.OfDouble.-CC.$default$newArray((Node.OfDouble) this, i);
        }

        public /* synthetic */ Node.OfDouble truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfDouble.-CC.$default$truncate((Node.OfDouble) this, j, j2, intFunction);
        }

        public /* bridge */ /* synthetic */ Node.OfPrimitive truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfDouble.-CC.$default$truncate((Node.OfDouble) this, j, j2, intFunction);
        }

        public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfDouble.-CC.$default$truncate((Node.OfDouble) this, j, j2, intFunction);
        }

        DoubleArrayNode(long j) {
            if (j >= 2147483639) {
                throw new IllegalArgumentException("Stream size exceeds max array size");
            }
            this.array = new double[(int) j];
            this.curSize = 0;
        }

        DoubleArrayNode(double[] dArr) {
            this.array = dArr;
            this.curSize = dArr.length;
        }

        public Spliterator.OfDouble spliterator() {
            return Arrays.spliterator(this.array, 0, this.curSize);
        }

        public double[] asPrimitiveArray() {
            double[] dArr = this.array;
            int length = dArr.length;
            int i = this.curSize;
            return length == i ? dArr : Arrays.copyOf(dArr, i);
        }

        public void copyInto(double[] dArr, int i) {
            System.arraycopy(this.array, 0, dArr, i, this.curSize);
        }

        public long count() {
            return this.curSize;
        }

        public void forEach(DoubleConsumer doubleConsumer) {
            for (int i = 0; i < this.curSize; i++) {
                doubleConsumer.accept(this.array[i]);
            }
        }

        public String toString() {
            return String.format("DoubleArrayNode[%d][%s]", Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array));
        }
    }

    private static final class IntFixedNodeBuilder extends IntArrayNode implements Node.Builder.OfInt {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        public /* synthetic */ void accept(double d) {
            Sink.-CC.$default$accept(this, d);
        }

        public /* synthetic */ void accept(long j) {
            Sink.-CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ void accept(Integer num) {
            Sink.OfInt.-CC.$default$accept((Sink.OfInt) this, num);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfInt.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
            return IntConsumer.-CC.$default$andThen(this, intConsumer);
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        IntFixedNodeBuilder(long j) {
            super(j);
        }

        public Node.OfInt build() {
            if (this.curSize >= this.array.length) {
                return this;
            }
            throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)));
        }

        public void begin(long j) {
            if (j != this.array.length) {
                throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", Long.valueOf(j), Integer.valueOf(this.array.length)));
            }
            this.curSize = 0;
        }

        public void accept(int i) {
            if (this.curSize < this.array.length) {
                int[] iArr = this.array;
                int i2 = this.curSize;
                this.curSize = i2 + 1;
                iArr[i2] = i;
                return;
            }
            throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", Integer.valueOf(this.array.length)));
        }

        public void end() {
            if (this.curSize < this.array.length) {
                throw new IllegalStateException(String.format("End size %d is less than fixed size %d", Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)));
            }
        }

        public String toString() {
            return String.format("IntFixedNodeBuilder[%d][%s]", Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array));
        }
    }

    private static final class LongFixedNodeBuilder extends LongArrayNode implements Node.Builder.OfLong {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        public /* synthetic */ void accept(double d) {
            Sink.-CC.$default$accept(this, d);
        }

        public /* synthetic */ void accept(int i) {
            Sink.-CC.$default$accept((Sink) this, i);
        }

        public /* synthetic */ void accept(Long l) {
            Sink.OfLong.-CC.$default$accept((Sink.OfLong) this, l);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfLong.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
            return LongConsumer.-CC.$default$andThen(this, longConsumer);
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        LongFixedNodeBuilder(long j) {
            super(j);
        }

        public Node.OfLong build() {
            if (this.curSize >= this.array.length) {
                return this;
            }
            throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)));
        }

        public void begin(long j) {
            if (j != this.array.length) {
                throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", Long.valueOf(j), Integer.valueOf(this.array.length)));
            }
            this.curSize = 0;
        }

        public void accept(long j) {
            if (this.curSize < this.array.length) {
                long[] jArr = this.array;
                int i = this.curSize;
                this.curSize = i + 1;
                jArr[i] = j;
                return;
            }
            throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", Integer.valueOf(this.array.length)));
        }

        public void end() {
            if (this.curSize < this.array.length) {
                throw new IllegalStateException(String.format("End size %d is less than fixed size %d", Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)));
            }
        }

        public String toString() {
            return String.format("LongFixedNodeBuilder[%d][%s]", Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array));
        }
    }

    private static final class DoubleFixedNodeBuilder extends DoubleArrayNode implements Node.Builder.OfDouble {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        public /* synthetic */ void accept(int i) {
            Sink.-CC.$default$accept((Sink) this, i);
        }

        public /* synthetic */ void accept(long j) {
            Sink.-CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ void accept(Double d) {
            Sink.OfDouble.-CC.$default$accept((Sink.OfDouble) this, d);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfDouble.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
            return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        DoubleFixedNodeBuilder(long j) {
            super(j);
        }

        public Node.OfDouble build() {
            if (this.curSize >= this.array.length) {
                return this;
            }
            throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)));
        }

        public void begin(long j) {
            if (j != this.array.length) {
                throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", Long.valueOf(j), Integer.valueOf(this.array.length)));
            }
            this.curSize = 0;
        }

        public void accept(double d) {
            if (this.curSize < this.array.length) {
                double[] dArr = this.array;
                int i = this.curSize;
                this.curSize = i + 1;
                dArr[i] = d;
                return;
            }
            throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", Integer.valueOf(this.array.length)));
        }

        public void end() {
            if (this.curSize < this.array.length) {
                throw new IllegalStateException(String.format("End size %d is less than fixed size %d", Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)));
            }
        }

        public String toString() {
            return String.format("DoubleFixedNodeBuilder[%d][%s]", Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array));
        }
    }

    private static final class IntSpinedNodeBuilder extends SpinedBuffer.OfInt implements Node.OfInt, Node.Builder.OfInt {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean building = false;

        public /* synthetic */ void accept(double d) {
            Sink.-CC.$default$accept(this, d);
        }

        public /* synthetic */ void accept(long j) {
            Sink.-CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ void accept(Integer num) {
            Sink.OfInt.-CC.$default$accept((Sink.OfInt) this, num);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfInt.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ Object[] asArray(IntFunction intFunction) {
            return Node.OfPrimitive.-CC.$default$asArray(this, intFunction);
        }

        public Node.OfInt build() {
            return this;
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void copyInto(Integer[] numArr, int i) {
            Node.OfInt.-CC.$default$copyInto((Node.OfInt) this, numArr, i);
        }

        public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
            Node.OfInt.-CC.$default$copyInto(this, objArr, i);
        }

        public /* synthetic */ Node.OfPrimitive getChild(int i) {
            return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        public /* bridge */ /* synthetic */ Node getChild(int i) {
            return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        public /* synthetic */ int getChildCount() {
            return Node.-CC.$default$getChildCount(this);
        }

        public /* synthetic */ StreamShape getShape() {
            return Node.OfInt.-CC.$default$getShape(this);
        }

        public /* synthetic */ Node.OfInt truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfInt.-CC.$default$truncate((Node.OfInt) this, j, j2, intFunction);
        }

        public /* bridge */ /* synthetic */ Node.OfPrimitive truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfInt.-CC.$default$truncate((Node.OfInt) this, j, j2, intFunction);
        }

        public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfInt.-CC.$default$truncate((Node.OfInt) this, j, j2, intFunction);
        }

        IntSpinedNodeBuilder() {
        }

        public Spliterator.OfInt spliterator() {
            return super.spliterator();
        }

        public void forEach(IntConsumer intConsumer) {
            super.forEach((Object) intConsumer);
        }

        public void begin(long j) {
            this.building = true;
            clear();
            ensureCapacity(j);
        }

        public void accept(int i) {
            super.accept(i);
        }

        public void end() {
            this.building = false;
        }

        public void copyInto(int[] iArr, int i) throws IndexOutOfBoundsException {
            super.copyInto((Object) iArr, i);
        }

        public int[] asPrimitiveArray() {
            return (int[]) super.asPrimitiveArray();
        }
    }

    private static final class LongSpinedNodeBuilder extends SpinedBuffer.OfLong implements Node.OfLong, Node.Builder.OfLong {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean building = false;

        public /* synthetic */ void accept(double d) {
            Sink.-CC.$default$accept(this, d);
        }

        public /* synthetic */ void accept(int i) {
            Sink.-CC.$default$accept((Sink) this, i);
        }

        public /* synthetic */ void accept(Long l) {
            Sink.OfLong.-CC.$default$accept((Sink.OfLong) this, l);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfLong.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ Object[] asArray(IntFunction intFunction) {
            return Node.OfPrimitive.-CC.$default$asArray(this, intFunction);
        }

        public Node.OfLong build() {
            return this;
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void copyInto(Long[] lArr, int i) {
            Node.OfLong.-CC.$default$copyInto((Node.OfLong) this, lArr, i);
        }

        public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
            Node.OfLong.-CC.$default$copyInto(this, objArr, i);
        }

        public /* synthetic */ Node.OfPrimitive getChild(int i) {
            return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        public /* bridge */ /* synthetic */ Node getChild(int i) {
            return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        public /* synthetic */ int getChildCount() {
            return Node.-CC.$default$getChildCount(this);
        }

        public /* synthetic */ StreamShape getShape() {
            return Node.OfLong.-CC.$default$getShape(this);
        }

        public /* synthetic */ Node.OfLong truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfLong.-CC.$default$truncate((Node.OfLong) this, j, j2, intFunction);
        }

        public /* bridge */ /* synthetic */ Node.OfPrimitive truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfLong.-CC.$default$truncate((Node.OfLong) this, j, j2, intFunction);
        }

        public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfLong.-CC.$default$truncate((Node.OfLong) this, j, j2, intFunction);
        }

        LongSpinedNodeBuilder() {
        }

        public Spliterator.OfLong spliterator() {
            return super.spliterator();
        }

        public void forEach(LongConsumer longConsumer) {
            super.forEach((Object) longConsumer);
        }

        public void begin(long j) {
            this.building = true;
            clear();
            ensureCapacity(j);
        }

        public void accept(long j) {
            super.accept(j);
        }

        public void end() {
            this.building = false;
        }

        public void copyInto(long[] jArr, int i) {
            super.copyInto((Object) jArr, i);
        }

        public long[] asPrimitiveArray() {
            return (long[]) super.asPrimitiveArray();
        }
    }

    private static final class DoubleSpinedNodeBuilder extends SpinedBuffer.OfDouble implements Node.OfDouble, Node.Builder.OfDouble {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean building = false;

        public /* synthetic */ void accept(int i) {
            Sink.-CC.$default$accept((Sink) this, i);
        }

        public /* synthetic */ void accept(long j) {
            Sink.-CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ void accept(Double d) {
            Sink.OfDouble.-CC.$default$accept((Sink.OfDouble) this, d);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfDouble.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ Object[] asArray(IntFunction intFunction) {
            return Node.OfPrimitive.-CC.$default$asArray(this, intFunction);
        }

        public Node.OfDouble build() {
            return this;
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void copyInto(Double[] dArr, int i) {
            Node.OfDouble.-CC.$default$copyInto((Node.OfDouble) this, dArr, i);
        }

        public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
            Node.OfDouble.-CC.$default$copyInto(this, objArr, i);
        }

        public /* synthetic */ Node.OfPrimitive getChild(int i) {
            return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        public /* bridge */ /* synthetic */ Node getChild(int i) {
            return Node.OfPrimitive.-CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        public /* synthetic */ int getChildCount() {
            return Node.-CC.$default$getChildCount(this);
        }

        public /* synthetic */ StreamShape getShape() {
            return Node.OfDouble.-CC.$default$getShape(this);
        }

        public /* synthetic */ Node.OfDouble truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfDouble.-CC.$default$truncate((Node.OfDouble) this, j, j2, intFunction);
        }

        public /* bridge */ /* synthetic */ Node.OfPrimitive truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfDouble.-CC.$default$truncate((Node.OfDouble) this, j, j2, intFunction);
        }

        public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfDouble.-CC.$default$truncate((Node.OfDouble) this, j, j2, intFunction);
        }

        DoubleSpinedNodeBuilder() {
        }

        public Spliterator.OfDouble spliterator() {
            return super.spliterator();
        }

        public void forEach(DoubleConsumer doubleConsumer) {
            super.forEach((Object) doubleConsumer);
        }

        public void begin(long j) {
            this.building = true;
            clear();
            ensureCapacity(j);
        }

        public void accept(double d) {
            super.accept(d);
        }

        public void end() {
            this.building = false;
        }

        public void copyInto(double[] dArr, int i) {
            super.copyInto((Object) dArr, i);
        }

        public double[] asPrimitiveArray() {
            return (double[]) super.asPrimitiveArray();
        }
    }

    private static abstract class SizedCollectorTask extends CountedCompleter implements Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        protected int fence;
        protected final PipelineHelper helper;
        protected int index;
        protected long length;
        protected long offset;
        protected final Spliterator spliterator;
        protected final long targetSize;

        public /* synthetic */ void accept(double d) {
            Sink.-CC.$default$accept(this, d);
        }

        public /* synthetic */ void accept(int i) {
            Sink.-CC.$default$accept((Sink) this, i);
        }

        public /* synthetic */ void accept(long j) {
            Sink.-CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void end() {
            Sink.-CC.$default$end(this);
        }

        abstract SizedCollectorTask makeChild(Spliterator spliterator, long j, long j2);

        SizedCollectorTask(Spliterator spliterator, PipelineHelper pipelineHelper, int i) {
            this.spliterator = spliterator;
            this.helper = pipelineHelper;
            this.targetSize = AbstractTask.suggestTargetSize(spliterator.estimateSize());
            this.offset = 0L;
            this.length = i;
        }

        SizedCollectorTask(SizedCollectorTask sizedCollectorTask, Spliterator spliterator, long j, long j2, int i) {
            super(sizedCollectorTask);
            this.spliterator = spliterator;
            this.helper = sizedCollectorTask.helper;
            this.targetSize = sizedCollectorTask.targetSize;
            this.offset = j;
            this.length = j2;
            if (j < 0 || j2 < 0 || (j + j2) - 1 >= i) {
                throw new IllegalArgumentException(String.format("offset and length interval [%d, %d + %d) is not within array size interval [0, %d)", Long.valueOf(j), Long.valueOf(j), Long.valueOf(j2), Integer.valueOf(i)));
            }
        }

        public void compute() {
            Spliterator trySplit;
            Spliterator spliterator = this.spliterator;
            SizedCollectorTask sizedCollectorTask = this;
            while (spliterator.estimateSize() > sizedCollectorTask.targetSize && (trySplit = spliterator.trySplit()) != null) {
                sizedCollectorTask.setPendingCount(1);
                long estimateSize = trySplit.estimateSize();
                SizedCollectorTask sizedCollectorTask2 = sizedCollectorTask;
                sizedCollectorTask2.makeChild(trySplit, sizedCollectorTask.offset, estimateSize).fork();
                sizedCollectorTask = sizedCollectorTask2.makeChild(spliterator, sizedCollectorTask2.offset + estimateSize, sizedCollectorTask2.length - estimateSize);
            }
            SizedCollectorTask sizedCollectorTask3 = sizedCollectorTask;
            sizedCollectorTask3.helper.wrapAndCopyInto(sizedCollectorTask3, spliterator);
            sizedCollectorTask3.propagateCompletion();
        }

        public void begin(long j) {
            long j2 = this.length;
            if (j > j2) {
                throw new IllegalStateException("size passed to Sink.begin exceeds array length");
            }
            int i = (int) this.offset;
            this.index = i;
            this.fence = i + ((int) j2);
        }

        static final class OfRef extends SizedCollectorTask implements Sink {
            private final Object[] array;

            OfRef(Spliterator spliterator, PipelineHelper pipelineHelper, Object[] objArr) {
                super(spliterator, pipelineHelper, objArr.length);
                this.array = objArr;
            }

            OfRef(OfRef ofRef, Spliterator spliterator, long j, long j2) {
                super(ofRef, spliterator, j, j2, ofRef.array.length);
                this.array = ofRef.array;
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public OfRef makeChild(Spliterator spliterator, long j, long j2) {
                return new OfRef(this, spliterator, j, j2);
            }

            public void accept(Object obj) {
                if (this.index >= this.fence) {
                    throw new IndexOutOfBoundsException(Integer.toString(this.index));
                }
                Object[] objArr = this.array;
                int i = this.index;
                this.index = i + 1;
                objArr[i] = obj;
            }
        }

        static final class OfInt extends SizedCollectorTask implements Sink.OfInt {
            private final int[] array;

            public /* synthetic */ void accept(Integer num) {
                Sink.OfInt.-CC.$default$accept((Sink.OfInt) this, num);
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                Sink.OfInt.-CC.$default$accept(this, obj);
            }

            public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
                return IntConsumer.-CC.$default$andThen(this, intConsumer);
            }

            OfInt(Spliterator spliterator, PipelineHelper pipelineHelper, int[] iArr) {
                super(spliterator, pipelineHelper, iArr.length);
                this.array = iArr;
            }

            OfInt(OfInt ofInt, Spliterator spliterator, long j, long j2) {
                super(ofInt, spliterator, j, j2, ofInt.array.length);
                this.array = ofInt.array;
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public OfInt makeChild(Spliterator spliterator, long j, long j2) {
                return new OfInt(this, spliterator, j, j2);
            }

            public void accept(int i) {
                if (this.index >= this.fence) {
                    throw new IndexOutOfBoundsException(Integer.toString(this.index));
                }
                int[] iArr = this.array;
                int i2 = this.index;
                this.index = i2 + 1;
                iArr[i2] = i;
            }
        }

        static final class OfLong extends SizedCollectorTask implements Sink.OfLong {
            private final long[] array;

            public /* synthetic */ void accept(Long l) {
                Sink.OfLong.-CC.$default$accept((Sink.OfLong) this, l);
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                Sink.OfLong.-CC.$default$accept(this, obj);
            }

            public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
                return LongConsumer.-CC.$default$andThen(this, longConsumer);
            }

            OfLong(Spliterator spliterator, PipelineHelper pipelineHelper, long[] jArr) {
                super(spliterator, pipelineHelper, jArr.length);
                this.array = jArr;
            }

            OfLong(OfLong ofLong, Spliterator spliterator, long j, long j2) {
                super(ofLong, spliterator, j, j2, ofLong.array.length);
                this.array = ofLong.array;
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public OfLong makeChild(Spliterator spliterator, long j, long j2) {
                return new OfLong(this, spliterator, j, j2);
            }

            public void accept(long j) {
                if (this.index >= this.fence) {
                    throw new IndexOutOfBoundsException(Integer.toString(this.index));
                }
                long[] jArr = this.array;
                int i = this.index;
                this.index = i + 1;
                jArr[i] = j;
            }
        }

        static final class OfDouble extends SizedCollectorTask implements Sink.OfDouble {
            private final double[] array;

            public /* synthetic */ void accept(Double d) {
                Sink.OfDouble.-CC.$default$accept((Sink.OfDouble) this, d);
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                Sink.OfDouble.-CC.$default$accept(this, obj);
            }

            public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
                return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
            }

            OfDouble(Spliterator spliterator, PipelineHelper pipelineHelper, double[] dArr) {
                super(spliterator, pipelineHelper, dArr.length);
                this.array = dArr;
            }

            OfDouble(OfDouble ofDouble, Spliterator spliterator, long j, long j2) {
                super(ofDouble, spliterator, j, j2, ofDouble.array.length);
                this.array = ofDouble.array;
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public OfDouble makeChild(Spliterator spliterator, long j, long j2) {
                return new OfDouble(this, spliterator, j, j2);
            }

            public void accept(double d) {
                if (this.index >= this.fence) {
                    throw new IndexOutOfBoundsException(Integer.toString(this.index));
                }
                double[] dArr = this.array;
                int i = this.index;
                this.index = i + 1;
                dArr[i] = d;
            }
        }
    }

    private static abstract class ToArrayTask extends CountedCompleter {
        protected final Node node;
        protected final int offset;

        abstract void copyNodeToArray();

        abstract ToArrayTask makeChild(int i, int i2);

        ToArrayTask(Node node, int i) {
            this.node = node;
            this.offset = i;
        }

        ToArrayTask(ToArrayTask toArrayTask, Node node, int i) {
            super(toArrayTask);
            this.node = node;
            this.offset = i;
        }

        public void compute() {
            ToArrayTask toArrayTask = this;
            while (toArrayTask.node.getChildCount() != 0) {
                toArrayTask.setPendingCount(toArrayTask.node.getChildCount() - 1);
                int i = 0;
                int i2 = 0;
                while (i < toArrayTask.node.getChildCount() - 1) {
                    ToArrayTask makeChild = toArrayTask.makeChild(i, toArrayTask.offset + i2);
                    i2 = (int) (i2 + makeChild.node.count());
                    makeChild.fork();
                    i++;
                }
                toArrayTask = toArrayTask.makeChild(i, toArrayTask.offset + i2);
            }
            toArrayTask.copyNodeToArray();
            toArrayTask.propagateCompletion();
        }

        private static final class OfRef extends ToArrayTask {
            private final Object[] array;

            /* synthetic */ OfRef(Node node, Object[] objArr, int i, Nodes-IA r4) {
                this(node, objArr, i);
            }

            private OfRef(Node node, Object[] objArr, int i) {
                super(node, i);
                this.array = objArr;
            }

            private OfRef(OfRef ofRef, Node node, int i) {
                super(ofRef, node, i);
                this.array = ofRef.array;
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public OfRef makeChild(int i, int i2) {
                return new OfRef(this, this.node.getChild(i), i2);
            }

            void copyNodeToArray() {
                this.node.copyInto(this.array, this.offset);
            }
        }

        private static class OfPrimitive extends ToArrayTask {
            private final Object array;

            /* synthetic */ OfPrimitive(Node.OfPrimitive ofPrimitive, Object obj, int i, Nodes-IA r4) {
                this(ofPrimitive, obj, i);
            }

            private OfPrimitive(Node.OfPrimitive ofPrimitive, Object obj, int i) {
                super(ofPrimitive, i);
                this.array = obj;
            }

            private OfPrimitive(OfPrimitive ofPrimitive, Node.OfPrimitive ofPrimitive2, int i) {
                super(ofPrimitive, ofPrimitive2, i);
                this.array = ofPrimitive.array;
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public OfPrimitive makeChild(int i, int i2) {
                return new OfPrimitive(this, ((Node.OfPrimitive) this.node).getChild(i), i2);
            }

            void copyNodeToArray() {
                ((Node.OfPrimitive) this.node).copyInto(this.array, this.offset);
            }
        }

        private static final class OfInt extends OfPrimitive {
            /* synthetic */ OfInt(Node.OfInt ofInt, int[] iArr, int i, Nodes-IA r4) {
                this(ofInt, iArr, i);
            }

            private OfInt(Node.OfInt ofInt, int[] iArr, int i) {
                super(ofInt, iArr, i, null);
            }
        }

        private static final class OfLong extends OfPrimitive {
            /* synthetic */ OfLong(Node.OfLong ofLong, long[] jArr, int i, Nodes-IA r4) {
                this(ofLong, jArr, i);
            }

            private OfLong(Node.OfLong ofLong, long[] jArr, int i) {
                super(ofLong, jArr, i, null);
            }
        }

        private static final class OfDouble extends OfPrimitive {
            /* synthetic */ OfDouble(Node.OfDouble ofDouble, double[] dArr, int i, Nodes-IA r4) {
                this(ofDouble, dArr, i);
            }

            private OfDouble(Node.OfDouble ofDouble, double[] dArr, int i) {
                super(ofDouble, dArr, i, null);
            }
        }
    }

    private static class CollectorTask extends AbstractTask {
        protected final LongFunction builderFactory;
        protected final BinaryOperator concFactory;
        protected final PipelineHelper helper;

        CollectorTask(PipelineHelper pipelineHelper, Spliterator spliterator, LongFunction longFunction, BinaryOperator binaryOperator) {
            super(pipelineHelper, spliterator);
            this.helper = pipelineHelper;
            this.builderFactory = longFunction;
            this.concFactory = binaryOperator;
        }

        CollectorTask(CollectorTask collectorTask, Spliterator spliterator) {
            super(collectorTask, spliterator);
            this.helper = collectorTask.helper;
            this.builderFactory = collectorTask.builderFactory;
            this.concFactory = collectorTask.concFactory;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public CollectorTask makeChild(Spliterator spliterator) {
            return new CollectorTask(this, spliterator);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public Node doLeaf() {
            return ((Node.Builder) this.helper.wrapAndCopyInto((Node.Builder) this.builderFactory.apply(this.helper.exactOutputSizeIfKnown(this.spliterator)), this.spliterator)).build();
        }

        public void onCompletion(CountedCompleter countedCompleter) {
            if (!isLeaf()) {
                setLocalResult((Node) this.concFactory.apply((Node) ((CollectorTask) this.leftChild).getLocalResult(), (Node) ((CollectorTask) this.rightChild).getLocalResult()));
            }
            super.onCompletion(countedCompleter);
        }

        private static final class OfRef extends CollectorTask {
            protected /* bridge */ /* synthetic */ Object doLeaf() {
                return super.doLeaf();
            }

            protected /* bridge */ /* synthetic */ AbstractTask makeChild(Spliterator spliterator) {
                return super.makeChild(spliterator);
            }

            OfRef(PipelineHelper pipelineHelper, IntFunction intFunction, Spliterator spliterator) {
                super(pipelineHelper, spliterator, new Nodes$CollectorTask$OfRef$$ExternalSyntheticLambda0(intFunction), new Nodes$CollectorTask$OfRef$$ExternalSyntheticLambda1());
            }

            static /* synthetic */ Node.Builder lambda$new$0(IntFunction intFunction, long j) {
                return Nodes.builder(j, intFunction);
            }
        }

        private static final class OfInt extends CollectorTask {
            protected /* bridge */ /* synthetic */ Object doLeaf() {
                return super.doLeaf();
            }

            protected /* bridge */ /* synthetic */ AbstractTask makeChild(Spliterator spliterator) {
                return super.makeChild(spliterator);
            }

            OfInt(PipelineHelper pipelineHelper, Spliterator spliterator) {
                super(pipelineHelper, spliterator, new Nodes$CollectorTask$OfInt$$ExternalSyntheticLambda0(), new Nodes$CollectorTask$OfInt$$ExternalSyntheticLambda1());
            }
        }

        private static final class OfLong extends CollectorTask {
            protected /* bridge */ /* synthetic */ Object doLeaf() {
                return super.doLeaf();
            }

            protected /* bridge */ /* synthetic */ AbstractTask makeChild(Spliterator spliterator) {
                return super.makeChild(spliterator);
            }

            OfLong(PipelineHelper pipelineHelper, Spliterator spliterator) {
                super(pipelineHelper, spliterator, new Nodes$CollectorTask$OfLong$$ExternalSyntheticLambda0(), new Nodes$CollectorTask$OfLong$$ExternalSyntheticLambda1());
            }
        }

        private static final class OfDouble extends CollectorTask {
            protected /* bridge */ /* synthetic */ Object doLeaf() {
                return super.doLeaf();
            }

            protected /* bridge */ /* synthetic */ AbstractTask makeChild(Spliterator spliterator) {
                return super.makeChild(spliterator);
            }

            OfDouble(PipelineHelper pipelineHelper, Spliterator spliterator) {
                super(pipelineHelper, spliterator, new Nodes$CollectorTask$OfDouble$$ExternalSyntheticLambda0(), new Nodes$CollectorTask$OfDouble$$ExternalSyntheticLambda1());
            }
        }
    }
}
