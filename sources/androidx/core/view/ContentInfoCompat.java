package androidx.core.view;

import android.content.ClipData;
import android.content.ClipDescription;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.ContentInfo;
import androidx.core.util.Preconditions;
import androidx.core.util.Predicate;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public final class ContentInfoCompat {
    public static final int FLAG_CONVERT_TO_PLAIN_TEXT = 1;
    public static final int SOURCE_APP = 0;
    public static final int SOURCE_AUTOFILL = 4;
    public static final int SOURCE_CLIPBOARD = 1;
    public static final int SOURCE_DRAG_AND_DROP = 3;
    public static final int SOURCE_INPUT_METHOD = 2;
    public static final int SOURCE_PROCESS_TEXT = 5;
    private final Compat mCompat;

    private interface BuilderCompat {
        ContentInfoCompat build();

        void setClip(ClipData clipData);

        void setExtras(Bundle bundle);

        void setFlags(int i);

        void setLinkUri(Uri uri);

        void setSource(int i);
    }

    private interface Compat {
        ClipData getClip();

        Bundle getExtras();

        int getFlags();

        Uri getLinkUri();

        int getSource();

        ContentInfo getWrapped();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Source {
    }

    static String sourceToString(int source) {
        switch (source) {
            case 0:
                return "SOURCE_APP";
            case 1:
                return "SOURCE_CLIPBOARD";
            case 2:
                return "SOURCE_INPUT_METHOD";
            case 3:
                return "SOURCE_DRAG_AND_DROP";
            case 4:
                return "SOURCE_AUTOFILL";
            case 5:
                return "SOURCE_PROCESS_TEXT";
            default:
                return String.valueOf(source);
        }
    }

    static String flagsToString(int flags) {
        if ((flags & 1) != 0) {
            return "FLAG_CONVERT_TO_PLAIN_TEXT";
        }
        return String.valueOf(flags);
    }

    ContentInfoCompat(Compat compat) {
        this.mCompat = compat;
    }

    public static ContentInfoCompat toContentInfoCompat(ContentInfo platContentInfo) {
        return new ContentInfoCompat(new Compat31Impl(platContentInfo));
    }

    public ContentInfo toContentInfo() {
        return this.mCompat.getWrapped();
    }

    public String toString() {
        return this.mCompat.toString();
    }

    public ClipData getClip() {
        return this.mCompat.getClip();
    }

    public int getSource() {
        return this.mCompat.getSource();
    }

    public int getFlags() {
        return this.mCompat.getFlags();
    }

    public Uri getLinkUri() {
        return this.mCompat.getLinkUri();
    }

    public Bundle getExtras() {
        return this.mCompat.getExtras();
    }

    public Pair partition(Predicate predicate) {
        ClipData clip = this.mCompat.getClip();
        if (clip.getItemCount() == 1) {
            boolean matched = predicate.test(clip.getItemAt(0));
            return Pair.create(matched ? this : null, matched ? null : this);
        }
        Pair<ClipData, ClipData> split = partition(clip, predicate);
        if (split.first == null) {
            return Pair.create((Object) null, this);
        }
        if (split.second == null) {
            return Pair.create(this, (Object) null);
        }
        return Pair.create(new Builder(this).setClip((ClipData) split.first).build(), new Builder(this).setClip((ClipData) split.second).build());
    }

    static Pair partition(ClipData clip, Predicate predicate) {
        ArrayList<ClipData.Item> acceptedItems = null;
        List list = null;
        for (int i = 0; i < clip.getItemCount(); i++) {
            ClipData.Item item = clip.getItemAt(i);
            if (predicate.test(item)) {
                acceptedItems = acceptedItems == null ? new ArrayList<>() : acceptedItems;
                acceptedItems.add(item);
            } else {
                list = list == null ? new ArrayList() : list;
                list.add(item);
            }
        }
        if (acceptedItems == null) {
            return Pair.create((Object) null, clip);
        }
        if (list == null) {
            return Pair.create(clip, (Object) null);
        }
        return Pair.create(buildClipData(clip.getDescription(), acceptedItems), buildClipData(clip.getDescription(), list));
    }

    static ClipData buildClipData(ClipDescription description, List list) {
        ClipData clip = new ClipData(new ClipDescription(description), (ClipData.Item) list.get(0));
        for (int i = 1; i < list.size(); i++) {
            clip.addItem((ClipData.Item) list.get(i));
        }
        return clip;
    }

    public static Pair partition(ContentInfo payload, java.util.function.Predicate predicate) {
        return Api31Impl.partition(payload, predicate);
    }

    private static final class Api31Impl {
        private Api31Impl() {
        }

        public static Pair partition(ContentInfo payload, java.util.function.Predicate predicate) {
            ClipData clip = payload.getClip();
            if (clip.getItemCount() == 1) {
                boolean matched = predicate.test(clip.getItemAt(0));
                return Pair.create(matched ? payload : null, matched ? null : payload);
            }
            predicate.getClass();
            Pair<ClipData, ClipData> split = ContentInfoCompat.partition(clip, new ContentInfoCompat$Api31Impl$$ExternalSyntheticLambda0(predicate));
            if (split.first == null) {
                return Pair.create((Object) null, payload);
            }
            if (split.second == null) {
                return Pair.create(payload, (Object) null);
            }
            return Pair.create(new ContentInfo.Builder(payload).setClip((ClipData) split.first).build(), new ContentInfo.Builder(payload).setClip((ClipData) split.second).build());
        }
    }

    private static final class CompatImpl implements Compat {
        private final ClipData mClip;
        private final Bundle mExtras;
        private final int mFlags;
        private final Uri mLinkUri;
        private final int mSource;

        CompatImpl(BuilderCompatImpl b) {
            this.mClip = (ClipData) Preconditions.checkNotNull(b.mClip);
            this.mSource = Preconditions.checkArgumentInRange(b.mSource, 0, 5, "source");
            this.mFlags = Preconditions.checkFlagsArgument(b.mFlags, 1);
            this.mLinkUri = b.mLinkUri;
            this.mExtras = b.mExtras;
        }

        public ContentInfo getWrapped() {
            return null;
        }

        public ClipData getClip() {
            return this.mClip;
        }

        public int getSource() {
            return this.mSource;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public Uri getLinkUri() {
            return this.mLinkUri;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public String toString() {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append("ContentInfoCompat{clip=");
            sb.append(this.mClip.getDescription());
            sb.append(", source=");
            sb.append(ContentInfoCompat.sourceToString(this.mSource));
            sb.append(", flags=");
            sb.append(ContentInfoCompat.flagsToString(this.mFlags));
            if (this.mLinkUri == null) {
                str = "";
            } else {
                str = ", hasLinkUri(" + this.mLinkUri.toString().length() + ")";
            }
            sb.append(str);
            sb.append(this.mExtras != null ? ", hasExtras" : "");
            sb.append("}");
            return sb.toString();
        }
    }

    private static final class Compat31Impl implements Compat {
        private final ContentInfo mWrapped;

        Compat31Impl(ContentInfo wrapped) {
            this.mWrapped = (ContentInfo) Preconditions.checkNotNull(wrapped);
        }

        public ContentInfo getWrapped() {
            return this.mWrapped;
        }

        public ClipData getClip() {
            return this.mWrapped.getClip();
        }

        public int getSource() {
            return this.mWrapped.getSource();
        }

        public int getFlags() {
            return this.mWrapped.getFlags();
        }

        public Uri getLinkUri() {
            return this.mWrapped.getLinkUri();
        }

        public Bundle getExtras() {
            return this.mWrapped.getExtras();
        }

        public String toString() {
            return "ContentInfoCompat{" + this.mWrapped + "}";
        }
    }

    public static final class Builder {
        private final BuilderCompat mBuilderCompat;

        public Builder(ContentInfoCompat other) {
            if (Build.VERSION.SDK_INT >= 31) {
                this.mBuilderCompat = new BuilderCompat31Impl(other);
            } else {
                this.mBuilderCompat = new BuilderCompatImpl(other);
            }
        }

        public Builder(ClipData clip, int source) {
            if (Build.VERSION.SDK_INT >= 31) {
                this.mBuilderCompat = new BuilderCompat31Impl(clip, source);
            } else {
                this.mBuilderCompat = new BuilderCompatImpl(clip, source);
            }
        }

        public Builder setClip(ClipData clip) {
            this.mBuilderCompat.setClip(clip);
            return this;
        }

        public Builder setSource(int source) {
            this.mBuilderCompat.setSource(source);
            return this;
        }

        public Builder setFlags(int flags) {
            this.mBuilderCompat.setFlags(flags);
            return this;
        }

        public Builder setLinkUri(Uri linkUri) {
            this.mBuilderCompat.setLinkUri(linkUri);
            return this;
        }

        public Builder setExtras(Bundle extras) {
            this.mBuilderCompat.setExtras(extras);
            return this;
        }

        public ContentInfoCompat build() {
            return this.mBuilderCompat.build();
        }
    }

    private static final class BuilderCompatImpl implements BuilderCompat {
        ClipData mClip;
        Bundle mExtras;
        int mFlags;
        Uri mLinkUri;
        int mSource;

        BuilderCompatImpl(ClipData clip, int source) {
            this.mClip = clip;
            this.mSource = source;
        }

        BuilderCompatImpl(ContentInfoCompat other) {
            this.mClip = other.getClip();
            this.mSource = other.getSource();
            this.mFlags = other.getFlags();
            this.mLinkUri = other.getLinkUri();
            this.mExtras = other.getExtras();
        }

        public void setClip(ClipData clip) {
            this.mClip = clip;
        }

        public void setSource(int source) {
            this.mSource = source;
        }

        public void setFlags(int flags) {
            this.mFlags = flags;
        }

        public void setLinkUri(Uri linkUri) {
            this.mLinkUri = linkUri;
        }

        public void setExtras(Bundle extras) {
            this.mExtras = extras;
        }

        public ContentInfoCompat build() {
            return new ContentInfoCompat(new CompatImpl(this));
        }
    }

    private static final class BuilderCompat31Impl implements BuilderCompat {
        private final ContentInfo.Builder mPlatformBuilder;

        BuilderCompat31Impl(ClipData clip, int source) {
            this.mPlatformBuilder = new ContentInfo.Builder(clip, source);
        }

        BuilderCompat31Impl(ContentInfoCompat other) {
            this.mPlatformBuilder = new ContentInfo.Builder(other.toContentInfo());
        }

        public void setClip(ClipData clip) {
            this.mPlatformBuilder.setClip(clip);
        }

        public void setSource(int source) {
            this.mPlatformBuilder.setSource(source);
        }

        public void setFlags(int flags) {
            this.mPlatformBuilder.setFlags(flags);
        }

        public void setLinkUri(Uri linkUri) {
            this.mPlatformBuilder.setLinkUri(linkUri);
        }

        public void setExtras(Bundle extras) {
            this.mPlatformBuilder.setExtras(extras);
        }

        public ContentInfoCompat build() {
            return new ContentInfoCompat(new Compat31Impl(this.mPlatformBuilder.build()));
        }
    }
}
