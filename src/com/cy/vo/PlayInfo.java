package com.cy.vo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cy.util.DatabaseHelper;

public class PlayInfo {
	private Context context;
	String dataTable;
	public PlayInfo(Context context){
		this.context = context;
		dataTable="play";
	}
	public List<PlayVo> getPlayByCity(String city_name){
		String sql = "select * from "+dataTable+" where city like '%"+city_name+"%'";
		return getInformation(sql);
	}
	
    protected List<PlayVo> getInformation(String sql){
		
		List<PlayVo> listPlayVo = new ArrayList<PlayVo>();
		DatabaseHelper helper = new DatabaseHelper(context);
		SQLiteDatabase db = helper.openDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			PlayVo playVo = new PlayVo();
			playVo.setId(cursor.getString(cursor.getColumnIndex("id")));
			playVo.setName(cursor.getString(cursor.getColumnIndex("name")));
			playVo.setAdr(cursor.getString(cursor.getColumnIndex("adr")));
			playVo.setIntro(cursor.getString(cursor.getColumnIndex("intro")));
			playVo.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
			playVo.setPrice(cursor.getString(cursor.getColumnIndex("price")));
			playVo.setPic(cursor.getString(cursor.getColumnIndex("pic")));
			playVo.setCity(cursor.getString(cursor.getColumnIndex("city")));
			listPlayVo.add(playVo);
		}
		cursor.close();
		db.close();
		
		return listPlayVo;
	}
}
