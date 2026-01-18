package java.io;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class UncheckedIOException extends RuntimeException {
    private static final long serialVersionUID = -8134305061645241065L;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UncheckedIOException(String str, IOException iOException) {
        super(str, (Throwable) iOException);
        iOException.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UncheckedIOException(IOException iOException) {
        super((Throwable) iOException);
        iOException.getClass();
    }

    public IOException getCause() {
        return super.getCause();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (!(super.getCause() instanceof IOException)) {
            throw new InvalidObjectException("Cause must be an IOException");
        }
    }
}
