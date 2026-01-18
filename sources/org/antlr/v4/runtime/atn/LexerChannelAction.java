package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.misc.MurmurHash;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public final class LexerChannelAction implements LexerAction {
    private final int channel;

    public LexerChannelAction(int channel) {
        this.channel = channel;
    }

    public int getChannel() {
        return this.channel;
    }

    public LexerActionType getActionType() {
        return LexerActionType.CHANNEL;
    }

    public boolean isPositionDependent() {
        return false;
    }

    public void execute(Lexer lexer) {
        lexer.setChannel(this.channel);
    }

    public int hashCode() {
        int hash = MurmurHash.initialize();
        return MurmurHash.finish(MurmurHash.update(MurmurHash.update(hash, getActionType().ordinal()), this.channel), 2);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof LexerChannelAction) && this.channel == ((LexerChannelAction) obj).channel;
    }

    public String toString() {
        return String.format("channel(%d)", new Object[]{Integer.valueOf(this.channel)});
    }
}
