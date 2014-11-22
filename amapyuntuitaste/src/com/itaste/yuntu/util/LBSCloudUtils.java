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
 * 高德云检索使用类
 *
 */
public class LBSCloudUtils {
	
	private final static String TAG = "LBSCloudUtils";
	
	//云检索API URI
	private static final String SEARCH_URI_LOCAL = "http://yuntuapi.amap.com/datasearch/local?";
	private static final String SEARCH_URI_NEARBY = "http://yuntuapi.amap.com/datasearch/around?";
	private static final String SEARCH_URI_ID = "http://yuntuapi.amap.com/datasearch/id?";
	//云存储API URI
	private static final String ADD_INFO_URI = "http://yuntuapi.amap.com/datamanage/data/create?";
	
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
	public static void request(final int searchType,final AsyncHttpResponseHandler handler) {
		if (IsBusy )return;
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
				 RequestParams params = new RequestParams( ItasteApplication.getInstance().filterParams); // 绑定参数
				 params.put("key", ak);
				 params.put("tableid", tableid);
				 client.get(requestURL.toString(),params,handler);
				 System.out.println("params>>>>>>:"+params);
			} catch (Exception e) {
				Log.e(TAG, "网络异常，请检查网络后重试！");
				e.printStackTrace();
			}
		
		IsBusy = false;
	}
	 //点击搜索按钮
  	public static void search(final Context context){
  		final ItasteApplication application = ItasteApplication.getInstance();
  		/*filterParams.put("city", "全国");
  		filterParams.put("keywords", "");
  		filterParams.put("filter", "fac_area:2000");*/
  		
		LBSCloudUtils.request(LBSCloudUtils.SEARCH_TYPE_LOCAL,new AsyncHttpResponseHandler(){
			 @Override
			public void onSuccess(int arg0, Header[] arg1, byte[] data) {
				String dataStr = new String(data);
				dataStr = dataStr.replaceAll("\"_", "\"");
				 AMapDTO<FacInfoModel>  dto = JSON.parseObject(dataStr, new TypeReference<AMapDTO<FacInfoModel>>(){});
				 application.getValidateDto().copy(dto);//对象copy
				Toast.makeText(
						context
						,"符合条件数据:"+dto.getCount()+"个"
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
						Toast.makeText(context, "无符合条件数据，请重新帅选！", Toast.LENGTH_LONG).show();
					}
				}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
				Throwable arg3) {
				Toast.makeText(context, "读取数据失败，请重试", Toast.LENGTH_LONG).show();
				
			}
		 });
  	}
  	
  	//添加info
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
					Toast.makeText(context, "数据已添加！", Toast.LENGTH_LONG).show();
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				
				Toast.makeText(context, "添加数据失败，请重试", Toast.LENGTH_LONG).show();
			}
		});
  		
  	}
}
