package com.cy.changyoutianxia;

import java.util.ArrayList;
import java.util.List;

import com.cy.guide.GuideMainActivity;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class GroupActivity  extends ActivityGroup {

    private ScrollView container = null;
	public static List activityList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置视图
        setContentView(R.layout.activity_group);

        container = (ScrollView) findViewById(R.id.containerBody);
        container.removeAllViews();
        container.addView(getLocalActivityManager().startActivity(
                "Module1",
                new Intent(GroupActivity.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                .getDecorView());
        
        GroupActivity.activityList.add(GroupActivity.this);

        // 模块1
        LinearLayout btnModule1 = (LinearLayout) findViewById(R.id.btnModule1);
        btnModule1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                container.removeAllViews();
                container.addView(getLocalActivityManager().startActivity(
                        "Module1",
                        new Intent(GroupActivity.this, MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        .getDecorView());
            }
        });

        // 模块2
        LinearLayout btnModule2 = (LinearLayout) findViewById(R.id.btnModule2);
        btnModule2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                container.removeAllViews();
                container.addView(getLocalActivityManager().startActivity(
                        "Module2",
                        new Intent(GroupActivity.this, GuideMainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        .getDecorView());
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
 									GroupActivity.this.finish();

 								}
 							}).create();
 		default:
 			return null;
 		}
 	}
}
