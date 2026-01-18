package java.nio.file.attribute;

import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface PosixFileAttributes extends BasicFileAttributes {
    GroupPrincipal group();

    UserPrincipal owner();

    Set permissions();
}
