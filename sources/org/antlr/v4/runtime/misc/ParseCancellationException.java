package org.antlr.v4.runtime.misc;

import java.util.concurrent.CancellationException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class ParseCancellationException extends CancellationException {
    public ParseCancellationException() {
    }

    public ParseCancellationException(String message) {
        super(message);
    }

    public ParseCancellationException(Throwable cause) {
        initCause(cause);
    }

    public ParseCancellationException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
