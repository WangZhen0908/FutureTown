package com.vvage.futuretown.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vvage.futuretown.R;
import com.vvage.futuretown.base.BaseView;
import com.vvage.futuretown.cmd.Apicmd;
import com.vvage.futuretown.model.Address;
import com.vvage.futuretown.model.Address.AddressList;
import com.vvage.futuretown.model.Address_save;
import com.vvage.futuretown.model.User;
import com.vvage.futuretown.util.DoAsyncTask;
import com.vvage.futuretown.util.DoAsyncTask.DoTask;
import com.vvage.futuretown.util.StringUtil;

public class ShippingActivity extends BaseView {
	private final int SHIPPING_RESULT=0x6000;
	
	private List<AddressList> data;
//	private ListView lv;
	private LinearLayout listLayout;
	private List<View> item = new ArrayList<View>();
	
	private View view;
	
	private EditText name_ed,ph_ed,add_ed;
	private Button add_di,add_shou,baocun_btn;

	private OnClickListener myOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.add_di:{
				Intent i = new Intent(getApplicationContext(), LocationForApi.class);
				startActivityForResult(i, SHIPPING_RESULT);
				startActivityForResult(i, LocationForApi.LOCAPI_LAT_LNG); 
				break;
			}
			case R.id.add_shou:{
				
				break;
			}
			case R.id.baocun_btn:{
				final String name = name_ed.getText().toString();
				final String phone = ph_ed.getText().toString();
				final String address = add_ed.getText().toString();
				if(!StringUtil.isValidStr(name)){
					Toast.makeText(getBaseContext(), "姓名不能为空，请正确输入", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!StringUtil.isValidStr(phone)){
					Toast.makeText(getBaseContext(), "电话不能为空，请正确输入", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!StringUtil.isValidStr(address)){
					Toast.makeText(getBaseContext(), "收货地不能为空，请正确输入", Toast.LENGTH_SHORT).show();
					return;
				}
				DoAsyncTask t = new DoAsyncTask(ShippingActivity.this, "正在提交收货地址", new DoTask() {
					@Override
					public Object task() {
						// TODO Auto-generated method stub
						return Apicmd.getInstance().getAddress_save(getApplicationContext(),
								"", 0, 0, 0, address, name, phone, "");
					}
				}, new DoAsyncTask.PostResult(){

					@Override
					public void result(Object result) {
						// TODO Auto-generated method stub
						if(result!=null){
							Address_save as = (Address_save)result;
							if(as.isSucceed()){
								DoAsyncTask t = new DoAsyncTask(ShippingActivity.this, "正在加载...", new DoTask() {
									@Override
									public Object task() {
										// TODO Auto-generated method stub
										return Apicmd.getInstance().getAddress(getApplicationContext(), User.uid);
									}
								}, new DoAsyncTask.PostResult() {
									
									@Override
									public void result(Object result) {
										// TODO Auto-generated method stub
										if(result!=null){
											Address a = (Address)result;
											if(a.addressList!=null){
												listLayout.removeAllViews();
												int count = a.addressList.size();
												for(int i=0;i<count;++i){
													listLayout.addView(getItemView(a.addressList.get(i), i));
												}
												Toast.makeText(getBaseContext(), "提交成功", Toast.LENGTH_SHORT).show();
											}
										}else{
											Toast.makeText(getBaseContext(), "网络错误,请稍后尝试", Toast.LENGTH_SHORT).show();
										}
									}
								}, true, true);
							}else{
								Toast.makeText(getBaseContext(), "网络错误,请稍后尝试", Toast.LENGTH_SHORT).show();
							}
						}
					}
				}, true, true);
				break;
			}

			default:
				break;
			}
		}
	};
	

	@Override
	protected View creatView(Context context) {
		// TODO Auto-generated method stub
		setTitleParams(new TitleParams("返回", View.VISIBLE, "收货地址", null, "", View.GONE));
		
		view = LayoutInflater.from(context).inflate(R.layout.shippingadd, null);
		listLayout = (LinearLayout)view.findViewById(R.id.ship_list_layout);
//		lv = (ListView)view.findViewById(R.id.ship_lv);
		
		name_ed = (EditText)view.findViewById(R.id.name_ed);
		ph_ed = (EditText)view.findViewById(R.id.ph_ed);
		add_ed = (EditText)view.findViewById(R.id.add_ed);
		
		add_di = (Button)view.findViewById(R.id.add_di);
		add_shou = (Button)view.findViewById(R.id.add_shou);
		baocun_btn = (Button)view.findViewById(R.id.baocun_btn);
		
		add_di.setOnClickListener(myOnClickListener);
		add_shou.setOnClickListener(myOnClickListener);
		baocun_btn.setOnClickListener(myOnClickListener);

//		lv.setAdapter(new AddresAdapter(list));
		DoAsyncTask t = new DoAsyncTask(ShippingActivity.this, "正在加载...", new DoTask() {
			@Override
			public Object task() {
				// TODO Auto-generated method stub
				return Apicmd.getInstance().getAddress(getApplicationContext(),User.uid);
			}
		}, new DoAsyncTask.PostResult() {
			
			@Override
			public void result(Object result) {
				// TODO Auto-generated method stub
				if(result!=null){
					Address a = (Address)result;
					if(a.isSucceed()){
						if(a.addressList!=null){
							int count = a.addressList.size();
							for(int i=0;i<count;++i){
								listLayout.addView(getItemView(a.addressList.get(i), i));
							}
						}
					}
				}else{
					Toast.makeText(getBaseContext(), "网络错误,请稍后尝试", Toast.LENGTH_SHORT).show();
				}
			}
		}, false, false);
		return view;
	}



	@Override
	protected void onClickLeft() {
		// TODO Auto-generated method stub
		finish();
	}



	@Override
	protected void onClickRight() {
		// TODO Auto-generated method stub
		
	}
	
	private View getItemView(AddressList as, int position){
		LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setBackgroundResource(R.drawable.pai_edit_03);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		AbsListView.LayoutParams layoutLPS = new AbsListView.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(layoutLPS);
		
		LinearLayout.LayoutParams snLPS = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		snLPS.topMargin = 10;
//		snLPS.gravity = Gravity.CENTER;
		TextView sn = new TextView(getApplicationContext());
		sn.setTextColor(Color.WHITE);
		sn.setGravity(Gravity.CENTER);
		sn.setBackgroundResource(R.drawable.shipping_sn);
		sn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		
		TextView name = new TextView(getApplicationContext());
		name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		name.setPadding(10, 0, 5, 5);
		TextView phone = new TextView(getApplicationContext());
		phone.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		phone.setPadding(10, 0, 5, 5);
		TextView address = new TextView(getApplicationContext());
		address.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		address.setPadding(10, 0, 5, 5);
		
		layout.addView(sn, snLPS);
		layout.addView(name);
		layout.addView(phone);
		layout.addView(address);
//		layout.setTag(new ViewHolder(sn, name, phone, address));
		
		if(address!=null){
			sn.setText(""+position);
			name.setText("收货人："+as.name);
			phone.setText("收货人电话："+as.phone);
			address.setText("收货地址："+as.region+" "+as.address);
		}
		return layout;
	}
	
	private class ViewHolder{
		public TextView sn;
		public TextView name;
		public TextView phone;
		public TextView address;
		
		public ViewHolder(TextView sn,TextView name,
				TextView phone, TextView address){
			this.sn = sn;
			this.name = name;
			this.phone = phone;
			this.address = address;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		 switch (requestCode) {
		    case LocationForApi.LOCAPI_LAT_LNG:{
		    	if (resultCode == Activity.RESULT_OK) {
		    		Bundle b = data.getBundleExtra(LocationForApi.LOC_BUNDLE);
		    		if(b!=null){
//		    			mSellermap[0]=b.getIntArray("latlog")[1]+"";
//		    			mSellermap[1]=b.getIntArray("latlog")[0]+"";
		    			
		    				String str[] = b.getStringArray("address");
		    				int count = str==null?0:str.length;
		    				String s="";
		    				for(int i=0;i<count;++i){
		    					s+=str[i];
		    				}
		    				add_ed.setText(s);
		    				
		    		}
		    	}
		    		break;
		    	}
		    default:
		    break;
		 }
		
	}
	
	
	
//	private class AddressAdapter extends BaseAdapter {
//		private List<AddressList> addres;
//		
//		public AddressAdapter(List<AddressList> list){
//			addres = list;
//		}
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			int count = 0;
//			if(addres!=null){
//				count =  addres.size();
//			}
//			return count;
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			// TODO Auto-generated method stub
//			if(addres!=null){
//				return addres.get(arg0);
//			}
//			return null;
//		}
//
//		@Override
//		public long getItemId(int arg0) {
//			// TODO Auto-generated method stub
//			return arg0;
//		}
//
//		@Override
//		public View getView(int arg0, View arg1, ViewGroup arg2) {
//			// TODO Auto-generated method stub
//			if(arg1 == null) {
//				arg1 = newItemView();
//			} 
//			bindView(arg0, arg1);
//			return arg1;
//		}
//		
//		private View newItemView(){
//			LinearLayout layout = new LinearLayout(getApplicationContext());
//			layout.setBackgroundResource(R.drawable.pai_edit_03);
//			layout.setOrientation(LinearLayout.VERTICAL);
//			layout.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
//			
//			LinearLayout.LayoutParams snLPS = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//			snLPS.topMargin = 10;
////			snLPS.gravity = Gravity.CENTER;
//			TextView sn = new TextView(getApplicationContext());
//			sn.setTextColor(Color.WHITE);
//			sn.setGravity(Gravity.CENTER);
//			sn.setBackgroundResource(R.drawable.shipping_sn);
//			sn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//			
//			TextView name = new TextView(getApplicationContext());
//			name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//			TextView phone = new TextView(getApplicationContext());
//			phone.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//			TextView address = new TextView(getApplicationContext());
//			phone.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//			layout.addView(sn, snLPS);
//			layout.addView(name);
//			layout.addView(phone);
//			layout.addView(address);
//			
////			View item = LayoutInflater.from(getApplicationContext())
////			.inflate(R.layout.list_merchant_item, null);
////			ImageView i = (ImageView) item
////					.findViewById(R.id.id_merchant_list_item_imageview);
////			TextView name = (TextView) item
////					.findViewById(R.id.id_merchant_list_item_goods_name_text);
////			TextView price = (TextView) item
////					.findViewById(R.id.id_merchant_list_item_price_text);
////			TextView detail = (TextView) item
////					.findViewById(R.id.id_merchant_list_item_goods_detail);
////			TextView purpose = (TextView) item
////					.findViewById(R.id.id_merchant_list_item_goods_purpose_type);
////			TextView phone = (TextView) item
////					.findViewById(R.id.id_merchant_list_item_dail);
////			
//			layout.setTag(new ViewHolder(sn, name, phone, address));
//			return layout;
//		}
//		
//		private void bindView(int position, View converView){
//			ViewHolder tag = (ViewHolder)converView.getTag();
////			tag.image
//			if(addres!=null){
//				final AddressList a = addres.get(position);
//				tag.sn.setText(""+position);
//				tag.name.setText("收货人："+a.name);
//				tag.phone.setText("收货人电话："+a.phone);
//				tag.address.setText("收货地址："+a.region+" "+a.address);
//			}
//			
//		}
//		
//		private class ViewHolder{
//			public TextView sn;
//			public TextView name;
//			public TextView phone;
//			public TextView address;
//			
//			public ViewHolder(TextView sn,TextView name,
//					TextView phone, TextView address){
//				this.sn = sn;
//				this.name = name;
//				this.phone = phone;
//				this.address = address;
//			}
//		}
//	}
}
