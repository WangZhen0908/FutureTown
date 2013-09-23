package com.vvage.futuretown.model;

import java.io.InputStream;
import com.vvage.futuretown.network.Http.ErrorMsg;

/*
 * 解析json抽象类
 */
public interface JsonReader {
	/*返回HTTP请求类型
	 * @param
	 * 
	 * @return HTTP请求类型  默认是返回GET
	 */
	public abstract int HttpRequestType();
	
	
	/*
	 *返回请求url
	 *
	 * @return 返回url
	 */
	public abstract String getUrl();
	
	/*
	 * 解析json数据
	 */
	void read(final String str) throws ErrorMsg;
}
