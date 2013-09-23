package com.vvage.futuretown.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vvage.futuretown.R;
import com.vvage.futuretown.model.Equire;

public class EquireAdapter extends BaseAdapter{
	private List<Equire>data;
	private Context context;
	
	public EquireAdapter(List<Equire> data, Context context) {
		super();
		this.data = data;
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View view=LayoutInflater.from(context).inflate(R.layout.equiry_lv, null);
		
		ImageView xiao=(ImageView) view.findViewById(R.id.e_lv_im);
		ImageView da=(ImageView) view.findViewById(R.id.e_lv_im1);
		
		TextView dian=(TextView) view.findViewById(R.id.e_lv_name);
		TextView bianhao=(TextView) view.findViewById(R.id.e_lv_bianhao);
		TextView add=(TextView) view.findViewById(R.id.e_lv_add);
		TextView pin=(TextView) view.findViewById(R.id.e_lv_goodsname);
		TextView price=(TextView) view.findViewById(R.id.e_lv_price);
		
		Equire item=data.get(position);
		
		xiao.setImageResource(item.getXiaotu());
		da.setImageResource(item.getDatu());
		dian.setText(item.getDianname());
		bianhao.setText(item.getBianhao());		
		add.setText(item.getAdd());
		pin.setText(item.getPinming());
		price.setText(item.getPrice());
		
		return view;
	}

}
