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
import com.vvage.futuretown.model.HuiFu;

public class HuifuAdapter extends BaseAdapter{
	private List<HuiFu>data;
	private Context context;
	

	public HuifuAdapter(List<HuiFu> data, Context context) {
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
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		View view=LayoutInflater.from(context).inflate(R.layout.hfe_lv,null);
		TextView tv=(TextView) view.findViewById(R.id.hf_lv_user);
		
		TextView tv1=(TextView) view.findViewById(R.id.hf_lv_phone);
		TextView tv2=(TextView) view.findViewById(R.id.hf_lv_add);
		
		TextView tv3=(TextView) view.findViewById(R.id.hf_lv_goodsname);
		TextView tv4=(TextView) view.findViewById(R.id.hf_lv_price);
		TextView tv5=(TextView) view.findViewById(R.id.hf_price);
		TextView tv6=(TextView) view.findViewById(R.id.hf_time);
		
		ImageView im=(ImageView) view.findViewById(R.id.hf_lv_im);
		
		HuiFu item=data.get(arg0);
		
		tv.setText(item.getUserName());
		tv1.setText(item.getPhone());
		tv2.setText(item.getAdd());
		tv3.setText(item.getPinName());
		tv4.setText(item.getBiaojia());
		tv5.setText(item.getFuPrice());
		tv6.setText(item.getTime());
		im.setImageResource(item.getIm());
		
		
		
		
		
		return view;
	
	
	}

}
