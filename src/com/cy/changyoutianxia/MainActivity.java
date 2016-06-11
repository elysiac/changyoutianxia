package com.cy.changyoutianxia;

import java.util.ArrayList;
import java.util.List;

import com.cy.util.ImageUtil;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends Activity {

	private LinearLayout mainLayout;
	public static List activityList = new ArrayList();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageUtil imUtil = new ImageUtil();
		MainActivity.activityList.add(MainActivity.this);
//		mainLayout = (LinearLayout) findViewById(R.id.mainbg);
//		mainLayout.setBackgroundDrawable(imUtil.getBitFromInner(
//				MainActivity.this, R.drawable.bg2));
		Button nmg_btn,nmg_btn2;
		nmg_btn=(Button)findViewById(R.id.nmg_btn);
		nmg_btn2=(Button)findViewById(R.id.nmg_btn2);
		nmg_btn.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,GroupActivity.class);
				startActivity(intent);
//				overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
//				finish();
			}
		});
		nmg_btn2.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,GroupActivity.class);
				startActivity(intent);
//				overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
//				finish();
			}
		});
	}

	
	// finish所有list中的activity
	 	public static void killall() {
	 		int siz = activityList.size();
	 		for (int i = 0; i < siz; i++) {
	 			if (activityList.get(i) != null) {
	 				((Activity) activityList.get(i)).finish();
	 			}
	 		}
	 	}

	 	/**
	 	 * 键盘事件
	 	 */
	 	@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
	 		// 返回事件
	 		if (keyCode == KeyEvent.KEYCODE_BACK) {
	 			showDialog(0);
	 			return true;
	 		} else {
	 			return super.onKeyDown(keyCode, event);
	 		}
	 	}

	 	/**
	 	 * 提示
	 	 */
	 	@Override
		protected Dialog onCreateDialog(int id, Bundle args) {
	 		switch (id) {
	 		case 0:
	 			return new AlertDialog.Builder(this)
	 					// .setTitle("提示")
	 					.setMessage("确定退出程序吗？")
	 					.setIcon(android.R.drawable.ic_dialog_info)
	 					.setNegativeButton(
	 							"取消",
	 							new android.content.DialogInterface.OnClickListener() {
	 								@Override
									public void onClick(DialogInterface dialog,
	 										int which) {
	 									dialog.cancel();
	 								}
	 							})
	 					.setPositiveButton(
	 							"确定",
	 							new android.content.DialogInterface.OnClickListener() {
	 								@Override
									public void onClick(DialogInterface dialog,
	 										int which) {
	 									dialog.cancel();
	 									// System.exit(0);
	 									// MainActivity.killall();
	 									MainActivity.this.finish();

	 								}
	 							}).create();
	 		default:
	 			return null;
	 		}
	 	}
	
	

}
