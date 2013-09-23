package com.vvage.futuretown.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.util.Log;

import com.vvage.futuretown.Global;
import com.vvage.futuretown.network.Http;
import com.vvage.futuretown.network.Http.ErrorMsg;

/*
 * 解析json数据父类
 */
public abstract class BaseJsonReader implements JsonReader {
	public static final String STATUS_JSON_TAG = "status";
	protected boolean isSucceed; 
	protected String urlParams="";
//	protected String urlParams(){
//		return "";
//	}
	
	/*
	 * 返回url 子类需要@Override
	 * 
	 * @retrun 返回请求url
	 */
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
//		try {
//			return URLEncoder.encode(Global.URL+urlParams(), "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
		return Global.URL+urlParams;
	}

	/*
	 * 返回HTTP请求类型
	 * 
	 * @return HTTP请求类型  默认是GET
	 */
	@Override
	public int HttpRequestType(){
		// TODO Auto-generated method stub
		return Http.GET;
	}

	@Override
	public void read(final String str) throws ErrorMsg {
		// TODO Auto-generated method stub
	}
	
	public boolean isSucceed(){
		return isSucceed;
	}

}
