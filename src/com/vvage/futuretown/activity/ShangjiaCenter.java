//package com.vvage.futuretown.activity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//
//import com.vvage.futuretown.R;
//
//public class ShangjiaCenter extends Activity implements OnClickListener {
//	private Button ding_btn, xun_btn, shou_btn, chou_btn, shouhuo_btn;
//	private Button userding_btn, userxun_btn, fa_btn, bianji_btn, yifa_btn,
//			yuangonghao_btn;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.shang_center);
//
//		userding_btn = (Button) findViewById(R.id.sc_userorder_btn);
//		userxun_btn = (Button) findViewById(R.id.sc_userenquire_btn);
//		fa_btn = (Button) findViewById(R.id.sc_fabu_btn);
//		bianji_btn = (Button) findViewById(R.id.sc_bianji_btn);
//		yifa_btn = (Button) findViewById(R.id.sc_yifa_btn);
//		yuangonghao_btn = (Button) findViewById(R.id.sc_yuan_btn);
//
//		ding_btn = (Button) findViewById(R.id.sc_dingdan_btn);
//		xun_btn = (Button) findViewById(R.id.sc_xunjia_btn);
//		shou_btn = (Button) findViewById(R.id.sc_shoucang_btn);
//		shouhuo_btn = (Button) findViewById(R.id.sc_shouhuo_btn);
//		chou_btn = (Button) findViewById(R.id.sc_choujiang_btn);
//
//		userding_btn.setOnClickListener(this);
//		userxun_btn.setOnClickListener(this);
//		fa_btn.setOnClickListener(this);
//		bianji_btn.setOnClickListener(this);
//		yifa_btn.setOnClickListener(this);
//		yuangonghao_btn.setOnClickListener(this);
//
//		ding_btn.setOnClickListener(this);
//		xun_btn.setOnClickListener(this);
//		shou_btn.setOnClickListener(this);
//		shouhuo_btn.setOnClickListener(this);
//		chou_btn.setOnClickListener(this);
//
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		if (v.getId() == R.id.sc_userorder_btn) {
//			Intent aIntent = new Intent(ShangjiaCenter.this,
//					WeiOrderActivity.class);
//
//			startActivity(aIntent);
//
//		}
//		if (v.getId() == R.id.sc_userenquire_btn) {
//			Intent aIntent = new Intent(ShangjiaCenter.this,
//					UserEnquire.class);
//
//			startActivity(aIntent);
//		}
//		if (v.getId() == R.id.sc_fabu_btn) {
//			Log.e("跳转不", "跳跳 跳 ");
//		}
//		if (v.getId() == R.id.sc_bianji_btn) {
//			Log.e("跳转不", "跳跳 跳 ");
//		}
//		if (v.getId() == R.id.sc_yifa_btn) {
//			Intent aIntent = new Intent(ShangjiaCenter.this,
//					YifaGoodsManagerActivity.class);
//
//			startActivity(aIntent);
//		}
//		if (v.getId() == R.id.sc_yuan_btn) {
//			Intent aIntent = new Intent(ShangjiaCenter.this,
//					EmployeeManager.class);
//			
//			startActivity(aIntent);
//		}
//		if (v.getId() == R.id.sc_dingdan_btn) {
//		
//		}
//		if (v.getId() == R.id.sc_xunjia_btn) {
//		
//		}
//		if (v.getId() == R.id.sc_shoucang_btn) {
//			
//		}
//		if (v.getId() == R.id.sc_shouhuo_btn) {
//			
//
//		}if (v.getId() == R.id.sc_choujiang_btn) {
//			Log.e("跳转不", "跳跳 跳 ");
//			Intent aIntent = new Intent(ShangjiaCenter.this,
//					MyChoujiangActivity.class);
//
//			startActivity(aIntent);
//
//		}
//
//	}
//
//}
