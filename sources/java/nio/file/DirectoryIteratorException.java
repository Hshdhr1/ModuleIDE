package java.nio.file;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.ConcurrentModificationException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class DirectoryIteratorException extends ConcurrentModificationException {
    private static final long serialVersionUID = -6012699886086212874L;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DirectoryIteratorException(IOException iOException) {
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
