package com.vvage.futuretown;

import android.view.View;
import android.view.View.OnClickListener;

public abstract interface BaseActivityInterface {

	public void showPopMenu(View parent);

	public void dismissPopMenu();

	public void showToast(String info);

	public void showInfoDialog(String content);

	public void showInfoDialog(String title, String content, String btnStr,
			final OnClickListener btnListener);

	public void showExcuteDialog(String content,
			final OnClickListener positiveListener);

	public void showExcuteDialog(String title, String content,
			String positiveStr, String negativeStr,
			final OnClickListener positiveListener,
			final OnClickListener negativeListener);

	public void showProgress();

	public void showProgress(String title, String message);

	public void dismissProgress();
}
