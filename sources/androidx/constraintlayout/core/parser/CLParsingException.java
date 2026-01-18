package androidx.constraintlayout.core.parser;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes40.dex */
public class CLParsingException extends Exception {
    private final String mElementClass;
    private final int mLineNumber;
    private final String mReason;

    public CLParsingException(String reason, CLElement element) {
        this.mReason = reason;
        if (element != null) {
            this.mElementClass = element.getStrClass();
            this.mLineNumber = element.getLine();
        } else {
            this.mElementClass = "unknown";
            this.mLineNumber = 0;
        }
    }

    public String reason() {
        return this.mReason + " (" + this.mElementClass + " at line " + this.mLineNumber + ")";
    }

    public String toString() {
        return "CLParsingException (" + hashCode() + ") : " + reason();
    }
}
