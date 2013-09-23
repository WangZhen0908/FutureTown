package com.vvage.futuretown.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.vvage.futuretown.network.Http.ErrorMsg;
import com.vvage.futuretown.util.StringUtil;

public class Address_save extends BaseJsonReader {
	
	public int id;
	
	public Address_save(final String id, final int province, final int city, final int country,
			final String address, final String name, final String phone, final String amap){
//		1203
		try {
			String add = URLEncoder.encode(StringUtil.isValidStr(address)?address:"", StringUtil.UTF_8);
			String n = URLEncoder.encode(StringUtil.isValidStr(name)?name:"", StringUtil.UTF_8);
			urlParams = "mod=misc_api&code=Address_save&id="+id+"&uid="+User.uid+
					"&province="+province+"&city="+city+"&country="+country+
					"&address="+add+"&name="+n+"&phone="+phone+"&amap="+amap;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		try {
			JSONObject obj = new JSONObject(str);
			String status = obj.optString("status");
			if(StringUtil.isValidStr(status) && status.equals("ok")){
				isSucceed = true;
				id = obj.optInt("id");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
