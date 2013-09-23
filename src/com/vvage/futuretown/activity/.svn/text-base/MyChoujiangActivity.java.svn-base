package com.vvage.futuretown.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.vvage.futuretown.R;
import com.vvage.futuretown.adapter.MychoujiangAdapter;
import com.vvage.futuretown.model.MyChoujiang;

public class MyChoujiangActivity extends Activity{
	private List<MyChoujiang>data;
	private BaseAdapter adapter;
	private ListView list;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mychoujiang);
		
		list=(ListView) findViewById(R.id.cj_lv);
		
		data=new ArrayList<MyChoujiang>();
		
		data.add(new MyChoujiang(R.drawable.ceshi_tu_01, "商家名:"+"豹纹纯棉外套餐","品名:"+"豹纹纯棉外套餐", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战", "未开奖"));
		
		data.add(new MyChoujiang(R.drawable.ceshi_tu_01, "商家名:"+"豹纹纯棉外套餐","品名:"+"豹纹纯棉外套餐", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战", "中奖"));
		
			data.add(new MyChoujiang(R.drawable.ceshi_tu_01, "商家名:"+"豹纹纯棉外套餐","品名:"+"豹纹纯棉外套餐", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战", "未开奖"));
			data.add(new MyChoujiang(R.drawable.ceshi_tu_01, "商家名:"+"豹纹纯棉外套餐","品名:"+"豹纹纯棉外套餐", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战", "未开奖"));
		
		adapter=new MychoujiangAdapter(data, this);
		list.setAdapter(adapter);
		
		
		
	}

}
