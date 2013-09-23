package com.vvage.futuretown.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.URLUtil;

public class AsyncViewTask extends AsyncTask<View, Void, Drawable> {

	private int width;
	private View mView;
    private HashMap<String, SoftReference<Drawable>> imageCache;
    private PostExecute pe;
//    private String url;
    
    public AsyncViewTask(int width, PostExecute pe) {
//    	this.url = url;
    	this.width = width;
        imageCache = new HashMap<String, SoftReference<Drawable>>();
        this.pe=pe;
    }
    
	@Override
	protected Drawable doInBackground(View... views) {
		// TODO Auto-generated method stub
		Drawable drawable = null;
        View view = views[0];
        if (view.getTag() != null) {
            if (imageCache.containsKey(view.getTag())) {
                SoftReference<Drawable> cache = imageCache.get(view.getTag().toString());
                drawable = cache.get();
                if (drawable != null) {
                    return drawable;
                }
            }
            try {
                if (URLUtil.isHttpUrl(view.getTag().toString())) {// 如果为网络地址。则连接url下载图片
                    URL url = new URL(view.getTag().toString());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream stream = conn.getInputStream();
                    byte[] data = readInputStream(stream);
                    
                  //以下是把图片转化为缩略图再加载  
                    BitmapFactory.Options options = new BitmapFactory.Options();   
                    options.inJustDecodeBounds = true;  
                    Bitmap bitmap=BitmapFactory.decodeByteArray(data, 0 , data.length, options);  
                    options.inJustDecodeBounds = false;  
                    int be = (int)(options.outWidth / (float)width );   
                    if (be <= 0 ) 
                    	be =  1 ;   
                    options.inSampleSize = be;   
                    Bitmap bitmap1 =BitmapFactory.decodeByteArray(data, 0 , data.length, options);  
                    BitmapDrawable bd = new BitmapDrawable(bitmap1);
                    
                    drawable = bd;
//                    drawable = Drawable.createFromStream(stream, "src");
                    stream.close();
                } else {// 如果为本地数据，直接解析
                    drawable = Drawable.createFromPath(view.getTag().toString());
                }
            } catch (Exception e) {
                LogUtil.d("img", e.getMessage());
                return null;
            }
        }
        this.mView = view;
		return drawable;
	}
	
	public interface PostExecute{
		public void result(boolean result);
	}

	@Override
	protected void onPostExecute(Drawable drawable) {
		// TODO Auto-generated method stub
		if (drawable != null) {
			
            this.mView.setBackgroundDrawable(ScaleImg(drawable));
            this.mView = null;
            pe.result(true);
        }
	}
	
    private Drawable ScaleImg(Drawable d){
    	Bitmap drawToBitmap = drawableToBitmap(d);
		if (drawToBitmap!=null&&drawToBitmap.getDensity() != DisplayMetrics.DENSITY_DEFAULT) {
			drawToBitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
		}
		// DisplayMetrics metric = new DisplayMetrics();
		// activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		// drawToBitmap.setDensity(metric.densityDpi);
		float scale = (float)width/(float)drawToBitmap.getWidth();
//		float scaleX = scale / (float) drawToBitmap.getWidth();
//		float scaleY = scale / (float) drawToBitmap.getHeight();
		Bitmap scaleBitmap = getScaleBitmap(drawToBitmap, scale, scale);
		BitmapDrawable bd = new BitmapDrawable(scaleBitmap);
		return bd;
    }
    
	// drawable转换bitmap
	private Bitmap drawableToBitmap(Drawable drawable) {
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap drawToBitmap = bd.getBitmap();
		// 一下代码需要去处理w,h=-1
		// int w = drawable.getIntrinsicWidth();
		// int h = drawable.getIntrinsicHeight();
		// Bitmap bitmap = Bitmap.createBitmap(w, h, drawable.getOpacity() !=
		// PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565);
		// Canvas canvas = new Canvas(bitmap);
		// drawable.setBounds(0, 0, w, h);
		// drawable.draw(canvas);
		// return bitmap;
		return drawToBitmap;
	}
	

	// 图片缩放设置
	private Bitmap getScaleBitmap(Bitmap bitmap, float sx, float sy) {
		if (sx == 1.0f && sy == 1.0f) {
			return bitmap;
		}
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		boolean result = matrix.postScale(sx, sy);
		Bitmap scaleBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return scaleBitmap;
	}
	
	  /**  
     * 读取InputStream数据，转为byte[]数据类型  
     * @param is  InputStream数据  
     * @return  返回byte[]数据  
     */  
	private byte[] readInputStream(InputStream is) {  
        ByteArrayOutputStream baos=new ByteArrayOutputStream();  
        byte[] buffer=new byte[1024 ];  
        int length=-1 ;  
        try {  
            while((length=is.read(buffer))!=-1 ){  
                baos.write(buffer, 0 , length);  
            }  
            baos.flush();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        byte[] data=baos.toByteArray();  
        try {  
            is.close();  
            baos.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return data;  
    }
}
