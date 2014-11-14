package com.itaste.yuntu;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.itaste.yuntu.adapter.LeftSlideMenuListViewAdapter;
import com.itaste.yuntu.util.ItasteApplication;
import com.itaste.yuntu.util.LBSCloudUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnClickListener,OnTabChangeListener  {

	private SlidingMenu leftmenu;
	private Button menuBtn;
	private Button searchBtn;
	private TabHost tabHost;
	//private String[] menus=new String[]{"地图","列表","地图","列表","地图","列表","地图","列表"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);//主题布局
		initTabHost();//tab主页面
		initSlideMenu();//左侧菜单
		initNavBtn();
		
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.menuBtn:
			leftmenu.toggle(true);
			break;
		case R.id.searchBtn:
			//查询窗口
			startActivityForResult(new Intent(this, SearchActivity.class), ItasteApplication.MAIN_REQUEST_SEARCH_CODE);
			break;

		default:
			break;
		}
		
	}
	//查询回归之后
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(ItasteApplication.MAIN_REQUEST_SEARCH_CODE==requestCode&&resultCode==ItasteApplication.SEARCH_FAC_RESULT_CODE){
			LBSCloudUtils.search(this);
		}
	}
	

	  //初始化上方导航菜单及其事件
	 private void initNavBtn() {
		  menuBtn = (Button) this.findViewById(R.id.menuBtn);
		  searchBtn = (Button) this.findViewById(R.id.searchBtn);
		  //添加事件
		  menuBtn.setOnClickListener(this);
		  searchBtn.setOnClickListener(this);
		  
	}
	  
				
	//初始化侧边菜单
	private void initSlideMenu() {
				leftmenu = new SlidingMenu(this);
				leftmenu.setMode(SlidingMenu.LEFT);
				// 设置触摸屏幕的模式
				leftmenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				leftmenu.setShadowWidthRes(R.dimen.shadow_width);
				leftmenu.setShadowDrawable(R.drawable.shadow);

				// 设置滑动菜单视图的宽度
				leftmenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
				// 设置渐入渐出效果的值
				leftmenu.setFadeDegree(0.35f);
				leftmenu.showSecondaryMenu(true);
				/**
				 * SLIDING_WINDOW will include the Title/ActionBar in the content
				 * section of the SlidingMenu, while SLIDING_CONTENT does not.
				 */
				leftmenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
				
				//设置侧滑菜单
				View view= LayoutInflater.from(this).inflate(R.layout.left_menu_panel, null);
				ListView lvListView = (ListView) view.findViewById(R.id.left_menu_lv);
				//设置数据数据源和单击协议事件
				LeftSlideMenuListViewAdapter adapter =LeftSlideMenuListViewAdapter.getInstance(this);
				lvListView.setAdapter(adapter);
				lvListView.setOnItemClickListener(adapter);
				
				//为侧滑菜单设置布局
				leftmenu.setMenu(view);
	}
	//初始化tab搜索选项卡
	private void initTabHost() {
		tabHost = getTabHost();
		// 添加列表tab和地图tab
		tabHost.setup();
		
		 View tab1 = (View) LayoutInflater.from(this).inflate(R.layout.tabmini, null);  
	        TextView text0 = (TextView) tab1.findViewById(R.id.tab_label);  
	        text0.setText(getString(R.string._map)); 
	        View tab2 = (View) LayoutInflater.from(this).inflate(R.layout.tabmini, null);  
	        TextView text1 = (TextView) tab2.findViewById(R.id.tab_label);  
	        text1.setText(getString(R.string._list)); 
	        
		tabHost.addTab(tabHost.newTabSpec(getString(R.string._maptab))
				.setIndicator(tab1)
				.setContent(new Intent(this, LBSFacMapActivity.class)));
		tabHost.addTab(tabHost.newTabSpec(getString(R.string._listtab))
				.setIndicator(tab2)
				.setContent(new Intent(this,LBSFacListActivity.class)));
		//面板切换时触发
		tabHost.setOnTabChangedListener(this);
	}
	
  	
  	
  	 
  	//当前tab切换时，记录当前激活的activity
	@Override
	public void onTabChanged(String tabId) {
		ItasteApplication.getInstance().currentactivateView = tabId;
	}
  	 
	/*
	 * 添加对back按钮的处理，点击提示退出
	 * (non-Javadoc)
	 * @see android.app.Activity#dispatchKeyEvent(android.view.KeyEvent)
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() != 1) {
			exit();
			return true;
		}

		return super.dispatchKeyEvent(event);
	}
	/*
	 * 退出应用程序
	 */
	private void exit() {
		AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("提示")
				.setMessage(R.string.exit_confirm)
				.setPositiveButton(R.string.button_ok,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int whichButton) {
								finish();
								android.os.Process
										.killProcess(android.os.Process.myPid());
							}
						}).setNegativeButton(R.string.button_cancel, null).create();
			alertDialog.show();
	}

}
