package com.vvage.futuretown.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.vvage.futuretown.network.Http.ErrorMsg;

public class Addorder_save extends BaseJsonReader {

	public String msg="";
	
	public Addorder_save(final String userName, final Cart cart){
		
//		http://www.vvage.com/?mod=buy_api&code=Addorder_save&uid=100&cart[1][id]=94&cart[1][qty]=1&cart[1][price]=100&extmsg=sdssd
		urlParams = "mod=buy_api&code=Addorder_save&uid="+User.uid+"&username="+userName+"&cart[1][id]="+cart.id
				+"&cart[1][qty]="+cart.qty+"&cart[1][price]="+cart.price+"&extmsg="+cart.extmsg;
	}
	@Override
	public void read(String str) {
		// TODO Auto-generated method stub
//		{"err_no":0,"msg":"\u8ba2\u5355\u5df2\u7ecf\u6210\u529f\u63d0\u4ea4"}
		try {
			JSONObject obj = new JSONObject(str);
			if(obj!=null&&obj.optInt("err_no")==0){
				isSucceed=true;
				msg = obj.optString("msg");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
