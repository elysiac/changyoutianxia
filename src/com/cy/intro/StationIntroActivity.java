package com.cy.intro;

import java.util.ArrayList;
import java.util.List;

import com.cy.changyoutianxia.GroupActivity;
import com.cy.changyoutianxia.MainActivity;
import com.cy.changyoutianxia.R;
import com.cy.util.CleanEditTextContent;
import com.cy.vo.CheciVo;
import com.cy.vo.CityInfo;
import com.cy.vo.CityVo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;

public class StationIntroActivity extends Activity {
	ListView cityList;
	CityInfo cityInfo;
	int index = 0;
	LinearLayout listLayout;
	RelativeLayout content_layout;
	TextView cityText,contentText;
	Button btn1,btn2;
	private List<CityVo> listcity=new ArrayList<CityVo>();
	CleanEditTextContent searchField;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainActivity.activityList.add(StationIntroActivity.this);
		setContentView(R.layout.activity_stationintro);
		
		cityList = (ListView)findViewById(R.id.cityList);
		cityInfo = new CityInfo(this);
		searchField =(CleanEditTextContent)findViewById(R.id.searchField);
		searchField.setOnEditorActionListener(onEditorActionListener);
		listcity = cityInfo.getCityAll();
		cityList.postInvalidate();
		ListViewAdapter listViewAdapter = new ListViewAdapter(this); 
		cityList.setAdapter(listViewAdapter);
		
		listLayout = (LinearLayout)findViewById(R.id.list_layout);
		content_layout = (RelativeLayout)findViewById(R.id.content_layout);
		cityText = (TextView)findViewById(R.id.cityText);
		contentText = (TextView)findViewById(R.id.contentText);
		btn1 = (Button)findViewById(R.id.btn1);
		btn2 = (Button)findViewById(R.id.btn2);
		btn1.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CityVo cityVo = listcity.get(index);
				String city = cityVo.getCity_name();
				Intent intent = new Intent();
				intent.putExtra("city", city);
				intent.setClass(StationIntroActivity.this, ShopActivity.class);
				StationIntroActivity.this.startActivity(intent);
			}
		});
		btn2.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listLayout.setVisibility(View.VISIBLE);
				content_layout.setVisibility(View.GONE);
			}
		});
		
		
		cityList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				index=(Integer)arg1.getTag();
				CityVo cityVo = listcity.get(index);
				String cityName = cityVo.getCity_name();
				String intro = cityVo.getStationintro();
				listLayout.setVisibility(View.GONE);
				content_layout.setVisibility(View.VISIBLE);
				cityText.setText(cityName);
				contentText.setText(intro);
			}
		});
	}

	private OnEditorActionListener onEditorActionListener = new OnEditorActionListener() {
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			 InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
             //隐藏软键盘
             imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
             
             String str = v.getText().toString().trim();
             listcity = cityInfo.getCityByName(str);
             
			if(listcity.size()==0)
			{
				Toast.makeText(StationIntroActivity.this, "该城市不在当前省，请重新输入", Toast.LENGTH_LONG).show();//提示用户
				searchField.setText("");
			}
			else
			{
				cityList.postInvalidate();
				ListViewAdapter listViewAdapter = new ListViewAdapter(StationIntroActivity.this); 
				cityList.setAdapter(listViewAdapter);	
			}
	
		return true;

		}

		};
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
			return listcity.size();
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
			convertView = listContainer.inflate(R.layout.city_list, null); 
			TextView title = (TextView)convertView.findViewById(R.id.title);
			title.setText(listcity.get(position).getCity_name());
			convertView.setTag(position);
			return convertView;   
		} 
	}
	
	/**
 	 * 键盘事件
 	 */
 	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
 		// 返回事件
 		if (keyCode == KeyEvent.KEYCODE_BACK) {
 			Intent intent = new Intent();
			intent.setClass(StationIntroActivity.this, GroupActivity.class);
			StationIntroActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right); 
			finish();
 			return true;
 		} else {
 			return super.onKeyDown(keyCode, event);
 		}
 	}

}
