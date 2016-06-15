package com.cy.intro;

import java.util.ArrayList;
import java.util.List;

import com.cy.changyoutianxia.GroupActivity;
import com.cy.changyoutianxia.MainActivity;
import com.cy.changyoutianxia.R;
import com.cy.changyoutianxia.R.anim;
import com.cy.changyoutianxia.R.id;
import com.cy.changyoutianxia.R.layout;
import com.cy.util.CleanEditTextContent;
import com.cy.vo.CheciInfo;
import com.cy.vo.CheciVo;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class TrainIntroActivity extends Activity {
	ListView checiList;
	private List<CheciVo> listmap=new ArrayList<CheciVo>();
	private boolean isCheci=true;
	CheciInfo checiInfo;
	CleanEditTextContent searchField;
	int index = 0;
	LinearLayout listLayout;
	RelativeLayout content_layout;
	TextView checiText,contentText;
	Button btn1,btn2,btn3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainActivity.activityList.add(TrainIntroActivity.this);
		setContentView(R.layout.activity_trainintro);
		
		checiList = (ListView)findViewById(R.id.checiList);
		checiInfo = new CheciInfo(TrainIntroActivity.this);
		searchField =(CleanEditTextContent)findViewById(R.id.searchField);
		searchField.setOnEditorActionListener(onEditorActionListener);
		listmap = checiInfo.getInfoAll();
		checiList.postInvalidate();
		ListViewAdapter listViewAdapter = new ListViewAdapter(TrainIntroActivity.this); 
		checiList.setAdapter(listViewAdapter);
		
		listLayout = (LinearLayout)findViewById(R.id.list_layout);
		content_layout = (RelativeLayout)findViewById(R.id.content_layout);
		checiText = (TextView)findViewById(R.id.checiText);
		contentText = (TextView)findViewById(R.id.contentText);
		btn1 = (Button)findViewById(R.id.btn1);
		btn2 = (Button)findViewById(R.id.btn2);
		btn3 = (Button)findViewById(R.id.btn3);
		btn1.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CheciVo checiVo = listmap.get(index);
				String checiName = checiVo.getCheci();
				Intent intent = new Intent();
				intent.putExtra("checi", checiName);
				intent.setClass(TrainIntroActivity.this, CheciItemActivity.class);
				TrainIntroActivity.this.startActivity(intent);
			}
		});
		btn2.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				CheciVo checiVo = listmap.get(index);
//				String checiName = checiVo.getCheci();
				Intent intent = new Intent();
//				intent.putExtra("checi", checiName);
				intent.setClass(TrainIntroActivity.this, TrainMenuActivity.class);
				TrainIntroActivity.this.startActivity(intent);
			}
		});
		btn3.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listLayout.setVisibility(View.VISIBLE);
				content_layout.setVisibility(View.GONE);
			}
		});
		
		checiList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				index=(Integer)arg1.getTag();
				CheciVo checiVo = listmap.get(index);
				String checiName = checiVo.getCheci();
				String intro = checiVo.getIntro();
				listLayout.setVisibility(View.GONE);
				content_layout.setVisibility(View.VISIBLE);
				checiText.setText(checiName);
				contentText.setText(intro);
			}
		});
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
				Toast.makeText(TrainIntroActivity.this, "该车次不存在，请重新输入", Toast.LENGTH_LONG).show();//提示用户
				searchField.setText("");
			}
			else
			{
				checiList.postInvalidate();
				ListViewAdapter listViewAdapter = new ListViewAdapter(TrainIntroActivity.this); 
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
	
	/**
 	 * 键盘事件
 	 */
 	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
 		// 返回事件
 		if (keyCode == KeyEvent.KEYCODE_BACK) {
 			Intent intent = new Intent();
			intent.setClass(TrainIntroActivity.this, GroupActivity.class);
			TrainIntroActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right); 
			finish();
 			return true;
 		} else {
 			return super.onKeyDown(keyCode, event);
 		}
 	}

}