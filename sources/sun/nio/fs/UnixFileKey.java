package sun.nio.fs;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UnixFileKey {
    private final long st_dev;
    private final long st_ino;

    UnixFileKey(long j, long j2) {
        this.st_dev = j;
        this.st_ino = j2;
    }

    public int hashCode() {
        long j = this.st_dev;
        int i = (int) (j ^ (j >>> 32));
        long j2 = this.st_ino;
        return i + ((int) (j2 ^ (j2 >>> 32)));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UnixFileKey)) {
            return false;
        }
        UnixFileKey unixFileKey = (UnixFileKey) obj;
        return this.st_dev == unixFileKey.st_dev && this.st_ino == unixFileKey.st_ino;
    }

    public String toString() {
        return "(dev=" + Long.toHexString(this.st_dev) + ",ino=" + this.st_ino + ')';
    }
}
