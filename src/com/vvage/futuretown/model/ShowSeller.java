package com.vvage.futuretown.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.vvage.futuretown.Global;
import com.vvage.futuretown.network.Http.ErrorMsg;
import com.vvage.futuretown.util.StringUtil;

/*
 * 5.根据商家id获取商家信息（简介|详细信息）
 * 测试地址：http://www.vv.com/?mod=index_api&code=showSeller&sid=1003&uid=1
 * GET
 */

public class ShowSeller extends BaseJsonReader {

	public String id;
	public String sid;
	
	public int status;
	public Seller seller;
	
	
	
	public ShowSeller(final String id, final String sid){
		this.sid = sid;
		this.id = id;
		urlParams = "mod=index_api&code=showSeller&sid="+sid+"&uid="+id;
	}

	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		if(str!=null && str.length()>0)
		try {
			this.status = new JSONObject(str).optInt(STATUS_JSON_TAG);
			if(status==0){
				isSucceed = true;
				String sellerstr = new JSONObject(str).optString("seller");
				if(StringUtil.isValidStr(sellerstr)){
					JSONObject s = new JSONObject(sellerstr);
					
					seller.addresscode = s.optString("addresscode");
					
					seller.bail_money = s.optString("bail_money");
					seller.check = s.optString("check");
					seller.clickcount = s.optString("clickcount");
					seller.field1 = s.optString("field1");
					seller.field2 = s.optString("field2");
					seller.id = s.optString("id");
					seller.maincat = s.optString("maincat");
					seller.mainservice = s.optString("mainservice");
					seller.money = s.optString("money");
					seller.productnum = s.optString("productnum");
					seller.publishtype = s.optString("publishtype");
					seller.scale = s.optString("scale");
					seller.selleraddress = s.optString("selleraddress");
					seller.sellerintro = s.optString("sellerintro");
					seller.sellerlevel = s.optString("sellerlevel");
					seller.sellermap = s.optString("sellermap");
					seller.sellername = s.optString("sellername");
					seller.sellerphone = s.optString("sellerphone");
					seller.sellerpic = s.optString("sellerpic");
					seller.sellerregion = s.optString("sellerregion");
					seller.sellerroute = s.optString("sellerroute");
					seller.sellertime = s.optString("sellertime");
					seller.sellerurl = s.optString("sellerurl");
					seller.sms_num = s.optString("sms_num");
					seller.sorder = s.optString("sorder");
					seller.stype = s.optString("stype");
					seller.successnum = s.optString("successnum");
					seller.time = s.optString("time");
					seller.userid = s.optString("userid");
				}
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class Seller {
		public String addresscode;
		public String area;
		public String bail_money;
		public String check;
		public String clickcount;
		public String field1;
		public String field2;
		public String id;
		public String maincat;
		public String mainservice;
		public String money;
		public String productnum;
		public String publishtype;
		public String scale;
		public String selleraddress;
		public String sellerintro;
		public String sellerlevel;
		public String sellermap;
		public String sellername;
		public String sellerphone;
		public String sellerpic;
		public String sellerregion;
		public String sellerroute;
		public String sellertime;
		public String sellerurl;
		public String sms_num;
		public String sorder;
		public String stype;
		public String successnum;
		public String time;
		public String userid;
	}

}
