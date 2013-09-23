package com.vvage.futuretown.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.ImageView.ScaleType;

import com.vvage.futuretown.Global;
import com.vvage.futuretown.R;
import com.vvage.futuretown.base.BaseView;
import com.vvage.futuretown.fragment.UserFragment;
import com.vvage.futuretown.model.User;
import com.vvage.futuretown.util.AsyncViewTask;
import com.vvage.futuretown.util.StringUtil;
import com.vvage.futuretown.util.AsyncViewTask.PostExecute;

public class MerchantGoodsDetail extends BaseView implements OnGestureListener {

	private ViewFlipper mFlipper;
	private GestureDetector mDetector;

	private TextView mMerchantGoodsName;
	private TextView mMerchantGoodsPrice;
	private TextView mAddCart;
	private TextView mDial;
	private TextView mMerchantGoodsDetail;
	
	private ImageView iv;
	
	

	@Override
	protected View creatView(Context context) {
		// TODO Auto-generated method stub
		iv = new ImageView(this);
		int visible = View.VISIBLE;
		boolean isLogin = StringUtil.isValidStr(User.uid);
		setTitleParams(new TitleParams("返回", visible, "商品详情", null,
				isLogin?"登录":"", isLogin?View.GONE:visible));
		
		
		Intent i = getIntent();
		
		Bundle b= i.getBundleExtra(Merchant.MERCHANT_DETAIL);
		
		String name = "";
		int price = -1;
		String type = "";
		String detail = "";
		String phone = "";
		String url="";
		if(b!=null){
			name = b.getString("name");
			price = b.getInt("price");
			type = b.getString("type");
			detail = b.getString("detail");
			phone = b.getString("phone");
			url = b.getString("url");
		}
		
		
		View layout = LayoutInflater.from(context).inflate(R.layout.merchantgoogsdetail, null);
		mDetector = new GestureDetector(this);

		mMerchantGoodsName = (TextView)layout.findViewById(R.id.id_merchant_goods_detail_name_textview);
		mMerchantGoodsName.setText(name);
		mMerchantGoodsPrice = (TextView)layout.findViewById(R.id.id_id_merchant_goods_detail_price);
		mMerchantGoodsPrice.setText("¥ "+price);
		mMerchantGoodsDetail = (TextView)layout.findViewById(R.id.id_merchant_goods_detail_detail_text);
		mMerchantGoodsDetail.setText(detail);
		
		mAddCart = (TextView)layout.findViewById(R.id.id_merchant_goods_detail_addtocart_btn);
		if(!type.equals("show")){
			mAddCart.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(MerchantGoodsDetail.this, "调用购物车接口",
							Toast.LENGTH_SHORT).show();
				}
			});
		}else{
			mAddCart.setVisibility(View.GONE);
		}
		
		
		final String p = phone;
		mDial = (TextView)layout.findViewById(R.id.id_merchant_goods_detail_dail_btn);
		mDial.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Global.call(MerchantGoodsDetail.this, p);
			}
		});
		

		mFlipper = (ViewFlipper)layout.findViewById(R.id.id_merchant_goods_detail_googs_photoshow_viewflipper);
		
		iv.setTag(url);
		iv.setScaleType(ScaleType.CENTER_INSIDE);
//		mMerchantImg.setLayoutParams(new LinearLayout.LayoutParams(Global.WIDTH_SCREEN/3, android.widget.LinearLayout.LayoutParams.wr))
		new AsyncViewTask(Global.WIDTH_SCREEN*2/3, new PostExecute() {
			
			@Override
			public void result(boolean result) {
				// TODO Auto-generated method stub
				
			}
		}).execute(iv);//异步加载图片
		
		mFlipper.addView(iv);
//		mFlipper.addView(addImageView(R.drawable.ceshi_tu_01));
//		mFlipper.addView(addImageView(R.drawable.ceshi_tu_02));
//		mFlipper.addView(addImageView(R.drawable.ceshi_tu_01));
//		mFlipper.addView(addImageView(R.drawable.ceshi_tu_02));
//		
		return layout;
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

	private View addImageView(int id) {
		ImageView iv = new ImageView(this);
		iv.setImageResource(id);
		return iv;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if (e1.getX() - e2.getX() > 120) {
			mFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_left_in));
			mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_left_out));
			mFlipper.showNext();
			return true;
		} else if (e1.getX() - e2.getX() < -120) {
			mFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_right_in));
			mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_right_out));
			mFlipper.showPrevious();
			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return mDetector.onTouchEvent(event);
	}

}
