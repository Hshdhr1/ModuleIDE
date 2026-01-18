package java.util;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ImmutableCollections;

/* compiled from: ImmutableCollections.java */
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class CollSer implements Serializable {
    static final int IMM_LIST = 1;
    static final int IMM_MAP = 3;
    static final int IMM_SET = 2;
    private static final long serialVersionUID = 6309168927139932177L;
    private transient Object[] array;
    private final int tag;

    CollSer(int i, Object... objArr) {
        this.tag = i;
        this.array = objArr;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int readInt = objectInputStream.readInt();
        if (readInt < 0) {
            throw new InvalidObjectException("negative length " + readInt);
        }
        Object[] objArr = new Object[readInt];
        for (int i = 0; i < readInt; i++) {
            objArr[i] = objectInputStream.readObject();
        }
        this.array = objArr;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.array.length);
        int i = 0;
        while (true) {
            Object[] objArr = this.array;
            if (i >= objArr.length) {
                return;
            }
            objectOutputStream.writeObject(objArr[i]);
            i++;
        }
    }

    private Object readResolve() throws ObjectStreamException {
        try {
            Object[] objArr = this.array;
            if (objArr == null) {
                throw new InvalidObjectException("null array");
            }
            int i = this.tag & 255;
            if (i == 1) {
                return CollSer$$ExternalSyntheticBackport0.m(objArr);
            }
            if (i == 2) {
                return CollSer$$ExternalSyntheticBackport1.m(objArr);
            }
            if (i == 3) {
                if (objArr.length == 0) {
                    return ImmutableCollections.emptyMap();
                }
                if (objArr.length == 2) {
                    Object[] objArr2 = this.array;
                    return new ImmutableCollections.Map1(objArr2[0], objArr2[1]);
                }
                return new ImmutableCollections.MapN(this.array);
            }
            throw new InvalidObjectException(String.format("invalid flags 0x%x", Integer.valueOf(this.tag)));
        } catch (NullPointerException | IllegalArgumentException e) {
            InvalidObjectException invalidObjectException = new InvalidObjectException("invalid object");
            invalidObjectException.initCause(e);
            throw invalidObjectException;
        }
    }
}
