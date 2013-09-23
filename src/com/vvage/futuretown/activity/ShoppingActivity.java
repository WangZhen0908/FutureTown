package com.vvage.futuretown.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.vvage.futuretown.R;
import com.vvage.futuretown.adapter.ShoppingAdapter;
import com.vvage.futuretown.adapter.TextAdapter;
import com.vvage.futuretown.model.Cang;
import com.vvage.futuretown.model.Text;
import com.vvage.futuretown.util.LogUtil;

public class ShoppingActivity extends Activity{
	private List<Text>data;
	private BaseAdapter adapter,adapter1;
	private GridView gv;
	private List<Cang>data1,data2,data3,data4;
	private ListView lv;
	private TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping);
		gv=(GridView) findViewById(R.id.sh_gv);
		lv=(ListView) findViewById(R.id.sh_Lv);
		
		
		data=new ArrayList<Text>();
		data.add(new Text("一层"));
		data.add(new Text("二层"));
		data.add(new Text("三层"));	
		data.add(new Text("四层"));
	
		
		adapter=new TextAdapter(data, this);
		
		gv.setAdapter(adapter);
		

		
		data1=new ArrayList<Cang>();
		data1.add(new Cang(R.drawable.bg, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
		data1.add(new Cang(R.drawable.bg, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
		data1.add(new Cang(R.drawable.bg, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
		data1.add(new Cang(R.drawable.bg, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
		data1.add(new Cang(R.drawable.bg, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
		
		adapter1=new ShoppingAdapter(data1,this);
		lv.setAdapter(adapter1);
		new Uitity().setListViewHeightBasedOnChildren(lv);
		
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 0) {
					data1=new ArrayList<Cang>();
					data1.add(new Cang(R.drawable.ceshi_tu_01, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					data1.add(new Cang(R.drawable.ceshi_tu_01, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					data1.add(new Cang(R.drawable.ceshi_tu_01, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					data1.add(new Cang(R.drawable.ceshi_tu_01, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					data1.add(new Cang(R.drawable.ceshi_tu_01, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					adapter1=new ShoppingAdapter(data1,ShoppingActivity.this);	
					LogUtil.d("他的位置id为什么 ", ""+data1.size());
					lv.setAdapter(adapter1);
			
				
				
				}
				if (arg2 == 1) {
					data1=new ArrayList<Cang>();
					data1.add(new Cang(R.drawable.bg, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					data1.add(new Cang(R.drawable.bg, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					data1.add(new Cang(R.drawable.bg, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					data1.add(new Cang(R.drawable.bg, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					data1.add(new Cang(R.drawable.bg, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					
					adapter1=new ShoppingAdapter(data1,ShoppingActivity.this);	
					LogUtil.d("他的位置id为什么 ", ""+data1.size());
					lv.setAdapter(adapter1);
			
				}

				if (arg2 == 2) {
					
					data1=new ArrayList<Cang>();
					data1.add(new Cang(R.drawable.ceshi_tu_01, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					data1.add(new Cang(R.drawable.ceshi_tu_01, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					data1.add(new Cang(R.drawable.ceshi_tu_01, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					data1.add(new Cang(R.drawable.ceshi_tu_01, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					data1.add(new Cang(R.drawable.ceshi_tu_01, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					adapter1=new ShoppingAdapter(data1,ShoppingActivity.this);	
					LogUtil.d("他的位置id为什么 ", ""+data1.size());
					lv.setAdapter(adapter1);
			
			
				}
				if (arg2 == 3) {
					data1=new ArrayList<Cang>();
					data1.add(new Cang(R.drawable.bg, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					data1.add(new Cang(R.drawable.bg, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					data1.add(new Cang(R.drawable.bg, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					data1.add(new Cang(R.drawable.bg, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					data1.add(new Cang(R.drawable.bg, "品名:"+"豹纹纯棉外套餐","编号:"+"12345678645", "详情:"+"这是一件收到的货款基本卡价格，三个磦思考代表处孤军奋战"));
					
					adapter1=new ShoppingAdapter(data1,ShoppingActivity.this);	
					LogUtil.d("他的位置id为什么 ", ""+data1.size());
					lv.setAdapter(adapter1);
				}

			}
			
		});
		
		
		
		
		
		
		
		
		
	}

}
