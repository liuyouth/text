package com.zihao;

import java.io.IOException;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;

import com.zihao.adapter.STSongListAdapter;
import com.zihao.adapter.STSongMessage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayView extends Activity {

	private ImageView btn_play,btn_last,btn_next,btn_back;
	private View.OnClickListener paly_OnClickListener;
	private TextView lab_songName,lab_singr,lab_songid;
	private ImageView singerIMG;
	String mp3Info;
	private Date app;
	private SeekBar seekBar;
	private View.OnClickListener nextClickListener,lastClickListener;
	private ArrayList<String> list;
	private int isPlayingSong=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_playview);
	  
	     final  Date app = (Date) getApplication();  
	    try {
			findCustoms();
			app.getMusicService1().musicServiceOnCreate(seekBar);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    btn_play.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
			mp3Info = lab_songName.getText().toString();
			try {
				
				app.getMusicService1().play(mp3Info);
//				musicService1.play(mp3Info);
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			}
		});
	    btn_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				Intent intent = new Intent();
//	            intent.setClass(PlayView.this, MainActivity.class);
//	            startActivity(intent);
	            PlayView.this.finish();
			}
		});
	    
	    lastClickListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
			}
		};
		nextClickListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		};
	};

      
	    
	
	public void findCustoms() throws Exception {
		
		btn_play = (ImageView) findViewById(R.id.btn_play);
		btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_last = (ImageView) findViewById(R.id.btn_last);
		btn_last.setOnClickListener(lastClickListener);
		btn_next = (ImageView) findViewById(R.id.btn_next);
		btn_next.setOnClickListener(nextClickListener);
		lab_songName = (TextView) findViewById(R.id.lab_songname);
		lab_songid = (TextView) findViewById(R.id.lab_songid);
		lab_singr = (TextView) findViewById(R.id.lab_singer);
		singerIMG = (ImageView) findViewById(R.id.img_singerimg);
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
//		mp3Info=getIntent().getStringExtra("songUrl");
		lab_songid.setText(getIntent().getStringExtra("songID"));		
		lab_songName.setText(getIntent().getStringExtra("songName"));		
		lab_singr.setText(getIntent().getStringExtra("singer"));	
		isPlayingSong = getIntent().getIntExtra("isPlayingSong", isPlayingSong);
	
		singerIMG.setImageBitmap((Bitmap)getIntent().getParcelableExtra("singerimg"));
		Urltask urltask = new Urltask(lab_songName,getIntent().getStringExtra("songID"));
		urltask.execute();
		urltask = null;

//		System.out.println(mp3Info);
//		if (mp3Info=="") {
//			Toast.makeText(PlayView.this, "没有取到链接", Toast.LENGTH_SHORT).show();
//		}else if (mp3Info=="") {
//			Toast.makeText(PlayView.this, "没有取到链接", Toast.LENGTH_SHORT).show();
//			
//		}
//		GetSongURL getSongURL = new GetSongURL(getIntent().getStringExtra("songID"));
//	mp3Info=getSongURL.getUrlPlay();
//		mp3Info = "http://media7.songtaste.com/201504251039/e45f3312556d0f0d7ea10558fc95b3b0/7/7b/7b71d724f5b13c5ff12adb093e43f452.mp3";
//		mp3Info=lab_songName.getText().toString();
		
	}

}
