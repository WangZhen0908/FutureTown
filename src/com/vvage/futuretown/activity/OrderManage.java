package com.vvage.futuretown.activity;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.vvage.futuretown.model.Addorder_save;
import com.vvage.futuretown.model.Address;
import com.vvage.futuretown.model.Cart;
import com.vvage.futuretown.model.MyOrder;
import com.vvage.futuretown.model.MyOrder.PersonalOrder;
import com.vvage.futuretown.model.MyOrder.PersonalOrderProduct;
import com.vvage.futuretown.model.User;
import com.vvage.futuretown.util.AsyncViewTask;
import com.vvage.futuretown.util.AsyncViewTask.PostExecute;
import com.vvage.futuretown.util.DoAsyncTask;
import com.vvage.futuretown.util.DoAsyncTask.DoTask;
import com.vvage.futuretown.util.DoAsyncTask.PostResult;
import com.vvage.futuretown.util.StringUtil;

/*
 * 我的订单
 * 
 */
public class OrderManage extends BaseView{
	//0 is order  1 is reply
	private ButtonTextView order;
	private ButtonTextView reply;
	
	private ExpandableListView orderList;
	private ExpandableListView replyList;
	
	private int press=0;
	

	
	@Override
	protected View creatView(Context context) {
		// TODO Auto-generated method stub
		setTitleParams(new TitleParams("返回", View.VISIBLE, null, createTitleView(), "刷新", View.VISIBLE));
		View view = LayoutInflater.from(context).inflate(R.layout.userorder, null);
		orderList = (ExpandableListView)view.findViewById(R.id.order_list);
		replyList = (ExpandableListView)view.findViewById(R.id.reply_list);
		refList();
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
		refList();
	}
	
	
	private void refList(){
		new DoAsyncTask(OrderManage.this, "正在加载...", new DoTask() {
			@Override
			public Object task() {
				// TODO Auto-generated method stub
				return Apicmd.getInstance().getMyOrder(getApplicationContext(), "");
			}
		}, new DoAsyncTask.PostResult() {
			
			@Override
			public void result(Object result) {
				// TODO Auto-generated method stub
				if(result!=null){
					MyOrder mo = (MyOrder)result;
					if(mo.isSucceed()){
						orderList.setAdapter(new orderAdapter(mo.orderList));
						int groupCount = orderList.getCount();
						for (int i=0; i<groupCount; i++) {
							orderList.expandGroup(i);
							};
						
//						replyList.setAdapter(new orderAdapter(mo.orderList));
//						int reCount = replyList.getCount();
//						for (int i=0; i<reCount; i++) {
//							replyList.expandGroup(i);
//							};
					}else{
						
					}
				}
			}
		}, true, true);
	}
	
	private class orderAdapter extends BaseExpandableListAdapter{
		
		private List<PersonalOrder> data;
		
		public orderAdapter(List<PersonalOrder> data){
			this.data = data;
		}
		
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			if(data!=null&&data.get(groupPosition)!=null&&data.get(groupPosition).pop!=null){
				return data.get(groupPosition).pop.get(childPosition);
			}
			return null;
			
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
			if(data!=null&&data.get(groupPosition)!=null&&data.get(groupPosition).pop!=null){
				return data.get(groupPosition).pop.size();
			}
			return 0;
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			if(data!=null){
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
			LinearLayout layout = new LinearLayout(getApplicationContext());
			layout.setPadding(10, 10, 10, 10);
			layout.setLayoutParams(new AbsListView.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
			LinearLayout.LayoutParams imLPS = new LinearLayout.LayoutParams(
					Global.WIDTH_SCREEN/4, LayoutParams.WRAP_CONTENT);
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
			TextView sn = new TextView(getApplicationContext());
			sn.setTextColor(Color.BLACK);
			TextView address = new TextView(getApplicationContext());
			address.setTextColor(Color.BLACK);
			right.addView(name);
			right.addView(sn);
			right.addView(address);
			
			LinearLayout statusLayout = new LinearLayout(getApplicationContext());
			statusLayout.setPadding(0, 10, 10, 10);
			statusLayout.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
			TextView total = new TextView(getApplicationContext());
			total.setTextColor(Color.BLACK);
			
			LinearLayout.LayoutParams callLPS = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			callLPS.leftMargin = 20;
			TextView call = new TextView(getApplicationContext());
			call.setPadding(10, 10, 10, 10);
			call.setText("拨打电话");
			call.setTextColor(Color.WHITE);
			call.setBackgroundDrawable(Global.newSelectorFromRes(
					getApplicationContext(), R.drawable.bg_btn_normal, 
					R.drawable.bg_btn_pressed, R.drawable.bg_btn_pressed, 
					R.drawable.bg_btn_normal));
			
			statusLayout.addView(total);
			statusLayout.addView(call, callLPS);
			
			right.addView(statusLayout);
			
			layout.addView(im, imLPS);
			layout.addView(right);
			
			layout.setTag(new GroupViewHolder(im, name, sn, address, total, call));
			
			return layout;
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
			del.setText("从订单中删除");
			del.setTextColor(Color.WHITE);
			del.setBackgroundResource(R.drawable.bg_blue);
			
			TextView status = new TextView(getApplicationContext());
			status.setVisibility(press==1?View.VISIBLE:View.GONE);
			
			statusLayout.addView(del);
			statusLayout.addView(status);
			
			right.addView(statusLayout);
			
			layout.addView(im, imLPS);
			layout.addView(right);
			
			View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.orderexpanditem, null);
			TextView shippaddress = (TextView)view.findViewById(R.id.shippingaddress);
			TextView shippname = (TextView)view.findViewById(R.id.name);
			TextView phone = (TextView)view.findViewById(R.id.phone);
			TextView address = (TextView)view.findViewById(R.id.address);
			EditText note = (EditText)view.findViewById(R.id.note);
			TextView ordering = (TextView)view.findViewById(R.id.ordering);
			
			main.addView(layout);
			main.addView(view);
			
			main.setTag(new ChildViewHolder(im, name, price, del, status, shippaddress, shippname, phone, address, note, ordering));
			
			return main;
		}
		
		private void bindGroupView(int groupPosition, View convertView){
			final PersonalOrder tc = data.get(groupPosition);
			
			GroupViewHolder gh = (GroupViewHolder)convertView.getTag();
			gh.im.setTag(tc.sellerurl);
			new AsyncViewTask(Global.WIDTH_SCREEN/3, new PostExecute() {
				
				@Override
				public void result(boolean result) {
					// TODO Auto-generated method stub
					
				}
			}).execute(gh.im);
			
			gh.name.setText("店名："+tc.sellername);
			gh.sn.setText("编号："+tc.sellersn);
			gh.address.setText("地址："+tc.selleraddress);
			gh.total.setText("订单总额："+tc.totalprice);
			gh.call.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(StringUtil.isMobileNumber(tc.sellerphone)){
						Global.call(OrderManage.this, tc.sellerphone);
					}else{
						Toast.makeText(getApplicationContext(), 
								"手机号码错误", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
		
		
		private void bindChindView(int groupPosition, int childPosition, View convertView){
			final PersonalOrder po = data.get(groupPosition);
			final PersonalOrderProduct tc = po.pop.get(childPosition);
			
			final ChildViewHolder ch = (ChildViewHolder)convertView.getTag();
			ch.im.setTag("");
			new AsyncViewTask(Global.WIDTH_SCREEN/3, new PostExecute() {
				
				@Override
				public void result(boolean result) {
					// TODO Auto-generated method stub
					
				}
			}).execute(ch.im);
			
			ch.name.setText("品名："+tc.name);
			ch.price.setText("金额：￥"+tc.price);
			ch.del.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "订单删除接口", Toast.LENGTH_SHORT).show();
				}
			});
			
			ch.shippname.setText("收货人："+po.shippingname);
			ch.phone.setText("联系方式："+po.shippingphone);
			ch.address.setText("地    址："+po.shippingaddress);
			
			if(press==1){
//				ch.status.setText(tc.status);
			}
			
			ch.shippname.setText("收货人："+po.shippingname);
			ch.phone.setText("联系方式："+po.shippingphone);
			ch.address.setText("地    址："+po.shippingaddress);
			
			ch.shippaddress.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					new DoAsyncTask(OrderManage.this, "正在加载全部收货地址", new DoTask() {
						
						@Override
						public Object task() {
							// TODO Auto-generated method stub
							return Apicmd.getInstance().getAddress(OrderManage.this, User.uid);
						}
					}, new PostResult() {
						
						@Override
						public void result(Object result) {
							// TODO Auto-generated method stub
							if(result!=null){
								Address address = (Address)result;
								if(address.isSucceed()){
									if(address.addressList!=null&&address.addressList.size()>0){
										final String stradd[] = address.addressDetail();
										new AlertDialog.Builder(OrderManage.this)
						                .setTitle("请选择地址")//标题
						                .setSingleChoiceItems(stradd,-1,new DialogInterface.OnClickListener() {
						                    public void onClick(DialogInterface dialog, int which) {//响应事件
						                        // do something
						                        //关闭对话框
//						                        dialog.dismiss();
						                    		int namepos = stradd[which].indexOf("-");
						                    		String name = (String) stradd[which].subSequence(0, namepos);
						                    		int phonepos = stradd[which].indexOf("-", namepos+1);
						                    		String phone = (String) stradd[which].subSequence(namepos+1, phonepos);
						                    		int addpos = stradd[which].indexOf("-", phonepos+1);
						                    		String add = (String) stradd[which].subSequence(phonepos+1, stradd[which].length());
						                    		ch.shippname.setText("收货人："+name);
						        					ch.phone.setText("联系方式："+phone);
						        					ch.address.setText("地    址："+add);
						                    }
						                })
						                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
						                    
						                    @Override
						                    public void onClick(DialogInterface dialog, int which) {
						                        //do something
//						                    	if(mShootMerchandies.getVisibility()==View.VISIBLE&&mPersonalAddress.getVisibility()==View.VISIBLE){
//						                    		mPersonalAddressGoods.setText(stradd[0]);
//						                    	}
						                    	 dialog.dismiss();
						                    }
						                })//添加button并响应点击事件
						                .show();
										
									}else{
										Toast.makeText(OrderManage.this, "目前没有收货地址", Toast.LENGTH_SHORT).show();
									}
								}else{
									Toast.makeText(OrderManage.this, "加载失败", Toast.LENGTH_SHORT).show();
								}
								
							}else{
								Toast.makeText(OrderManage.this, "加载失败", Toast.LENGTH_SHORT).show();
							}
						}
					}, true, true);
					
				}
			});
			
			ch.note.setText(po.extmsg);
			ch.ordering.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new DoAsyncTask(OrderManage.this, "正在提交订单...", new DoTask() {
						
						@Override
						public Object task() {
							// TODO Auto-generated method stub
							Cart cart = new Cart();
							cart.price = tc.price;
							cart.extmsg = po.extmsg;
							return Apicmd.getInstance().getAddorder_save(getApplicationContext(), ch.shippname.getText().toString(), cart);
						}
					}, new PostResult() {
						
						@Override
						public void result(Object result) {
							// TODO Auto-generated method stub
							if(result!=null){
								Addorder_save aos = (Addorder_save)result;
								if(aos.isSucceed()){
									Toast.makeText(getApplicationContext(), aos.msg, Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(getApplicationContext(), "下单失败,请稍后重试", Toast.LENGTH_SHORT).show();
								}
							}else{
								Toast.makeText(getApplicationContext(), "下单失败,请稍后重试", Toast.LENGTH_SHORT).show();
							}
						}
					}, true, true);
				}
			});
			
		}
		
		private class ChildViewHolder{
			public ImageView im;
			public TextView name;
			public TextView price;
			public TextView del;
			public TextView status;
			
			public TextView shippaddress;
			public TextView shippname;
			public TextView phone;
			public TextView address;
			public EditText note;
			public TextView ordering; 
			
			public ChildViewHolder(ImageView im, TextView name,
					TextView price, TextView del, TextView status,
					TextView shippaddress, TextView shippname,
					TextView phone, TextView address, EditText note,
					TextView ordering){
				this.im = im;
				this.name = name;
				this.price = price;
				this.del = del;
				this.status = status;
				
				this.shippaddress = shippaddress;
				this.shippname = shippname;
				this.phone = phone;
				this.address = address;
				this.note = note;
				this.ordering = ordering;
			}
		}
		
		private class GroupViewHolder{
			public ImageView im;
			public TextView name;
			public TextView sn;
			public TextView address;
			public TextView total;
			public TextView call;
			
			public GroupViewHolder(ImageView im, TextView name,
					TextView sn, TextView address, TextView total,
					TextView call){
				this.im = im;
				this.name = name;
				this.sn = sn;
				this.address = address;
				this.total = total;
				this.call = call;
			}
		}
		
	}
	
	private View createTitleView(){
		LinearLayout layout = new LinearLayout(this);
		
		order = new ButtonTextView(this);
		order.setTag(order);
		order.setBackgroundResource(R.drawable.btn_title_l_press);
		order.setText("订单管理");
		
		order.setOnClickListener(mOnClickListener);
		
		reply = new ButtonTextView(this);
		reply.setTag(reply);
		reply.setBackgroundResource(R.drawable.btn_title_r);
		reply.setText("商家回复");
		reply.setOnClickListener(mOnClickListener);
		
		layout.addView(order);
		layout.addView(reply);
		return layout;
	}
	
	private void switchButton(int type){
		press = type;
		if(type==0){
			order.setBackgroundResource(R.drawable.btn_title_l_press);
			reply.setBackgroundResource(R.drawable.btn_title_r);
			orderList.setVisibility(View.VISIBLE);
			replyList.setVisibility(View.GONE);
		}else if(type==1){
			order.setBackgroundResource(R.drawable.btn_title_l);
			reply.setBackgroundResource(R.drawable.btn_title_r_press);
			orderList.setVisibility(View.GONE);
			replyList.setVisibility(View.VISIBLE);
		}
	}
	
	private OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getTag() == order){
				switchButton(0);
			}else if(v.getTag() == reply){
				switchButton(1);
				
			}
		}
	};


	@Override
	protected void getViewData() {
		// TODO Auto-generated method stub
		DoAsyncTask t = new DoAsyncTask(OrderManage.this, "正在加载订单", new DoTask() {
			
			@Override
			public Object task() {
				// TODO Auto-generated method stub
//				type	1 历史订单，不传或者其他为最新订单
				return Apicmd.getInstance().getMyOrder(getApplicationContext(), "");
			}
		}, new DoAsyncTask.PostResult() {
			
			@Override
			public void result(Object result) {
				// TODO Auto-generated method stub
				if(result!=null){
					MyOrder mo = (MyOrder)result;
					if(mo.isSucceed()){
						
					}
				}
			}
		}, true, true);
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
