package com.vvage.futuretown.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.vvage.futuretown.network.Http.ErrorMsg;

public class doSubAccount extends BaseJsonReader {

	public doSubAccount(final String subId){
		// www.vvage.com/?mod=seller_api&code=doSubAccount&uid=subId
		urlParams = "mod=seller_api&code=doSubAccount&uid="+subId;
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
