package com.vvage.futuretown.activity;

import android.R;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HeadLayout extends View {
	//list直接访问Head
	public ImageView arrowImageView;
	public ProgressBar progressBar;
	public TextView tipsTextview;
	public TextView lastUpdatedTextView;
	
	public HeadLayout(Context context){
		super(context);
		
	}
	
	public HeadLayout(Context context,AttributeSet attrs){
		super(context, attrs);
	}
	
	private void createLayout(Context context){
		LinearLayout layout = new LinearLayout(context);
		
		layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		layout.setBackgroundColor(getResources().getColor(R.color.background_light));
		
		RelativeLayout relative = new RelativeLayout(context);
		relative.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		relative.setPadding(30, 0, 0, 0);
		
		FrameLayout frame = new FrameLayout(context);
		FrameLayout.LayoutParams flLPS = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		flLPS.gravity = Gravity.CENTER_VERTICAL;		
		
		
	}
}
