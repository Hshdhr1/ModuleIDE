package java.nio.file.attribute;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public enum AclEntryPermission {
    READ_DATA,
    WRITE_DATA,
    APPEND_DATA,
    READ_NAMED_ATTRS,
    WRITE_NAMED_ATTRS,
    EXECUTE,
    DELETE_CHILD,
    READ_ATTRIBUTES,
    WRITE_ATTRIBUTES,
    DELETE,
    READ_ACL,
    WRITE_ACL,
    WRITE_OWNER,
    SYNCHRONIZE;

    public static final AclEntryPermission ADD_FILE;
    public static final AclEntryPermission ADD_SUBDIRECTORY;
    public static final AclEntryPermission LIST_DIRECTORY;

    static {
        AclEntryPermission aclEntryPermission = READ_DATA;
        AclEntryPermission aclEntryPermission2 = WRITE_DATA;
        AclEntryPermission aclEntryPermission3 = APPEND_DATA;
        LIST_DIRECTORY = aclEntryPermission;
        ADD_FILE = aclEntryPermission2;
        ADD_SUBDIRECTORY = aclEntryPermission3;
    }
}
