package com.cy.changyoutianxia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
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
	private List<CheciVo> listmap=new ArrayList<CheciVo>();
	private boolean isCheci=true;
	CheciInfo checiInfo;
	CleanEditTextContent searchField;
	LinearLayout whole;
	int screenHeight;
	List<RelativeLayout> menus = new ArrayList<RelativeLayout>();
	List<View> views = new ArrayList<View>();
	int ii=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainActivity.activityList.add(CheciItemActivity.this);
		setContentView(R.layout.activity_checiitem);
		Intent intent=this.getIntent();
		String checi = intent.getStringExtra("checi");
		List<CheciItemVo> listCheciItemVo = new ArrayList<CheciItemVo>();
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
			TextView station = (TextView)convertView.findViewById(R.id.station1);
			station.setText(listCheciItemVo.get(i).getStation());
			RelativeLayout menu = (RelativeLayout)convertView.findViewById(R.id.menu);
			menus.add(menu);
			convertView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, screenHeight/listCheciItemVo.size()));
			views.add(convertView);
			whole.addView(convertView);
		}
		for(int i=0;i<listCheciItemVo.size();i++){
			
			views.get(i).setOnClickListener(new android.view.View.OnClickListener() {
				
				public void onClick(View v) {
//					Intent intent = new Intent(CheciItemActivity.this,StationActivity.class);
//					startActivity(intent);
					menus.get(ii).setVisibility(View.VISIBLE);
				}
			});
		}
		
	}

}
