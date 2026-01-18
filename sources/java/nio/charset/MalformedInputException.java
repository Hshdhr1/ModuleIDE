package java.nio.charset;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class MalformedInputException extends CharacterCodingException {
    private static final long serialVersionUID = -3438823399834806194L;
    private int inputLength;

    public MalformedInputException(int i) {
        this.inputLength = i;
    }

    public int getInputLength() {
        return this.inputLength;
    }

    public String getMessage() {
        return "Input length = " + this.inputLength;
    }
}
