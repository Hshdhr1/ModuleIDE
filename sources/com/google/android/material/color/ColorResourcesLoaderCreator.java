package com.google.android.material.color;

import android.content.Context;
import android.content.res.loader.AssetsProvider;
import android.content.res.loader.ResourcesLoader;
import android.content.res.loader.ResourcesProvider;
import android.os.ParcelFileDescriptor;
import android.system.Os;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
final class ColorResourcesLoaderCreator {
    private static final String TAG = ColorResourcesLoaderCreator.class.getSimpleName();

    private ColorResourcesLoaderCreator() {
    }

    static ResourcesLoader create(Context context, Map map) {
        try {
            byte[] contentBytes = ColorResourcesTableCreator.create(context, map);
            Log.i(TAG, "Table created, length: " + contentBytes.length);
            if (contentBytes.length == 0) {
                return null;
            }
            FileDescriptor arscFile = null;
            try {
                arscFile = Os.memfd_create("temp.arsc", 0);
                FileOutputStream fileOutputStream = new FileOutputStream(arscFile);
                try {
                    fileOutputStream.write(contentBytes);
                    ParcelFileDescriptor pfd = ParcelFileDescriptor.dup(arscFile);
                    try {
                        ResourcesLoader colorsLoader = new ResourcesLoader();
                        colorsLoader.addProvider(ResourcesProvider.loadFromTable(pfd, (AssetsProvider) null));
                        if (pfd != null) {
                            pfd.close();
                        }
                        fileOutputStream.close();
                        return colorsLoader;
                    } finally {
                    }
                } finally {
                }
            } finally {
                if (arscFile != null) {
                    Os.close(arscFile);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to create the ColorResourcesTableCreator.", e);
            return null;
        }
    }
}
