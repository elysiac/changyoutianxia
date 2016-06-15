package com.cy.vo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cy.util.DatabaseHelper;

public class ShopInfo {
	private Context context;
	String dataTable;
	public ShopInfo(Context context){
		this.context = context;
		dataTable="shop";
	}
	public List<ShopVo> getShopByCity(String city_name){
		String sql = "select * from "+dataTable+" where city like '%"+city_name+"%'";
		return getInformation(sql);
	}
	
    protected List<ShopVo> getInformation(String sql){
		
		List<ShopVo> listShopVo = new ArrayList<ShopVo>();
		DatabaseHelper helper = new DatabaseHelper(context);
		SQLiteDatabase db = helper.openDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			ShopVo shopVo = new ShopVo();
			shopVo.setId(cursor.getString(cursor.getColumnIndex("id")));
			shopVo.setName(cursor.getString(cursor.getColumnIndex("name")));
			shopVo.setAdr(cursor.getString(cursor.getColumnIndex("adr")));
			shopVo.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
			shopVo.setPic(cursor.getString(cursor.getColumnIndex("pic")));
			shopVo.setCity(cursor.getString(cursor.getColumnIndex("city")));
			listShopVo.add(shopVo);
		}
		cursor.close();
		db.close();
		
		return listShopVo;
	}

}
