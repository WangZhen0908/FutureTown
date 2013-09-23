package com.vvage.futuretown.fragment;

import com.vvage.futuretown.Global;
import com.vvage.futuretown.util.StringUtil;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
	public static final String MAIN = "MAIN";
	private static SharedPreferencesHelper _instance;
	
	private static SharedPreferences _sp;
	private SharedPreferencesHelper (){
	}
	
	public static SharedPreferencesHelper getSharePreHelper(){
		if(_instance==null){
			_instance = new SharedPreferencesHelper();
		}
		return _instance;
	}
	
	public void setSharedPreHelper(Context context, String des){
		if(_sp!=null){
			_sp = null;
		}
		_sp = context.getSharedPreferences(des, context.MODE_PRIVATE);
	}
	//读取内容
	public static boolean getBoolean(String key, boolean defValue){
		return _sp.getBoolean(key, defValue);
	}
	
	public static String getString(String key, String defValue){
		return _sp.getString(key, defValue);
	}
	
	//写入内容
	public static void putBoolean(String key, boolean value){
		_sp.edit().putBoolean(key, value).commit();
	}
	
	public static void putFloat(String key, float value){
		_sp.edit().putFloat(key, value).commit();
	}
	public static void putString(String key, String value){
		_sp.edit().putString(key, value).commit();
	}
}
