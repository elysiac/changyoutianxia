package com.cy.intro;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
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
import com.cy.vo.TrainMenuInfo;
import com.cy.vo.TrainMenuVo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class TrainMenuActivity extends Activity {

	private ListView listView;
	private List<TrainMenuVo> listmap=new ArrayList<TrainMenuVo>();
	private ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trainmenu);
		MainActivity.activityList.add(TrainMenuActivity.this);
		listView = (ListView)findViewById(R.id.menuList);
		TrainMenuInfo trainMenuInfo = new TrainMenuInfo(this);
		listmap = trainMenuInfo.getAllTrainMenu();
		listView.postInvalidate();
		ListViewAdapter listViewAdapter = new ListViewAdapter(TrainMenuActivity.this); 
		listView.setAdapter(listViewAdapter);
//		initImageLoader(this);
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions  
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
			convertView = listContainer.inflate(R.layout.train_menu_list, null); 
			TextView name = (TextView)convertView.findViewById(R.id.name);
			TextView price = (TextView)convertView.findViewById(R.id.price);
			ImageView pic = (ImageView)convertView.findViewById(R.id.pic);
			String url = listmap.get(position).getPic().trim();
			if(url.equals("")||url==null){
			}
			else{
				imageLoader.displayImage(url, pic,options);
			}
			
			name.setText(listmap.get(position).getName());
			price.setText("价格："+listmap.get(position).getPrice()+" 元");
			convertView.setTag(position);
			return convertView;   
		} 
	}
	
//	public static void initImageLoader(Context context) {
//		// This configuration tuning is custom. You can tune every option, you
//		// may tune some of them,
//		// or you can create default configuration by
//		// ImageLoaderConfiguration.createDefault(this);
//		// method.
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove
//				.build();
//		// Initialize ImageLoader with configuration.
//		ImageLoader.getInstance().init(config);
//	}
}
