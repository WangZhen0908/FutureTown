package com.vvage.futuretown.util;

import java.util.ArrayList;

import android.R.color;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.vvage.futuretown.Global;
import com.vvage.futuretown.R;

public class CustomSpinner extends Spinner implements OnItemClickListener {

	
	public static SelectDialog dialog = null;  
    private ArrayList<String> list;  
    public static String text;  
    
    
	public CustomSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onItemClick(AdapterView<?> view, View itemView, int position,
			long id) {
		// TODO Auto-generated method stub
		setSelection(position);  
//        setText(list.get(position));  
        if (dialog != null) {  
            dialog.dismiss();  
            dialog = null;  
        }  
	}
	
	
	
	
	@Override
	public boolean performClick() {
		// TODO Auto-generated method stub
		Context context = getContext();
		
		LinearLayout layout = new LinearLayout(context);
//		layout.setLayoutParams(new LinearLayout.LayoutParams(
//				LayoutParams.WRAP_CONTENT, Global.HEIGHT_SCREEN*3/5));
		
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundColor(color.background_light);
		
		TextView t = new TextView(context);
		t.setTextSize(TypedValue.COMPLEX_UNIT_SP, Global.TEXT_SIZE_LV3);
		t.setGravity(Gravity.CENTER);
		
		ListView l = new ListView(context);
		l.setLayoutParams(new AbsListView.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.FILL_PARENT));
		l.setScrollingCacheEnabled(false);
		l.setDivider(new ColorDrawable(0xffCCC9C9));
		l.setDividerHeight(1);// 设置分隔线的高度为0
		l.setOnItemClickListener(this);
		
		dialog = new SelectDialog(context, R.style.ftselectdialog);//创建Dialog并设置样式主题
		LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, Global.HEIGHT_SCREEN*3/5);
		dialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
		dialog.show();
		dialog.addContentView(layout, params);
		
		return true;
	}




	public class SelectDialog extends AlertDialog {  
		  
	    public SelectDialog(Context context, int theme) {  
	        super(context, theme);  
	    }  
	  
	    public SelectDialog(Context context) {  
	        super(context);  
	    }  
	  
	    @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        // setContentView(R.layout.slt_cnt_type);  
	    }  
	}  

}
