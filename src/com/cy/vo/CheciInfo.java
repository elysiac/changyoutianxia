package com.cy.vo;

import java.util.ArrayList;
import java.util.List;

import com.cy.vo.CheciVo;
import com.cy.util.DatabaseHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CheciInfo {
	private Context context;
	String dataTable;
	/**
	 * 根据车次查询
	 * @param id
	 * @return
	 */
	public CheciInfo(Context context){
		this.context = context;
		dataTable="train_list";
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
			listCheciVo.add(checiVo);
		}
		cursor.close();
		db.close();
		
		return listCheciVo;
	}

}
