package com.vvage.futuretown;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class Global {
	public static final String VERSION = "furturetown_1_0";
	
	public static final String URL = "http://www.vvage.com/?";
	
	//国际移动设备身份码
	public static String IMEI;
	//国际移动用户识别码
	public static String IMSI;
	
	// ---------------------------------------依赖设备
		public static int HEIGHT_SCREEN;
		public static int WIDTH_SCREEN;
		
	public static DisplayMetrics metric;
	
	public static int TEXT_SIZE_LV1 = 23;// 一级字号（标题）
	public static int TEXT_SIZE_LV2 = 18;// 二级字号（可已跳转到二级页面选项的文字，如我的空间中“我的消息”选项）
	public static int TEXT_SIZE_LV3 = 15;// 三级字号（正文，说明类文字）
	public static int TEXT_SIZE_LV4 = 13;// 四级字号（补充类文字）
	public static int TEXT_SIZE_LV5 = 11;// 五级字号（更小号）
	public static int TEXT_SIZE_LV6 = 9;// 六级字号（更小号）
		
	/**
	 * 内容与边距的距离(全局)
	 */
	public static int PADDING_GLOBAL = WIDTH_SCREEN / 25;
	
	
	public static void initResource(Context activity) {
//		if (getInitResourceResult()) {
//			return;
//		}
		TelephonyManager mTelephonyMgr = (TelephonyManager) activity  
                .getSystemService(Context.TELEPHONY_SERVICE);  
		IMSI = mTelephonyMgr.getSubscriberId();  
        IMEI = mTelephonyMgr.getDeviceId(); 
		
		metric = activity.getResources().getDisplayMetrics();
		TEXT_SIZE_LV1 = 23;// 一级字号（标题）
		TEXT_SIZE_LV2 = 20;// 二级字号（可已跳转到二级页面选项的文字）
		TEXT_SIZE_LV3 = 15;// 三级字号（正文，说明类文字）
		TEXT_SIZE_LV4 = 13;// 四级字号（补充类文字）
		TEXT_SIZE_LV5 = 11;// 五级字号（更小号）
		TEXT_SIZE_LV6 = 9;// 六级字号（更小号）
		if (metric.widthPixels > metric.heightPixels) {
			HEIGHT_SCREEN = metric.widthPixels;
			WIDTH_SCREEN = metric.heightPixels;
		} else {
			HEIGHT_SCREEN = metric.heightPixels;
			WIDTH_SCREEN = metric.widthPixels;
		}

		PADDING_GLOBAL = WIDTH_SCREEN / 25;
	}
	
	 /** 
     * 将px值转换为dip或dp值，保证尺寸大小不变 
     *  
     * @param pxValue 
     * @param scale 
     *            （DisplayMetrics类中属性density） 
     * @return 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
  
    /** 
     * 将dip或dp值转换为px值，保证尺寸大小不变 
     *  
     * @param dipValue 
     * @param scale 
     *            （DisplayMetrics类中属性density） 
     * @return 
     */  
    public static int dip2px(Context context, float dipValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dipValue * scale + 0.5f);  
    }  
  
    /** 
     * 将px值转换为sp值，保证文字大小不变 
     *  
     * @param pxValue 
     * @param fontScale 
     *            （DisplayMetrics类中属性scaledDensity） 
     * @return 
     */  
    public static int px2sp(Context context, float pxValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (pxValue / fontScale + 0.5f);  
    }  
  
    /** 
     * 将sp值转换为px值，保证文字大小不变 
     *  
     * @param spValue 
     * @param fontScale 
     *            （DisplayMetrics类中属性scaledDensity） 
     * @return 
     */  
    public static int sp2px(Context context, float spValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (spValue * fontScale + 0.5f);  
    }  
	
	
		
		/**
		 * 构造点击背景效果 -- Drawable
		 * 
		 * @param activity
		 * @param normal
		 * @param pressed
		 * @param focused
		 * @param unable
		 * @return
		 */
	public static StateListDrawable newSelectorFromDrawable(Activity activity,
			Drawable normal, Drawable pressed, Drawable focused, Drawable unable) {
		StateListDrawable bg = new StateListDrawable();

		// View.PRESSED_ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_pressed,
				android.R.attr.state_enabled }, pressed);
		// View.ENABLED_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled,
				android.R.attr.state_focused }, focused);
		// View.ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled }, normal);
		// View.FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_focused }, focused);
		// View.WINDOW_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
		// View.EMPTY_STATE_SET
		bg.addState(new int[] {}, normal);
		return bg;
	}
	
	/**
	 * 构造点击背景效果 -- Resource
	 * 
	 * @param activity
	 * @param idNormal
	 * @param idPressed
	 * @param idFocused
	 * @param idUnable
	 * @return
	 */
	public static StateListDrawable newSelectorFromRes(Context c,
			int idNormal, int idPressed, int idFocused, int idUnable) {
		StateListDrawable bg = new StateListDrawable();
		Drawable normal = idNormal == -1 ? null : c.getResources()
				.getDrawable(idNormal);
		Drawable pressed = idPressed == -1 ? null : c.getResources()
				.getDrawable(idPressed);
		Drawable focused = idFocused == -1 ? null : c.getResources()
				.getDrawable(idFocused);
		Drawable unable = idUnable == -1 ? null : c.getResources()
				.getDrawable(idUnable);

		// View.PRESSED_ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_pressed,
				android.R.attr.state_enabled }, pressed);
		// View.ENABLED_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled,
				android.R.attr.state_focused }, focused);
		// View.ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled }, normal);
		// View.FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_focused }, focused);
		// View.WINDOW_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
		// View.EMPTY_STATE_SET
		bg.addState(new int[] {}, normal);
		return bg;
	}
	
	/**
	 * 网络连接检测
	 * 
	 * @return
	 */
	public static boolean netCheck(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo != null && networkinfo.isAvailable()) {
			return true;
		}
		return false;
	}
	
	public static void call(Activity activity, String phoneNumber){
		if(phoneNumber!=null && !phoneNumber.equals("")){
			phoneNumber.trim();
			Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phoneNumber));
			//ACTION_DIAL进入拨号界面
			activity.startActivity(intent);
		}
	}
	
}
