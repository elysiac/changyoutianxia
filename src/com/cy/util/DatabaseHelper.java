package com.cy.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class DatabaseHelper{
	private Context context;
	public static int APP_RUN_MODE = 0;//运行模式 0 开发 1 发布
	public static float VERSION = 1.0f;//数据库版本
	public static String NAME = ""; //保存的数据库文件名
	public static String PACKAGE_NAME = "";//包名
	private String dbPath = "";  //在手机里存放数据库的位置
	private final int BUFFER_SIZE = 400000;

	public DatabaseHelper(Context context) {
		this.context = context;
		dbPath = "/data"+Environment.getDataDirectory().getAbsolutePath()+"/"+PACKAGE_NAME+"/databases";
	}

	public SQLiteDatabase openDatabase() {
		return SQLiteDatabase.openOrCreateDatabase(dbPath + "/" + NAME,null);
	}
	
	/**
	 * 创建数据库
	 */
	public void createDatabase() {
		//判断数据库文件是否存在,如果存在删除目标数据库
		File dbFile = new File(dbPath + "/" + NAME);
		//数据库存在
		if (dbFile.exists()) {
			//数据库是否需要更新
			if(checkDataBase()){
				dbFile.delete();
				this.copyDataBase();
//				copyDataBaseFromInet();
			}else{
				if(APP_RUN_MODE==0){
					dbFile.delete();
					this.copyDataBase();
//					copyDataBaseFromInet();
				}
			}
		}else{
			this.copyDataBase();
//			copyDataBaseFromInet();
		}
	}

	/**
	 * 将应用assets目录中的数据库文件拷贝到指定目录
	 */
	private void copyDataBase(){
		try {
			//应用assets目录中的数据库文
			InputStream is = context.getAssets().open(NAME);
			//数据库目录是否存在，不存在创建目录
			File dbDir = new File(dbPath);
			boolean exi = dbDir.exists();
			if (!dbDir.exists()) {
				dbDir.mkdirs();
			}
			//拷贝文件
			FileOutputStream fos = new FileOutputStream(dbPath + "/" + NAME);
			byte[] buffer = new byte[BUFFER_SIZE];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				fos.write(buffer, 0, count);
			}
			fos.close();
			is.close();
			
			File dbFile = new File(dbPath + "/" + NAME);
			String oth = ""+dbFile.length();
			boolean exi2 = dbFile.exists();
			boolean read = dbFile.canRead();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public InputStream getStream(String path){  
        URL url = null;  
        InputStream is =null;  
        try {  
        	url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();//利用HttpURLConnection对象,我们可以从网络中获取网页数据.  
            conn.setDoInput(true);  
            conn.connect();  
            is = conn.getInputStream(); //得到网络返回的输入流  
              
        } catch (IOException e) {  
//            e.printStackTrace(); 
        	try {
				is = context.getAssets().open(NAME);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }  
        catch(Exception e)
        {
        	e.printStackTrace(); 
        }
        return is;  
    }  
	
	/**
	 * 检查数据库是否需要更新
	 * @return boolean true:是；false:否
	 */
	private boolean checkDataBase(){
		boolean isUpdate = false;
		
		SQLiteDatabase db = this.openDatabase();
		String sql = "select value from app_config where key='db_version'";
		Cursor cursor = db.rawQuery(sql, null);
		float value = 0;
		if(cursor.moveToNext()){
			value = cursor.getFloat(cursor.getColumnIndex("value"));
		}
		cursor.close();
		db.close();
		if(VERSION>value){
			isUpdate = true;
		}
		return isUpdate;
	}
	
	
	
}
