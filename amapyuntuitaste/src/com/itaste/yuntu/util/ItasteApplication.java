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
 * ���ϵͳ�ĵ�ͼ��ѯ�����Ͳ�ѯ���
 * @author tom
 *
 */
public class ItasteApplication extends  Application{
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
	 * ���dto�����ڣ��򴴽�һ������
	 * @return
	 */
	public AMapDTO<FacInfoModel> getValidateDto(){
		if(dto==null)dto = new AMapDTO<FacInfoModel>();
		return dto;
	}
	
	
	
}