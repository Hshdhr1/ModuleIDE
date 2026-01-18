package sun.nio.cs;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class ThreadLocalCoders {
    private static final int CACHE_SIZE = 3;
    private static Cache decoderCache = new 1(3);
    private static Cache encoderCache = new 2(3);

    private static abstract class Cache {
        private ThreadLocal cache = new ThreadLocal();
        private final int size;

        abstract Object create(Object obj);

        abstract boolean hasName(Object obj, Object obj2);

        Cache(int i) {
            this.size = i;
        }

        private void moveToFront(Object[] objArr, int i) {
            Object obj = objArr[i];
            while (i > 0) {
                objArr[i] = objArr[i - 1];
                i--;
            }
            objArr[0] = obj;
        }

        Object forName(Object obj) {
            Object[] objArr = (Object[]) this.cache.get();
            if (objArr == null) {
                objArr = new Object[this.size];
                this.cache.set(objArr);
            } else {
                for (int i = 0; i < objArr.length; i++) {
                    Object obj2 = objArr[i];
                    if (obj2 != null && hasName(obj2, obj)) {
                        if (i > 0) {
                            moveToFront(objArr, i);
                        }
                        return obj2;
                    }
                }
            }
            Object create = create(obj);
            objArr[objArr.length - 1] = create;
            moveToFront(objArr, objArr.length - 1);
            return create;
        }
    }

    class 1 extends Cache {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        1(int i) {
            super(i);
        }

        boolean hasName(Object obj, Object obj2) {
            if (obj2 instanceof Charset) {
                return ((CharsetDecoder) obj).charset().equals(obj2);
            }
            if (obj2 instanceof String) {
                return ((CharsetDecoder) obj).charset().name().equals(obj2);
            }
            return false;
        }

        Object create(Object obj) {
            if (obj instanceof Charset) {
                return ((Charset) obj).newDecoder();
            }
            if (obj instanceof String) {
                return Charset.forName((String) obj).newDecoder();
            }
            return null;
        }
    }

    public static CharsetDecoder decoderFor(Object obj) {
        CharsetDecoder charsetDecoder = (CharsetDecoder) decoderCache.forName(obj);
        charsetDecoder.reset();
        return charsetDecoder;
    }

    class 2 extends Cache {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        2(int i) {
            super(i);
        }

        boolean hasName(Object obj, Object obj2) {
            if (obj2 instanceof Charset) {
                return ((CharsetEncoder) obj).charset().equals(obj2);
            }
            if (obj2 instanceof String) {
                return ((CharsetEncoder) obj).charset().name().equals(obj2);
            }
            return false;
        }

        Object create(Object obj) {
            if (obj instanceof Charset) {
                return ((Charset) obj).newEncoder();
            }
            if (obj instanceof String) {
                return Charset.forName((String) obj).newEncoder();
            }
            return null;
        }
    }

    public static CharsetEncoder encoderFor(Object obj) {
        CharsetEncoder charsetEncoder = (CharsetEncoder) encoderCache.forName(obj);
        charsetEncoder.reset();
        return charsetEncoder;
    }
}
