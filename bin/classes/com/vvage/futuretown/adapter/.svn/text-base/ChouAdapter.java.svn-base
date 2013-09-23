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
import com.vvage.futuretown.model.ChouJiang;

public class ChouAdapter extends BaseAdapter{
	
	private List<ChouJiang>data;
	private Context context;
	
	
	

	public ChouAdapter(List<ChouJiang> data, Context context) {
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
		
		View view=LayoutInflater.from(context).inflate(R.layout.chou_lv,null);
		
		ImageView im=(ImageView) view.findViewById(R.id.chou_tu);
		TextView pinming=(TextView) view.findViewById(R.id.chou_pinming);
		TextView bianhao=(TextView) view.findViewById(R.id.chou_bianhao);
		TextView xiangqing=(TextView) view.findViewById(R.id.chou_xiangqing);
		
		
		ChouJiang item=data.get(position);
		im.setImageResource(item.getIm());
		pinming.setText(item.getPinming());
		bianhao.setText(item.getBianhao());
		xiangqing.setText(item.getXiangqing());
		return view;
	}


	

}
