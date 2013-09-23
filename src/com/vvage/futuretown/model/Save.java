package com.vvage.futuretown.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.vvage.futuretown.network.Http;
import com.vvage.futuretown.network.Http.ErrorMsg;
import com.vvage.futuretown.util.StringUtil;

public class Save extends BaseJsonReader {

	private String pic="";
	private ReleaseGoods goods;
	public Save(final ReleaseGoods goods){
		 this.goods=goods;
		 this.pic = "";
		for(int i=0;i<goods.goodspic.length;++i){
			if(goods.goodspic[i]!=0){
				pic+=(goods.goodspic[i]+",");
			}
			
		}
		pic = pic.substring(0, pic.length()-1);
		
		String str ="";
		if(StringUtil.isValidStr(User.uid)){
			str = "&uid="+User.uid+"&name="+this.goods.name+"&nowprice="+this.goods.newPrice
					+"&price="+this.goods.price+"&content="+this.goods.content+"&imgs="+this.pic;
			if(User.mIdentify<1){
				str+="&showinfos="+this.goods.address;
			}else{
				str+="&type="+this.goods.type;
			}
		}
		
		urlParams = "mod=seller_api&code=Save"+str;
	}
	
	
	@Override
	public void read(String str) throws ErrorMsg {
		// TODO Auto-generated method stub
		try {
			JSONObject obj = new JSONObject(str);
			if(obj!=null&&obj.optInt("status")==0){
				isSucceed=true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			isSucceed=false;
			e.printStackTrace();
		}
	}
//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		
////		uid	用户id
////		name 商品名称
////		flag 描述
////		nowprice 	现价
////		content	详细内容
////		intro		简介
////		price		原价
////		maincat	分类	整型数字
////		address	int型  地址表主键	后台根据此字段自动关联地图坐标
////		showinfos	若没有address字段，则传递这个字符串即可realname,phone,qq,email 
////		type	ticket:打折商品、show：展示、ask：询价、order:订单
////		imgs	多个图片id，用逗号隔开
////		id 不传或为0为添加新商品，否则为编辑商品
//
//		String str ="";
//		if(StringUtil.isValidStr(User.uid)){
//			str = "uid="+User.uid+"&name="+this.goods.name+"&nowprice="+this.goods.newPrice
//					+"&price="+this.goods.price+"&content="+this.goods.content+"&imgs="+this.pic;
//			if(User.mIdentify==0){
//				str+="&showinfos="+this.goods.address;
//			}else{
//				str+="&type="+this.goods.type;
//			}
//		}
//		return str;
//	}
	
	

}
