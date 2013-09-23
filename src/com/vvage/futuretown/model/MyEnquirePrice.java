package com.vvage.futuretown.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vvage.futuretown.network.Http.ErrorMsg;

public class MyEnquirePrice extends BaseJsonReader {

	public List<EnquireSeller> list = new ArrayList<MyEnquirePrice.EnquireSeller>();
	private String type;
	
	public MyEnquirePrice(final String type){
		this.type=type;
//		type	1 未回复，2已回复
//		www.vvage.com/?mod=me_api&code=myPrice&uid=10&type=1
		urlParams = "mod=me_api&code=myPrice&uid="+User.uid+"&type="+type;
	}

	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		try {
			JSONObject obj = new JSONObject(str);
			if(obj!=null&&obj.optInt("status")==0){
				JSONArray array = obj.getJSONArray("notelist");
				int count = array.length();
				for(int i=0;i<count;++i){
					JSONObject item = array.getJSONObject(i);
					
					EnquireSeller es = new EnquireSeller();
					
					es.sellername = item.optString("sellername");
					es.selleraddress = item.optString("sellerregion")+item.optString("selleraddress");
					es.sellerphone = item.optString("sellerphone");
					es.sellerurl = item.optString("url");
					
					list.add(es);
					isSucceed = true;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isSucceed = false;
		}
	}
	
	public class SellerProduct{
		public String productname;
		public String productowprice;
		public String producturl;
		public String productphone;
		
		public String yikoujia;
		public String outtime;
		public String extmsg;
		
	}
	
	public class EnquireSeller {
		public String sellername;
		public String sellersn="缺少编号字段";
		public String selleraddress;
		public String sellerphone;
		public String sellerurl;
		
		public List<SellerProduct> productList = new ArrayList<SellerProduct>();
	}
	

}
