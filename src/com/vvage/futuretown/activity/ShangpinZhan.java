package com.vvage.futuretown.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vvage.futuretown.R;
import com.vvage.futuretown.adapter.ImageAdapter;
import com.vvage.futuretown.model.Image;

public class ShangpinZhan extends Activity{
	private List<Image> data;
	private Gallery gy;
	private BaseAdapter adapter;
	private ImageButton pre, next;
	private TextView pinName, price, xiangqing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shangzhan);
		gy = (Gallery) findViewById(R.id.sz_ger);
		pre = (ImageButton) findViewById(R.id.sz_up);
		next = (ImageButton) findViewById(R.id.sz_next);

		pinName = (TextView) findViewById(R.id.sz_pinname);
		price = (TextView) findViewById(R.id.sz_price);
		xiangqing = (TextView) findViewById(R.id.sz_xiangq);
		pinName.setText("糖果屋");
		price.setText("20132");
		xiangqing.setText("根据当前经纬度返回周围指定半径内的商家，post参数1.经度（String）2.纬度（纬度）3.商家类型（int类型，范围0-14），接收到的数据为json数据，每个数据组内包含商家全部信息以及，额外的经纬度");
		
		data = new ArrayList<Image>();

		data.add(new Image(R.drawable.ceshi_tu_01));
		data.add(new Image(R.drawable.shoucang));
		data.add(new Image(R.drawable.ceshi_tu_01));
		data.add(new Image(R.drawable.ceshi_tu_02));

		adapter = new ImageAdapter(data, this);

		gy.setAdapter(adapter);
		pre.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				gy.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);

			}
		});

		next.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				gy.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
			}
		});
	}
	

}
