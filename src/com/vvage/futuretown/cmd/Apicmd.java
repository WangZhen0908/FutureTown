package com.vvage.futuretown.cmd;

import android.content.Context;
import android.graphics.Bitmap;

import com.vvage.futuretown.model.AddOrUnCollect;
import com.vvage.futuretown.model.AddSeller;
import com.vvage.futuretown.model.AddSellerInfo;
import com.vvage.futuretown.model.Addorder_save;
import com.vvage.futuretown.model.Address;
import com.vvage.futuretown.model.Address_save;
import com.vvage.futuretown.model.AddsubAccount;
import com.vvage.futuretown.model.Cart;
import com.vvage.futuretown.model.CityMerchants;
import com.vvage.futuretown.model.CollectList;
import com.vvage.futuretown.model.GeoCity;
import com.vvage.futuretown.model.LoginParse;
import com.vvage.futuretown.model.MerchantAndGoods;
import com.vvage.futuretown.model.MyEnquirePrice;
import com.vvage.futuretown.model.MyOrder;
import com.vvage.futuretown.model.Order;
import com.vvage.futuretown.model.RegisterParse;
import com.vvage.futuretown.model.ReleaseGoods;
import com.vvage.futuretown.model.Save;
import com.vvage.futuretown.model.SellerInArea;
import com.vvage.futuretown.model.SendAskPrice;
import com.vvage.futuretown.model.SendVCodeParse;
import com.vvage.futuretown.model.ShowSeller;
import com.vvage.futuretown.model.UpLoadImage;
import com.vvage.futuretown.model.askprice;
import com.vvage.futuretown.model.deleteUncheckProduct;
import com.vvage.futuretown.model.doSubAccount;
import com.vvage.futuretown.model.replyPrice;
import com.vvage.futuretown.model.transe_seller;
import com.vvage.futuretown.network.Http;
import com.vvage.futuretown.network.Http.ErrorMsg;

public class Apicmd {
	// private Context mContext;
	private static Apicmd _instance;

	private Apicmd() {

	}

	public static Apicmd getInstance() {
		if (_instance == null) {
			_instance = new Apicmd();
		}
		return _instance;
	}

	/*
	 * 登录
	 */
	public LoginParse Login(Context context, final String phoneNum, final String password) {
		LoginParse login = new LoginParse(phoneNum, password);
		try {
			Http.getInstance().HttpRequest(context, login);
		} catch (ErrorMsg e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return login;
	}
	
	/*
	 * 发送验证码
	 * 0注册 1找密码 2添加小号 3转让 
	 */
	public SendVCodeParse sendVCode(Context context,final String type, final String phone){
		SendVCodeParse sendVCodeParse = new SendVCodeParse(type, phone);
		try {
			Http.getInstance().HttpRequest(context, sendVCodeParse);
		} catch (ErrorMsg e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return sendVCodeParse;
	}
	
	/**
	 * 注册
	 * @param context
	 * @param phoneNum
	 * @param password
	 * @param phone_code
	 * @return
	 */
	public RegisterParse register(Context context,final String phoneNum, 
			final String password, final String phone_code){
		RegisterParse registerParse = new RegisterParse(phoneNum, password, phone_code);
		try {
			Http.getInstance().HttpRequest(context, registerParse);
		} catch (ErrorMsg e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return registerParse;
	}
	
	/*
	 * 获取首页商家list
	 */
	public CityMerchants getCityMerchants(Context context){
		CityMerchants cms = new CityMerchants();
		try {
			Http.getInstance().HttpRequest(context, cms);
		} catch (ErrorMsg e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return cms;
	}
	
	
	/*
	 * 上传图片
	 */
	public UpLoadImage upLoadImage(Context context, final String name, final Bitmap bm){
		UpLoadImage up = new UpLoadImage(name, bm);
//		File file = new File("/sdcard/未来城/1.jpeg");
//		Http.getInstance().uploadStatus(file, "", up.getUrl());
			try {
//				Http.getInstance().HttpRequest(context, up);
				Http.getInstance().uploadFile(context, up);
			} catch (ErrorMsg e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		return up;
	}
	
	/*
	 * 获取商家
	 */
	
	public ShowSeller getShowSeller(Context context, final String id, final String sid){
		ShowSeller ss = new ShowSeller(id, sid);
		try {
			Http.getInstance().HttpRequest(context, ss);
		} catch (ErrorMsg e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return ss;
	}
	
	/*
	 * 首页城市搜索
	 */
	
	public SellerInArea getMerchantsInArea(Context context, final String type, 
			final String city, final String category, final String searchKey){
		SellerInArea s = new SellerInArea(type, city, category, searchKey);
		try {
			Http.getInstance().HttpRequest(context, s);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return s;
	}
	
	/*
	 * 查询城市经纬度
	 */
	public GeoCity getGeoCity(Context context, final String address){
		GeoCity g = new GeoCity(address);
		try {
			Http.getInstance().HttpRequest(context, g);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return g;
	}
	
	/*
	 * 5.2商家详情页面（获取商家信息和商家商品信息）
	 */
	public MerchantAndGoods getMerchantAndGoods(Context context, 
			final String id, final String sid){
		MerchantAndGoods m = new MerchantAndGoods(id, sid);
		try {
			Http.getInstance().HttpRequest(context, m);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return m;
	}
	
	/*
	 * 注册商家
	 */
	public AddSeller getAddSeller(Context context, final AddSellerInfo s){
		AddSeller as = new AddSeller(s);
		try {
			Http.getInstance().HttpRequest(context, as);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return as;
	}
	
	
	/*
	 * 发布商品
	 */
	public Save getSave(Context context, final ReleaseGoods goods){
		Save s = new Save(goods);
		try {
			Http.getInstance().HttpRequest(context, s);
//			Http.getInstance().uploadFile(context, s);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return s;
	}
	
	
	/*
	 * 添加收藏/删除收藏
	 * type 类型:1商品2商家3会员
	 */
	public AddOrUnCollect getAddOrUnCollect(Context context, 
			final String id, final String tid, final String collectType){
		AddOrUnCollect ac = new AddOrUnCollect(id, tid, collectType);
		try {
			Http.getInstance().HttpRequest(context, ac);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return ac;
	}
	
	
	/*
	 * 商家收藏/商品收藏  列表
	 */
	public CollectList getCollectList(Context context, final String type){
		CollectList c = new CollectList(type);
		try {
			Http.getInstance().HttpRequest(context, c);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return c;
	}
	
	/*
	 * 查询某买家全部收货地址
	 */
	public Address getAddress(Context context, final String userUid){
		Address a = new Address(userUid);
		try {
			Http.getInstance().HttpRequest(context, a);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return a;
	}
	
	/*
	 * 添加收货地址
	 */
	public Address_save getAddress_save(Context context,final String id, 
			final int province, final int city, final int country,
			final String address, final String name, 
			final String phone, final String amap){
		Address_save as = new Address_save(id, province, city, country, address, name, phone, amap);
		try {
			Http.getInstance().HttpRequest(context, as);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return as;
	}
	
	/*
	 * 7.2 个人-查询我的订单
	 */
	public MyOrder getMyOrder(Context context, final String type){
		MyOrder mo = new MyOrder(type);
		try {
			Http.getInstance().HttpRequest(context, mo);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return mo;
	}
	
	/*
	 * 7.6.个人-查询我的询价接口
	 * 
	 * 备注：已回复附加字段：一口价、剩余时间、附言
	 * 个人：个人信息发布，有位置坐标
	 * 商家：拍商家、拍商品（没有位置坐标）
	 * 
	 * type	1 未回复，2已回复
	 */
	public MyEnquirePrice getMyEnquirePrice(Context context, final String type){
		MyEnquirePrice mep = new MyEnquirePrice(type);
		try {
			Http.getInstance().HttpRequest(context, mep);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return mep;
	}
	
	/*
	 * 6.添加员工
	 */
	public AddsubAccount getAddsubAccount(Context context,final String vcode, final String phone, 
			final String password, final String email){
		AddsubAccount asa = new AddsubAccount(vcode, phone, password, email);
		try {
			Http.getInstance().HttpRequest(context, asa);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return asa;
	}
	
	/*
	 * 7.删除员工
	 */
	public doSubAccount getdoSubAccount(Context context,final String subId){
		doSubAccount dsa = new doSubAccount(subId);
		try {
			Http.getInstance().HttpRequest(context, dsa);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return dsa;
	}
	
	
	/*
	 * 8.转让店铺
	 */
	public transe_seller gettranse_seller(Context context,
			final String phone, final String vcode){
		transe_seller ts = new transe_seller(phone, vcode);
		try {
			Http.getInstance().HttpRequest(context, ts);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return ts;
	}
	
	/*
	 * 商家处理客户订单
	 * 商家处理订单
		不传或者type=0是未处理
		type=1是已处理
	 */
	public Order getOrder(Context context,final String type){
		Order order = new Order(type);
		try {
			Http.getInstance().HttpRequest(context, order);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return order;
	}
	
	
	/*
	 * 商家处理客户询价
	 * 商家询价接口，type=1是已经回复，不传或者type=0是未回复
	 */
	public askprice getaskprice(Context context,final String type){
		askprice ap = new askprice(type);
		try {
			Http.getInstance().HttpRequest(context, ap);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return ap;
	}
	
	
	/*
	 * 发送询价
	 */
	public SendAskPrice getSendAskPrice(Context context,final String id){
		SendAskPrice sap = new SendAskPrice(id);
		try {
			Http.getInstance().HttpRequest(context, sap);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return sap;
	}
	
	
	/*
	 * 下单
	 */
	public Addorder_save getAddorder_save(Context context,final String userName, final Cart cart){
		Addorder_save aos = new Addorder_save(userName, cart);
		try {
			Http.getInstance().HttpRequest(context, aos);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return aos;
	}
	
	
	/*
	 * 回复询价
	 */
	public replyPrice getreplyPrice(Context context,final String id, final String price, final String msg){
		replyPrice rp = new replyPrice(id, price, msg);
		try {
			Http.getInstance().HttpRequest(context, rp);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return rp;
	}
	
	
	/*
	 * 删除商品
	 */
	public deleteUncheckProduct getdeleteUncheckProduct(
			Context context,final String id){
		deleteUncheckProduct dp = new deleteUncheckProduct(id);
		try {
			Http.getInstance().HttpRequest(context, dp);
		} catch (ErrorMsg e){
			e.printStackTrace();
			return null;
		}
		return dp;
	}
	
}
