package com.example.androidtestui;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class PopWindowActivity extends Activity {

	private Button BtnView, Btnhide;
	PopupWindow myPopWindow;
	View PopView;
	private ListView list;
	private LinearLayout title_layout;
	public static final String KEY = "key";
	ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
	Context mContext;

	String items1[] = { "2012", "海纳百川", "有容乃大", "雄心壮志" };

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popwindow);
		initView();

	}

	public void initView() {
		BtnView = (Button) findViewById(R.id.popwindow_button1);
		Btnhide = (Button) findViewById(R.id.popwindow_button2);
		BtnView.setOnClickListener(listener);
		Btnhide.setOnClickListener(listener);
	}

	private OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			// 显示
			if (v.getId() == R.id.popwindow_button1) {
				Toast.makeText(getApplicationContext(), "^0^", 1).show();
				isPopwindows();
			}
			// 隐藏
			if (v.getId() == R.id.popwindow_button2) {
				Toast.makeText(getApplicationContext(), "...", 1).show();
			}
		}
	};

	protected void isPopwindows() {
		LayoutInflater inflater_layout = LayoutInflater
				.from(PopWindowActivity.this);
		PopView = inflater_layout.inflate(R.layout.tetspopwindow, null);
		myPopWindow = new PopupWindow(PopView, 400, 500, true);
		ListView listPop = (ListView) PopView.findViewById(R.id.pop_listView1);
		listPop.setBackgroundColor(Color.LTGRAY);
		listPop.setAdapter(new ArrayAdapter<String>(PopWindowActivity.this,
				android.R.layout.simple_list_item_1, items1));
		
		Button btnPop = (Button) PopView.findViewById(R.id.Pop_button);

		btnPop.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myPopWindow.dismiss();
			}
		});

		 myPopWindow.showAtLocation(BtnView, Gravity.CENTER, 0, 0);
	}
}
