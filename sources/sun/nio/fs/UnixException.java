package sun.nio.fs;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.NoSuchFileException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UnixException extends Exception {
    static final long serialVersionUID = 7227016794320723218L;
    private int errno;
    private String msg;

    public Throwable fillInStackTrace() {
        return this;
    }

    UnixException(int i) {
        this.errno = i;
        this.msg = null;
    }

    UnixException(String str) {
        this.errno = 0;
        this.msg = str;
    }

    int errno() {
        return this.errno;
    }

    void setError(int i) {
        this.errno = i;
        this.msg = null;
    }

    String errorString() {
        String str = this.msg;
        return str != null ? str : Util.toString(UnixNativeDispatcher.strerror(errno()));
    }

    public String getMessage() {
        return errorString();
    }

    private IOException translateToIOException(String str, String str2) {
        if (this.msg != null) {
            return new IOException(this.msg);
        }
        if (errno() == 13) {
            return new AccessDeniedException(str, str2, null);
        }
        if (errno() == 2) {
            return new NoSuchFileException(str, str2, null);
        }
        if (errno() == 17) {
            return new FileAlreadyExistsException(str, str2, null);
        }
        if (errno() == 40) {
            return new FileSystemException(str, str2, errorString() + " or unable to access attributes of symbolic link");
        }
        return new FileSystemException(str, str2, errorString());
    }

    void rethrowAsIOException(String str) throws IOException {
        throw translateToIOException(str, null);
    }

    void rethrowAsIOException(UnixPath unixPath, UnixPath unixPath2) throws IOException {
        throw translateToIOException(unixPath == null ? null : unixPath.getPathForExceptionMessage(), unixPath2 != null ? unixPath2.getPathForExceptionMessage() : null);
    }

    void rethrowAsIOException(UnixPath unixPath) throws IOException {
        rethrowAsIOException(unixPath, null);
    }

    IOException asIOException(UnixPath unixPath) {
        return translateToIOException(unixPath.getPathForExceptionMessage(), null);
    }
}
