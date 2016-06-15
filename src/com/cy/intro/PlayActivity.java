package com.cy.intro;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cy.changyoutianxia.MainActivity;
import com.cy.changyoutianxia.R;
import com.cy.vo.PlayInfo;
import com.cy.vo.PlayVo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class PlayActivity extends Activity {

	private ListView listView;
	private List<PlayVo> listmap=new ArrayList<PlayVo>();
	private String city;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		MainActivity.activityList.add(PlayActivity.this);
		listView = (ListView)findViewById(R.id.playList);
		Intent intent=this.getIntent();
		city = intent.getStringExtra("city");
		PlayInfo playInfo = new PlayInfo(this);
		listmap = playInfo.getPlayByCity(city);
		listView.postInvalidate();
		ListViewAdapter listViewAdapter = new ListViewAdapter(PlayActivity.this); 
		listView.setAdapter(listViewAdapter);
		options = new DisplayImageOptions.Builder()  
        .showStubImage(R.drawable.face2)          // 设置图片下载期间显示的图片  
        .showImageForEmptyUri(R.drawable.face)  // 设置图片Uri为空或是错误的时候显示的图片  
        .showImageOnFail(R.drawable.face)       // 设置图片加载或解码过程中发生错误显示的图片      
        .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中  
        .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中  
        .displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片  
        .build();                                   // 创建配置过得DisplayImageOption对象  
	}

	class ListViewAdapter extends BaseAdapter{
		LayoutInflater listContainer; 
		private Context context;   
		//		private boolean[] hasChecked;
		public ListViewAdapter(Context context){
			this.context=context;  
			listContainer = LayoutInflater.from(context);  
		}
		@Override
		public int getCount() {
			return listmap.size();
		}
		@Override
		public Object getItem(int position) {
			return null;
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = listContainer.inflate(R.layout.shop_list, null); 
			TextView name = (TextView)convertView.findViewById(R.id.name);
			TextView adr = (TextView)convertView.findViewById(R.id.adr);
			TextView phone = (TextView)convertView.findViewById(R.id.phone);
			ImageView pic = (ImageView)convertView.findViewById(R.id.pic);
			String url = listmap.get(position).getPic().trim();
			if(url.equals("")||url==null){
			}
			else{
				imageLoader.displayImage(url, pic,options);
			}
			
			name.setText(listmap.get(position).getName());
			adr.setText("地址："+listmap.get(position).getAdr());
			phone.setText("电话："+listmap.get(position).getPhone());
			convertView.setTag(position);
			return convertView;   
		} 
	}
}

