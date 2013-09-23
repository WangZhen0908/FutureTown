package com.vvage.futuretown.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/*
 * 商家 处理订单
 * http://www.vvage.com/?mod=seller_api&code=order&uid=1104&type=0
 */
public class Order extends BaseJsonReader{

	public List<HandlerOrder> orderList = new ArrayList<HandlerOrder>();
	private String type="0";
	
	public Order(final String type){
		
//		www.vvage.com/?mod=seller_api&code=order&uid=1104&type=1
		
		this.type = type;
		urlParams = "mod=seller_api&code=order&uid="+User.uid+"&type="+type;
	}
	
	@Override
	public void read(String str) {
		// TODO Auto-generated method stub
		try{
			JSONObject obj = new JSONObject(str);
			if(obj!=null&&obj.optInt("status")==0){
				JSONTokener jsonParser = new JSONTokener(str);
				JSONObject next = (JSONObject) jsonParser.nextValue();
				JSONArray array = next.getJSONArray("order_all");
				int count = array.length();
				for(int i=0;i<count;++i){
					JSONObject item = array.getJSONObject(i);
					HandlerOrder unh = new HandlerOrder();
					unh.username = item.optString("username");
					unh.userphone = item.optString("userphone");
					unh.orderid = item.optString("orderid");
					unh.addressid = item.optString("addressid");
					unh.extmsg = item.optString("extmsg");
					unh.userid = item.optString("userid");
					unh.shippingname = item.optString("name");
					unh.shippingphone = item.optString("phone");
					unh.shippingaddress = item.optString("region")+item.optString("address");
					
					JSONObject item_P = item.getJSONObject("product");
					Iterator<String> it = item_P.keys();
					while(it.hasNext()){
						String key = it.next();
						String value = item_P.optString(key);
						JSONObject o = new JSONObject(value);
						HandlerOrderProduct h = new HandlerOrderProduct();
						h.name = o.optString("name");
						h.price = o.optString("nowprice");
						h.url = o.optString("url");
						h.id = o.optString("id");
						
						unh.hop.add(h);
					}
					
					orderList.add(unh);
					isSucceed = true;
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			isSucceed = false;
		}
	}
	
	public class HandlerOrderProduct{
		public String name;
		public String price;
		public String url;
		public String id;
	}
	
	public class HandlerOrder{
		public String username;
		public String userphone;
		public String orderid;
		public String addressid;
		public String extmsg;
		public String userid;
		public String shippingname;
		public String shippingphone;
		public String shippingaddress;
		
		
		public List<HandlerOrderProduct> hop = new ArrayList<HandlerOrderProduct>(); 
		
	}
	
	
	
}