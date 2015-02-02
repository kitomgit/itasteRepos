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
import com.itaste.yuntu.model.DtoUser;
import com.itaste.yuntu.model.FacInfoModel;
/**
 * ���ϵͳ�ĵ�ͼ��ѯ�����Ͳ�ѯ���
 * @author tom
 *
 */
public class ItasteApplication extends  Application{
	public static final String FILTERKEY = "filter";

	//��ǰ�������ͼ��maptab,listtab��
	public  String currentactivateView;

	//��������
	public HashMap<String, String> filterParams = new HashMap<String, String>();
	
	//��ѯ�����
	private AMapDTO<FacInfoModel>  dto;
	
	private int page=1;//ҳ��
	
	private int limit = 10;//��ҳ��С
	
	public boolean isrequery;//�Ƿ���������
	
	public LBSFacListActivity facListActivity;
	//���ڸ���mapͼ��ͱ��
	public LBSFacMapActivity facMapActivity;
	
	//����ˢ������
	public BaseAdapter listAdapter;
	
	public boolean istosearch;
	
	//��ǰλ����Ϣ
	public AMapLocation currentLocation;
	
	public static ItasteApplication instance;
	
	public final static int SEARCH_FAC_RESULT_CODE=1;
	
	public final static int MAIN_REQUEST_SEARCH_CODE=2; 
	
	public final static int List_REQUEST_SEARCH_CODE=2; 
	
	//�������result code
	public final static int ADD_FAC_RESULT_CODE=3;
	//����activity 
	public final static int ADD_FAC_REQUEST_CODE=4;
	//��ǰ�û�
	public static DtoUser dtoUser;
	
	
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
		listInit();
  		//Ĭ�ϵ�ǰ������ͼΪmap��ͼ
		this.currentactivateView = getString(R.string._maptab);
	}
	private void listInit() {
		//���������ʼ��
		filterParams.put("city", "ȫ��");
  		filterParams.put("keywords","");
  		filterParams.put(FILTERKEY,"");
  		filterParams.put("page", page+"");
  		filterParams.put("limit", limit+"");
  		filterParams.remove("radius");
  		filterParams.remove("center");
  		this.isrequery = true;
	}
	/*
	 * ���dto�����ڣ��򴴽�һ������
	 * @return
	 */
	public AMapDTO<FacInfoModel> getValidateDto(){
		if(dto==null)dto = new AMapDTO<FacInfoModel>();
		return dto;
	}
	//���ؼ�¼����
	public int getTotalCount(){
		return dto!=null?dto.getCount():0;
	}
	
	
	/*�ߵµ�ͼ�Ƽ���filter������һ�������*/
	public void setFilters(String filterstr){
		listInit();
		if (filterstr==null) {
			filterstr = "";
		}
		filterParams.put(FILTERKEY, filterstr);
	}
	/*�ߵµ�ͼ�Ƽ���filter������һ�������*/
	public void setFilters(HashMap<String,String> filters){
		listInit();
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
	//��ȡ��ǰҳ��
	public int getCurPage(){
		return this.page;
	}
	//��ȡ��ǰ�ķ�ҳ��С
	public int getpageSize(){
		return this.limit;
	}
	
	public int setNextStepPage(int step){
			this.page= getCurPage()+step;
			filterParams.put("page", this.page+"");
			this.isrequery = false;
			//this.istosearch=true;
			return this.page;
	}
	//��Χ������
	public void nearbyInit(){
		//this.istosearch = false;
		filterParams.remove(FILTERKEY);
		//filterParams.remove("limit");
		//filterParams.remove("page");
		filterParams.put("limit", "100");
		this.isrequery = true;
	}
	
}