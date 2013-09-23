package com.vvage.futuretown.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vvage.futuretown.network.Http.ErrorMsg;

/*
 * 查询某买家全部收货地址
 */
public class Address extends BaseJsonReader {
	
	public List<AddressList> addressList = new ArrayList<AddressList>();
	
	public Address(final String userUid){
		urlParams = "mod=me_api&code=Address&uid="+userUid;
	}
	
	public String[] addressArray(){
		String address[]={};
		if(addressList!=null){
			address=new String[addressList.size()];
			for(int i=0;i<addressList.size();++i){
				address[i] = addressList.get(i).region+addressList.get(i).address;
			}
		}
		return address;
	}
	
	public String[] addressDetail(){
		String address[]={};
		if(addressList!=null){
			address=new String[addressList.size()];
			for(int i=0;i<addressList.size();++i){
				AddressList l = addressList.get(i);
				address[i] = l.name+"-"+l.phone+"-"+l.region+l.address;
			}
		}
		return address;
	}
	
	

	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		try {
			JSONObject s = new JSONObject(str);
			if(s.optInt("status")==0){
				JSONArray array = s.getJSONArray("address_list");
				int count = array.length();
				for(int i=0;i<count;++i){
					isSucceed = true;
					JSONObject item = array.getJSONObject(i);
					if(item!=null){
						AddressList a = new AddressList();
						a.address = item.optString("address");
						a.amap = item.optString("amap");
						a.id = item.optString("id");
						a.lastuse = item.optString("lastuse");
						a.loc_city = item.optString("loc_city");
						a.loc_country = item.optString("loc_country");
						a.loc_province = item.optString("loc_province");
						a.name = item.optString("name");
						a.owner = item.optString("owner");
						a.phone = item.optString("phone");
						a.region = item.optString("region");
						a.region_loc = item.optString("region_loc");
						a.zip = item.optString("zip");
						addressList.add(a);
						isSucceed=true;
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			isSucceed=false;
			e.printStackTrace();
		}
	}
	
	public class AddressList{
		public String address;
		public String amap;
		public String id;
		public String lastuse;
		public String loc_city;
		public String loc_country;
		public String loc_province;
		public String name;
		public String owner;
		public String phone;
		public String region;
		public String region_loc;
		public String zip;
		
	}

}
