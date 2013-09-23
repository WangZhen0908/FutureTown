package com.vvage.futuretown.model;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.vvage.futuretown.network.Http.ErrorMsg;
import com.vvage.futuretown.util.FileUtil;
import com.vvage.futuretown.util.StringUtil;

public class GeoCity extends BaseJsonReader {
	
	private String address;
	public double lat;
	public double lng;
	
	public GeoCity(final String address){
		this.address = address;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "http://maps.googleapis.com/maps/api/geocode/json?address="+StringUtil.HanziToHanyupinyin(address)+"&sensor=true";
	}

	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		try {
			JSONObject o = new JSONObject(str);
			String status = o.optString("status");
			if(StringUtil.isValidStr(status) && status.equals("OK")){
				JSONArray rArray = o.getJSONArray("results");
				for(int i=0;i< rArray.length();++i){
					JSONObject item =(JSONObject) rArray.get(i);
					
					if(item!=null){
						String geo = item.optJSONObject("geometry").optString("location");
						if(StringUtil.isValidStr(geo)){
							isSucceed = true;
							JSONObject ll = new JSONObject(geo);
							lat = ll.optDouble("lat");
							lng = ll.optDouble("lng");
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
