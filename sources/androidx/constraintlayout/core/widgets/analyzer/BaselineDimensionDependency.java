package androidx.constraintlayout.core.widgets.analyzer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes40.dex */
class BaselineDimensionDependency extends DimensionDependency {
    public BaselineDimensionDependency(WidgetRun run) {
        super(run);
    }

    public void update(DependencyNode node) {
        VerticalWidgetRun verticalRun = (VerticalWidgetRun) this.run;
        verticalRun.baseline.margin = this.run.widget.getBaselineDistance();
        this.resolved = true;
    }
}
