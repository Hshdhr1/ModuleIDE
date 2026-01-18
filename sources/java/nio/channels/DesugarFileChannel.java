package java.nio.channels;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class DesugarFileChannel {
    private DesugarFileChannel() {
    }

    public static FileChannel open(Path path, Set set, FileAttribute... fileAttributeArr) throws IOException {
        return path.getFileSystem().provider().newFileChannel(path, set, fileAttributeArr);
    }

    public static FileChannel open(Path path, OpenOption... openOptionArr) throws IOException {
        Set set;
        if (openOptionArr.length == 0) {
            set = Collections.EMPTY_SET;
        } else {
            Set hashSet = new HashSet();
            Collections.addAll(hashSet, openOptionArr);
            set = hashSet;
        }
        return FileChannel.open(path, set, new FileAttribute[0]);
    }
}
