package com.itaste.yuntu;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SearchActivity extends Activity {
	private LinearLayout layout; 
	private EditText struct,region,price,floor,peidian,area,phone;
	private Button structbtn,regionbtn,pricebtn,floorbtn,peidianbtn,areabtn;
	

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
		
		regionbtn = (Button) this.findViewById(R.id.regionbtn);
		pricebtn = (Button) this.findViewById(R.id.pricebtn);
		floorbtn = (Button) this.findViewById(R.id.floorbtn);
		peidianbtn = (Button) this.findViewById(R.id.peidianbtn);
		areabtn = (Button) this.findViewById(R.id.areabtn);
		structbtn = (Button) this.findViewById(R.id.structbtn);
		layout=(LinearLayout)findViewById(R.id.search_dialog);
		
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
	//传递数据回去
	Bundle bundle = new Bundle();
	bundle.putString("region",region.getText().toString().trim());
	bundle.putString("price",price.getText().toString().trim());
	bundle.putString("floor",floor.getText().toString().trim());
	bundle.putString("peidian",peidian.getText().toString().trim());
	bundle.putString("area",area.getText().toString().trim());
	bundle.putString("struct",struct.getText().toString().trim());
	bundle.putString("phone",phone.getText().toString().trim());
	setResult(RESULT_OK,this.getIntent().putExtras(bundle));
	this.finish(); 
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
