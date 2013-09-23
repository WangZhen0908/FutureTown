package com.vvage.futuretown.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.vvage.futuretown.R;
import com.vvage.futuretown.model.Image;

public class Image1Adapter extends BaseAdapter{
	private List<Image>data;
	private Context context;
	
	public Image1Adapter(List<Image> data, Context context) {
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
		
		View view=LayoutInflater.from(context).inflate(R.layout.sj_gallery,null);
		ImageView im=(ImageView) view.findViewById(R.id.sj_GL_im);
		
		Image item=data.get(arg0);
		
		im.setImageResource(item.getIm());
	
		im.setScaleType(ImageView.ScaleType.FIT_XY);
		
		
		
		
		return view;
	}

}
