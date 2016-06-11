package com.cy.util;

import java.util.ArrayList;
import java.util.List;

public class MyParameters {
	final static double[] CITY_JNN = {41.037129, 113.106447}; //集宁南113.106447,41.037129
	final static double[] CITY_ZZD = {40.930004, 112.626197}; //卓资东112.626197,40.930004
	final static double[] CITY_HHD = {40.855974, 111.771885}; //呼和浩特东111.771885,40.855974
	final static double[] CITY_HHT = {40.836327, 111.672281}; //呼和浩特111.672281,40.836327
	final static double[] CITY_SLQ = {40.57069, 110.537893}; //萨拉齐110.537893,40.57069
	final static double[] CITY_BTD = {40.576554, 110.031235}; //包头东 110.031235,40.576554
	final static double[] CITY_BT = {40.611930, 109.842569}; //包头
	final static double[] CITY_EES = {39.592796, 109.875941}; //鄂尔多斯
	static List<double[]> CITYS = new ArrayList<double[]>();
	public static List<double[]> getCityList(){
		CITYS.add(CITY_JNN);
		CITYS.add(CITY_HHT);
		CITYS.add(CITY_BT);
		CITYS.add(CITY_EES);
		return CITYS;
	}
	public static List<String> getCityNames(){
		List<String> cityNames = new ArrayList<String>();
		cityNames.add("集宁南");
		cityNames.add("呼和浩特");
		cityNames.add("包头");
		cityNames.add("鄂尔多斯");
		return cityNames;
	}
	//距离车站多少米时启动讲解
	public static double getMyRage(){
		double rage = 30000;
//		double rage = 10000;
		return rage;
	}

}
