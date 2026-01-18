package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.channels.Pipe;
import java.nio.channels.spi.SelectorProvider;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class PipeImpl extends Pipe {
    private final Pipe.SinkChannel sink;
    private final Pipe.SourceChannel source;

    PipeImpl(SelectorProvider selectorProvider) throws IOException {
        long makePipe = IOUtil.makePipe(true);
        int i = (int) (makePipe >>> 32);
        int i2 = (int) makePipe;
        FileDescriptor fileDescriptor = new FileDescriptor();
        IOUtil.setfdVal(fileDescriptor, i);
        this.source = new SourceChannelImpl(selectorProvider, fileDescriptor);
        FileDescriptor fileDescriptor2 = new FileDescriptor();
        IOUtil.setfdVal(fileDescriptor2, i2);
        this.sink = new SinkChannelImpl(selectorProvider, fileDescriptor2);
    }

    public Pipe.SourceChannel source() {
        return this.source;
    }

    public Pipe.SinkChannel sink() {
        return this.sink;
    }
}
