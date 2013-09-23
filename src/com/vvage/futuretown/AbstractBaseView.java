//package com.vvage.futuretown;
//
//import com.vvage.futuretown.base.BaseView;
//import com.vvage.futuretown.base.BaseView.ButtonTextView;
//import com.vvage.futuretown.base.BaseView.TitleParams;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.support.v4.view.ViewPager.LayoutParams;
//import android.util.TypedValue;
//import android.view.Gravity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//
//public abstract class AbstractBaseView {
//
//	private int TITLE_HEIGHT;
//	private final int TEXT_PANDDING = 8;
//	protected TitleParams mTitleParams;
//	
//	private LinearLayout mTitleLayout;
//	private LinearLayout mContainerLayout;
//	private LinearLayout mLayout;
//	
//	/*
//	 * 页面子类content view
//	 * @param Context
//	 * @return View 返回子类内容页面布局
//	 */
//	protected abstract View creatView(Context context);
//	protected abstract void onClickLeft();
//	protected abstract void onClickRight();
//	
//	protected void setTitleParams(TitleParams tp){
//		mTitleParams = tp;
//	}
//	
//	protected View creatTitle(){
//		RelativeLayout layout = new RelativeLayout(getApplicationContext());
//		RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, TITLE_HEIGHT);
//		layout.setLayoutParams(lps);
//		layout.setBackgroundResource(R.drawable.bg_titleview);
//		
//		RelativeLayout.LayoutParams leftLayoutLPS = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		leftLayoutLPS.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//		leftLayoutLPS.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
//		leftLayoutLPS.leftMargin = TITLE_HEIGHT/4;
//		
//		final ButtonTextView left = new ButtonTextView(this);
//		left.setLayoutParams(leftLayoutLPS);
//		left.setText(mTitleParams==null?"":mTitleParams.left);
//		left.setVisibility(mTitleParams==null?View.GONE:mTitleParams.isleftVisible);
//		left.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				if(mTitleParams!=null && mTitleParams.isleftVisible==View.VISIBLE){
//					BaseView.this.onClickLeft();
//					if(mTitleParams.isleftVisible != View.VISIBLE){
//						left.setVisibility(mTitleParams.isleftVisible);
//					}
//				}
//			}
//		});
//		
//		
//		RelativeLayout.LayoutParams rightLayoutLPS = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		rightLayoutLPS.alignWithParent = true;
//		rightLayoutLPS.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//		rightLayoutLPS.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
//		rightLayoutLPS.rightMargin = TITLE_HEIGHT/4;
//		
//		final ButtonTextView right = new ButtonTextView(this);
//		right.setLayoutParams(rightLayoutLPS);
//		right.setText(mTitleParams==null?"":mTitleParams.right);
//		right.setVisibility(mTitleParams==null?View.GONE:mTitleParams.isrightVisible);
//		right.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				if(mTitleParams==null){
//					
//				}else if(mTitleParams!=null && mTitleParams.isrightVisible==View.VISIBLE){
//					BaseView.this.onClickRight();
//					if(mTitleParams.isrightVisible != View.VISIBLE){
//						right.setVisibility(mTitleParams.isrightVisible);
//					}
//				}
//			}
//		});
//		
//		
//		RelativeLayout.LayoutParams titleLPS = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		titleLPS.addRule(RelativeLayout.CENTER_IN_PARENT);
//		
//		
//		TextView title = new TextView(this);
//		title.setGravity(Gravity.CENTER);
//		title.setLayoutParams(titleLPS);
//		title.setPadding(TEXT_PANDDING,TEXT_PANDDING,TEXT_PANDDING,TEXT_PANDDING);
//		title.setSingleLine(true);
//		title.setTextColor(Color.WHITE);
//		title.setVisibility(mTitleParams==null?View.GONE:View.VISIBLE);
//		title.setText(mTitleParams==null?"":mTitleParams.title);
//		title.setTextSize(TypedValue.COMPLEX_UNIT_SP, Global.TEXT_SIZE_LV1);
//		
//		RelativeLayout.LayoutParams titleViewLPS = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		titleViewLPS.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//		
//		RelativeLayout titleView = new RelativeLayout(this);
////		titleView.setGravity(Gravity.CENTER_VERTICAL);
//		titleView.setLayoutParams(titleViewLPS);
//		
//		layout.addView(left);
//		if(mTitleParams.view!=null){
//			titleView.addView(mTitleParams.view);
//			layout.addView(titleView);
//		}else{
//			layout.addView(title);
//		}
//		layout.addView(right);
//		
//		return layout;
//	}
//	
//	public 
//	
//}
