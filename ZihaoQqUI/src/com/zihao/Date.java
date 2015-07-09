package com.zihao;

import com.zihao.service.MusicService1;

import android.app.Application;

public class Date extends Application {

	private MusicService1 musicService1;
	private String mp3info;
	

	
	public String getMp3info() {
		return mp3info;
	}



	public void setMp3info(String mp3info) {
		this.mp3info = mp3info;
	}



	public MusicService1 getMusicService1() {
		return musicService1;
	}



	public void setMusicService1(MusicService1 musicService1) {
		this.musicService1 = musicService1;
	}



	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		musicService1 = new MusicService1();
		super.onCreate();
	}
}
