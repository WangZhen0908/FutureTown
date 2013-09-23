package com.vvage.futuretown.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vvage.futuretown.R;
import com.vvage.futuretown.model.Text;

public class TextAdapter extends BaseAdapter{
	private List<Text>data;
	private Context context;
	
	

	public TextAdapter(List<Text> data, Context context) {
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
		
		View view=LayoutInflater.from(context).inflate(R.layout.sh_gv, null);
		
		
		TextView tv=(TextView) view.findViewById(R.id.sh_gv_tv);
		
		Text item=data.get(arg0);
		tv.setText(item.getTv());
		
		return view;
	}

}
