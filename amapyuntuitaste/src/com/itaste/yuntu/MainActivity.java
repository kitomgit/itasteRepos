package com.itaste.yuntu;


import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.itaste.yuntu.adapter.LeftSlideMenuListViewAdapter;
import com.itaste.yuntu.util.ItasteApplication;
import com.itaste.yuntu.util.LBSCloudUtils;
import com.itaste.yuntu.widget.WhiteDialog;
import com.itaste.yuntu.widget.WhiteDialog.WhiteDialogClickListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnClickListener,OnTabChangeListener  {

	private SlidingMenu leftmenu;
	private Button menuBtn,searchBtn;
	private TabHost tabHost;
	private ImageView zwsearchiv;
	private EditText keywords;
	private Spinner nearby;
	//private String[] menus=new String[]{"地图","列表","地图","列表","地图","列表","地图","列表"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);//主题布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.main_title_bar);
		initTabHost();//tab主页面
		//initSlideMenu();//左侧菜单
		initNavBtn();
	}
	

	@Override
	public void onClick(View view) {
		switch (view.getId()){
		/*case R.id.menuBtn:
			leftmenu.toggle(true);
			break;*/
		case R.id.searchBtn:
			//查询窗口
			startActivityForResult(new Intent(this, SearchActivity.class), ItasteApplication.MAIN_REQUEST_SEARCH_CODE);
			break;
		case R.id.zwsearchiv://周围搜索
			ItasteApplication app = ItasteApplication.getInstance();
			if(app.currentLocation==null){
				Toast.makeText(this,"自动定位失败，请在地图页面右上角手动点击定位按钮", Toast.LENGTH_LONG).show();
				return;
			}
			app.nearbyInit();
			HashMap<String, String> filterParams = app.filterParams;
			filterParams.put("center", app.currentLocation.getLongitude()+","+app.currentLocation.getLatitude());
			filterParams.put("radius",getNearbySerach(nearby.getSelectedItem()));
			filterParams.put("keywords", keywords.getText().toString().trim());
			LBSCloudUtils.search(this,LBSCloudUtils.SEARCH_TYPE_NEARBY);
			break;
		default:
			break;
		}
		
		
	}
	private String getNearbySerach(Object selectedItem) {
		/*
		<item>1000   米</item>
        <item>1500 米</item>
        <item>2000 米</item>
        <item>2500 米</item>
        <item>3000 米</item>
        <item>3500 米</item>
        <item>4000 米</item>
        <item>4500 米</item>
        <item>5000 米</item>*/
		int result = 1000;
		if("1000 米".equals(selectedItem)){
			result=1000;
		}else if("1500 米".equals(selectedItem)){
			result=1500;
		}else if("2000 米".equals(selectedItem)){
			result=2000;
		}else if("2500 米".equals(selectedItem)){
			result=2500;
		}else if("3000 米".equals(selectedItem)){
			result=3000;
		}else if("3500 米".equals(selectedItem)){
			result=3500;
		}else if("4000 米".equals(selectedItem)){
			result=4000;
		}else if("4500 米".equals(selectedItem)){
			result=4500;
		}else if("5000 米".equals(selectedItem)){
			result=5000;
		}
		return String.valueOf(result);
	}


	//查询回归之后
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(ItasteApplication.MAIN_REQUEST_SEARCH_CODE==requestCode&&resultCode==ItasteApplication.SEARCH_FAC_RESULT_CODE){
			LBSCloudUtils.search(this,LBSCloudUtils.SEARCH_TYPE_ALL_LIST);
		}
	}
	

	  //初始化上方导航菜单及其事件
	 private void initNavBtn() {
		 // menuBtn = (Button) this.findViewById(R.id.menuBtn);
		 searchBtn = (Button) this.findViewById(R.id.searchBtn);
		  nearby = (Spinner) this.findViewById(R.id.nearby);
		  zwsearchiv =(ImageView) this.findViewById(R.id.zwsearchiv);
		  keywords = (EditText) this.findViewById(R.id.keywords);
		  //下拉菜单
		   ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,R.layout.simple_spinner_item, getResources().getStringArray(R.array.nearby));
		   adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		   nearby.setAdapter(adapter);
		   //添加事件
		   //menuBtn.setOnClickListener(this);
		   searchBtn.setOnClickListener(this);
		  zwsearchiv.setOnClickListener(this);
	 }
	  
				
	//初始化侧边菜单
	private void initSlideMenu() {
				leftmenu = new SlidingMenu(this);
				leftmenu.setMode(SlidingMenu.LEFT);
				// 设置触摸屏幕的模式
				leftmenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				leftmenu.setShadowWidthRes(R.dimen.shadow_width);
				leftmenu.setShadowDrawable(R.drawable.orange_jianbian_bg);

				// 设置滑动菜单视图的宽度
				leftmenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
				// 设置渐入渐出效果的值
				leftmenu.setFadeDegree(0.35f);
				leftmenu.showSecondaryMenu(true);
				//leftmenu.setMode(SlidingMenu.RIGHT);
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
			 WhiteDialog dialog = WhiteDialog.fastBuild(this, getResources().getString(R.string.dialog_title), "确定要退出吗？"
						, new WhiteDialogClickListener() {
						@Override
						public void onClick(Dialog dialog,View v) {
							android.os.Process.killProcess(android.os.Process.myPid());
						}
						}, 
						new WhiteDialogClickListener() {
						@Override
						public void onClick(Dialog dialog,View v) {
							dialog.dismiss();
							Toast.makeText(MainActivity.this, "已取消退出...", Toast.LENGTH_LONG).show();
						}
					});
					dialog.show();
	}

}
