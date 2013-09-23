package com.vvage.futuretown.model;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Handler;
import android.util.Log;

import com.vvage.futuretown.Global;
import com.vvage.futuretown.network.Http;
import com.vvage.futuretown.util.FileUtil;
import com.vvage.futuretown.util.LogUtil;
import com.vvage.futuretown.util.StringUtil;

public class UpLoadImage extends BaseJsonReader {

	private int status;
	public int imgId;
	
	public String name;
	private String bmBase64Str;
	
	public UpLoadImage(final String name, Bitmap bm){
		this.name = name;
		if(bm!=null){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(CompressFormat.JPEG, 100, baos);
			byte[] bytes = baos.toByteArray();
			this.bmBase64Str = StringUtil.Base64Encode(bytes);
		}
		
//		http://www.vvage.com/?mod=upload_api&code=Image&Filedata=imaged
//			return "mod=upload_api&code=Image&Filedata=imaged&uid=1199";
		urlParams = "mod=upload_api&code=Image2&uid="+User.uid;
	}
	
	
	@Override
	public int HttpRequestType() {
		// TODO Auto-generated method stub
		return Http.POST;
	}

	@Override
	public void read(String str) {
		// TODO Auto-generated method stub
		LogUtil.d("JSON", str);
		if(StringUtil.isValidStr(str)){
			try {
				JSONObject o = new JSONObject(str);
				this.status = o.optInt("status");
				
				if(status==0){
					JSONObject info = o.getJSONObject("info");
					if(info!=null){
						imgId = info.optInt("id");
						isSucceed = true;
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		try {
			return "fileName="+ URLEncoder.encode(this.name, "UTF-8")+
					"&img="+URLEncoder.encode(StringUtil.isValidStr(this.bmBase64Str)?bmBase64Str:"",StringUtil.UTF_8);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
}
