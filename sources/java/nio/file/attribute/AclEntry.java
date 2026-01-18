package java.nio.file.attribute;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class AclEntry {
    private final Set flags;
    private volatile int hash;
    private final Set perms;
    private final AclEntryType type;
    private final UserPrincipal who;

    /* synthetic */ AclEntry(AclEntryType aclEntryType, UserPrincipal userPrincipal, Set set, Set set2, AclEntry-IA r5) {
        this(aclEntryType, userPrincipal, set, set2);
    }

    private AclEntry(AclEntryType aclEntryType, UserPrincipal userPrincipal, Set set, Set set2) {
        this.type = aclEntryType;
        this.who = userPrincipal;
        this.perms = set;
        this.flags = set2;
    }

    public static final class Builder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private Set flags;
        private Set perms;
        private AclEntryType type;
        private UserPrincipal who;

        /* synthetic */ Builder(AclEntryType aclEntryType, UserPrincipal userPrincipal, Set set, Set set2, AclEntry-IA r5) {
            this(aclEntryType, userPrincipal, set, set2);
        }

        private Builder(AclEntryType aclEntryType, UserPrincipal userPrincipal, Set set, Set set2) {
            this.type = aclEntryType;
            this.who = userPrincipal;
            this.perms = set;
            this.flags = set2;
        }

        public AclEntry build() {
            if (this.type == null) {
                throw new IllegalStateException("Missing type component");
            }
            if (this.who == null) {
                throw new IllegalStateException("Missing who component");
            }
            return new AclEntry(this.type, this.who, this.perms, this.flags, null);
        }

        public Builder setType(AclEntryType aclEntryType) {
            aclEntryType.getClass();
            this.type = aclEntryType;
            return this;
        }

        public Builder setPrincipal(UserPrincipal userPrincipal) {
            userPrincipal.getClass();
            this.who = userPrincipal;
            return this;
        }

        private static void checkSet(Set set, Class cls) {
            for (Object obj : set) {
                obj.getClass();
                cls.cast(obj);
            }
        }

        public Builder setPermissions(Set set) {
            Set copyOf;
            if (set.isEmpty()) {
                copyOf = Collections.EMPTY_SET;
            } else {
                copyOf = EnumSet.copyOf(set);
                checkSet(copyOf, AclEntryPermission.class);
            }
            this.perms = copyOf;
            return this;
        }

        public Builder setPermissions(AclEntryPermission... aclEntryPermissionArr) {
            EnumSet noneOf = EnumSet.noneOf(AclEntryPermission.class);
            for (AclEntryPermission aclEntryPermission : aclEntryPermissionArr) {
                aclEntryPermission.getClass();
                noneOf.add(aclEntryPermission);
            }
            this.perms = noneOf;
            return this;
        }

        public Builder setFlags(Set set) {
            Set copyOf;
            if (set.isEmpty()) {
                copyOf = Collections.EMPTY_SET;
            } else {
                copyOf = EnumSet.copyOf(set);
                checkSet(copyOf, AclEntryFlag.class);
            }
            this.flags = copyOf;
            return this;
        }

        public Builder setFlags(AclEntryFlag... aclEntryFlagArr) {
            EnumSet noneOf = EnumSet.noneOf(AclEntryFlag.class);
            for (AclEntryFlag aclEntryFlag : aclEntryFlagArr) {
                aclEntryFlag.getClass();
                noneOf.add(aclEntryFlag);
            }
            this.flags = noneOf;
            return this;
        }
    }

    public static Builder newBuilder() {
        return new Builder(null, null, Collections.EMPTY_SET, Collections.EMPTY_SET, null);
    }

    public static Builder newBuilder(AclEntry aclEntry) {
        return new Builder(aclEntry.type, aclEntry.who, aclEntry.perms, aclEntry.flags, null);
    }

    public AclEntryType type() {
        return this.type;
    }

    public UserPrincipal principal() {
        return this.who;
    }

    public Set permissions() {
        return new HashSet(this.perms);
    }

    public Set flags() {
        return new HashSet(this.flags);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof AclEntry)) {
            return false;
        }
        AclEntry aclEntry = (AclEntry) obj;
        return this.type == aclEntry.type && this.who.equals(aclEntry.who) && this.perms.equals(aclEntry.perms) && this.flags.equals(aclEntry.flags);
    }

    private static int hash(int i, Object obj) {
        return (i * 127) + obj.hashCode();
    }

    public int hashCode() {
        if (this.hash != 0) {
            return this.hash;
        }
        this.hash = hash(hash(hash(this.type.hashCode(), this.who), this.perms), this.flags);
        return this.hash;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.who.getName());
        sb.append(':');
        Iterator it = this.perms.iterator();
        while (it.hasNext()) {
            sb.append(((AclEntryPermission) it.next()).name());
            sb.append('/');
        }
        sb.setLength(sb.length() - 1);
        sb.append(':');
        if (!this.flags.isEmpty()) {
            Iterator it2 = this.flags.iterator();
            while (it2.hasNext()) {
                sb.append(((AclEntryFlag) it2.next()).name());
                sb.append('/');
            }
            sb.setLength(sb.length() - 1);
            sb.append(':');
        }
        sb.append(this.type.name());
        return sb.toString();
    }
}
