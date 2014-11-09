package com.itaste.yuntu;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.itaste.yuntu.adapter.LeftSlideMenuListViewAdapter;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initTabHost();
		initSlideMenu();
	}
	
	//初始化侧边菜单
	private void initSlideMenu() {
		// configure the SlidingMenu
				SlidingMenu menu = new SlidingMenu(this);
				menu.setMode(SlidingMenu.LEFT);
				// 设置触摸屏幕的模式
				menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				menu.setShadowWidthRes(R.dimen.shadow_width);
				menu.setShadowDrawable(R.drawable.shadow);

				// 设置滑动菜单视图的宽度
				menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
				// 设置渐入渐出效果的值
				menu.setFadeDegree(0.35f);
				menu.showSecondaryMenu(true);
				/**
				 * SLIDING_WINDOW will include the Title/ActionBar in the content
				 * section of the SlidingMenu, while SLIDING_CONTENT does not.
				 */
				menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
				
				//设置侧滑菜单
				View view= LayoutInflater.from(this).inflate(R.layout.left_menu_panel, null);
				ListView lvListView = (ListView) view.findViewById(R.id.left_menu_lv);
				//设置数据数据源和单击协议事件
				LeftSlideMenuListViewAdapter adapter =LeftSlideMenuListViewAdapter.getInstance(this);
				lvListView.setAdapter(adapter);
				lvListView.setOnItemClickListener(adapter);
				
				//为侧滑菜单设置布局
				menu.setMenu(view);
	}
	/**
	 * 初始化tab搜索选项卡
	 */
	private void initTabHost() {
		final TabHost tabHost =getTabHost();
		// 添加列表tab和地图tab
		tabHost.setup();
		
		 View tab1 = (View) LayoutInflater.from(this).inflate(R.layout.tabmini, null);  
	        TextView text0 = (TextView) tab1.findViewById(R.id.tab_label);  
	        text0.setText(getString(R.string._map)); 
	        View tab2 = (View) LayoutInflater.from(this).inflate(R.layout.tabmini, null);  
	        TextView text1 = (TextView) tab2.findViewById(R.id.tab_label);  
	        text1.setText(getString(R.string._list)); 
	        
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator(tab1)
				.setContent(new Intent(this, LBSFacMapActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("tab2")
				.setIndicator(tab2)
				.setContent(new Intent(this,LBSFacListActivity.class)));
	}


}
