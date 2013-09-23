package com.vvage.futuretown.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.vvage.futuretown.BaseFragment;
import com.vvage.futuretown.Global;
import com.vvage.futuretown.HomeActivity;
import com.vvage.futuretown.R;
import com.vvage.futuretown.activity.LocationForApi;
import com.vvage.futuretown.activity.LoginListener;
import com.vvage.futuretown.cmd.Apicmd;
import com.vvage.futuretown.model.AddSeller;
import com.vvage.futuretown.model.AddSellerInfo;
import com.vvage.futuretown.model.Address;
import com.vvage.futuretown.model.PCCModel;
import com.vvage.futuretown.model.ReleaseGoods;
import com.vvage.futuretown.model.Save;
import com.vvage.futuretown.model.UpLoadImage;
import com.vvage.futuretown.model.User;
import com.vvage.futuretown.util.DoAsyncTask;
import com.vvage.futuretown.util.DoAsyncTask.DoTask;
import com.vvage.futuretown.util.DoAsyncTask.PostResult;
import com.vvage.futuretown.util.LogUtil;
import com.vvage.futuretown.util.MemoryStatus;
import com.vvage.futuretown.util.StringUtil;

public class MessageFragment extends BaseFragment {
	private final int PHOTO_MERCHANT=0;
	private final int PHOTO_GOODS=1;
	
	private int type;
	
	/*
	 * 调用系统相机相关参数
	 */
	private final int IMAGE_CAPTURE = 0xF1;
	private final int IMAGE_LOCAL = 0xF2;
	private final int IMAGE_SCALE = 0xF3;
	private final String BITMAP_PROP_NAME = "data";
	
	private int imageFrom;
	
	private final int fPicWidth = 100;
	private Uri imageUrl;
	private Bitmap bitMap;
	
	private Button mLogin;
	
	private Button mShootShop;
	private Button mShootMerchandies;
	
	private Button mPhoto;
	private ImageView mIM1;
	private ImageView mIM2;
	private ImageView mIM3;
	private ImageView mIM4;
	
	private Bitmap mIM1BitmapSeller;
	private Bitmap mIM2BitmapSeller;
	private Bitmap mIM3BitmapSeller;
	private Bitmap mIM4BitmapSeller;
	
	private Bitmap mIM1BitmapGoods;
	private Bitmap mIM2BitmapGoods;
	private Bitmap mIM3BitmapGoods;
	private Bitmap mIM4BitmapGoods;
	
	private ImageView mIM1Delete;
	private ImageView mIM2Delete;
	private ImageView mIM3Delete;
	private ImageView mIM4Delete;
	
	/*
	 * 0 is all have bmp 
	 * 1 is first default etc.
	 */
	private List<Integer> mPhotoFillArraySeller;
	private List<Integer> mPhotoFillArrayGoods;
	
	private ImageSort mImageSort = new ImageSort();
	
	
	/*
	 * 拍商家需要保存的信息
	 */
	private EditText mMerchantName;
	private EditText mMerchantTip;
	
	private String mMaincat;
	private RadioGroup mMerchantCategory;
	private RadioButton mMerchantTypeEat;
	private RadioButton mMerchantTypeWear;
	private RadioButton mMerchantTypeLive;
	private RadioButton mMerchantTypeUse;
	private RadioButton mMerchantTypePlay;
	private RadioButton mMerchantTypeEtc;
	
	private String mSType="0";
	private RadioGroup mMerchantStoreType;
	private RadioButton mMerchantStoreTypeIndependent;
	private RadioButton mMerchantStoreTypeShoppingMall;
	private LinearLayout mMerchantStoreTypeShoppingMallSelected;
	
	//三级联动
	private Spinner mProvince;
	private Spinner mCity;
	private Spinner mDistrict;
	
	private Integer provinceId, cityId;
	private String strProvince, strCity, strCounty;
	
	private ArrayAdapter<CharSequence> province_adapter;
	private ArrayAdapter<CharSequence> city_adapter;
	private ArrayAdapter<CharSequence> county_adapter;
	
	private List<String> mProvinceArray;
	
	private EditText mMerchantAddress;
	
	private Spinner mMerchantInShoppingMall;
	private Spinner mMerchantInShoppingMallFloor;
	
	private EditText mMerchantPhone;
	private EditText mMerchantBusinesshours;
	private EditText mMerchantBusLine;
	private EditText mMerchantNetworkUrl;
	
	//拍商品布局使用的参数  个人没有
	private RelativeLayout mMerchantGoodsLayout;
	private RadioGroup mMerchantGoodsPurpose;
	private String mGoodsType="";
//	private RadioButton mMerchantGoodsTypeShow;
//	private RadioButton mMerchantGoodsTypeEnquiry;
//	private RadioButton mMerchantGoodsTypeHomeDelivery;
	
	private EditText mMerchantGoodsName;
	private EditText mMerchantGoodsCurrentPrice;
	private EditText mMerchantGoodsOriginalPrice;
	
	//个人拍商品
	private RelativeLayout mPersonalContactLayout;
	private RadioGroup mPersonalGoodsContact;
	private String mGoodsContact="";
//	private RadioButton mPersonalGoodsQQ;
//	private RadioButton mPersonalGoodsPhone;
//	private RadioButton mPersonalGoodsMail;
//	private RadioButton mPersonalGoodsMessage;
	
	private EditText mGoodsDetail;
	
	private LinearLayout mPersonalAddress;
	private EditText mPersonalAddressGoods;
	private Button mPersonalAddressHave;
	
	private Button mButtonSave;
	
	public boolean isLogin;
	
	
	//公共按钮
	private LinearLayout mMerchantLocationLayout;
	private Button mMerchantGPSMap;
	private Button mMerchantLBS;
	
	
	//拍商家和拍商品layout
	private LinearLayout mMerchantLayout;
	private LinearLayout mMerchantOrPersonalGoodsLayout;
	
	
	
	
	private List<String> list = new ArrayList<String>();
//	private TextView myTextView;
	private Spinner mySpinner;
	private ArrayAdapter<String> adapter;
	private String[] mSellermap={"",""};
//	private Animation myAnimation;
//	private RadioButton duli, shangchang;
//	private LinearLayout re;
//	private Button paigoods, paishangjia;
	
	private LinearLayout l1, l2;
	private View mView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		HashMap<String , LoginListener> l = new HashMap<String, LoginListener>();
		l.put("message", mLoginListener);
		HomeActivity.setLoginListener(l);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_message, null);
		init(mView);
		return mView;

	}
	

	private void init(View view) {
		// TODO Auto-generated method stub
		//构造控件  需要切换的
		mLogin = (Button)view.findViewById(R.id.id_merchant_photo_login_button);
		if(!StringUtil.isValidStr(User.uid)){
			mLogin.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					HomeActivity.tabHost.setCurrentTabByTag("user");
				}
			});
		} else{
			mLogin.setVisibility(View.GONE);
		}
		
		
		mShootShop = (Button)view.findViewById(R.id.id_shoot_shop);
		mShootMerchandies = (Button)view.findViewById(R.id.id_shoot_merchandies);
		
		//获取拍商家layout
		mMerchantLayout = (LinearLayout) view.findViewById(R.id.l1);
		mMerchantName = (EditText)view.findViewById(R.id.id_merchant_name_edit);
		mMerchantTip = (EditText)view.findViewById(R.id.id_merchant_tip_edit);
		
		//获取拍商品layout,分商家  个人
		mMerchantOrPersonalGoodsLayout = (LinearLayout) view.findViewById(R.id.l2);
		//商家发布商品类型   个人没有
		mMerchantGoodsLayout = (RelativeLayout)view.findViewById(R.id.id_merchant_goods);
		mMerchantGoodsPurpose = (RadioGroup)view.findViewById(R.id.id_merchant_goods_purpose);
//		mMerchantGoodsTypeShow = (RadioButton)view.findViewById(R.id.id_merchant_googs_type_show);
//		mMerchantGoodsTypeEnquiry = (RadioButton)view.findViewById(R.id.id_merchant_googs_type_enquiry);
//		mMerchantGoodsTypeHomeDelivery = (RadioButton)view.findViewById(R.id.id_merchant_googs_type_homedelivery);
		mMerchantGoodsPurpose.setOnCheckedChangeListener(mMerchantGoodsPurposeOncCheckedListener);
		//商品名称和价格
		mMerchantGoodsName = (EditText)view.findViewById(R.id.id_merchant_goods_name);
		mMerchantGoodsCurrentPrice = (EditText)view.findViewById(R.id.id_merchant_goods_current_price);
		mMerchantGoodsOriginalPrice = (EditText)view.findViewById(R.id.id_merchant_goods_original_price);
		//个人发布商品联系方式  商家没有
		mPersonalContactLayout = (RelativeLayout)view.findViewById(R.id.id_personal_contact);
		mPersonalGoodsContact = (RadioGroup)view.findViewById(R.id.id_personal_goods_contact);
//		mPersonalGoodsQQ = (RadioButton)view.findViewById(R.id.id_personal_goods_contact_qq);
//		mPersonalGoodsPhone = (RadioButton)view.findViewById(R.id.id_personal_goods_contact_phone);
//		mPersonalGoodsMail = (RadioButton)view.findViewById(R.id.id_personal_goods_contact_mail);
//		mPersonalGoodsMessage = (RadioButton)view.findViewById(R.id.id_personal_goods_contact_message);
		mPersonalGoodsContact.setOnCheckedChangeListener(mPersonalGoodsContactOncCheckedListener);
		
		
		
		//商家和个人都有的介绍
		mGoodsDetail = (EditText)view.findViewById(R.id.id_merchant_and_personal_goods_detail);
		//个人发布联系地址  商家没有
		mPersonalAddress = (LinearLayout)view.findViewById(R.id.id_personal_address);
		mPersonalAddressGoods = (EditText)view.findViewById(R.id.id_personal_goods_address);
		mPersonalAddressHave = (Button)view.findViewById(R.id.id_personal_address_have);
		mPersonalAddressHave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new DoAsyncTask(getActivity(), "正在加载全部收货地址", new DoTask() {
					
					@Override
					public Object task() {
						// TODO Auto-generated method stub
						return Apicmd.getInstance().getAddress(getActivity(), User.uid);
					}
				}, new PostResult() {
					
					@Override
					public void result(Object result) {
						// TODO Auto-generated method stub
						if(result!=null){
							Address address = (Address)result;
							if(address.isSucceed()){
								if(address.addressList!=null&&address.addressList.size()>0){
									final String stradd[] = address.addressArray();
									new AlertDialog.Builder(getActivity())
					                .setTitle("请选择地址")//标题
					                .setSingleChoiceItems(stradd,-1,new DialogInterface.OnClickListener() {
					                    public void onClick(DialogInterface dialog, int which) {//响应事件
					                        // do something
					                        //关闭对话框
//					                        dialog.dismiss();
					                    	if(mShootMerchandies.getVisibility()==View.VISIBLE&&mPersonalAddress.getVisibility()==View.VISIBLE){
					                    		mPersonalAddressGoods.setText(stradd[which]);
					                    	}
					                    }
					                })
					                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
					                    
					                    @Override
					                    public void onClick(DialogInterface dialog, int which) {
					                        //do something
//					                    	if(mShootMerchandies.getVisibility()==View.VISIBLE&&mPersonalAddress.getVisibility()==View.VISIBLE){
//					                    		mPersonalAddressGoods.setText(stradd[0]);
//					                    	}
					                    	 dialog.dismiss();
					                    }
					                })//添加button并响应点击事件
					                .show();
									
								}else{
									Toast.makeText(getActivity(), "目前没有收货地址", Toast.LENGTH_SHORT).show();
								}
							}else{
								Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
							}
							
						}else{
							Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
						}
					}
				}, true, true);
				
			}
		});
		//个人发布商品 地图手机定位， 商家没有
		mMerchantLocationLayout= (LinearLayout)view.findViewById(R.id.id_merchant_location_layout);
//		if(StringUtil.isValidStr(User.uid)&&User.mIdentify<1){
//			mMerchantLocationLayout.setVisibility(View.VISIBLE);
//		}
		mMerchantGPSMap = (Button)view.findViewById(R.id.id_merchant_gpsmap);
		mMerchantGPSMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Toast.makeText(getActivity(), "启动百度地图定位", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(getActivity(), LocationForApi.class);
	            startActivityForResult(i, LocationForApi.LOCAPI_LAT_LNG); 
			}
		});
		mMerchantLBS = (Button)view.findViewById(R.id.id_merchant_lbs);
		mMerchantLBS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "手机定位失败,请稍后尝试", Toast.LENGTH_SHORT).show();
			}
		});
		
		mPhoto = (Button)view.findViewById(R.id.id_photo_button);
		mPhoto.setOnClickListener(mPhotoOnClickListener);
		mIM1 = (ImageView)view.findViewById(R.id.id_photo_image_1);
		mIM2 = (ImageView)view.findViewById(R.id.id_photo_image_2);
		mIM3 = (ImageView)view.findViewById(R.id.id_photo_image_3);
		mIM4 = (ImageView)view.findViewById(R.id.id_photo_image_4);
		mIM1Delete = (ImageView)view.findViewById(R.id.id_photo_image_delete_1);
		mIM2Delete = (ImageView)view.findViewById(R.id.id_photo_image_delete_2);
		mIM3Delete = (ImageView)view.findViewById(R.id.id_photo_image_delete_3);
		mIM4Delete = (ImageView)view.findViewById(R.id.id_photo_image_delete_4);
		
		//商家发布  主营分类
		mMerchantCategory = (RadioGroup)view.findViewById(R.id.id_merchant_management_categories);
//		mMerchantTypeEat = (RadioButton)view.findViewById(R.id.id_merchant_management_categories_eat);
//		mMerchantTypeWear = (RadioButton)view.findViewById(R.id.id_merchant_management_categories_wear);
//		mMerchantTypeLive = (RadioButton)view.findViewById(R.id.id_merchant_management_categories_live);
//		mMerchantTypeUse = (RadioButton)view.findViewById(R.id.id_merchant_management_categories_use);
//		mMerchantTypePlay = (RadioButton)view.findViewById(R.id.id_merchant_management_categories_play);
//		mMerchantTypeEtc = (RadioButton)view.findViewById(R.id.id_merchant_management_categories_etc);
		mMerchantCategory.setOnCheckedChangeListener(mMerchantCategoryOncCheckedListener);
		
		//商家发布  店铺类型
		mMerchantStoreType = (RadioGroup)view.findViewById(R.id.id_merchant_store_type);
		mMerchantStoreTypeIndependent = (RadioButton)view.findViewById(R.id.id_merchant_store_type_independent);
		mMerchantStoreTypeShoppingMall = (RadioButton)view.findViewById(R.id.id_merchant_store_type_shoppingmall);
		mMerchantStoreTypeShoppingMallSelected = (LinearLayout)view.findViewById(R.id.id_merchant_shoppngmall_select);
		mMerchantStoreType.setOnCheckedChangeListener(mMerchantStroeTypeOncCheckedListener);
		
		//商家发布  独立商铺 需要提供商场和楼层
		mMerchantInShoppingMall = (Spinner)view.findViewById(R.id.id_merchant_in_shopingmall);
		mMerchantInShoppingMallFloor = (Spinner)view.findViewById(R.id.id_merchant_in_shoppingmall_floor);
		
		//省市区 三级联动
		mProvince = (Spinner)view.findViewById(R.id.id_merchant_location_province);
		mCity = (Spinner)view.findViewById(R.id.id_merchant_location_city);
		mDistrict = (Spinner)view.findViewById(R.id.id_merchant_location_district);
		
		cfgPCC();
		
		//商家发布  街道地址  联系电话  营业时间  乘车路线  商家网址
		mMerchantAddress = (EditText)view.findViewById(R.id.id_merchant_address);
		mMerchantPhone = (EditText)view.findViewById(R.id.id_merchant_phone);
		mMerchantBusinesshours = (EditText)view.findViewById(R.id.id_merchant_businesshours);
		mMerchantBusLine = (EditText)view.findViewById(R.id.id_merchant_busline);
		mMerchantNetworkUrl = (EditText)view.findViewById(R.id.id_merchant_network_url);
		
		
		mShootShop.setOnClickListener(mShootShopOnClickListener);
		mShootMerchandies.setOnClickListener(mShootMerchandiesOnClickListener);
		
		mButtonSave = (Button)view.findViewById(R.id.id_merchant_save);
		mButtonSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mMerchantLayout.getVisibility()==View.VISIBLE){
					if(!StringUtil.isValidStr(User.uid)){
//						Toast.makeText(getActivity(), "可以保存",
//								Toast.LENGTH_SHORT).show();	
						AlertDialog.Builder builder = new Builder(getActivity());
						builder.setMessage("亲~您还没有登录！请选择登录或者注册之后在上传信息\n选择放弃，将丢失已填写信息.");
						builder.setTitle("提示");
						builder.setPositiveButton("放弃", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						builder.setNegativeButton("登录", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								HomeActivity.tabHost.setCurrentTabByTag("user");
							}
						});
						builder.create().show();
					}else if(StringUtil.isValidStr(User.uid) && User.mAction ==1 && User.mIdentify<1){
						addSeller();
					}//已是商家，可以修改
					else if(StringUtil.isValidStr(User.uid) && User.mIdentify >0){
						addSeller();
						
					}
					
				}else if(mMerchantOrPersonalGoodsLayout.getVisibility()==View.VISIBLE){
					if(StringUtil.isValidStr(User.uid)){
						int[] pics = sendImgSync();
						final ReleaseGoods goods = new ReleaseGoods();
						goods.goodspic=pics;
						try {
							if(User.mIdentify>1){
//								type	ticket:打折商品、show：展示、ask：询价、order:订单
								if(mGoodsType.equals("打折商品")){
									goods.type="ticket";
								}else if(mGoodsType.equals("展示")){
									goods.type="show";
								}else if(mGoodsType.equals("询价")){
									goods.type="ask";
								}else if(mGoodsType.equals("订单")){
									goods.type="order";
								}
							}
							String name = mMerchantGoodsName.getText().toString();
							goods.name=URLEncoder.encode(StringUtil.isValidStr(name)?name:"", 
											StringUtil.UTF_8);
							
							String newprice = mMerchantGoodsCurrentPrice.getText().toString();
							goods.newPrice=URLEncoder.encode(StringUtil.isValidStr(newprice)?newprice:"", 
									StringUtil.UTF_8);
							
							String price = mMerchantGoodsOriginalPrice.getText().toString();
							goods.price=URLEncoder.encode(StringUtil.isValidStr(price)?price:"", 
									StringUtil.UTF_8);
							
							String content = mGoodsDetail.getText().toString();
							goods.content=URLEncoder.encode(StringUtil.isValidStr(content)?content:"", 
									StringUtil.UTF_8);
							if(User.mIdentify<1){
								goods.contact = URLEncoder.encode(StringUtil.isValidStr(mGoodsContact)?mGoodsContact:"", 
										StringUtil.UTF_8);
								String address = mPersonalAddressGoods.getText().toString();
								goods.address=URLEncoder.encode(StringUtil.isValidStr(content)?content:"", 
										StringUtil.UTF_8);
							}
							
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						//发布商品
						DoAsyncTask upimgTask = new DoAsyncTask(getActivity(), "正在发布商品...", new DoTask() {
							@Override
							public Object task() {
								// TODO Auto-generated method stub
								return Apicmd.getInstance().getSave(getActivity(), goods);
							}
						}, new DoAsyncTask.PostResult() {
							
							@Override
							public void result(Object result) {
								// TODO Auto-generated method stub
								if(result!=null){
									Save s = (Save)result;
									if(s.isSucceed()){
										Toast.makeText(getActivity(), "发布成功", Toast.LENGTH_SHORT).show();
									}else{
										Toast.makeText(getActivity(), "发布失败，请稍后尝试", Toast.LENGTH_SHORT).show();
									}
								}
							}
						}, true, true);	
					}else{
						Toast.makeText(getActivity(), "亲~您还没有登录...", Toast.LENGTH_SHORT).show();
					}
					
				}
			}
		});
		
		mPhotoFillArrayGoods = new ArrayList<Integer>(4);
		for(int i =1; i<=4; ++i){
			mPhotoFillArrayGoods.add(Integer.valueOf(i));
		}
		
		mPhotoFillArraySeller = new ArrayList<Integer>(4);
		for(int i =1; i<=4; ++i){
			mPhotoFillArraySeller.add(Integer.valueOf(i));
		}
		
		if(User.mIdentify<1){
			switchMerchandAndPersonal(PHOTO_GOODS);
			
		}else{
			
		}
		
		HandleImageUpdate();
	}
	
	private int[] sendImgSync(){
		int[] imgIds = new int[4];
		String md5_IMSI_16 = StringUtil.getMD5_16(Global.IMSI);
		if(mMerchantLayout.getVisibility()==View.VISIBLE){
			if(mIM1BitmapSeller!=null){
				String imgName = md5_IMSI_16+StringUtil.getTimeYMDHMS()+"_1.jpeg";
				UpLoadImage u = Apicmd.getInstance().upLoadImage(getActivity(), imgName, mIM1BitmapSeller);
				if(u.isSucceed()){
					imgIds[0]=u.imgId;
				}
			}
			
			if(mIM2BitmapSeller!=null){
				String imgName = md5_IMSI_16+StringUtil.getTimeYMDHMS()+"_2.jpeg";
				UpLoadImage u = Apicmd.getInstance().upLoadImage(getActivity(), imgName, mIM2BitmapSeller);
				if(u.isSucceed()){
					imgIds[1]=u.imgId;			
				}
			}
			
			if(mIM3BitmapSeller!=null){
				String imgName = md5_IMSI_16+StringUtil.getTimeYMDHMS()+"_3.jpeg";
				UpLoadImage u = Apicmd.getInstance().upLoadImage(getActivity(), imgName, mIM3BitmapSeller);
				if(u.isSucceed()){
					imgIds[2]=u.imgId;			
				}
			}
			
			if(mIM4BitmapSeller!=null){
				String imgName = md5_IMSI_16+StringUtil.getTimeYMDHMS()+"_4.jpeg";
				UpLoadImage u = Apicmd.getInstance().upLoadImage(getActivity(), imgName, mIM4BitmapSeller);
				if(u.isSucceed()){
					imgIds[3]=u.imgId;			
				}
			}
		}else if(mMerchantOrPersonalGoodsLayout.getVisibility()==View.VISIBLE){
			if(mIM1BitmapGoods!=null){
				String imgName = md5_IMSI_16+StringUtil.getTimeYMDHMS()+"_1.jpeg";
				UpLoadImage u = Apicmd.getInstance().upLoadImage(getActivity(), imgName, mIM1BitmapGoods);
				if(u.isSucceed()){
					imgIds[0]=u.imgId;
				}
			}
			
			if(mIM2BitmapGoods!=null){
				String imgName = md5_IMSI_16+StringUtil.getTimeYMDHMS()+"_2.jpeg";
				UpLoadImage u = Apicmd.getInstance().upLoadImage(getActivity(), imgName, mIM2BitmapGoods);
				if(u.isSucceed()){
					imgIds[1]=u.imgId;			
				}
			}
			
			if(mIM3BitmapGoods!=null){
				String imgName = md5_IMSI_16+StringUtil.getTimeYMDHMS()+"_3.jpeg";
				UpLoadImage u = Apicmd.getInstance().upLoadImage(getActivity(), imgName, mIM3BitmapGoods);
				if(u.isSucceed()){
					imgIds[2]=u.imgId;			
				}
			}
			
			if(mIM4BitmapGoods!=null){
				String imgName = md5_IMSI_16+StringUtil.getTimeYMDHMS()+"_4.jpeg";
				UpLoadImage u = Apicmd.getInstance().upLoadImage(getActivity(), imgName, mIM4BitmapGoods);
				if(u.isSucceed()){
					imgIds[3]=u.imgId;			
				}
			}
		}
		return imgIds;
	}
	
	private void initPCZSpinner(){
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	private void switchMerchandAndPersonal(int type){
		this.type = type;
		//切换商家 商品laout
		//商家入驻
		if(type==PHOTO_MERCHANT){
			mShootShop.setBackgroundResource(R.drawable.btn_title_l_press);
			mShootMerchandies.setBackgroundResource(R.drawable.btn_title_r);
			
			mMerchantLayout.setVisibility(View.VISIBLE);
			mMerchantOrPersonalGoodsLayout.setVisibility(View.GONE);
			
			mIM1.setImageBitmap(mIM1BitmapSeller);
			mIM2.setImageBitmap(mIM2BitmapSeller);
			mIM3.setImageBitmap(mIM3BitmapSeller);
			mIM4.setImageBitmap(mIM4BitmapSeller);
			
			mMerchantLocationLayout.setVisibility(View.VISIBLE);
			
//			mMerchantGoodsLayout.setVisibility(RelativeLayout.GONE);
//			mPersonalContactLayout.setVisibility(RelativeLayout.VISIBLE);
//			mPersonalAddress.setVisibility(View.VISIBLE);
//			mPersonalAddressHave.setVisibility(Button.VISIBLE);
//			mMerchantLocationLayout.setVisibility(View.VISIBLE);
		}else if(type==PHOTO_GOODS){
			//发布商品
			mShootShop.setBackgroundResource(R.drawable.btn_title_l);
			mShootMerchandies.setBackgroundResource(R.drawable.btn_title_r_press);
			
			mMerchantLayout.setVisibility(View.GONE);
			mMerchantOrPersonalGoodsLayout.setVisibility(View.VISIBLE);
			
			mIM1.setImageBitmap(mIM1BitmapGoods);
			mIM2.setImageBitmap(mIM2BitmapGoods);
			mIM3.setImageBitmap(mIM3BitmapGoods);
			mIM4.setImageBitmap(mIM4BitmapGoods);
			if(StringUtil.isValidStr(User.uid)&&User.mIdentify<1){
				mMerchantGoodsLayout.setVisibility(RelativeLayout.GONE);
				mMerchantGoodsPurpose.setVisibility(View.GONE);
				mPersonalContactLayout.setVisibility(RelativeLayout.VISIBLE);
				mPersonalAddress.setVisibility(View.VISIBLE);
				mPersonalAddressHave.setVisibility(Button.VISIBLE);
				mMerchantLocationLayout.setVisibility(View.VISIBLE);
			}
		}
	}
	
	private LoginListener mLoginListener = new LoginListener() {
		
		@Override
		public void Login(Class<?> cls) {
			// TODO Auto-generated method stub
			if(StringUtil.isValidStr(User.uid)){
//				Toast.makeText(getActivity(), "Message  Fragment 已登录", Toast.LENGTH_LONG).show();
				mLogin.setVisibility(View.GONE);
			}
		}
	};
	
	private OnClickListener mShootShopOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			//登录 && 个人 
			if(StringUtil.isValidStr(User.uid) && User.mIdentify<1&&User.mAction<0){
				Toast.makeText(getActivity(),"亲~您目前还不是商家，请先注册~",Toast.LENGTH_SHORT).show();
			}else{
				switchMerchandAndPersonal(PHOTO_MERCHANT);
			}
		}
	};
	
	private OnClickListener mShootMerchandiesOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switchMerchandAndPersonal(PHOTO_GOODS);
		}
	};
	
	private RadioGroup.OnCheckedChangeListener mPersonalGoodsContactOncCheckedListener = new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
//			(RadioButton)group.getChildAt(checkedId);
			RadioButton rb = (RadioButton)mView.findViewById(checkedId); 
			mGoodsContact = rb.getText().toString();
		}
	};
	
	private RadioGroup.OnCheckedChangeListener mMerchantGoodsPurposeOncCheckedListener = new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
//			(RadioButton)group.getChildAt(checkedId);
			RadioButton rb = (RadioButton)mView.findViewById(checkedId); 
			mGoodsType = rb.getText().toString();
		}
	};
	
	private RadioGroup.OnCheckedChangeListener mMerchantCategoryOncCheckedListener = new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			RadioButton rb = (RadioButton)mView.findViewById(checkedId); 
			mMaincat = rb.getText().toString();
		}
	};
	
	private RadioGroup.OnCheckedChangeListener mMerchantStroeTypeOncCheckedListener = new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
//			独立店铺</string>
//		    <string name="merchant_store_type_shoppingmall_text">商场店铺</string>
			RadioButton rb = (RadioButton)mView.findViewById(checkedId); 
			if(rb.getText().toString().equals("独立店铺")){
				mSType="0";
				mMerchantStoreTypeShoppingMallSelected.setVisibility(View.GONE);
			}else if(rb.getText().toString().equals("商场店铺")){
				mSType="1";
				mMerchantStoreTypeShoppingMallSelected.setVisibility(View.VISIBLE);
			}
		}
	};
	
	private void setImageViewBitmap(Bitmap bmp){
		
	}
	
	
	/*
	 * 处理photo事件
	 */
	
	private OnClickListener mPhotoOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(MemoryStatus.externalMemoryAvailable()){
				takePhoto();
			}else {
//				Toast.makeText(this, "没有SD卡，无法启动相机", duration)
			}
			
		}
	};
	//自己愚蠢的做法，等有时间了在从新写这逻辑
	private void HandleImageUpdate(){
		mIM1Delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(mIM1!= null){
					if(mMerchantLayout.getVisibility()==View.VISIBLE
							&&mIM1BitmapSeller!=null && !mIM1BitmapSeller.isRecycled()){
						mIM1BitmapSeller.recycle();
						mIM1BitmapSeller = null;
						mIM1.setImageBitmap(mIM1BitmapSeller);
						mPhotoFillArraySeller.add(Integer.valueOf(1));
						Collections.sort(mPhotoFillArraySeller, mImageSort);
					}else if(mMerchantOrPersonalGoodsLayout.getVisibility()==View.VISIBLE
							&&mIM1BitmapGoods!=null && !mIM1BitmapGoods.isRecycled()){
						mIM1BitmapGoods.recycle();
						mIM1BitmapGoods = null;
						mIM1.setImageBitmap(mIM1BitmapGoods);
						mPhotoFillArrayGoods.add(Integer.valueOf(1));
						Collections.sort(mPhotoFillArrayGoods, mImageSort);
					}
				}
			}
		});
		mIM2Delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(mIM2!=null){
					if(mMerchantLayout.getVisibility()==View.VISIBLE
							&&mIM2BitmapSeller!=null && !mIM2BitmapSeller.isRecycled()){
						mIM2BitmapSeller.recycle();
						mIM2BitmapSeller = null;
						mIM2.setImageBitmap(mIM2BitmapSeller);
						mPhotoFillArraySeller.add(Integer.valueOf(2));
						Collections.sort(mPhotoFillArraySeller, mImageSort);
					}else if(mMerchantOrPersonalGoodsLayout.getVisibility()==View.VISIBLE
							&&mIM2BitmapGoods!=null && !mIM2BitmapGoods.isRecycled()){
						mIM2BitmapGoods.recycle();
						mIM2BitmapGoods = null;
						mIM2.setImageBitmap(mIM2BitmapGoods);
						mPhotoFillArrayGoods.add(Integer.valueOf(2));
						Collections.sort(mPhotoFillArrayGoods, mImageSort);
					}
				}
			}
		});
		mIM3Delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(mIM3!=null){
					if(mMerchantLayout.getVisibility()==View.VISIBLE
							&&mIM3BitmapSeller!=null && !mIM3BitmapSeller.isRecycled()){
						mIM3BitmapSeller.recycle();
						mIM3BitmapSeller = null;
						mIM3.setImageBitmap(mIM3BitmapSeller);
						mPhotoFillArraySeller.add(Integer.valueOf(3));
						Collections.sort(mPhotoFillArraySeller, mImageSort);
					}else if(mMerchantOrPersonalGoodsLayout.getVisibility()==View.VISIBLE
							&&mIM3BitmapGoods!=null && !mIM3BitmapGoods.isRecycled()){
						mIM3BitmapGoods.recycle();
						mIM3BitmapGoods = null;
						mIM3.setImageBitmap(mIM3BitmapGoods);
						mPhotoFillArrayGoods.add(Integer.valueOf(3));
						Collections.sort(mPhotoFillArrayGoods, mImageSort);
					}
				}
			}
		});
		mIM4Delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(mIM4!=null){
					if(mMerchantLayout.getVisibility()==View.VISIBLE
							&&mIM4BitmapSeller!=null && !mIM4BitmapSeller.isRecycled()){
						mIM4BitmapSeller.recycle();
						mIM4BitmapSeller = null;
						mIM4.setImageBitmap(mIM4BitmapSeller);
						mPhotoFillArraySeller.add(Integer.valueOf(4));
						Collections.sort(mPhotoFillArraySeller, mImageSort);
					}else if(mMerchantOrPersonalGoodsLayout.getVisibility()==View.VISIBLE
							&&mIM4BitmapGoods!=null && !mIM4BitmapGoods.isRecycled()){
						mIM4BitmapGoods.recycle();
						mIM4BitmapGoods = null;
						mIM4.setImageBitmap(mIM4BitmapGoods);
						mPhotoFillArrayGoods.add(Integer.valueOf(4));
						Collections.sort(mPhotoFillArrayGoods, mImageSort);
					}
				}
			}
		});
	}
	
	private void HandlePhoto(Bitmap bmp){
		if(bmp==null){
			Toast.makeText(getActivity(), "获取图片失败,请重新尝试，或者稍后拍照", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(mMerchantLayout.getVisibility()==View.VISIBLE){
			if(mPhotoFillArraySeller.size()==0){
				Toast.makeText(getActivity(), "请先删除一张图片,在更新", Toast.LENGTH_SHORT).show();
				return;
			}else{
				int fillorder = mPhotoFillArraySeller.get(0).intValue();
				switch(fillorder){
		    	case 1:
		    	{
		    		mIM1BitmapSeller = bitMap;
		    		mIM1.setImageBitmap(mIM1BitmapSeller);
		    		mPhotoFillArraySeller.remove(0);
		    		break;
		    	}
		    	case 2:
		    	{
		    		mIM2BitmapSeller = bitMap;
		    		mIM2.setImageBitmap(mIM2BitmapSeller);
		    		mPhotoFillArraySeller.remove(0);
		    		break;
		    	}
		    	case 3:
		    	{
		    		mIM3BitmapSeller = bitMap;
		    		mIM3.setImageBitmap(mIM3BitmapSeller);
		    		mPhotoFillArraySeller.remove(0);
		    		break;
		    	}
		    	case 4:
		    	{
		    		mIM4BitmapSeller = bitMap;
		    		mIM4.setImageBitmap(mIM4BitmapSeller);
		    		mPhotoFillArraySeller.remove(0);
		    		break;
		    	}
		        default:
		        	break;
		    	}
			}
		}else if(mMerchantOrPersonalGoodsLayout.getVisibility()==View.VISIBLE){
			if(mPhotoFillArrayGoods.size()==0){
				Toast.makeText(getActivity(), "请先删除一张图片,在更新", Toast.LENGTH_SHORT).show();
				return;
			}else{
				int fillorder = mPhotoFillArrayGoods.get(0).intValue();
				switch(fillorder){
		    	case 1:
		    	{
		    		mIM1BitmapGoods = bitMap;
		    		mIM1.setImageBitmap(mIM1BitmapGoods);
		    		mPhotoFillArrayGoods.remove(0);
		    		break;
		    	}
		    	case 2:
		    	{
		    		mIM2BitmapGoods = bitMap;
		    		mIM2.setImageBitmap(mIM2BitmapGoods);
		    		mPhotoFillArrayGoods.remove(0);
		    		break;
		    	}
		    	case 3:
		    	{
		    		mIM3BitmapGoods = bitMap;
		    		mIM3.setImageBitmap(mIM3BitmapGoods);
		    		mPhotoFillArrayGoods.remove(0);
		    		break;
		    	}
		    	case 4:
		    	{
		    		mIM4BitmapGoods = bitMap;
		    		mIM4.setImageBitmap(mIM4BitmapGoods);
		    		mPhotoFillArrayGoods.remove(0);
		    		break;
		    	}
		        default:
		        	break;
		    	}
			}
		}
	}
	
	/*
	<!----------------------------------------------------------------------------------->
	 * 调用系统相机
	 */
	private void takePhoto() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, IMAGE_SCALE);
//            startActivityForResult(intent, IMAGE_CAPTURE);
        } catch (Exception e) {
           // CSLog.e(UserPicView.class, e.getMessage());
        }
    }
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	    switch (requestCode) {
	    case LocationForApi.LOCAPI_LAT_LNG:{
	    	if (resultCode == Activity.RESULT_OK) {
	    		//上传坐标用  经度Longitude  纬度Latitude
	    		Bundle b = data.getBundleExtra(LocationForApi.LOC_BUNDLE);
	    		if(b!=null){
	    			mSellermap[0]=b.getIntArray("latlog")[1]+"";
	    			mSellermap[1]=b.getIntArray("latlog")[0]+"";
	    			String str[] = b.getStringArray("address");
    				int count = str==null?0:str.length;
    				String s="";
    				for(int i=0;i<count;++i){
    					s+=str[i];
    				}
					if (mMerchantLayout.getVisibility() == View.VISIBLE) {
						String p = str[0];
						String city = str[1];
						
						int pos = province_adapter.getPosition(p.substring(0, p.length()-1));
						mProvince.setSelection(pos);
						
//						select(mCity, city_adapter, PCCModel.city[pos]);
//						int c = city_adapter.getPosition(city.substring(0, city.length()-1));
//						mCity.setSelection(c);
						mMerchantAddress.setText(s);
					} else if(mMerchantOrPersonalGoodsLayout.getVisibility() == View.VISIBLE&&
							mPersonalAddress.getVisibility() == View.VISIBLE){
						mPersonalAddressGoods.setText(s);
					}
	    		}
	    		
            }
	    	break;
	    }
        case IMAGE_CAPTURE:
            if (resultCode == Activity.RESULT_OK) {
                imageFrom = requestCode;
                scaleImage(data);
            }
            break;
        case IMAGE_LOCAL:
            if (resultCode == Activity.RESULT_OK) {
                imageFrom = requestCode;
                imageUrl = data.getData();
                scaleImage(data);
            }
            break;
        case IMAGE_SCALE:
            if (data != null && data.getParcelableExtra(BITMAP_PROP_NAME) != null) {
                bitMap = (Bitmap) data.getParcelableExtra(BITMAP_PROP_NAME);
                if (bitMap != null) {
                	HandlePhoto(bitMap);
//                	returnLastView(bitMap);
                    /*if (getContext() instanceof ActivityController) {
                        ((ActivityController) getContext()).showWait("正在修改头像,请稍后...");
                    } else {
                        PKGameInterface.getInstance(getContext()).showWait("正在修改头像,请稍后...");
                    }
                    bitMap = Tool.getBitmap(bitMap, fPicWidth, fPicWidth, fPicSize);
                    setUploadUserPicAction(bitMap);*/
                }
            }
            break;
        default:
            break;
	    }
	}

	/**
     * 调用系统相册中的剪裁图片方法
     * 
     * @param data 调用系统相机和图片选取返回的信息
     */
    private void scaleImage(Intent data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (imageFrom == IMAGE_CAPTURE ) {
            intent.setType("image/*");
            intent.putExtra("data", data.getParcelableExtra(BITMAP_PROP_NAME));
        } else {
            intent.setDataAndType(imageUrl, "image/*");
        }
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", fPicWidth);
        intent.putExtra("outputY", fPicWidth);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, IMAGE_SCALE);
    }
    
    private void addSeller(){
    	//已登录  &&  user中心跳转过来注册商家
		if(User.mIdentify<1&&!StringUtil.isValidStr(mMerchantName.getText().toString())){
			//注册商家，商家名称不能为空
			Toast.makeText(getActivity(), "商家名不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		String url = mMerchantNetworkUrl.getText().toString();
		boolean isUrl = URLUtil.isAboutUrl(url)||URLUtil.isAssetUrl(url)||URLUtil.isContentUrl(url)||URLUtil.isCookielessProxyUrl(url)
				||URLUtil.isDataUrl(url)||URLUtil.isFileUrl(url)||URLUtil.isHttpsUrl(url)||URLUtil.isHttpUrl(url)||URLUtil.isJavaScriptUrl(url)
				||URLUtil.isNetworkUrl(url)||URLUtil.isValidUrl(url);
		if(!isUrl){
			//注册商家，商家名称不能为空
			Toast.makeText(getActivity(), "商家网址不正确,请从新输入", Toast.LENGTH_LONG).show();
			return;
		}
		
		DoAsyncTask upimgTask = new DoAsyncTask(getActivity(), "正在注册商家...", new DoTask() {
			@Override
			public Object task() {
				// TODO Auto-generated method stub
				int[] pics = sendImgSync();
				AddSellerInfo s = new AddSellerInfo();
				s.uid = User.uid;
				try {
					String name = mMerchantName.getText().toString();
					s.sellername=URLEncoder.encode(StringUtil.isValidStr(name)?name:"", 
									StringUtil.UTF_8);
					
					s.maincat=URLEncoder.encode(StringUtil.isValidStr(mMaincat)?mMaincat:"", 
									StringUtil.UTF_8);
					
					String tip = mMerchantTip.getText().toString();
					s.sellerintro=URLEncoder.encode(StringUtil.isValidStr(tip)?tip:"", 
							StringUtil.UTF_8);
					
					s.area=URLEncoder.encode(StringUtil.isValidStr(strCity)?strCity:"", 
							StringUtil.UTF_8);
					
					s.province=URLEncoder.encode(StringUtil.isValidStr(strProvince)?strProvince:"", 
							StringUtil.UTF_8);
					s.city=s.area;
					s.country=URLEncoder.encode(StringUtil.isValidStr(strCounty)?strCounty:"", 
							StringUtil.UTF_8);
					
					String address = mMerchantAddress.getText().toString();
					s.selleraddress=URLEncoder.encode(StringUtil.isValidStr(address)?address:"", 
							StringUtil.UTF_8);
					
					String sellertime = mMerchantBusinesshours.getText().toString();
					s.sellertime=URLEncoder.encode(StringUtil.isValidStr(sellertime)?sellertime:"", 
							StringUtil.UTF_8);
					
					String sellerroute=mMerchantBusLine.getText().toString();
					s.sellerroute=URLEncoder.encode(StringUtil.isValidStr(sellerroute)?sellerroute:"", 
							StringUtil.UTF_8);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				s.sellerpic = pics;
				s.sellerphone=mMerchantPhone.getText().toString();
				s.sellermap = mSellermap;
				s.stype=mSType;
				s.sellerurl=mMerchantNetworkUrl.getText().toString();
				return Apicmd.getInstance().getAddSeller(getActivity(), s);
			}
		}, new DoAsyncTask.PostResult() {
			
			@Override
			public void result(Object result) {
				// TODO Auto-generated method stub
				if(result!=null){
					AddSeller as = (AddSeller)result;
					if(as.isSucceed()){
						User.mAction = 3;
						Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(getActivity(), "注册失败,请稍后重试", Toast.LENGTH_SHORT).show();
					}
					
					
				}
			}
		}, true, true);
    }
    
    
    class ImageSort implements Comparator<Integer>{

		@Override
		public int compare(Integer arg0, Integer arg1) {
			// TODO Auto-generated method stub
			return arg0.intValue()-arg1.intValue();
		}
    	
    }

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
	
	private void cfgPCC(){
		mProvince.setPrompt("请选择省份");
		province_adapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.province_item, android.R.layout.simple_spinner_item);
		province_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mProvince.setAdapter(province_adapter);

		// select(mProvince, province_adapter, R.array.province_item);
		// 添加监听，一开始的时候城市，县区的内容是不显示的而是根据省的内容进行联动
		mProvince.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						provinceId = mProvince.getSelectedItemPosition();
						strProvince = mProvince.getSelectedItem().toString();// 得到选择的内容，也就是省的名字
//						mCity = (Spinner) findViewById(R.id.city_spinner);

						if (true) {
							System.out.println("province: "+ mProvince.getSelectedItem().toString() + provinceId.toString());

//							county_spinner = (Spinner) findViewById(R.id.county_spinner);
//							city_spinner = (Spinner) findViewById(R.id.city_spinner);
							mCity.setPrompt("请选择城市");// 设置标题
							select(mCity, city_adapter, PCCModel.city[provinceId]);// 城市一级的数据绑定
							/*
							 * 通过这个city[provinceId]指明了该省市的City集合 R。array.beijing
							 */
							mCity
									.setOnItemSelectedListener(new OnItemSelectedListener() {

										@Override
										public void onItemSelected(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											cityId = mCity
													.getSelectedItemPosition();// 得到city的id
											strCity = mCity
													.getSelectedItem()
													.toString();// 得到city的内容
											LogUtil.d("test", "city: "
													+ mCity
															.getSelectedItem()
															.toString()// 输出测试一下
													+ cityId.toString());
											if (true) {
												// 这里开始设置县区一级的内容
//												county_spinner = (Spinner) findViewById(R.id.county_spinner);
												mDistrict.setPrompt("请选择县区");
												switch (provinceId) {
												case 0:
													select(mDistrict,county_adapter,PCCModel.countyOfBeiJing[cityId]);
													break;
												case 1:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfTianJing[cityId]);
													break;
												case 2:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfHeBei[cityId]);
													break;
												case 3:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfShanXi1[cityId]);
													break;
												case 4:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfNeiMengGu[cityId]);
													break;
												case 5:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfLiaoNing[cityId]);
													break;
												case 6:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfJiLin[cityId]);
													break;
												case 7:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfHeiLongJiang[cityId]);
													break;
												case 8:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfShangHai[cityId]);
													break;
												case 9:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfJiangSu[cityId]);
													break;
												case 10:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfZheJiang[cityId]);
													break;
												case 11:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfAnHui[cityId]);
													break;
												case 12:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfFuJian[cityId]);
													break;
												case 13:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfJiangXi[cityId]);
													break;
												case 14:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfShanDong[cityId]);
													break;
												case 15:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfHeNan[cityId]);
													break;
												case 16:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfHuBei[cityId]);
													break;
												case 17:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfHuNan[cityId]);
													break;
												case 18:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfGuangDong[cityId]);
													break;
												case 19:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfGuangXi[cityId]);
													break;
												case 20:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfHaiNan[cityId]);
													break;
												case 21:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfChongQing[cityId]);
													break;
												case 22:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfSiChuan[cityId]);
													break;
												case 23:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfGuiZhou[cityId]);
													break;
												case 24:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfYunNan[cityId]);
													break;
												case 25:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfXiZang[cityId]);
													break;
												case 26:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfShanXi2[cityId]);
													break;
												case 27:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfGanSu[cityId]);
													break;
												case 28:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfQingHai[cityId]);
													break;
												case 29:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfNingXia[cityId]);
													break;
												case 30:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfXinJiang[cityId]);
													break;
												case 31:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfHongKong[cityId]);
													break;
												case 32:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfAoMen[cityId]);
													break;
												case 33:
													select(mDistrict,
															county_adapter,
															PCCModel.countyOfTaiWan[cityId]);
													break;

												default:
													break;
												}

												mDistrict.setOnItemSelectedListener(new OnItemSelectedListener() {

															@Override
															public void onItemSelected(
																	AdapterView<?> arg0,
																	View arg1,
																	int arg2,
																	long arg3) {
																strCounty = mDistrict
																		.getSelectedItem()
																		.toString();
															}

															@Override
															public void onNothingSelected(
																	AdapterView<?> arg0) {

															}

														});
											}
										}

										@Override
										public void onNothingSelected(
												AdapterView<?> arg0) {
											// TODO Auto-generated method stub

										}

									});
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
	}
	
	/*通过方法动态的添加适配器*/
	private void select(Spinner spin, ArrayAdapter<CharSequence> adapter,
			int arry) {
		//注意这里的arry不仅仅但是一个整形，他代表了一个数组！
		adapter = ArrayAdapter.createFromResource(getActivity(), arry,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adapter);
		// spin.setSelection(0,true);
	}
    
}