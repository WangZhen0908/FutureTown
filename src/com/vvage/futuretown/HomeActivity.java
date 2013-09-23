package com.vvage.futuretown;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.vvage.futuretown.activity.LoginListener;
import com.vvage.futuretown.model.User;

public class HomeActivity extends FragmentActivity {

	public static TabHost tabHost;
//	private HashMap<String, LoginListener> listener;
	public static ArrayList<HashMap<String, LoginListener>> loginListeners;
	private LinearLayout layout;
	private Animation animation;
	public static BMapManager mBMapMan = null;
	private static final String strKey = "6C34DDDC2C51ECA281BDE40EB684831F12AC94B8";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBMapMan = new BMapManager(this);
		mBMapMan.init(strKey, new MyGeneralListener());
		
		setContentView(R.layout.activity_home);
		layout = (LinearLayout)findViewById(R.id.id_welcome);
		animation = AnimationUtils.loadAnimation(this, R.anim.push_out);
		layout.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				layout.setVisibility(View.GONE);
				tabHost.setVisibility(TabHost.VISIBLE);
			}
		});
		
		
		tabHost = (TabHost)findViewById(android.R.id.tabhost);
		tabHost.setup();

		final LayoutInflater mInflater = LayoutInflater.from(this);
		final View homeView = mInflater.inflate(R.layout.tab_home, null);
		final View messageView = mInflater.inflate(R.layout.tab_message, null);
		final View favorView = mInflater.inflate(R.layout.tab_favor, null);
		final View bonusView = mInflater.inflate(R.layout.tab_bonus, null);
		final View userView = mInflater.inflate(R.layout.tab_user, null);

		TabHost.TabSpec user = tabHost.newTabSpec("user").setIndicator(userView)
				.setContent(R.id.tab_user);
		
		tabHost.addTab(tabHost.newTabSpec("home").setIndicator(homeView)
				.setContent(R.id.tab_home));
		tabHost.addTab(tabHost.newTabSpec("message").setIndicator(messageView)
				.setContent(R.id.tab_message));
		tabHost.addTab(tabHost.newTabSpec("favor").setIndicator(favorView)
				.setContent(R.id.tab_favor));
		tabHost.addTab(tabHost.newTabSpec("bonus").setIndicator(bonusView)
				.setContent(R.id.tab_bonus));
		tabHost.addTab(user);

		Global.initResource(getApplicationContext());

		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				// tabHost.clearAllTabs();
				if (HomeActivity.loginListeners != null) {
					int size = HomeActivity.loginListeners.size();
					for(int i=0; i<size;++i){
						HashMap<String, LoginListener> hm = HomeActivity.loginListeners.get(i);
						if(hm.containsKey(tabId)){
							hm.get(tabId).Login(User.class);
						}
					}
				}
			}
		});
		
//		setContentView(mContentView);

	}
	
	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
    private class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(getApplicationContext(), "您的网络出错啦！",
                    Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(HomeActivity.this, "输入正确的检索条件！",
                        Toast.LENGTH_LONG).show();
            }
            // ...
        }

        @Override
        public void onGetPermissionState(int iError) {
            if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
                //授权Key错误：
                Toast.makeText(getApplicationContext(), 
                        "请在 DemoApplication.java文件输入正确的授权Key！", Toast.LENGTH_LONG).show();
//                DemoApplication.getInstance().m_bKeyRight = false;
            }
        }
    }
	
	public static void setLoginListener(HashMap<String, LoginListener> fmListener){
		if (loginListeners == null) {
			loginListeners = new ArrayList<HashMap<String, LoginListener>>();
		}
		if (!loginListeners.contains(fmListener)) {
			loginListeners.add(fmListener);
		}
	}
	

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(loginListeners!=null){
			loginListeners.clear();
			loginListeners = null;
		}
		tabHost = null;
		if (mBMapMan != null) {
//			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
//		if (mBMapMan != null) {
//			mBMapMan.stop();
//		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
//		if (mBMapMan != null) {
//			mBMapMan.start();
//		}
		super.onResume();
	}

	@Override
	protected void onResumeFragments() {
		// TODO Auto-generated method stub
		super.onResumeFragments();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		super.onPostResume();
	}
	
    @Override
    public void onBackPressed() {
	new AlertDialog.Builder(this).setTitle("确认退出吗？")
		.setIcon(android.R.drawable.ic_dialog_info)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
			// 点击“确认”后的操作
		    	HomeActivity.this.finish();
 
		    }
		})
		.setNegativeButton("返回", new DialogInterface.OnClickListener() {
 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
			// 点击“返回”后的操作,这里不设置没有任何操作
		    }
		}).show();
	// super.onBackPressed();
    }


}
