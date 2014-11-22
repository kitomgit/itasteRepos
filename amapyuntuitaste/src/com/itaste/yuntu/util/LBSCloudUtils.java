package com.itaste.yuntu.util;

import java.util.HashMap;

import org.apache.http.Header;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.itaste.yuntu.R;
import com.itaste.yuntu.model.AMapDTO;
import com.itaste.yuntu.model.FacInfoModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


/**
 * �ߵ��Ƽ���ʹ����
 *
 */
public class LBSCloudUtils {
	
	private final static String TAG = "LBSCloudUtils";
	
	//�Ƽ���API URI
	private static final String SEARCH_URI_LOCAL = "http://yuntuapi.amap.com/datasearch/local?";
	private static final String SEARCH_URI_NEARBY = "http://yuntuapi.amap.com/datasearch/around?";
	private static final String SEARCH_URI_ID = "http://yuntuapi.amap.com/datasearch/id?";
	//�ƴ洢API URI
	private static final String ADD_INFO_URI = "http://yuntuapi.amap.com/datamanage/data/create?";
	
	public static final int SEARCH_TYPE_NEARBY = 1;
	public static final int SEARCH_TYPE_URI_ID = 2; 
	public static final int SEARCH_TYPE_LOCAL = 3;
	
	public static int currSearchType = 0;
	
	//�Ƽ�����Կ
	private static String ak = "26fb00c60d85ea30460a484e2367a89a";
	
	private static String tableid = "544b4df5e4b056afbbf01fb5";
	public static boolean IsBusy = false;
	
	/**
	 * �Ƽ�������
	 * @param filterParams	���ʲ�����keyΪfilterʱ���⴦��
	 * @param handler		���ݻص�Handler
	 * @return
	 */
	public static void request(final int searchType,final AsyncHttpResponseHandler handler) {
		if (IsBusy )return;
		IsBusy = true;
		AsyncHttpClient client = new AsyncHttpClient();
			try {
				//���ݹ���ѡ��ƴ������URL
				StringBuffer requestURL = new StringBuffer(0);
				
				switch (searchType) {
				case SEARCH_TYPE_LOCAL:
					requestURL.append(SEARCH_URI_LOCAL);
					break;
				case SEARCH_TYPE_URI_ID:
					requestURL.append(SEARCH_URI_ID);
					break;
				case SEARCH_TYPE_NEARBY:
					requestURL.append(SEARCH_URI_NEARBY);
					break;
				default://Ĭ��Ϊ��������
					requestURL.append(SEARCH_URI_LOCAL);
					break;
				}
				//��ǰ��������
				currSearchType = searchType;
				//�������
				 RequestParams params = new RequestParams( ItasteApplication.getInstance().filterParams); // �󶨲���
				 params.put("key", ak);
				 params.put("tableid", tableid);
				 client.get(requestURL.toString(),params,handler);
				 System.out.println("params>>>>>>:"+params);
			} catch (Exception e) {
				Log.e(TAG, "�����쳣��������������ԣ�");
				e.printStackTrace();
			}
		
		IsBusy = false;
	}
	 //���������ť
  	public static void search(final Context context){
  		final ItasteApplication application = ItasteApplication.getInstance();
  		/*filterParams.put("city", "ȫ��");
  		filterParams.put("keywords", "");
  		filterParams.put("filter", "fac_area:2000");*/
  		
		LBSCloudUtils.request(LBSCloudUtils.SEARCH_TYPE_LOCAL,new AsyncHttpResponseHandler(){
			 @Override
			public void onSuccess(int arg0, Header[] arg1, byte[] data) {
				String dataStr = new String(data);
				dataStr = dataStr.replaceAll("\"_", "\"");
				 AMapDTO<FacInfoModel>  dto = JSON.parseObject(dataStr, new TypeReference<AMapDTO<FacInfoModel>>(){});
				 application.getValidateDto().copy(dto);//����copy
				Toast.makeText(
						context
						,"������������:"+dto.getCount()+"��"
						,Toast.LENGTH_LONG).show();
				
					refreshActiveView();
				
				//addMarkersToMap(facInfos);
			}
			 
			  private void refreshActiveView() {
			  		int count =application.getValidateDto().getCount();
					if(application.currentactivateView.equals(context.getString(R.string._maptab))){
						//if(count>0){
							application.facMapActivity.addMarkersToMap();
						//}
			  		}else if(application.currentactivateView.equals(context.getString(R.string._listtab))){
						BaseAdapter listAdapter = application.listAdapter;
						if(listAdapter!=null){
							listAdapter.notifyDataSetChanged();
						}
			  		}
					if(count==0){
						Toast.makeText(context, "�޷����������ݣ�������˧ѡ��", Toast.LENGTH_LONG).show();
					}
				}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
				Throwable arg3) {
				Toast.makeText(context, "��ȡ����ʧ�ܣ�������", Toast.LENGTH_LONG).show();
				
			}
		 });
  	}
  	
  	//���info
  	public static void addinfo(final Context context,String jsoninfo){
  		AsyncHttpClient client = new AsyncHttpClient();
  		RequestParams params = new RequestParams();
  		params.put("key", ak);
  		params.put("tableid", tableid);
  		params.put("data",jsoninfo);
  		client.post(ADD_INFO_URI, params,new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] data) {
				String resultstr = new String(data);
				HashMap<String,String> resultmap =  JSON.parseObject(resultstr, new TypeReference<HashMap<String,String>>(){});
				Toast.makeText(context, resultstr, Toast.LENGTH_LONG).show();
				if(false&&"1".equals(resultmap.get("status"))){
					Toast.makeText(context, "��������ӣ�", Toast.LENGTH_LONG).show();
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				
				Toast.makeText(context, "�������ʧ�ܣ�������", Toast.LENGTH_LONG).show();
			}
		});
  		
  	}
}
