package com.vvage.futuretown.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.vvage.futuretown.network.Http.ErrorMsg;
import com.vvage.futuretown.util.StringUtil;

public class CollectList extends BaseJsonReader {

	public List<CollectGoods> favorgoods = new ArrayList<CollectList.CollectGoods>();
	public List<CollectSeller> favorseller  = new ArrayList<CollectList.CollectSeller>();
	
	private String type;
//	type 类型:1商品2商家3会员
	public CollectList(final String type){
//		www.vvage.com/?mod=me_api&code=collect&type=1&uid=10
		this.type = type;
		urlParams = "mod=me_api&code=collect&type="+type+"&uid="+User.uid;
	}

	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		try {
			int s = new JSONObject(str).optInt("status");
			if(s == 0){
				JSONTokener jsonParser = new JSONTokener(str);
				JSONObject next = (JSONObject) jsonParser.nextValue();
				JSONArray collectList = next.getJSONArray("shoucang");
				int count = collectList.length();
				for(int i=0; i< count; ++i){
					JSONObject item = collectList.getJSONObject(i);
					if(this.type.equals("1")){
						CollectGoods goods = new CollectGoods();
						goods.addtime = item.optString("addtime");
						goods.allownum = item.optString("allownum");
						goods.ctype = item.optString("ctype");
						goods.id = item.optString("id");
						goods.img = item.optString("img");
						goods.name = item.optString("name");
						goods.nowprice = item.optString("nowprice");
						goods.psid = item.optString("psid");
						goods.type = item.optString("type");
						goods.uid = item.optString("uid");
						
						JSONObject urlObj = item.optJSONObject("urls");
						if(urlObj!=null){
							goods.url = urlObj.optString("url");
						}
						
						favorgoods.add(goods);
					}else if(this.type.equals("2")){
						CollectSeller cs = new CollectSeller();
						cs.addtime = item.optString("addtime");
						cs.ctype = item.optString("ctype");
						cs.id = item.optString("id");
						cs.psid = item.optString("psid");
						cs.sellername = item.optString("sellername");
						cs.sellerpic = item.optString("sellerpic");
						cs.uid = item.optString("uid");
						JSONObject urlObj = item.optJSONObject("urls");
						if(urlObj!=null){
							cs.url = urlObj.optString("url");
						}
						
						favorseller.add(cs);
					}
					isSucceed = true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			isSucceed = false;
		}
	}
	
	public class CollectSeller{
		public String addtime;
		public String ctype;
		public String id;
		public String[] imgs;
		public String psid;
		public String sellername;
		public String sellerpic;
		public String uid;
		public String url;
	}
	
	public class CollectGoods{
		public String addtime;
		public String allownum;
		public String ctype;
		public String id;
		public String img;
		public String[] imgs;
		public String name;
		public String nowprice;
		public String psid;
		public String type;
		public String uid;
		public String url;
	}

}
