package com.vvage.futuretown.fragment;

import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vvage.futuretown.BaseFragment;
import com.vvage.futuretown.Global;
import com.vvage.futuretown.HomeActivity;
import com.vvage.futuretown.R;
import com.vvage.futuretown.activity.LoginListener;
import com.vvage.futuretown.activity.UserEnquire;
import com.vvage.futuretown.cmd.Apicmd;
import com.vvage.futuretown.model.AddOrUnCollect;
import com.vvage.futuretown.model.CollectList;
import com.vvage.futuretown.model.CollectList.CollectGoods;
import com.vvage.futuretown.model.CollectList.CollectSeller;
import com.vvage.futuretown.model.User;
import com.vvage.futuretown.util.AsyncViewTask;
import com.vvage.futuretown.util.AsyncViewTask.PostExecute;
import com.vvage.futuretown.util.DoAsyncTask;
import com.vvage.futuretown.util.DoAsyncTask.DoTask;
import com.vvage.futuretown.util.StringUtil;

public class FavorFragment extends BaseFragment {
	private Button ref;
	private Button login;
	private Button favorSeller;
	private Button favorGoods;
	private ListView sellerList;
	private ListView goodsList;
	private boolean isFirst=true;
	private int press=1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		HashMap<String, LoginListener> l = new HashMap<String, LoginListener>();
		l.put("favor", mLoginListener);
		HomeActivity.setLoginListener(l);

	}

	private LoginListener mLoginListener = new LoginListener() {

		@Override
		public void Login(Class<?> cls) {
			// TODO Auto-generated method stub
			if (StringUtil.isValidStr(User.uid)) {
				// TO-DO your something
				login.setVisibility(View.GONE);
				if(isFirst){
					DoAsyncTask task = new DoAsyncTask(FavorFragment.this.getActivity(), "正在刷新...", new DoTask() {
						@Override
						public Object task() {
							// TODO Auto-generated method stub
//							type 类型:1商品2商家3会员
							int t = press+1;
							return Apicmd.getInstance().getCollectList(getActivity(), t+"");
						}
					}, new DoAsyncTask.PostResult() {
						
						@Override
						public void result(Object result) {
							// TODO Auto-generated method stub
							if(result!=null){
								CollectList cl = (CollectList)result;
								if(cl.isSucceed()){
									if(press==1){
										FavorSellerAdapter sa = new FavorSellerAdapter(cl.favorseller);
										sellerList.setAdapter(sa);
										isFirst = false;
									}else if(press==0){
										GoodsAdapter ga = new GoodsAdapter(cl.favorgoods);
										goodsList.setAdapter(ga);
										isFirst = false;
									}
								}
							}
						}
					}, true, true);
				}
			}else{
				login.setVisibility(View.VISIBLE);
			}
		}
	};

	private OnClickListener mOnclickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(ref.getId()==v.getId()){
				refList();
			}else if(login.getId()==v.getId()&&login.getVisibility()!=View.GONE){
				HomeActivity.tabHost.setCurrentTabByTag("user");
			}else if(v.getId()==R.id.id_favor_seller){
				press=1;
				favorSeller.setBackgroundResource(R.drawable.btn_title_l_press);
				favorGoods.setBackgroundResource(R.drawable.btn_title_r);
				sellerList.setVisibility(View.VISIBLE);
				goodsList.setVisibility(View.GONE);
			}else if(v.getId()==R.id.id_favor_goods){
				press=0;
				favorSeller.setBackgroundResource(R.drawable.btn_title_l);
				favorGoods.setBackgroundResource(R.drawable.btn_title_r_press);
				sellerList.setVisibility(View.GONE);
				goodsList.setVisibility(View.VISIBLE);
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = (View)inflater.inflate(R.layout.fragment_favor, null);
		ref =(Button)v.findViewById(R.id.id_ref_button);
		ref.setOnClickListener(mOnclickListener);
		login =(Button)v.findViewById(R.id.id_login_button);
		login.setOnClickListener(mOnclickListener);
		favorSeller =(Button)v.findViewById(R.id.id_favor_seller);
		favorSeller.setOnClickListener(mOnclickListener);
		favorGoods =(Button)v.findViewById(R.id.id_favor_goods);
		favorGoods.setOnClickListener(mOnclickListener);
		
		sellerList = (ListView)v.findViewById(R.id.id_favor_seller_list);
		goodsList = (ListView)v.findViewById(R.id.id_favor_goods_list);
		
		return v;
	}
	
	private class FavorSellerAdapter extends BaseAdapter {

		private List<CollectSeller> sellers;
		
		public FavorSellerAdapter(List<CollectSeller> sellers){
			this.sellers = sellers;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			int count = 0;
			if(sellers!=null){
				count =  sellers.size();
			}
			return count;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			if(sellers!=null){
				return sellers.get(arg0);
			}
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			if(arg1 == null) {
				arg1 = newItemView();
			} 
			bindView(arg0, arg1);
			return arg1;
		}
		
		private View newItemView(){
			View item = LayoutInflater.from(getActivity())
			.inflate(R.layout.list_favor_item, null);
			
			ImageView i = (ImageView) item
					.findViewById(R.id.id_favor_image);
			
			TextView name = (TextView) item
					.findViewById(R.id.id_favor_name_text);
			TextView sn = (TextView) item
					.findViewById(R.id.id_favor_sn_price_text);
			TextView detail = (TextView) item
					.findViewById(R.id.id_favor_detail_text);
			TextView call = (TextView) item
					.findViewById(R.id.id_favor_dial);
			TextView wishlist = (TextView) item
					.findViewById(R.id.id_favor_wishlist);
			
			item.setTag(new ViewHolder(i, name, sn, detail, call, wishlist));
			return item;
		}
		
		private String getType(String str){
			String type="";
			if(str.equals("show")){
				type = "展示";
			}else if(str.equals("ask")){
				type = "询价";
			}else if(str.equals("home")){
				type = "送货上门";
			}
			
			return type;
		}
		
		private void bindView(int position, View converView){
			ViewHolder tag = (ViewHolder)converView.getTag();
//			tag.image
			if(sellers!=null){
				final CollectSeller cs = sellers.get(position);
				tag.image.setTag(cs.url);
				new AsyncViewTask(Global.WIDTH_SCREEN/3, new PostExecute() {
					
					@Override
					public void result(boolean result) {
						// TODO Auto-generated method stub
						
					}
				}).execute(tag.image);
				tag.name.setText("品  名:"+cs.sellername);
				tag.serialnumber.setText("编号："+cs.psid);
				tag.detail.setText("详情：");
				
				tag.phone.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
//						Global.call(getActivity(), cs.sellerphone);
					}
				});
				
				tag.wishlist.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						UnCollect(cs.id, "2");
					}
				});
			}
			
		}
		
		private class ViewHolder{
			public ImageView image;
			public TextView name;
			public TextView serialnumber;
			public TextView detail;
			public TextView phone;
			public TextView wishlist;
			
			public ViewHolder(ImageView image, TextView name,
					TextView serialnumber, TextView detail, TextView phone, TextView wishlist){
				this.image = image;
				this.name = name;
				this.serialnumber = serialnumber;
				this.detail = detail;
				this.phone = phone;
				this.wishlist = wishlist;
			}
		}
	}
	
	
	
	private class GoodsAdapter extends BaseAdapter {

		private List<CollectGoods> goods;
		
		public GoodsAdapter(List<CollectGoods> list){
			goods = list;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			int count = 0;
			if(goods!=null){
				count =  goods.size();
			}
			return count;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			if(goods!=null){
				return goods.get(arg0);
			}
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			if(arg1 == null) {
				arg1 = newItemView();
			} 
			bindView(arg0, arg1);
			return arg1;
		}
		
		private View newItemView(){
			View item = LayoutInflater.from(getActivity())
				.inflate(R.layout.list_favor_item, null);
				ImageView i = (ImageView) item
						.findViewById(R.id.id_favor_image);
				TextView name = (TextView) item
						.findViewById(R.id.id_favor_name_text);
				TextView price = (TextView) item
						.findViewById(R.id.id_favor_sn_price_text);
				TextView detail = (TextView) item
						.findViewById(R.id.id_favor_detail_text);
				TextView type = (TextView) item
						.findViewById(R.id.id_favor_dial);
				TextView wishlist = (TextView) item
						.findViewById(R.id.id_favor_wishlist);
			
			item.setTag(new ViewHolder(i, name, price, detail, type, wishlist));
			return item;
		}
		
		private String getType(String str){
			String type="";
			if(str.equals("show")){
				type = "展示";
			}else if(str.equals("ask")){
				type = "询价";
			}else if(str.equals("home")){
				type = "送货上门";
			}else if(str.equals("order")){
				type = "订单";
			}
			return type;
		}
		
		private void bindView(int position, View converView){
			ViewHolder tag = (ViewHolder)converView.getTag();
//			tag.image
			if(goods!=null){
				final CollectGoods g = goods.get(position);
				tag.image.setTag(g.url);
				new AsyncViewTask(Global.WIDTH_SCREEN/3, new PostExecute() {
					
					@Override
					public void result(boolean result) {
						// TODO Auto-generated method stub
						
					}
				}).execute(tag.image);
				tag.name.setText("品  名:"+g.name);
				tag.price.setText("价  格:"+g.nowprice);
				tag.detail.setText("详  情:");
				tag.type.setText(getType(g.type));
				tag.type.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if(g.type.equals("show")){
							Toast.makeText(getActivity(), "展示商品", Toast.LENGTH_SHORT).show();
						}else if(g.type.equals("ask")){
							Toast.makeText(getActivity(), "调用询价接口", Toast.LENGTH_SHORT).show();
						}
					}
				});
				
				tag.wishlist.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						UnCollect(g.id, "1");
					}
				});
			}
			
		}
		
		private class ViewHolder{
			public ImageView image;
			public TextView name;
			public TextView price;
			public TextView detail;
			public TextView type;
			public TextView wishlist;
			
			public ViewHolder(ImageView image, TextView name,
					TextView price, TextView detail, TextView type, TextView wishlist){
				this.image = image;
				this.name = name;
				this.price = price;
				this.detail = detail;
				this.type = type;
				this.wishlist = wishlist;
			}
		}
		
	}
	
	
	private void refList(){
		if(!StringUtil.isValidStr(User.uid)){
			Toast.makeText(getActivity(), "亲~还没有登录呢...", Toast.LENGTH_SHORT).show();
			return;
		}
		DoAsyncTask task = new DoAsyncTask(FavorFragment.this.getActivity(), "正在刷新...", new DoTask() {
			@Override
			public Object task() {
				// TODO Auto-generated method stub
//				type 类型:1商品2商家3会员
				int t = press+1;
				return Apicmd.getInstance().getCollectList(getActivity(), t+"");
			}
		}, new DoAsyncTask.PostResult() {
			
			@Override
			public void result(Object result) {
				// TODO Auto-generated method stub
				if(result!=null){
					CollectList cl = (CollectList)result;
					if(cl.isSucceed()){
						if(press==1){
							FavorSellerAdapter sa = new FavorSellerAdapter(cl.favorseller);
							sellerList.setAdapter(sa);
						}else if(press==0){
							GoodsAdapter ga = new GoodsAdapter(cl.favorgoods);
							goodsList.setAdapter(ga);
						}
					}
				}
			}
		}, true, true);
	}
	
	private void UnCollect(final String id, final String type){
		new DoAsyncTask(getActivity(), "正在提交...", new DoTask() {
			
			@Override
			public Object task() {
				// TODO Auto-generated method stub
				return Apicmd.getInstance().getAddOrUnCollect(getActivity(), id, type, AddOrUnCollect.UNCOLLECT);
			}
		},new DoAsyncTask.PostResult() {
			
			@Override
			public void result(Object result) {
				// TODO Auto-generated method stub
				if(result!=null){
					AddOrUnCollect auc = (AddOrUnCollect)result;
					if(auc.isSucceed()){
						refList();
					}else{
						Toast.makeText(getActivity(), "请稍后重试", Toast.LENGTH_SHORT).show();
					}
				}
			}
		}, true, true);
	}
}
