package com.cy.util;

import com.baidu.mapapi.SDKInitializer;
import android.app.Application;
//1 包头，2 鄂尔多斯
public class MyApplication extends Application{  

	@Override  
    public void onCreate(){  
        super.onCreate();  
        SDKInitializer.initialize(this);
    }  
}  

