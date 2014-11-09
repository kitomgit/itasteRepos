package com.itaste.yuntu.util;

import java.util.HashMap;

import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


/**
 * 高德云检索使用类
 *
 */
public class LBSCloudSearch {
	
	private final static String TAG = "LBSCloudSearch";
	
	//云检索API URI
	private static final String SEARCH_URI_LOCAL = "http://yuntuapi.amap.com/datasearch/local?";
	private static final String SEARCH_URI_NEARBY = "http://yuntuapi.amap.com/datasearch/around?";
	private static final String SEARCH_URI_ID = "http://yuntuapi.amap.com/datasearch/id?";
	
	public static final int SEARCH_TYPE_NEARBY = 1;
	public static final int SEARCH_TYPE_URI_ID = 2;
	public static final int SEARCH_TYPE_LOCAL = 3;
	
	public static int currSearchType = 0;
	
	//云检索公钥
	private static String ak = "26fb00c60d85ea30460a484e2367a89a";
	
	private static String tableid = "544b4df5e4b056afbbf01fb5";
	public static boolean IsBusy = false;
	
	/**
	 * 云检索访问
	 * @param filterParams	访问参数，key为filter时特殊处理。
	 * @param handler		数据回调Handler
	 * @return
	 */
	public static void request(final int searchType,final HashMap<String, String> filterParams,AsyncHttpResponseHandler handler) {
		if (IsBusy || filterParams == null)return;
		IsBusy = true;
		AsyncHttpClient client = new AsyncHttpClient();
			try {
				//根据过滤选项拼接请求URL
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
				default://默认为本地搜索
					requestURL.append(SEARCH_URI_LOCAL);
					break;
				}
				//当前搜索类型
				currSearchType = searchType;
				//请求参数
				 RequestParams params = new RequestParams(filterParams); // 绑定参数
				 params.put("key", ak);
				 params.put("tableid", tableid);
				 client.get(requestURL.toString(),params,handler);
			} catch (Exception e) {
				Log.e(TAG, "网络异常，请检查网络后重试！");
				e.printStackTrace();
			}
		
		IsBusy = false;
	}
	
}
