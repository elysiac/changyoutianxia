package com.cy.vo;

import java.util.ArrayList;
import java.util.List;

import com.cy.util.DatabaseHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TrainMenuInfo {
	private Context context;
	String dataTable;
	public TrainMenuInfo(Context context){
		this.context = context;
		dataTable="train_menu";
	}
	public List<TrainMenuVo> getAllTrainMenu(){
		String sql = "select * from "+dataTable;
		return getInformation(sql);
	}
    protected List<TrainMenuVo> getInformation(String sql){
		
		List<TrainMenuVo> listTrainMenuVo = new ArrayList<TrainMenuVo>();
		DatabaseHelper helper = new DatabaseHelper(context);
		SQLiteDatabase db = helper.openDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			TrainMenuVo trainMenuVo = new TrainMenuVo();
			trainMenuVo.setId(cursor.getString(cursor.getColumnIndex("id")));
			trainMenuVo.setName(cursor.getString(cursor.getColumnIndex("name")));
			trainMenuVo.setPrice(cursor.getString(cursor.getColumnIndex("price")));
			trainMenuVo.setPic(cursor.getString(cursor.getColumnIndex("pic")));
			listTrainMenuVo.add(trainMenuVo);
		}
		cursor.close();
		db.close();
		
		return listTrainMenuVo;
	}

}
