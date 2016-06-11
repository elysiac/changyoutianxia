package com.cy.changyoutianxia;

import com.cy.guide.GuideMenuActivity;
import com.cy.intro.IntroMainActivity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

public class GroupActivity extends ActivityGroup {

    private LinearLayout container = null;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置视图
        setContentView(R.layout.activity_group);

        container = (LinearLayout) findViewById(R.id.containerBody);
        container.removeAllViews();
        container.addView(getLocalActivityManager().startActivity(
                "Module1",
                new Intent(GroupActivity.this, IntroMainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                .getDecorView());
        
        MainActivity.activityList.add(GroupActivity.this);

        // 模块1
        LinearLayout btnModule1 = (LinearLayout) findViewById(R.id.btnModule1);
        btnModule1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                container.removeAllViews();
                container.addView(getLocalActivityManager().startActivity(
                        "Module1",
                        new Intent(GroupActivity.this, IntroMainActivity.class)
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
                        new Intent(GroupActivity.this, GuideMenuActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        .getDecorView());
            }
        });
    }
    
    
 
}
