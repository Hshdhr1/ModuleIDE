package java.nio.charset;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class UnsupportedCharsetException extends IllegalArgumentException {
    private static final long serialVersionUID = 1490765524727386367L;
    private String charsetName;

    public UnsupportedCharsetException(String str) {
        super(String.valueOf(str));
        this.charsetName = str;
    }

    public String getCharsetName() {
        return this.charsetName;
    }
}
