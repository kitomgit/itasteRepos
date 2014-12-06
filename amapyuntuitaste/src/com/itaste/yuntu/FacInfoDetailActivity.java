package com.itaste.yuntu;



import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itaste.yuntu.model.DtoImage;
import com.itaste.yuntu.model.FacInfoModel;
import com.loopj.android.image.SmartImageView;


public class FacInfoDetailActivity extends Activity {
	private TextView  fanhui,
					struct,region,price
					,floor,peidian,area,sushe,phone
					,name,address,qqcode,weixincode,height;
	private FacInfoModel facinfo;
	private String location;
	private LinearLayout picregion;
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState); 
	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	setContentView(R.layout.fac_info_detail); 
	getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.back_title_bar);
	//初始化控件
	initView(); 
	}
	
	


	private void initView() {
		picregion =(LinearLayout) this.findViewById(R.id.picregion);
		region = (TextView) this.findViewById(R.id.region);
		price = (TextView) this.findViewById(R.id.price);
		floor = (TextView) this.findViewById(R.id.floor);
		peidian = (TextView) this.findViewById(R.id.peidian);
		area = (TextView) this.findViewById(R.id.area);
		sushe = (TextView) this.findViewById(R.id.sushe_area);
		phone = (TextView) this.findViewById(R.id.phone);
		struct = (TextView) this.findViewById(R.id.struct);
		name = (TextView) this.findViewById(R.id.name);
		address = (TextView) this.findViewById(R.id.address);
		qqcode = (TextView) this.findViewById(R.id.qqcode);
		weixincode = (TextView) this.findViewById(R.id.weinxin);
		height = (TextView) this.findViewById(R.id.height);
		fanhui = (TextView) this.findViewById(R.id.fanhui);
		fanhui.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FacInfoDetailActivity.this.finish();
			}
		});
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if(bundle!=null){
		facinfo = (FacInfoModel)bundle.get("facinfo");
		if(facinfo!=null){
			region.setText(facinfo.getFac_region());
			price.setText(facinfo.getFac_price());
			floor.setText(facinfo.getFac_floor());
			peidian.setText(facinfo.getFac_peidian());
			area.setText(facinfo.getFac_area());
			sushe.setText(facinfo.getFac_sushe_area());
			phone.setText(facinfo.getFac_mobile());
			struct.setText(facinfo.getFac_struct());
			name.setText(facinfo.getName());
			address.setText(facinfo.getAddress());
			qqcode.setText(facinfo.getQq_code());
			weixincode.setText(facinfo.getWeixin_code());
			height.setText(facinfo.getFac_height());
			location = facinfo.getLocationStr();
			List<DtoImage> images = facinfo.getImage();
			if(images==null){
				picregion.setVisibility(View.INVISIBLE);
				return;
			}
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.setMargins(5, 3,5, 3);
			lp.width=150;
			lp.height=150;
			for (DtoImage dtoImage : images) {
				SmartImageView iv = new SmartImageView(this);
				//iv.setPadding(0, 0, 0, 0);
				iv.setLayoutParams(lp);
				iv.setImageUrl(dtoImage.getPreurl());
				picregion.addView(iv);
			}
		 }
	   }
	} 
}
