package sun.nio.fs;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UnixMountEntry {
    private long dev;
    private byte[] dir;
    private byte[] fstype;
    private volatile String fstypeAsString;
    private byte[] name;
    private volatile String optionsAsString;
    private byte[] opts;

    UnixMountEntry() {
    }

    String name() {
        return Util.toString(this.name);
    }

    String fstype() {
        if (this.fstypeAsString == null) {
            this.fstypeAsString = Util.toString(this.fstype);
        }
        return this.fstypeAsString;
    }

    byte[] dir() {
        return this.dir;
    }

    long dev() {
        return this.dev;
    }

    boolean hasOption(String str) {
        if (this.optionsAsString == null) {
            this.optionsAsString = Util.toString(this.opts);
        }
        for (String str2 : Util.split(this.optionsAsString, ',')) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    boolean isIgnored() {
        return hasOption("ignore");
    }

    boolean isReadOnly() {
        return hasOption("ro");
    }
}
