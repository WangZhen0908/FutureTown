package com.vvage.futuretown.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.vvage.futuretown.Global;
import com.vvage.futuretown.network.Http.ErrorMsg;

/**
 * 一、 手机短信验证码模块 1.注册 2.找回密码 3.添加员工 4.转让店铺 2013-8-5 下午10:56:25
 */
public class SendVCodeParse extends BaseJsonReader {

	public int err_no;
	public String err_str;

	/**
	 * type 短信类型 
	 * 0注册 1找密码 2添加小号 3转让 
	 * mobile 手机号 uid 登录用户id
	 * 注册接口传0就可以
	 */
	public SendVCodeParse(final String type, final String phone) {
		urlParams = "mod=account_api&code=SendSigncode&type="
		+ type+"&mobile="+phone+"&uid="+User.uid;
	}



	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(str);
			err_no = jsonObject.optInt("err_no");
			err_str = jsonObject.optString("err_str");
			if(err_no==0){
				isSucceed = true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
