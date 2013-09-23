package com.vvage.futuretown.fragment;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.vvage.futuretown.BaseFragment;
import com.vvage.futuretown.Global;
import com.vvage.futuretown.HomeActivity;
import com.vvage.futuretown.R;
import com.vvage.futuretown.activity.EmployeeManager;
import com.vvage.futuretown.activity.LoginListener;
import com.vvage.futuretown.activity.MyEquire;
import com.vvage.futuretown.activity.OrderManage;
import com.vvage.futuretown.activity.ShippingActivity;
import com.vvage.futuretown.activity.UserEnquire;
import com.vvage.futuretown.activity.UserInfoActivity;
import com.vvage.futuretown.activity.WeiOrderActivity;
import com.vvage.futuretown.cmd.Apicmd;
import com.vvage.futuretown.model.LoginParse;
import com.vvage.futuretown.model.Order;
import com.vvage.futuretown.model.RegisterParse;
import com.vvage.futuretown.model.SendVCodeParse;
import com.vvage.futuretown.model.User;
import com.vvage.futuretown.util.DoAsyncTask;
import com.vvage.futuretown.util.DoAsyncTask.DoTask;
import com.vvage.futuretown.util.LogUtil;
import com.vvage.futuretown.util.StringUtil;

public class UserFragment extends BaseFragment {
	
	private final String PHONE_NUMBER="number";
	private final String PASSWORD="pass";
	private final String UID="uid";
	private final String ISREMEMBER = "isRemember";
	
	private Button loginBtn, findPassBtn, registerBtn;
	private EditText userNameEdit, passwordEdit;
//	private Button leftImgBtn, rightImgBtn, quitImgBtn;// 标题栏，左侧首页按钮、右侧注销
	private Button leftImgBtn, rightImgBtn;// 标题栏，左侧首页按钮、右侧注销
	private TextView centerText;
	private CheckBox freeLoginCheckBox;
	private SharedPreferences sharePre;
	private Editor edit;
	private LayoutInflater inflater;
	private LinearLayout userLinear;
	private View loginView;// 默认是登陆界面
	private Button sendValidationCodeBtn;// 发送验证码
	private EditText phoneNumEdit, validationEdit, registerpasswordEdit;// 手机号EditText
	private Button registerConfirmBtn;
	private RelativeLayout myOrderBtn, myEnquiryBtn, myFavorBtn, raffleBtn,
			publishMsgBtn, editPersonalInfoBtn, sendMsgDoneBtn,
			goodsDeliverAddressBtn;
	private TextView regiterStoreBtn;

	private Button getVCodeSmsBtn, updatePassBtn;// 更新密码
	private EditText phoneSMSEdit, verificationCodeEdit, passwordNewEdit1, passwordNewEdit2;
	
	//商家-用户中心
	private RelativeLayout handling_customer_orders, handling_customer_enquiry,release_goods,
	edit_business_information, handle_send_goods_management, staff_management,
	my_order, my_enquiry, my_favor, shipping_address, my_raffle;
	
//	private MyOnTouchListener myOnTouchListener = new MyOnTouchListener();
//	protected final static int MSG_REGISTER = 0x111; // 没有网络连接
//	protected final static int MSG_GET_VCODE = 0x112; // 没有网络连接
	
//	public static boolean isLogin;
	
	private ScrollView personalCenter;
	private ScrollView merchantcenter;
	private LinearLayout findPassword;
	private LinearLayout user_register;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		HashMap<String, LoginListener> l = new HashMap<String, LoginListener>();
		l.put("user", mLoginListener);
		HomeActivity.setLoginListener(l);

	}

	private LoginListener mLoginListener = new LoginListener() {

		@Override
		public void Login(Class<?> cls) {
			// TODO Auto-generated method stub
			//商家注册成功
			if(User.mAction==3){
				personalCenter.setVisibility(View.GONE);
				merchantcenter.setVisibility(View.VISIBLE);
				centerText.setText("商家个人中心");
			}
//			if(identify>0){
//				DoAsyncTask task = new DoAsyncTask(getActivity(), "正在登陆...",
//						new DoTask() {
//							@Override
//							public Object task() {
//								// TODO Auto-generated method stub
//								return Apicmd.getInstance().Login(getActivity(), lastMobile, pass);
//							}
//						}, new DoAsyncTask.PostResult() {
//							@Override
//							public void result(Object result) {
//								// TODO Auto-generated method stub
//								if (result != null) {
//									LoginParse log = (LoginParse) result;
//									if (log.isSucceed()) {
//										Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();
//										// preparePersonCenterView(loginView);
//										personalCenter.setVisibility(View.VISIBLE);
//										userLinear.setVisibility(View.GONE);
//										centerText.setText("个人中心");
//										rightImgBtn.setVisibility(View.VISIBLE);
//										
//										if (freeLoginCheckBox.isChecked()) {
//											// 如果选中七天免登陆
//											SharedPreferencesHelper sph = SharedPreferencesHelper.getSharePreHelper();
//											sph.setSharedPreHelper(getActivity(),SharedPreferencesHelper.MAIN);
//											sph.putString(PHONE_NUMBER, lastMobile);
//											
//											sph.setSharedPreHelper(getActivity(),StringUtil.getMD5_16(lastMobile));
//											sph.putString(PASSWORD, pass);
//											sph.putString(PASSWORD, pass);
//											sph.putBoolean(ISREMEMBER, true);
//										}
//									}else {
//										Toast.makeText(getActivity(), "请稍后尝试", Toast.LENGTH_SHORT).show();
//									}
//								}
//							}
//						}, true, true);
//			}
			
//			sharePre = getActivity().getSharedPreferences("loginStatus",
//					Context.MODE_PRIVATE);
//			edit = sharePre.edit();
//			userNameEdit.setText(sharePre.getString("userName", ""));
//			if(!sharePre.getBoolean("loginStatus", false)){
//				freeLoginCheckBox.setChecked(false);
//			}else{
//				passwordEdit.setText(sharePre.getString("password", ""));
//				freeLoginCheckBox.setChecked(true);
//				AsyncTask<Object, Object, LoginParse> asyncTask = new AsyncTask<Object, Object, LoginParse>() {
//
//					@Override
//					protected LoginParse doInBackground(
//							Object... params) {
//						// TODO Auto-generated method stub
//						return Apicmd.getInstance().Login(
//								getActivity(), sharePre.getString("userName", ""), sharePre.getString("password", ""));
//
//					}
//
//					protected void onPostExecute(LoginParse result) {
//						// TODO Auto-generated method stub
//						if (result != null) {
//							// 注意：此处需要记录获取用户标记，以区别是个人用户还是商家，此处只演示个人用户中心显示
//							userLinear.removeAllViews();
//							View personView = inflater.inflate(
//									R.layout.person_center, null);
//							prepareHeaderView(personView);
//							preparePersonCenterView(personView);
//							preparePersonCenterData();
//							userLinear.addView(personView);
//						}
//					}
//
//				};
//				asyncTask.execute(new Object[] {});
//			}
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		super.onCreateView(inflater, container, savedInstanceState);
		this.inflater = inflater;
		loginView = inflater.inflate(R.layout.fragment_login, null);
		
		userLinear = (LinearLayout) loginView.findViewById(R.id.userLinear);
		personalCenter = (ScrollView)loginView.findViewById(R.id.person_center);
		merchantcenter = (ScrollView)loginView.findViewById(R.id.merchant_center);
		findPassword = (LinearLayout)loginView.findViewById(R.id.find_password);
		user_register = (LinearLayout)loginView.findViewById(R.id.user_register);
		
		prepareHeaderView(loginView);
		prepareLoginView(loginView);
		
		preparePersonCenterView(loginView);
		prepareMerchantCenterView(loginView);
		prepareFindPassView(loginView);
		prepareRegisterView(loginView);
		
		prepareLoginData();
		return loginView;
	}

	private void preparePersonCenterData() {
		// TODO Auto-generated method stub
		sharePre = getActivity().getSharedPreferences("loginStatus",Context.MODE_PRIVATE);
		edit = sharePre.edit();
		// 用户中心界面，此处初始化数据
	}

	private void prepareLoginData() {
		// TODO Auto-generated method stub
		SharedPreferencesHelper sph = SharedPreferencesHelper.getSharePreHelper();
		sph.setSharedPreHelper(getActivity(), SharedPreferencesHelper.MAIN);
		
		final String mobile = new String(StringUtil.Base64Decode(sph.getString(PHONE_NUMBER, "")));
		LogUtil.d("User", PHONE_NUMBER+" = "+mobile);
		
		boolean isrem = false;
		String p="";
		if(StringUtil.isMobileNumber(mobile)){
			isrem = sph.getBoolean(ISREMEMBER, false);
			freeLoginCheckBox.setChecked(isrem);
			p= sph.getString(PASSWORD, "");
			userNameEdit.setText(mobile);
			
			if(isrem){
				passwordEdit.setText(p);
			}
		}
		final String pass = p;
		LogUtil.d("User", "pass = "+pass);
		
		
//		if(sharePre.getBoolean("loginStatus", false)){
//			freeLoginCheckBox.setChecked(false);
//		}else{
//			passwordEdit.setText(sharePre.getString("password", ""));
//			freeLoginCheckBox.setChecked(true);
//			AsyncTask<Object, Object, LoginParse> asyncTask = new AsyncTask<Object, Object, LoginParse>() {
//
//				@Override
//				protected LoginParse doInBackground(
//						Object... params) {
//					// TODO Auto-generated method stub
//					return  Apicmd.getInstance().Login(
//							getActivity(), lastMobile, pass);
//
//				}
//
//				protected void onPostExecute(LoginParse result) {
//					// TODO Auto-generated method stub
//					if (result != null && result.status.equals("0")) {
//						//登陆成功，则设置登陆标记为true
//						isLogin=true;
//						User.mIdentify=1;
//						// 注意：此处需要记录获取用户标记，以区别是个人用户还是商家，此处只演示个人用户中心显示
//						userLinear.removeAllViews();
//						View personView = inflater.inflate(
//								R.layout.person_center, null);
//						prepareHeaderView(personView);
//						preparePersonCenterView(personView);
//						preparePersonCenterData();
//						userLinear.addView(personView);
//					}
//				}
//
//			};
//			asyncTask.execute(new Object[] {});
//		}
	}

	private void prepareHeaderView(View view) {
		// TODO Auto-generated method stub
		leftImgBtn = (Button) view.findViewById(R.id.leftImgBtn);
		rightImgBtn = (Button) view.findViewById(R.id.rightImgBtn);
		centerText = (TextView) view.findViewById(R.id.centerText);
		leftImgBtn.setOnClickListener(myOnClickListener);
		rightImgBtn.setOnClickListener(myOnClickListener);
	}

	private void prepareLoginView(View view) {
		// TODO Auto-generated method stub
		freeLoginCheckBox = (CheckBox) view.findViewById(R.id.freeLoginRadio);
		leftImgBtn.setVisibility(View.GONE);
		rightImgBtn.setVisibility(View.GONE);
		centerText.setText("登陆");
		
		userNameEdit = (EditText) view.findViewById(R.id.userName);
		passwordEdit = (EditText) view.findViewById(R.id.password);
		
		loginBtn = (Button) view.findViewById(R.id.loginBtn);
		findPassBtn = (Button) view.findViewById(R.id.findPassBtn);
		registerBtn = (Button) view.findViewById(R.id.registerBtn);
		loginBtn.setOnClickListener(myOnClickListener);
		findPassBtn.setOnClickListener(myOnClickListener);
		registerBtn.setOnClickListener(myOnClickListener);
		
	}

	private void preparePersonCenterView(View view) {
		// TODO Auto-generated method stub
		myOrderBtn = (RelativeLayout) view.findViewById(R.id.myOrderBtn);
		myEnquiryBtn = (RelativeLayout) view.findViewById(R.id.myEnquiryBtn);
		myFavorBtn = (RelativeLayout) view.findViewById(R.id.myFavorBtn);
		raffleBtn = (RelativeLayout) view.findViewById(R.id.raffleBtn);
		publishMsgBtn = (RelativeLayout) view.findViewById(R.id.publishMsgBtn);
		editPersonalInfoBtn = (RelativeLayout) view
				.findViewById(R.id.editPersonalInfoBtn);
		sendMsgDoneBtn = (RelativeLayout) view.findViewById(R.id.sendMsgDoneBtn);
		goodsDeliverAddressBtn = (RelativeLayout) view
				.findViewById(R.id.goodsDeliverAddressBtn);
		regiterStoreBtn = (TextView) view.findViewById(R.id.regiterStoreBtn);

		myOrderBtn.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		myEnquiryBtn.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		myFavorBtn.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		raffleBtn.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		publishMsgBtn.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		editPersonalInfoBtn.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		sendMsgDoneBtn.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		goodsDeliverAddressBtn.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		regiterStoreBtn.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.denglu, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.denglu));
		
		
		myOrderBtn.setOnClickListener(myOnClickListener);
		myEnquiryBtn.setOnClickListener(myOnClickListener);
		myFavorBtn.setOnClickListener(myOnClickListener);
		raffleBtn.setOnClickListener(myOnClickListener);
		publishMsgBtn.setOnClickListener(myOnClickListener);
		editPersonalInfoBtn.setOnClickListener(myOnClickListener);
		sendMsgDoneBtn.setOnClickListener(myOnClickListener);
		goodsDeliverAddressBtn.setOnClickListener(myOnClickListener);
		regiterStoreBtn.setOnClickListener(myOnClickListener);
	}
	
	private void prepareMerchantCenterView(View view){
		handling_customer_orders = (RelativeLayout) view.findViewById(R.id.handling_customer_orders);
		handling_customer_enquiry = (RelativeLayout) view.findViewById(R.id.handling_customer_enquiry);
		release_goods = (RelativeLayout) view.findViewById(R.id.release_goods);
		edit_business_information = (RelativeLayout) view.findViewById(R.id.edit_business_information);
		handle_send_goods_management = (RelativeLayout) view.findViewById(R.id.handle_send_goods_management);
		staff_management = (RelativeLayout) view.findViewById(R.id.staff_management);
		my_order = (RelativeLayout) view.findViewById(R.id.my_order);
		my_enquiry = (RelativeLayout) view.findViewById(R.id.my_enquiry);
		my_favor = (RelativeLayout) view.findViewById(R.id.my_favor);
		shipping_address = (RelativeLayout) view.findViewById(R.id.shipping_address);
		my_raffle = (RelativeLayout) view.findViewById(R.id.my_raffle);
		
		handling_customer_orders.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		handling_customer_enquiry.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		
		release_goods.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		edit_business_information.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		handle_send_goods_management.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		staff_management.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		my_order.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		my_enquiry.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		my_favor.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		shipping_address.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		my_raffle.setBackgroundDrawable(Global.newSelectorFromRes(
				getActivity(), R.drawable.center_bg_9, R.drawable.center_bg_press_9, 
				R.drawable.center_bg_press_9, R.drawable.center_bg_9));
		
		
		handling_customer_orders.setOnClickListener(myOnClickListener);
		handling_customer_enquiry.setOnClickListener(myOnClickListener);
		release_goods.setOnClickListener(myOnClickListener);
		edit_business_information.setOnClickListener(myOnClickListener);
		handle_send_goods_management.setOnClickListener(myOnClickListener);
		staff_management.setOnClickListener(myOnClickListener);
		my_order.setOnClickListener(myOnClickListener);
		my_enquiry.setOnClickListener(myOnClickListener);
		my_favor.setOnClickListener(myOnClickListener);
		shipping_address.setOnClickListener(myOnClickListener);
		my_raffle.setOnClickListener(myOnClickListener);
		
	}

	private void prepareRegisterView(View view) {
		// TODO Auto-generated method stub
		
//		leftImgBtn.setBackgroundResource(R.drawable.btn_back);
//		leftImgBtn.setVisibility(View.VISIBLE);
//		rightImgBtn.setVisibility(View.GONE);
//		centerText.setText("注册");
//		leftImgBtn.setOnClickListener(myOnClickListener);
//		leftImgBtn.setOnTouchListener(myOnTouchListener);
		
		sendValidationCodeBtn = (Button) view
				.findViewById(R.id.sendValidationCodeBtn);
		registerConfirmBtn = (Button) view
				.findViewById(R.id.registerConfirmBtn);// 注册按钮
		phoneNumEdit = (EditText) view.findViewById(R.id.phoneNumEdit);// 电话
		registerpasswordEdit = (EditText) view.findViewById(R.id.register_passwordEdit);// 密码
		validationEdit = (EditText) view.findViewById(R.id.validationEdit);// 验证码
		sendValidationCodeBtn.setOnClickListener(myOnClickListener);
		registerConfirmBtn.setOnClickListener(myOnClickListener);
//		sendValidationCodeBtn.setOnTouchListener(myOnTouchListener);
//		registerConfirmBtn.setOnTouchListener(myOnTouchListener);
	}

	private void prepareFindPassView(View view) {
		// TODO Auto-generated method stub
//		leftImgBtn.setBackgroundResource(R.drawable.btn_back);
//		leftImgBtn.setVisibility(View.VISIBLE);
//		rightImgBtn.setVisibility(View.GONE);
//		centerText.setText("找回密码");
//		leftImgBtn.setOnClickListener(myOnClickListener);
//		leftImgBtn.setOnTouchListener(myOnTouchListener);

		getVCodeSmsBtn = (Button) view.findViewById(R.id.getVCodeSmsBtn);
		updatePassBtn = (Button) view.findViewById(R.id.updatePassBtn);// 注册按钮
		verificationCodeEdit = (EditText) view.findViewById(R.id.verificationCodeEdit);// 电话
		phoneSMSEdit = (EditText) view.findViewById(R.id.phoneSMSEdit);// 电话
		passwordNewEdit1 = (EditText) view.findViewById(R.id.passwordNewEdit1);// 密码
		passwordNewEdit2 = (EditText) view.findViewById(R.id.passwordNewEdit2);// 验证码
		getVCodeSmsBtn.setOnClickListener(myOnClickListener);
		updatePassBtn.setOnClickListener(myOnClickListener);
//		getVCodeSmsBtn.setOnTouchListener(myOnTouchListener);
//		updatePassBtn.setOnTouchListener(myOnTouchListener);
	}
	
	private OnClickListener myOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			
			//登录页面    登录按钮
			case R.id.loginBtn:{
				final String phone = userNameEdit.getText().toString();
				final String password = passwordEdit.getText().toString();
				if(!StringUtil.isMobileNumber(phone)){
					Toast.makeText(getActivity(), "无效手机号码,请输入正确手机号码",Toast.LENGTH_SHORT).show();
					return;
				}
				if(!StringUtil.isValidStr(password)){
					Toast.makeText(getActivity(), "密码不能为空，请从新输入",Toast.LENGTH_SHORT).show();
					return;
				}
				
//				//测试
//				merchantcenter.setVisibility(View.VISIBLE);
//				centerText.setText("商家个人中心");
//				userLinear.setVisibility(View.GONE);
//				rightImgBtn.setVisibility(View.VISIBLE);
				
				DoAsyncTask task = new DoAsyncTask(getActivity(), "正在登陆...",
						new DoTask() {
							@Override
							public Object task() {
								// TODO Auto-generated method stub
								return Apicmd.getInstance().Login(getActivity(), phone, password);
							}
						}, new DoAsyncTask.PostResult() {
							@Override
							public void result(Object result) {
								// TODO Auto-generated method stub
								if (result != null) {
									LoginParse log = (LoginParse) result;
									if (log.isSucceed()) {
										Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();
										// preparePersonCenterView(loginView);
										//个人
										if(User.mIdentify<1){
											personalCenter.setVisibility(View.VISIBLE);
											centerText.setText("个人中心");
										}else{
											//商家
											merchantcenter.setVisibility(View.VISIBLE);
											centerText.setText("商家个人中心");
										}
										
										userLinear.setVisibility(View.GONE);
										rightImgBtn.setVisibility(View.VISIBLE);
										
										if (freeLoginCheckBox.isChecked()) {
											SharedPreferencesHelper sph = SharedPreferencesHelper.getSharePreHelper();
											sph.putString(PHONE_NUMBER, StringUtil.Base64Encode(phone.getBytes()));
											sph.putString(PASSWORD, password);
//											sph.putString(UID, User.uid);
											sph.putBoolean(ISREMEMBER, true);
										}
									}else {
										Toast.makeText(getActivity(), "登陆失败,请稍后尝试", Toast.LENGTH_SHORT).show();
									}
								}
							}
						}, true, true);
				
				break;
				}
				
			//跳转找回密码页面
			case R.id.findPassBtn:{
				userLinear.setVisibility(View.GONE);
				findPassword.setVisibility(View.VISIBLE);
				leftImgBtn.setVisibility(View.VISIBLE);
				centerText.setText("找回密码");
				break;
			}
			//找回密码页面  获取验证码
			case R.id.getVCodeSmsBtn: {
				sendVcode(phoneNumEdit.getText().toString(), "1");
				break;
			}
			//找回密码页面  确认按钮  更新密码
			case R.id.updatePassBtn:{
					Toast.makeText(
							getActivity(),
							"更新密码成功\n" + verificationCodeEdit.getText() + "\t"
									+ passwordNewEdit1.getText() + "\t"
									+ passwordNewEdit2.getText(), 0).show();
					
					
					findPassword.setVisibility(View.GONE);
					leftImgBtn.setVisibility(View.GONE);
					personalCenter.setVisibility(View.VISIBLE);
					centerText.setText("个人中心");
					rightImgBtn.setVisibility(View.VISIBLE);
				break;
			}
			//跳转 注册页面
			case R.id.registerBtn:{
				userLinear.setVisibility(View.GONE);
				user_register.setVisibility(View.VISIBLE);
				centerText.setText("注册");
				leftImgBtn.setVisibility(View.VISIBLE);
				break;
			}
			//注册页面  获取验证码按钮
			case R.id.sendValidationCodeBtn:{
				sendVcode(phoneNumEdit.getText().toString(), "0");
				break;
			}
//			注册页面，确定按钮注册
			case R.id.registerConfirmBtn:{
				final String phone = phoneNumEdit.getText().toString();
				final String pass = passwordEdit.getText().toString();
				final String val = validationEdit.getText().toString();
				if(!StringUtil.isMobileNumber(phone)){
					Toast.makeText(getActivity(), "手机号码无效,请从新输入", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!StringUtil.isValidStr(val)){
					Toast.makeText(getActivity(), "验证码不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!StringUtil.isValidStr(pass)){
					Toast.makeText(getActivity(), "密码不能为空,请从新设置", Toast.LENGTH_SHORT).show();
					return;
				}
				DoAsyncTask t = new DoAsyncTask(getActivity(), "正在注册，请等待...", new DoTask() {
					
					@Override
					public Object task() {
						// TODO Auto-generated method stub
						return Apicmd.getInstance().register(getActivity(),phone,
								pass,val);
					}
				}, new DoAsyncTask.PostResult(){

					@Override
					public void result(Object result) {
						// TODO Auto-generated method stub
						if(result!=null){
							RegisterParse r = (RegisterParse)result;
							if(r.isSucceed()){
								personalCenter.setVisibility(View.VISIBLE);
								user_register.setVisibility(View.GONE);
								Toast.makeText(getActivity(), r.msg, Toast.LENGTH_SHORT).show();
							}
						}
					}
					
				}, true, true);
				break;
			}
			case R.id.leftImgBtn:{
				if(findPassword.getVisibility()==View.VISIBLE){
					findPassword.setVisibility(View.GONE);
					
				}else if(user_register.getVisibility()==View.VISIBLE){
					user_register.setVisibility(View.GONE);
				}
				userLinear.setVisibility(View.VISIBLE);
				centerText.setText("登陆");
				leftImgBtn.setVisibility(View.GONE);
				break;
			}
			//退出按钮
			case R.id.rightImgBtn:{
				personalCenter.setVisibility(View.GONE);
				merchantcenter.setVisibility(View.GONE);
				userLinear.setVisibility(View.VISIBLE);
				centerText.setText("登陆");
				leftImgBtn.setVisibility(View.GONE);
				rightImgBtn.setVisibility(View.GONE);
				User.reset();
				break;
			}
			//个人中心 跳转
			
			//我的订单
			case R.id.myOrderBtn:{
				Intent intent=new Intent();
				intent.setClass(getActivity(), OrderManage.class);
				startActivity(intent);
				break;
			}
			//我的询价
			case R.id.myEnquiryBtn:{
				Intent intent=new Intent(getActivity(), MyEquire.class);
				startActivity(intent);
				break;
			}
			//我的收藏
			case R.id.myFavorBtn: {
				HomeActivity.tabHost.setCurrentTabByTag("favor");
				break;
			}
			//我的抽奖
			case R.id.raffleBtn: {
				HomeActivity.tabHost.setCurrentTabByTag("bonus");
				break;
			}
			//发布信息
			case R.id.publishMsgBtn: {
				User.mAction=2;
				HomeActivity.tabHost.setCurrentTabByTag("message");
				break;
			}
			//编辑个人信息
			case R.id.editPersonalInfoBtn:{
				Toast.makeText(getActivity(),"即将开启,尽请期待\n编辑个人信息",Toast.LENGTH_SHORT).show();
//				Intent intent=new Intent();
//				intent.setClass(getActivity(), UserInfoActivity.class);
//				startActivity(intent);
				break;
			}
			//已发消息管理
			case R.id.sendMsgDoneBtn: {
				Toast.makeText(getActivity(),"即将开启,尽请期待\n已发消息管理",Toast.LENGTH_SHORT).show();
				break;
			}
			//收货地址
			case R.id.goodsDeliverAddressBtn: {
				Intent intent = new Intent(getActivity(), ShippingActivity.class);
				startActivity(intent);
				break;
			}
			
//			注册商家按钮
			case R.id.regiterStoreBtn:{
				//动作注册
				User.mAction =1;
				HomeActivity.tabHost.setCurrentTabByTag("message");
				break;
			}
			
			//商家 个人中心 跳转
			case R.id.handling_customer_orders: {
//				Toast.makeText(getActivity(), "处理用户订单", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(), WeiOrderActivity.class);
				startActivity(intent);
				break;
			}
			case R.id.handling_customer_enquiry: {
				Intent intent = new Intent(getActivity(), UserEnquire.class);
				startActivity(intent);
				break;
			}
			case R.id.release_goods: {
				User.mAction = 2;
				HomeActivity.tabHost.setCurrentTabByTag("message");
				break;
			}
			case R.id.edit_business_information: {
				HomeActivity.tabHost.setCurrentTabByTag("message");
				break;
			}
			case R.id.handle_send_goods_management: {
				Toast.makeText(getActivity(), "处理已发商品", Toast.LENGTH_SHORT).show();
				break;
			}
			//员工管理
			case R.id.staff_management: {
				Intent intent = new Intent(getActivity(), EmployeeManager.class);
				startActivity(intent);
				break;
			}
			case R.id.my_order: {
				Intent intent=new Intent();
				intent.setClass(getActivity(), OrderManage.class);
				startActivity(intent);
				break;
			}
			case R.id.my_enquiry: {
				Intent intent=new Intent(getActivity(), MyEquire.class);
				startActivity(intent);
				break;
			}
			case R.id.my_favor: {
				HomeActivity.tabHost.setCurrentTabByTag("favor");
				break;
			}
			case R.id.shipping_address: {
				Intent intent = new Intent(getActivity(), ShippingActivity.class);
				startActivity(intent);
				break;
			}
			case R.id.my_raffle: {
				HomeActivity.tabHost.setCurrentTabByTag("bonus");
				break;
			}
			
			//默认不执行
			default:{
				break;
				}
			}
		}
	};
	
	private void sendVcode(final String phone, final String type){
//		0注册 1找密码 2添加小号 3转让
		if(StringUtil.isMobileNumber(phone)){
			DoAsyncTask t = new DoAsyncTask(getActivity(), "正在发送请求，请稍后接收短信", new DoTask() {
				@Override
				public Object task() {
					// TODO Auto-generated method stub
//					0注册 1找密码 2添加小号 3转让
					return Apicmd.getInstance().sendVCode(
							getActivity(), type, phone);
				}
			}, new DoAsyncTask.PostResult(){
				@Override
				public void result(Object result) {
					// TODO Auto-generated method stub
					if (result != null) {
						// 如果手机未注册
						SendVCodeParse sv = (SendVCodeParse)result;
						Toast.makeText(getActivity(), sv.err_str, Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(getActivity(), "获取失败.", Toast.LENGTH_SHORT).show();
					}
				}
			}, true, true);
		}else{
			Toast.makeText(getActivity(), "手机号码无效,请从新输入", Toast.LENGTH_SHORT).show();
		}
	}

}
