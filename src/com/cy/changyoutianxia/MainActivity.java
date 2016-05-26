package com.cy.changyoutianxia;

import com.cy.util.ImageUtil;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.app.Activity;

public class MainActivity extends Activity {
	private LinearLayout mainLayout;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageUtil imUtil = new ImageUtil();
		
		mainLayout = (LinearLayout)findViewById(R.id.mainbg); 
		mainLayout.setBackgroundDrawable(imUtil.getBitFromInner(MainActivity.this, R.drawable.bg));
	}


}
