package androidx.recyclerview.widget;

import androidx.recyclerview.widget.TileList;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes42.dex */
interface ThreadUtil {

    public interface BackgroundCallback {
        void loadTile(int i, int i2);

        void recycleTile(TileList.Tile tile);

        void refresh(int i);

        void updateRange(int i, int i2, int i3, int i4, int i5);
    }

    public interface MainThreadCallback {
        void addTile(int i, TileList.Tile tile);

        void removeTile(int i, int i2);

        void updateItemCount(int i, int i2);
    }

    BackgroundCallback getBackgroundProxy(BackgroundCallback backgroundCallback);

    MainThreadCallback getMainThreadProxy(MainThreadCallback mainThreadCallback);
}
