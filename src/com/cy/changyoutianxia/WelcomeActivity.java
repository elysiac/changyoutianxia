package com.cy.changyoutianxia;

import com.cy.util.DatabaseHelper;
import com.cy.util.ImageUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;

public class WelcomeActivity extends Activity {

	//布局
	private LinearLayout welcomeLayout;
//	Bitmap img;
//	Canvas c;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		ImageUtil imUtil = new ImageUtil();
		
		welcomeLayout = (LinearLayout)findViewById(R.id.welcomeLayout); 
		welcomeLayout.setBackgroundDrawable(imUtil.getBitFromInner(WelcomeActivity.this, R.drawable.bg));
//		//拷贝需要写入的图片来创建画布
//		img = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_background).copy(Bitmap.Config.ARGB_8888, true);  
//		c = new Canvas(img);
		//跳转
		this.FadeInAnimation();
	}
	
	
	
	/**
	 * 淡入淡出动画
	 */
	public void FadeInAnimation(){
		AlphaAnimation animation = new AlphaAnimation(1.0f, 1.0f);
		animation.setDuration(3000);
		animation.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation animation) {
				initData();
				Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.fade, R.anim.hold);
			}
			
			public void onAnimationStart(Animation animation) {}
			
			public void onAnimationRepeat(Animation animation) {}
		});
		welcomeLayout.setAnimation(animation);
	}
	
	/**
	 * 返回按键事件
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return true;
		}else{		
			return super.onKeyDown(keyCode, event);
		}
	}
	
	
	private void initData(){
		Resources res = this.getResources();
		//数据库参数
        DatabaseHelper.NAME = res.getString(R.string.db_name); 
        DatabaseHelper.VERSION = Float.parseFloat(res.getString(R.string.db_version));
        DatabaseHelper.PACKAGE_NAME = this.getPackageName();
        DatabaseHelper.APP_RUN_MODE = Integer.parseInt(res.getString(R.string.app_runn_mode));
        //创建数据库
        new DatabaseHelper(WelcomeActivity.this).createDatabase();
	}
	
	protected void onDestroy() {
		welcomeLayout.setBackgroundDrawable(null);
		super.onDestroy();
	}
}
