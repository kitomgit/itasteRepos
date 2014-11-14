package com.itaste.yuntu.util;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.itaste.yuntu.LBSFacListActivity;
import com.itaste.yuntu.LBSFacMapActivity;
import com.itaste.yuntu.R;
import com.itaste.yuntu.model.AMapDTO;
import com.itaste.yuntu.model.FacInfoModel;
import android.app.Application;
import android.widget.BaseAdapter;
/**
 * ���ϵͳ�ĵ�ͼ��ѯ�����Ͳ�ѯ���
 * @author tom
 *
 */
public class ItasteApplication extends  Application{
	private static final String FILTERKEY = "filter";

	//��ǰ�������ͼ��maptab,listtab��
	public  String currentactivateView;

	//��������
	public HashMap<String, String> filterParams = new HashMap<String, String>();
	
	//��ѯ�����
	private AMapDTO<FacInfoModel>  dto;
	
	public LBSFacListActivity facListActivity;
	//���ڸ���mapͼ��ͱ��
	public LBSFacMapActivity facMapActivity;
	
	//����ˢ������
	public BaseAdapter listAdapter;
	
	public boolean istosearch;
	
	public static ItasteApplication instance;
	
	public final static int SEARCH_RESULT_CODE=1;
	
	public final static int MAIN_REQUEST_SEARCH_CODE=2; 
	
	public final static int List_REQUEST_SEARCH_CODE=2; 
	
	
	
	
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
		//���������ʼ��
		filterParams.put("city", "ȫ��");
  		filterParams.put("keywords","");
  		//Ĭ�ϵ�ǰ������ͼΪmap��ͼ
		this.currentactivateView = getString(R.string._maptab);
	}
	/*
	 * ���dto�����ڣ��򴴽�һ������
	 * @return
	 */
	public AMapDTO<FacInfoModel> getValidateDto(){
		if(dto==null)dto = new AMapDTO<FacInfoModel>();
		return dto;
	}
	
	/*�ߵµ�ͼ�Ƽ���filter������һ�������*/
	public void setFilters(String filterstr){
		if(filterParams.containsKey(FILTERKEY)){
			filterParams.remove(FILTERKEY);
		}
		if (filterstr==null) {
			filterstr = "";
		}
		filterParams.put(FILTERKEY, filterstr);
	}
	/*�ߵµ�ͼ�Ƽ���filter������һ�������*/
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
}