package com.vvage.futuretown.activity;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vvage.futuretown.Global;
import com.vvage.futuretown.R;
import com.vvage.futuretown.base.BaseView;
import com.vvage.futuretown.cmd.Apicmd;
import com.vvage.futuretown.model.askprice;
import com.vvage.futuretown.model.askprice.Reply;
import com.vvage.futuretown.model.askprice.UnReply;
import com.vvage.futuretown.model.replyPrice;
import com.vvage.futuretown.util.AsyncViewTask;
import com.vvage.futuretown.util.AsyncViewTask.PostExecute;
import com.vvage.futuretown.util.DoAsyncTask;
import com.vvage.futuretown.util.DoAsyncTask.DoTask;
import com.vvage.futuretown.util.DoAsyncTask.PostResult;

/*
 * 商家 处理客户询价
 */
public class UserEnquire extends BaseView {
	
	private ButtonTextView mEnquire, mReply;
	private ListView sendList;
	private ListView replyList;
	private int press=0;

	@Override
	protected View creatView(Context context) {
		// TODO Auto-generated method stub
		int visible = View.VISIBLE;
		setTitleParams(new TitleParams("返回", visible, null, createTitleView(), "刷新", View.VISIBLE));
		
		View layout = LayoutInflater.from(context).inflate(R.layout.usersxunjia, null);
		
		sendList = (ListView)layout.findViewById(R.id.my_enquire_send_list);
		replyList = (ListView)layout.findViewById(R.id.my_enquire_reply_list);
		
		new DoAsyncTask(UserEnquire.this, "正在加载...", new DoTask() {
			
			@Override
			public Object task() {
				// TODO Auto-generated method stub
//				商家询价接口，type=1是已经回复，不传或者type=0是未回复
				return Apicmd.getInstance().getaskprice(getApplicationContext(), press+"");
			}
		}, new DoAsyncTask.PostResult() {
			
			@Override
			public void result(Object result) {
				// TODO Auto-generated method stub
				if(result!=null){
					askprice ap = (askprice)result;
					if(ap.isSucceed()){
						if(press==0){
						sendList.setAdapter(new EnquireAdapter(ap.unReplyList, null));
						}else if(press==1){
						replyList.setAdapter(new EnquireAdapter(null, ap.replyList));
						}
					}
				}
			}
		},true,true);
		
		return layout;
	}

	@Override
	protected void onClickLeft() {
		// TODO Auto-generated method stub
//		switchTab(tagId, true);
		finish();
	}

	@Override
	protected void onClickRight() {
		// TODO Auto-generated method stub
		new DoAsyncTask(UserEnquire.this, "正在加载...", new DoTask() {
			
			@Override
			public Object task() {
				// TODO Auto-generated method stub
//				商家询价接口，type=1是已经回复，不传或者type=0是未回复
				return Apicmd.getInstance().getaskprice(getApplicationContext(), press+"");
			}
		}, new DoAsyncTask.PostResult() {
			
			@Override
			public void result(Object result) {
				// TODO Auto-generated method stub
				if(result!=null){
					askprice ap = (askprice)result;
					if(ap.isSucceed()){
						if(press==0){
							sendList.setAdapter(new EnquireAdapter(ap.unReplyList, null));
							}else if(press==1){
							replyList.setAdapter(new EnquireAdapter(null, ap.replyList));
							}
					}
				}
			}
		},true,true);
	}
	
	private class EnquireAdapter extends BaseAdapter{

		private List<UnReply> unrelist;
		private List<Reply> relist;
		
		public EnquireAdapter(List<UnReply> unrelist, List<Reply> relist){
			this.unrelist=  unrelist;
			this.relist=  relist;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			int count = 0;
			if(unrelist!=null&&relist==null){
				return unrelist.size(); 
			}
			if(relist!=null&&unrelist==null){
				return relist.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			if(unrelist!=null&&relist==null){
				return unrelist.get(arg0); 
			}
			if(relist!=null&&unrelist==null){
				return relist.get(arg0);
			}
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			if(arg1==null){
				arg1 = getItemView();
			}
			bindItemView(arg0, arg1);
			return arg1;
		}
		
		private View getItemView(){
			View view = LayoutInflater.from(getApplicationContext()).
					inflate(R.layout.users_enquire, null);
			TextView username = (TextView)view.findViewById(R.id.username);
			TextView userphone = (TextView)view.findViewById(R.id.userphone);
			TextView useraddress = (TextView)view.findViewById(R.id.useraddress);
			
			ImageView im = (ImageView)view.findViewById(R.id.imageView1);
			TextView productname = (TextView)view.findViewById(R.id.productname);
			TextView productprice = (TextView)view.findViewById(R.id.productprice);
			TextView delenquire_phone = (TextView)view.findViewById(R.id.delenquire_phone);
			TextView replyenquire = (TextView)view.findViewById(R.id.replyenquire);
			TextView yikoujia = (TextView)view.findViewById(R.id.yikoujia);
			TextView outtime = (TextView)view.findViewById(R.id.outtime);
			TextView note = (TextView)view.findViewById(R.id.note);
			
			LinearLayout yt = (LinearLayout)view.findViewById(R.id.yikoujia_outtime);
			
			if(press==0){
				delenquire_phone.setText("删除询价");
				yt.setVisibility(View.GONE);
			}else if(press==1){
				delenquire_phone.setText("拨打电话");
				replyenquire.setVisibility(View.GONE);
				yt.setVisibility(View.VISIBLE);
			}
			
			view.setTag(new ViewHolder(username, userphone, useraddress, 
					im, productname, productprice, delenquire_phone, 
					replyenquire, yikoujia, outtime, note));
			
			return view;
		}
		
		private void bindItemView(int position, View convertView){
			final ViewHolder tag = (ViewHolder)convertView.getTag();
			
			UnReply ur = null;
			Reply re = null;
			if(press==0){
				ur = unrelist.get(position);
				tag.im.setTag(ur.url);
				new AsyncViewTask(Global.WIDTH_SCREEN/3, new PostExecute() {
					
					@Override
					public void result(boolean result) {
						// TODO Auto-generated method stub
						
					}
				}).execute(tag.im);
				tag.username.setText("用户名："+ur.username);
				tag.userphone.setText("电  话："+ur.phone);
				tag.useraddress.setText("送货地址：");
				tag.productname.setText("品名："+ur.name);
				tag.productprice.setText("标价：￥"+ur.nowprice);
				tag.note.setText("附言："+ur.note);
				tag.delenquire_phone.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						Toast.makeText(getApplicationContext(), "回复成功", Toast.LENGTH_SHORT).show();
					}
				});
				final String id = ur.pid;
				tag.replyenquire.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AlertDialog.Builder dialog=new AlertDialog.Builder(UserEnquire.this);
				    	  dialog.setTitle("回复询价");
				    	  LinearLayout layout = new LinearLayout(UserEnquire.this);
				    	  layout.setOrientation(LinearLayout.VERTICAL);
				    	  layout.setPadding(8, 8, 8, 8);
				    	  
				    	  final EditText price = new EditText(UserEnquire.this);
//				    	  price.setBackgroundDrawa/ble(null);
				    	  price.setHint("请输入回复价格");
				    	  price.setInputType(InputType.TYPE_CLASS_PHONE);
				    	  
				    	  final EditText n = new EditText(UserEnquire.this);
//				    	  n.setBackgroundDrawable(null);
				    	  n.setHint("请输入回复内容");
				    	  layout.addView(price);
				    	  layout.addView(n);
				    	  dialog.setView(layout);
				    	  
				    	  dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								new DoAsyncTask(UserEnquire.this, "正在回复...", new DoTask() {
									
									@Override
									public Object task() {
										// TODO Auto-generated method stub
										return Apicmd.getInstance().getreplyPrice(getApplicationContext(), id,
												price.getText().toString(),n.getText().toString());
									}
								}, new PostResult() {
									
									@Override
									public void result(Object result) {
										// TODO Auto-generated method stub
										if(result!=null&&((replyPrice)result).isSucceed()){
											Toast.makeText(getApplicationContext(), "回复成功", Toast.LENGTH_SHORT).show();
										}else{
											Toast.makeText(getApplicationContext(), "回复失败", Toast.LENGTH_SHORT).show();
										}
									}
								}, true, true);
							}
						}).setPositiveButton("取消", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
				    	dialog.show();
						
					}
				});
			}else if(press==1){
				re = relist.get(position);
				tag.im.setTag(re.url);
				new AsyncViewTask(Global.WIDTH_SCREEN/3, new PostExecute() {
					
					@Override
					public void result(boolean result) {
						// TODO Auto-generated method stub
						
					}
				}).execute(tag.im);
				
				tag.username.setText("用户名："+re.username);
				tag.userphone.setText("电  话："+re.phone);
				tag.useraddress.setText("送货地址：");
				tag.productname.setText("品名："+re.name);
				tag.productprice.setText("标价：￥"+re.nowprice);
				tag.yikoujia.setText("￥"+re.replyprice);
				tag.outtime.setText(re.remaintime);
				tag.note.setText("附言："+re.note);
				final Reply rePhone = re;
				tag.delenquire_phone.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Global.call(UserEnquire.this, rePhone.phone);
					}
				});
			}
		}
		
		private class ViewHolder{
			public TextView username;
			public TextView userphone;
			public TextView useraddress;
			
			public ImageView im;
			public TextView productname;
			public TextView productprice;
			public TextView delenquire_phone;
			public TextView replyenquire;
			public TextView yikoujia;
			public TextView outtime;
			public TextView note;
			
			public ViewHolder(TextView username, TextView userphone, TextView useraddress,
					ImageView im, TextView productname, TextView productprice, TextView delenquire_phone,
					TextView replyenquire, TextView yikoujia, TextView outtime, TextView note){
				this.username = username;
				this.userphone = userphone;
				this.useraddress = useraddress;
				
				this.im = im;
				
				this.productname = productname;
				this.productprice = productprice;
				this.delenquire_phone = delenquire_phone;
				this.replyenquire = replyenquire;
				this.yikoujia = yikoujia;
				this.outtime = outtime;
				this.note = note;
			}
		}
		
		
	}


	private View createTitleView(){
		LinearLayout layout = new LinearLayout(this);
		
		mEnquire = new ButtonTextView(this);
		mEnquire.setBackgroundResource(R.drawable.btn_title_l_press);
		mEnquire.setText("客户询价");
		
		mEnquire.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switchButton(0);
			}
		});
		
		mReply = new ButtonTextView(this);
		mReply.setBackgroundResource(R.drawable.btn_title_r);
		mReply.setText("已复询价");
		mReply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switchButton(1);
			}
		});
		
		layout.addView(mEnquire);
		layout.addView(mReply);
		return layout;
	}
	
	private void switchButton(int type){
		press = type;
		if(type==0){
			mEnquire.setBackgroundResource(R.drawable.btn_title_l_press);
			mReply.setBackgroundResource(R.drawable.btn_title_r);
			sendList.setVisibility(View.VISIBLE);
			replyList.setVisibility(View.GONE);
		}else if(type ==1){
			mEnquire.setBackgroundResource(R.drawable.btn_title_l);
			mReply.setBackgroundResource(R.drawable.btn_title_r_press);
			sendList.setVisibility(View.GONE);
			replyList.setVisibility(View.VISIBLE);
		}
	}
	
}
