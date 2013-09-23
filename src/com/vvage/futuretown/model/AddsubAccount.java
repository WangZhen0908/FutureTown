package com.vvage.futuretown.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.vvage.futuretown.network.Http.ErrorMsg;

public class AddsubAccount extends BaseJsonReader {

	public AddsubAccount(final String vcode, final String phone, 
			final String password, final String email) {
//		www.vvage.com/?mod=seller_api&code=AddsubAccount&uid=19&password=1234&notpassword=1234&&phone=13488757657&phone_code=2277
		
		urlParams = "mod=seller_api&code=AddsubAccount&uid=" + User.uid
				+ "&password=" + password + "&notpassword=" + password
				+ "&phone=" + phone + "&email=" + email + "&phone_code="
				+ vcode;
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
