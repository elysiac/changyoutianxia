package com.cy.guide;

import com.cy.changyoutianxia.MainActivity;
import com.cy.changyoutianxia.R;
import com.cy.intro.TrainIntroActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class GuideMenuActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide_menu);
		MainActivity.activityList.add(GuideMenuActivity.this);
		LinearLayout guide1_lay = (LinearLayout)findViewById(R.id.guide1_lay);
		guide1_lay.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GuideMenuActivity.this,GuideMainActivity.class);
				startActivity(intent);
//				overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
//				finish();
			}
		});
		LinearLayout guide2_lay = (LinearLayout)findViewById(R.id.guide2_lay);
		guide2_lay.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GuideMenuActivity.this,PoiSearchActivity.class);
				startActivity(intent);
//				overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
//				finish();
			}
		});
	}
}
