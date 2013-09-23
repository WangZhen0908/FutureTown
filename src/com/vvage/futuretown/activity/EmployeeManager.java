package com.vvage.futuretown.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vvage.futuretown.Global;
import com.vvage.futuretown.R;
import com.vvage.futuretown.base.BaseView;
import com.vvage.futuretown.cmd.Apicmd;
import com.vvage.futuretown.model.AddsubAccount;
import com.vvage.futuretown.model.Employee;
import com.vvage.futuretown.model.doSubAccount;
import com.vvage.futuretown.util.DoAsyncTask;
import com.vvage.futuretown.util.DoAsyncTask.DoTask;
import com.vvage.futuretown.util.StringUtil;

public class EmployeeManager extends BaseView {
	private List<Employee> data;
	private BaseAdapter adapter;
//	private ListView lv;
	
	private LinearLayout em;
	private List<View> item = new ArrayList<View>();
	
	//添加新员工
	private EditText addItemPhone;
	private EditText em_yan_ma;
	private EditText addItempass;
	private Button addItemsend;
	private Button addItembtn;

	//转让店铺
	private EditText em_phone1;
	private EditText em_yan_ma1;
	private EditText em_mi_ma1;
	private Button em_send_ma1;
	private Button em_sure_btn1;
	
	//测试数据结构
	private static List<TestData> ld = new ArrayList<EmployeeManager.TestData>();
	
	
	
	@Override
	protected View creatView(Context context) {
		// TODO Auto-generated method stub
		setTitleParams(new TitleParams("返回", View.VISIBLE, "员工管理",
				null, "", View.GONE));
		View view = LayoutInflater.from(context).inflate(R.layout.employee_new, null);
		
		TestData td1 = new TestData(ld.size()+"这是测试数据", "12394837483");
		TestData td2 = new TestData(ld.size()+"这是测试数据", "12394837483");
		TestData td3 = new TestData(ld.size()+"这是测试数据", "12394837483");
		
		View v1 = newItemView(td1);
		View v2 = newItemView(td2);
		View v3 = newItemView(td3);
		
		item.add(v1);
		item.add(v2);
		item.add(v3);
		
		em = (LinearLayout)view.findViewById(R.id.employee_list);
		int count = item.size();
		for(int i=0;i<count;++i){
			em.addView(item.get(i));
		}
		
//		lv = (ListView)view.findViewById(R.id.em_lv);
		//添加新员工
		addItemPhone = (EditText)view.findViewById(R.id.em_phone);
		em_yan_ma = (EditText)view.findViewById(R.id.em_yan_ma);
		addItempass = (EditText)view.findViewById(R.id.em_mi_ma);
		addItemsend = (Button)view.findViewById(R.id.em_send_ma);
		addItembtn = (Button)view.findViewById(R.id.em_sure_btn);
		
		addItemsend.setOnClickListener(myOnClickListener);
		addItembtn.setOnClickListener(myOnClickListener);

		//转让店铺
		em_phone1 = (EditText)view.findViewById(R.id.em_phone1);
		em_yan_ma1 = (EditText)view.findViewById(R.id.em_yan_ma1);
		em_mi_ma1 = (EditText)view.findViewById(R.id.em_mi_ma1);
		em_send_ma1 = (Button)view.findViewById(R.id.em_send_ma1);
		em_sure_btn1 = (Button)view.findViewById(R.id.em_sure_btn1);
		
		em_send_ma1.setOnClickListener(myOnClickListener);
		em_sure_btn1.setOnClickListener(myOnClickListener);
		
//		setListViewHeightBasedOnChildren(lv);
		
		return view;
		
	}

	private OnClickListener myOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			//添加新员工  发送验证码
			case R.id.em_send_ma:{
				sendVcode(addItemPhone.getText().toString(), "2");
				break;
			}
			case R.id.em_sure_btn:{
				final String phone = addItemPhone.getText().toString();
				if(!StringUtil.isMobileNumber(phone)){
					Toast.makeText(getApplicationContext(), "手机号码无效,请从新输入", Toast.LENGTH_SHORT).show();
					return;
				}
				final String v = em_yan_ma.getText().toString();
				if(!StringUtil.isValidStr(v)){
					Toast.makeText(getApplicationContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				final String pass = addItempass.getText().toString();
				if(!StringUtil.isValidStr(pass)){
					Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				
				DoAsyncTask task = new DoAsyncTask(EmployeeManager.this, "正在提交...", new DoTask() {
					
					@Override
					public Object task() {
						// TODO Auto-generated method stub
						return Apicmd.getInstance().getAddsubAccount(EmployeeManager.this, v, phone, pass, "");
					}
				}, new DoAsyncTask.PostResult(){

					@Override
					public void result(Object result) {
						// TODO Auto-generated method stub
						if(result!=null){
							AddsubAccount asa = (AddsubAccount)result;
							if(asa.isSucceed()){
								TestData td = new TestData(ld.size()+"这是测试数据", addItemPhone.getText().toString());
								item.add(newItemView(td));
							}
						}
					}
					
				}, true, true);
				break;
			}
			//转让店铺  发送验证码
			case R.id.em_send_ma1:{
				sendVcode(em_phone1.getText().toString(), "3");
				break;
			}
			case R.id.em_sure_btn1:{
				final String phone = em_phone1.getText().toString();
				if(!StringUtil.isMobileNumber(phone)){
					Toast.makeText(getApplicationContext(), "手机号码无效,请从新输入", Toast.LENGTH_SHORT).show();
					return;
				}
				final String v = em_yan_ma1.getText().toString();
				if(!StringUtil.isValidStr(v)){
					Toast.makeText(getApplicationContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				final String pass = em_mi_ma1.getText().toString();
				if(!StringUtil.isValidStr(pass)){
					Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				
				DoAsyncTask task = new DoAsyncTask(EmployeeManager.this, "正在提交...", new DoTask() {
					
					@Override
					public Object task() {
						// TODO Auto-generated method stub
						return Apicmd.getInstance().gettranse_seller(EmployeeManager.this, phone, v);
					}
				}, new DoAsyncTask.PostResult(){

					@Override
					public void result(Object result) {
						// TODO Auto-generated method stub
						if(result!=null){
							AddsubAccount asa = (AddsubAccount)result;
							if(asa.isSucceed()){
								Toast.makeText(getApplicationContext(), "转让成功，只是测试而已", Toast.LENGTH_SHORT).show();
							}
						}
					}
					
				}, true, true);
				
				break;
			}
			default:
				break;
			}
		}
	};


	@Override
	protected void onClickLeft() {
		// TODO Auto-generated method stub
		finish();
	}


	@Override
	protected void onClickRight() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	private View newItemView(final TestData td){
		final LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setBackgroundResource(R.drawable.center_bg_9);
		LinearLayout.LayoutParams layoutLPS = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		layoutLPS.bottomMargin = 5;
		layout.setLayoutParams(layoutLPS);
		layout.setPadding(5, 5, 5, 5);
//		layout.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout left = new LinearLayout(getApplicationContext());
		left.setOrientation(LinearLayout.VERTICAL);
		
		left.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
		left.setPadding(10, 5, 5, 5);
		left.setGravity(Gravity.CENTER_VERTICAL);
		
		LinearLayout.LayoutParams tvLPS = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tvLPS.gravity=Gravity.CENTER_VERTICAL|Gravity.LEFT;
		
		TextView name = new TextView(getApplicationContext());
		name.setTextColor(Color.BLACK);
		name.setLayoutParams(tvLPS);
		name.setText("姓名："+td.name);
		
		
		TextView phone = new TextView(getApplicationContext());
		phone.setTextColor(Color.BLACK);
		phone.setLayoutParams(tvLPS);
		phone.setEllipsize(TruncateAt.MARQUEE);
		phone.setText("号码："+td.phone);
		
		left.addView(name);
		left.addView(phone);
		
		LinearLayout.LayoutParams delLPS = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		delLPS.gravity=Gravity.RIGHT|Gravity.CENTER_VERTICAL;
		delLPS.rightMargin=10;
		
		Button del = new Button(getApplicationContext());
		del.setTextColor(Color.BLACK);
		del.setPadding(5, 5, 5, 5);
		del.setLayoutParams(delLPS);
		del.setText("删除此号码");
		del.setTextSize(TypedValue.COMPLEX_UNIT_SP, 
				Global.TEXT_SIZE_LV3);
		del.setBackgroundDrawable(Global.newSelectorFromRes(getApplicationContext(), 
				R.drawable.center_bg_9, R.drawable.center_bg_press_9, R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		
		layout.addView(left);
		layout.addView(del);
		
		layout.setTag(new ViewHolder(name, phone, del));
		
		
		
		
		del.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DoAsyncTask task = new DoAsyncTask(EmployeeManager.this, "正在提交...", new DoTask() {
					
					@Override
					public Object task() {
						// TODO Auto-generated method stub
						return Apicmd.getInstance().getdoSubAccount(EmployeeManager.this, "");
					}
				}, new DoAsyncTask.PostResult(){

					@Override
					public void result(Object result) {
						// TODO Auto-generated method stub
						if(result!=null){
							doSubAccount dsa = (doSubAccount)result;
							if(dsa.isSucceed()){
								int count = item.size();
								for(int i=0;i<count;++i){
									if(item.get(i).getTag()==layout.getTag()){
										em.removeView(item.get(i));
										item.remove(i);
										break;
									}
								}
							}
						}
					}
					
				}, true, true);
			}
		});
		
		return layout;
	}
	
	
	private class ViewHolder{
		public TextView name;
		public TextView phone;
		public TextView del;
		ViewHolder(TextView name, TextView phone, TextView del){
			this.name = name;
			this.phone=phone;
			this.del=del;
		}
	}
	
	
	
	
//	private class ItemAdapter extends BaseAdapter{
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return ld.size();
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			// TODO Auto-generated method stub
//			return ld.get(arg0);
//		}
//
//		@Override
//		public long getItemId(int arg0) {
//			// TODO Auto-generated method stub
//			return arg0;
//		}
//
//		@Override
//		public View getView(int arg0, View arg1, ViewGroup arg2) {
//			// TODO Auto-generated method stub
//			if(arg1==null){
//				arg1 = newItemView();
//			}
//			bindView(arg0, arg1);
//			return arg1;
//		}
//		
//		private View newItemView(){
//			LinearLayout layout = new LinearLayout(getApplicationContext());
//			layout.setBackgroundResource(R.drawable.pai_edit_03);
//			layout.setLayoutParams(new AbsListView.LayoutParams(
//					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
//			layout.setPadding(5, 5, 5, 5);
//			
//			LinearLayout left = new LinearLayout(getApplicationContext());
//			left.setOrientation(LinearLayout.VERTICAL);
//			
//			LinearLayout.LayoutParams tvLPS = new LinearLayout.LayoutParams(
//					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//			tvLPS.gravity=Gravity.CENTER;
//			
//			TextView name = new TextView(getApplicationContext());
//			name.setTextColor(color.background_dark);
//			name.setLayoutParams(tvLPS);
//			TextView phone = new TextView(getApplicationContext());
//			phone.setTextColor(color.background_dark);
//			phone.setLayoutParams(tvLPS);
//			phone.setEllipsize(TruncateAt.MARQUEE);
//			
//			left.addView(name);
//			left.addView(phone);
//			
//			TextView del = new TextView(getApplicationContext());
//			del.setTextColor(color.background_light);
//			del.setPadding(5, 5, 5, 5);
//			del.setLayoutParams(tvLPS);
//			del.setText("删除此号码");
//			del.setTextSize(TypedValue.COMPLEX_UNIT_SP, 
//					Global.TEXT_SIZE_LV3);
//			del.setBackgroundDrawable(Global.newSelectorFromRes(getApplicationContext(), 
//					R.drawable.pai_edit_03, R.drawable.bg_orange, R.drawable.bg_orange, R.drawable.pai_edit_03));
//			
//			layout.addView(left);
//			layout.addView(del);
//			
//			layout.setTag(new ViewHolder(name, phone, del));
//			
//			return layout;
//		}
//		
//		private class ViewHolder{
//			public TextView name;
//			public TextView phone;
//			public TextView del;
//			ViewHolder(TextView name, TextView phone, TextView del){
//				this.name = name;
//				this.phone=phone;
//				this.del=del;
//			}
//		}
//		
//		private void bindView(int position, View converView){
//			ViewHolder vh = (ViewHolder)converView.getTag();
//			final TestData td = ld.get(position);
//			vh.name.setText("姓名："+td.name);
//			vh.phone.setText("号码："+td.number);
//			
//			vh.del.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//					DoAsyncTask task = new DoAsyncTask(getApplicationContext(), "正在提交...", new DoTask() {
//						
//						@Override
//						public Object task() {
//							// TODO Auto-generated method stub
//							return Apicmd.getInstance().getdoSubAccount(getApplicationContext(), "");
//						}
//					}, new DoAsyncTask.PostResult(){
//
//						@Override
//						public void result(Object result) {
//							// TODO Auto-generated method stub
//							if(result!=null){
//								doSubAccount dsa = (doSubAccount)result;
//								if(dsa.isSucceed()){
//									ld.remove(td);
//									ItemAdapter item = new ItemAdapter();
//									lv.setAdapter(item);
//								}
//							}
//						}
//						
//					}, true, true);
//				}
//			});
//		}
//		
//	}
	
	private class TestData{
		public int sn;
		public String name;
		public String phone;
		
		TestData(String name, String phone){
			this.name = name;
			this.phone = phone;
		}
	}
	
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		listView.setLayoutParams(params);
	}
	
	
//	private View createItem(final String str){
//		LinearLayout item = new LinearLayout(getApplicationContext());
//		item.setGravity(Gravity.CENTER_VERTICAL);
//		item.setLayoutParams(new LinearLayout.LayoutParams(
//				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//		
//		TextView tv = new TextView(getApplicationContext());
//		tv.setGravity(Gravity.CENTER);
//		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, Global.TEXT_SIZE_LV4);
//		tv.setText(str);
//		
//		EditText et = new EditText(getApplicationContext());
//		et.setBackgroundResource(R.drawable.pai_edit_03);
//		
//		item.addView(tv);
//		item.addView(et, new LinearLayout.LayoutParams(
//				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1));
//		
//		return item;
//	}
}
