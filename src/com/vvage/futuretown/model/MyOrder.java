package com.vvage.futuretown.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.vvage.futuretown.model.Order.HandlerOrder;
import com.vvage.futuretown.model.Order.HandlerOrderProduct;
import com.vvage.futuretown.network.Http.ErrorMsg;

public class MyOrder extends BaseJsonReader {

	public List<PersonalOrder> orderList = new ArrayList<MyOrder.PersonalOrder>();
	private String type="0";
	
	public MyOrder(final String type){
		this.type = type;
//		http://www.vvage.com/?mod=me_api&code=Order&uid=10
		urlParams = "mod=me_api&code=Order&uid="+User.uid+"&type="+type;
	}

	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		try {
			JSONObject obj = new JSONObject(str);
			if(obj!=null&&obj.optInt("status")==0){
				JSONTokener jsonParser = new JSONTokener(str);
				JSONObject next = (JSONObject) jsonParser.nextValue();
				JSONArray array = next.getJSONArray("order_all");
				int count = array.length();
				for(int i=0;i<count;++i){
					JSONObject item = array.getJSONObject(i);
					Iterator<String> it = item.keys();
					
					PersonalOrder po = new PersonalOrder();
					while(it.hasNext()){
						String key = it.next();
						String value = item.optString(key);
						
						if(key.equals("sellername")){
							po.sellername=value;
							continue;
						}
						if(key.equals("orderid")){
							po.sellersn=value;
							continue;
						}
						if(key.equals("sellerphone")){
							po.sellerphone=value;
							continue;
						}
						if(key.equals("totalprice")){
							po.totalprice=value;
							continue;
						}
						if(key.equals("userid")){
							po.userid=value;
							continue;
						}
						if(key.equals("name")){
							po.shippingname=value;
							continue;
						}
						if(key.equals("phone")){
							po.shippingphone=value;
							continue;
						}
						if(key.equals("sellerregion")){
							po.selleraddress=value;
							continue;
						}
						if(key.equals("selleraddress")){
							po.selleraddress+=value;
							continue;
						}
						if(key.equals("region")){
							po.shippingaddress=value;
							continue;
						}
						if(key.equals("address")){
							po.shippingaddress+=value;
							continue;
						}
						
//						po.sellerurl=item.optString("");
						if(key.equals("product")){
//							value = (String) value.subSequence(1, value.length()-1);
							JSONObject item_P = new JSONObject(value);
							@SuppressWarnings("unchecked")
							Iterator<String> it_p = item_P.keys();
							PersonalOrderProduct p = new PersonalOrderProduct();
							while(it_p.hasNext()){
								String key_p = it_p.next();
								String value_p = item_P.optString(key_p);
								if(key_p.equals("name")){
									p.name=value_p;
									continue;
								}
								if(key_p.equals("nowprice")){
									p.price=value_p;
									continue;
								}
								if(key_p.equals("url")){
									p.url=value_p;
									continue;
								}
							}
							po.pop.add(p);
							continue;
						}
						
					}
					
					orderList.add(po);
					isSucceed = true;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isSucceed = false;
		}
	}
	
	public class PersonalOrderProduct{
		public String name;
		public String price;
		public String url;
	}
	
	public class PersonalOrder{
		public String sellerurl="";
		public String sellername;
		public String sellersn;
		public String selleraddress;
		public String sellerphone;
		public String totalprice;//订单总额
		
		public String addressid;
		public String extmsg;
		public String userid;
		public String shippingname;
		public String shippingaddress;
		public String shippingphone;
		public List<PersonalOrderProduct> pop = new ArrayList<PersonalOrderProduct>(); 
		
	}

}
