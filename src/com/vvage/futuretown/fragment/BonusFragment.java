package com.vvage.futuretown.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.vvage.futuretown.BaseFragment;
import com.vvage.futuretown.R;
import com.vvage.futuretown.adapter.ChouAdapter;
import com.vvage.futuretown.model.ChouJiang;

public class BonusFragment extends BaseFragment {

	private List<ChouJiang> data;
	private BaseAdapter adapter;
	private ListView list;
	private ImageView leftImgBtn, rightImgBtn;// 标题栏，左侧首页按钮、右侧刷新按
	private TextView centerText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("ddd");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bonus, null);

//		prepareView(view);
//		prepareData();
		return view;
	}

//	private void prepareData() {
//		// TODO Auto-generated method stub
//		data = new ArrayList<ChouJiang>();
//		data.add(new ChouJiang(R.drawable.ceshi_tu_01, "商家名:" + "品名", "品名:"
//				+ "12345678645", "详情:" + "这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
//		data.add(new ChouJiang(R.drawable.ceshi_tu_01, "商家名:" + "豹纹纯棉外套餐",
//				"品名:" + "豹纹纯棉外套餐", "详情:" + "这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
//		data.add(new ChouJiang(R.drawable.ceshi_tu_01, "商家名:" + "豹纹纯棉外套餐",
//				"品名:" + "豹纹纯棉外套餐", "详情:" + "这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
//		data.add(new ChouJiang(R.drawable.ceshi_tu_01, "商家名:" + "豹纹纯棉外套餐",
//				"品名:" + "1豹纹纯棉外套餐", "详情:" + "这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
//		data.add(new ChouJiang(R.drawable.bg, "商家名:" + "豹纹纯棉外套餐", "品名:"
//				+ "1豹纹纯棉外套餐5", "详情:" + "这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
//		data.add(new ChouJiang(R.drawable.ceshi_tu_01, "商家名:" + "豹纹纯棉外套餐",
//				"品名:" + "1豹纹纯棉外套餐5", "详情:" + "这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
//
//		adapter = new ChouAdapter(data, getActivity());
//		list.setAdapter(adapter);
//	}

//	private void prepareView(View view) {
//		// TODO Auto-generated method stub
//		leftImgBtn = (ImageView) view.findViewById(R.id.leftImgBtn);
//		rightImgBtn = (ImageView) view.findViewById(R.id.rightImgBtn);
//		centerText = (TextView) view.findViewById(R.id.centerText);
//		leftImgBtn.setVisibility(View.GONE);
//		centerText.setText("抽奖");
//		rightImgBtn.setImageResource(R.drawable.btn_login);
//		rightImgBtn.setOnTouchListener(new MyOnTouchListener());
//		list = (ListView) view.findViewById(R.id.chou_lv);
//	}

//	private class MyOnTouchListener implements OnTouchListener {
//
//		@Override
//		public boolean onTouch(View v, MotionEvent event) {
//			// TODO Auto-generated method stub
//			Intent intent = new Intent();
//			switch (v.getId()) {
//			case R.id.rightImgBtn:
//				switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:
//					rightImgBtn.setAlpha(100);
//					break;
//				case MotionEvent.ACTION_MOVE:
//
//					break;
//				case MotionEvent.ACTION_UP:
//					rightImgBtn.setAlpha(255);
//					break;
//				default:
//					break;
//				}
//				break;
//
//			default:
//				break;
//			}
//			return true;
//		}
//
//	}

}
