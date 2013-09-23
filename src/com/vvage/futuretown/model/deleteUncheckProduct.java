package com.vvage.futuretown.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.vvage.futuretown.network.Http.ErrorMsg;

public class deleteUncheckProduct extends BaseJsonReader {

	public deleteUncheckProduct(final String id){
		urlParams = "mod=seller_api&code=deleteUncheckProduct&uid="+User.uid+"&id="+id;
	}
	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		try {
			JSONObject obj = new JSONObject(str);
			if(obj!=null){
				isSucceed=true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
