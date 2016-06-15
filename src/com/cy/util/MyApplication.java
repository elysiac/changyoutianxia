package com.cy.util;

import com.baidu.mapapi.SDKInitializer;
import com.cy.maputil.LocationService;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
      //创建默认的ImageLoader配置参数  
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration  
                .createDefault(this);  
          
        //Initialize ImageLoader with configuration.  
        ImageLoader.getInstance().init(configuration);
    }  
}  

