package com.example.androidtestui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

public class MainActivity extends Activity {

	private ListView lv;
	private Button btn;
	private SlidingDrawer slidingDrawer;
	String date[] = { "海霓科技", "数码笔", "http://www.haini.com.cn", "研发部", "独一无二" };
	LinearLayout layout;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn = (Button) findViewById(R.id.handle);
		slidingDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer1);
		 layout = (LinearLayout) super.findViewById(R.id.content) ;
			lv = new ListView(this);
			lv.setAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_expandable_list_item_1, date));
			layout.addView(lv);
		slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			public void onDrawerOpened() {
				btn.setBackgroundResource(R.drawable.l);
			}
		});
		slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			public void onDrawerClosed() {
				btn.setBackgroundResource(R.drawable.a);
			}
		});
	}
	
}
