package com.itaste.yuntu;
import java.util.HashMap;

import com.itaste.yuntu.model.FacInfoModel;
import com.itaste.yuntu.util.ItasteApplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SearchActivity extends Activity {
	private LinearLayout layout; 
	private EditText struct,region,price,floor,peidian,area,phone,name,address;
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		
	super.onCreate(savedInstanceState); 
	setContentView(R.layout.activity_search); 
	//初始化控件
	initView(); 
	//添加监听事件
	layout.setOnClickListener(new OnClickListener() { 
		@Override 
		public void onClick(View v) { 
		// TODO Auto-generated method stub 
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
		name =(EditText) this.findViewById(R.id.name); 
		address = (EditText) this.findViewById(R.id.address);
		layout=(LinearLayout)findViewById(R.id.search_dialog);
		
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
	public void searchBtnClickHandler(View v) { 
	//将查询条件放入到application中
	ItasteApplication app = ItasteApplication.getInstance();
	String regionstr = region.getText().toString().trim();
	String pricestr = price.getText().toString().trim();
	String floorstr = floor.getText().toString().trim();
	String peidianstr = peidian.getText().toString().trim();
	String areastr = area.getText().toString().trim();
	String structstr = struct.getText().toString().trim();
	String phonestr = phone.getText().toString().trim();
	String namestr = name.getText().toString().trim();
	String addressstr = address.getText().toString().trim();
	HashMap<String,String> filters = new HashMap<String, String>();
	
	filters.put(FacInfoModel.FAC_REGION, getSearch(regionstr));
	filters.put(FacInfoModel.FAC_PRICE, getSearchPrice(pricestr));
	filters.put(FacInfoModel.FAC_FLOOR, getSearchFloor(floorstr));
	filters.put(FacInfoModel.FAC_PEIDIAN, getSearch(peidianstr));
	filters.put(FacInfoModel.FAC_AREA, getSearchArea(areastr));
	filters.put(FacInfoModel.FAC_STRUCT, getSearch(structstr));
	filters.put(FacInfoModel.FAC_MOBILE, phonestr);
	filters.put(FacInfoModel._NAME, namestr);
	filters.put(FacInfoModel._ADDRESS, addressstr);
	app.setFilters(filters);
	setResult(ItasteApplication.SEARCH_FAC_RESULT_CODE);
	this.finish(); 
	}



	private String getSearchArea(String areastr) {
		String result = "";
		/**
		 *    <item>不限</item>
        <item>200平米以下</item>
        <item>200-500平米</item>
        <item>500-800平米</item>
        <item>800-1000平米</item>
        <item>1000-1500平米</item>
        <item>1500-2000平米</item>
        <item>2000-2500平米</item>
        <item>2500-3000平米</item>
        <item>3000-3500平米</item>
        <item>3500-4000平米</item>
        <item>4000-6000平米</item>
        <item>6000-8000平米</item>
        <item>8000-10000平米</item>
        <item>10000平米以上</item>
		 */
		if("200平米以下".equals(areastr)){
			result = "[0,200]";
		}else if("200-500平米".equals(areastr)){
			result = "[200,500]";
		}else if("500-800平米".equals(areastr)){
			result = "[500,800]";
		}else if("800-1000平米".equals(areastr)){
			result = "[800,1000]";
		}else if("1000-1500平米".equals(areastr)){
			result = "[1000,1500]";
		}else if("1500-2000平米".equals(areastr)){
			result = "[1500,2000]";
		}else if("2000-2500平米".equals(areastr)){
			result = "[2000,2500]";
		}else if("2500-3000平米".equals(areastr)){
			result = "[2500,3000]";
		}else if("3000-3500平米".equals(areastr)){
			result = "[3000,3500]";
		}else if("3500-4000平米".equals(areastr)){
			result = "[3500,4000]";
		}else if("4000-6000平米".equals(areastr)){
			result = "[4000,6000]";
		}else if("6000-8000平米".equals(areastr)){
			result = "[6000,8000]";
		}else if("8000-10000平米".equals(areastr)){
			result = "[8000,10000]";
		}else if("10000平米以上".equals(areastr)){
			result = "[10000,100000]";
		}
		return result;
	}



	private String getSearch(String str) {
		String result = str;
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



	private String getSearchPrice(String pricestr) {
		/**
        <item>10元以下</item>
        <item>10-12元</item>
        <item>12-13元</item>
        <item>13-15元</item>
        <item>15-16元</item>
        <item>16-18元</item>
        <item>18-20元</item>
        <item>20-23元</item>
        <item>23-25元</item>
        <item>25元以上</item>
		 */
		String result = "";
		if("10元以下".equals(pricestr)){
			result = "[0,10]";
		}else if("10-12元".equals(pricestr)){
			result = "[10,12]";
		}else if("12-13元".equals(pricestr)){
			result = "[12,13]";
		}else if("13-15元".equals(pricestr)){
			result = "[13,15]";
		}else if("16-18元".equals(pricestr)){
			result = "[16,18]";
		}else if("18-20元".equals(pricestr)){
			result = "[18,20]";
		}else if("20-23元".equals(pricestr)){
			result = "[20,23]";
		}else if("23-25元".equals(pricestr)){
			result = "[23,25]";
		}else if("25元以上".equals(pricestr)){
			result = "[25,100]";
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
			Toast.makeText(SearchActivity.this, "尚未选择任何条件", Toast.LENGTH_LONG).show();
			break;
		}
		
	}



	private void initAdapterDialog(final int adapterArrId, int adapterStrId,final EditText edit) {
		AlertDialog.Builder builder = new Builder(SearchActivity.this);
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
