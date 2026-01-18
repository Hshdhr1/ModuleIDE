package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import jdk.internal.misc.TerminatingThreadLocal;
import jdk.internal.misc.Unsafe;
import sun.security.action.GetPropertyAction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class Util {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static volatile Constructor directByteBufferConstructor;
    private static volatile Constructor directByteBufferRConstructor;
    private static final int TEMP_BUF_POOL_SIZE = IOUtil.IOV_MAX;
    private static final long MAX_CACHED_BUFFER_SIZE = getMaxCachedBufferSize();
    private static ThreadLocal bufferCache = new 1();
    private static Unsafe unsafe = Unsafe.getUnsafe();
    private static int pageSize = -1;

    static /* bridge */ /* synthetic */ int -$$Nest$sfgetTEMP_BUF_POOL_SIZE() {
        return TEMP_BUF_POOL_SIZE;
    }

    static /* bridge */ /* synthetic */ void -$$Nest$sfputdirectByteBufferConstructor(Constructor constructor) {
        directByteBufferConstructor = constructor;
    }

    static /* bridge */ /* synthetic */ void -$$Nest$sfputdirectByteBufferRConstructor(Constructor constructor) {
        directByteBufferRConstructor = constructor;
    }

    static /* bridge */ /* synthetic */ void -$$Nest$smfree(ByteBuffer byteBuffer) {
        free(byteBuffer);
    }

    static /* bridge */ /* synthetic */ boolean -$$Nest$smisBufferTooLarge(int i) {
        return isBufferTooLarge(i);
    }

    static /* bridge */ /* synthetic */ boolean -$$Nest$smisBufferTooLarge(ByteBuffer byteBuffer) {
        return isBufferTooLarge(byteBuffer);
    }

    class 1 extends TerminatingThreadLocal {
        1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public BufferCache initialValue() {
            return new BufferCache();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void threadTerminated(BufferCache bufferCache) {
            while (!bufferCache.isEmpty()) {
                Util.-$$Nest$smfree(bufferCache.removeFirst());
            }
        }
    }

    private static long getMaxCachedBufferSize() {
        String privilegedGetProperty = GetPropertyAction.privilegedGetProperty("jdk.nio.maxCachedBufferSize");
        if (privilegedGetProperty == null) {
            return Long.MAX_VALUE;
        }
        try {
            long parseLong = Long.parseLong(privilegedGetProperty);
            if (parseLong >= 0) {
                return parseLong;
            }
            return Long.MAX_VALUE;
        } catch (NumberFormatException unused) {
            return Long.MAX_VALUE;
        }
    }

    private static boolean isBufferTooLarge(int i) {
        return ((long) i) > MAX_CACHED_BUFFER_SIZE;
    }

    private static boolean isBufferTooLarge(ByteBuffer byteBuffer) {
        return isBufferTooLarge(byteBuffer.capacity());
    }

    private static class BufferCache {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private ByteBuffer[] buffers = new ByteBuffer[Util.-$$Nest$sfgetTEMP_BUF_POOL_SIZE()];
        private int count;
        private int start;

        private int next(int i) {
            return (i + 1) % Util.-$$Nest$sfgetTEMP_BUF_POOL_SIZE();
        }

        BufferCache() {
        }

        ByteBuffer get(int i) {
            ByteBuffer byteBuffer;
            if (this.count == 0) {
                return null;
            }
            ByteBuffer[] byteBufferArr = this.buffers;
            ByteBuffer byteBuffer2 = byteBufferArr[this.start];
            if (byteBuffer2.capacity() < i) {
                int i2 = this.start;
                do {
                    i2 = next(i2);
                    if (i2 == this.start || (byteBuffer = byteBufferArr[i2]) == null) {
                        byteBuffer = null;
                        break;
                    }
                } while (byteBuffer.capacity() < i);
                if (byteBuffer == null) {
                    return null;
                }
                byteBufferArr[i2] = byteBufferArr[this.start];
                byteBuffer2 = byteBuffer;
            }
            int i3 = this.start;
            byteBufferArr[i3] = null;
            this.start = next(i3);
            this.count--;
            byteBuffer2.rewind();
            byteBuffer2.limit(i);
            return byteBuffer2;
        }

        boolean offerFirst(ByteBuffer byteBuffer) {
            if (this.count >= Util.-$$Nest$sfgetTEMP_BUF_POOL_SIZE()) {
                return false;
            }
            int i = ((this.start + Util.-$$Nest$sfgetTEMP_BUF_POOL_SIZE()) - 1) % Util.-$$Nest$sfgetTEMP_BUF_POOL_SIZE();
            this.start = i;
            this.buffers[i] = byteBuffer;
            this.count++;
            return true;
        }

        boolean offerLast(ByteBuffer byteBuffer) {
            if (this.count >= Util.-$$Nest$sfgetTEMP_BUF_POOL_SIZE()) {
                return false;
            }
            this.buffers[(this.start + this.count) % Util.-$$Nest$sfgetTEMP_BUF_POOL_SIZE()] = byteBuffer;
            this.count++;
            return true;
        }

        boolean isEmpty() {
            return this.count == 0;
        }

        ByteBuffer removeFirst() {
            ByteBuffer[] byteBufferArr = this.buffers;
            int i = this.start;
            ByteBuffer byteBuffer = byteBufferArr[i];
            byteBufferArr[i] = null;
            this.start = next(i);
            this.count--;
            return byteBuffer;
        }
    }

    public static ByteBuffer getTemporaryDirectBuffer(int i) {
        if (isBufferTooLarge(i)) {
            return ByteBuffer.allocateDirect(i);
        }
        BufferCache bufferCache2 = (BufferCache) bufferCache.get();
        ByteBuffer byteBuffer = bufferCache2.get(i);
        if (byteBuffer != null) {
            return byteBuffer;
        }
        if (!bufferCache2.isEmpty()) {
            free(bufferCache2.removeFirst());
        }
        return ByteBuffer.allocateDirect(i);
    }

    public static ByteBuffer getTemporaryAlignedDirectBuffer(int i, int i2) {
        if (isBufferTooLarge(i)) {
            return ByteBuffer.allocateDirect((i + i2) - 1).alignedSlice(i2);
        }
        BufferCache bufferCache2 = (BufferCache) bufferCache.get();
        ByteBuffer byteBuffer = bufferCache2.get(i);
        if (byteBuffer != null) {
            if (byteBuffer.alignmentOffset(0, i2) == 0) {
                return byteBuffer;
            }
        } else if (!bufferCache2.isEmpty()) {
            free(bufferCache2.removeFirst());
        }
        return ByteBuffer.allocateDirect((i + i2) - 1).alignedSlice(i2);
    }

    public static void releaseTemporaryDirectBuffer(ByteBuffer byteBuffer) {
        offerFirstTemporaryDirectBuffer(byteBuffer);
    }

    static void offerFirstTemporaryDirectBuffer(ByteBuffer byteBuffer) {
        if (isBufferTooLarge(byteBuffer)) {
            free(byteBuffer);
        } else {
            if (((BufferCache) bufferCache.get()).offerFirst(byteBuffer)) {
                return;
            }
            free(byteBuffer);
        }
    }

    static void offerLastTemporaryDirectBuffer(ByteBuffer byteBuffer) {
        if (isBufferTooLarge(byteBuffer)) {
            free(byteBuffer);
        } else {
            if (((BufferCache) bufferCache.get()).offerLast(byteBuffer)) {
                return;
            }
            free(byteBuffer);
        }
    }

    private static void free(ByteBuffer byteBuffer) {
        ((DirectBuffer) byteBuffer).cleaner().clean();
    }

    static ByteBuffer[] subsequence(ByteBuffer[] byteBufferArr, int i, int i2) {
        if (i == 0 && i2 == byteBufferArr.length) {
            return byteBufferArr;
        }
        ByteBuffer[] byteBufferArr2 = new ByteBuffer[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            byteBufferArr2[i3] = byteBufferArr[i + i3];
        }
        return byteBufferArr2;
    }

    class 2 implements Set {
        final /* synthetic */ Set val$s;

        public /* synthetic */ void forEach(Consumer consumer) {
            Collection.-CC.$default$forEach(this, consumer);
        }

        public /* synthetic */ Stream parallelStream() {
            return Collection.-CC.$default$parallelStream(this);
        }

        public /* synthetic */ boolean removeIf(Predicate predicate) {
            return Collection.-CC.$default$removeIf(this, predicate);
        }

        public /* synthetic */ Spliterator spliterator() {
            return Set.-CC.$default$spliterator(this);
        }

        public /* synthetic */ Stream stream() {
            return Collection.-CC.$default$stream(this);
        }

        public /* synthetic */ Object[] toArray(IntFunction intFunction) {
            return Collection.-CC.$default$toArray(this, intFunction);
        }

        2(Set set) {
            this.val$s = set;
        }

        public int size() {
            return this.val$s.size();
        }

        public boolean isEmpty() {
            return this.val$s.isEmpty();
        }

        public boolean contains(Object obj) {
            return this.val$s.contains(obj);
        }

        public Object[] toArray() {
            return this.val$s.toArray();
        }

        public Object[] toArray(Object[] objArr) {
            return this.val$s.toArray(objArr);
        }

        public String toString() {
            return this.val$s.toString();
        }

        public Iterator iterator() {
            return this.val$s.iterator();
        }

        public boolean equals(Object obj) {
            return this.val$s.equals(obj);
        }

        public int hashCode() {
            return this.val$s.hashCode();
        }

        public void clear() {
            this.val$s.clear();
        }

        public boolean remove(Object obj) {
            return this.val$s.remove(obj);
        }

        public boolean containsAll(Collection collection) {
            return this.val$s.containsAll(collection);
        }

        public boolean removeAll(Collection collection) {
            return this.val$s.removeAll(collection);
        }

        public boolean retainAll(Collection collection) {
            return this.val$s.retainAll(collection);
        }

        public boolean add(Object obj) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection collection) {
            throw new UnsupportedOperationException();
        }
    }

    static Set ungrowableSet(Set set) {
        return new 2(set);
    }

    private static byte _get(long j) {
        return unsafe.getByte(j);
    }

    private static void _put(long j, byte b) {
        unsafe.putByte(j, b);
    }

    static void erase(ByteBuffer byteBuffer) {
        unsafe.setMemory(((DirectBuffer) byteBuffer).address(), byteBuffer.capacity(), (byte) 0);
    }

    static Unsafe unsafe() {
        return unsafe;
    }

    static int pageSize() {
        if (pageSize == -1) {
            pageSize = unsafe().pageSize();
        }
        return pageSize;
    }

    class 3 implements PrivilegedAction {
        3() {
        }

        public Void run() {
            try {
                Constructor declaredConstructor = Class.forName("java.nio.DirectByteBuffer").getDeclaredConstructor(new Class[]{Integer.TYPE, Long.TYPE, FileDescriptor.class, Runnable.class});
                declaredConstructor.setAccessible(true);
                Util.-$$Nest$sfputdirectByteBufferConstructor(declaredConstructor);
                return null;
            } catch (NoSuchMethodException | ClassCastException | ClassNotFoundException | IllegalArgumentException e) {
                throw new InternalError(e);
            }
        }
    }

    private static void initDBBConstructor() {
        AccessController.doPrivileged(new 3());
    }

    static MappedByteBuffer newMappedByteBuffer(int i, long j, FileDescriptor fileDescriptor, Runnable runnable) {
        if (directByteBufferConstructor == null) {
            initDBBConstructor();
        }
        try {
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e = e;
        }
        try {
            return (MappedByteBuffer) directByteBufferConstructor.newInstance(new Object[]{Integer.valueOf(i), Long.valueOf(j), fileDescriptor, runnable});
        } catch (InvocationTargetException e2) {
            e = e2;
            throw new InternalError(e);
        } catch (IllegalAccessException e3) {
            e = e3;
            throw new InternalError(e);
        }
    }

    class 4 implements PrivilegedAction {
        4() {
        }

        public Void run() {
            try {
                Constructor declaredConstructor = Class.forName("java.nio.DirectByteBufferR").getDeclaredConstructor(new Class[]{Integer.TYPE, Long.TYPE, FileDescriptor.class, Runnable.class});
                declaredConstructor.setAccessible(true);
                Util.-$$Nest$sfputdirectByteBufferRConstructor(declaredConstructor);
                return null;
            } catch (NoSuchMethodException | ClassCastException | ClassNotFoundException | IllegalArgumentException e) {
                throw new InternalError(e);
            }
        }
    }

    private static void initDBBRConstructor() {
        AccessController.doPrivileged(new 4());
    }

    static MappedByteBuffer newMappedByteBufferR(int i, long j, FileDescriptor fileDescriptor, Runnable runnable) {
        if (directByteBufferRConstructor == null) {
            initDBBRConstructor();
        }
        try {
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e = e;
        }
        try {
            return (MappedByteBuffer) directByteBufferRConstructor.newInstance(new Object[]{Integer.valueOf(i), Long.valueOf(j), fileDescriptor, runnable});
        } catch (InvocationTargetException e2) {
            e = e2;
            throw new InternalError(e);
        } catch (IllegalAccessException e3) {
            e = e3;
            throw new InternalError(e);
        }
    }

    static void checkBufferPositionAligned(ByteBuffer byteBuffer, int i, int i2) throws IOException {
        if (byteBuffer.alignmentOffset(i, i2) == 0) {
            return;
        }
        throw new IOException("Current location of the bytebuffer (" + i + ") is not a multiple of the block size (" + i2 + ")");
    }

    static void checkRemainingBufferSizeAligned(int i, int i2) throws IOException {
        if (i % i2 == 0) {
            return;
        }
        throw new IOException("Number of remaining bytes (" + i + ") is not a multiple of the block size (" + i2 + ")");
    }

    static void checkChannelPositionAligned(long j, int i) throws IOException {
        if (j % i == 0) {
            return;
        }
        throw new IOException("Channel position (" + j + ") is not a multiple of the block size (" + i + ")");
    }
}
