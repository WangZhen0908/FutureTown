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
import com.vvage.futuretown.model.MyChoujiang;

public class MychoujiangAdapter extends BaseAdapter {
	private List<MyChoujiang> data;
	private Context context;

	public MychoujiangAdapter(List<MyChoujiang> data, Context context) {
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
		View view = LayoutInflater.from(context).inflate(R.layout.cj_lv, null);
		ImageView im = (ImageView) view.findViewById(R.id.cj_tu);
		TextView pinming = (TextView) view.findViewById(R.id.cj_pinming);
		TextView bianhao = (TextView) view.findViewById(R.id.cj_bianhao);
		TextView xiangqing = (TextView) view.findViewById(R.id.cj_xiangqing);
		TextView jiang = (TextView) view.findViewById(R.id.cj_jiang);

		MyChoujiang item = data.get(arg0);
		im.setImageResource(item.getIm());
		pinming.setText(item.getName());
		bianhao.setText(item.getPinname());
		xiangqing.setText(item.getXiangqing());
		jiang.setText(item.getKaijiang());

		return view;
	}
}
