package androidx.versionedparcelable;

import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.NetworkOnMainThreadException;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseBooleanArray;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes14.dex */
public abstract class VersionedParcel {
    private static final int EX_BAD_PARCELABLE = -2;
    private static final int EX_ILLEGAL_ARGUMENT = -3;
    private static final int EX_ILLEGAL_STATE = -5;
    private static final int EX_NETWORK_MAIN_THREAD = -6;
    private static final int EX_NULL_POINTER = -4;
    private static final int EX_PARCELABLE = -9;
    private static final int EX_SECURITY = -1;
    private static final int EX_UNSUPPORTED_OPERATION = -7;
    private static final String TAG = "VersionedParcel";
    private static final int TYPE_BINDER = 5;
    private static final int TYPE_FLOAT = 8;
    private static final int TYPE_INTEGER = 7;
    private static final int TYPE_PARCELABLE = 2;
    private static final int TYPE_SERIALIZABLE = 3;
    private static final int TYPE_STRING = 4;
    private static final int TYPE_VERSIONED_PARCELABLE = 1;
    protected final ArrayMap mParcelizerCache;
    protected final ArrayMap mReadCache;
    protected final ArrayMap mWriteCache;

    protected abstract void closeField();

    protected abstract VersionedParcel createSubParcel();

    protected abstract boolean readBoolean();

    protected abstract Bundle readBundle();

    protected abstract byte[] readByteArray();

    protected abstract CharSequence readCharSequence();

    protected abstract double readDouble();

    protected abstract boolean readField(int i);

    protected abstract float readFloat();

    protected abstract int readInt();

    protected abstract long readLong();

    protected abstract Parcelable readParcelable();

    protected abstract String readString();

    protected abstract IBinder readStrongBinder();

    protected abstract void setOutputField(int i);

    protected abstract void writeBoolean(boolean z);

    protected abstract void writeBundle(Bundle bundle);

    protected abstract void writeByteArray(byte[] bArr);

    protected abstract void writeByteArray(byte[] bArr, int i, int i2);

    protected abstract void writeCharSequence(CharSequence charSequence);

    protected abstract void writeDouble(double d);

    protected abstract void writeFloat(float f);

    protected abstract void writeInt(int i);

    protected abstract void writeLong(long j);

    protected abstract void writeParcelable(Parcelable parcelable);

    protected abstract void writeString(String str);

    protected abstract void writeStrongBinder(IBinder iBinder);

    protected abstract void writeStrongInterface(IInterface iInterface);

    public VersionedParcel(ArrayMap arrayMap, ArrayMap arrayMap2, ArrayMap arrayMap3) {
        this.mReadCache = arrayMap;
        this.mWriteCache = arrayMap2;
        this.mParcelizerCache = arrayMap3;
    }

    public boolean isStream() {
        return false;
    }

    public void setSerializationFlags(boolean allowSerialization, boolean ignoreParcelables) {
    }

    public void writeStrongInterface(IInterface val, int fieldId) {
        setOutputField(fieldId);
        writeStrongInterface(val);
    }

    public void writeBundle(Bundle val, int fieldId) {
        setOutputField(fieldId);
        writeBundle(val);
    }

    public void writeBoolean(boolean val, int fieldId) {
        setOutputField(fieldId);
        writeBoolean(val);
    }

    public void writeByteArray(byte[] b, int fieldId) {
        setOutputField(fieldId);
        writeByteArray(b);
    }

    public void writeByteArray(byte[] b, int offset, int len, int fieldId) {
        setOutputField(fieldId);
        writeByteArray(b, offset, len);
    }

    public void writeCharSequence(CharSequence val, int fieldId) {
        setOutputField(fieldId);
        writeCharSequence(val);
    }

    public void writeInt(int val, int fieldId) {
        setOutputField(fieldId);
        writeInt(val);
    }

    public void writeLong(long val, int fieldId) {
        setOutputField(fieldId);
        writeLong(val);
    }

    public void writeFloat(float val, int fieldId) {
        setOutputField(fieldId);
        writeFloat(val);
    }

    public void writeDouble(double val, int fieldId) {
        setOutputField(fieldId);
        writeDouble(val);
    }

    public void writeString(String val, int fieldId) {
        setOutputField(fieldId);
        writeString(val);
    }

    public void writeStrongBinder(IBinder val, int fieldId) {
        setOutputField(fieldId);
        writeStrongBinder(val);
    }

    public void writeParcelable(Parcelable p, int fieldId) {
        setOutputField(fieldId);
        writeParcelable(p);
    }

    public boolean readBoolean(boolean def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readBoolean();
    }

    public int readInt(int def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readInt();
    }

    public long readLong(long def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readLong();
    }

    public float readFloat(float def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readFloat();
    }

    public double readDouble(double def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readDouble();
    }

    public String readString(String def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readString();
    }

    public IBinder readStrongBinder(IBinder def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readStrongBinder();
    }

    public byte[] readByteArray(byte[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readByteArray();
    }

    public Parcelable readParcelable(Parcelable parcelable, int fieldId) {
        if (!readField(fieldId)) {
            return parcelable;
        }
        return readParcelable();
    }

    public Bundle readBundle(Bundle def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readBundle();
    }

    public void writeByte(byte val, int fieldId) {
        setOutputField(fieldId);
        writeInt(val);
    }

    public void writeSize(Size val, int fieldId) {
        setOutputField(fieldId);
        writeBoolean(val != null);
        if (val != null) {
            writeInt(val.getWidth());
            writeInt(val.getHeight());
        }
    }

    public void writeSizeF(SizeF val, int fieldId) {
        setOutputField(fieldId);
        writeBoolean(val != null);
        if (val != null) {
            writeFloat(val.getWidth());
            writeFloat(val.getHeight());
        }
    }

    public void writeSparseBooleanArray(SparseBooleanArray val, int fieldId) {
        setOutputField(fieldId);
        if (val == null) {
            writeInt(-1);
            return;
        }
        int n = val.size();
        writeInt(n);
        for (int i = 0; i < n; i++) {
            writeInt(val.keyAt(i));
            writeBoolean(val.valueAt(i));
        }
    }

    public void writeBooleanArray(boolean[] val, int fieldId) {
        setOutputField(fieldId);
        writeBooleanArray(val);
    }

    protected void writeBooleanArray(boolean[] zArr) {
        if (zArr != null) {
            writeInt(zArr.length);
            for (boolean z : zArr) {
                writeInt(z ? 1 : 0);
            }
            return;
        }
        writeInt(-1);
    }

    public boolean[] readBooleanArray(boolean[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readBooleanArray();
    }

    protected boolean[] readBooleanArray() {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        boolean[] val = new boolean[n];
        for (int i = 0; i < n; i++) {
            val[i] = readInt() != 0;
        }
        return val;
    }

    public void writeCharArray(char[] val, int fieldId) {
        setOutputField(fieldId);
        if (val != null) {
            int n = val.length;
            writeInt(n);
            for (char c : val) {
                writeInt(c);
            }
            return;
        }
        writeInt(-1);
    }

    public CharSequence readCharSequence(CharSequence def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readCharSequence();
    }

    public char[] readCharArray(char[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        int n = readInt();
        if (n < 0) {
            return null;
        }
        char[] val = new char[n];
        for (int i = 0; i < n; i++) {
            val[i] = (char) readInt();
        }
        return val;
    }

    public void writeIntArray(int[] val, int fieldId) {
        setOutputField(fieldId);
        writeIntArray(val);
    }

    protected void writeIntArray(int[] val) {
        if (val != null) {
            int n = val.length;
            writeInt(n);
            for (int i : val) {
                writeInt(i);
            }
            return;
        }
        writeInt(-1);
    }

    public int[] readIntArray(int[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readIntArray();
    }

    protected int[] readIntArray() {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        int[] val = new int[n];
        for (int i = 0; i < n; i++) {
            val[i] = readInt();
        }
        return val;
    }

    public void writeLongArray(long[] val, int fieldId) {
        setOutputField(fieldId);
        writeLongArray(val);
    }

    protected void writeLongArray(long[] val) {
        if (val != null) {
            int n = val.length;
            writeInt(n);
            for (long j : val) {
                writeLong(j);
            }
            return;
        }
        writeInt(-1);
    }

    public long[] readLongArray(long[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readLongArray();
    }

    protected long[] readLongArray() {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        long[] val = new long[n];
        for (int i = 0; i < n; i++) {
            val[i] = readLong();
        }
        return val;
    }

    public void writeFloatArray(float[] val, int fieldId) {
        setOutputField(fieldId);
        writeFloatArray(val);
    }

    protected void writeFloatArray(float[] val) {
        if (val != null) {
            int n = val.length;
            writeInt(n);
            for (float f : val) {
                writeFloat(f);
            }
            return;
        }
        writeInt(-1);
    }

    public float[] readFloatArray(float[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readFloatArray();
    }

    protected float[] readFloatArray() {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        float[] val = new float[n];
        for (int i = 0; i < n; i++) {
            val[i] = readFloat();
        }
        return val;
    }

    public void writeDoubleArray(double[] val, int fieldId) {
        setOutputField(fieldId);
        writeDoubleArray(val);
    }

    protected void writeDoubleArray(double[] val) {
        if (val != null) {
            int n = val.length;
            writeInt(n);
            for (double d : val) {
                writeDouble(d);
            }
            return;
        }
        writeInt(-1);
    }

    public double[] readDoubleArray(double[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readDoubleArray();
    }

    protected double[] readDoubleArray() {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        double[] val = new double[n];
        for (int i = 0; i < n; i++) {
            val[i] = readDouble();
        }
        return val;
    }

    public void writeSet(Set set, int fieldId) {
        writeCollection(set, fieldId);
    }

    public void writeList(List list, int fieldId) {
        writeCollection(list, fieldId);
    }

    public void writeMap(Map map, int fieldId) {
        setOutputField(fieldId);
        if (map == null) {
            writeInt(-1);
            return;
        }
        int size = map.size();
        writeInt(size);
        if (size == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry entry : map.entrySet()) {
            arrayList.add(entry.getKey());
            arrayList2.add(entry.getValue());
        }
        writeCollection(arrayList);
        writeCollection(arrayList2);
    }

    private void writeCollection(Collection collection, int fieldId) {
        setOutputField(fieldId);
        writeCollection(collection);
    }

    private void writeCollection(Collection collection) {
        if (collection == null) {
            writeInt(-1);
        }
        int n = collection.size();
        writeInt(n);
        if (n > 0) {
            int type = getType(collection.iterator().next());
            writeInt(type);
            switch (type) {
                case 1:
                    Iterator it = collection.iterator();
                    while (it.hasNext()) {
                        writeVersionedParcelable((VersionedParcelable) it.next());
                    }
                    break;
                case 2:
                    Iterator it2 = collection.iterator();
                    while (it2.hasNext()) {
                        writeParcelable((Parcelable) it2.next());
                    }
                    break;
                case 3:
                    Iterator it3 = collection.iterator();
                    while (it3.hasNext()) {
                        writeSerializable((Serializable) it3.next());
                    }
                    break;
                case 4:
                    Iterator it4 = collection.iterator();
                    while (it4.hasNext()) {
                        writeString((String) it4.next());
                    }
                    break;
                case 5:
                    Iterator it5 = collection.iterator();
                    while (it5.hasNext()) {
                        writeStrongBinder((IBinder) it5.next());
                    }
                    break;
                case 7:
                    Iterator it6 = collection.iterator();
                    while (it6.hasNext()) {
                        writeInt(((Integer) it6.next()).intValue());
                    }
                    break;
                case 8:
                    Iterator it7 = collection.iterator();
                    while (it7.hasNext()) {
                        writeFloat(((Float) it7.next()).floatValue());
                    }
                    break;
            }
        }
    }

    public void writeArray(Object[] objArr, int fieldId) {
        setOutputField(fieldId);
        writeArray(objArr);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    protected void writeArray(Object[] objArr) {
        if (objArr == null) {
            writeInt(-1);
        }
        int n = objArr.length;
        int i = 0;
        writeInt(n);
        if (n > 0) {
            int type = getType(objArr[0]);
            writeInt(type);
            switch (type) {
                case 1:
                    while (i < n) {
                        writeVersionedParcelable((VersionedParcelable) objArr[i]);
                        i++;
                    }
                    break;
                case 2:
                    while (i < n) {
                        writeParcelable((Parcelable) objArr[i]);
                        i++;
                    }
                    break;
                case 3:
                    while (i < n) {
                        writeSerializable((Serializable) objArr[i]);
                        i++;
                    }
                    break;
                case 4:
                    while (i < n) {
                        writeString((String) objArr[i]);
                        i++;
                    }
                    break;
                case 5:
                    while (i < n) {
                        writeStrongBinder((IBinder) objArr[i]);
                        i++;
                    }
                    break;
            }
        }
    }

    private int getType(Object obj) {
        if (obj instanceof String) {
            return 4;
        }
        if (obj instanceof Parcelable) {
            return 2;
        }
        if (obj instanceof VersionedParcelable) {
            return 1;
        }
        if (obj instanceof Serializable) {
            return 3;
        }
        if (obj instanceof IBinder) {
            return 5;
        }
        if (obj instanceof Integer) {
            return 7;
        }
        if (obj instanceof Float) {
            return 8;
        }
        throw new IllegalArgumentException(obj.getClass().getName() + " cannot be VersionedParcelled");
    }

    public void writeVersionedParcelable(VersionedParcelable p, int fieldId) {
        setOutputField(fieldId);
        writeVersionedParcelable(p);
    }

    protected void writeVersionedParcelable(VersionedParcelable p) {
        if (p == null) {
            writeString(null);
            return;
        }
        writeVersionedParcelableCreator(p);
        VersionedParcel subParcel = createSubParcel();
        writeToParcel(p, subParcel);
        subParcel.closeField();
    }

    private void writeVersionedParcelableCreator(VersionedParcelable p) {
        try {
            Class name = findParcelClass(p.getClass());
            writeString(name.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(p.getClass().getSimpleName() + " does not have a Parcelizer", e);
        }
    }

    public void writeSerializable(Serializable s, int fieldId) {
        setOutputField(fieldId);
        writeSerializable(s);
    }

    private void writeSerializable(Serializable s) {
        if (s == null) {
            writeString(null);
            return;
        }
        String name = s.getClass().getName();
        writeString(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(s);
            oos.close();
            writeByteArray(baos.toByteArray());
        } catch (IOException ioe) {
            throw new RuntimeException("VersionedParcelable encountered IOException writing serializable object (name = " + name + ")", ioe);
        }
    }

    public void writeException(Exception e, int fieldId) {
        setOutputField(fieldId);
        if (e == null) {
            writeNoException();
            return;
        }
        int code = 0;
        if ((e instanceof Parcelable) && e.getClass().getClassLoader() == Parcelable.class.getClassLoader()) {
            code = -9;
        } else if (e instanceof SecurityException) {
            code = -1;
        } else if (e instanceof BadParcelableException) {
            code = -2;
        } else if (e instanceof IllegalArgumentException) {
            code = -3;
        } else if (e instanceof NullPointerException) {
            code = -4;
        } else if (e instanceof IllegalStateException) {
            code = -5;
        } else if (e instanceof NetworkOnMainThreadException) {
            code = -6;
        } else if (e instanceof UnsupportedOperationException) {
            code = -7;
        }
        writeInt(code);
        if (code == 0) {
            if (e instanceof RuntimeException) {
                throw ((RuntimeException) e);
            }
            throw new RuntimeException(e);
        }
        writeString(e.getMessage());
        switch (code) {
            case -9:
                writeParcelable((Parcelable) e);
                return;
            default:
                return;
        }
    }

    protected void writeNoException() {
        writeInt(0);
    }

    public Exception readException(Exception def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        int code = readExceptionCode();
        if (code != 0) {
            String msg = readString();
            return readException(code, msg);
        }
        return def;
    }

    private int readExceptionCode() {
        int code = readInt();
        return code;
    }

    private Exception readException(int code, String msg) {
        Exception e = createException(code, msg);
        return e;
    }

    protected static Throwable getRootCause(Throwable t) {
        while (t.getCause() != null) {
            t = t.getCause();
        }
        return t;
    }

    private Exception createException(int code, String msg) {
        switch (code) {
            case -9:
                return readParcelable();
            case -8:
            default:
                return new RuntimeException("Unknown exception code: " + code + " msg " + msg);
            case -7:
                return new UnsupportedOperationException(msg);
            case -6:
                return new NetworkOnMainThreadException();
            case -5:
                return new IllegalStateException(msg);
            case -4:
                return new NullPointerException(msg);
            case -3:
                return new IllegalArgumentException(msg);
            case -2:
                return new BadParcelableException(msg);
            case -1:
                return new SecurityException(msg);
        }
    }

    public byte readByte(byte def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return (byte) (readInt() & 255);
    }

    public Size readSize(Size def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        if (readBoolean()) {
            int width = readInt();
            int height = readInt();
            return new Size(width, height);
        }
        return null;
    }

    public SizeF readSizeF(SizeF def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        if (readBoolean()) {
            float width = readFloat();
            float height = readFloat();
            return new SizeF(width, height);
        }
        return null;
    }

    public SparseBooleanArray readSparseBooleanArray(SparseBooleanArray def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        int n = readInt();
        if (n < 0) {
            return null;
        }
        SparseBooleanArray sa = new SparseBooleanArray(n);
        for (int i = 0; i < n; i++) {
            sa.put(readInt(), readBoolean());
        }
        return sa;
    }

    public Set readSet(Set set, int fieldId) {
        if (!readField(fieldId)) {
            return set;
        }
        return readCollection(new ArraySet());
    }

    public List readList(List list, int fieldId) {
        if (!readField(fieldId)) {
            return list;
        }
        return readCollection(new ArrayList());
    }

    private Collection readCollection(Collection collection) {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        if (n != 0) {
            int type = readInt();
            if (n < 0) {
                return null;
            }
            switch (type) {
                case 1:
                    while (n > 0) {
                        collection.add(readVersionedParcelable());
                        n--;
                    }
                    break;
                case 2:
                    while (n > 0) {
                        collection.add(readParcelable());
                        n--;
                    }
                    break;
                case 3:
                    while (n > 0) {
                        collection.add(readSerializable());
                        n--;
                    }
                    break;
                case 4:
                    while (n > 0) {
                        collection.add(readString());
                        n--;
                    }
                    break;
                case 5:
                    while (n > 0) {
                        collection.add(readStrongBinder());
                        n--;
                    }
                    break;
            }
        }
        return collection;
    }

    public Map readMap(Map map, int fieldId) {
        if (!readField(fieldId)) {
            return map;
        }
        int size = readInt();
        if (size < 0) {
            return null;
        }
        ArrayMap arrayMap = new ArrayMap();
        if (size == 0) {
            return arrayMap;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        readCollection(arrayList);
        readCollection(arrayList2);
        for (int i = 0; i < size; i++) {
            arrayMap.put(arrayList.get(i), arrayList2.get(i));
        }
        return arrayMap;
    }

    public Object[] readArray(Object[] objArr, int fieldId) {
        if (!readField(fieldId)) {
            return objArr;
        }
        return readArray(objArr);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    protected Object[] readArray(Object[] objArr) {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(n);
        if (n != 0) {
            int type = readInt();
            if (n < 0) {
                return null;
            }
            switch (type) {
                case 1:
                    while (n > 0) {
                        arrayList.add(readVersionedParcelable());
                        n--;
                    }
                    break;
                case 2:
                    while (n > 0) {
                        arrayList.add(readParcelable());
                        n--;
                    }
                    break;
                case 3:
                    while (n > 0) {
                        arrayList.add(readSerializable());
                        n--;
                    }
                    break;
                case 4:
                    while (n > 0) {
                        arrayList.add(readString());
                        n--;
                    }
                    break;
                case 5:
                    while (n > 0) {
                        arrayList.add(readStrongBinder());
                        n--;
                    }
                    break;
            }
        }
        return arrayList.toArray(objArr);
    }

    public VersionedParcelable readVersionedParcelable(VersionedParcelable versionedParcelable, int fieldId) {
        if (!readField(fieldId)) {
            return versionedParcelable;
        }
        return readVersionedParcelable();
    }

    protected VersionedParcelable readVersionedParcelable() {
        String name = readString();
        if (name == null) {
            return null;
        }
        return readFromParcel(name, createSubParcel());
    }

    protected Serializable readSerializable() {
        String name = readString();
        if (name == null) {
            return null;
        }
        byte[] serializedData = readByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(serializedData);
        try {
            ObjectInputStream ois = new 1(bais);
            return (Serializable) ois.readObject();
        } catch (IOException ioe) {
            throw new RuntimeException("VersionedParcelable encountered IOException reading a Serializable object (name = " + name + ")", ioe);
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException("VersionedParcelable encountered ClassNotFoundException reading a Serializable object (name = " + name + ")", cnfe);
        }
    }

    class 1 extends ObjectInputStream {
        1(InputStream x0) {
            super(x0);
        }

        protected Class resolveClass(ObjectStreamClass osClass) throws IOException, ClassNotFoundException {
            Class<?> c = Class.forName(osClass.getName(), false, getClass().getClassLoader());
            if (c != null) {
                return c;
            }
            return super.resolveClass(osClass);
        }
    }

    protected VersionedParcelable readFromParcel(String parcelCls, VersionedParcel versionedParcel) {
        try {
            Method m = getReadMethod(parcelCls);
            return (VersionedParcelable) m.invoke((Object) null, new Object[]{versionedParcel});
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof RuntimeException) {
                throw e.getCause();
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e);
        } catch (NoSuchMethodException e2) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e2);
        } catch (ClassNotFoundException e3) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e3);
        } catch (IllegalAccessException e4) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e4);
        }
    }

    protected void writeToParcel(VersionedParcelable versionedParcelable, VersionedParcel versionedParcel) {
        try {
            Method m = getWriteMethod(versionedParcelable.getClass());
            m.invoke((Object) null, new Object[]{versionedParcelable, versionedParcel});
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
        } catch (InvocationTargetException e4) {
            if (e4.getCause() instanceof RuntimeException) {
                throw e4.getCause();
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e4);
        }
    }

    private Method getReadMethod(String parcelCls) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        Method m = (Method) this.mReadCache.get(parcelCls);
        if (m == null) {
            System.currentTimeMillis();
            Class cls = Class.forName(parcelCls, true, VersionedParcel.class.getClassLoader());
            Method m2 = cls.getDeclaredMethod("read", new Class[]{VersionedParcel.class});
            this.mReadCache.put(parcelCls, m2);
            return m2;
        }
        return m;
    }

    private Method getWriteMethod(Class baseCls) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        Method m = (Method) this.mWriteCache.get(baseCls.getName());
        if (m == null) {
            Class cls = findParcelClass(baseCls);
            System.currentTimeMillis();
            Method m2 = cls.getDeclaredMethod("write", new Class[]{baseCls, VersionedParcel.class});
            this.mWriteCache.put(baseCls.getName(), m2);
            return m2;
        }
        return m;
    }

    private Class findParcelClass(Class cls) throws ClassNotFoundException {
        Class ret = (Class) this.mParcelizerCache.get(cls.getName());
        if (ret == null) {
            String pkg = cls.getPackage().getName();
            String c = String.format("%s.%sParcelizer", new Object[]{pkg, cls.getSimpleName()});
            Class ret2 = Class.forName(c, false, cls.getClassLoader());
            this.mParcelizerCache.put(cls.getName(), ret2);
            return ret2;
        }
        return ret;
    }

    public static class ParcelException extends RuntimeException {
        public ParcelException(Throwable source) {
            super(source);
        }
    }
}
