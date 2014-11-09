package com.itaste.yuntu.util;

import java.util.HashMap;

import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


/**
 * �ߵ��Ƽ���ʹ����
 *
 */
public class LBSCloudSearch {
	
	private final static String TAG = "LBSCloudSearch";
	
	//�Ƽ���API URI
	private static final String SEARCH_URI_LOCAL = "http://yuntuapi.amap.com/datasearch/local?";
	private static final String SEARCH_URI_NEARBY = "http://yuntuapi.amap.com/datasearch/around?";
	private static final String SEARCH_URI_ID = "http://yuntuapi.amap.com/datasearch/id?";
	
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
	public static void request(final int searchType,final HashMap<String, String> filterParams,AsyncHttpResponseHandler handler) {
		if (IsBusy || filterParams == null)return;
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
				 RequestParams params = new RequestParams(filterParams); // �󶨲���
				 params.put("key", ak);
				 params.put("tableid", tableid);
				 client.get(requestURL.toString(),params,handler);
			} catch (Exception e) {
				Log.e(TAG, "�����쳣��������������ԣ�");
				e.printStackTrace();
			}
		
		IsBusy = false;
	}
	
}
