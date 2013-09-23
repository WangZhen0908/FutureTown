package com.vvage.futuretown.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.widget.Toast;

public class FileUtil {

	public static String getApplicationDocPathInSDcard(Context context){
		// /sdcard/packname/
		return Environment.getExternalStorageDirectory()+"/"+getApplicationName(context);
	}
	
	private static String getApplicationName(Context context){
		PackageManager packageManager = null; 
		ApplicationInfo applicationInfo = null; 
		try { 
			packageManager = context.getPackageManager(); 
			applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0); 
		}catch (PackageManager.NameNotFoundException e) { 
			applicationInfo = null; 
		} 
		String applicationName = (String) packageManager.getApplicationLabel(applicationInfo); 
		return applicationName;
	}

	/**
	 * 判断SD卡是否存在
	 * 
	 * false 不存在 true 存在
	 */
	public static boolean isSDCardExist() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	// public static void save(Activity activity, Bitmap bmp, String name) {
	// /*
	// * data/data/xxx.xxx.xxx(package name) path
	// */
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// bmp.compress(CompressFormat.PNG, 100, baos);
	// byte[] bytes = baos.toByteArray();
	//
	// save(activity, name+".PNG", bytes, false);
	// }

	public static void save(Activity activity, String path,
			String filename, Bitmap bmp, boolean isToast) {
		if (isSDCardExist()) {
			if (!StringUtil.isValidStr(path) || !StringUtil.isValidStr(filename) ||bmp==null)
				return;
			FileOutputStream outputStream=null;
			try {
//				File file = new File(path);
//				if (!file.exists()) {
//					file.mkdir();
//				}
//				File doc = new File(path+"/"+filename);
//				if (!doc.exists()) {
//					doc.createNewFile();
//				}
					outputStream = new FileOutputStream(path+"/"+filename);
					outputStream.write(bmp2Bytes(bmp));
					outputStream.flush();
					outputStream.close();
					if (isToast)
						Toast.makeText(activity, "保存成功", Toast.LENGTH_SHORT).show();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// FileOutputStream outputStream = activity.openFileOutput(filename,
			// activity.MODE_PRIVATE);
			// outputStream.write(bytes);
			// outputStream.flush();
			// outputStream.close();
		}
	}
	
	private static byte[] bmp2Bytes(Bitmap bmp){
		if(bmp==null){
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}

	public static byte[] readImage2Bytes(Context context, String path,
			String filename) throws IOException {
		DataInputStream input = new DataInputStream(new FileInputStream(path+"/"+filename));
		int len = input.available();
		byte[] content = new byte[len];
		int r = input.read(content, 0, content.length);
		if(r != len) {
			content = null;
			throw new IOException("读取文件失败");
		}
		input.close();
		return content;
		
		
		
//		byte[] b = null;
//		File file = new File(path+"/"+filename);
//		if (!file.exists()){
//			return b;
//		}
//		try {
//			FileInputStream inputStream = new FileInputStream(file);
//			byte[] bytes = new byte[1024];
//			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
//			while (inputStream.read(bytes) != -1) {
//				arrayOutputStream.write(bytes, 0, bytes.length);
//			}
//			b = arrayOutputStream.toByteArray();
////			inputStream.close();
////			arrayOutputStream.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return b;
		
	}
	
	public static void writeToPrivatePath(Context context, InputStream is, String filepath, String filename){
		File dir = new File(filepath);//"data/data/" + context.getPackageName() + "/databases"
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdir();
        }
        File file = new File(dir, filename);//"china_province_city_zone.db3"
        if (!file.exists()) {
        	
			FileOutputStream outStream;
			try {
				outStream = new FileOutputStream(file);
				outStream.write(inputStreamToBytes(is));
				outStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//            FileUtil.loadDbFile(R.raw.china_province_city_zone, file,
//                    getResources(), getPackageName());
//            Log.d("WineStock", "DataBase Load Successfully");

        }
		
	}
	
	public static byte[] inputStreamToBytes(InputStream inputStream)
			throws Exception {
		if (inputStream == null) {
			return null;
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		outputStream.close();
		inputStream.close();
		return outputStream.toByteArray();
	}
	
	public static Map<Integer, List> getProvince(File file) {
		String sql = "select ProSort ,ProName from T_Province ";
		SQLiteDatabase db = null;
		Cursor c = null;
		Map<Integer, List> provinceData = new HashMap<Integer, List>();
		// List provinceList = null;
		try {
			db = SQLiteDatabase.openOrCreateDatabase(file, null);
			c = db.rawQuery(sql, null);
			List provinceList1 = new ArrayList();
			List provinceList2 = new ArrayList();
			while (c.moveToNext()) {
				Map provinceMap = new HashMap();
				provinceMap.put(c.getString(1), c.getInt(0));
				provinceList1.add(provinceMap);
				provinceList2.add(c.getString(1));
			}
			provinceData.put(0, provinceList1);
			provinceData.put(1, provinceList2);
		} catch (Exception e) {
			// Log.d("WineStock", "getProvince:"+e.getMessage());
		} finally {
			if (c != null) {
				c.close();
			}
			if (db != null) {
				db.close();
			}
		}
		return provinceData;
	}
	
	
	public static Map<Integer,List> getCityByPid(int id,File file){
        String sql = "select ProID,CityName  from T_City where ProID= "+id;
        SQLiteDatabase db = null;
        Cursor c = null;
        Map<Integer,List> cityData = new HashMap<Integer,List>();
        //List cityList = null;
        try{
                db = SQLiteDatabase.openOrCreateDatabase(file, null);
                c = db.rawQuery(sql, null);
                List cityList1 = new ArrayList();
                List cityList2 = new ArrayList();
                while(c.moveToNext()){
                        Map cityMap = new HashMap();
                        cityMap.put(c.getString(1), c.getInt(0));
                        cityList1.add(cityMap);
                        cityList2.add(c.getString(1));
                }
                cityData.put(0, cityList1);
                cityData.put(1, cityList2);
                
        }catch(Exception e){
            LogUtil.d("FT", "getCityByPid:"+e.getMessage());
        }finally{
                if(c!=null){
                        c.close();
                }
                if(db!=null){
                        db.close();
                }
        }
        return cityData;
    } 
    //获取对应市下面区的列表,//file-->数据库文件,id-->指对应市的ID
    public static List<String> getAreaByPid(int id,File file){
        String sql = "select ZoneName  from T_Zone where CityID= "+id;
        SQLiteDatabase db = null;
        Cursor c = null;
        List<String> areaList = null;
        try{
                db = SQLiteDatabase.openOrCreateDatabase(file, null);
                c = db.rawQuery(sql, null);
                areaList = new ArrayList<String>();
                while(c.moveToNext()){
                    areaList.add(c.getString(0));
                }
        }catch(Exception e){
//            Log.d("WineStock", "getAreaByPid:"+e.getMessage());
        }finally{
                if(c!=null){
                        c.close();
                }
                if(db!=null){
                        db.close();
                }
        }
        return areaList;
    } 
}
