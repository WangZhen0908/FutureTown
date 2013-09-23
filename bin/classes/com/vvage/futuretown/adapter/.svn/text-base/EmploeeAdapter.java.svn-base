package com.vvage.futuretown.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vvage.futuretown.R;
import com.vvage.futuretown.model.Employee;

public class EmploeeAdapter extends BaseAdapter{
	private List<Employee>data;
	private Context context;
	
	
	
	public EmploeeAdapter(List<Employee> data, Context context) {
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
		View view=LayoutInflater.from(context).inflate(R.layout.em_lv, null);
		TextView t1=(TextView) view.findViewById(R.id.em_lv_name);
		TextView t2=(TextView) view.findViewById(R.id.em_lv_phone);
		Employee item=data.get(arg0);
		t1.setText(item.getName());
		t2.setText(item.getPhone());
		
		
		
		
		return view;
	}
	

}
