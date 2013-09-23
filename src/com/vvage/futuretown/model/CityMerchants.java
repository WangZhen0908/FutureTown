package com.vvage.futuretown.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.test.IsolatedContext;
import android.util.Log;

import com.vvage.futuretown.Global;
import com.vvage.futuretown.network.Http.ErrorMsg;
import com.vvage.futuretown.util.LogUtil;
import com.vvage.futuretown.util.StringUtil;

public class CityMerchants extends BaseJsonReader {
	
	public String status;
	public String msg;
	public List<HashMap<String, List<City>>> citylist;
	public HashMap<String, List<City>> hotcity;
	
	public CityMerchants(){
		urlParams = "mod=index_api&code=City";
	}

	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		// super.read(str);
		try {
			 JSONObject jsonObj = new JSONObject(str);
			 this.status = jsonObj.optString("status");
			 if(status!=null && status.equals("0")){
				 isSucceed = true;
				 String data = jsonObj.optString("data");
				 if(data!=null){
					 String cityliststr = new JSONObject(data).optString("city_list");
					 JSONObject citylist = new JSONObject(cityliststr);
					 @SuppressWarnings("unchecked")
					Iterator<String> it = citylist.keys();
					 while(it.hasNext()){
						 String key = it.next();
						 String value = citylist.getString(key);
						 
						 List<City> citys = new ArrayList<CityMerchants.City>();
						 parseArray(value, citys);
						 
						 HashMap<String, List<City>> clMap = new HashMap<String, List<City>>();
						 
						 clMap.put(key, citys);
						 
						 if(this.citylist==null){
							 this.citylist = new ArrayList<HashMap<String, List<City>>>();
						 }
						 this.citylist.add(clMap);
					 }
					 
					 
					 
					 
					 List<City> citys = new ArrayList<CityMerchants.City>();
					 parseArray(new JSONObject(data).optString("hot_city"), citys);
					 if(hotcity==null){
						 hotcity = new HashMap<String, List<City>>();
					 }
					 hotcity.put("hot_city", citys);
				 }
				 
			 }
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.d("ft", "parse error");
			e.printStackTrace();
		}
	}
	
	private void parseArray(String str, List<City> cityList){
		if(StringUtil.isValidStr(str)){
			try {
				JSONArray array = new JSONArray(str);
				int length = array.length();
				for(int i=0; i<length; i++){
					JSONObject item = array.getJSONObject(i);
					City c = new City();
					c.cityid = item.optString("cityid");
					c.cityname = item.optString("cityname");
					c.shorthand = item.optString("shorthand");
					c.display = item.optString("display");
					c.czone = item.optString("czone");
					c.letter = item.optString("letter");
					c.sort_order = item.optString("sort_order");
					cityList.add(c);
					LogUtil.d("JSON", "City = "+c.toString());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public class City {
		public String cityid;
		public String cityname;
		public String shorthand;
		public String display;
		public String czone;
		public String letter;
		public String sort_order;
	}

}

/*
 * {"status":0,"msg":"ok",
 * "data":{"city_list":{"B":[{"cityid":"6","cityname":"\u5317\u4eac","shorthand":"beijing","display":"1","czone":"\u5317\u4eac\u5e02","letter":"B","sort_order":"5000"}],
 * 						"D":[{"cityid":"16","cityname":"\u5927\u8fde","shorthand":"dalian","display":"1","czone":"\u8fbd\u5b81","letter":"D","sort_order":"0"},{"cityid":"18","cityname":"\u9093\u5dde","shorthand":"dengzhou","display":"1","czone":"\u6cb3\u5357","letter":"D","sort_order":"0"}],
 * 						"E":[{"cityid":"14","cityname":"\u6069\u65bd","shorthand":"enshi","display":"1","czone":"\u6e56\u5317","letter":"E","sort_order":"0"}],
 * 						"F":[{"cityid":"24","cityname":"\u4f5b\u5c71","shorthand":"fuoshan","display":"1","czone":"\u5e7f\u4e1c","letter":"F","sort_order":"0"}],
 * 						"G":[{"cityid":"9","cityname":"\u5e7f\u5dde","shorthand":"guangzhou","display":"1","czone":"\u5e7f\u4e1c","letter":"G","sort_order":"0"},{"cityid":"17","cityname":"\u8d35\u9633","shorthand":"guiyang","display":"1","czone":"\u8d35\u5dde","letter":"G","sort_order":"0"}],
 * 						"H":[{"cityid":"4","cityname":"\u676d\u5dde","shorthand":"hangzhou","display":"1","czone":"\u6d59\u6c5f\u7701","letter":"H","sort_order":"0"},{"cityid":"7","cityname":"\u6d77\u53e3","shorthand":"haikou","display":"1","czone":"\u6d77\u5357","letter":"H","sort_order":"0"},{"cityid":"12","cityname":"\u5408\u80a5","shorthand":"hefei","display":"1","czone":"\u5b89\u5fbd","letter":"H","sort_order":"0"}],
 * 						"J":[{"cityid":"19","cityname":"\u96c6\u5b89","shorthand":"jian","display":"1","czone":"\u5409\u6797","letter":"J","sort_order":"0"},{"cityid":"22","cityname":"\u664b\u57ce","shorthand":"jincheng","display":"1","czone":"\u5c71\u897f","letter":"J","sort_order":"0"}],"L":[{"cityid":"10","cityname":"\u62c9\u8428","shorthand":"lasa","display":"1","czone":"\u897f\u85cf","letter":"L","sort_order":"0"}],"N":[{"cityid":"2","cityname":"\u5357\u9633","shorthand":"nanyang","display":"1","czone":"\u6cb3\u5357\u7701","letter":"N","sort_order":"5000"},{"cityid":"21","cityname":"\u5357\u660c","shorthand":"nanchang","display":"1","czone":"\u6c5f\u897f","letter":"N","sort_order":"0"}],"S":[{"cityid":"3","cityname":"\u4e0a\u6d77","shorthand":"shanghai","display":"1","czone":"\u4e0a\u6d77\u5e02","letter":"S","sort_order":"100"},{"cityid":"15","cityname":"\u4e09\u4e9a","shorthand":"sanya","display":"1","czone":"\u6d77\u5357","letter":"S","sort_order":"0"},{"cityid":"25","cityname":"\u6df1\u5733","shorthand":"shenzhen","display":"1","czone":"\u5e7f\u4e1c","letter":"S","sort_order":"0"}],"W":[{"cityid":"11","cityname":"\u6b66\u6c49","shorthand":"wuhan","display":"1","czone":"\u6e56\u5317","letter":"W","sort_order":"0"}],"X":[{"cityid":"13","cityname":"\u5f90\u5dde","shorthand":"xuzhou","display":"1","czone":"\u6c5f\u82cf","letter":"X","sort_order":"0"},{"cityid":"23","cityname":"\u6e58\u6f6d","shorthand":"xiangtan","display":"1","czone":"\u6e56\u5357","letter":"X","sort_order":"0"}],"Y":[{"cityid":"8","cityname":"\u4e49\u4e4c","shorthand":"yiwu","display":"1","czone":"\u6d59\u6c5f\u7701","letter":"Y","sort_order":"0"}],
 * 						"Z":[{"cityid":"20","cityname":"\u90d1\u5dde","shorthand":"zhengzhou","display":"1","czone":"\u6cb3\u5357","letter":"Z","sort_order":"0"}]},
 * "hot_city":[{"cityid":"2","cityname":"\u5357\u9633","shorthand":"nanyang","display":"1","czone":"\u6cb3\u5357\u7701","letter":"N","sort_order":"5000"},
 * {"cityid":"6","cityname":"\u5317\u4eac","shorthand":"beijing","display":"1","czone":"\u5317\u4eac\u5e02","letter":"B","sort_order":"5000"}]}}
 *
 * 
 */