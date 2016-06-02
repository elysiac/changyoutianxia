package com.cy.changyoutianxia;

import java.util.ArrayList;
import java.util.List;

import com.cy.util.ImageUtil;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

public class MainActivity extends Activity {
	public static List activityList = new ArrayList();
	private LinearLayout mainLayout;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageUtil imUtil = new ImageUtil();
		MainActivity.activityList.add(MainActivity.this);
		mainLayout = (LinearLayout) findViewById(R.id.mainbg);
		mainLayout.setBackgroundDrawable(imUtil.getBitFromInner(
				MainActivity.this, R.drawable.bg));
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
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							})
					.setPositiveButton(
							"确定",
							new android.content.DialogInterface.OnClickListener() {
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
