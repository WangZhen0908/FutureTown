package com.vvage.futuretown.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.vvage.futuretown.network.Http.ErrorMsg;

public class MerchantAndGoods extends BaseJsonReader {

	public Seller seller;
	public List<Goods> goods;
	
	public MerchantAndGoods(final String id, final String sid){
//		www.vvage.com/?mod=index_api&code=Seller&id=&sid=1114
		urlParams = "mod=index_api&code=Seller&id="+id+"&sid="+sid;
	}




	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		try {
			JSONObject s = new JSONObject(str);
			int st = s.optInt("status");
			if(st==0){
				JSONObject obj = s.optJSONObject("data");
				if(obj!=null){
					isSucceed=true;
					JSONObject sellerObj = obj.optJSONObject("seller");
					parseSeller(sellerObj);
					JSONArray p = obj.getJSONArray("products");
					parseGoods(p);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			isSucceed = false;
		}
	}
	
	private void parseSeller(JSONObject sObj){
		if(sObj!=null){
			seller = new Seller();
			seller.addresscode = sObj.optString("addresscode");
			seller.area = sObj.optString("area");
			seller.bail_money = sObj.optString("bail_money");
			seller.check = sObj.optString("check");
			seller.clickcount = sObj.optString("clickcount");
			seller.field1 = sObj.optString("field1");
			seller.field2 = sObj.optString("field2");
			seller.id = sObj.optString("id");
			seller.maincat = sObj.optString("maincat");
			seller.mainservice = sObj.optString("mainservice");
			seller.money = sObj.optString("money");
			seller.productnum = sObj.optString("productnum");
			seller.publishtype = sObj.optString("publishtype");
			seller.scale = sObj.optString("scale");
			seller.selleraddress = sObj.optString("selleraddress");
			seller.sellerintro = sObj.optString("sellerintro");
			seller.sellerlevel = sObj.optString("sellerlevel");
			seller.sellermap = sObj.optString("sellermap");
			seller.sellername = sObj.optString("sellername");
			seller.sellerphone = sObj.optString("sellerphone");
			seller.sellerpic = sObj.optString("sellerpic");
			
			JSONObject regoin = sObj.optJSONObject("sellerregion");
			if(regoin!=null){
				seller.sellerregion = new SellerRegion();
				seller.sellerregion.loc_city = regoin.optString("loc_city");
				seller.sellerregion.loc_country = regoin.optString("loc_country");
				seller.sellerregion.loc_province = regoin.optString("loc_province");
				seller.sellerregion.region = regoin.optString("region");
				seller.sellerregion.region_loc = regoin.optString("region_loc");
			}
			
			seller.sellerroute = sObj.optString("sellerroute");
			seller.sellertime = sObj.optString("sellertime");
			seller.sellerurl = sObj.optString("sellerurl");
			seller.sms_num = sObj.optString("sms_num");
			seller.sorder = sObj.optString("sorder");
			seller.stype = sObj.optString("stype");
			seller.successnum = sObj.optString("successnum");
			seller.time = sObj.optString("time");
			seller.userid = sObj.optString("userid");
			seller.imgUrl = sObj.optString("url");
			
		}
	}
	
	public class SellerRegion{
		public String loc_city;
		public String loc_country;
		public String loc_province;
		public String region;
		public String region_loc;
	}
	
	public class Seller{
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
		public SellerRegion sellerregion;
		public String sellerroute;
		public String sellertime;
		public String sellerurl;
		public String sms_num;
		public String sorder;
		public String stype;
		public String successnum;
		public String time;
		public String userid;
		public String imgUrl;

	}
	
	private void parseGoods(JSONArray gObjArray){
		if(gObjArray!=null){
			int count = gObjArray.length();
			for(int i = 0; i< count; ++i){
				try {
					JSONObject item = gObjArray.getJSONObject(i);
					Goods g = new Goods();
					g.addtime = item.optString("addtime");
					g.allinone = item.optString("allinone");
					g.allownum = item.optString("allownum");
					g.begintime = item.optString("begintime");
					g.category = item.optString("category");
					g.city = item.optString("city");
					g.cityname = item.optString("cityname");
					g.clickcount = item.optString("clickcount");
					g.content = item.optString("content");
					g.cue = item.optString("cue");
					g.discount = item.optString("discount");
					g.display = item.optString("display");
					g.draft = item.optString("draft");
					g.flag = item.optString("flag");
					
					g.hasgetphone = item.optInt("hasgetphone");
					g.hasgettype1 = item.optInt("hasgettype1");
					g.hasgettype2 = item.optInt("hasgettype2");
					g.hasgettype3 = item.optInt("hasgettype3");
					
					g.id = item.optString("id");
					g.img = item.optString("img");
					
					JSONArray imgObjArray = item.getJSONArray("imgs");
					int imgCount = imgObjArray.length();
					
					for(int j=0;j<imgCount;++j){
						if(g.imgs==null){
							g.imgs = new ArrayList<String>();
						}
						String itemstr = (String)imgObjArray.get(j);
						g.imgs.add(itemstr);
//						Iterator<String> it = imgItem.keys();
//						while(it.hasNext()){
//							String k = it.next();
//							String v = imgItem.optString(k);
//							if(g.imgs==null){
//								g.imgs = new ArrayList<String>();
//							}
//							g.imgs.add(v);
//						}
					}
					
					g.intro = item.optString("intro");
					g.invite_reward = item.optBoolean("invite_reward");
					g.is_new_pro = item.optInt("is_new_pro");
					g.level = item.optString("level");
					g.maxnum = item.optString("maxnum");
					g.multibuy = item.optString("multibuy");
					g.name = item.optString("name");
					g.nowprice = item.optInt("nowprice");
					g.oncemax = item.optString("oncemax");
					g.oncemin = item.optString("oncemin");
					g.order = item.optString("order");
					g.overtime = item.optString("overtime");
					g.peoplenum = item.optString("peoplenum");
					g.perioddate = item.optString("perioddate");
					g.pmap = item.optString("pmap");
					g.price = item.optInt("price");
					g.prize_address = item.optString("prize_address");
					g.prize_phone = item.optString("prize_phone");
					g.product_no = item.optString("product_no");
					g.saveHandler = item.optString("saveHandler");
					g.selleraddress = item.optString("selleraddress");
					g.sellerid = item.optString("sellerid");
					g.sellerlevel = item.optString("sellerlevel");
					g.sellermap = item.optString("sellermap");
					g.sellername = item.optString("sellername");
					g.sellerphone = item.optString("sellerphone");
					g.sellerregion = item.optString("sellerregion");
					g.sellerroute = item.optString("sellerroute");
					g.sellertime = item.optString("sellertime");
					g.sellerurl = item.optString("sellerurl");
					g.sells_count = item.optInt("sells_count");
					g.sells_real = item.optInt("sells_real");
					g.shopid = item.optString("shopid");
					g.showbig = item.optString("showbig");
					g.show_time = item.optString("show_time");
					g.sms_num = item.optString("sms_num");
					g.status = item.optString("status");
					g.successnum = item.optString("successnum");
					g.succ_buyers = item.optInt("succ_buyers");
					g.succ_real = item.optInt("succ_real");
					g.succ_remain = item.optInt("succ_remain");
					g.succ_total = item.optString("succ_total");
					g.surplus = item.optInt("surplus");
					g.theysay = item.optString("theysay");
					g.time_remain = item.optString("time_remain");
					g.totalnum = item.optString("totalnum");
					g.type = item.optString("type");
					g.usenum = item.optString("usenum");
					g.username = item.optString("username");
					g.virtualnum = item.optString("virtualnum");
					g.weight = item.optString("weight");
					g.wesay = item.optString("wesay");
					g.win_code = item.optString("win_code");
					g.win_status = item.optString("win_status");
					g.url = item.optString("url");
					if(goods==null){
						goods = new ArrayList<MerchantAndGoods.Goods>();
					}
					goods.add(g);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public class Goods{
		public String addtime;
		public String allinone;
		public String allownum;
		public String begintime;
		public String category;
		public String city;
		public String cityname;
		public String clickcount;
		public String content;
		public String cue;
		public String discount;
		public String display;
		public String draft;
		public String flag;
		public int hasgetphone;
		public int hasgettype1;
		public int hasgettype2;
		public int hasgettype3;
		public String id;
		public String img;
		public List<String> imgs;
		public String intro;
		public boolean invite_reward;
		public int is_new_pro;
		public String level;
		public String maxnum;
		public String multibuy;
		public String name;
		public int nowprice;
		public String oncemax;
		public String oncemin;
		public String order;
		public String overtime;
		public String peoplenum;
		public String perioddate;
		public String pmap;
		public int price;
		public String prize_address;
		public String prize_phone;
		public String product_no;
		public String saveHandler;
		public String selleraddress;
		public String sellerid;
		public String sellerlevel;
		public String sellermap;
		public String sellername;
		public String sellerphone;
		public String sellerregion;
		public String sellerroute;
		public String sellertime;
		public String sellerurl;
		public int sells_count;
		public int sells_real;
		public String shopid;
		public String showbig;
		public String show_time;
		public String sms_num;
		public String status;
		public String successnum;
		public int succ_buyers;
		public int succ_real;
		public int succ_remain;
		public String succ_total;
		public int surplus;
		public String theysay;
		public String time_remain;
		public String totalnum;
		public String type;
		public String usenum;
		public String username;
		public String virtualnum;
		public String weight;
		public String wesay;
		public String win_code;
		public String win_status;
		public String url;
	}

}
