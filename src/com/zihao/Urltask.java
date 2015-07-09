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

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class Urltask extends AsyncTask<Integer, Integer, String> {
	// ����������ڷֱ��ǲ��������������߳���Ϣʱ�䣩������(publishProgress�õ�)������ֵ ����
	private static String songUri = null;
	private static String stringExtra=null;
	private static TextView tv;
	
	
	
	public Urltask(TextView tv,String songid) {
		// TODO Auto-generated constructor stub
		stringExtra = songid;

		Urltask.tv = tv;
		
	}

	

	@Override
	protected void onPreExecute() {
		// ��һ��ִ�з���
		super.onPreExecute();
		

	}

	@Override
	protected String doInBackground(Integer... params) {
		// �ڶ���ִ�з���,onPreExecute()ִ�����ִ��
		try {
			getSongUri(stringExtra);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return songUri;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		// ���������doInBackground����publishProgressʱ��������Ȼ����ʱֻ��һ������
		// ��������ȡ������һ������,����Ҫ��progesss[0]��ȡֵ
		// ��n����������progress[n]��ȡֵ

		super.onProgressUpdate(progress[0]);
	}

	@Override
	public void onPostExecute(String result) {
		// doInBackground����ʱ���������仰˵������doInBackgroundִ����󴥷�
		// �����result��������doInBackgroundִ�к�ķ���ֵ������������"ִ�����"
		super.onPostExecute(result);
		tv.setText(result);
		
//		app.setMp3info(songUri);
		
		
	}
	

	public static String getSongUri(String songid) throws Exception {
//		URL url1 = new URL("http://www.songtaste.com/song/" + songid + "/");
		URL url1 = new URL("http://www.songtaste.com/playmusic.php?song_id=" + songid + "/");
		HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
		conn.setConnectTimeout(6 * 1000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			System.out.println("������ҳ��ȡ�ɹ�");
			InputStream inputStream = conn.getInputStream();
			byte[] data = readStream(inputStream);
			String html = new String(data, "gb2312");
			Pattern p = Pattern.compile("WrtSongLine((.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?));",
					Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = p.matcher(html);
			System.out.println(html);
			while (m.find()) {
				songUri = m.group(8);
				String regEx = "\"+"; // ��ʾһ������@
				Pattern pat = Pattern.compile(regEx);
				Matcher mat1 = pat.matcher(songUri);
				songUri= mat1.replaceAll("");
				songUri=songUri.substring(1, songUri.length()-1);
				Log.i("��һ������ȡ��������", songUri);
				URI url = new URI("http://www.songtaste.com/time.php");
				HttpPost httpRequest = new HttpPost(url);
				/*
				 * Post�������ͱ���������NameValuePair[]���鴢��
				 */
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("str", songUri));
				params.add(new BasicNameValuePair("sid", songid));
				params.add(new BasicNameValuePair("t", "0"));
				try {
					/* ����HTTP request */
					httpRequest.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					/* ȡ��HTTP response */
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(httpRequest);
					/* ��״̬��Ϊ200 ok */
					System.out.println("��2��ȡ��������");
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						/* ȡ����Ӧ�ַ��� */
						songUri = EntityUtils
								.toString(httpResponse.getEntity());
						// mTextView1.setText(strResult);
					} else {
						// mTextView1.setText("Error Response: "+httpResponse.getStatusLine().toString());
					}
				} catch (ClientProtocolException e) {
					// mTextView1.setText(e.getMessage().toString());
					e.printStackTrace();
				} catch (IOException e) {
					// mTextView1.setText(e.getMessage().toString());
					e.printStackTrace();
				} catch (Exception e) {
					// mTextView1.setText(e.getMessage().toString());
					e.printStackTrace();
				}
				Log.d("tureurl111", songUri);

			}}
			if (songUri=="http://songtaste.com/404.html?3=") {
				
			
				URL url2 = new URL("http://www.songtaste.com/song/" + songid + "/");
//				URL url1 = new URL("http://www.songtaste.com/playmusic.php?song_id=" + songid + "/");
				HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
				conn2.setConnectTimeout(6 * 1000);
				conn2.setRequestMethod("GET");
				if (conn.getResponseCode() == 200) {
					System.out.println("������ҳ��ȡ�ɹ�");
					InputStream inputStream2 = conn2.getInputStream();
					byte[] data2 = readStream(inputStream2);
					String html2 = new String(data2, "gb2312");
					Pattern p2 = Pattern.compile("strURL =(.*?)\"(.*?)\"",
							Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
					Matcher m2 = p2.matcher(html2);
					if (m2.find()) {
						songUri = m2.group(2);
						
						Log.i("�ڶ�������ȡ��������", songUri);
						URI url = new URI("http://www.songtaste.com/time.php");
						HttpPost httpRequest = new HttpPost(url);
						/*
						 * Post�������ͱ���������NameValuePair[]���鴢��
						 */
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("str", songUri));
						params.add(new BasicNameValuePair("sid", songid));
						params.add(new BasicNameValuePair("t", "0"));
						try {
							/* ����HTTP request */
							httpRequest.setEntity(new UrlEncodedFormEntity(params,
									HTTP.UTF_8));
							/* ȡ��HTTP response */
							HttpResponse httpResponse = new DefaultHttpClient()
									.execute(httpRequest);
							/* ��״̬��Ϊ200 ok */
							System.out.println("��2��ȡ��������");
							if (httpResponse.getStatusLine().getStatusCode() == 200) {
								/* ȡ����Ӧ�ַ��� */
								songUri = EntityUtils
										.toString(httpResponse.getEntity());
								// mTextView1.setText(strResult);
							} else {
								// mTextView1.setText("Error Response: "+httpResponse.getStatusLine().toString());
							}
						} catch (ClientProtocolException e) {
							// mTextView1.setText(e.getMessage().toString());
							e.printStackTrace();
						} catch (IOException e) {
							// mTextView1.setText(e.getMessage().toString());
							e.printStackTrace();
						} catch (Exception e) {
							// mTextView1.setText(e.getMessage().toString());
							e.printStackTrace();
						}
						Log.d("tureurl222", songUri);
				
			}}}

			Log.d("doinback", "�������û������Ļ�����ֱ��������"+songUri+songid);
		return songUri;

		// URI url = new
		// URI("http://huodong.duomi.com/songtaste/?songid="+songid);

		// //
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
