package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Spliterator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
interface TerminalOp {
    Object evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator);

    Object evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator);

    int getOpFlags();

    StreamShape inputShape();

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static int $default$getOpFlags(TerminalOp _this) {
            return 0;
        }

        public static StreamShape $default$inputShape(TerminalOp _this) {
            return StreamShape.REFERENCE;
        }

        public static Object $default$evaluateParallel(TerminalOp _this, PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (Tripwire.ENABLED) {
                Tripwire.trip(_this.getClass(), "{0} triggering TerminalOp.evaluateParallel serial default");
            }
            return _this.evaluateSequential(pipelineHelper, spliterator);
        }
    }
}
