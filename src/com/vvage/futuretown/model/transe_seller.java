package com.vvage.futuretown.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.vvage.futuretown.network.Http.ErrorMsg;

public class transe_seller extends BaseJsonReader {

	public transe_seller(final String phone, final String vcode){
		urlParams = "mod=seller_api&code=transe_seller&phone="+phone+"&vicode="+vcode;
	}

	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		try {
			JSONObject obj = new JSONObject(str);
			if(obj.optInt("status")==0){
				isSucceed=true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
