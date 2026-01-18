package androidx.viewpager2.widget;

import android.view.View;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes52.dex */
public final class CompositePageTransformer implements ViewPager2.PageTransformer {
    private final List mTransformers = new ArrayList();

    public void addTransformer(ViewPager2.PageTransformer transformer) {
        this.mTransformers.add(transformer);
    }

    public void removeTransformer(ViewPager2.PageTransformer transformer) {
        this.mTransformers.remove(transformer);
    }

    public void transformPage(View page, float position) {
        for (ViewPager2.PageTransformer transformer : this.mTransformers) {
            transformer.transformPage(page, position);
        }
    }
}
