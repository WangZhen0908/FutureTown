package com.vvage.futuretown.model;

import org.json.JSONObject;

import com.vvage.futuretown.network.Http.ErrorMsg;

/*
 * type 类型:1商品2商家3会员
 */
public class AddOrUnCollect extends BaseJsonReader {
	public static String ADD_COLLECT="addCollect";
	public static String UNCOLLECT="unCollect";
	

	public String errMsg="";
/*
 * type 类型:1商品2商家3会员
 */
	
	public AddOrUnCollect(final String id, 
			final String tid, final String collectType){
		urlParams = "mod=account_api&code="+collectType+"&id="+id+"&uid="+User.uid+"&tid="+tid;
	}


	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		try {
			int err = new JSONObject(str).optInt("err_no");
			errMsg = new JSONObject(str).optString("err_msg");
			if(err==0){
				isSucceed=true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			isSucceed=false;
		}
	}
	
//	{"err_no":0,"err_msg":"\u6210\u529f\u6536\u85cf\u5546\u5bb6"}

}
