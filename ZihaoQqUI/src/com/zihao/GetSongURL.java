package com.zihao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class GetSongURL {
	private static String songUri=null;
	private static String stringExtra;
	

	public GetSongURL(String stringExtra) throws Exception {
		// TODO Auto-generated constructor stub
		GetSongURL.stringExtra=stringExtra;
		
	}

	public String getUrlPlay() throws Exception {
		 songUri =getSongUri(stringExtra);
		 return songUri;
	}
	
	
	public static String getSongUri(String songid) throws Exception {
		URL url1 = new URL("http://www.songtaste.com/song/"+songid+"/");
		HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
		conn.setConnectTimeout(6 * 1000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			System.out.println(conn.getResponseCode());
			InputStream inputStream = conn.getInputStream();
			byte[] data = readStream(inputStream);
			String html = new String(data, "gb2312");
			Pattern p = Pattern.compile("strURL =(.*?)\"(.*?)\"", Pattern.CASE_INSENSITIVE
					| Pattern.DOTALL);
			Matcher m = p.matcher(html);
				while (m.find()) {
					songUri = m.group(2);
					
					URI url = new URI("http://www.songtaste.com/time.php");
					HttpPost httpRequest = new HttpPost(url);   
					/* 
					 * Post运作传送变量必须用NameValuePair[]数组储存 
					 */ 
					List <NameValuePair> params = new ArrayList <NameValuePair>();
					params.add(new BasicNameValuePair("str", songUri));   
					params.add(new BasicNameValuePair("sid", songid));   
					params.add(new BasicNameValuePair("t", "0"));   
					Log.d("uirsongidkey", songid);
					try   
					{   
						/*发出HTTP request*/  
						httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));   
						/*取得HTTP response*/  
						HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);   
						/*若状态码为200 ok*/  
						System.out.println("第2个取到数据了");
						if(httpResponse.getStatusLine().getStatusCode() == 200)    
						{   
							/*取出响应字符串*/  
							songUri = EntityUtils.toString(httpResponse.getEntity());   
//			        mTextView1.setText(strResult);   
						}   
						else   
						{   
//			        mTextView1.setText("Error Response: "+httpResponse.getStatusLine().toString());   
						}   
					}   
					catch (ClientProtocolException e)   
					{    
//			      mTextView1.setText(e.getMessage().toString());   
						e.printStackTrace();   
					}   
					catch (IOException e)   
					{    
//			      mTextView1.setText(e.getMessage().toString());   
						e.printStackTrace();   
					}   
					catch (Exception e)   
					{    
//			      mTextView1.setText(e.getMessage().toString());   
						e.printStackTrace();    
					}    
					Log.d("tureurl", songUri);
					
					
				}
				}
		
		
		
		return songUri;
		
//			URI url = new URI("http://huodong.duomi.com/songtaste/?songid="+songid);
		
		
////	
	}
	


	public static String getSongTureUrl(String songid,String songurll) throws Exception{
		
		URI url = new URI("http://www.songtaste.com/time.php");
		
		HttpPost httpRequest = new HttpPost(url);   
		/* 
		 * Post运作传送变量必须用NameValuePair[]数组储存 
		 */  
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		
		params.add(new BasicNameValuePair("str", songUri));   
		params.add(new BasicNameValuePair("sid", songid));   
		params.add(new BasicNameValuePair("t", "0"));   
		
		Log.d("uirsongidkey", songid);
		try   
		{   
			/*发出HTTP request*/  
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));   
			/*取得HTTP response*/  
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);   
			/*若状态码为200 ok*/  
			if(httpResponse.getStatusLine().getStatusCode() == 200)    
			{   
				/*取出响应字符串*/  
				songUri = EntityUtils.toString(httpResponse.getEntity());   
//        mTextView1.setText(strResult);   
			}   
			else   
			{   
//        mTextView1.setText("Error Response: "+httpResponse.getStatusLine().toString());   
			}   
		}   
		catch (ClientProtocolException e)   
		{    
//      mTextView1.setText(e.getMessage().toString());   
			e.printStackTrace();   
		}   
		catch (IOException e)   
		{    
//      mTextView1.setText(e.getMessage().toString());   
			e.printStackTrace();   
		}   
		catch (Exception e)   
		{    
//      mTextView1.setText(e.getMessage().toString());   
			e.printStackTrace();    
		}    
		Log.d("tureurl", songUri);
		return songUri;
	}
	
	public static byte[] readStream(InputStream inputStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		while ((len = inputStream.read(buffer)) != -1) {
			byteArrayOutputStream.write(buffer, 0, len);
		}

		inputStream.close();
		byteArrayOutputStream.close();
		return byteArrayOutputStream.toByteArray();
	}
}
