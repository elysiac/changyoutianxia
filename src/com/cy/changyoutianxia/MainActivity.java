package com.cy.changyoutianxia;

import java.util.ArrayList;
import java.util.List;

import com.cy.util.ImageUtil;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends Activity {

	private LinearLayout mainLayout;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageUtil imUtil = new ImageUtil();
		GroupActivity.activityList.add(MainActivity.this);
		mainLayout = (LinearLayout) findViewById(R.id.mainbg);
		mainLayout.setBackgroundDrawable(imUtil.getBitFromInner(
				MainActivity.this, R.drawable.bg2));
		Button nmg_btn;
		nmg_btn=(Button)findViewById(R.id.nmg_btn);
		nmg_btn.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,Neimenggu.class);
				startActivity(intent);
//				overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
//				finish();
			}
		});
	}

	
	
	
	

}
