package com.vvage.futuretown;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class BaseFragment extends Fragment{
	protected BaseActivityInterface mBaseInterface; 
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onStart() {
		if (this.getActivity() instanceof BaseActivityInterface) {
			mBaseInterface = (BaseActivityInterface) this.getActivity();
		}
		super.onStart();
		
//		Log.d("Fragment----", "BaseFragment void onStart()---------------->");
	}


	public void showPopMenu(View parent) {
		if (mBaseInterface != null) {
			mBaseInterface.showPopMenu(parent);
		}
	}


	public void dismissPopMenu() {
		if (mBaseInterface != null) {
			mBaseInterface.dismissPopMenu();
		}
	}


	public void showToast(String info) {
		if (mBaseInterface != null) {
			mBaseInterface.showToast(info);
		}
	}


	public void showInfoDialog(String content) {
		if (mBaseInterface != null) {
			mBaseInterface.showInfoDialog(content);
		}
	}


	public void showInfoDialog(String title, String content, String btnStr, final OnClickListener btnListener) {
		if (mBaseInterface != null) {
			mBaseInterface.showInfoDialog(title, content, btnStr, btnListener);
		}
	}


	public void showExcuteDialog(String content, final OnClickListener positiveListener) {
		if (mBaseInterface != null) {
			mBaseInterface.showExcuteDialog(content, positiveListener);
		}
	}


	public void showExcuteDialog(String title, String content, String positiveStr, String negativeStr, final OnClickListener positiveListener, final OnClickListener negativeListener) {
		if (mBaseInterface != null) {
			mBaseInterface.showExcuteDialog(title, content, positiveStr, negativeStr, positiveListener, negativeListener);
		}
	}


	public void showProgress() {
		if (mBaseInterface != null) {
			mBaseInterface.showProgress();
		}
	}


	public void showProgress(String title, String message) {
		if (mBaseInterface != null) {
			mBaseInterface.showProgress(title, message);
		}
	}


	public void dismissProgress() {
		if (mBaseInterface != null) {
			mBaseInterface.dismissProgress();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
//		Log.d("Fragment----", "BaseFragment void onActivityResult()---------------->");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		Log.d("Fragment----", "BaseFragment void onDestroy()---------------->");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		Log.d("Fragment----", "BaseFragment void onResume()---------------->");
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
//		Log.d("Fragment----", "BaseFragment void onSaveInstanceState()---------------->");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		Log.d("Fragment----", "BaseFragment void onPause()---------------->");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
//		Log.d("Fragment----", "BaseFragment void onStop()---------------->");
	}
	
	

}
