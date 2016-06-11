package com.cy.util;

import com.baidu.mapapi.SDKInitializer;
import com.cy.maputil.LocationService;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

public class MyApplication extends Application{  
	public LocationService locationService;
    public Vibrator mVibrator;
	@Override  
    public void onCreate(){  
        super.onCreate();  
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        WriteLog.getInstance().init(); // 初始化日志
        SDKInitializer.initialize(getApplicationContext());  
    }  
}  

