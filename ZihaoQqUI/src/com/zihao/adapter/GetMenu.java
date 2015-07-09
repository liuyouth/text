package com.zihao.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

public class GetMenu extends AsyncTask<Integer, Integer, STSongListAdapter> {
	private STSongListAdapter adapter;
	private tryhttp tryhttp;
	private Context context;
	private ListView songListView;
	private String indexMenu;
	private String zhengze;
	private int sNameNumb=2,sIdNumb=3,sImgNumb=6,singerNumb=4;
	
	
	
	public GetMenu(Context context,ListView songListView,String indexMenu,String zhengze,int sNameNumb,int sIdNumb,int singerNumb,int sImgNumb) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.songListView=songListView;
		this.indexMenu=indexMenu;
		this.zhengze=zhengze;
		
		this.sNameNumb =sNameNumb;
		this.sIdNumb =sIdNumb;
		this.sImgNumb=sImgNumb;
		this.singerNumb =singerNumb;
		
		
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	@Override
	protected void onPostExecute(STSongListAdapter result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		songListView.setAdapter(result);
	}
	@Override
	protected STSongListAdapter doInBackground(Integer... arg0) {
		// TODO Auto-generated method stub
		try {
			adapter = com.zihao.adapter.tryhttp.testGetHtml(context,
					indexMenu,zhengze,sNameNumb,sIdNumb,singerNumb,sImgNumb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adapter;
	}
}
