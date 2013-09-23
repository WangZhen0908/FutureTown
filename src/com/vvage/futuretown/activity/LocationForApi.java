package com.vvage.futuretown.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.vvage.futuretown.HomeActivity;
import com.vvage.futuretown.R;
import com.vvage.futuretown.util.LogUtil;

public class LocationForApi extends Activity {

	public static final int LOCAPI_LAT_LNG = 0x9000;
	public static final String LOC_BUNDLE = "latlog";
	private MapView mMapView=null;
	private MapController mMapController;
	private LocPositionOverLay mLocPosintonOverlay;
	
	private PopupOverlay mPop=null;
	
	
	//搜索相关
	private	MKSearch mSearch = null;	// 搜索模块，也可去掉地图模块独立使用
	private GeoPoint mGeoP;
	private String strInfo;
	
	private TextView mTextView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(this);
		layout.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		mMapView = new MapView(this);
		mMapView.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		layout.addView(mMapView);
		
		setContentView(layout);
		
		mMapView.setBuiltInZoomControls(true);
		
		mMapController = mMapView.getController();
		mMapController.enableClick(true);
		mMapController.setZoom(12);
		
		mLocPosintonOverlay = new LocPositionOverLay(getResources().
				getDrawable(R.drawable.nav_turn_via_1), mMapView);
		
		mMapView.getOverlays().add(mLocPosintonOverlay);
		mMapView.refresh();
		
		mPop = new PopupOverlay(mMapView, null);
		mSearch = new MKSearch();
		boolean r = mSearch.init(HomeActivity.mBMapMan, new MySearchListener());
		mTextView = new TextView(getApplicationContext());
		mTextView.setGravity(Gravity.CENTER);
        
//        LogUtil.d("baiduapi", ""+r);
		
	}



	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mMapView.destroy();
		super.onDestroy();
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mMapView.onPause();
		super.onPause();
	}



	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mMapView.onResume();
		super.onResume();
	}



	private class LocPositionOverLay extends ItemizedOverlay<OverlayItem>{

		public LocPositionOverLay(Drawable defaultMarker, MapView mapView) {
			super(defaultMarker, mapView);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean onTap(GeoPoint p, MapView mapView) {
			// TODO Auto-generated method stub
			mGeoP = p;
			mSearch.reverseGeocode(p);
			LogUtil.d("LOC", "Search = "+ "Lat = "+p.getLatitudeE6()+"Log = "+p.getLongitudeE6());
			return true;
		}
	}
	
	private void showOverlayItem(){
		
	}
	
	private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 1:
            	mLocPosintonOverlay.removeAll();
				OverlayItem item = new OverlayItem(mGeoP, strInfo, "");
				mLocPosintonOverlay.addItem(item);
				mMapView.refresh();
				Toast.makeText(LocationForApi.this, strInfo, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
            }
        }
    };
	
	private class MySearchListener implements MKSearchListener {
		@Override
		public void onGetPoiDetailSearchResult(int type, int error) {
		}
		
		public void onGetAddrResult(MKAddrInfo res, int error) {
			if (error != 0) {
				String str = String.format("错误号：%d", error);
				Toast.makeText(LocationForApi.this, str, Toast.LENGTH_SHORT).show();
				return;
			}
			//地图移动到该点
			mMapController.animateTo(res.geoPt);	
//		if (res.type == MKAddrInfo.MK_GEOCODE){
//			//地理编码：通过地址检索坐标点
//			String strInfo = String.format("纬度：%f 经度：%f", res.geoPt.getLatitudeE6()/1e6, res.geoPt.getLongitudeE6()/1e6);
//			Toast.makeText(LocationForApi.this, strInfo, Toast.LENGTH_LONG).show();
//		}
			if (res.type == MKAddrInfo.MK_REVERSEGEOCODE){
				//反地理编码：通过坐标点检索详细地址及周边poi
				
				LocationForApi.this.strInfo = res.addressComponents.province+
						res.addressComponents.city+res.addressComponents.district+
						res.addressComponents.street+res.addressComponents.streetNumber;
				
				Intent i=new Intent();  
				
				int latlog[]={mGeoP.getLatitudeE6(), mGeoP.getLongitudeE6()};
				String addressInfo[]={res.addressComponents.province, res.addressComponents.city,
						res.addressComponents.district, res.addressComponents.street, res.addressComponents.streetNumber};
				Bundle b = new Bundle();
				b.putIntArray("latlog", latlog);
				b.putStringArray("address", addressInfo);
				i.putExtra(LOC_BUNDLE, b);
		        setResult(Activity.RESULT_OK, i);
		        
		        
//				Toast.makeText(LocationForApi.this, strInfo, Toast.LENGTH_SHORT).show();
				 Message localMessage = new Message();
		         localMessage.what = 1;
		         handler.sendMessage(localMessage);
//				if(mPop!=null){
//					
//				}
				
			}
//		//生成ItemizedOverlay图层用来标注结果点
//		ItemizedOverlay<OverlayItem> itemOverlay = new ItemizedOverlay<OverlayItem>(null, mMapView);
//		//生成Item
//		OverlayItem item = new OverlayItem(res.geoPt, "", null);
//		//得到需要标在地图上的资源
//		Drawable marker = getResources().getDrawable(R.drawable.icon_markf);  
//		//为maker定义位置和边界
//		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
//		//给item设置marker
//		item.setMarker(marker);
//		//在图层上添加item
//		itemOverlay.addItem(item);
			
			//清除地图其他图层
//			mMapView.getOverlays().clear();
//		//添加一个标注ItemizedOverlay图层
//		mMapView.getOverlays().add(itemOverlay);
			//执行刷新使生效
			mMapView.refresh();
		}
		public void onGetPoiResult(MKPoiResult res, int type, int error) {
			
		}
		public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {
		}
		public void onGetTransitRouteResult(MKTransitRouteResult res, int error) {
		}
		public void onGetWalkingRouteResult(MKWalkingRouteResult res, int error) {
		}
		public void onGetBusDetailResult(MKBusLineResult result, int iError) {
		}
		@Override
		public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
		}
		
	};
}
