package com.itaste.yuntu;

import java.util.HashMap;

import com.itaste.yuntu.model.FacInfoModel;
import com.itaste.yuntu.util.ItasteApplication;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

public class FacInfoAddActivity extends Activity {

	private ScrollView layout; 
	private EditText 
					struct,region,price
					,floor,peidian,area,phone
					,name,address,qqcode,weixincode,height;
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		
	super.onCreate(savedInstanceState); 
	setContentView(R.layout.fac_info_add); 
	//初始化控件
	initView(); 
	//添加监听事件
	layout.setOnClickListener(new OnClickListener() { 
		@Override 
		public void onClick(View v) { 
		Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",Toast.LENGTH_SHORT).show(); 
		} 
		}); 
	
	}
	


	private void initView() {
		region = (EditText) this.findViewById(R.id.region);
		price = (EditText) this.findViewById(R.id.price);
		floor = (EditText) this.findViewById(R.id.floor);
		peidian = (EditText) this.findViewById(R.id.peidian);
		area = (EditText) this.findViewById(R.id.area);
		phone = (EditText) this.findViewById(R.id.phone);
		struct = (EditText) this.findViewById(R.id.struct);
		name = (EditText) this.findViewById(R.id.name);
		address = (EditText) this.findViewById(R.id.address);
		qqcode = (EditText) this.findViewById(R.id.qqcode);
		weixincode = (EditText) this.findViewById(R.id.weinxin);
		height = (EditText) this.findViewById(R.id.height);
		layout=(ScrollView)findViewById(R.id.add_dialog);
		
		/*regionbtn = (Button) this.findViewById(R.id.regionbtn);
		pricebtn = (Button) this.findViewById(R.id.pricebtn);
		floorbtn = (Button) this.findViewById(R.id.floorbtn);
		peidianbtn = (Button) this.findViewById(R.id.peidianbtn);
		areabtn = (Button) this.findViewById(R.id.areabtn);
		structbtn = (Button) this.findViewById(R.id.structbtn);*/
		
		
	} 
	@Override 
	public boolean onTouchEvent(MotionEvent event){
	if(event.getAction()==MotionEvent.ACTION_DOWN){
		Toast.makeText(getApplicationContext(), "提示：查询已取消！", 
				Toast.LENGTH_SHORT).show(); 
		finish();
	} 
	return true; 
	} 
	
	//查询按钮
	public void addFacInfoClickHandler(View v) { 
	//将查询条件放入到application中
	String regionstr = region.getText().toString().trim();
	String pricestr = price.getText().toString().trim();
	String floorstr = floor.getText().toString().trim();
	String peidianstr = peidian.getText().toString().trim();
	String areastr = area.getText().toString().trim();
	String structstr = struct.getText().toString().trim();
	String phonestr = phone.getText().toString().trim();
	String namevalue = name.getText().toString().trim();
	String addressvalue = address.getText().toString().trim();
	String qqcodevalue = qqcode.getText().toString().trim();
	String weixinvalue = weixincode.getText().toString().trim();
	String heightvalue = height.getText().toString().trim();
	
	
	HashMap<String,String> addfacinfo = new HashMap<String, String>();
	
	addfacinfo.put(FacInfoModel.FAC_REGION, regionstr);
	addfacinfo.put(FacInfoModel.FAC_RENT_ORSALE_PRICE, pricestr);
	addfacinfo.put(FacInfoModel.FAC_FLOOR, getSearchFloor(floorstr));
	addfacinfo.put(FacInfoModel.FAC_PEIDIAN,peidianstr);
	addfacinfo.put(FacInfoModel.FAC_AREA, areastr);
	addfacinfo.put(FacInfoModel.FAC_STRUCT, structstr);
	addfacinfo.put(FacInfoModel.FAC_MOBILE, phonestr);
	addfacinfo.put(FacInfoModel._NAME, namevalue);
	addfacinfo.put(FacInfoModel._ADDRESS, addressvalue);
	addfacinfo.put(FacInfoModel.QQ_CODE, qqcodevalue);
	addfacinfo.put(FacInfoModel.WEIXIN_CODE, weixinvalue);
	addfacinfo.put(FacInfoModel.FAC_HEIGHT, heightvalue);
	Intent intent = getIntent();
	intent.putExtra("addfacinfo", addfacinfo);
	setResult(ItasteApplication.ADD_FAC_RESULT_CODE,intent);
	this.finish(); 
	}



	private String getSearch(String str) {
		String result = "";
		if("不限".equals(str)){
			result = "";
		}
		return result;
	}



	private String getSearchFloor(String floorstr) {
		/**
		 *  <string-array name="floor">
        <item>独栋</item>
        <item>分租</item>
        <item>1楼</item>
        <item>2楼</item>
        <item>3楼</item>
        <item>4楼</item>
        <item>5楼</item>
        <item>6楼</item>
        <item>7楼</item>
    </string-array>
		 */
		String result = "";
		if("独栋".equals(floorstr)){
			result = "-2";
		}else if("分租".equals(floorstr)){
			result = "-1";
		}else if("1楼".equals(floorstr)){
			result = "1";
		}else if("2楼".equals(floorstr)){
			result = "2";
		}else if("3楼".equals(floorstr)){
			result = "3";
		}else if("4楼".equals(floorstr)){
			result = "4";
		}else if("5楼".equals(floorstr)){
			result = "5";
		}else if("6楼".equals(floorstr)){
			result = "6";
		}else if("7楼".equals(floorstr)){
			result = "7";
		}
		
		return result;
	}



	

	//查询取消按钮
	public void cancelBtnClickHandler(View v) { 
	Toast.makeText(getApplicationContext(), "提示：查询已取消！", 
	Toast.LENGTH_SHORT).show(); 
	this.finish(); 
	} 
	//选择按钮的单击事件
	public void chooseCondition(View view){
		
		final int adapterArrId;
		int adapterStrId;
		
		switch (view.getId()) {
		case R.id.regionbtn:
			adapterArrId = R.array.region;
			adapterStrId = R.string._region;
			initAdapterDialog(adapterArrId, adapterStrId,region);
			break;
		case R.id.areabtn:
			adapterArrId = R.array.area;
			adapterStrId = R.string._area;
			initAdapterDialog(adapterArrId, adapterStrId,area);
			break;
		case R.id.pricebtn:
			adapterArrId = R.array.price;
			adapterStrId = R.string._price;
			initAdapterDialog(adapterArrId, adapterStrId,price);
			break;
		case R.id.floorbtn:
			adapterArrId = R.array.floor;
			adapterStrId = R.string._height;
			initAdapterDialog(adapterArrId, adapterStrId,floor);
			break;
		case R.id.peidianbtn:
			adapterArrId = R.array.peidian;
			adapterStrId = R.string._peidian;
			initAdapterDialog(adapterArrId, adapterStrId,peidian);
			break;
		case R.id.structbtn:
			adapterArrId = R.array.struct;
			adapterStrId = R.string._struct;
			initAdapterDialog(adapterArrId, adapterStrId,struct);
			break;

		default:
			Toast.makeText(FacInfoAddActivity.this, "尚未选择任何条件", Toast.LENGTH_LONG).show();
			break;
		}
		
	}


	private void initAdapterDialog(final int adapterArrId, int adapterStrId,final EditText edit) {
		AlertDialog.Builder builder = new Builder(FacInfoAddActivity.this);
		 builder.setIcon(R.drawable.ic_launcher)
			   .setTitle(getString(adapterStrId))
			   .setSingleChoiceItems(adapterArrId, 0,new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					edit.setText(getResources().getStringArray(adapterArrId)[whichButton]);
					dialog.dismiss();
				}
			});
		builder.show();
	}
}
