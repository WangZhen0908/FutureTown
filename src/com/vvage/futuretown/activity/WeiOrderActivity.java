package com.vvage.futuretown.activity;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.vvage.futuretown.Global;
import com.vvage.futuretown.R;
import com.vvage.futuretown.base.BaseView;
import com.vvage.futuretown.cmd.Apicmd;
import com.vvage.futuretown.model.Order;
import com.vvage.futuretown.model.Order.HandlerOrder;
import com.vvage.futuretown.model.Order.HandlerOrderProduct;
import com.vvage.futuretown.model.deleteUncheckProduct;
import com.vvage.futuretown.util.AsyncViewTask;
import com.vvage.futuretown.util.AsyncViewTask.PostExecute;
import com.vvage.futuretown.util.DoAsyncTask;
import com.vvage.futuretown.util.DoAsyncTask.DoTask;
import com.vvage.futuretown.util.DoAsyncTask.PostResult;
import com.vvage.futuretown.util.StringUtil;

/*
 * 商家  处理客户订单
 */
public class WeiOrderActivity extends BaseView{
	private ButtonTextView unHandler;
	private ButtonTextView handler;
	
	private ExpandableListView unhandleorderlist;
	private ExpandableListView handleorderlist;
	
	private int press=0;
	

	@Override
	protected View creatView(Context context) {
		// TODO Auto-generated method stub
		setTitleParams(new TitleParams("返回", View.VISIBLE, null, createTitleView(), "刷新", View.VISIBLE));
		View view = LayoutInflater.from(context).inflate(R.layout.myorder, null);
		unhandleorderlist = (ExpandableListView)view.findViewById(R.id.unhandle_order_list);
		handleorderlist = (ExpandableListView)view.findViewById(R.id.handle_order_list);
		refView();
		
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
		refView();
	}
	
	private void refView(){
		new DoAsyncTask(WeiOrderActivity.this, "正在刷新...", new DoTask() {
			
			@Override
			public Object task() {
				// TODO Auto-generated method stub
				return Apicmd.getInstance().getOrder(WeiOrderActivity.this, press+"");
			}
		},new PostResult() {
			
			@Override
			public void result(Object result) {
				// TODO Auto-generated method stub
				if(result!=null){
					Order order = (Order)result;
					if(order.isSucceed()){
						orderAdapter o = new orderAdapter(order.orderList);
						if(press==0){
							unhandleorderlist.setAdapter(o);
							int reCount = unhandleorderlist.getAdapter().getCount();
							for (int i=0; i<reCount; i++) {
								unhandleorderlist.expandGroup(i);
								};
						}else if(press==1){
							
							handleorderlist.setAdapter(o);
							int reCount = handleorderlist.getAdapter().getCount();
							for (int i=0; i<reCount; i++) {
								handleorderlist.expandGroup(i);
								};
						}
					}
				}
			}
		},true,true);
	}

	private class orderAdapter extends BaseExpandableListAdapter{

		
		private List<HandlerOrder> data;
		
		public orderAdapter(List<HandlerOrder> data){
			this.data = data;
		}
		
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			if(data!=null&&data.get(groupPosition)!=null&&data.get(groupPosition).hop!=null){
				return data.get(groupPosition).hop.get(childPosition);
			}
			return 0;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
                boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null){
				convertView = getItemView();
			}
			bindChindView(groupPosition, childPosition,  convertView);
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			if(data!=null&&data.get(groupPosition)!=null&&data.get(groupPosition).hop!=null){
				return data.get(groupPosition).hop.size();
			}
			return 0;
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			if(data!=null&&data.get(groupPosition)!=null){
				return data.get(groupPosition);
			}
			return null;
				
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			if(data!=null){
				return data.size();
			}
			
			return 0;
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
                View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null){
				convertView = getGroupView();
			}
			bindGroupView(groupPosition, convertView);
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}
		
		
		private View getGroupView(){
			View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.handlerorder_group, null);
			
			TextView name = (TextView)view.findViewById(R.id.ourorder_name);
			TextView phone = (TextView)view.findViewById(R.id.ourorder_phone);
			TextView ordersn = (TextView)view.findViewById(R.id.ourorder_order_sn);
			TextView call = (TextView)view.findViewById(R.id.ourorder_call);
			TextView note = (TextView)view.findViewById(R.id.ourorder_note);
			TextView shippingName = (TextView)view.findViewById(R.id.ourorder_shipping_name);
			TextView shippingPhone = (TextView)view.findViewById(R.id.ourorder_shipping_phone);
			TextView address = (TextView)view.findViewById(R.id.ourorder_address);
			
			
			view.setTag(new GroupViewHolder(name, phone, ordersn,
					call, note, shippingName, shippingPhone, address));
			
			return view;
		}
		
		private View getItemView(){
			LinearLayout main = new LinearLayout(getApplicationContext());
			main.setOrientation(LinearLayout.VERTICAL);
			main.setLayoutParams(new AbsListView.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
			LinearLayout layout = new LinearLayout(getApplicationContext());
			layout.setPadding(10, 10, 10, 10);
			layout.setLayoutParams(new AbsListView.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
			LinearLayout.LayoutParams imLPS = new LinearLayout.LayoutParams(
					Global.WIDTH_SCREEN/3, LayoutParams.WRAP_CONTENT);
			ImageView im = new ImageView(getApplicationContext());
			
			LinearLayout right = new LinearLayout(getApplicationContext());
			right.setPadding(10, 10, 10, 10);
			LinearLayout.LayoutParams rightLPS = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			rightLPS.leftMargin = 15;
			right.setLayoutParams(rightLPS);
			right.setOrientation(LinearLayout.VERTICAL);
			
			TextView name = new TextView(getApplicationContext());
			name.setTextColor(Color.BLACK);
			TextView price = new TextView(getApplicationContext());
			price.setTextColor(Color.BLACK);
			right.addView(name);
			right.addView(price);
			
			LinearLayout statusLayout = new LinearLayout(getApplicationContext());
			statusLayout.setPadding(10, 10, 10, 10);
			statusLayout.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
			TextView del = new TextView(getApplicationContext());
			del.setPadding(20,10,20,10);
			del.setText("删除商品");
			del.setTextColor(Color.WHITE);
			del.setBackgroundResource(R.drawable.bg_blue);
			
			LinearLayout.LayoutParams editLPS = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			editLPS.rightMargin = 10;
			TextView edit = new TextView(getApplicationContext());
			edit.setPadding(20,10,20,10);
			edit.setText("编辑商品");
			edit.setTextColor(Color.WHITE);
			edit.setBackgroundResource(R.drawable.bg_blue);
			
			
			statusLayout.addView(del);
			statusLayout.addView(edit, editLPS);
			right.addView(statusLayout);
			
			layout.addView(im, imLPS);
			layout.addView(right);
			
			View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.handlerorder_footer, null);
			TextView unshipping = (TextView)view.findViewById(R.id.unshipping);
			TextView shipping = (TextView)view.findViewById(R.id.shipping);
			EditText note = (EditText)view.findViewById(R.id.note);
			
			main.addView(layout);
			main.addView(view);
			
			main.setTag(new ChildViewHolder(im, name, price, del, edit, unshipping, shipping, note));
			
			return main;
		}
		
		private void bindGroupView(int groupPosition, View convertView){
			final HandlerOrder tc = data.get(groupPosition);
			
			final GroupViewHolder gh = (GroupViewHolder)convertView.getTag();
			gh.name.setText("用户名："+tc.username);
			gh.phone.setText("电话："+tc.userphone);
			gh.ordersn.setText("订单号："+tc.orderid);
			gh.note.setText("用户附言："+tc.extmsg);
			
			gh.shippingName.setText("收货人："+tc.shippingname);
			gh.shippingPhone.setText("电话："+tc.shippingphone);
			gh.address.setText("地址："+tc.shippingaddress);
			
//			new DoAsyncTask(getApplicationContext(), "", new DoTask() {
//				
//				@Override
//				public Object task() {
//					// TODO Auto-generated method stub
//					return Apicmd.getInstance().getAddress(WeiOrderActivity.this, tc.userid);
//				}
//			}, new PostResult() {
//				
//				@Override
//				public void result(Object result) {
//					// TODO Auto-generated method stub
//					if(result!=null){
//						Address a = (Address)result;
//						if(a.isSucceed()){
//							int addressid = -1;
//							if(StringUtil.isValidStr(tc.addressid)){
//								addressid = Integer.valueOf(tc.addressid).intValue();
//							}
//							if(addressid>-1&&a.addressList!=null&&a.addressList.size()>0){
//								AddressList item = a.addressList.get(addressid);
//								
//								gh.shippingName.setText("收货人："+item.name);
//								gh.shippingPhone.setText("电话："+item.phone);
//								gh.address.setText("地址："+item.address);
//							}
//						}
//					}
//				}
//			}, false, false);
			
			gh.call.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(StringUtil.isMobileNumber(tc.userphone)){
						Global.call(WeiOrderActivity.this, tc.userphone);
					}else{
						Toast.makeText(getApplicationContext(), 
								"手机号码错误", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
		
		
		private void bindChindView(int groupPosition, int childPosition, View convertView){
			final HandlerOrderProduct tc = data.get(groupPosition).hop.get(childPosition);
			
			final ChildViewHolder ch = (ChildViewHolder)convertView.getTag();
			ch.im.setTag(tc.url);
			new AsyncViewTask(Global.WIDTH_SCREEN/3, new PostExecute() {
				
				@Override
				public void result(boolean result) {
					// TODO Auto-generated method stub
					
				}
			}).execute(ch.im);
			
			ch.name.setText("品名："+tc.name);
			ch.price.setText("金额：￥"+tc.price);
			if(press==0){
				ch.del.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						new DoAsyncTask(getApplicationContext(), "正在删除", new DoTask() {
							
							@Override
							public Object task() {
								// TODO Auto-generated method stub
								return Apicmd.getInstance().getdeleteUncheckProduct(getApplicationContext(), tc.id);
							}
						}, new PostResult() {
							
							@Override
							public void result(Object result) {
								// TODO Auto-generated method stub
								if(result!=null){
									deleteUncheckProduct dp = (deleteUncheckProduct)result;
									if(dp.isSucceed()){
										Toast.makeText(getApplicationContext(), "删除商品成功", Toast.LENGTH_SHORT).show();
									}else{
										Toast.makeText(getApplicationContext(), "删除商品失败", Toast.LENGTH_SHORT).show();
									}
								}else{
									Toast.makeText(getApplicationContext(), "删除商品失败", Toast.LENGTH_SHORT).show();
								}
							}
						}, true, true);
					}
				});
				
				ch.edit.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), 
								"编辑商品", Toast.LENGTH_SHORT).show();
					}
				});
				
				ch.unshipping.setText("送货");
				ch.shipping.setText("不送货");
				ch.note.setText("附言：");
			}else if(press==1){
				ch.del.setVisibility(View.GONE);
				ch.edit.setVisibility(View.GONE);
				ch.unshipping.setText("已送货");
				ch.shipping.setText("未送货");
				ch.note.setText("附言：");
			}
		}
		
		private class ChildViewHolder{
			public ImageView im;
			public TextView name;
			public TextView price;
			public TextView del;
			public TextView edit;
			
			public TextView unshipping;
			public TextView shipping;
			public EditText note;
			
			public ChildViewHolder(ImageView im, TextView name,
					TextView price, TextView del, TextView edit,
					TextView unshipping, TextView shipping,EditText note){
				this.im = im;
				this.name = name;
				this.price = price;
				this.del = del;
				this.edit = edit;
				
				this.unshipping = unshipping;
				this.shipping = shipping;
				this.note = note;
			}
		}
		
		private class GroupViewHolder{
			public TextView name;
			public TextView phone;
			public TextView ordersn;
			public TextView call;
			public TextView note;
			public TextView shippingName;
			public TextView shippingPhone;
			public TextView address;
			
			public GroupViewHolder(TextView name, TextView phone, TextView ordersn,
					TextView call, TextView note, TextView shippingName, 
					TextView shippingPhone,TextView address){
				this.name = name;
				this.phone = phone;
				this.ordersn = ordersn;
				this.call = call;
				this.note = note;
				this.shippingName = shippingName;
				this.shippingPhone = shippingPhone;
				this.address = address;
			}
		}
		
	}
	
	private View createTitleView(){
		LinearLayout layout = new LinearLayout(this);
		
		unHandler = new ButtonTextView(this);
		unHandler.setTag(unHandler);
		unHandler.setBackgroundResource(R.drawable.btn_title_l_press);
		unHandler.setText("未处理订单");
		
		unHandler.setOnClickListener(mOnClickListener);
		
		handler = new ButtonTextView(this);
		handler.setTag(handler);
		handler.setBackgroundResource(R.drawable.btn_title_r);
		handler.setText("已处理订单");
		handler.setOnClickListener(mOnClickListener);
		
		layout.addView(unHandler);
		layout.addView(handler);
		return layout;
	}
	
	private void switchButton(int type){
		press = type;
		if(type==0){
			unHandler.setBackgroundResource(R.drawable.btn_title_l_press);
			handler.setBackgroundResource(R.drawable.btn_title_r);
			unhandleorderlist.setVisibility(View.VISIBLE);
			handleorderlist.setVisibility(View.GONE);
		}else if(type==1){
			unHandler.setBackgroundResource(R.drawable.btn_title_l);
			handler.setBackgroundResource(R.drawable.btn_title_r_press);
			unhandleorderlist.setVisibility(View.GONE);
			handleorderlist.setVisibility(View.VISIBLE);
		}
	}
	
	private OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getTag() == unHandler){
				switchButton(0);
			}else if(v.getTag() == handler){
				switchButton(1);
				
			}
		}
	};


	@Override
	protected void getViewData() {
		// TODO Auto-generated method stub
	}
	
	private void setHeight(ExpandableListView eplv){
		ListAdapter adapter = eplv.getAdapter();
		int totalHeight = 0;
        for (int i = 0, len = adapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
            View listItem = adapter.getView(i, null, eplv);
            listItem.measure(0, 0);  //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = eplv.getLayoutParams();
        params.height = totalHeight + (eplv.getDividerHeight() * (adapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        eplv.setLayoutParams(params);
	}
	
}
