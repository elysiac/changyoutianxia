package com.cy.changyoutianxia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class StationActivity extends Activity {
	String checi;
	String stationName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainActivity.activityList.add(StationActivity.this);
		setContentView(R.layout.activity_station);
		Intent intent=this.getIntent();
		checi = intent.getStringExtra("checi");
		stationName = intent.getStringExtra("stationName");
		System.out.println("checi = "+checi);
		System.out.println("stationName = "+stationName);
	}

}
