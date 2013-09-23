package com.vvage.futuretown.util;

import android.util.Log;

public class LogUtil {
	private static boolean debug=false;
	
	public static void d(String tag, String msg){
		if(debug){
			Log.d(tag, msg);
		}
	}
}
