package com.vvage.futuretown.activity;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vvage.futuretown.Global;
import com.vvage.futuretown.R;
import com.vvage.futuretown.base.BaseView;
import com.vvage.futuretown.cmd.Apicmd;
import com.vvage.futuretown.model.MyEnquirePrice;
import com.vvage.futuretown.model.MyEnquirePrice.EnquireSeller;
import com.vvage.futuretown.model.MyEnquirePrice.SellerProduct;
import com.vvage.futuretown.util.AsyncViewTask;
import com.vvage.futuretown.util.AsyncViewTask.PostExecute;
import com.vvage.futuretown.util.DoAsyncTask;
import com.vvage.futuretown.util.DoAsyncTask.DoTask;
import com.vvage.futuretown.util.DoAsyncTask.PostResult;
import com.vvage.futuretown.util.StringUtil;

/*
 * 用户中心 商家和个人共用
 * 我的询价页面
 * 6.个人-查询我的询价接口
 */


public class MyEquire extends BaseView{
	private ButtonTextView sendedEnquire;
	private ButtonTextView merchantReply;
	
	private ExpandableListView sendedExpList;
	private ExpandableListView replyExpList;
	
	private int press=0;
	
	@Override
	protected View creatView(Context context) {
		// TODO Auto-generated method stub
		setTitleParams(new TitleParams("返回", View.VISIBLE, null, createTitleView(), "刷新", View.VISIBLE));
		View view = LayoutInflater.from(context).inflate(R.layout.enquiry, null);
		sendedExpList = (ExpandableListView)view.findViewById(R.id.sendedenquire_list);
		replyExpList = (ExpandableListView)view.findViewById(R.id.merchantreply_list);
		reflist();
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
		reflist();
	}

	private void reflist(){
		new DoAsyncTask(MyEquire.this, "正在加载...", new DoTask() {
			
			@Override
			public Object task() {
				// TODO Auto-generated method stub
				int type = press+1;
				return Apicmd.getInstance().
						getMyEnquirePrice(getApplicationContext(), type+"");
			}
		}, new PostResult() {
			
			@Override
			public void result(Object result) {
				// TODO Auto-generated method stub
				if(result!=null){
					MyEnquirePrice me = (MyEnquirePrice)result;
					if(me!=null&&me.isSucceed()){
						if(press==0){
							sendedExpList.setAdapter(new EnquireExpandAdapter(me.list));
							int groupCount = sendedExpList.getCount();
							for (int i=0; i<groupCount; i++) {
								sendedExpList.expandGroup(i);
								};
						}else if(press==1){
							replyExpList.setAdapter(new EnquireExpandAdapter(me.list));
							int groupCount = replyExpList.getCount();
							for (int i=0; i<groupCount; i++) {
								replyExpList.expandGroup(i);
								};
						}
					}
				}
			}
		}, true, true);
	}
	private View createTitleView(){
		LinearLayout layout = new LinearLayout(this);
		
		sendedEnquire = new ButtonTextView(this);
		sendedEnquire.setTag(sendedEnquire);
		sendedEnquire.setBackgroundResource(R.drawable.btn_title_l_press);
		sendedEnquire.setText("已发询价");
		sendedEnquire.setOnClickListener(mOnClickListener);
		
		merchantReply = new ButtonTextView(this);
		merchantReply.setTag(merchantReply);
		merchantReply.setBackgroundResource(R.drawable.btn_title_r);
		merchantReply.setText("商家回复");
		merchantReply.setOnClickListener(mOnClickListener);
		
		layout.addView(sendedEnquire);
		layout.addView(merchantReply);
		
		return layout;
	}
	
	private OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getTag()==sendedEnquire){
				switchButton(0);
			}else if(v.getTag()==merchantReply){
				switchButton(1);
			}
		}
	};
	
	private void switchButton(int type){
		press = type;
		if(type==0){
			sendedEnquire.setBackgroundResource(R.drawable.btn_title_l_press);
			merchantReply.setBackgroundResource(R.drawable.btn_title_r);
			sendedExpList.setVisibility(View.VISIBLE);
			replyExpList.setVisibility(View.GONE);
		}else if(type==1){
			sendedEnquire.setBackgroundResource(R.drawable.btn_title_l);
			merchantReply.setBackgroundResource(R.drawable.btn_title_r_press);
			sendedExpList.setVisibility(View.GONE);
			replyExpList.setVisibility(View.VISIBLE);
		}
	}
	
	private void expandAllGroup(ExpandableListView el){
		// 遍历所有group,将所有项设置成默认展开
		if(el!=null){
			int groupCount = el.getCount();
			for (int i = 0; i < groupCount; i++) {
				el.expandGroup(i);
			}
		}
	}
	
	private class EnquireExpandAdapter extends BaseExpandableListAdapter{
		
		private List<EnquireSeller> data;
		public EnquireExpandAdapter(final List<EnquireSeller> data){
			this.data = data;
		}

		
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			if(data!=null&&data.get(groupPosition)!=null&&data.get(groupPosition).productList!=null){
				return data.get(groupPosition).productList.get(childPosition);
			}
			return null;
		}

		@Override
		public long getChildId(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return arg1;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null){
				convertView = getExpChildView();
			}
			bindChildView(groupPosition, childPosition, convertView);
			return convertView;
		}

		@Override
		public int getChildrenCount(int arg0) {
			// TODO Auto-generated method stub
			if(data!=null&&data.get(arg0)!=null&&data.get(arg0).productList!=null){
				return data.get(arg0).productList.size();
			}
			return 0;
		}

		@Override
		public Object getGroup(int arg0) {
			// TODO Auto-generated method stub
			if(data!=null){
				return data.get(arg0);
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
		public long getGroupId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, 
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null){
				convertView = getExpGroupView();
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
		
		private void bindGroupView(int groupPosition, View convertView){
			final GroupViewHolder gv = (GroupViewHolder)convertView.getTag();
			final EnquireSeller es = data.get(groupPosition);
			
			gv.im.setTag(es.sellerurl);
			new AsyncViewTask(Global.WIDTH_SCREEN/4, new PostExecute() {
				
				@Override
				public void result(boolean result) {
					// TODO Auto-generated method stub
					
				}
			}).execute(gv.im);
			
			gv.name.setText("店名："+es.sellername);
			gv.sn.setText("编号："+es.sellersn);
			gv.address.setText("地址："+es.selleraddress);
			
			if(press==0){
				gv.location.setVisibility(View.VISIBLE);
			}else if(press==1){
				gv.location.setVisibility(View.GONE);
				
			}
		}
		private void bindChildView(int groupPosition, int childPosition, View convertView){
			final ChildViewHolder cv = (ChildViewHolder)convertView.getTag();
			final SellerProduct sp = data.get(groupPosition).productList.get(childPosition);
			cv.name.setText("品名："+sp.productname);
			cv.price.setText("标价：￥"+sp.productowprice);
			cv.del.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "调用删除询价接口", Toast.LENGTH_SHORT).show();
				}
			});
			final String phone = data.get(groupPosition).sellerphone;
			cv.dial.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(StringUtil.isMobileNumber(phone)){
						Global.call(MyEquire.this, phone);
					}else{
						Toast.makeText(getApplicationContext(), "手机号码无效", Toast.LENGTH_SHORT).show();
					}
				}
			});
			
			if(press==0){
				cv.below.setVisibility(View.GONE);
			}else if(press==1)
				cv.below.setVisibility(View.VISIBLE);
				cv.yikoujia.setText(sp.yikoujia);
				cv.time.setText(sp.outtime);
				cv.note.setText(sp.extmsg);
		}
		
	}
	//共用商家 layout
	private View getExpGroupView(){
		
		View view = LayoutInflater.from(MyEquire.this).inflate(R.layout.enquire_list_group, null);
		ImageView im = (ImageView)view.findViewById(R.id.imageView1);
		TextView name = (TextView)view.findViewById(R.id.textView1);
		TextView sn = (TextView)view.findViewById(R.id.textView2);
		TextView address = (TextView)view.findViewById(R.id.textView3);
		TextView location = (TextView)view.findViewById(R.id.textView4);
		
//		int width = Global.WIDTH_SCREEN;
//		LinearLayout layout = new LinearLayout(getApplicationContext());
//		layout.setLayoutParams(new AbsListView.LayoutParams(
//				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
//		
//		ImageView im = new ImageView(getApplicationContext());
//		im.setLayoutParams(new LinearLayout.LayoutParams(width/3, LayoutParams.WRAP_CONTENT));
//		im.setScaleType(ScaleType.FIT_XY);
//		
//		LinearLayout right = new LinearLayout(getApplicationContext());
//		right.setLayoutParams(new LinearLayout.LayoutParams(
//				width-width/3, LayoutParams.WRAP_CONTENT));
//		right.setPadding(10, 10, 10, 10);
//		right.setOrientation(LinearLayout.VERTICAL);
//		
//		TextView name = new TextView(getApplicationContext());
//		name.setGravity(Gravity.CENTER);
//		name.setTextColor(color.black);
//		
//		TextView sn = new TextView(getApplicationContext());
//		sn.setGravity(Gravity.CENTER);
//		sn.setTextColor(color.black);
//		
//		TextView address = new TextView(getApplicationContext());
//		address.setGravity(Gravity.CENTER);
//		address.setTextColor(color.black);
//		
//		TextView location = new TextView(getApplicationContext());
//		location.setText("商家位置");
//		location.setGravity(Gravity.CENTER);
////		location.setBackgroundResource(R.drawable.btn_quit);
//		location.setTextColor(color.white);
//		
//		right.addView(name);
//		right.addView(sn);
//		right.addView(address);
//		right.addView(location);
//		
//		layout.addView(im);
//		layout.addView(right);
//		
//		layout.setTag(new GroupViewHolder(im, name, sn, address, location));
		view.setTag(new GroupViewHolder(im, name, sn, address, location));
		
		return view;
	}
	
	private class GroupViewHolder{
		public ImageView im;
		public TextView name;
		public TextView sn;
		public TextView address;
		public TextView location;
		
		public GroupViewHolder(ImageView im, TextView name, TextView sn, TextView address, TextView location){
			this.im = im;
			this.name = name;
			this.sn = sn;
			this.address = address;
			this.location = location;
		}
	}
	
	private View getExpChildView(){
		int width = Global.WIDTH_SCREEN;
		LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setLayoutParams(new AbsListView.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		layout.setOrientation(LinearLayout.VERTICAL);
		
		
		LinearLayout top = new LinearLayout(getApplicationContext());
		
		ImageView im = new ImageView(getApplicationContext());
		im.setLayoutParams(new AbsListView.LayoutParams(width*2/5, LayoutParams.WRAP_CONTENT));
		im.setScaleType(ScaleType.FIT_XY);
		
		LinearLayout right = new LinearLayout(getApplicationContext());
		right.setLayoutParams(new AbsListView.LayoutParams(
				width-width*2/5, LayoutParams.WRAP_CONTENT));
		right.setPadding(10, 10, 10, 10);
		right.setOrientation(LinearLayout.VERTICAL);
		
		TextView name = new TextView(getApplicationContext());
		name.setGravity(Gravity.CENTER);
		
		TextView price = new TextView(getApplicationContext());
		price.setGravity(Gravity.CENTER);
		
		LinearLayout rightbelow = new LinearLayout(getApplicationContext());
		rightbelow.setLayoutParams(new AbsListView.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		TextView del = new TextView(getApplicationContext());
		del.setPadding(3, 3, 3, 3);
		del.setGravity(Gravity.CENTER);
		del.setBackgroundResource(R.drawable.bg_blue);
		del.setText("删除询价");
		
		TextView dial = new TextView(getApplicationContext());
		dial.setGravity(Gravity.CENTER);
		dial.setPadding(3, 3, 3, 3);
		dial.setBackgroundResource(R.drawable.bg_orange);
		dial.setText("拨打电话");
		rightbelow.addView(del);
		rightbelow.addView(dial);
		
		
		
		right.addView(name);
		right.addView(price);
		right.addView(rightbelow);
		
		top.addView(im);
		top.addView(right);
		
		LinearLayout below = new LinearLayout(getApplicationContext());
		AbsListView.LayoutParams belowLPS = new AbsListView.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		below.setLayoutParams(belowLPS);
		below.setBackgroundResource(R.drawable.bg_edittext);
		below.setPadding(10, 10, 10, 10);
		below.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout belowTop = new LinearLayout(getApplicationContext());
		TextView yikoujia = new TextView(getApplicationContext());
		yikoujia.setGravity(Gravity.CENTER);
		
		TextView time = new TextView(getApplicationContext());
		time.setGravity(Gravity.CENTER);
		
		belowTop.addView(yikoujia);
		belowTop.addView(time);
		
		TextView note = new TextView(getApplicationContext());
		note.setGravity(Gravity.CENTER);
		
		below.addView(belowTop);
		below.addView(note);
		
		
		layout.addView(top);
		layout.addView(below);
		
		layout.setTag(new ChildViewHolder(im, name, price, del, 
				dial, below, yikoujia, time, note));
		
		return layout;
	}
	
	private class ChildViewHolder{
		public ImageView im;
		public TextView name;
		public TextView price;
		public TextView del;
		public TextView dial;
		
		public LinearLayout below;
		public TextView yikoujia;
		public TextView time;
		public TextView note;
		public ChildViewHolder(ImageView im, TextView name, TextView price, 
					TextView del, TextView dial, LinearLayout below, 
					TextView yikoujia, TextView time, TextView note){
			this.im = im;
			this.name = name;
			this.price = price;
			this.del = del;
			this.dial = dial;
			this.below = below;
			this.yikoujia = yikoujia;
			this.time = time;
			this.note = note;
		}
	}
	
//	AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {  
//	      
//	    @Override  
//	    public void onScrollStateChanged(AbsListView view, int scrollState) {  
//	        switch (scrollState) {  
//	            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:  
//	                DebugUtil.debug("SCROLL_STATE_FLING");  
//	                syncImageLoader.lock();  
//	                break;  
//	            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:  
//	                DebugUtil.debug("SCROLL_STATE_IDLE");  
//	                loadImage();  
//	                //loadImage();  
//	                break;  
//	            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:  
//	                syncImageLoader.lock();  
//	                break;  
//	  
//	            default:  
//	                break;  
//	        }  
//	          
//	    }  
//	      
//	    @Override  
//	    public void onScroll(AbsListView view, int firstVisibleItem,  
//	            int visibleItemCount, int totalItemCount) {  
//	        // TODO Auto-generated method stub  
//	          
//	    }  
//	};  

}
