package com.vvage.futuretown.model;

public class User {
	/*
	 * user 身份识别
	 * unlogin       未注册或者未登陆
	 * personal  <1   个人
	 * merchant  >=1  商家
	 */
	public static int mIdentify=-1;
	
	/*
	 * user action
	 * 1 注册商家
	 * 2 发布信息
	 * 3 商家注册成功
	 */
	public static int mAction=-1;
	
	/*
	 * "" 未登录 
	 */
	public static String uid="";//10
	
	public static String userName="";
	public static void reset(){
		uid="";//10
		mAction=-1;
		mIdentify=-1;
	}

}
