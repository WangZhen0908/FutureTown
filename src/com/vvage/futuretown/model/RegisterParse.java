package com.vvage.futuretown.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.vvage.futuretown.Global;
import com.vvage.futuretown.network.Http.ErrorMsg;

public class RegisterParse extends BaseJsonReader {

	public String msg;

	public RegisterParse(final String phoneNum, final String password, final String phone_code) {
		urlParams = "mod=account_api&code=Register_done&phone=" + phoneNum
				+ "&password=" + password+ "&phone_code=" + phone_code;
	}


	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(str);
			int status = jsonObject.optInt("status");
			msg = jsonObject.getString("msg");
			if(status==0){
				isSucceed=true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
