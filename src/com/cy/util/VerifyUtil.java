package com.cy.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.provider.Settings;

public class VerifyUtil {
	
	/**
	 * 获得GPS状态
	 * @param locationManager
	 * @return
	 */
	public static boolean getGSPStatus(LocationManager locationManager){
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
	
	/**
	 * 判断网络状态
	 * @param connectivityManager
	 * @return	: true|连接/false|未连接
	 */
	public static boolean getNetworkStatus(Context content){

		ConnectivityManager connectivityManager = (ConnectivityManager) content.getSystemService(Context.CONNECTIVITY_SERVICE);
		//3G
		State mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		if(mobile==State.CONNECTED||mobile==State.CONNECTING)
			return true;

		//Wifi
		State wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if(wifi==State.CONNECTED||wifi==State.CONNECTING)
			return true;

		return false;
	}
	
	/**
	 * 网络设置提示
	 * @param content
	 */
	public static void alertNetSettings(final Context context){
		AlertDialog.Builder ab = new AlertDialog.Builder(context);
		ab.setTitle("无法连接网络，请检查网络配置");
//		ab.setMessage("无法连接网络，请检查网络配置");
		ab.setNegativeButton("退出", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				((Activity)context).finish();
			}
		});
		ab.setPositiveButton("配置", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				context.startActivity(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS));//ACTION_WIRELESS_SETTINGS
			}
		});
		ab.create().show();
	}	
	
	/**
	 * GSP设置提示
	 * @param content
	 */
	public static void alertGPSSettings(final Context context){
		AlertDialog.Builder ab = new AlertDialog.Builder(context);
		ab.setTitle("请在设置中开启GSP");
		//		ab.setMessage("无法连接网络，请检查网络配置");
		ab.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				((Activity)context).finish();
			}
		});
		ab.setPositiveButton("配置", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
			}
		});
		ab.create().show();
	}		
}


