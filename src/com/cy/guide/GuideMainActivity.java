package com.cy.guide;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.cy.changyoutianxia.GroupActivity;
import com.cy.changyoutianxia.MainActivity;
import com.cy.changyoutianxia.R;
import com.cy.intro.TrainIntroActivity;
import com.cy.maputil.LocationService;
import com.cy.maputil.MapUtils;
import com.cy.util.MapUtil;
import com.cy.util.MyApplication;
import com.cy.util.MyDialog;
import com.cy.util.MyParameters;

public class GuideMainActivity extends Activity {
	/**
	 * MapView 是地图主控件
	 */
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	double[] city1=new double[2];
	MapStatus mMapStatus;
	//location
	private LocationService locationService;
	TextView LocationResult;
	private LinkedList<LocationEntity> locationList = new LinkedList<LocationEntity>(); // 存放历史定位结果的链表，最大存放当前结果的前5次定位结果
	//Notify
    private LocationClient mLocationClient;
    private Vibrator mVibrator;
//    private List<NotifyLister> mNotifyLister_list = new ArrayList<NotifyLister>();
    private boolean isFirstLoc = true;
    private List<double[]> citys;
    private List<String> cityNames;
    private TextView map_text;
    private Button map_play;
    private String cityName = "";
    private double distance = 0;
    private double rage = 0;
    //原生location图标
    private SensorManager mSensorManager;
    private LocationMode mCurrentMode;
    private float[] accelerometerValues = new float[3];
    private float[] magneticFieldValues = new float[3];
    //方向传感器
    private Sensor accelerometer; // 加速度传感器
    private Sensor magnetic; // 地磁场传感器
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		MainActivity.activityList.add(GuideMainActivity.this);        
		mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		mCurrentMode = LocationMode.NORMAL;
		 mBaiduMap
         .setMyLocationConfigeration(new MyLocationConfiguration(
                 mCurrentMode, true, null));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15));
		locationService = ((MyApplication) getApplication()).locationService;
		LocationClientOption mOption = locationService.getDefaultLocationClientOption();
//		mOption.setLocationMode(LocationClientOption.LocationMode.Battery_Saving); 
		mOption.setCoorType("bd09ll");
		mOption.setOpenGps(true); // 打开gps
		mOption.setScanSpan(1000);
		// 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
		mLocationClient  = new LocationClient(this);
	    mLocationClient.registerLocationListener(mListener);
	    mLocationClient.setLocOption(mOption);
	    mLocationClient.start();
		locationService.setLocationOption(mOption);
		locationService.registerListener(mListener);
		locationService.start();
		// 实例化传感器管理者
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // 初始化加速度传感器
        accelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 初始化地磁场传感器
        magnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        
        
        //注册距离提醒监听
        citys = MyParameters.getCityList();
        cityNames = MyParameters.getCityNames();
        rage = MyParameters.getMyRage();
//      	for(int i=0;i<citys.size();i++){
//      		NotifyLister mNotifyLister = new NotifyLister();
//      		mNotifyLister.SetNotifyLocation(citys.get(i)[0], citys.get(i)[1], (float)rage,"gcj02");//4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
//      		mLocationClient.registerNotify(mNotifyLister);
//      		mNotifyLister_list.add(mNotifyLister);
//      	} 
      	map_text = (TextView)findViewById(R.id.map_text);
      	map_play = (Button)findViewById(R.id.map_play);
      	map_play.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {
				shouldOpen = true;
			}
		});
      	map_text.setText("正在定位，请稍后");
	}
	{
//	public void initOverlay() {
//		// add marker overlay
//		double[] adr = city1;
//		double[] adrSw = {adr[0]-0.002022,adr[1]+0.004395};
//		double[] adrNe = {adr[0]+0.00136,adr[1]-0.002342};
////		LatLng llA = new LatLng(40.611930, 109.842569);
//		LatLng llA = new LatLng(adr[0], adr[1]);
//		OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA)
//				.zIndex(9).draggable(true);
//		mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
//		// add ground overlay
////		LatLng southwest = new LatLng(40.609908, 109.846964);
////		LatLng northeast = new LatLng(40.61329, 109.840227);
//		LatLng southwest = new LatLng(adrSw[0], adrSw[1]);
//		LatLng northeast = new LatLng(adrNe[0], adrNe[1]);
//		LatLngBounds bounds = new LatLngBounds.Builder().include(northeast)
//				.include(southwest).build();
//
//		OverlayOptions ooGround = new GroundOverlayOptions()
//				.positionFromBounds(bounds).image(bdGround).transparency(0.8f);
//		mBaiduMap.addOverlay(ooGround);
//
//		MapStatusUpdate u = MapStatusUpdateFactory
//				.newLatLng(bounds.getCenter());
//		mBaiduMap.setMapStatus(u);
//
//		mBaiduMap.setOnMarkerDragListener(new OnMarkerDragListener() {
//			@Override
//			public void onMarkerDrag(Marker marker) {
//			}
//
//			@Override
//			public void onMarkerDragEnd(Marker marker) {
//				Toast.makeText(
//						GuideMainActivity.this,
//						"拖拽结束，新位置：" + marker.getPosition().latitude + ", "
//								+ marker.getPosition().longitude,
//						Toast.LENGTH_LONG).show();
//			}
//
//			@Override
//			public void onMarkerDragStart(Marker marker) {
//			}
//		});
//	}
	}

	@Override
	protected void onPause() {
		// activity 暂停时同时暂停地图控件
		mMapView.onPause();
		// 解除注册
        mSensorManager.unregisterListener(new MySensorEventListener());
        super.onPause();
	}

	@Override
	protected void onResume() {
		// activity 恢复时同时恢复地图控件
		mMapView.onResume();
		// 注册监听
        mSensorManager.registerListener(new MySensorEventListener(),
                accelerometer, Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(new MySensorEventListener(), magnetic,
                Sensor.TYPE_MAGNETIC_FIELD);
        super.onResume();
	}

	@Override
	protected void onDestroy() {
		// activity 销毁时同时销毁地图控件
		mMapView.onDestroy();
		// 取消监听 SDK 广播
		locationService.unregisterListener(mListener); //注销掉监听
		locationService.stop(); //停止定位服务
//		mLocationClient.removeNotifyEvent(mNotifyLister);
        mLocationClient = null;
        mListener= null;
        super.onDestroy();
	}
	
	
	/**
	 * 显示请求字符串
	 * 
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			if (LocationResult != null)
				LocationResult.setText(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/***
	 * Stop location service
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		locationService.unregisterListener(mListener); //注销掉监听
		locationService.stop(); //停止定位服务
//		for(int i=0;i<mNotifyLister_list.size();i++){
//			mLocationClient.removeNotifyEvent(mNotifyLister_list.get(i));
//		}
        mLocationClient = null;
        mListener= null;
//        mNotifyLister = null;
		super.onStop();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// -----------location config ------------
		locationService = ((MyApplication) getApplication()).locationService; 
		//获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
		locationService.registerListener(mListener);
		//注册监听
		int type = getIntent().getIntExtra("from", 0);
		if (type == 0) {
			locationService.setLocationOption(locationService.getDefaultLocationClientOption());
		} else if (type == 1) {
			locationService.setLocationOption(locationService.getOption());
		}
		locationService.start();
		
		  
	}

	
	/*****
	 * @see copy funtion to you project
	 * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
	 *
	 */
	Message locMsg;
	private BDLocationListener mListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (location == null || mMapView == null) {
                return;
            }
			if (null != location && location.getLocType() != BDLocation.TypeServerError) {
				locMsg = locHander.obtainMessage();
				Bundle locData_org;
				locData_org = Algorithm(location);
				if (locData_org != null) {
				    locData_org.putParcelable("loc", location);
				    locMsg.setData(locData_org);
				    locHander.sendMessage(locMsg);
			    }
					
			}
		}
		public void onReceivePoi(BDLocation poiLocation) {
        }

	};
	/***
	 * 平滑策略代码实现方法，主要通过对新定位和历史定位结果进行速度评分，
	 * 来判断新定位结果的抖动幅度，如果超过经验值，则判定为过大抖动，进行平滑处理,若速度过快，
	 * 则推测有可能是由于运动速度本身造成的，则不进行低速平滑处理 ╭(●｀∀´●)╯
	 * 
	 * @param BDLocation
	 * @return Bundle
	 */
	private Bundle Algorithm(BDLocation location) {
		Bundle locData = new Bundle();
		double curSpeed = 0;
		if (locationList.isEmpty() || locationList.size() < 2) {
			LocationEntity temp = new LocationEntity();
			temp.location = location;
			temp.time = System.currentTimeMillis();
			locData.putInt("iscalculate", 0);
			locationList.add(temp);
		} else {
			if (locationList.size() > 5)
				locationList.removeFirst();
			double score = 0;
			for (int i = 0; i < locationList.size(); ++i) {
				LatLng lastPoint = new LatLng(locationList.get(i).location.getLatitude(),
						locationList.get(i).location.getLongitude());
				LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
				double distance = DistanceUtil.getDistance(lastPoint, curPoint);
				curSpeed = distance / (System.currentTimeMillis() - locationList.get(i).time) / 1000;
				score += curSpeed * MapUtils.EARTH_WEIGHT[i];
			}
			if (score > 0.00000999 && score < 0.00005) { // 经验值,开发者可根据业务自行调整，也可以不使用这种算法
				location.setLongitude(
						(locationList.get(locationList.size() - 1).location.getLongitude() + location.getLongitude())
								/ 2);
				location.setLatitude(
						(locationList.get(locationList.size() - 1).location.getLatitude() + location.getLatitude())
								/ 2);
				locData.putInt("iscalculate", 1);
			} else {
				locData.putInt("iscalculate", 0);
			}
			LocationEntity newLocation = new LocationEntity();
			newLocation.location = location;
			newLocation.time = System.currentTimeMillis();
			locationList.add(newLocation);

		}
		return locData;
	}
	
	/***
	 * 接收定位结果消息，并显示在地图上
	 * 10次定位计算一次距离
	 */
	int ttime = 0;
	private Handler locHander = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			ttime++;
			try {
//				System.out.println("进入locHander");
				BDLocation location = msg.getData().getParcelable("loc");
				int iscal = msg.getData().getInt("iscalculate");
				if (location != null) {
					LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
					// 构建Marker图标
					BitmapDescriptor bitmap = null;
					
					//方向
					float[] values = new float[3];
			        float[] R = new float[9];
			        SensorManager.getRotationMatrix(R, null, accelerometerValues,
			                magneticFieldValues);
			        SensorManager.getOrientation(R, values);
//			        System.out.println("value[0]11111 = "+values[0]);
			        values[0] = (float) Math.toDegrees(values[0]);
//			        System.out.println("value[0]11111 = "+values[0]);
			        float deg = values[0];
					if(deg<0){
						deg=deg+360;
					}	
					
					MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(deg).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
                     mBaiduMap.setMyLocationData(locData);
                    if (isFirstLoc) {
                        isFirstLoc = false;
                        LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.target(ll).zoom(15.0f);
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    }
					
//					System.out.println("获取位置");
					if(ttime==10){
						System.out.println("计算距离");
						ttime=0;
						cityName = cityNames.get(0);
						MapUtil mapUtil = new MapUtil();
						distance = mapUtil.GetDistance(location.getLatitude(), location.getLongitude(), citys.get(0)[0], citys.get(0)[1]);
						for(int i=1;i<citys.size();i++){
							double distance2 = mapUtil.GetDistance(location.getLatitude(), location.getLongitude(), citys.get(i)[0], citys.get(i)[1]);
							if(distance2<distance){
								distance = distance2;
								cityName = cityNames.get(i);
							}
						}
						if(distance==0){
							map_text.setText("正在定位，请稍后");
						}
						else if(distance>=300000){
							map_text.setText("您已超出现有车次范围");
						}
						else if(distance<300000&&distance>rage){
							map_text.setText("当前位置距离"+cityName+"站还有"+distance+"公里");
							map_play.setVisibility(View.GONE);
						}
						else{
							map_text.setText("当前位置距离"+cityName+"站还有"+distance+"公里");
							map_play.setVisibility(View.VISIBLE);
							//弹出提示窗口
				        	if(shouldOpen&&!isOpen){
				        		MyDialog.Builder builder = new MyDialog.Builder(GuideMainActivity.this);
				        		String note = "距离"+cityName+"还有"+distance+"公里";
				        		builder.setMessage(note);
				        		builder.setTitle("导览信息");
				        		builder.setNegativeButton("取消",
				        				new android.content.DialogInterface.OnClickListener() {
				        					public void onClick(DialogInterface dialog, int which) {
				        						dialog.dismiss();
				        						isOpen = false;
				        						shouldOpen = false;
				        					}
				        				});
				        		builder.create().show();
				        		isOpen = true;
				        	}
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	};
	/**
	 * 封装定位结果和时间的实体类
	 * 
	 * @author baidu
	 *
	 */
	class LocationEntity {
		BDLocation location;
		long time;
	}
	//Notify
	boolean shouldOpen = true;
	boolean isOpen = false;
//    public class NotifyLister extends BDNotifyListener{
//        public void onNotify(BDLocation mlocation, float distance){
//            mVibrator.vibrate(1000);//振动提醒已到设定位置附近
//            Toast.makeText(GuideMainActivity.this, "震动提醒", Toast.LENGTH_SHORT).show();
//        	
        	//弹出提示窗口
//        	if(shouldOpen&&!isOpen){
//        		MyDialog.Builder builder = new MyDialog.Builder(GuideMainActivity.this);
//        		String note = "距离"+cityName+"还有"+distance+"米！";
//        		builder.setMessage(note);
//        		builder.setTitle("提示");
//        		builder.setNegativeButton("取消",
//        				new android.content.DialogInterface.OnClickListener() {
//        					public void onClick(DialogInterface dialog, int which) {
//        						dialog.dismiss();
//        						isOpen = false;
//        						shouldOpen = false;
//        					}
//        				});
//        		builder.create().show();
//        		isOpen = true;
//        	}
        	
//    		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//    			public void onClick(DialogInterface dialog, int which) {
//    				dialog.dismiss();
//    				//设置你的操作事项
//    			}
//    		});
//
    		
//        	System.out.println("进入范围");
//        	
//            
//        }
//    }
    
    
    
    //方向
    class MySensorEventListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelerometerValues = event.values;
            }
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magneticFieldValues = event.values;
            }
//            locHander.sendMessage(locMsg);;
        }
 
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
 
        }
 
    }
}
