//package com.vvage.futuretown.activity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.BaseAdapter;
//import android.widget.ListView;
//
//import com.vvage.futuretown.R;
//import com.vvage.futuretown.adapter.YifaGoodsManaAdapter;
//import com.vvage.futuretown.model.Order;
//
//public class YifaGoodsManagerActivity extends Activity{
//	private List<Order>data;
//	private BaseAdapter adapter;
//	private ListView lv;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.yigoodsmanager);
//		
//		lv=(ListView) findViewById(R.id.yg_lv);
//		data=new ArrayList<Order>();
//		
//		data.add(new Order(R.drawable.ceshi_tu_01, "品名："+"糖果屋", "金额:￥"+"445"));
//		data.add(new Order(R.drawable.ceshi_tu_01, "品名："+"糖果屋", "金额:￥"+"445"));
//		data.add(new Order(R.drawable.ceshi_tu_01, "品名："+"糖果屋", "金额:￥"+"445"));
//		adapter=new YifaGoodsManaAdapter(data, this);
//		lv.setAdapter(adapter);
//		
//	}
//
//}
