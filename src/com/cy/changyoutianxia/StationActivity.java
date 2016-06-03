package com.cy.changyoutianxia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class StationActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainActivity.activityList.add(StationActivity.this);
		setContentView(R.layout.activity_station);
	}

}
