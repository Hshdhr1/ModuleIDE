package java.nio.file;

import java.io.IOException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class FileSystemException extends IOException {
    static final long serialVersionUID = -3055425747967319812L;
    private final String file;
    private final String other;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FileSystemException(String str) {
        super((String) null);
        this.file = str;
        this.other = null;
    }

    public FileSystemException(String str, String str2, String str3) {
        super(str3);
        this.file = str;
        this.other = str2;
    }

    public String getFile() {
        return this.file;
    }

    public String getOtherFile() {
        return this.other;
    }

    public String getReason() {
        return super.getMessage();
    }

    public String getMessage() {
        if (this.file == null && this.other == null) {
            return getReason();
        }
        StringBuilder sb = new StringBuilder();
        String str = this.file;
        if (str != null) {
            sb.append(str);
        }
        if (this.other != null) {
            sb.append(" -> ");
            sb.append(this.other);
        }
        if (getReason() != null) {
            sb.append(": ");
            sb.append(getReason());
        }
        return sb.toString();
    }
}
