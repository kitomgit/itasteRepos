package com.itaste.yuntu.util;

import java.util.Map;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AsyncHttpUtils {
	// post请求
	/**
	 * 
	 * @param requestURL 请求路径
	 * @param params 请求参数
	 * @param handler 完成请求后的处理回调
	 */
	public static void post(String requestURL
			,Map<String, String> params
			,AsyncHttpResponseHandler handler
			) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams _params = new RequestParams(params);
		client.post(requestURL, _params, handler);
	}
	

}
