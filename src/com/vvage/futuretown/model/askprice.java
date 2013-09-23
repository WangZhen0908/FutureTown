package com.vvage.futuretown.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.vvage.futuretown.model.Order.HandlerOrderProduct;
import com.vvage.futuretown.network.Http.ErrorMsg;


/*
 * 商家处理客户询价
 */
public class askprice extends BaseJsonReader {

	public List<UnReply> unReplyList = new ArrayList<UnReply>();
	public List<Reply> replyList = new ArrayList<Reply>();
	
	private String type="";
	public askprice(final String type){
		this.type = type;
/*
 * 		http://www.vv.com/?mod=seller_api&code=askprice&uid=100&type=1 
		商家询价接口，type=1是已经回复，不传或者type=0是未回复
 */
		urlParams = "mod=seller_api&code=askprice&uid="+User.uid+"&type="+type;
	}
	
	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		try {
			JSONObject obj = new JSONObject(str);
			if(obj!=null&&obj.optInt("status")==0){
				JSONTokener jsonParser = new JSONTokener(str);
				JSONObject next = (JSONObject) jsonParser.nextValue();
				JSONArray array = next.getJSONArray("notelist");
				int count = array.length();
				for(int i=0;i<count;++i){
					JSONObject item = array.getJSONObject(i);
					if(type.equals("0")){
						UnReply unr = new UnReply();
						unr.addtime = item.optString("addtime");
						unr.author = item.optString("author");
						unr.authorid = item.optString("authorid");
						unr.dateline = item.optString("dateline");
						unr.id = item.optString("id");
						unr.img = item.optString("img");
						unr.name = item.optString("name");
						unr.new_t = item.optString("new");
						unr.note = item.optString("note");
						unr.nowprice = item.optString("nowprice");
						unr.phone = item.optString("phone");
						unr.pid = item.optString("pid");
						unr.replymsg = item.optString("replymsg");
						unr.replyprice = item.optString("replyprice");
						unr.replytime = item.optString("replytime");
						unr.type = item.optString("type");
						unr.uid = item.optString("uid");
						unr.username = item.optString("username");
						
						JSONArray urlarray = item.optJSONArray("urls");
						int urlcount = urlarray.length();
						for(int j=0;j<urlcount;++j){
							JSONObject urlItem = urlarray.getJSONObject(j);
							unr.url = urlItem.optString("url");
						}
						
						unReplyList.add(unr);
						isSucceed = true;
					}else if(type.equals("1")){
						Reply r = new Reply();
						r.addtime = item.optString("addtime");
						r.author = item.optString("author");
						r.authorid = item.optString("authorid");
						r.dateline = item.optString("dateline");
						r.id = item.optString("id");
						r.img = item.optString("img");
						r.name = item.optString("name");
						r.new_t = item.optString("new");
						r.note = item.optString("note");
						r.nowprice = item.optString("nowprice");
						r.phone = item.optString("phone");
						r.pid = item.optString("pid");
						r.replymsg = item.optString("replymsg");
						r.replyprice = item.optString("replyprice");
						r.replytime = item.optString("replytime");
						r.type = item.optString("type");
						r.uid = item.optString("uid");
						r.username = item.optString("username");
						r.remaintime = item.optString("remaintime");
						
						JSONArray urlarray = item.optJSONArray("urls");
						int urlcount = urlarray.length();
						for(int j=0;j<urlcount;++j){
							JSONObject urlItem = urlarray.getJSONObject(j);
							r.url = urlItem.optString("url");
						}
						
						replyList.add(r);
						isSucceed = true;
					}
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isSucceed = false;
		}
	}
	
	public class UnReply{
		public String addtime;
		public String author;
		public String authorid;
		public String dateline;
		public String id;
		public String img;
		public String[] imgs;
		public String name;
		public String new_t;
		public String note;
		public String nowprice;
		public String phone;
		public String pid;
		public String replymsg;
		public String replyprice;
		public String replytime;
		public String type;
		public String uid;
		public String username;
		public String url;
	}
	
	public class Reply extends UnReply{
		public String remaintime;
	}
	

}
