package com.cy.vo;

import java.util.ArrayList;
import java.util.List;

import com.cy.util.DatabaseHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CityInfo {
	private Context context;
	String dataTable;
	public CityInfo(Context context){
		this.context = context;
		dataTable="city";
	}
	public List<CityVo> getCityByName(String city_name){
		String sql = "select * from "+dataTable+" where city_name like '%"+city_name+"%'";
		return getInformation(sql);
	}
	public List<CityVo> getCityAll(){
		String sql = "select * from "+dataTable;
		return getInformation(sql);
	}
    protected List<CityVo> getInformation(String sql){
		
		List<CityVo> listCityVo = new ArrayList<CityVo>();
		DatabaseHelper helper = new DatabaseHelper(context);
		SQLiteDatabase db = helper.openDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			CityVo cityVo = new CityVo();
			cityVo.setId(cursor.getString(cursor.getColumnIndex("id")));
			cityVo.setCity_name(cursor.getString(cursor.getColumnIndex("city_name")));
			cityVo.setStationintro(cursor.getString(cursor.getColumnIndex("stationintro")));
			cityVo.setCityintro(cursor.getString(cursor.getColumnIndex("cityintro")));
			listCityVo.add(cityVo);
		}
		cursor.close();
		db.close();
		
		return listCityVo;
	}

}
