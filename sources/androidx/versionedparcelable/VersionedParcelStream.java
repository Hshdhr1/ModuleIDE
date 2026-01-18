package androidx.versionedparcelable;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcelable;
import androidx.collection.ArrayMap;
import androidx.versionedparcelable.VersionedParcel;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes14.dex */
class VersionedParcelStream extends VersionedParcel {
    private static final int TYPE_BOOLEAN = 5;
    private static final int TYPE_BOOLEAN_ARRAY = 6;
    private static final int TYPE_DOUBLE = 7;
    private static final int TYPE_DOUBLE_ARRAY = 8;
    private static final int TYPE_FLOAT = 13;
    private static final int TYPE_FLOAT_ARRAY = 14;
    private static final int TYPE_INT = 9;
    private static final int TYPE_INT_ARRAY = 10;
    private static final int TYPE_LONG = 11;
    private static final int TYPE_LONG_ARRAY = 12;
    private static final int TYPE_NULL = 0;
    private static final int TYPE_STRING = 3;
    private static final int TYPE_STRING_ARRAY = 4;
    private static final int TYPE_SUB_BUNDLE = 1;
    private static final int TYPE_SUB_PERSISTABLE_BUNDLE = 2;
    private static final Charset UTF_16 = Charset.forName("UTF-16");
    int mCount;
    private DataInputStream mCurrentInput;
    private DataOutputStream mCurrentOutput;
    private FieldBuffer mFieldBuffer;
    private int mFieldId;
    int mFieldSize;
    private boolean mIgnoreParcelables;
    private final DataInputStream mMasterInput;
    private final DataOutputStream mMasterOutput;

    public VersionedParcelStream(InputStream input, OutputStream output) {
        this(input, output, new ArrayMap(), new ArrayMap(), new ArrayMap());
    }

    class 1 extends FilterInputStream {
        1(InputStream x0) {
            super(x0);
        }

        public int read() throws IOException {
            if (VersionedParcelStream.this.mFieldSize != -1 && VersionedParcelStream.this.mCount >= VersionedParcelStream.this.mFieldSize) {
                throw new IOException();
            }
            int read = super.read();
            VersionedParcelStream.this.mCount++;
            return read;
        }

        public int read(byte[] b, int off, int len) throws IOException {
            if (VersionedParcelStream.this.mFieldSize != -1 && VersionedParcelStream.this.mCount >= VersionedParcelStream.this.mFieldSize) {
                throw new IOException();
            }
            int read = super.read(b, off, len);
            if (read > 0) {
                VersionedParcelStream.this.mCount += read;
            }
            return read;
        }

        public long skip(long n) throws IOException {
            if (VersionedParcelStream.this.mFieldSize != -1 && VersionedParcelStream.this.mCount >= VersionedParcelStream.this.mFieldSize) {
                throw new IOException();
            }
            long skip = super.skip(n);
            if (skip > 0) {
                VersionedParcelStream.this.mCount += (int) skip;
            }
            return skip;
        }
    }

    private VersionedParcelStream(InputStream input, OutputStream output, ArrayMap arrayMap, ArrayMap arrayMap2, ArrayMap arrayMap3) {
        super(arrayMap, arrayMap2, arrayMap3);
        this.mCount = 0;
        this.mFieldId = -1;
        this.mFieldSize = -1;
        DataInputStream dataInputStream = input != null ? new DataInputStream(new 1(input)) : null;
        this.mMasterInput = dataInputStream;
        DataOutputStream dataOutputStream = output != null ? new DataOutputStream(output) : null;
        this.mMasterOutput = dataOutputStream;
        this.mCurrentInput = dataInputStream;
        this.mCurrentOutput = dataOutputStream;
    }

    public boolean isStream() {
        return true;
    }

    public void setSerializationFlags(boolean allowSerialization, boolean ignoreParcelables) {
        if (!allowSerialization) {
            throw new RuntimeException("Serialization of this object is not allowed");
        }
        this.mIgnoreParcelables = ignoreParcelables;
    }

    public void closeField() {
        FieldBuffer fieldBuffer = this.mFieldBuffer;
        if (fieldBuffer != null) {
            try {
                if (fieldBuffer.mOutput.size() != 0) {
                    this.mFieldBuffer.flushField();
                }
                this.mFieldBuffer = null;
            } catch (IOException e) {
                throw new VersionedParcel.ParcelException(e);
            }
        }
    }

    protected VersionedParcel createSubParcel() {
        return new VersionedParcelStream(this.mCurrentInput, this.mCurrentOutput, this.mReadCache, this.mWriteCache, this.mParcelizerCache);
    }

    public boolean readField(int fieldId) {
        while (true) {
            try {
                int i = this.mFieldId;
                if (i == fieldId) {
                    return true;
                }
                if (String.valueOf(i).compareTo(String.valueOf(fieldId)) > 0) {
                    return false;
                }
                if (this.mCount < this.mFieldSize) {
                    this.mMasterInput.skip(r2 - r1);
                }
                this.mFieldSize = -1;
                int fieldInfo = this.mMasterInput.readInt();
                this.mCount = 0;
                int size = fieldInfo & 65535;
                if (size == 65535) {
                    size = this.mMasterInput.readInt();
                }
                int id = 65535 & (fieldInfo >> 16);
                this.mFieldId = id;
                this.mFieldSize = size;
            } catch (IOException e) {
                return false;
            }
        }
    }

    public void setOutputField(int fieldId) {
        closeField();
        FieldBuffer fieldBuffer = new FieldBuffer(fieldId, this.mMasterOutput);
        this.mFieldBuffer = fieldBuffer;
        this.mCurrentOutput = fieldBuffer.mDataStream;
    }

    public void writeByteArray(byte[] b) {
        try {
            if (b != null) {
                this.mCurrentOutput.writeInt(b.length);
                this.mCurrentOutput.write(b);
            } else {
                this.mCurrentOutput.writeInt(-1);
            }
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    public void writeByteArray(byte[] b, int offset, int len) {
        try {
            if (b != null) {
                this.mCurrentOutput.writeInt(len);
                this.mCurrentOutput.write(b, offset, len);
            } else {
                this.mCurrentOutput.writeInt(-1);
            }
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    protected void writeCharSequence(CharSequence charSequence) {
        if (!this.mIgnoreParcelables) {
            throw new RuntimeException("CharSequence cannot be written to an OutputStream");
        }
    }

    public void writeInt(int val) {
        try {
            this.mCurrentOutput.writeInt(val);
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    public void writeLong(long val) {
        try {
            this.mCurrentOutput.writeLong(val);
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    public void writeFloat(float val) {
        try {
            this.mCurrentOutput.writeFloat(val);
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    public void writeDouble(double val) {
        try {
            this.mCurrentOutput.writeDouble(val);
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    public void writeString(String val) {
        try {
            if (val != null) {
                byte[] bytes = val.getBytes(UTF_16);
                this.mCurrentOutput.writeInt(bytes.length);
                this.mCurrentOutput.write(bytes);
            } else {
                this.mCurrentOutput.writeInt(-1);
            }
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    public void writeBoolean(boolean val) {
        try {
            this.mCurrentOutput.writeBoolean(val);
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    public void writeStrongBinder(IBinder val) {
        if (!this.mIgnoreParcelables) {
            throw new RuntimeException("Binders cannot be written to an OutputStream");
        }
    }

    public void writeParcelable(Parcelable p) {
        if (!this.mIgnoreParcelables) {
            throw new RuntimeException("Parcelables cannot be written to an OutputStream");
        }
    }

    public void writeStrongInterface(IInterface val) {
        if (!this.mIgnoreParcelables) {
            throw new RuntimeException("Binders cannot be written to an OutputStream");
        }
    }

    public IBinder readStrongBinder() {
        return null;
    }

    public Parcelable readParcelable() {
        return null;
    }

    public int readInt() {
        try {
            return this.mCurrentInput.readInt();
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    public long readLong() {
        try {
            return this.mCurrentInput.readLong();
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    public float readFloat() {
        try {
            return this.mCurrentInput.readFloat();
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    public double readDouble() {
        try {
            return this.mCurrentInput.readDouble();
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    public String readString() {
        try {
            int len = this.mCurrentInput.readInt();
            if (len > 0) {
                byte[] bytes = new byte[len];
                this.mCurrentInput.readFully(bytes);
                return new String(bytes, UTF_16);
            }
            return null;
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    public byte[] readByteArray() {
        try {
            int len = this.mCurrentInput.readInt();
            if (len > 0) {
                byte[] bytes = new byte[len];
                this.mCurrentInput.readFully(bytes);
                return bytes;
            }
            return null;
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    protected CharSequence readCharSequence() {
        return null;
    }

    public boolean readBoolean() {
        try {
            return this.mCurrentInput.readBoolean();
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    public void writeBundle(Bundle val) {
        try {
            if (val != null) {
                Set<String> keys = val.keySet();
                this.mCurrentOutput.writeInt(keys.size());
                for (String key : keys) {
                    writeString(key);
                    Object o = val.get(key);
                    writeObject(o);
                }
                return;
            }
            this.mCurrentOutput.writeInt(-1);
        } catch (IOException e) {
            throw new VersionedParcel.ParcelException(e);
        }
    }

    public Bundle readBundle() {
        int size = readInt();
        if (size < 0) {
            return null;
        }
        Bundle b = new Bundle();
        for (int i = 0; i < size; i++) {
            String key = readString();
            readObject(readInt(), key, b);
        }
        return b;
    }

    private void writeObject(Object o) {
        if (o == null) {
            writeInt(0);
            return;
        }
        if (o instanceof Bundle) {
            writeInt(1);
            writeBundle((Bundle) o);
            return;
        }
        if (o instanceof String) {
            writeInt(3);
            writeString((String) o);
            return;
        }
        if (o instanceof String[]) {
            writeInt(4);
            writeArray((String[]) o);
            return;
        }
        if (o instanceof Boolean) {
            writeInt(5);
            writeBoolean(((Boolean) o).booleanValue());
            return;
        }
        if (o instanceof boolean[]) {
            writeInt(6);
            writeBooleanArray((boolean[]) o);
            return;
        }
        if (o instanceof Double) {
            writeInt(7);
            writeDouble(((Double) o).doubleValue());
            return;
        }
        if (o instanceof double[]) {
            writeInt(8);
            writeDoubleArray((double[]) o);
            return;
        }
        if (o instanceof Integer) {
            writeInt(9);
            writeInt(((Integer) o).intValue());
            return;
        }
        if (o instanceof int[]) {
            writeInt(10);
            writeIntArray((int[]) o);
            return;
        }
        if (o instanceof Long) {
            writeInt(11);
            writeLong(((Long) o).longValue());
            return;
        }
        if (o instanceof long[]) {
            writeInt(12);
            writeLongArray((long[]) o);
            return;
        }
        if (o instanceof Float) {
            writeInt(13);
            writeFloat(((Float) o).floatValue());
        } else if (o instanceof float[]) {
            writeInt(14);
            writeFloatArray((float[]) o);
        } else {
            throw new IllegalArgumentException("Unsupported type " + o.getClass());
        }
    }

    private void readObject(int type, String key, Bundle b) {
        switch (type) {
            case 0:
                b.putParcelable(key, (Parcelable) null);
                return;
            case 1:
                b.putBundle(key, readBundle());
                return;
            case 2:
                b.putBundle(key, readBundle());
                return;
            case 3:
                b.putString(key, readString());
                return;
            case 4:
                b.putStringArray(key, (String[]) readArray(new String[0]));
                return;
            case 5:
                b.putBoolean(key, readBoolean());
                return;
            case 6:
                b.putBooleanArray(key, readBooleanArray());
                return;
            case 7:
                b.putDouble(key, readDouble());
                return;
            case 8:
                b.putDoubleArray(key, readDoubleArray());
                return;
            case 9:
                b.putInt(key, readInt());
                return;
            case 10:
                b.putIntArray(key, readIntArray());
                return;
            case 11:
                b.putLong(key, readLong());
                return;
            case 12:
                b.putLongArray(key, readLongArray());
                return;
            case 13:
                b.putFloat(key, readFloat());
                return;
            case 14:
                b.putFloatArray(key, readFloatArray());
                return;
            default:
                throw new RuntimeException("Unknown type " + type);
        }
    }

    private static class FieldBuffer {
        final DataOutputStream mDataStream;
        private final int mFieldId;
        final ByteArrayOutputStream mOutput;
        private final DataOutputStream mTarget;

        FieldBuffer(int fieldId, DataOutputStream target) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            this.mOutput = byteArrayOutputStream;
            this.mDataStream = new DataOutputStream(byteArrayOutputStream);
            this.mFieldId = fieldId;
            this.mTarget = target;
        }

        void flushField() throws IOException {
            this.mDataStream.flush();
            int size = this.mOutput.size();
            int fieldInfo = (this.mFieldId << 16) | (size >= 65535 ? 65535 : size);
            this.mTarget.writeInt(fieldInfo);
            if (size >= 65535) {
                this.mTarget.writeInt(size);
            }
            this.mOutput.writeTo(this.mTarget);
        }
    }
}
