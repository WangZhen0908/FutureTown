package com.vvage.futuretown.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.vvage.futuretown.network.Http.ErrorMsg;


/*
 * 发送询价接口
 * id		商品
 */
public class SendAskPrice extends BaseJsonReader {

	public String msg="";
	public SendAskPrice(final String id){
		urlParams = "mod=account_api&code=askPrice&id="+id+"&uid="+User.uid;
	}
	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		try {
			JSONObject obj = new JSONObject(str);
			if(obj!=null&&obj.optInt("status")==0){
				msg = obj.optString("msg");
				isSucceed=true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isSucceed = false;
		}
		
	}

}
