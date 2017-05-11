package com.example.john.chagepkg;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;

public class FbUseContextBuilderUtils {
	private static final String TAG = "sign";
	private static String key ="";
	public static Context start(Context context){
		FbUseContextBuilder fb = FbUseContextBuilder.getInstance(context);
		fb.setPackageName("content");
		fb.setVersionName("1.0");
		fb.setVersionCode(1);
		fb.setAppLabel("abe");
		String[] strs = new String[] { key, key };
		Signature[] sign = new Signature[strs.length];
        for(int i = 0 ; i < sign.length; i++) {
            Signature s = new Signature(strs[i]);
            sign[i] = s;
        }
//		fb.setSign(sign);
		fb.setSign(getSignature(context));
		Context c = fb.getContext();
		return c;
//		String pkg = c.getPackageName();
//		Log.e(TAG, "pkg : "+pkg);
//        NativeAd ad = new NativeAd(c, "key");
//        NativeAd.
	}
	
	public static String sign(Context context){
		String sign ="";
		try {
			 PackageInfo pis = context.getPackageManager().getPackageInfo("com.news.boost.clean", PackageManager.GET_SIGNATURES);
			 Signature[] sigs = pis.signatures;  
			 sign= new String(sigs[0].toChars()); 
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return sign;
         
	}
	public static Signature[] getSignature(Context context){
		Signature[] sigs =null;
		try {
			PackageInfo pis = context.getPackageManager().getPackageInfo("com.news.boost.clean", PackageManager.GET_SIGNATURES);
			sigs = pis.signatures;  
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return sigs;
		
	}

}
