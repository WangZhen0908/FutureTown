package com.vvage.futuretown.model;

import java.net.URLEncoder;

import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.vvage.futuretown.network.Http.ErrorMsg;
import com.vvage.futuretown.util.StringUtil;

public class AddSeller extends BaseJsonReader {

	public AddSeller(final AddSellerInfo seller){
		String pic = "";
		for(int i=0;i<seller.sellerpic.length;++i){
			if(seller.sellerpic[i]!=0){
				pic+=(seller.sellerpic[i]+",");
			}
			
		}
		pic = pic.substring(0, pic.length()-1);
		urlParams = "mod=seller_api&code=Addseller&seller[uid]:="+User.uid+"&seller[sellername]:="+seller.sellername
				+"&seller[sellerpic]:="+pic+"&seller[sellerphone]:="+seller.sellerphone
				+"&seller[sellermap]:="+seller.sellermap[0]+","+seller.sellermap[1]
				+"&seller[maincat]:="+seller.maincat+"&seller[sellerintro]:="+seller.sellerintro+"&seller[stype]:="+seller.stype
				+"&seller[area]:="+seller.area+"&seller[province]:="+seller.province+"&seller[city]:="+seller.city+"&seller[country]:="+seller.country
				+"&seller[selleraddress]:="+seller.selleraddress+"&seller[sellertime]:="+seller.sellertime+"&seller[sellerroute]:="+seller.sellerroute
				+"&seller[sellerurl]:="+seller.sellerurl;
	}



	@Override
	public void read(String str){
		// TODO Auto-generated method stub
		try {
			JSONObject obj = new JSONObject(str);
			if(obj!=null&&obj.optString("status").length()>0){
				isSucceed=true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			isSucceed=false;
			e.printStackTrace();
		}
		
		
	}

}
