package java.nio.charset;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class UnmappableCharacterException extends CharacterCodingException {
    private static final long serialVersionUID = -7026962371537706123L;
    private int inputLength;

    public UnmappableCharacterException(int i) {
        this.inputLength = i;
    }

    public int getInputLength() {
        return this.inputLength;
    }

    public String getMessage() {
        return "Input length = " + this.inputLength;
    }
}
