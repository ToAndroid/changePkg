package com.example.john.chagepkg;

import java.util.List;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.UserHandle;
import android.text.TextUtils;

public class FbUseContextBuilder {
    private Context mContext;
    private String appLabel;
    private String packageName;
    private Signature[] signatures;
    private int versionCode;
    private String versionName;

    private static class MyContextWrapper extends ContextWrapper {
        String mAppLabel;
        PackageManager mPackageManager;
        String packageName;
        Signature[] signatures;
        int versionCode;
        String versionName;

        MyContextWrapper(Context context, String str, String str2, int i, String str3, Signature[] signatureArr) {
            super(context);
            MyContextWrapper.ai(str, "packageName");
            this.packageName = str;
            MyContextWrapper.ai(str2, "appLabel");
            this.mAppLabel = str2;
            MyContextWrapper.m2387l(i, "versionCode");
            this.versionCode = i;
            MyContextWrapper.ai(str3, "versionName");
            this.versionName = str3;
            this.signatures = signatureArr;
            if (this.signatures == null || this.signatures.length == 0) {
                try {
                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, PackageManager.GET_SIGNATURES);
                    if (packageInfo != null) {
                        this.signatures = packageInfo.signatures;
                    }
                } catch (NameNotFoundException e) {
                }
            }
            this.mPackageManager = new MyPackageManager(this, context.getPackageManager());
        }

        public String getPackageName() {
            return this.packageName;
        }

        public Context getApplicationContext() {
            return this;
        }

        static void ai(String str, String str2) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException(TextUtils.concat(new CharSequence[]{str2, " should not be null or PixelBuffer empty string"}).toString());
            }
        }

        static void m2387l(int i, String str) {
            if (i <= 0) {
                throw new IllegalArgumentException(TextUtils.concat(new CharSequence[]{str, " should not be lower than 0"}).toString());
            }
        }

        public PackageManager getPackageManager() {
            return this.mPackageManager;
        }
    }

    private static class MyPackageManager extends PackageManager {
        private PackageInfo mPackageInfo;
        PackageManager mPackageManager;
        MyContextWrapper mContextWrapper;

        private MyPackageManager(MyContextWrapper wrapper, PackageManager packageManager) {
            if (packageManager == null) {
                throw new IllegalArgumentException("the parameter innerPackageManager should not return null");
            }
            this.mPackageManager = packageManager;
            this.mContextWrapper = wrapper;
        }

        public PackageInfo getPackageInfo(String str, int i) throws NameNotFoundException {
            if (!str.equals(this.mContextWrapper.packageName)) {
                return this.mPackageManager.getPackageInfo(str, i);
            }
            if (this.mPackageInfo == null) {
                this.mPackageInfo = new PackageInfo();
                this.mPackageInfo.packageName = this.mContextWrapper.packageName;
                this.mPackageInfo.versionCode = this.mContextWrapper.versionCode;
                this.mPackageInfo.versionName = this.mContextWrapper.versionName;
                this.mPackageInfo.signatures = this.mContextWrapper.signatures;
            }
            return this.mPackageInfo;
        }

        public String[] currentToCanonicalPackageNames(String[] strArr) {
            return this.mPackageManager.currentToCanonicalPackageNames(strArr);
        }

        public String[] canonicalToCurrentPackageNames(String[] strArr) {
            return this.mPackageManager.canonicalToCurrentPackageNames(strArr);
        }

        public Intent getLaunchIntentForPackage(String str) {
            return this.mPackageManager.getLaunchIntentForPackage(str);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public Intent getLeanbackLaunchIntentForPackage(String str) {
            return this.mPackageManager.getLeanbackLaunchIntentForPackage(str);
        }

        public int[] getPackageGids(String str) throws NameNotFoundException {
            return this.mPackageManager.getPackageGids(str);
        }

        public PermissionInfo getPermissionInfo(String str, int i) throws NameNotFoundException {
            return this.mPackageManager.getPermissionInfo(str, i);
        }

        public List<PermissionInfo> queryPermissionsByGroup(String str, int i) throws NameNotFoundException {
            return this.mPackageManager.queryPermissionsByGroup(str, i);
        }

        public PermissionGroupInfo getPermissionGroupInfo(String str, int i) throws NameNotFoundException {
            return this.mPackageManager.getPermissionGroupInfo(str, i);
        }

        public List<PermissionGroupInfo> getAllPermissionGroups(int i) {
            return null;
        }

        public ApplicationInfo getApplicationInfo(String str, int i) throws NameNotFoundException {
            if (!str.equals(this.mContextWrapper.packageName)) {
                return this.mPackageManager.getApplicationInfo(str, i);
            }
            ApplicationInfo applicationInfo = new ApplicationInfo();
            applicationInfo.packageName = this.mContextWrapper.packageName;
            return applicationInfo;
        }

        public ActivityInfo getActivityInfo(ComponentName componentName, int i) throws NameNotFoundException {
            return this.mPackageManager.getActivityInfo(componentName, i);
        }

        public ActivityInfo getReceiverInfo(ComponentName componentName, int i) throws NameNotFoundException {
            return this.mPackageManager.getReceiverInfo(componentName, i);
        }

        public ServiceInfo getServiceInfo(ComponentName componentName, int i) throws NameNotFoundException {
            return this.mPackageManager.getServiceInfo(componentName, i);
        }

        public ProviderInfo getProviderInfo(ComponentName componentName, int i) throws NameNotFoundException {
            return this.mPackageManager.getProviderInfo(componentName, i);
        }

        public List<PackageInfo> getInstalledPackages(int i) {
            return this.mPackageManager.getInstalledPackages(i);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        public List<PackageInfo> getPackagesHoldingPermissions(String[] strArr, int i) {
            return this.mPackageManager.getPackagesHoldingPermissions(strArr, i);
        }

        public int checkPermission(String str, String str2) {
            return this.mPackageManager.checkPermission(str, str2);
        }

        public boolean addPermission(PermissionInfo permissionInfo) {
            return this.mPackageManager.addPermission(permissionInfo);
        }

        public boolean addPermissionAsync(PermissionInfo permissionInfo) {
            return this.mPackageManager.addPermissionAsync(permissionInfo);
        }

        public void removePermission(String str) {
            this.mPackageManager.removePermission(str);
        }

        public int checkSignatures(String str, String str2) {
            return this.mPackageManager.checkSignatures(str, str2);
        }

        public int checkSignatures(int i, int i2) {
            return this.mPackageManager.checkSignatures(i, i2);
        }

        public String[] getPackagesForUid(int i) {
            return this.mPackageManager.getPackagesForUid(i);
        }

        public String getNameForUid(int i) {
            return this.mPackageManager.getNameForUid(i);
        }

        public List<ApplicationInfo> getInstalledApplications(int i) {
            return this.mPackageManager.getInstalledApplications(i);
        }

        public String[] getSystemSharedLibraryNames() {
            return this.mPackageManager.getSystemSharedLibraryNames();
        }

        public FeatureInfo[] getSystemAvailableFeatures() {
            return this.mPackageManager.getSystemAvailableFeatures();
        }

        public boolean hasSystemFeature(String str) {
            return this.mPackageManager.hasSystemFeature(str);
        }

        public ResolveInfo resolveActivity(Intent intent, int i) {
            return this.mPackageManager.resolveActivity(intent, i);
        }

        public List<ResolveInfo> queryIntentActivities(Intent intent, int i) {
            return this.mPackageManager.queryIntentActivities(intent, i);
        }

        public List<ResolveInfo> queryIntentActivityOptions(ComponentName componentName, Intent[] intentArr, Intent intent, int i) {
            return this.mPackageManager.queryIntentActivityOptions(componentName, intentArr, intent, i);
        }

        public List<ResolveInfo> queryBroadcastReceivers(Intent intent, int i) {
            return this.mPackageManager.queryBroadcastReceivers(intent, i);
        }

        public ResolveInfo resolveService(Intent intent, int i) {
            return this.mPackageManager.resolveService(intent, i);
        }

        public List<ResolveInfo> queryIntentServices(Intent intent, int i) {
            return this.mPackageManager.queryIntentServices(intent, i);
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        public List<ResolveInfo> queryIntentContentProviders(Intent intent, int i) {
            return this.mPackageManager.queryIntentContentProviders(intent, i);
        }

        public ProviderInfo resolveContentProvider(String str, int i) {
            return this.mPackageManager.resolveContentProvider(str, i);
        }

        public List<ProviderInfo> queryContentProviders(String str, int i, int i2) {
            return this.mPackageManager.queryContentProviders(str, i, i2);
        }

        public InstrumentationInfo getInstrumentationInfo(ComponentName componentName, int i) throws NameNotFoundException {
            return this.mPackageManager.getInstrumentationInfo(componentName, i);
        }

        public List<InstrumentationInfo> queryInstrumentation(String str, int i) {
            return this.mPackageManager.queryInstrumentation(str, i);
        }

        public Drawable getDrawable(String str, int i, ApplicationInfo applicationInfo) {
            return this.mPackageManager.getDrawable(str, i, applicationInfo);
        }

        public Drawable getActivityIcon(ComponentName componentName) throws NameNotFoundException {
            return this.mPackageManager.getActivityIcon(componentName);
        }

        public Drawable getActivityIcon(Intent intent) throws NameNotFoundException {
            return this.mPackageManager.getActivityIcon(intent);
        }

        @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
        public Drawable getActivityBanner(ComponentName componentName) throws NameNotFoundException {
            return this.mPackageManager.getActivityBanner(componentName);
        }

        @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
        public Drawable getActivityBanner(Intent intent) throws NameNotFoundException {
            return this.mPackageManager.getActivityBanner(intent);
        }

        public Drawable getDefaultActivityIcon() {
            return this.mPackageManager.getDefaultActivityIcon();
        }

        public Drawable getApplicationIcon(ApplicationInfo applicationInfo) {
            return this.mPackageManager.getApplicationIcon(applicationInfo);
        }

        public Drawable getApplicationIcon(String str) throws NameNotFoundException {
            return this.mPackageManager.getApplicationIcon(str);
        }

        @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
        public Drawable getApplicationBanner(ApplicationInfo applicationInfo) {
            return this.mPackageManager.getApplicationBanner(applicationInfo);
        }

        @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
        public Drawable getApplicationBanner(String str) throws NameNotFoundException {
            return this.mPackageManager.getApplicationBanner(str);
        }

        public Drawable getActivityLogo(ComponentName componentName) throws NameNotFoundException {
            return this.mPackageManager.getActivityLogo(componentName);
        }

        public Drawable getActivityLogo(Intent intent) throws NameNotFoundException {
            return this.mPackageManager.getActivityLogo(intent);
        }

        public Drawable getApplicationLogo(ApplicationInfo applicationInfo) {
            return this.mPackageManager.getApplicationLogo(applicationInfo);
        }

        public Drawable getApplicationLogo(String str) throws NameNotFoundException {
            return this.mPackageManager.getApplicationLogo(str);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public Drawable getUserBadgedIcon(Drawable drawable, UserHandle userHandle) {
            return this.mPackageManager.getUserBadgedIcon(drawable, userHandle);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public Drawable getUserBadgedDrawableForDensity(Drawable drawable, UserHandle userHandle, Rect rect, int i) {
            return this.mPackageManager.getUserBadgedDrawableForDensity(drawable, userHandle, rect, i);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public CharSequence getUserBadgedLabel(CharSequence charSequence, UserHandle userHandle) {
            return this.mPackageManager.getUserBadgedLabel(charSequence, userHandle);
        }

        public CharSequence getText(String str, int i, ApplicationInfo applicationInfo) {
            return this.mPackageManager.getText(str, i, applicationInfo);
        }

        public XmlResourceParser getXml(String str, int i, ApplicationInfo applicationInfo) {
            return this.mPackageManager.getXml(str, i, applicationInfo);
        }

        public CharSequence getApplicationLabel(ApplicationInfo applicationInfo) {
        	return this.mContextWrapper.mAppLabel;
//        	if (TextUtils.equals(applicationInfo==null?"":applicationInfo.packageName, this.mContextWrapper.packageName)) {
//        		  return this.mContextWrapper.mAppLabel;
//			}
////            if (applicationInfo.packageName.equals(this.mContextWrapper.packageName)) {
////                return this.mContextWrapper.mAppLabel;
////            }
//            return this.mPackageManager.getApplicationLabel(applicationInfo);
        }

        public Resources getResourcesForActivity(ComponentName componentName) throws NameNotFoundException {
            return this.mPackageManager.getResourcesForActivity(componentName);
        }

        public Resources getResourcesForApplication(ApplicationInfo applicationInfo) throws NameNotFoundException {
            return this.mPackageManager.getResourcesForApplication(applicationInfo);
        }

        public Resources getResourcesForApplication(String str) throws NameNotFoundException {
            return this.mPackageManager.getResourcesForApplication(str);
        }

        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        public void verifyPendingInstall(int i, int i2) {
            this.mPackageManager.verifyPendingInstall(i, i2);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        public void extendVerificationTimeout(int i, int i2, long j) {
            this.mPackageManager.extendVerificationTimeout(i, i2, j);
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public void setInstallerPackageName(String str, String str2) {
            this.mPackageManager.setInstallerPackageName(str, str2);
        }

        public String getInstallerPackageName(String str) {
        	return str;
//            return this.mPackageManager.getInstallerPackageName(str);
        }

        public void addPackageToPreferred(String str) {
            this.mPackageManager.addPackageToPreferred(str);
        }

        public void removePackageFromPreferred(String str) {
            this.mPackageManager.removePackageFromPreferred(str);
        }

        public List<PackageInfo> getPreferredPackages(int i) {
            return this.mPackageManager.getPreferredPackages(i);
        }

        public void addPreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName) {
            this.mPackageManager.addPreferredActivity(intentFilter, i, componentNameArr, componentName);
        }

        public void clearPackagePreferredActivities(String str) {
            this.mPackageManager.clearPackagePreferredActivities(str);
        }

        public int getPreferredActivities(List<IntentFilter> list, List<ComponentName> list2, String str) {
            return this.mPackageManager.getPreferredActivities(list, list2, str);
        }

        public void setComponentEnabledSetting(ComponentName componentName, int i, int i2) {
            this.mPackageManager.setComponentEnabledSetting(componentName, i, i2);
        }

        public int getComponentEnabledSetting(ComponentName componentName) {
            return this.mPackageManager.getComponentEnabledSetting(componentName);
        }

        public void setApplicationEnabledSetting(String str, int i, int i2) {
            this.mPackageManager.setApplicationEnabledSetting(str, i, i2);
        }

        public int getApplicationEnabledSetting(String str) {
            return this.mPackageManager.getApplicationEnabledSetting(str);
        }

        public boolean isSafeMode() {
            return this.mPackageManager.isSafeMode();
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public PackageInstaller getPackageInstaller() {
            return this.mPackageManager.getPackageInstaller();
        }

		public int[] getPackageGids(String arg0, int arg1) throws NameNotFoundException {
			// TODO Auto-generated method stub
			return null;
		}

		public int getPackageUid(String arg0, int arg1) throws NameNotFoundException {
			// TODO Auto-generated method stub
			return 0;
		}

		public boolean hasSystemFeature(String arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean isPermissionRevokedByPolicy(String arg0, String arg1) {
			// TODO Auto-generated method stub
			return false;
		}
    }

    public static FbUseContextBuilder getInstance(Context context) {
        return new FbUseContextBuilder(context);
    }

    private FbUseContextBuilder(Context context) {
        this.mContext = context;
    }

    public FbUseContextBuilder setPackageName(String str) {
        this.packageName = str;
        return this;
    }

    public FbUseContextBuilder setVersionCode(int i) {
        this.versionCode = i;
        return this;
    }

    public FbUseContextBuilder setVersionName(String str) {
        this.versionName = str;
        return this;
    }

    public FbUseContextBuilder setAppLabel(String str) {
        this.appLabel = str;
        return this;
    }

    public FbUseContextBuilder setSign(Signature[] signatureArr) {
        this.signatures = signatureArr;
        return this;
    }

    public Context getContext() {
        return new MyContextWrapper(this.mContext, this.packageName, this.appLabel, this.versionCode, this.versionName, this.signatures);
    }
}
