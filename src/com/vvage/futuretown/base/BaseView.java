package com.vvage.futuretown.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vvage.futuretown.Global;
import com.vvage.futuretown.HomeActivity;
import com.vvage.futuretown.R;
import com.vvage.futuretown.cmd.Apicmd;
import com.vvage.futuretown.model.SendVCodeParse;
import com.vvage.futuretown.util.DoAsyncTask;
import com.vvage.futuretown.util.StringUtil;
import com.vvage.futuretown.util.DoAsyncTask.DoTask;

/*
 * 页面抽象类 继承Activity
 * 
 */
public abstract class BaseView extends Activity{
//	public static final int CUSTOM = 0;
//	public static final int FUTURE_SYSTEM=1;
	
	private int TITLE_HEIGHT;
	private final int TEXT_PANDDING = 8;
	protected TitleParams mTitleParams;
	
	private View mProgressBarLayout;
	private LinearLayout mTitleLayout;
	private LinearLayout mContainerLayout;
	private LinearLayout mLayout;
	
	private AsyncTask<Object, Object, Object> task = null;
	
	/*
	 * 页面子类content view
	 * @param Context
	 * @return View 返回子类内容页面布局
	 */
	protected abstract View creatView(Context context);
	
	protected abstract void onClickLeft();
	protected abstract void onClickRight();
	
	protected void setTitleParams(TitleParams tp){
		mTitleParams = tp;
	}
	
	protected void getViewData(){
		
	}
	
	protected View creatTitle(){
		RelativeLayout layout = new RelativeLayout(getApplicationContext());
		RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, TITLE_HEIGHT);
		layout.setLayoutParams(lps);
		layout.setBackgroundResource(R.drawable.bg_titleview);
		
		RelativeLayout.LayoutParams leftLayoutLPS = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		leftLayoutLPS.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		leftLayoutLPS.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		leftLayoutLPS.leftMargin = TITLE_HEIGHT/4;
		
		final ButtonTextView left = new ButtonTextView(this);
		left.setLayoutParams(leftLayoutLPS);
		left.setText(mTitleParams==null?"":mTitleParams.left);
		left.setVisibility(mTitleParams==null?View.GONE:mTitleParams.isleftVisible);
		left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(mTitleParams!=null && mTitleParams.isleftVisible==View.VISIBLE){
					BaseView.this.onClickLeft();
					if(mTitleParams.isleftVisible != View.VISIBLE){
						left.setVisibility(mTitleParams.isleftVisible);
					}
				}
			}
		});
		
		
		RelativeLayout.LayoutParams rightLayoutLPS = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rightLayoutLPS.alignWithParent = true;
		rightLayoutLPS.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		rightLayoutLPS.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		rightLayoutLPS.rightMargin = TITLE_HEIGHT/4;
		
		final ButtonTextView right = new ButtonTextView(this);
		right.setLayoutParams(rightLayoutLPS);
		right.setText(mTitleParams==null?"":mTitleParams.right);
		right.setVisibility(mTitleParams==null?View.GONE:mTitleParams.isrightVisible);
		right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(mTitleParams==null){
					
				}else if(mTitleParams!=null && mTitleParams.isrightVisible==View.VISIBLE){
					BaseView.this.onClickRight();
					if(mTitleParams.isrightVisible != View.VISIBLE){
						right.setVisibility(mTitleParams.isrightVisible);
					}
				}
			}
		});
		
		
		RelativeLayout.LayoutParams titleLPS = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		titleLPS.addRule(RelativeLayout.CENTER_IN_PARENT);
		
		
		TextView title = new TextView(this);
		title.setGravity(Gravity.CENTER);
		title.setLayoutParams(titleLPS);
		title.setPadding(TEXT_PANDDING,TEXT_PANDDING,TEXT_PANDDING,TEXT_PANDDING);
		title.setSingleLine(true);
		title.setTextColor(Color.WHITE);
		title.setVisibility(mTitleParams==null?View.GONE:View.VISIBLE);
		title.setText(mTitleParams==null?"":mTitleParams.title);
		title.setTextSize(TypedValue.COMPLEX_UNIT_SP, Global.TEXT_SIZE_LV1);
		
		RelativeLayout.LayoutParams titleViewLPS = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		titleViewLPS.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		
		RelativeLayout titleView = new RelativeLayout(this);
//		titleView.setGravity(Gravity.CENTER_VERTICAL);
		titleView.setLayoutParams(titleViewLPS);
		
		layout.addView(left);
		if(mTitleParams.view!=null){
			titleView.addView(mTitleParams.view);
			layout.addView(titleView);
		}else{
			layout.addView(title);
		}
		layout.addView(right);
		
		return layout;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		The conversion of dp units to screen pixels is simple: px = dp * (dpi / 160).
		
		super.onCreate(savedInstanceState);
		
		TITLE_HEIGHT = Global.dip2px(getApplicationContext(), 50);
		mLayout = new LinearLayout(getApplicationContext());
		LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
		mLayout.setLayoutParams(lps);
		mLayout.setOrientation(LinearLayout.VERTICAL);
		mLayout.setBackgroundResource(R.drawable.bg);
		
		mProgressBarLayout = createProgressBar();
		
		mTitleLayout = new LinearLayout(getApplicationContext());
		mTitleLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, TITLE_HEIGHT));
		
		mContainerLayout = new LinearLayout(getApplicationContext());
		mContainerLayout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
		
		View v = creatView(this);
		if(v!=null){
			mContainerLayout.addView(v);
		}
		mTitleLayout.addView(creatTitle());
		
		mLayout.addView(mTitleLayout);
		mLayout.addView(mContainerLayout);
		
//		mLayout.addView(mProgressBarLayout);
//		mLayout.post(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				mLayout.removeView(mProgressBarLayout);
//				mLayout.addView(mContainerLayout);
//			}
//		});
		
		
		setContentView(mLayout);
		
		
		
	}
	
	private View createProgressBar(){
		LinearLayout progressLayout = new LinearLayout(getApplicationContext());
		LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		progressParams.gravity = Gravity.CENTER;
		progressLayout.setOrientation(LinearLayout.HORIZONTAL);
		progressLayout.setGravity(Gravity.CENTER);
		progressLayout.setLayoutParams(progressParams);

		ProgressBar progress = new ProgressBar(getApplicationContext());

		TextView info = new TextView(getApplicationContext());
		info.setText("正在加载, 网络不好\n会稍慢一点哦~请稍后...");
//		info.setTextColor(Global.PKGAME_COLOR_TEXT_BLACK);

		progressLayout.addView(progress, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		progressLayout.addView(info, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		return progressLayout;
	}
	
	protected void switchTab(String tagId, boolean isClose){
		HomeActivity.tabHost.setCurrentTabByTag(tagId);
		if(isClose){
//			Intent i = new Intent();
//			i.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//			startActivity(i);
			finish();
		}
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
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
		super.onResume();
	}
	
	protected class TitleParams {
//		public boolean defaultParams=false;
		public String left;
		public int isleftVisible;
		
		public String title;
		public View view;
		
		public String right;
		public int isrightVisible;
		
		public TitleParams(String leftText, int isLeftVisible, 
				String title, View view, String rightText, int isrightVisible){
			this.left = leftText;
			this.isleftVisible = isLeftVisible;
			this.title = title;
			this.view = view;
			this.right = rightText;
			this.isrightVisible = isrightVisible;
		}

	}
	
	public class ButtonTextView extends TextView{
		public ButtonTextView(Context context){
			super(context);
			configurationTextView(context);
		}
		private void configurationTextView(Context context){
			setGravity(Gravity.CENTER);
			setPadding(TEXT_PANDDING*2,TEXT_PANDDING,TEXT_PANDDING*2,TEXT_PANDDING);
			setBackgroundDrawable(Global.newSelectorFromRes(getApplicationContext(), 
					R.drawable.bg_btn_normal, R.drawable.bg_btn_pressed, 
					R.drawable.bg_btn_pressed, R.drawable.bg_btn_normal));
			setSingleLine(true);
			setTextColor(Color.WHITE);
			setTextSize(TypedValue.COMPLEX_UNIT_SP, Global.TEXT_SIZE_LV4);
		}
	}
	
	protected void sendVcode(final String phone, final String type){
//		0注册 1找密码 2添加小号 3转让
		if(StringUtil.isMobileNumber(phone)){
			DoAsyncTask t = new DoAsyncTask(BaseView.this, "正在发送请求，请稍后接收短信", new DoTask() {
				@Override
				public Object task() {
					// TODO Auto-generated method stub
//					0注册 1找密码 2添加小号 3转让
					return Apicmd.getInstance().sendVCode(
							getApplicationContext(), type, phone);
				}
			}, new DoAsyncTask.PostResult(){
				@Override
				public void result(Object result) {
					// TODO Auto-generated method stub
					if (result != null) {
						// 如果手机未注册
						SendVCodeParse sv = (SendVCodeParse)result;
						Toast.makeText(getApplicationContext(), sv.err_str, Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(getApplicationContext(), "获取失败.", Toast.LENGTH_SHORT).show();
					}
				}
			}, true, true);
		}else{
			Toast.makeText(getApplicationContext(), "手机号码无效,请从新输入", Toast.LENGTH_SHORT).show();
		}
	}
	
}
