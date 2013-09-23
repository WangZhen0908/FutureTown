package com.vvage.futuretown.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.vvage.futuretown.BaseFragment;
import com.vvage.futuretown.HomeActivity;
import com.vvage.futuretown.R;
import com.vvage.futuretown.activity.LoginListener;
import com.vvage.futuretown.activity.Merchant;
import com.vvage.futuretown.cmd.Apicmd;
import com.vvage.futuretown.model.GeoCity;
import com.vvage.futuretown.model.PCCModel;
import com.vvage.futuretown.model.SellerInArea;
import com.vvage.futuretown.model.SellerInArea.Seller;
import com.vvage.futuretown.model.User;
import com.vvage.futuretown.util.AsyncViewTask;
import com.vvage.futuretown.util.AsyncViewTask.PostExecute;
import com.vvage.futuretown.util.DoAsyncTask;
import com.vvage.futuretown.util.DoAsyncTask.DoTask;
import com.vvage.futuretown.util.LogUtil;
import com.vvage.futuretown.util.StringUtil;

public class HomeFragment extends BaseFragment {
	private static final String[] spinner_text = { "商家", "商品"};
	public static final String HOME_FRAGMENT_MSG = "homeFragment_value";
	private ArrayAdapter<String> adapter;
	
	private SellerInArea mSellers;
	
	private List<String> mSellerNameList;
	private List<String> mSellerFilterNameList;
	private boolean isFirstStart=true;
	
//	private ArrayWheelAdapter<Seller> mWheelAdapter;
	
	
	//搜索相关
//	MKSearch mSearch = null;	// 搜索模块，也可去掉地图模块独立使用

	/**
	 *  MapView 是地图主控件
	 */
	private MapView mMapView = null;
	/**
	 *  用MapController完成地图控制 
	 */
	private MapController mMapController = null;
	private MerchantOverlay mOverlay;
	private PopupOverlay mPop = null;
	private ArrayList<OverlayItem>  mItems = null; 
	private ArrayList<OverlayItem>  mFilterItems = null; 
	private OverlayItem mCurItem = null;
	
	private View mPopCustomView;
	private TextView mMerchantName;
	private TextView mMerchantHot;
	private TextView mMerchantGoodsCount;
	private TextView mMerchantPhone;
	private ImageView mMerchantImage;

	// 定位相关 初始化定位自己的位置
	private LocationClient mLocClient = null;
	private LocationData mLocData = null;
	private MyLocationListener myListener = new MyLocationListener();
//	boolean isRequest = false;//是否手动触发请求定位
	boolean isFirstLoc = true;//是否首次定位
	boolean isLocationClientStop = false;

	private EditText mInputCity;
	private TextView mArea;
	private String mCity = "北京";
	private List<String> mAreaList;
	private double beijingLoc[]={39.914889, 116.403874};
	
	private EditText mSearchEditText;
	private TextView mSelectorTextView;
	private TextView mLogin;

	private WheelView picker;
	private View mMainView;

//	// test data
//	/**
//	 * overlay 位置坐标
//	 */
//	double mLon1 = 116.400244;
//	double mLat1 = 39.963175;
//	double mLon2 = 116.369199;
//	double mLat2 = 39.942821;
//	double mLon3 = 116.425541;
//	double mLat3 = 39.939723;
//	double mLon4 = 116.401394;
//	double mLat4 = 39.906965;
//	double mLon5 = 116.402096;
//	double mLat5 = 39.942057;
//	private double lonlats[][] = { { mLon1, mLat1 }, { mLon2, mLat2 },
//			{ mLon3, mLat3 }, { mLon4, mLat4 }, { mLon5, mLat5 } };
//	double mLon5 = 112.504969;
//	double mLat5 = 32.987317;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		HashMap<String , LoginListener> l = new HashMap<String, LoginListener>();
		l.put("home", mLoginListener);
		HomeActivity.setLoginListener(l);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)  {
		mMainView = inflater.inflate(R.layout.fragment_home, null);
		initBDMapView(mMainView);
		initView(mMainView);
		initData();
		return mMainView;
	}
	
//	private void obtainCityMerchants(){
//		AsyncTask<Object, Object, Object> task = new AsyncTask<Object, Object, Object>(){
//			@Override
//			protected Object doInBackground(Object... params) {
//				// TODO Auto-generated method stub
//				return Apicmd.getInstance().getCityMerchants(getActivity());
//			}
//
//			@Override
//			protected void onPostExecute(Object result) {
//				// TODO Auto-generated method stub
//				if(result!=null){
//					CityMerchants cms = (CityMerchants)result;
//					if(cms.isSucceed()){
//						
//					}
//				}
//			}
//			
//		};
//		task.execute(null);
//	}
	
	private void obatinLoc() {
		//定位初始化
		mLocClient = new LocationClient(getActivity()); // 声明LocationClient类
		mLocData = new LocationData();
		mLocClient.registerLocationListener(myListener); // 注册监听函数
		
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);//打开GPS
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(1000);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
//		option.setPoiNumber(5); // 最多返回POI个数
//		option.setPoiDistance(1000); // poi查询距离
//		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		option.setPriority(LocationClientOption.GpsFirst);
		option.setProdName("通过GPS定位");
		mLocClient.setLocOption(option);
		mLocClient.start();
		
		if (mLocClient != null && mLocClient.isStarted()) {
			mLocClient.requestLocation();
			Toast.makeText(getActivity(), "正在定位…", Toast.LENGTH_SHORT).show();
		} else {
			LogUtil.d("LocSDK3", "locClient is null or not started");
		}

	}
	
	private void initBDMapView(View view){
		mMapView = (MapView) view.findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true);
//		mMapView.setLongClickable(true);

		mMapController = mMapView.getController();
		mMapController.enableClick(true);
		mMapController.setZoom(12);
		mMapView.regMapViewListener(HomeActivity.mBMapMan, null);
		initOverlay();
		//获取目前自身位置，设置地图中心点
		obatinLoc();
	}

	private void initView(View view) {
		mArea = (TextView) view.findViewById(R.id.id_area);
		mArea.setText(mCity);
		mArea.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
		    	  dialog.setTitle("请选择");
//		    	  dialog.setMessage("这是新添加的");
		    	  dialog.setView(getSpinnerView());
		    	  
		    	  dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						mArea.setText(mCity);
						SearchGeo(mCity);
					}
				});
		    	dialog.show();
			}
		});
		
		mAreaList = new ArrayList<String>();
		
		mSearchEditText = (EditText) view
		.findViewById(R.id.id_search_editview);
		mSearchEditText.addTextChangedListener(filterTextWatcher);
		
		mSelectorTextView = (TextView) view
				.findViewById(R.id.id_search_spinner_text);
		mSelectorTextView.setText(spinner_text[0]);
		mSelectorTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dialog dialog = null;
				Activity activity = getActivity();
				Builder builder = new AlertDialog.Builder(activity);
				final ChoiceOnClickListener choiceListener = new ChoiceOnClickListener();
				builder.setSingleChoiceItems(spinner_text, 0, choiceListener);
				DialogInterface.OnClickListener btnListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface,
							int which) {
						mSelectorTextView.setText(spinner_text[choiceListener
								.getWhich()]);
					}
				};
				builder.setPositiveButton("确定", btnListener);
				dialog = builder.create();
				dialog.show();

			}
		});
		
		mLogin = (TextView) view.findViewById(R.id.id_login);
		if(StringUtil.isValidStr(User.uid)){
			mLogin.setText("已登录");
		}
		mLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!StringUtil.isValidStr(User.uid)){
				HomeActivity.tabHost.setCurrentTabByTag("user");
				}
			}
		});
		
		picker = (WheelView) view.findViewById(R.id.picker);

	}
	
	private LoginListener mLoginListener = new LoginListener() {
		
		@Override
		public void Login(Class<?> cls) {
			// TODO Auto-generated method stub
			if(StringUtil.isValidStr(User.uid)){
				mLogin.setText("已登录");
			}
		}
	};

	private void initData() {
		mSellerNameList = new ArrayList<String>();
		mSellerFilterNameList = new ArrayList<String>();
		mItems = new ArrayList<OverlayItem>();
		mFilterItems = new ArrayList<OverlayItem>();
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, spinner_text);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		picker.setVisibleItems(8);
		
		picker.addChangingListener(listener);

	}

	OnWheelChangedListener listener = new OnWheelChangedListener() {
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			showOverlayItem(newValue);
		}
	};

	class SpinnerSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			showToast(spinner_text[arg2]);
		}
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	@Override
	public void onDestroy() {
		//退出时销毁定位
        if (mLocClient != null)
            mLocClient.stop();
        isLocationClientStop = true;
        if(mMapView!=null){
        	mMapView.destroy();
        	
        }
		super.onDestroy();
	}

	@Override
	public void onPause() {
		isLocationClientStop = true;
		 if(mMapView!=null){
			 
			 mMapView.onPause();
		 }
		
		super.onPause();
	}

	@Override
	public void onResume() {
		isLocationClientStop = false;
		 if(mMapView!=null){
			 mMapView.onResume();
			 if(!isFirstStart){
				 showOverlayItem(-1);
			 }
		 }
		
		super.onResume();
	}
	
	private void SearchGeo(final String city){
		//Geo搜索
		new DoAsyncTask(getActivity(), "正在努力查找城市...", new DoTask() {
			
			@Override
			public Object task() {
				// TODO Auto-generated method stub
				return Apicmd.getInstance().getGeoCity(getActivity(), city);
			}
		}, new DoAsyncTask.PostResult() {
			
			@Override
			public void result(Object result) {
				// TODO Auto-generated method stub
				if(result!=null){
					GeoCity g = (GeoCity)result;
					if(g.isSucceed()){
						//地图移动到该点
						mArea.setText(mCity);
						mMapView.getController().animateTo(new GeoPoint((int)(g.lat*1e6), (int)(g.lng*1e6)));
						searchSeller();
						
					}
				}
				
			}
		}, true, true);
	}

	private void initOverlay() {
		mOverlay = new MerchantOverlay(getResources().getDrawable(
				R.drawable.nav_turn_via_1), mMapView);
		
		/**
		 * 向地图添加自定义View.
		 */
		mPopCustomView = getActivity().getLayoutInflater().inflate(
				R.layout.merchantiteminbdmap, null);
		
		mMerchantName = (TextView) mPopCustomView
				.findViewById(R.id.id_inbaidumap_merchant_item_name);
		mMerchantHot = (TextView) mPopCustomView
				.findViewById(R.id.id_inbaidumap_merchant_item_hot);
		mMerchantGoodsCount = (TextView) mPopCustomView
				.findViewById(R.id.id_inbaidumap_merchant_item_goods_count);
		mMerchantPhone = (TextView) mPopCustomView
				.findViewById(R.id.id_inbaidumap_merchant_item_phone);
		mMerchantImage = (ImageView) mPopCustomView
				.findViewById(R.id.id_inbaidumap_merchant_item_image);
		
		
		 mMapView.getOverlays().add(mOverlay);
		/**
         * 创建一个popupoverlay 创建pop对象，注册点击事件监听接口
         */
		mPop = new PopupOverlay(mMapView,new PopupClickListener() {
			
			@Override
			public void onClickedPopup(int index) {
				// TODO Auto-generated method stub
				Seller seller = getSeller(mCurItem.getTitle());
				if(seller!=null){
					Intent i = new Intent(getActivity(), Merchant.class);
					i.putExtra(HOME_FRAGMENT_MSG, seller.sid);
					startActivity(i);
					isFirstStart=false;
				}
				
			}
		});

	}
	
    /**
     * 清除所有Overlay
     * @param view
     */
    private void clearOverlay(View view){
    	mOverlay.removeAll();
    	if (mPop != null){
            mPop.hidePop();
    	}
    	mMapView.refresh();
    }
    
    /**
     * 重新添加Overlay
     * @param view
     */
    private void resetOverlay(View view){
    	clearOverlay(null);
    	//重新add overlay
    	if(mFilterItems!=null&&mFilterItems.size()>0){
    		mOverlay.addItem(mFilterItems);
    	}else{
    		mOverlay.addItem(mItems);
    	}
    	mMapView.refresh();
    }
    
    

	private class ChoiceOnClickListener implements
			DialogInterface.OnClickListener {

		private int which = 0;

		@Override
		public void onClick(DialogInterface dialogInterface, int which) {
			this.which = which;
		}

		public int getWhich() {
			return which;
		}
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			mLocData.latitude = location.getLatitude();
			mLocData.longitude = location.getLongitude();
//			//如果不显示定位精度圈，将accuracy赋值为0即可
//			mLocData.accuracy = location.getRadius();
//			mLocData.direction = location.getDerect();
			if (isFirstLoc&& location.getLocType()==61){
				mCity = location.getCity();
				searchSeller();
                mMapController.animateTo(new GeoPoint((int)(mLocData.latitude* 1e6), (int)(mLocData.longitude *  1e6)));
			}else{
				final AlertDialog.Builder auto=new AlertDialog.Builder(getActivity());
				final AlertDialog.Builder d = new AlertDialog.Builder(getActivity());
				d.setTitle("提示");
				d.setMessage("定位失败，是否尝试手动选择，否则将启动默认城市");
				d.setNegativeButton("定位自己所在城市", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						dialog.dismiss();
						auto.setTitle("请选择");
						auto.setView(getSpinnerView());
				    	  
						auto.setNegativeButton("确定", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								SearchGeo(mCity);
							}
						});
						auto.show();
					}
				});
				
				d.setPositiveButton("去北京看看~~", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mMapController.setCenter(new GeoPoint((int)(beijingLoc[0]*1e6), (int)(beijingLoc[1]*1e6)));
						dialog.dismiss();
						mArea.setText(mCity);
						searchSeller();
					}
				});
				
				d.show();
				
				
			}
			mLocClient.unRegisterLocationListener(myListener);
			//首次定位完成
			isFirstLoc = false;
			
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}
			
			LogUtil.d("LOC", sb.toString());
            
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
//			StringBuffer sb = new StringBuffer(256);
//			sb.append("Poi time : ");
//			sb.append(poiLocation.getTime());
//			sb.append("\nerror code : ");
//			sb.append(poiLocation.getLocType());
//			sb.append("\nlatitude : ");
//			sb.append(poiLocation.getLatitude());
//			sb.append("\nlontitude : ");
//			sb.append(poiLocation.getLongitude());
//			sb.append("\nradius : ");
//			sb.append(poiLocation.getRadius());
//			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
//				sb.append("\naddr : ");
//				sb.append(poiLocation.getAddrStr());
//			}
//			if (poiLocation.hasPoi()) {
//				sb.append("\nPoi:");
//				sb.append(poiLocation.getPoi());
//			} else {
//				sb.append("noPoi information");
//			}
//			// logMsg(sb.toString());
		}
	}

	private class MerchantOverlay extends ItemizedOverlay {

		public MerchantOverlay(Drawable mark, MapView mapView) {
			super(mark, mapView);

		}

		@Override
		public boolean onTap(GeoPoint pt, MapView mapView) {
			// TODO Auto-generated method stub
//			super.onTap(pt, mapView);
			LogUtil.d("LOC", "Geo Point = "+ pt.getLatitudeE6()+"--"+pt.getLongitudeE6());
			return false;
		}

		@Override
		protected boolean onTap(int index) {
			// TODO Auto-generated method stub
			OverlayItem item = getItem(index);
			mCurItem = item;
			
			showOverlayItem(-1);
			picker.setCurrentItem(index);
			return true;
		}

	}
	
	/**
	 * 把view转换成bitmap
	 * @param view
	 * @return
	 */
	
	public Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache(true);
        return bitmap;
	}
	
	private void showOverlayItem(int index){
		if(index>=0){
		if(mFilterItems.size()>0){
			mCurItem = mFilterItems.get(index);
		}else{
			mCurItem = mItems.get(index);
		}
		}
		
		final Seller seller = getSeller(mCurItem.getTitle());
		if(seller!=null){
			mMerchantImage.setTag(seller.spic);
			new AsyncViewTask(100,new PostExecute() {
				
				@Override
				public void result(boolean result) {
					// TODO Auto-generated method stub
					if(result){
						mMerchantName.setText(seller.name);
						mMerchantHot.setText("人气:"+seller.click);
						mMerchantGoodsCount.setText("商品数量:"+seller.snum+"");
						mMerchantPhone.setText("电话:"+seller.phone);
						
						Bitmap[] bitMaps={getBitmapFromView(mPopCustomView)};		
						mPop.showPopup(bitMaps, mCurItem.getPoint(), 32);
						mMapController.setCenter(mCurItem.getPoint());
						mMapView.refresh();
					}else{
						mMerchantName.setText(seller.name);
						mMerchantHot.setText("人气:"+seller.click);
						mMerchantGoodsCount.setText("商品数量:"+seller.snum+"");
						mMerchantPhone.setText("电话:"+seller.phone);
						
						Bitmap[] bitMaps={getBitmapFromView(mPopCustomView)};		
						mPop.showPopup(bitMaps, mCurItem.getPoint(), 32);
						mMapController.setCenter(mCurItem.getPoint());
						mMapView.refresh();
					}
				}
			}).execute(mMerchantImage);
			mMapView.refresh();
		}
	}
	
	private Seller getSeller(String key){
		int count = mSellers.sellerList.size();
		for(int i=0;i<count;++i){
			Seller s = mSellers.sellerList.get(i);
			if(s.name.equals(key)){
				return s;
			}
		}
		return null;
	}
	
	private void searchSeller(){
		
		GeoPoint gp = mMapView.getProjection().fromPixels(0, 0);
		LogUtil.d("LOC", "屏幕左上角坐标(0,0)转换GeoPoint = 纬度："+gp.getLatitudeE6()+"经度："+gp.getLongitudeE6());
		
		
		new DoAsyncTask(getActivity(), "正在努力搜索附近商家...", new DoAsyncTask.DoTask() {
			@Override
			public Object task() {
				// TODO Auto-generated method stub
				return Apicmd.getInstance().getMerchantsInArea(getActivity(), "2", StringUtil.HanziToHanyupinyin(mCity), "", "");
			}
		}, new DoAsyncTask.PostResult() {

			@Override
			public void result(Object result) {
				// TODO Auto-generated method stub
				if(result != null){
					mSellers = (SellerInArea)result;
					int count = mSellerNameList.size();
					if(count>0){
						mSellerNameList.clear();
					}
					for(Seller s : mSellers.sellerList){
						mSellerNameList.add(s.name);
					}
					count = mSellerNameList.size();
					String[] sellerName = mSellerNameList.toArray(new String[count]);
					WheelAdapter w = new WheelAdapter(
							getActivity(), sellerName);
					w.setTextSize(12);
					picker.setViewAdapter(w);
					
					updataOverlayItem();
					
					showOverlayItem(0);
				}else{
					Toast.makeText(getActivity(), "搜索失败", Toast.LENGTH_SHORT).show();
				}
			}
		},true, true);
	}
	
	
	private void updataOverlayItem(){
		clearOverlay(null);
		int num = mSellers==null?0:mSellers.sellerList.size();
		for(int i =0; i< num;i++){
			if(mSellers!=null && mSellers.sellerList!=null){
				Seller s = mSellers.sellerList.get(i);
				double[] lonlat = s.getlonlat();

				GeoPoint p = new GeoPoint((int) (lonlat[1] * 1e6),
						(int) (lonlat[0] * 1e6));
				OverlayItem item = new OverlayItem(p, s.name, "");
				mOverlay.addItem(item);
			}
		}
		mItems.addAll(mOverlay.getAllItem());
	}
	
	private void updataFilterOverlayItem(){
		clearOverlay(null);
		int num = mSellers==null?0:mSellers.sellerList.size();
		for(int i =0; i< num;i++){
			if(mSellers!=null && mSellers.sellerList!=null){
				Seller s = mSellers.sellerList.get(i);
				double[] lonlat = s.getlonlat();

				GeoPoint p = new GeoPoint((int) (lonlat[1] * 1e6),
						(int) (lonlat[0] * 1e6));
				OverlayItem item = new OverlayItem(p, s.name, "");
				mOverlay.addItem(item);
			}
		}
		mItems.addAll(mOverlay.getAllItem());
	}
	
	
	
	
	private TextWatcher filterTextWatcher = new TextWatcher() {  
		  
        @Override  
        public void afterTextChanged(Editable s) {  
        }  
        @Override  
        public void beforeTextChanged(CharSequence s, int start, int count,  
                int after) {  
        }  
        @Override  
        public void onTextChanged(CharSequence s, int start, int before,  
                int count) {  
        	mFilterItems.clear();
        	mSellerFilterNameList.clear();
        	String key = mSearchEditText.getText().toString();
        	int c = mItems.size();
        	for(int i=0;i<c;i++){
        		OverlayItem item = mItems.get(i);
        		String name = item.getTitle();
        		if(item.getTitle().contains(key)){
        			mFilterItems.add(item);
        			mSellerFilterNameList.add(name);
        		}
        	}
        	int filterCount = mSellerFilterNameList.size();
			String[] sellerName = mSellerFilterNameList.toArray(new String[filterCount]);
			WheelAdapter w = new WheelAdapter(
					getActivity(), sellerName);
			w.setTextSize(12);
			picker.setViewAdapter(w);
			if(mFilterItems.size()>0){
				resetOverlay(null);
				showOverlayItem(0);
			}else{
				clearOverlay(null);
//				Toast.makeText(getActivity(), "很抱歉~没有找到哟,->_->搜搜其他的", Toast.LENGTH_LONG).show();
			}
        	
        }  
  
    }; 
    
    private class WheelAdapter extends ArrayWheelAdapter<String> {
    	private String[] items;
		public WheelAdapter(Context context, String[] items) {
			super(context, items);
			this.items = items;
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public int getTextSize() {
			// TODO Auto-generated method stub
			return 9;
		}

		@Override
		public CharSequence getItemText(int index) {
			// TODO Auto-generated method stub
			return items==null?"":items[index];
		}

		@Override
		public int getItemsCount() {
			// TODO Auto-generated method stub
			return items==null?0:items.length;
		}
    	
    }
    
    
    private void SpinnerInDialog(){
    	  AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
    	  dialog.setTitle("请选择");
    	  dialog.setView(getSpinnerView());
    	  
    	  dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				
			}
		});
    	dialog.show();
    }
    
	private ArrayAdapter<CharSequence> province_adapter;
	private ArrayAdapter<CharSequence> city_adapter;
	
	private Integer provinceId, cityId;
	private String strProvince, strCity;
    
    private View getSpinnerView(){
    	LinearLayout layout = new LinearLayout(getActivity());
    	layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    	layout.setGravity(Gravity.CENTER_VERTICAL);
    	
    	final Spinner p = new Spinner(getActivity());
    	p.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    	
    	final Spinner city = new Spinner(getActivity());
    	city.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    	
    	
	    //绑定省份的数据
		p.setPrompt("请选择省份");
		province_adapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.province_item, android.R.layout.simple_spinner_item);
		province_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		p.setAdapter(province_adapter);
    	
		// select(province_spinner, province_adapter, R.array.province_item);
	    //添加监听，一开始的时候城市，县区的内容是不显示的而是根据省的内容进行联动
		p.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						provinceId = p.getSelectedItemPosition();
						strProvince = p.getSelectedItem()
								.toString();//得到选择的内容，也就是省的名字
						if (true) {
//							county_spinner = (Spinner) findViewById(R.id.county_spinner);
//							city_spinner = (Spinner) findViewById(R.id.city_spinner);
							city.setPrompt("请选择城市");//设置标题
							select(city, city_adapter, PCCModel.city[provinceId]);//城市一级的数据绑定
							/*通过这个city[provinceId]指明了该省市的City集合
							 * R。array.beijing*/
							city
									.setOnItemSelectedListener(new OnItemSelectedListener() {

										@Override
										public void onItemSelected(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											cityId = city
													.getSelectedItemPosition();//得到city的id
											strCity = city
													.getSelectedItem()
													.toString();//得到city的内容
											mCity = strCity;
//											}
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
		
		
		layout.addView(p);
		layout.addView(city);
    	return layout;
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
