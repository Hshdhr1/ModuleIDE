package androidx.recyclerview.widget;

import android.util.SparseArray;
import java.lang.reflect.Array;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes42.dex */
class TileList {
    Tile mLastAccessedTile;
    final int mTileSize;
    private final SparseArray mTiles = new SparseArray(10);

    public TileList(int tileSize) {
        this.mTileSize = tileSize;
    }

    public Object getItemAt(int pos) {
        if (this.mLastAccessedTile == null || !this.mLastAccessedTile.containsPosition(pos)) {
            int startPosition = pos - (pos % this.mTileSize);
            int index = this.mTiles.indexOfKey(startPosition);
            if (index < 0) {
                return null;
            }
            this.mLastAccessedTile = (Tile) this.mTiles.valueAt(index);
        }
        return this.mLastAccessedTile.getByPosition(pos);
    }

    public int size() {
        return this.mTiles.size();
    }

    public void clear() {
        this.mTiles.clear();
    }

    public Tile getAtIndex(int index) {
        if (index < 0 || index >= this.mTiles.size()) {
            return null;
        }
        return (Tile) this.mTiles.valueAt(index);
    }

    public Tile addOrReplace(Tile tile) {
        int index = this.mTiles.indexOfKey(tile.mStartPosition);
        if (index < 0) {
            this.mTiles.put(tile.mStartPosition, tile);
            return null;
        }
        Tile tile2 = (Tile) this.mTiles.valueAt(index);
        this.mTiles.setValueAt(index, tile);
        if (this.mLastAccessedTile == tile2) {
            this.mLastAccessedTile = tile;
            return tile2;
        }
        return tile2;
    }

    public Tile removeAtPos(int startPosition) {
        Tile tile = (Tile) this.mTiles.get(startPosition);
        if (this.mLastAccessedTile == tile) {
            this.mLastAccessedTile = null;
        }
        this.mTiles.delete(startPosition);
        return tile;
    }

    public static class Tile {
        public int mItemCount;
        public final Object[] mItems;
        Tile mNext;
        public int mStartPosition;

        public Tile(Class cls, int size) {
            this.mItems = (Object[]) Array.newInstance(cls, size);
        }

        boolean containsPosition(int pos) {
            return this.mStartPosition <= pos && pos < this.mStartPosition + this.mItemCount;
        }

        Object getByPosition(int pos) {
            return this.mItems[pos - this.mStartPosition];
        }
    }
}
