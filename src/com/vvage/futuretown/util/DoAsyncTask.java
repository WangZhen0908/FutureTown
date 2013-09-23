package com.vvage.futuretown.util;

import com.vvage.futuretown.Global;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class DoAsyncTask{
	
	
	public DoAsyncTask(Context context, final String message, final DoTask doTask, 
			final PostResult postResult, final boolean isShowDlg, final boolean cancel){
		if(Global.netCheck(context)){
			final ProgressDialog d = new ProgressDialog(context);
			d.setMessage(message);
			
			final AsyncTask<Object, Object, Object> task = new AsyncTask<Object, Object, Object>() {

				@Override
				protected Object doInBackground(Object... arg0) {
					// TODO Auto-generated method stub
					if(doTask == null){
						this.cancel(true);
						return null;
					}
					
					Object object = null;
					try {
						object = doTask.task();
					} catch (Exception e) {
						// TODO: handle exception
						LogUtil.d("DoAsyncTask_construct", e.toString());
					}
					return object;
				}

				@Override
				protected void onPostExecute(Object result) {
					// TODO Auto-generated method stub
					if(d != null && isShowDlg){
						d.dismiss();
					}
					postResult.result(result);
				}
				
			};
			
			d.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface arg0) {
					// TODO Auto-generated method stub
					if(cancel){
						task.cancel(cancel);
						d.dismiss();
						postResult.result(null);
					}
				}
			});
			
			if(isShowDlg){
				d.show();
			}
			
			task.execute();
		}else{
			Toast.makeText(context, "亲~网络不给力呀,稍后试试吧", Toast.LENGTH_SHORT).show();
		}
	}
	
	public interface DoTask {
		public Object task();
	}
	
	public interface PostResult {
		public void result(Object result);
	}
}
