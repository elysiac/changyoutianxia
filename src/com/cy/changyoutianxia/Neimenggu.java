package com.cy.changyoutianxia;

import java.util.ArrayList;
import java.util.List;

import com.cy.util.CleanEditTextContent;
import com.cy.vo.CheciInfo;
import com.cy.vo.CheciVo;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;
import android.app.Activity;
import android.content.Context;

public class Neimenggu extends Activity {
	ListView checiList;
	private List<CheciVo> listmap=new ArrayList<CheciVo>();
	private boolean isCheci=true;
	CheciInfo checiInfo;
	CleanEditTextContent searchField;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainActivity.activityList.add(Neimenggu.this);
		setContentView(R.layout.activity_neimenggu);
		
		checiList = (ListView)findViewById(R.id.checiList);
		checiInfo = new CheciInfo(Neimenggu.this);
		searchField =(CleanEditTextContent)findViewById(R.id.searchField);
	}

	private OnEditorActionListener onEditorActionListener = new OnEditorActionListener() {
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			 InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
             //隐藏软键盘
             imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
             
             String str = v.getText().toString().trim();
             if(isCheci)
			{
            	 listmap = checiInfo.getInfoByCheci(str);
			}
             else
             {
            	 listmap = checiInfo.getInfoByZhanming(str);
             }
             
			if(listmap.size()==0)
			{
				Toast.makeText(Neimenggu.this, "该车次不存在，请重新输入", Toast.LENGTH_LONG).show();//提示用户
				searchField.setText("");
			}
			else
			{
				checiList.postInvalidate();
				ListViewAdapter listViewAdapter = new ListViewAdapter(Neimenggu.this); 
				checiList.setAdapter(listViewAdapter);	
			}
	
		return true;

		}

		};
	class ListViewAdapter extends BaseAdapter{
		LayoutInflater listContainer; 
		private Context context;   
		//		private boolean[] hasChecked;
		public ListViewAdapter(Context context){
			this.context=context;  
			listContainer = LayoutInflater.from(context);  
		}
		@Override
		public int getCount() {
			return listmap.size();
		}
		@Override
		public Object getItem(int position) {
			return null;
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = listContainer.inflate(R.layout.checi_list, null); 
			TextView title = (TextView)convertView.findViewById(R.id.title);
			TextView fromTo = (TextView)convertView.findViewById(R.id.fromTo);
			title.setText(listmap.get(position).getCheci());
			fromTo.setText(listmap.get(position).getFrom()+" —— "+listmap.get(position).getTo());
			convertView.setTag(position);
			return convertView;   
		} 
	}
}