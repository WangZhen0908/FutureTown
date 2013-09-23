package com.vvage.futuretown.activity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vvage.futuretown.Global;
import com.vvage.futuretown.R;
import com.vvage.futuretown.base.BaseView;
import com.vvage.futuretown.cmd.Apicmd;
import com.vvage.futuretown.fragment.HomeFragment;
import com.vvage.futuretown.model.AddOrUnCollect;
import com.vvage.futuretown.model.Addorder_save;
import com.vvage.futuretown.model.Cart;
import com.vvage.futuretown.model.MerchantAndGoods;
import com.vvage.futuretown.model.MerchantAndGoods.Goods;
import com.vvage.futuretown.model.MerchantAndGoods.Seller;
import com.vvage.futuretown.model.SendAskPrice;
import com.vvage.futuretown.model.User;
import com.vvage.futuretown.util.AsyncViewTask;
import com.vvage.futuretown.util.AsyncViewTask.PostExecute;
import com.vvage.futuretown.util.DoAsyncTask;
import com.vvage.futuretown.util.DoAsyncTask.DoTask;
import com.vvage.futuretown.util.DoAsyncTask.PostResult;
import com.vvage.futuretown.util.StringUtil;

public class Merchant extends BaseView {
	
//	private BaseAdapter adapter;
//	private LinkedList<TestData> data;
	public static final String MERCHANT="merchant_from";
	public static final String MERCHANT_DETAIL="merchant_from_detail";
	private MerchantAndGoods mMerchantandGoods;
	
	private ListView listView;
	private GoodsAdapter mAdapter;
	private RelativeLayout mLayout;
	private String mSid;
	
	private ImageView mMerchantImg;
	private TextView mMerchantIntroduce;
	private TextView mMerchantIdentifier;
	private TextView mMerchantDial;
	private TextView mMerchantWishlist;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		int visible = View.VISIBLE;
		setTitleParams(new TitleParams("返回", visible, "商家", null, 
				StringUtil.isValidStr(User.uid)?"登录":"登录", 
						StringUtil.isValidStr(User.uid)?View.GONE:visible));
		Intent i = getIntent();
		mSid = i.getStringExtra(HomeFragment.HOME_FRAGMENT_MSG);
		super.onCreate(savedInstanceState);
		
	}

	@Override
	protected void onClickLeft() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	protected void onClickRight() {
		// TODO Auto-generated method stub
		switchTab("user", true);
	}

	@Override
	protected View creatView(Context context) {
		// TODO Auto-generated method stub
		LinearLayout l = new LinearLayout(getApplicationContext());
//		l.setLayoutParams(new L)
		ProgressBar pb = new ProgressBar(getApplicationContext());
		
		
		View layout = LayoutInflater.from(context).inflate(R.layout.merchant, null);
//		layout.setVisibility(View.GONE);
		
		mLayout = (RelativeLayout)layout.findViewById(R.id.id_merchant_introdcue_layout);
		mLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!StringUtil.isValidStr(User.uid)){
					Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent i=new Intent(Merchant.this, MerchantDetail.class);
				Seller s = mMerchantandGoods.seller;
				if(s!=null){
					Bundle b = new Bundle();
					b.putString("name", s.sellername);
					b.putString("detail", s.mainservice);
					b.putString("phone", s.sellerphone);
					b.putString("url", s.imgUrl);
					b.putString("id", s.id);
					i.putExtra(MERCHANT, b);
				}
				startActivity(i);
			}
		});
		
		mMerchantImg = (ImageView)layout.findViewById(R.id.id_merchant_image);
		mMerchantIntroduce = (TextView)layout.findViewById(R.id.id_merchant_introduce_text);
		mMerchantIdentifier = (TextView)layout.findViewById(R.id.id_merchant_identifier_text);
		mMerchantDial = (TextView)layout.findViewById(R.id.id_merchant_dial);
		mMerchantWishlist = (TextView)layout.findViewById(R.id.id_merchant_wishlist);

		listView = (ListView)layout.findViewById(R.id.id_merchant_goods_listview);
		listView.setOnItemClickListener(itemClickListener);

//		listView.setonRefreshListener(new OnRefreshListener() {
//			public void onRefresh(final RefreshDirecton directon) {
//				new AsyncTask<Void, Void, Void>() {
//					protected Void doInBackground(Void... params) {
////						try {
////							Thread.sleep(1000);
////						} catch (Exception e) {
////							e.printStackTrace();
////						}
//						if (directon == RefreshDirecton.DOWN) {
//							data.addFirst(new TestData());
//						} else if (directon == RefreshDirecton.UP) {
//							data.add(new TestData());
//						}
//						return null;
//					}
//
//					@Override
//					protected void onPostExecute(Void result) {
//						if (directon == RefreshDirecton.DOWN) {
//							adapter.notifyDataSetChanged();
//							listView.onRefreshComplete();
//						} else if (directon == RefreshDirecton.UP) {
//							adapter.notifyDataSetChanged();
//							listView.onLoadMoreComplete();
//						}
//
//					}
//
//				}.execute(null);
//			}
//		});
		loadData();
		return layout;
	}


	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if(arg3==-1)
				return;
			if(!StringUtil.isValidStr(User.uid)){
				Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
				return;
			}
			int realPosition=(int)arg2;  
			if(mMerchantandGoods!=null && mMerchantandGoods.goods!=null){
				Goods g = mMerchantandGoods.goods.get(realPosition);
				Intent intent=new Intent(Merchant.this, MerchantGoodsDetail.class);
				
				Bundle b = new Bundle();
				b.putString("name", g.name);
				b.putInt("price", g.nowprice);
				b.putString("type", g.type);
				b.putString("phone", g.sellerphone);
				b.putString("detail", g.content);
				b.putString("url", g.url);
				intent.putExtra(MERCHANT_DETAIL, b);
				startActivity(intent);
			}
		}
		
	};

	private void loadData(){
		new DoAsyncTask(Merchant.this, "正在努力加载商家...", new DoAsyncTask.DoTask() {
			@Override
			public Object task() {
				// TODO Auto-generated method stub
				return Apicmd.getInstance().getMerchantAndGoods(getApplicationContext(), "1", StringUtil.isValidStr(mSid)?mSid:"");
			}
		}, new DoAsyncTask.PostResult() {
			
			@Override
			public void result(Object result) {
				// TODO Auto-generated method stub
				if(result!=null){
					mMerchantandGoods = (MerchantAndGoods)result;
					if(mMerchantandGoods.isSucceed()){
						updataMerchant();
						updataListView();
					}
				}
			}
		}, true, true);
	}
	
	private void updataMerchant(){
//		LayoutParams lps = mMerchantImg.getLayoutParams();
//		lps.width=;
//		lps.width=android.widget.LinearLayout.LayoutParams.WRAP_CONTENT;
//		mMerchantImg.setLayoutParams(lps);
		mMerchantImg.setTag(mMerchantandGoods.seller.imgUrl);
		mMerchantImg.setScaleType(ScaleType.CENTER_INSIDE);
//		mMerchantImg.setLayoutParams(new LinearLayout.LayoutParams(Global.WIDTH_SCREEN/3, android.widget.LinearLayout.LayoutParams.wr))
		new AsyncViewTask(Global.WIDTH_SCREEN/3, new PostExecute() {
			
			@Override
			public void result(boolean result) {
				// TODO Auto-generated method stub
				
			}
		}).execute(mMerchantImg);//异步加载图片
		mMerchantIntroduce.setText("经营范围:"+mMerchantandGoods.seller.mainservice);
		mMerchantIdentifier.setText("编号:"+mMerchantandGoods.seller.userid);
		mMerchantDial.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.call(Merchant.this, mMerchantandGoods.seller.sellerphone);
			}
		});
		
		mMerchantWishlist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DoAsyncTask t = new DoAsyncTask(Merchant.this, "正在收藏...", new DoTask() {
					
					@Override
					public Object task() {
						// TODO Auto-generated method stub
//						tid		分享类型:1商品2商家3会员
						return Apicmd.getInstance().getAddOrUnCollect(getApplicationContext(), mMerchantandGoods.seller.id, "2", AddOrUnCollect.ADD_COLLECT);
					}
				}, new DoAsyncTask.PostResult() {
					
					@Override
					public void result(Object result) {
						// TODO Auto-generated method stub
						if(result!=null){
							AddOrUnCollect ac = (AddOrUnCollect)result;
							if(ac.isSucceed()){
								mMerchantWishlist.setBackgroundResource(R.drawable.wishlist);
								Toast.makeText(getApplicationContext(), ac.errMsg, Toast.LENGTH_SHORT).show();
							}else{
								Toast.makeText(getApplicationContext(), ac.errMsg, Toast.LENGTH_SHORT).show();
							}
						}
					}
				}, true, true);
			}
		});
		
	}
	
	private void updataListView(){
		GoodsAdapter ga = new GoodsAdapter(mMerchantandGoods.goods);
		listView.setAdapter(ga);
	}
	
	private class GoodsAdapter extends BaseAdapter {

		private List<Goods> goods;
		
		public GoodsAdapter(List<Goods> list){
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
			View item = LayoutInflater.from(getApplicationContext())
			.inflate(R.layout.list_merchant_item, null);
			ImageView i = (ImageView) item
					.findViewById(R.id.id_merchant_list_item_imageview);
			RelativeLayout.LayoutParams alps = new RelativeLayout.LayoutParams(
					Global.WIDTH_SCREEN/3, RelativeLayout.LayoutParams.WRAP_CONTENT);
			i.setLayoutParams(alps);
			i.setScaleType(ScaleType.CENTER_INSIDE);
			TextView name = (TextView) item
					.findViewById(R.id.id_merchant_list_item_goods_name_text);
			TextView price = (TextView) item
					.findViewById(R.id.id_merchant_list_item_price_text);
			TextView wishlist = (TextView) item
					.findViewById(R.id.id_merchant_list_item_wishlist);
			TextView detail = (TextView) item
					.findViewById(R.id.id_merchant_list_item_goods_detail);
			TextView purpose = (TextView) item
					.findViewById(R.id.id_merchant_list_item_goods_purpose_type);
			TextView phone = (TextView) item
					.findViewById(R.id.id_merchant_list_item_dail);
			
			item.setTag(new ViewHolder(i, name, price, wishlist, detail, purpose, phone));
			return item;
		}
		
		private String getType(String str){
			String type="";
			if(str.equals("ticket")){
				type="打折商品";
			}else if(str.equals("show")){
				type="展示";
			}else if(str.equals("ask")){
				type="询价";
			}else if(str.equals("order")){
				type="订单";
			}
			return type;
		}
		
		private void bindView(int position, View converView){
			final ViewHolder tag = (ViewHolder)converView.getTag();
//			tag.image
			if(goods!=null){
				final Goods g = goods.get(position);
				tag.image.setTag(g.url);
				new AsyncViewTask(Global.WIDTH_SCREEN/3, new PostExecute() {
					
					@Override
					public void result(boolean result) {
						// TODO Auto-generated method stub
						
					}
				}).execute(tag.image);//异步加载图片
				tag.name.setText("品  名:"+g.name);
				tag.price.setText("价  格:"+g.nowprice);
				tag.wishlist.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				tag.detail.setText("详  情:"+g.content);
				tag.type.setText(getType(g.type));
				tag.type.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if(g.type.equals("ticket")){
							
						}else if(g.type.equals("ask")){
							new DoAsyncTask(Merchant.this, "正在询价...", new DoTask() {
								
								@Override
								public Object task() {
									// TODO Auto-generated method stub
									return Apicmd.getInstance().getSendAskPrice(getApplicationContext(), g.id);
								}
							}, new PostResult() {
								
								@Override
								public void result(Object result) {
									// TODO Auto-generated method stub
									if(result!=null){
										SendAskPrice sap = (SendAskPrice)result;
										if(sap.isSucceed()){
											Toast.makeText(getApplicationContext(), sap.msg, Toast.LENGTH_SHORT).show();
										}else{
											Toast.makeText(getApplicationContext(), "询价失败,请稍后重试", Toast.LENGTH_SHORT).show();
										}
									}else{
										Toast.makeText(getApplicationContext(), "询价失败,请稍后重试", Toast.LENGTH_SHORT).show();
									}
								}
							}, true, true);
						}else if(g.type.equals("order")){
							new DoAsyncTask(Merchant.this, "正在提交订单...", new DoTask() {
								
								@Override
								public Object task() {
									// TODO Auto-generated method stub
									Cart cart = new Cart();
									cart.price = g.nowprice+"";
									return Apicmd.getInstance().getAddorder_save(getApplicationContext(), mMerchantandGoods.seller.sellername, cart);
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
						}else{
							Toast.makeText(getApplicationContext(), "展示商品", Toast.LENGTH_SHORT).show();
						}
						
					}
				});
				
				tag.phone.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Global.call(Merchant.this, g.sellerphone);
					}
				});
				
				tag.wishlist.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
							DoAsyncTask t = new DoAsyncTask(Merchant.this, "正在收藏...", new DoTask() {
							
							@Override
							public Object task() {
								// TODO Auto-generated method stub
//								tid		分享类型:1商品2商家3会员
								return Apicmd.getInstance().getAddOrUnCollect(getApplicationContext(),
										g.id, "1", AddOrUnCollect.ADD_COLLECT);
							}
						}, new DoAsyncTask.PostResult() {
							
							@Override
							public void result(Object result) {
								// TODO Auto-generated method stub
								if(result!=null){
									AddOrUnCollect ac = (AddOrUnCollect)result;
									if(ac.isSucceed()){
										tag.wishlist.setBackgroundResource(R.drawable.wishlist);
										Toast.makeText(getApplicationContext(), ac.errMsg, Toast.LENGTH_SHORT).show();
									}else{
										Toast.makeText(getApplicationContext(), ac.errMsg, Toast.LENGTH_SHORT).show();
									}
								}
							}
						}, true, true);
					}
				});
			}
			
		}
		
		private class ViewHolder{
			public ImageView image;
			public TextView name;
			public TextView price;
			public TextView wishlist;
			public TextView detail;
			public TextView type;
			public TextView phone;
			
			public ViewHolder(ImageView image, TextView name,
					TextView price, TextView wishlist, TextView detail, TextView type, TextView phone){
				this.image = image;
				this.name = name;
				this.price = price;
				this.wishlist = wishlist;
				this.detail = detail;
				this.type = type;
				this.phone = phone;
			}
		}
		
	}
	
//	public class MerchantDetail implements Parcelable{
//		public String name;
//		public String price;
//		public String detail;
//		public List<String> imgUrl;
////		public MerchantDetail(String name, String price,
////				String detail, List<String> imgUrl){
////			this.name = name;
////			this.price = price;
////			this.detail = detail;
////			this.imgUrl = imgUrl;
////		}
//		
//		public static final Parcelable.Creator<MerchantDetail> CREATOR = 
//				new Creator<MerchantDetail>() {  
//	        public MerchantDetail createFromParcel(Parcel source) { 
//	        	return new MerchantDetail(source);
////	        	MerchantDetail m = new MerchantDetail();  
////	            m.name = source.readString();  
////	            m.price = source.readString();  
////	            m.detail = source.readString();
////	            List<String> l = new ArrayList<String>();
////	            m.imgUrl = new ArrayList<String>();
////	            source.readStringList(m.imgUrl);
////	            return m;  
//	        }  
//	        public MerchantDetail[] newArray(int size) {  
//	            return new MerchantDetail[size];  
//	        }  
//	    }; 
//		
//	    private MerchantDetail(Parcel source) {
//	    	name = source.readString();  
//            price = source.readString();  
//            detail = source.readString();
//            imgUrl = new ArrayList<String>();
//            source.readStringList(imgUrl);
//	     }
//	    
//		@Override
//		public int describeContents() {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public void writeToParcel(Parcel dest, int flags) {
//			// TODO Auto-generated method stub
//			dest.writeString(name);
//			dest.writeString(price);
//			dest.writeString(detail);
//			dest.writeStringList(imgUrl);
//		}
//		
//	}
	

}
