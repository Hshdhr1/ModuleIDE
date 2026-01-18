package androidx.core.app;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.os.Build;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public class NotificationChannelGroupCompat {
    private boolean mBlocked;
    private List mChannels;
    String mDescription;
    final String mId;
    CharSequence mName;

    public static class Builder {
        final NotificationChannelGroupCompat mGroup;

        public Builder(String id) {
            this.mGroup = new NotificationChannelGroupCompat(id);
        }

        public Builder setName(CharSequence name) {
            this.mGroup.mName = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.mGroup.mDescription = description;
            return this;
        }

        public NotificationChannelGroupCompat build() {
            return this.mGroup;
        }
    }

    NotificationChannelGroupCompat(String id) {
        this.mChannels = Collections.emptyList();
        this.mId = (String) Preconditions.checkNotNull(id);
    }

    NotificationChannelGroupCompat(NotificationChannelGroup group) {
        this(group, Collections.emptyList());
    }

    NotificationChannelGroupCompat(NotificationChannelGroup group, List list) {
        this(group.getId());
        this.mName = group.getName();
        if (Build.VERSION.SDK_INT >= 28) {
            this.mDescription = group.getDescription();
        }
        if (Build.VERSION.SDK_INT >= 28) {
            this.mBlocked = group.isBlocked();
            this.mChannels = getChannelsCompat(group.getChannels());
        } else {
            this.mChannels = getChannelsCompat(list);
        }
    }

    private List getChannelsCompat(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            NotificationChannel channel = (NotificationChannel) it.next();
            if (this.mId.equals(channel.getGroup())) {
                arrayList.add(new NotificationChannelCompat(channel));
            }
        }
        return arrayList;
    }

    NotificationChannelGroup getNotificationChannelGroup() {
        if (Build.VERSION.SDK_INT < 26) {
            return null;
        }
        NotificationChannelGroup group = new NotificationChannelGroup(this.mId, this.mName);
        if (Build.VERSION.SDK_INT >= 28) {
            group.setDescription(this.mDescription);
        }
        return group;
    }

    public Builder toBuilder() {
        return new Builder(this.mId).setName(this.mName).setDescription(this.mDescription);
    }

    public String getId() {
        return this.mId;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public boolean isBlocked() {
        return this.mBlocked;
    }

    public List getChannels() {
        return this.mChannels;
    }
}
