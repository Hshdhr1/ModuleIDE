package com.blacksquircle.ui.editorkit.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: StylingTask.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B3\u0012\u0012\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003\u0012\u0018\u0010\u0006\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u0006\u0010\u000f\u001a\u00020\bJ\u0006\u0010\u0010\u001a\u00020\bR\u001a\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0006\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/blacksquircle/ui/editorkit/utils/StylingTask;", "", "doAsync", "Lkotlin/Function0;", "", "Lcom/blacksquircle/ui/language/base/model/SyntaxHighlightResult;", "onSuccess", "Lkotlin/Function1;", "", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;)V", "mainThreadHandler", "Landroid/os/Handler;", "singleThreadExecutor", "Ljava/util/concurrent/ExecutorService;", "kotlin.jvm.PlatformType", "cancel", "execute", "Companion", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class StylingTask {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final String TAG = "StylingTask";

    @NotNull
    private final Function0 doAsync;

    @NotNull
    private final Handler mainThreadHandler;

    @NotNull
    private final Function1 onSuccess;
    private final ExecutorService singleThreadExecutor;

    public static /* synthetic */ void $r8$lambda$9L5KfEsnh2CF69B5DLV4JKO2Ix4(StylingTask stylingTask, List list) {
        execute$lambda$1$lambda$0(stylingTask, list);
    }

    public static /* synthetic */ void $r8$lambda$a78RxU2N8MX7fZJAlq51gA0URUU(StylingTask stylingTask) {
        execute$lambda$1(stylingTask);
    }

    public StylingTask(@NotNull Function0 function0, @NotNull Function1 function1) {
        Intrinsics.checkNotNullParameter(function0, "doAsync");
        Intrinsics.checkNotNullParameter(function1, "onSuccess");
        this.doAsync = function0;
        this.onSuccess = function1;
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
        this.singleThreadExecutor = Executors.newSingleThreadExecutor();
    }

    public final void execute() {
        this.singleThreadExecutor.execute(new StylingTask$$ExternalSyntheticLambda1(this));
    }

    private static final void execute$lambda$1(StylingTask stylingTask) {
        Intrinsics.checkNotNullParameter(stylingTask, "this$0");
        try {
            stylingTask.mainThreadHandler.post(new StylingTask$$ExternalSyntheticLambda0(stylingTask, (List) stylingTask.doAsync.invoke()));
        } catch (Throwable th) {
            Log.e("StylingTask", th.getMessage(), th);
        }
    }

    private static final void execute$lambda$1$lambda$0(StylingTask stylingTask, List list) {
        Intrinsics.checkNotNullParameter(stylingTask, "this$0");
        Intrinsics.checkNotNullParameter(list, "$syntaxHighlightSpans");
        if (stylingTask.singleThreadExecutor.isShutdown()) {
            return;
        }
        stylingTask.onSuccess.invoke(list);
    }

    public final void cancel() {
        this.singleThreadExecutor.shutdown();
    }

    /* compiled from: StylingTask.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/blacksquircle/ui/editorkit/utils/StylingTask$Companion;", "", "()V", "TAG", "", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
