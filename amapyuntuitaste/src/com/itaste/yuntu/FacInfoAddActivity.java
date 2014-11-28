package com.itaste.yuntu;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itaste.yuntu.model.FacInfoModel;
import com.itaste.yuntu.util.ItasteApplication;

public class FacInfoAddActivity extends Activity {
	private static int  CAMERA_CAPTURE=0;
	private static int  CAMERA_GELLAY=1;
	private ScrollView layout; 
	private LinearLayout picregion;
	private ImageView topbackBtn;
	private TextView fanhui;
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
	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	setContentView(R.layout.fac_info_add); 
	getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.back_title_bar);
	//初始化控件
	initView(); 
	//添加监听事件
	layout.setOnClickListener(new OnClickListener() { 
		@Override 
		public void onClick(View v) { 
		Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",Toast.LENGTH_SHORT).show(); 
		} 
		}); 
	OnClickListener fanhuicl = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			FacInfoAddActivity.this.finish();
			
		}
	};
	topbackBtn.setOnClickListener(fanhuicl);
	fanhui.setOnClickListener(fanhuicl);
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
		topbackBtn = (ImageView) this.findViewById(R.id.top_back_iv);
		picregion = (LinearLayout) this.findViewById(R.id.picregion);
		fanhui = (TextView) this.findViewById(R.id.fanhui);
		/*regionbtn = (Button) this.findViewById(R.id.regionbtn);
		pricebtn = (Button) this.findViewById(R.id.pricebtn);
		floorbtn = (Button) this.findViewById(R.id.floorbtn);
		peidianbtn = (Button) this.findViewById(R.id.peidianbtn);
		areabtn = (Button) this.findViewById(R.id.areabtn);
		structbtn = (Button) this.findViewById(R.id.structbtn);*/
		
		
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
	//调用系统相机或相册
	public void addfacpic(View imageview){
		
		Toast.makeText(FacInfoAddActivity.this, "哈哈。。。", 5000).show();
		CharSequence [] items ={"拍照","相册"};
		AlertDialog.Builder builder = new Builder(FacInfoAddActivity.this);
		builder.setTitle("选择图片")
			   .setItems(items,new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:
						Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
						 startActivityForResult(getImageByCamera, CAMERA_CAPTURE);
						break;
					case 1:
						 Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
						 getImage.addCategory(Intent.CATEGORY_OPENABLE);
						 getImage.setType("image/jpeg");
						 startActivityForResult(getImage, CAMERA_GELLAY);
						break;
					default:
						break;
					}
					
				}
			});
		builder.show();
		
	}
	

	 @ Override

	 protected void onActivityResult ( int requestCode , int resultCode , Intent data ){
	 /**
	
	  * 因为两种方式都用到了startActivityForResult方法，
	
	  * 这个方法执行完后都会执行onActivityResult方法， 所以为了区别到底选择了那个方式获取图片要进行判断，
	
	  * 这里的requestCode跟startActivityForResult里面第二个参数对应
	
	  */
	super.onActivityResult(requestCode, resultCode, data);
	 ContentResolver resolver = getContentResolver();
	 Bitmap  myBitmap = null;
	 if (requestCode == CAMERA_GELLAY){
	 try{
	 // 获得图片的uri
	 Uri originalUri = data.getData();

	 // 将图片内容解析成字节数组

	 byte[] mContent = readStream(resolver.openInputStream(Uri.parse(originalUri.toString())));

	 // 将字节数组转换为ImageView可调用的Bitmap对象

	  myBitmap = getPicFromBytes(mContent, null);
	 } catch ( Exception e ){
	 e.printStackTrace();
	 }
	 } else if (requestCode == CAMERA_CAPTURE){

	 try{
	 Bundle extras = data.getExtras();
	       myBitmap = (Bitmap) extras.get("data");
	 ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	 } catch ( Exception e ){
	 e.printStackTrace();
	 }
	 }
	 if(myBitmap!=null){
		 ImageView iv  = new ImageView(this);
		 LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(80,80);
		 lp.setMargins(0, 0, 0, 2);
		 iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
		 iv.setImageBitmap(myBitmap);
		 iv.setLayoutParams(lp);
		 picregion.addView(iv, picregion.getChildCount());
		 //picregion.addView(iv);
	 }
	
	 }



	 public static Bitmap getPicFromBytes ( byte[] bytes , BitmapFactory.Options opts )
	 {
	 if (bytes != null)
	 if (opts != null)
	 return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
	 else
	 return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	 return null;
	 }



	 public static byte[] readStream ( InputStream inStream ) throws Exception
	 {
	 byte[] buffer = new byte[1024];
	 int len = -1;
	 ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	 while ((len = inStream.read(buffer)) != -1)
	 {
	 outStream.write(buffer, 0, len);
	 }
	 byte[] data = outStream.toByteArray();
	 outStream.close();
	 inStream.close();
	 return data;
	 }


}
