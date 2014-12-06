package com.itaste.yuntu;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.itaste.yuntu.adapter.FacBaseListAdapter;
import com.itaste.yuntu.util.ItasteApplication;
import com.itaste.yuntu.util.LBSCloudUtils;

public class LBSFacListActivity extends Activity 
implements OnRefreshListener,OnScrollListener  {
	private ListView faclv;
	private SwipeRefreshLayout swipe_ly;
	private BaseAdapter listAdapter;
	// 最后可见条目的索引
    private int lastVisibleIndex;
	// ListView底部View
    private View moreView;
	public final static int CALL_PHONE=1;
	private static final int REFRESH_COMPLETE = 1;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg){
			switch (msg.what)
			{
			case REFRESH_COMPLETE:
				ItasteApplication.getInstance().isrequery=true;
				LBSCloudUtils.search(LBSFacListActivity.this,LBSCloudUtils.currSearchType);
				listAdapter.notifyDataSetChanged();
				swipe_ly.setRefreshing(false);
				break;

			}
		}
	};
	
	
	
	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fac_list);
		ItasteApplication application = ItasteApplication.getInstance();
		faclv = (ListView) this.findViewById(R.id.faclv);
		
		//上方刷新控件
		swipe_ly = (SwipeRefreshLayout) this.findViewById(R.id.swipe_ly);
		swipe_ly.setOnRefreshListener(this);
		swipe_ly.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
		//下方加载更多数据view
		moreView = getLayoutInflater().inflate(R.layout.more_data, null);
		moreView.setVisibility(View.GONE);
		listAdapter = new FacBaseListAdapter(this,application.getValidateDto().getDatas());
		faclv.addFooterView(moreView);
		faclv.setAdapter(listAdapter);
		application.listAdapter = listAdapter;
		faclv.setOnScrollListener(this);
		
	}
	@Override
	protected void onResume(){
		super.onResume();
		final ItasteApplication application = ItasteApplication.getInstance();
		BaseAdapter listAdapter = application.listAdapter;
		if (listAdapter!=null) {
		listAdapter.notifyDataSetChanged();
		}
		if(application.getValidateDto().getCount()<=0){
			LBSCloudUtils.search(this,LBSCloudUtils.SEARCH_TYPE_ALL_LIST);
		}
	}
	//下拉刷新
	@Override
	public void onRefresh() {
		Toast.makeText(this, "加载最新......", Toast.LENGTH_LONG).show();
		mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// 滑到底部后自动加载，判断listview已经停止滚动并且最后可视的条目等于adapter的条目
        int count = listAdapter.getCount();
		int totalCount = ItasteApplication.getInstance().getTotalCount();
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                && lastVisibleIndex == count
                &&totalCount>count
                ) {
        	ItasteApplication.getInstance().setNextStepPage(1);
        	moreView.setVisibility(View.VISIBLE);
        	LBSCloudUtils.search(this,LBSCloudUtils.currSearchType);
        	Toast.makeText(this, "加载中......", Toast.LENGTH_LONG).show();
        }
		  // 所有的条目已经和最大条数相等，则移除底部的View
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE&&
        		lastVisibleIndex>0&&totalCount==lastVisibleIndex) {
        	moreView.setVisibility(View.GONE);
            Toast.makeText(this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
        }
		
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		 // 计算最后可见条目的索引
        lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;
	}
	
	public void detailfac(View v){
		
		RelativeLayout rl = (RelativeLayout) v.getParent();
		Toast.makeText(this, v.toString(), Toast.LENGTH_LONG).show();
	}
	
	
}
