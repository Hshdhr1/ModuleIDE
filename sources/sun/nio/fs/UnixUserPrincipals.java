package sun.nio.fs;

import java.io.IOException;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalNotFoundException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UnixUserPrincipals {
    static final User SPECIAL_OWNER = createSpecial("OWNER@");
    static final User SPECIAL_GROUP = createSpecial("GROUP@");
    static final User SPECIAL_EVERYONE = createSpecial("EVERYONE@");

    UnixUserPrincipals() {
    }

    private static User createSpecial(String str) {
        return new User(-1, str);
    }

    static class User implements UserPrincipal {
        private final int id;
        private final boolean isGroup;
        private final String name;

        /* synthetic */ User(int i, boolean z, String str, UnixUserPrincipals-IA r4) {
            this(i, z, str);
        }

        private User(int i, boolean z, String str) {
            this.id = i;
            this.isGroup = z;
            this.name = str;
        }

        User(int i, String str) {
            this(i, false, str);
        }

        int uid() {
            if (this.isGroup) {
                throw new AssertionError();
            }
            return this.id;
        }

        int gid() {
            if (this.isGroup) {
                return this.id;
            }
            throw new AssertionError();
        }

        boolean isSpecial() {
            return this.id == -1;
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            return this.name;
        }

        public boolean equals(Object obj) {
            User user;
            int i;
            int i2;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof User) || (i = this.id) != (i2 = (user = (User) obj).id) || this.isGroup != user.isGroup) {
                return false;
            }
            if (i == -1 && i2 == -1) {
                return this.name.equals(user.name);
            }
            return true;
        }

        public int hashCode() {
            int i = this.id;
            return i != -1 ? i : this.name.hashCode();
        }
    }

    static class Group extends User implements GroupPrincipal {
        Group(int i, String str) {
            super(i, true, str, null);
        }
    }

    static User fromUid(int i) {
        String num;
        try {
            num = Util.toString(UnixNativeDispatcher.getpwuid(i));
        } catch (UnixException unused) {
            num = Integer.toString(i);
        }
        return new User(i, num);
    }

    static Group fromGid(int i) {
        String num;
        try {
            num = Util.toString(UnixNativeDispatcher.getgrgid(i));
        } catch (UnixException unused) {
            num = Integer.toString(i);
        }
        return new Group(i, num);
    }

    private static int lookupName(String str, boolean z) throws IOException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("lookupUserInformation"));
        }
        try {
            int i = z ? UnixNativeDispatcher.getgrnam(str) : UnixNativeDispatcher.getpwnam(str);
            if (i != -1) {
                return i;
            }
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException unused) {
                throw new UserPrincipalNotFoundException(str);
            }
        } catch (UnixException e) {
            throw new IOException(str + ": " + e.errorString());
        }
    }

    static UserPrincipal lookupUser(String str) throws IOException {
        User user = SPECIAL_OWNER;
        if (str.equals(user.getName())) {
            return user;
        }
        User user2 = SPECIAL_GROUP;
        if (str.equals(user2.getName())) {
            return user2;
        }
        User user3 = SPECIAL_EVERYONE;
        return str.equals(user3.getName()) ? user3 : new User(lookupName(str, false), str);
    }

    static GroupPrincipal lookupGroup(String str) throws IOException {
        return new Group(lookupName(str, true), str);
    }
}
