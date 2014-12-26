package com.itaste.yuntu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;


public class LoadActivity extends Activity {
	private static final int LOAD_DISPLAY_TIME = 1500;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		setContentView(R.layout.activity_load);
		
		/*Editor editor = sharedPreferences.edit();//获取编辑器
		editor.putString("name", "wujaycode");
		editor.putInt("age", 4);
		editor.commit();//提交修改
*/	    new Handler().postDelayed(new Runnable() {
	    	             public void run() {
	    	                 /* Create an Intent that will start the Main WordPress Activity. */
	    	                 Intent mainIntent = new Intent(LoadActivity.this, MainActivity.class);
	    	                 LoadActivity.this.startActivity(mainIntent);
	    	                 LoadActivity.this.finish();
	    	             }
	    	         }, LOAD_DISPLAY_TIME); //1500 for release
	    	 
	}
}
