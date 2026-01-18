package androidx.emoji2.text;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.os.Build;
import android.util.Log;
import androidx.core.provider.FontRequest;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.EmojiCompat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes27.dex */
public final class DefaultEmojiCompatConfig {
    private DefaultEmojiCompatConfig() {
    }

    public static FontRequestEmojiCompatConfig create(Context context) {
        return (FontRequestEmojiCompatConfig) new DefaultEmojiCompatConfigFactory(null).create(context);
    }

    public static class DefaultEmojiCompatConfigFactory {
        private static final String DEFAULT_EMOJI_QUERY = "emojicompat-emoji-font";
        private static final String INTENT_LOAD_EMOJI_FONT = "androidx.content.action.LOAD_EMOJI_FONT";
        private static final String TAG = "emoji2.text.DefaultEmojiConfig";
        private final DefaultEmojiCompatConfigHelper mHelper;

        public DefaultEmojiCompatConfigFactory(DefaultEmojiCompatConfigHelper helper) {
            this.mHelper = helper != null ? helper : getHelperForApi();
        }

        public EmojiCompat.Config create(Context context) {
            return configOrNull(context, queryForDefaultFontRequest(context));
        }

        private EmojiCompat.Config configOrNull(Context context, FontRequest fontRequest) {
            if (fontRequest == null) {
                return null;
            }
            return new FontRequestEmojiCompatConfig(context, fontRequest);
        }

        FontRequest queryForDefaultFontRequest(Context context) {
            PackageManager packageManager = context.getPackageManager();
            Preconditions.checkNotNull(packageManager, "Package manager required to locate emoji font provider");
            ProviderInfo providerInfo = queryDefaultInstalledContentProvider(packageManager);
            if (providerInfo == null) {
                return null;
            }
            try {
                return generateFontRequestFrom(providerInfo, packageManager);
            } catch (PackageManager.NameNotFoundException e) {
                Log.wtf("emoji2.text.DefaultEmojiConfig", e);
                return null;
            }
        }

        private ProviderInfo queryDefaultInstalledContentProvider(PackageManager packageManager) {
            List<ResolveInfo> providers = this.mHelper.queryIntentContentProviders(packageManager, new Intent("androidx.content.action.LOAD_EMOJI_FONT"), 0);
            for (ResolveInfo resolveInfo : providers) {
                ProviderInfo providerInfo = this.mHelper.getProviderInfo(resolveInfo);
                if (hasFlagSystem(providerInfo)) {
                    return providerInfo;
                }
            }
            return null;
        }

        private boolean hasFlagSystem(ProviderInfo providerInfo) {
            return (providerInfo == null || providerInfo.applicationInfo == null || (providerInfo.applicationInfo.flags & 1) != 1) ? false : true;
        }

        private FontRequest generateFontRequestFrom(ProviderInfo providerInfo, PackageManager packageManager) throws PackageManager.NameNotFoundException {
            String providerAuthority = providerInfo.authority;
            String providerPackage = providerInfo.packageName;
            Signature[] signingSignatures = this.mHelper.getSigningSignatures(packageManager, providerPackage);
            List<List<byte[]>> signatures = convertToByteArray(signingSignatures);
            return new FontRequest(providerAuthority, providerPackage, "emojicompat-emoji-font", signatures);
        }

        private List convertToByteArray(Signature[] signatures) {
            ArrayList arrayList = new ArrayList();
            for (Signature signature : signatures) {
                arrayList.add(signature.toByteArray());
            }
            return Collections.singletonList(arrayList);
        }

        private static DefaultEmojiCompatConfigHelper getHelperForApi() {
            if (Build.VERSION.SDK_INT >= 28) {
                return new DefaultEmojiCompatConfigHelper_API28();
            }
            if (Build.VERSION.SDK_INT >= 19) {
                return new DefaultEmojiCompatConfigHelper_API19();
            }
            return new DefaultEmojiCompatConfigHelper();
        }
    }

    public static class DefaultEmojiCompatConfigHelper {
        public Signature[] getSigningSignatures(PackageManager packageManager, String providerPackage) throws PackageManager.NameNotFoundException {
            PackageInfo packageInfoForSignatures = packageManager.getPackageInfo(providerPackage, 64);
            return packageInfoForSignatures.signatures;
        }

        public List queryIntentContentProviders(PackageManager packageManager, Intent intent, int flags) {
            return Collections.emptyList();
        }

        public ProviderInfo getProviderInfo(ResolveInfo resolveInfo) {
            throw new IllegalStateException("Unable to get provider info prior to API 19");
        }
    }

    public static class DefaultEmojiCompatConfigHelper_API19 extends DefaultEmojiCompatConfigHelper {
        public List queryIntentContentProviders(PackageManager packageManager, Intent intent, int flags) {
            return packageManager.queryIntentContentProviders(intent, flags);
        }

        public ProviderInfo getProviderInfo(ResolveInfo resolveInfo) {
            return resolveInfo.providerInfo;
        }
    }

    public static class DefaultEmojiCompatConfigHelper_API28 extends DefaultEmojiCompatConfigHelper_API19 {
        public Signature[] getSigningSignatures(PackageManager packageManager, String providerPackage) throws PackageManager.NameNotFoundException {
            PackageInfo packageInfoForSignatures = packageManager.getPackageInfo(providerPackage, 64);
            return packageInfoForSignatures.signatures;
        }
    }
}
