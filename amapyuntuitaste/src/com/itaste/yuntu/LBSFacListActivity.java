package com.itaste.yuntu;

import com.itaste.yuntu.adapter.FacListBaseListAdapter;
import com.itaste.yuntu.model.AMapDTO;
import com.itaste.yuntu.model.FacInfoModel;
import com.itaste.yuntu.util.ItasteApplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class LBSFacListActivity extends Activity {
	private ListView faclv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fac_list);
		faclv = (ListView) this.findViewById(R.id.faclv);
		ItasteApplication application = ItasteApplication.getInstance();
		AMapDTO<FacInfoModel> dto = application.dto;
		if(dto!=null){
			faclv.setAdapter(new FacListBaseListAdapter(this,dto.getDatas()));
		}
		
	}
	
}
