package com.zihao.adapter;

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

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.zihao.PlayView;
import com.zihao.R.string;
import com.zihao.service.MusicService1;
import com.zihao.ui.ImageService;

public class tryhttp {

	public static String songName, songID, singer;
	public static Bitmap SingerImg;
	public static String zhengze;
	public static String songUri =null;
	public static STSongListAdapter adapter;
	public static ArrayList<STSongMessage> list;// 声明列表容器

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

	public static STSongListAdapter testGetHtml(Context context,
			String urlpath, String zhengze, int sNameNumb, int sIdNumb,
			int singerNumb, int sImgNumb) throws Exception {
		URL url = new URL(urlpath);

		tryhttp.zhengze = zhengze;
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(6 * 1000);
		conn.setRequestMethod("GET");

		if (conn.getResponseCode() == 200) {
Log.i("gethtml", "获取列表数据成功");
			InputStream inputStream = conn.getInputStream();
			byte[] data = readStream(inputStream);
			String html = new String(data, "gb2312");

			// 实例化列表容器
			list = new ArrayList<STSongMessage>();

			Pattern p = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE
					| Pattern.DOTALL);
			Matcher m = p.matcher(html);

			int index = 1;
			if (sNameNumb == 2) {

				while (m.find()) {

					songName = m.group(sNameNumb);
					songID = m.group(sIdNumb);
					String SingerImgStr = m.group(sImgNumb);
					singer =m.group(singerNumb);
					String regEx = "\"+"; // 表示一个或多个@
					Pattern pat = Pattern.compile(regEx);
					Matcher mat1 = pat.matcher(songName);
					songName = mat1.replaceAll("");
					songName = songName.substring(1, songName.length() - 1);
					Matcher mat4 = pat.matcher(singer);
					singer = mat4.replaceAll("");
					Matcher mat2 = pat.matcher(songID);
//					songUri = getSongUri(mat2.replaceAll("").substring(1,mat2.replaceAll("").length()));
//					songUri = getSongTureUrl(mat2.replaceAll("").substring(1,mat2.replaceAll("").length()), songUri);
					songID = mat2.replaceAll("");
					songID=songID.substring(1,songID.length());
					Matcher mat3 = pat.matcher(SingerImgStr);
					SingerImgStr = mat3.replaceAll("");
					SingerImgStr = SingerImgStr.trim();
				
					String urlPathContent = "http://image.songtaste.com/images/usericon/s/"
							+ SingerImgStr;

					try {
						byte[] data1 = ImageService.getImage(urlPathContent);
						SingerImg = BitmapFactory.decodeByteArray(data1, 0,
								data1.length); // 生成位图

					} catch (IOException e) {
						// 通知用户连接超时信息
						Log.d("获取图片失败了", e.toString());
					}
				
					list.add(new STSongMessage(songName, songID, singer,
							SingerImg,songUri,context,new Intent(context, PlayView.class)));

				}
			} else {
				while (m.find()) {
					if (index != 1) {

						songName = m.group(sNameNumb);
						songID = m.group(sIdNumb);
//						songUri = getSongUri(m.group(sIdNumb));
						String SingerImgStr = m.group(sImgNumb);
						singer = m.group(singerNumb);
						String regEx = "\"+"; // 表示一个或多个@
						Pattern pat = Pattern.compile(regEx);
						Matcher mat1 = pat.matcher(songName);
						songName = mat1.replaceAll("");
						Matcher mat4 = pat.matcher(singer);
						singer = mat4.replaceAll("");
						Matcher mat2 = pat.matcher(songID);
						songID = mat2.replaceAll("");
						songID=songID.substring(1,songID.length());
						Matcher mat3 = pat.matcher(SingerImgStr);
						SingerImgStr = mat3.replaceAll("");
						 SingerImgStr=SingerImgStr.trim();
			
							SingerImgStr = "http://"+getSingerIMG(SingerImgStr);
							
								
						String urlPathContent = (SingerImgStr);

						try {
							byte[] data1 = ImageService
									.getImage(urlPathContent);

							SingerImg = BitmapFactory.decodeByteArray(data1, 0,
									data1.length); // 生成位图

						} catch (IOException e) {
							// 通知用户连接超时信息
							Log.d("获取图片失败了", SingerImgStr);
						}

						
						
						list.add(new STSongMessage(songName, songID, singer,
								SingerImg,songUri,context,new Intent(context, PlayView.class)));
					
						}
					index++;
					}

			}
		
			adapter = new STSongListAdapter(context, list);
			System.out.println(list);

			return adapter;
		}
		
		
		
		
		return null;
	}
	

	public static String getSongUri(String songid) throws Exception {
		URL url1 = new URL("http://www.songtaste.com/song/"+songid+"/");
		HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
		conn.setConnectTimeout(6 * 1000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			byte[] data = readStream(inputStream);
			String html = new String(data, "gb2312");
			Pattern p = Pattern.compile("strURL =(.*?)\"(.*?)\"", Pattern.CASE_INSENSITIVE
					| Pattern.DOTALL);
			Matcher m = p.matcher(html);
				while (m.find()) {
					songUri = m.group(2);
					Log.d("uirkey", songUri);
					
					
				}}
		
		
		
		return songUri;
		
//			URI url = new URI("http://huodong.duomi.com/songtaste/?songid="+songid);
		
		
////	
	}
//	
//	public static String getSongTureUrl(String songid,String songurll) throws Exception{
//		
//		URI url = new URI("http://www.songtaste.com/time.php");
//		
//		HttpPost httpRequest = new HttpPost(url);   
//		/* 
//		 * Post运作传送变量必须用NameValuePair[]数组储存 
//		 */  
//		List <NameValuePair> params = new ArrayList <NameValuePair>();   
//		params.add(new BasicNameValuePair("str", songUri));   
//		params.add(new BasicNameValuePair("sid", songid));   
//		params.add(new BasicNameValuePair("t", "0"));   
//		
//		Log.d("uirsongidkey", songid);
//		try   
//		{   
//			/*发出HTTP request*/  
//			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));   
//			/*取得HTTP response*/  
//			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);   
//			/*若状态码为200 ok*/  
//			if(httpResponse.getStatusLine().getStatusCode() == 200)    
//			{   
//				/*取出响应字符串*/  
//				songUri = EntityUtils.toString(httpResponse.getEntity());   
////        mTextView1.setText(strResult);   
//			}   
//			else   
//			{   
////        mTextView1.setText("Error Response: "+httpResponse.getStatusLine().toString());   
//			}   
//		}   
//		catch (ClientProtocolException e)   
//		{    
////      mTextView1.setText(e.getMessage().toString());   
//			e.printStackTrace();   
//		}   
//		catch (IOException e)   
//		{    
////      mTextView1.setText(e.getMessage().toString());   
//			e.printStackTrace();   
//		}   
//		catch (Exception e)   
//		{    
////      mTextView1.setText(e.getMessage().toString());   
//			e.printStackTrace();    
//		}    
//		Log.d("tureurl", songUri);
//		return songUri;
//	}
	public static String getSingerIMG(String singerID) throws Exception{
		String singerIMGID=singerID;
		URL url = new URL("http://www.songtaste.com/user/"+singerID+"/");
System.out.println(url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(6 * 1000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			byte[] data = readStream(inputStream);
			String html = new String(data, "gb2312");
			Pattern p = Pattern.compile("src='http://(.*?)'", Pattern.CASE_INSENSITIVE
					| Pattern.DOTALL);
			Matcher m = p.matcher(html);
				while (m.find()) {
					singerIMGID = m.group(1);
		}}
		return singerIMGID;}

}
