package com.cy.vo;

import java.util.ArrayList;
import java.util.List;

import com.cy.vo.CheciVo;
import com.cy.vo.CheciItemVo;
import com.cy.util.DatabaseHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CheciInfo {
	private Context context;
	String dataTable,dataTable2;
	/**
	 * 根据车次查询
	 * @param id
	 * @return
	 */
	public CheciInfo(Context context){
		this.context = context;
		dataTable="train_list";
		dataTable2 = "train";
	}
	public List<CheciItemVo> getCheciItem(String checi){
		String sql = "select * from "+dataTable2+" where checi is '"+checi+"'";
		return getInformation2(sql);
	}
	public List<CheciItemVo> getCheciItemByCity(String checi,String cityName){
		String sql = "select * from "+dataTable2+" where checi is '"+checi+"' and station is '"+cityName+"'";
		return getInformation2(sql);
	} 
	
	public List<CheciVo> getInfoAll(){
		String sql = "select * from "+dataTable;
		return getInformation(sql);
	}
	
	public List<CheciVo> getInfoByCheci(String checi){
		String sql = "select * from "+dataTable+" where CHECI like '%"+checi+"%'";
		return getInformation(sql);
	}
	
	public List<CheciVo> getInfoByZhanming(String zhanming){
		String sql = "select * from "+dataTable+" where [FROM] like '%"+zhanming+"%' or [TO] like '%"+zhanming+"%'";
		return getInformation(sql);
	}
	
	protected List<CheciVo> getInformation(String sql){
		
		List<CheciVo> listCheciVo = new ArrayList<CheciVo>();
		DatabaseHelper helper = new DatabaseHelper(context);
		SQLiteDatabase db = helper.openDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			CheciVo checiVo = new CheciVo();
			checiVo.setId(cursor.getString(cursor.getColumnIndex("id")));
			checiVo.setCheci(cursor.getString(cursor.getColumnIndex("checi")));
			checiVo.setType(cursor.getString(cursor.getColumnIndex("type")));
			checiVo.setFrom(cursor.getString(cursor.getColumnIndex("from")));
			checiVo.setTo(cursor.getString(cursor.getColumnIndex("to")));
			checiVo.setDuration(cursor.getString(cursor.getColumnIndex("duration")));
			checiVo.setMileage(cursor.getString(cursor.getColumnIndex("mileage")));
			checiVo.setIntro(cursor.getString(cursor.getColumnIndex("intro")));
			listCheciVo.add(checiVo);
		}
		cursor.close();
		db.close();
		
		return listCheciVo;
	}
	
     protected List<CheciItemVo> getInformation2(String sql){
		List<CheciItemVo> listCheciItemVo = new ArrayList<CheciItemVo>();
		DatabaseHelper helper = new DatabaseHelper(context);
		SQLiteDatabase db = helper.openDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			CheciItemVo checiItemVo = new CheciItemVo();
			checiItemVo.setId(cursor.getString(cursor.getColumnIndex("id")));
			checiItemVo.setCheci(cursor.getString(cursor.getColumnIndex("checi")));
			checiItemVo.setType(cursor.getString(cursor.getColumnIndex("type")));
			checiItemVo.setStation(cursor.getString(cursor.getColumnIndex("station")));
			checiItemVo.setOrder(cursor.getString(cursor.getColumnIndex("order")));
			checiItemVo.setDay(cursor.getString(cursor.getColumnIndex("day")));
			checiItemVo.setArrive_time(cursor.getString(cursor.getColumnIndex("arrive_time")));
			checiItemVo.setLeave_time(cursor.getString(cursor.getColumnIndex("leave_time")));
			checiItemVo.setStay_time(cursor.getString(cursor.getColumnIndex("stay_time")));
			checiItemVo.setDuration(cursor.getString(cursor.getColumnIndex("duration")));
			checiItemVo.setMileage(cursor.getString(cursor.getColumnIndex("mileage")));
			checiItemVo.setSecond_seat(cursor.getString(cursor.getColumnIndex("second_seat")));
			checiItemVo.setFirst_seat(cursor.getString(cursor.getColumnIndex("first_seat")));
			checiItemVo.setSecond_bed(cursor.getString(cursor.getColumnIndex("second_bed")));
			checiItemVo.setFirst_bed(cursor.getString(cursor.getColumnIndex("first_bed")));
			listCheciItemVo.add(checiItemVo);
		}
		cursor.close();
		db.close();
		
		return listCheciItemVo;
	}

}
