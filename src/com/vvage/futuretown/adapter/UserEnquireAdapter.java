//package com.vvage.futuretown.adapter;
//
//import java.util.List;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.vvage.futuretown.R;
//import com.vvage.futuretown.model.Order;
//
//public class UserEnquireAdapter extends BaseAdapter {
//	private List<Order> data;
//	private Context context;
//
//	public UserEnquireAdapter(List<Order> data, Context context) {
//		super();
//		this.data = data;
//		this.context = context;
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return data.size();
//	}
//
//	@Override
//	public Object getItem(int arg0) {
//		// TODO Auto-generated method stub
//		return data.get(arg0);
//	}
//
//	@Override
//	public long getItemId(int arg0) {
//		// TODO Auto-generated method stub
//		return arg0;
//	}
//
//	@Override
//	public View getView(int arg0, View arg1, ViewGroup arg2) {
//		// TODO Auto-generated method stub
//
//		View view = LayoutInflater.from(context).inflate(R.layout.ux_lv, null);
//
//		TextView tv3 = (TextView) view.findViewById(R.id.ux_lv_goodsname);
//		TextView tv4 = (TextView) view.findViewById(R.id.ux_lv_price);
//
//		ImageView im = (ImageView) view.findViewById(R.id.ux_lv_im);
//		Order item = data.get(arg0);
//
//		tv3.setText(item.getTv());
//		tv4.setText(item.getTv2());
//		im.setImageResource(item.getIm());
//
//		return view;
//	}
//
//}
