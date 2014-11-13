package com.itaste.yuntu.util;

import java.util.HashMap;

import com.itaste.yuntu.LBSFacListActivity;
import com.itaste.yuntu.LBSFacMapActivity;
import com.itaste.yuntu.R;
import com.itaste.yuntu.model.AMapDTO;
import com.itaste.yuntu.model.FacInfoModel;
import android.app.Application;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
/**
 * 存放系统的地图查询条件和查询结果
 * @author tom
 *
 */
public class ItasteApplication extends  Application{
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
	
	public static ItasteApplication instance;
	
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
		this.currentactivateView = getString(R.string._maptab);
	}
	/**
	 * 如果dto不存在，则创建一个返回
	 * @return
	 */
	public AMapDTO<FacInfoModel> getValidateDto(){
		if(dto==null)dto = new AMapDTO<FacInfoModel>();
		return dto;
	}
	
	
	
}