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
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.vvage.futuretown.Global;
import com.vvage.futuretown.R;
import com.vvage.futuretown.base.BaseView;
import com.vvage.futuretown.cmd.Apicmd;
import com.vvage.futuretown.model.AddOrUnCollect;
import com.vvage.futuretown.model.User;
import com.vvage.futuretown.util.AsyncViewTask;
import com.vvage.futuretown.util.DoAsyncTask;
import com.vvage.futuretown.util.AsyncViewTask.PostExecute;
import com.vvage.futuretown.util.DoAsyncTask.DoTask;
import com.vvage.futuretown.util.StringUtil;

public class MerchantDetail extends BaseView implements OnGestureListener {

	private ViewFlipper mFlipper;
	private GestureDetector mDetector;

	private TextView mMerchantName;
	private Button mWishlist;
	private TextView mDial;
	private TextView mMerchantDetail;
	private ImageView iv;
	

	// test data

	@Override
	protected View creatView(Context context) {
		// TODO Auto-generated method stub
		
		iv = new ImageView(this);
		Intent i = getIntent();
		Bundle b= i.getBundleExtra(Merchant.MERCHANT);
		
		String name = "";
		String detail = "";
		String phone = "";
		String url="";
		String id="";
		if(b!=null){
			name = b.getString("name");
			detail = b.getString("detail");
			phone = b.getString("phone");
			url = b.getString("url");
			id = b.getString("id");
		}
		
		final String p =phone;
		int visible = View.VISIBLE;
		boolean isLogin = StringUtil.isValidStr(User.uid);
		setTitleParams(new TitleParams("返回", visible, "商家详情", null,
				isLogin?"登录":"登录", isLogin?View.GONE:visible));
		
		View layout = LayoutInflater.from(context).inflate(R.layout.merchantdetail, null);
		
		mMerchantName = (TextView)layout.findViewById(R.id.id_merchant_detail_textview);
		mMerchantName.setText(name);
		mMerchantDetail = (TextView)layout.findViewById(R.id.id_merchant_detail_detail_text);
		mMerchantDetail.setText(detail);

		mDial = (TextView)layout.findViewById(R.id.id_merchant_detail_dail_btn);
		mDial.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Global.call(MerchantDetail.this, p);
			}
		});
		final String final_id = id;
		mWishlist = (Button)layout.findViewById(R.id.id_merchant_detail_wishlist_btn);
		mWishlist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				DoAsyncTask t = new DoAsyncTask(MerchantDetail.this, "正在收藏...", new DoTask() {
					
					@Override
					public Object task() {
						// TODO Auto-generated method stub
//						tid		分享类型:1商品2商家3会员
						return Apicmd.getInstance().getAddOrUnCollect(getApplicationContext(), final_id, "2", AddOrUnCollect.ADD_COLLECT);
					}
				}, new DoAsyncTask.PostResult() {
					
					@Override
					public void result(Object result) {
						// TODO Auto-generated method stub
						if(result!=null){
							AddOrUnCollect ac = (AddOrUnCollect)result;
							if(ac.isSucceed()){
								mWishlist.setBackgroundResource(R.drawable.wishlist);
								Toast.makeText(getApplicationContext(), ac.errMsg, Toast.LENGTH_SHORT).show();
							}else{
								Toast.makeText(getApplicationContext(), ac.errMsg, Toast.LENGTH_SHORT).show();
							}
						}
					}
				}, true, true);
				
			}
		});
		
		mDetector = new GestureDetector(this);

		mFlipper = (ViewFlipper)layout.findViewById(R.id.id_merchant_detail_googs_photoshow_view);
		
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
