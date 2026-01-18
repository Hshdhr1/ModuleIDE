package sun.nio.fs;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UnixFileStoreAttributes {
    private long f_bavail;
    private long f_bfree;
    private long f_blocks;
    private long f_frsize;

    private UnixFileStoreAttributes() {
    }

    static UnixFileStoreAttributes get(UnixPath unixPath) throws UnixException {
        UnixFileStoreAttributes unixFileStoreAttributes = new UnixFileStoreAttributes();
        UnixNativeDispatcher.statvfs(unixPath, unixFileStoreAttributes);
        return unixFileStoreAttributes;
    }

    long blockSize() {
        return this.f_frsize;
    }

    long totalBlocks() {
        return this.f_blocks;
    }

    long freeBlocks() {
        return this.f_bfree;
    }

    long availableBlocks() {
        return this.f_bavail;
    }
}
