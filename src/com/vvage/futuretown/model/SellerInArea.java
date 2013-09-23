package com.vvage.futuretown.model;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.vvage.futuretown.network.Http.ErrorMsg;
import com.vvage.futuretown.util.LogUtil;
import com.vvage.futuretown.util.StringUtil;

public class SellerInArea extends BaseJsonReader {

	public int status;
	public int num;
	public List<Seller> sellerList;
//	public List<HashMap<String, Seller>> sellerList;

	private String type = "2";
	private String category = "";// 空，默认全部
	private String searchKey = "";

	public SellerInArea(final String type, final String city, 
			final String category,
			final String searchKey) {
		this.type = type;
		this.category = category;
		this.searchKey = searchKey;

//		http://www.vvage.com/?mod=index_api&code=main&type=2&city=nanyang&category=2&search=beijing
		urlParams = "mod=index_api&code=main&type=" + type + "&city=" + city
				+ "&category=" + category + "&search=" + searchKey;
	}

	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		// super.read(str);
		LogUtil.d("JSON", "MerchantsInArea = " + str);
		try {
			JSONObject o = new JSONObject(str);
			this.status = o.optInt("status");
			if (status==0) {
				JSONObject data = new JSONObject(o.optString("data"));
				this.num = data.optInt("num");
				sellerList = new ArrayList<Seller>();
				parseArray(data.optString("seller"), sellerList);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void parseArray(String str, List<Seller> sellerList) {
		if (StringUtil.isValidStr(str)) {
			try {
				JSONObject obj = new JSONObject(str);
				Iterator<String> it = obj.keys();
				while (it.hasNext()) {
					String key = it.next();
					String value = obj.getString(key);
					if(StringUtil.isValidStr(value)){
						JSONObject item = new JSONObject(value);
						
						Seller s = new Seller();
						s.address = item.optString("address");
						s.cat = item.optString("cat");
						s.click = item.optString("click");
						s.name = item.optString("name");
						s.num = item.optInt("num");
						s.phone = item.optString("phone");
						s.scale = item.optString("scale");
						s.sid = item.optString("sid");
						s.snum = item.optInt("snum");
						s.spic = item.optString("spic");
						s.stype = item.optInt("stype");
						s.tag = item.optString("tag");
						s.time = item.optString("time");
						s.url = item.optString("url");
						s.zuobiao = item.optString("zuobiao");
						
//						HashMap<String, Seller> smap = new HashMap<String, SellerInArea.Seller>();
//						smap.put(key, s);
						sellerList.add(s);
					}

				}
				
				Collections.sort(sellerList, cwjComparator);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public class Seller {
		public String address;
		public String cat;
		public String click;
		public String name;
		public int num;
		public String phone;
		public String scale;
		public String sid;
		public int snum;
		public String spic;
		public int stype;
		public String tag;
		public String time;
		public String url;
		public String zuobiao;
		public double[] getlonlat(){
			double[] lonlat = new double[2];
			if(StringUtil.isValidStr(zuobiao)){
				zuobiao.trim();
				String str = zuobiao;
				int offset = -1;
				int pre = 0;
				List<Integer> offsets = new LinkedList<Integer>();
				while((offset = str.indexOf(","))!=-1){
					offsets.add(Integer.valueOf(pre+offset));
					pre = offset;
					str = str.substring(offset+1);
				}
				
				if(offsets.size()>0){
					int off = offsets.get(0).intValue();
					lonlat[0]=Double.valueOf(zuobiao.substring(0, off));
					if(offsets.size()==1){
						lonlat[1]=Double.valueOf(zuobiao.substring(off+1));
					} else if(offsets.size()>1){
						lonlat[1]=Double.valueOf(zuobiao.substring(off+1, offsets.get(1)));
					}
					return lonlat;
				}
				
			}
			return lonlat;
		}
		
	}
	
	private final Comparator cwjComparator = new Comparator() {

        private final Collator   collator = Collator.getInstance();
        public final int compare(Object a, Object b) {
            return (int) (((Seller)b).getlonlat()[1]*(1E6)-((Seller)a).getlonlat()[1]*(1E6));
        }

    };
    
}
