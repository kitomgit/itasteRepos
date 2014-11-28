package com.itaste.yuntu.util;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.widget.BaseAdapter;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;
import com.itaste.yuntu.LBSFacListActivity;
import com.itaste.yuntu.LBSFacMapActivity;
import com.itaste.yuntu.R;
import com.itaste.yuntu.model.AMapDTO;
import com.itaste.yuntu.model.FacInfoModel;
/**
 * 存放系统的地图查询条件和查询结果
 * @author tom
 *
 */
public class ItasteApplication extends  Application{
	public static final String FILTERKEY = "filter";

	//当前激活的试图【maptab,listtab】
	public  String currentactivateView;

	//过滤条件
	public HashMap<String, String> filterParams = new HashMap<String, String>();
	
	//查询结果集
	private AMapDTO<FacInfoModel>  dto;
	
	public LBSFacListActivity facListActivity;
	//用于更新map图层和标记
	public LBSFacMapActivity facMapActivity;
	
	//用于刷新数据
	public BaseAdapter listAdapter;
	
	public boolean istosearch;
	
	//当前位置信息
	public AMapLocation currentLocation;
	
	public static ItasteApplication instance;
	
	public final static int SEARCH_FAC_RESULT_CODE=1;
	
	public final static int MAIN_REQUEST_SEARCH_CODE=2; 
	
	public final static int List_REQUEST_SEARCH_CODE=2; 
	
	//添加数据result code
	public final static int ADD_FAC_RESULT_CODE=3;
	//发出activity 
	public final static int ADD_FAC_REQUEST_CODE=4;
	
	
	public static ItasteApplication getInstance(){
		if (instance==null) {
			instance = new ItasteApplication();
		}
		return instance;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		//必填参数初始化
		filterParams.put("city", "全国");
  		filterParams.put("keywords","");
  		filterParams.put(FILTERKEY,"");
  		//默认当前激活试图为map试图
		this.currentactivateView = getString(R.string._maptab);
	}
	/*
	 * 如果dto不存在，则创建一个返回
	 * @return
	 */
	public AMapDTO<FacInfoModel> getValidateDto(){
		if(dto==null)dto = new AMapDTO<FacInfoModel>();
		return dto;
	}
	
	/*高德地图云检索filter条件，一次性添加*/
	public void setFilters(String filterstr){
		if(filterParams.containsKey(FILTERKEY)){
			filterParams.remove(FILTERKEY);
		}
		if (filterstr==null) {
			filterstr = "";
		}
		filterParams.put(FILTERKEY, filterstr);
	}
	/*高德地图云检索filter条件，一次性添加*/
	public void setFilters(HashMap<String,String> filters){
		if(filterParams.containsKey(FILTERKEY)){
			filterParams.remove(FILTERKEY);
		}
		String filter = "";
		StringBuffer filterstr = new StringBuffer("");
		if(filters!=null&&filters.size()>0){
		String value;
		for (Map.Entry<String, String> entry: filters.entrySet()){
			value = entry.getValue();
			if(value!=null&&!value.trim().equals("")){
				try {
					filterstr.append(URLEncoder.encode("+","UTF-8"))
						     .append(entry.getKey())
						     .append(":")
						     .append(value);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(filterstr.length()>3){
			filter = filterstr.substring(3);
			System.out.println(filter);
		}
	  }
		filterParams.put(FILTERKEY, filter);
	}
	public LatLng getCurrentLocation(){
		return currentLocation!=null? new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()):null;
	}
	
}