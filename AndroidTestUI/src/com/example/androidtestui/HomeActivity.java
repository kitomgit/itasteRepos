package com.example.androidtestui;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class HomeActivity extends Activity{
	@Override
	//3D界面效果
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		CoverFlow cf = new CoverFlow(this);  
		  
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
  
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏   
  
        cf.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.imagetest));//背景   
  
        cf.setAdapter(new ImageAdapter(this));  
  
        ImageAdapter imageAdapter = new ImageAdapter(this);  
  
        cf.setAdapter(imageAdapter);  
  
        cf.setSelection(2, true);  
  
        cf.setAnimationDuration(1000);  
  
        setContentView(cf);  

		
	}
}
