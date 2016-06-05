package com.cy.changyoutianxia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import com.cy.util.CleanEditTextContent;
import com.cy.vo.CheciInfo;
import com.cy.vo.CheciItemVo;
import com.cy.vo.CheciVo;

public class CheciItemActivity extends Activity {
	ListView checiList;
	CheciInfo checiInfo;
	CleanEditTextContent searchField;
	LinearLayout whole;
	int screenHeight;
	List<RelativeLayout> menus = new ArrayList<RelativeLayout>();
	List<View> views = new ArrayList<View>();
	List<CheciItemVo> listCheciItemVo;
	List<String> stationNames;
	String checi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GroupActivity.activityList.add(CheciItemActivity.this);
		setContentView(R.layout.activity_checiitem);
		Intent intent=this.getIntent();
		checi = intent.getStringExtra("checi");
		listCheciItemVo = new ArrayList<CheciItemVo>();
		stationNames = new ArrayList<String>();
		CheciInfo checiInfo = new CheciInfo(CheciItemActivity.this);
		listCheciItemVo = checiInfo.getCheciItem(checi);
		whole = (LinearLayout)findViewById(R.id.whole);
		WindowManager windowManager = getWindowManager();    
        Display display = windowManager.getDefaultDisplay();    
//        int screenWidth = display.getWidth();    
        screenHeight = display.getHeight()-20;
		
		for(int i=0;i<listCheciItemVo.size();i++){
			LayoutInflater listContainer = LayoutInflater.from(this);  
			View convertView = listContainer.inflate(R.layout.checi_item_list, null); 
			TextView station = (TextView)convertView.findViewById(R.id.station);
			String stationName = listCheciItemVo.get(i).getStation();
			station.setText(stationName);
			TextView menustationname1 = (TextView)convertView.findViewById(R.id.menustationname1);
			TextView menustationname2 = (TextView)convertView.findViewById(R.id.menustationname2);
			TextView menustationname3 = (TextView)convertView.findViewById(R.id.menustationname3);
			menustationname1.setText(stationName);
			menustationname2.setText(stationName);
			menustationname3.setText(stationName);
//			stationNames.add(stationName);
			convertView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, screenHeight/listCheciItemVo.size()));
			views.add(convertView);
			whole.addView(convertView);
		}
		for(int i=0;i<listCheciItemVo.size();i++){
			views.get(i).setOnClickListener(new android.view.View.OnClickListener() {
				public void onClick(View v) {
					for(int i=0;i<listCheciItemVo.size();i++){
						RelativeLayout menu = (RelativeLayout)views.get(i).findViewById(R.id.menu);
						menu.setVisibility(View.GONE);
					}
					RelativeLayout menu = (RelativeLayout)v.findViewById(R.id.menu);
					if(menu.getVisibility()==View.VISIBLE){
						menu.setVisibility(View.GONE);
					}
					else{
						menu.setVisibility(View.VISIBLE);
					}
				}
			});
			
			LinearLayout menu1 = (LinearLayout)views.get(i).findViewById(R.id.menu1);
			LinearLayout menu2 = (LinearLayout)views.get(i).findViewById(R.id.menu2);
			LinearLayout menu3 = (LinearLayout)views.get(i).findViewById(R.id.menu3);
			menu1.setOnClickListener(new android.view.View.OnClickListener() {
				public void onClick(View v) {
					TextView menustationname1 = (TextView)v.findViewById(R.id.menustationname1);
					String stationName = menustationname1.getText().toString();
					Intent intent = new Intent(CheciItemActivity.this,StationActivity.class);
					intent.putExtra("checi", checi);
					intent.putExtra("stationName", stationName);
					startActivity(intent);
				}
			});
			menu2.setOnClickListener(new android.view.View.OnClickListener() {
				public void onClick(View v) {
					TextView menustationname1 = (TextView)v.findViewById(R.id.menustationname2);
					String stationName = menustationname1.getText().toString();
					Intent intent = new Intent(CheciItemActivity.this,StationActivity.class);
					intent.putExtra("checi", checi);
					intent.putExtra("stationName", stationName);
					startActivity(intent);
				}
			});
			menu3.setOnClickListener(new android.view.View.OnClickListener() {
				public void onClick(View v) {
					TextView menustationname1 = (TextView)v.findViewById(R.id.menustationname3);
					String stationName = menustationname1.getText().toString();
					Intent intent = new Intent(CheciItemActivity.this,StationActivity.class);
					intent.putExtra("checi", checi);
					intent.putExtra("stationName", stationName);
					startActivity(intent);
				}
			});
			
		}
		
	}
	

}
