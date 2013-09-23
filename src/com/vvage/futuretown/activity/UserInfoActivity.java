package com.vvage.futuretown.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vvage.futuretown.HomeActivity;
import com.vvage.futuretown.R;

public class UserInfoActivity extends Activity {
	private ImageView leftImgBtn,rightImgBtn,quitImgBtn;
	private TextView centerText;
	private EditText nickNameEdit,phoneNumEdit,emailEdit,qqEdit;
	private Button saveBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);

		prepareView();
		prepareData();
	}

	private void prepareData() {
		// TODO Auto-generated method stub
		
	}

	private void prepareView() {
		// TODO Auto-generated method stub
		leftImgBtn = (ImageView)findViewById(R.id.leftImgBtn);
		rightImgBtn = (ImageView)findViewById(R.id.rightImgBtn);
//		quitImgBtn = (ImageView)findViewById(R.id.quitImgBtn);
		centerText = (TextView)findViewById(R.id.centerText);
		leftImgBtn.setImageResource(R.drawable.btn_back);
		rightImgBtn.setVisibility(View.GONE);
		quitImgBtn.setVisibility(View.GONE);
		centerText.setText("个人信息");
		
		nickNameEdit = (EditText)findViewById(R.id.nickNameEdit);
		phoneNumEdit = (EditText)findViewById(R.id.phoneNumEdit);
		emailEdit = (EditText)findViewById(R.id.emailEdit);
		qqEdit = (EditText)findViewById(R.id.qqEdit);
		saveBtn = (Button)findViewById(R.id.saveBtn);
		
		leftImgBtn.setOnTouchListener(new MyOnTouchListener());
		saveBtn.setOnTouchListener(new MyOnTouchListener());
	}
	private class MyOnTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.leftImgBtn:
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					leftImgBtn.setAlpha(100);
					break;
				case MotionEvent.ACTION_MOVE:
					
					break;
				case MotionEvent.ACTION_UP:
					leftImgBtn.setAlpha(255);
					Intent intent=new Intent();
					intent.setClass(UserInfoActivity.this, HomeActivity.class);
					startActivity(intent);
					break;
				default:
					break;
				}
				break;
			case R.id.saveBtn:
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					saveBtn.getBackground().setAlpha(100);
					break;
				case MotionEvent.ACTION_MOVE:

					break;
				case MotionEvent.ACTION_UP:
					saveBtn.getBackground().setAlpha(255);
					
					
					break;
				default:
					break;
				}
				break;
			}
			return true;
		}
	}
}
