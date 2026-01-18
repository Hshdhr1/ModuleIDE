package androidx.core.graphics;

import android.graphics.Path;
import android.graphics.PointF;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public final class PathUtils {
    public static Collection flatten(Path path) {
        return flatten(path, 0.5f);
    }

    public static Collection flatten(Path path, float error) {
        float[] pathData = path.approximate(error);
        int pointCount = pathData.length / 3;
        ArrayList arrayList = new ArrayList(pointCount);
        for (int i = 1; i < pointCount; i++) {
            int index = i * 3;
            int prevIndex = (i - 1) * 3;
            float d = pathData[index];
            float x = pathData[index + 1];
            float y = pathData[index + 2];
            float pd = pathData[prevIndex];
            float px = pathData[prevIndex + 1];
            float py = pathData[prevIndex + 2];
            if (d != pd && (x != px || y != py)) {
                arrayList.add(new PathSegment(new PointF(px, py), pd, new PointF(x, y), d));
            }
        }
        return arrayList;
    }

    private PathUtils() {
    }
}
