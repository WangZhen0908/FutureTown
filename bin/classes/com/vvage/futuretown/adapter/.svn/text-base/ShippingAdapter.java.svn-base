package com.vvage.futuretown.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vvage.futuretown.R;
import com.vvage.futuretown.model.Shipping;

public class ShippingAdapter extends BaseAdapter {
	private List<Shipping> data;
	private Context context;

	public ShippingAdapter(List<Shipping> data, Context context) {
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
		View view = LayoutInflater.from(context)
				.inflate(R.layout.ship_lv, null);
		TextView id = (TextView) view.findViewById(R.id.ship_id);
		TextView name = (TextView) view.findViewById(R.id.ship_name);
		TextView phone = (TextView) view.findViewById(R.id.ship_phone);
		TextView add = (TextView) view.findViewById(R.id.ship_add);

		Shipping item = data.get(position);

		id.setText(item.getId());
		name.setText(item.getName());
		phone.setText(item.getPhone());
		add.setText(item.getAdd());

		return view;
	}

}
