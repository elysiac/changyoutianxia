package com.cy.intro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cy.changyoutianxia.GroupActivity;
import com.cy.changyoutianxia.MainActivity;
import com.cy.changyoutianxia.R;

public class IntroMainActivity extends Activity {

	private LinearLayout intro1_lay,intro2_lay,intro3_lay,intro4_lay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		MainActivity.activityList.add(IntroMainActivity.this);
		intro1_lay = (LinearLayout)findViewById(R.id.intro1_lay);
		intro2_lay = (LinearLayout)findViewById(R.id.intro2_lay);
		intro3_lay = (LinearLayout)findViewById(R.id.intro3_lay);
		intro4_lay = (LinearLayout)findViewById(R.id.intro4_lay);
		intro1_lay.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IntroMainActivity.this,TrainIntroActivity.class);
				startActivity(intent);
				finish();
//				overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
			}
		});
		intro2_lay.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IntroMainActivity.this,StationIntroActivity.class);
				startActivity(intent);
				finish();
//				overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
			}
		});
		intro3_lay.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IntroMainActivity.this,CityIntroActivity.class);
				startActivity(intent);
				finish();
//				overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
			}
		});
		intro4_lay.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IntroMainActivity.this,CaptureActivity.class);
				startActivity(intent);
				finish();
//				overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
			}
		});

	}

	
	
	
	

}
