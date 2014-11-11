package com.itaste.yuntu;


import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.itaste.yuntu.adapter.LeftSlideMenuListViewAdapter;
import com.itaste.yuntu.model.AMapDTO;
import com.itaste.yuntu.model.FacInfoModel;
import com.itaste.yuntu.util.ItasteApplication;
import com.itaste.yuntu.util.LBSCloudSearch;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.AsyncHttpResponseHandler;

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
			startActivityForResult(new Intent(this, SearchActivity.class), R.string._height_search);
			break;

		default:
			break;
		}
		
	}
	//查询回归之后
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data!=null){
			Bundle bundle = data.getExtras();
			search();
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
	 //点击搜索按钮
  	public void search(){
  		HashMap<String, String> filterParams = new HashMap<String, String>();
  		filterParams.put("city", "全国");
  		filterParams.put("keywords", "");
  		filterParams.put("filter", "fac_area:200");
		LBSCloudSearch.request(LBSCloudSearch.SEARCH_TYPE_LOCAL, filterParams,new AsyncHttpResponseHandler(){
			 @Override
			public void onSuccess(int arg0, Header[] arg1, byte[] data) {
				String dataStr = new String(data);
				dataStr = dataStr.replaceAll("\"_", "\"");
				 AMapDTO<FacInfoModel>  dto = JSON.parseObject(dataStr, new TypeReference<AMapDTO<FacInfoModel>>(){});
				 ItasteApplication.getInstance().dto = dto;
				
				Toast.makeText(
						MainActivity.this
						,"符合条件数据:"+dto.getCount()+"个"
						,Toast.LENGTH_LONG).show();
				//刷新试图
				refreshActiveView();
				//addMarkersToMap(facInfos);
			}
			 
			  private void refreshActiveView() {
			  		ItasteApplication application = ItasteApplication.getInstance();
					if(application.currentactivateView.equals(getString(R.string._maptab))){
						application.facMapActivity.addMarkersToMap();
			  		}
				}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
				Throwable arg3) {
				Toast.makeText(MainActivity.this, "读取数据失败，请重试", Toast.LENGTH_LONG).show();
				
			}
		 });
  	}
  	
  	
  	 
  	//当前tab切换时，记录当前激活的activity
	@Override
	public void onTabChanged(String tabId) {
		ItasteApplication.getInstance().currentactivateView = tabId;
	}
  	 


}
