package com.vvage.futuretown.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.support.v4.app.ShareCompat.IntentBuilder;

import com.vvage.futuretown.network.Http.ErrorMsg;

public class LoginParse extends BaseJsonReader {

	public LoginParse(final String phoneNum, final String password) {
		urlParams = "mod=account_api&code=Login_done&phone=" + phoneNum
				+ "&password=" + password;
	}
	
	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(str);

			int status = jsonObject.optInt("status");
			if(status==0){
				JSONObject userObj = jsonObject.optJSONObject("user");  
				if(userObj!=null){
					User.uid = userObj.optString("uid");
					User.userName = userObj.optString("username");
					User.mIdentify= userObj.optInt("seller_id");
					isSucceed = true;
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
