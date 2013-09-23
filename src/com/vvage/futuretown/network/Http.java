package com.vvage.futuretown.network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.vvage.futuretown.model.JsonReader;
import com.vvage.futuretown.util.LogUtil;

public class Http {
	static String tag = "HttpConnector_f";

	public static final int GET = 0;
	public static final int POST = 1;

	/**
	 * 接入点类型
	 */
	public enum APNType {
		CMWAP, CMNET, Unknow, CTWAP, CTNET, _3GNET, _3GWAP, UNIWAP;
	}

	static Uri PREFERRED_APN_URI = Uri
			.parse("content://telephony/carriers/preferapn");
	static String proxyAddress = "";// "10.0.0.200";
	static String proxyProt = "";// "80";

	public static final String CMPROXY = "10.0.0.172";
	public static final String CTPROXY = "10.0.0.200";
	public static final String PROT = "80";

	private long execTime;
	private long currentTime;

	private static Http mInstance;

	private Http() {
	}

	public static Http getInstance() {
		if (mInstance == null) {
			mInstance = new Http();
		}
		return mInstance;
	}
	
	/**       
	* 上传文件和文字       
	* @param aFile       
	* @param status       
	* @param urlPath       
	* @return       
	*/        
	public boolean uploadStatus(File aFile, String status, String urlPath) {
		boolean result = false;
		String n = "/sdcard/未来城/1.jpeg";
		try {
			URL url = new URL(urlPath);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			request.setDoOutput(true);
			request.setRequestMethod("POST");
			
			String boundary = "---------------------------37531613912423";
			String pic = "\r\n--"+ boundary+ "\r\nContent-Disposition: form-data; name=\"pic\"; "+ "filename=\""+n+"\"\r\nContent-Type: image/jpeg\r\n\r\n";
			String content = "--"+ boundary+ "\r\nContent-Disposition: form-data; name=\"submit\"\r\n\r\n";
			byte[] end_data = ("\r\n--" + boundary + "--\r\n").getBytes();
			
			FileInputStream stream = new FileInputStream(aFile);
			byte[] file = new byte[(int) aFile.length()];
			stream.read(file);
			
			request.setRequestProperty("Content-Type","multipart/form-data; boundary=" + boundary); // 设置表单类型和分隔符
			request.setRequestProperty("Content-Length",
					String.valueOf(content.getBytes().length
							+ status.getBytes().length + pic.getBytes().length
							+ aFile.length() + end_data.length)); // 设置内容长度
			OutputStream ot = request.getOutputStream();
			ot.write(pic.getBytes());
			ot.write(file);
			ot.write(content.getBytes());
			ot.write(status.getBytes());
			ot.write(end_data);
			
			ot.flush();
			ot.close();
			request.connect();
			BufferedReader input;
			if (200 == request.getResponseCode()) {
				result = true;
				input = new BufferedReader(new InputStreamReader(request.getInputStream()));   
	            StringBuilder sb = new StringBuilder();   
	            String oneLine;   
	            while((oneLine = input.readLine()) != null) {   
	                sb.append(oneLine + "\n");   
	            }   
	            String s = sb.toString();
	            LogUtil.d("JSON",s);
	            input.close();   
//	            ds.close();
	            request.disconnect();
	           
//	        	jr.read(sb.toString());
			} else {
				result = false;
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}   
	
	
	public void uploadFile(Context context, JsonReader jr)throws ErrorMsg{
        HttpURLConnection conn = null;
		DataOutputStream output = null;
		BufferedReader input = null;
		
		try {
			URL url = new URL(jr.getUrl());
			conn = (HttpURLConnection)url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			
			
//			String str = jr.toString();
//			int offset = str.lastIndexOf("|");
//			String content = URLEncoder.encode("fileName", "UTF-8") + "="+ URLEncoder.encode(str.substring(offset+1), "UTF-8");
//		       content += "&" + URLEncoder.encode("img", "UTF-8") + "="
//		              + URLEncoder.encode(str.substring(0, offset), "UTF-8");
//			conn.setRequestProperty("Connection", "Keep-Alive");
//			conn.setRequestProperty("Charset", "UTF-8");
//			conn.setRequestProperty("Content-Type", "image/jpeg");
			
			
	        output = new DataOutputStream(conn.getOutputStream());
	        output.writeBytes(jr.toString());
	        output.flush();
	        conn.connect();
	        
	        if(conn.getResponseCode() == 200){
	        	input = new BufferedReader(new InputStreamReader(conn.getInputStream()));   
	            StringBuilder sb = new StringBuilder();   
	            String oneLine;   
	            while((oneLine = input.readLine()) != null) {   
	                sb.append(oneLine);   
	            }   
	               
	            input.close();   
	            conn.disconnect();
	        	jr.read(sb.toString());
	        	
	        	
	        }else{
	        	throw new RuntimeException("请求‘" + jr.getUrl() +"’失败！");   
	        }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ErrorMsg("网络错误，请稍候重试...");
		}finally {
				try {
				if(output != null) {
					output.close();
				}
				if(input != null) {
					input.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			
			if(conn != null) {
				conn.disconnect();
			}
		}
		
//        try {
//        	
//        	
//			URL url = new URL(jr.getUrl());
//			conn = (HttpURLConnection)url.openConnection();
//			
//			conn.setConnectTimeout(120000);
//			conn.setReadTimeout(5000);
//			
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
//			conn.setUseCaches(false);
//			
//			conn.setRequestMethod("POST");
//			
//			conn.setRequestProperty("Connection", "Keep-Alive");
//			conn.setRequestProperty("Charset", "UTF-8");
////			conn.setRequestProperty("Content-Type", "image/jpeg");
//			
//	        conn.setRequestProperty("Content-Type", multipart_form_data + "; boundary=" + boundary);
//	        
//	        conn.connect();
//	        
//	        output = new DataOutputStream(conn.getOutputStream());
//	        output.writeBytes(twoHyphens+boundary+end);
//	        
//	        String str = jr.toString();
//	        int offset = jr.toString().lastIndexOf("/");
//	        
//	        output.writeBytes("Content-Disposition: form-data; name=\""+"1.jpeg"+"\"; filename=\""+"1.jpeg"+"\""+end);
//	        output.writeBytes("Content-Type: image/jpeg"+end +end);
//	        
//	        
//	        
//	        byte[] b= FileUtil.readImage2Bytes(context,str.substring(0, offset), str.substring(offset+1));
//	        
//	        output.write(b, 0, b.length);
//	       
//	        output.writeBytes(end);
//	        
//	        output.writeBytes(twoHyphens+boundary+twoHyphens+end);
//	        output.flush();
//	        
//	        if(conn.getResponseCode() == 200){
//	        	input = new BufferedReader(new InputStreamReader(conn.getInputStream()));   
//	            StringBuilder sb = new StringBuilder();   
//	            String oneLine;   
//	            while((oneLine = input.readLine()) != null) {   
//	                sb.append(oneLine + end);   
//	            }   
//	               
//	            input.close();   
////	            ds.close();
//	            conn.disconnect();
//	            
//	        	jr.read(sb.toString());
//	        	
//	        	
//	        }else{
//	        	throw new RuntimeException("请求‘" + jr.getUrl() +"’失败！");   
//	        }
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			throw new ErrorMsg("网络错误，请稍候重试...");
//		}finally {
//			try {
//				if(output != null) {
//					output.close();
//				}
//				if(input != null) {
//					input.close();
//				}
//			} catch (IOException e) {
//				throw new RuntimeException(e);
//			}
//			
//			if(conn != null) {
//				conn.disconnect();
//			}
//		}
		
	}

	public void HttpRequest(Context context, JsonReader jr) throws ErrorMsg {
		DataInputStream dis = null;
		String data;
		try {
			data = getData(context, jr.getUrl(), jr.HttpRequestType(), jr.toString());
//			dis = new DataInputStream(new ByteArrayInputStream(data.getBytes()));
			currentTime = System.currentTimeMillis();
			jr.read(data);
			currentTime -= System.currentTimeMillis();
			if (currentTime > 2000) {
				LogUtil.d("checkTime", "data=" + currentTime);
			} else {
				LogUtil.d("checkTime", "data=" + currentTime);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ErrorMsg("网络错误，请稍候重试...");
		}
	}

	private String getData(Context context, String url, int requestType, String post)throws Exception{
		String str=null;
		DefaultHttpClient http = null;
		try {
			http = creatHttp(context, requestType);
			URI uri = new URI(url);
			// 0 is GET
			if(requestType == GET){
				HttpGet get = new HttpGet(uri);
				execTime = System.currentTimeMillis();
				HttpResponse rsp = http.execute(get);
				execTime -=System.currentTimeMillis();
				if (execTime > 2000) {
					LogUtil.d("checkTime", "http=" + execTime);
				} else {
					LogUtil.d("checkTime", "http=" + execTime);
				}
				if (rsp.getStatusLine().getStatusCode() == 200) {
					str = EntityUtils.toString(rsp.getEntity());
//					return str.trim();
				} else {
					return "Error Response: " + rsp.getStatusLine().toString();
				}
			}else if(requestType == POST){
				HttpPost req = new HttpPost(uri);
				StringEntity entity = new StringEntity(post, "utf-8");
				req.setEntity(entity);
				execTime = System.currentTimeMillis();
				HttpResponse rsp = http.execute(req);
				execTime -=System.currentTimeMillis();
				if (execTime > 2000) {
					LogUtil.d("checkTime", "http=" + execTime);
				} else {
					LogUtil.d("checkTime", "http=" + execTime);
				}
				if (rsp.getStatusLine().getStatusCode() == 200) {
					str = EntityUtils.toString(rsp.getEntity());
//					return str.trim();
				} else {
					return "Error Response: " + rsp.getStatusLine().toString();
				}
			}
			
			
		} finally {
			if (http != null) {
				http.getConnectionManager().shutdown();
			}
		}
		return str.trim();
	}

	private DefaultHttpClient creatHttp(Context context, int requestType) {
		DefaultHttpClient client=null;
		if (requestType == POST) {
			HttpHost proxy = null;
			APNType apnType = getCurrentAPNType(context);
			if (APNType.CTWAP.equals(apnType)) {// 电信
				proxy = new HttpHost(CTPROXY, Integer.parseInt(PROT));
			} else if (APNType.CMWAP.equals(apnType)) {// 移动
				proxy = new HttpHost(CMPROXY, Integer.parseInt(PROT));
			} else if (APNType._3GWAP.equals(apnType)) {// 联通
				proxy = new HttpHost(CMPROXY, Integer.parseInt(PROT));
			}
			HttpParams p = new BasicHttpParams();
			int timeoutConnection = 10*1000;
			HttpConnectionParams.setConnectionTimeout(p, timeoutConnection);
			int timeoutSocket = 10 * 1000;
			HttpConnectionParams.setSoTimeout(p, timeoutSocket);
			client =  new DefaultHttpClient(p);
			// 没有wifi就不设置代理
			if (!testWifi(context)) {
				if (proxy != null) {
					client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
				}
			}
		} else if (requestType == GET) {
			client = new DefaultHttpClient();
		}
		
		return client;
	}

	private APNType getCurrentAPNType(Context context) {
		try {
			ContentResolver cr = context.getContentResolver();
			Cursor c = cr.query(PREFERRED_APN_URI, new String[] { "_id",
					"name", "apn", "proxy", "port" }, null, null, null);
			c.moveToFirst();
			if (c.isAfterLast()) {
				return APNType.Unknow;
			}
			String id = c.getString(0);
			String name = c.getString(1);
			String apn = c.getString(2);
			String proxy = c.getString(3);
			String port = c.getString(4);

			if (proxy != null && !proxy.equals("")) {
				proxyAddress = proxy;
			}
			if (port != null && !port.equals("")) {
				proxyProt = port;
			}

			c.close();

			if (// "1".equals(id) || //三星电信1为ctwap,2为ctnet
			(("CTWAP".equals(apn.toUpperCase()) || "CTWAP".equals(name
					.toUpperCase())) && !isEmpty(proxy) && !isEmpty(port)))
				return APNType.CTWAP;
			else if (// "2".equals(id) ||
			(("CTNET".equals(apn.toUpperCase()) || "CTNET".equals(name
					.toUpperCase())) && isEmpty(proxy) && isEmpty(port)))
				return APNType.CTNET;
			else if (("CMWAP".equals(apn.toUpperCase()) || "CMWAP".equals(name
					.toUpperCase())) && !isEmpty(proxy) && !isEmpty(port))
				return APNType.CMWAP;
			else if (("CMNET".equals(apn.toUpperCase()) || "CMNET".equals(name
					.toUpperCase())) && isEmpty(proxy) && isEmpty(port))
				return APNType.CMNET;
			else if (("3GWAP".equals(apn.toUpperCase()) || "3GWAP".equals(name
					.toUpperCase())) && !isEmpty(proxy) && !isEmpty(port))
				return APNType._3GWAP;
			else if (("3GNET".equals(apn.toUpperCase()) || "3GNET".equals(name
					.toUpperCase())) && isEmpty(proxy) && isEmpty(port))
				return APNType._3GNET;
			else
				return APNType.Unknow;

		} catch (Exception e) {
			// TODO: handle exception
			Log.e(tag, "getCurrentUsedAPNTypeException");
			return APNType.Unknow;
		}
	}

	public class ErrorMsg extends Throwable {
		private String errorStr;

		public ErrorMsg(String msg) {
			errorStr = msg;
		}

		public String toString() {
			return errorStr;
		}
	}

	public static boolean isEmpty(String str) {
		return str == null || "".equals(str);
	}
	
	public static boolean testWifi(Context ctx) {
		ConnectivityManager mConnectivity = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = mConnectivity.getActiveNetworkInfo();
		if (info == null || !mConnectivity.getBackgroundDataSetting()) {
			LogUtil.d(tag, "!mConnectivity.getBackgroundDataSetting()" + mConnectivity.getBackgroundDataSetting());
			return false;
		}
		int netType = info.getType();
		int netSubType = info.getSubtype();
		LogUtil.d(tag, "netType:" + netType + " netSubType:" + netSubType);
		if (ConnectivityManager.TYPE_WIFI == netType) {
			LogUtil.d(tag, "TYPE_WIFI");
			return info.isConnectedOrConnecting();
		}
		return false;
	}

}
