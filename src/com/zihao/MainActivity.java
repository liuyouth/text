package com.zihao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zihao.adapter.GetMenu;
import com.zihao.adapter.STSongListAdapter;
import com.zihao.adapter.STSongListAdapter.ViewHolder;
import com.zihao.adapter.STSongMessage;
import com.zihao.adapter.tryhttp;
import com.zihao.service.MusicService1;
import com.zihao.ui.DragLayout;
import com.zihao.ui.DragLayout.DragListener;
import com.zihao.ui.RefreshableView.PullToRefreshListener;

public class MainActivity extends Activity {

	  
	    private ArrayList<STSongMessage> list;// 声明列表容器
	/** 声明变量 */
	private tryhttp tryhttp;
	private STSongListAdapter adapter;
	private ViewHolder mLastTouchTag = null;
	private String zhengze;
	private int sNameNumb=2,sIdNumb=3,sImgNumb=6,singerNumb=4;
	private DragLayout mDragLayout;
	private com.zihao.ui.RefreshableView refreshableView;
	private ListView menuListView, songListView;// 设置ListView变量
	private ImageButton menuSettingBtn;// 声明liftMenuBar 左边窗口
	private String[] sTMenuStrings = { "Home", "Anything",
			"China", "Cantonese", "English",
			"Other", "J&K" };
	private String indexMenu;
	private Date app;
	private TextView menuListTop;
	
	
	


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
	   app = (Date) getApplication();
		/**
		 * 设置列表 设置窗体
		 */
		refreshableView = (com.zihao.ui.RefreshableView) findViewById(R.id.refreshable_view);

		mDragLayout = (DragLayout) findViewById(R.id.dl);
		menuListTop = (TextView) findViewById(R.id.menuListTop);
		mDragLayout.setDragListener(new DragListener() {// 设置总窗体
					@Override
					public void onOpen() {
					}

					@Override
					public void onClose() {
					}

					@Override
					public void onDrag(float percent) {

					}
				});

		// 设置list数据

		List<Map<String, Object>> data1 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < sTMenuStrings.length; i++) {
			Map<String, Object> item;
			item = new HashMap<String, Object>();
			item.put("item", sTMenuStrings[i]);

			data1.add(item);
		}

		menuListView = (ListView) findViewById(R.id.menu_listview);
		menuListView.setCacheColorHint(Color.TRANSPARENT);
		menuListView.setAdapter(new SimpleAdapter(this, data1,
				R.layout.menulist_item_text, new String[] { "item" },
				new int[] { R.id.menu_text }));

		
		menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				sNameNumb=4	;	
				sIdNumb=3;
				sImgNumb=7;singerNumb=8;
				switch (arg2) {
				case 1:
					indexMenu =  getString(R.string.stm_url_yz_suoyou);
					zhengze = getString(R.string.stm_zhengze_yz);
					break;
				case 2:
					indexMenu =  getString(R.string.stm_url_yz_china);
					zhengze = getString(R.string.stm_zhengze_yz);
					break;
				case 3:
					indexMenu =  getString(R.string.stm_url_yz_yue);
					zhengze = getString(R.string.stm_zhengze_yz);
					break;
				case 4:
					indexMenu =  getString(R.string.stm_url_yz_english);
					zhengze = getString(R.string.stm_zhengze_yz);
					break;
				case 5:
					indexMenu =  getString(R.string.stm_url_yz_other);
					zhengze = getString(R.string.stm_zhengze_yz);
					break;
				case 6:
					indexMenu =  getString(R.string.stm_url_yz_rh);
					zhengze = getString(R.string.stm_zhengze_yz);
					break;

				default:
					sNameNumb=2;sIdNumb=3;sImgNumb=6;singerNumb=4;
					indexMenu =  getString(R.string.stm_url_dajiatuijian);
					zhengze = getString(R.string.stm_zhengze_dajiatuijian);
					break;
				}
				mDragLayout.close();
				listRefresh();
				Log.d("listindex", String.format("%s", arg2));
				menuListTop.setText(sTMenuStrings[arg2]);
				
			}
			
			
		});
		
		
		

		
		songListView = (ListView) findViewById(R.id.songlistView);
		indexMenu =  getString(R.string.stm_url_dajiatuijian);
		zhengze = getString(R.string.stm_zhengze_dajiatuijian);
		songListView.setItemsCanFocus(true);
		menuListTop.setText(sTMenuStrings[1]);
		try {
//			adapter = com.zihao.adapter.tryhttp.testGetHtml(MainActivity.this,
//					indexMenu,zhengze,sNameNumb,sIdNumb,singerNumb,sImgNumb);
//			System.out.print("Adapter成功");
			GetMenu getMenu = new GetMenu(this, songListView, indexMenu, zhengze, sNameNumb, sIdNumb, singerNumb, sImgNumb);
			getMenu.execute();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		songListView.setAdapter(adapter);
		
	songListView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
//			if (mLastTouchTag != null) {
//				View temp = arg0.findViewWithTag(mLastTouchTag);
//				if (temp != null) {
//					View footTemp = temp.findViewById(R.id.item_footer);
//					if (footTemp != null
//							&& (footTemp.getVisibility() != View.GONE)) {
//						footTemp.startAnimation(new ViewExpandAnimation(
//								footTemp));
//					}
//				}
//			}
//			mLastTouchTag = (ViewHolder) arg1.getTag();
//			// onion555 end
//			View footer = arg1.findViewById(R.id.item_footer);
//			footer.startAnimation(new ViewExpandAnimation(footer));
//			
//		}
//	});
			Toast.makeText(MainActivity.this, "ssssss222222222", Toast.LENGTH_SHORT).show();
		
		
		}});
		
		
	
		//

		// 设置列表上拉事件
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {

			@Override
			public void onRefresh() {

				/*
				 * doinbackground里面要做后台的任务操作,不能对UI组件修改,要在住进程中设置你的图片,
				 * onPostExecute这里面写UI操作就行. AsyncTask 安卓封装的轻量级异步处理 用子线程来进行工作
				 * 【这里是从网络中获得数据】 doinbackgroud 后台执行 这里是进行网络申请数据 onPostExecute
				 * 申请结束后处理的事件 现在这里用来处理ui
				 */
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						try {
							adapter = com.zihao.adapter.tryhttp.testGetHtml(
									MainActivity.this,
									indexMenu,zhengze,sNameNumb,sIdNumb,singerNumb,sImgNumb);
							Log.d("adapter try ", "adapter 成功");
						} catch (Exception e) {
							e.printStackTrace();
							Log.d("adapter try ", "adapter 失败");
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						songListView.setAdapter(adapter);
						adapter.notifyDataSetChanged();
						songListView.refreshDrawableState();
						refreshableView.finishRefreshing();
					}
				}.execute(null, null, null);
			}
		}, 0);

		// 设置左边窗体按钮 导航栏左边的bar
		menuSettingBtn = (ImageButton) findViewById(R.id.menu_imgbtn);
		menuSettingBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 原本打开左侧窗口的功能
				mDragLayout.open();
			}
		});
	}
	private void listRefresh() {
		new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... params) {
				try {
					adapter = com.zihao.adapter.tryhttp.testGetHtml(
							MainActivity.this,
							indexMenu,zhengze,sNameNumb,sIdNumb,singerNumb,sImgNumb);
					Log.d("adapter try ", "adapter 成功");
				} catch (Exception e) {
					e.printStackTrace();
					Log.d("adapter try ", "adapter 失败");
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				songListView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				songListView.refreshDrawableState();
				
			}
		}.execute(null, null, null);

	}
	
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK  
                && event.getAction() == KeyEvent.ACTION_DOWN) {  
  
            new AlertDialog.Builder(this)  
                    .setIcon(R.drawable.ic_launcher)  
                    .setTitle("退出")  
                    .setMessage("您确定要退出？")  
                    .setNegativeButton("取消", null)  
                    .setPositiveButton("确定",  
                            new DialogInterface.OnClickListener() {  
  
                                @Override  
                                public void onClick(DialogInterface dialog,  
                                        int which) {  
                                	app.getMusicService1().stopSelf();
                                	System.exit(0); // 停止后台服务  
                                }  
                            }).show();  
  
        }  
        return super.onKeyDown(keyCode, event);  
    }  

	
}
