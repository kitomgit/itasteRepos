package com.itaste.yuntu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.itaste.yuntu.adapter.FacListBaseListAdapter;
import com.itaste.yuntu.util.ItasteApplication;
import com.itaste.yuntu.util.LBSCloudSearch;

public class LBSFacListActivity extends Activity {
	private ListView faclv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fac_list);
		faclv = (ListView) this.findViewById(R.id.faclv);
		ItasteApplication application = ItasteApplication.getInstance();
		BaseAdapter listAdapter = new FacListBaseListAdapter(this,application.getValidateDto().getDatas());
		faclv.setAdapter(listAdapter);
		application.listAdapter = listAdapter;
	}
	@Override
	protected void onResume() {
		super.onResume();
		final ItasteApplication application = ItasteApplication.getInstance();
		BaseAdapter listAdapter = application.listAdapter;
		if (listAdapter!=null) {
		listAdapter.notifyDataSetChanged();
		}
		if(application.getValidateDto().getCount()<=0&&!application.istosearch){
			AlertDialog.Builder builder = new Builder(this);
			builder.setIcon(R.drawable.ic_launcher)
				   .setTitle("提示")
				   .setMessage("尚无数据，是否立即搜索！")
				   .setPositiveButton("确定", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						application.istosearch = true;
						startActivityForResult(new Intent(LBSFacListActivity.this.getParent(), SearchActivity.class), R.string._height_search);
						
					}
				   }).setNegativeButton("稍后", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						
					}
				   });
			builder.create().show();
	
		}else{
			application.istosearch = false;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(ItasteApplication.List_REQUEST_SEARCH_CODE==requestCode&&resultCode==ItasteApplication.SEARCH_RESULT_CODE){
			LBSCloudSearch.search(this);
		}
	}
	
	
}
