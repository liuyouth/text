package com.zihao.adapter;

import java.util.ArrayList;

import com.zihao.R.string;
import com.zihao.service.MusicService1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;


public class STSongMessage {

	private String songName="";
	private String songID="";
	private Bitmap singerImg;
	private String singer;
	private String songUri;
	private Intent playIntent;
	private Context context ; 
	
	
	
	
	public STSongMessage(String songName ,String songID ,String singer,Bitmap songSingerImg,String uri,Context context, Intent playIntent) {
		
		this.songName=songName;
		this.songID=songID;
		this.singer=singer;
		this.singerImg=songSingerImg;
		this.songUri = uri;
		this.playIntent=playIntent;
		this.context = context ; 
	
	}
	
	public String getSongUri() {
		return songUri;
	}
	public void setSongUri(String songUri) {
		this.songUri = songUri;
	}
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Intent getPlayIntent() {
		return playIntent;
	}

	public void setPlayIntent(Intent playIntent) {
		this.playIntent = playIntent;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}
	
	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public Bitmap getSingerImg() {
		return singerImg;
	}

	public void setSingerImg(Bitmap songSingerImg) {
		this.singerImg = songSingerImg;
	}

	public String getSongID() {
		return songID;
	}

	public void setSongID(String songID) {
		this.songID = songID;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getSongName();
	}
    
	public void StartActivity(int isPlayingSong,ArrayList<STSongMessage> list){
		getPlayIntent().putExtra("songID", getSongID());
		getPlayIntent().putExtra("songName", getSongName());
		getPlayIntent().putExtra("songUrl", getSongUri());
		getPlayIntent().putExtra("singerimg", getSingerImg());
		getPlayIntent().putExtra("singer", getSinger());
		getPlayIntent().putExtra("isPlayingSong", isPlayingSong);
		
		
		
	Log.i("intent数据加载", "完毕"+getSongID());
		getContext().startActivity(getPlayIntent());
		
	}
    
	
}
