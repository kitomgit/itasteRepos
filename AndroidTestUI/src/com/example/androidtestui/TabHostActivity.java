package com.example.androidtestui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class TabHostActivity extends Activity {
	ImageView imgleft1,imgright1,imgleft2,imageright2,imageright3;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost);
		initview();
		imgleft1.setOnClickListener(listener);
		imgright1.setOnClickListener(listener);
		imgleft2.setOnClickListener(listener);
		imageright2.setOnClickListener(listener);
		imageright3.setOnClickListener(listener);
	}
	public void initview()
	{
		imgleft1=(ImageView)findViewById(R.id.UIimageViewleft1);
		imgleft2=(ImageView)findViewById(R.id.UIimageViewleft2);
		imgright1=(ImageView)findViewById(R.id.UiimageViewright1);
		imageright2=(ImageView)findViewById(R.id.UIimageViewright2);
		imageright3=(ImageView)findViewById(R.id.UIimageViewCenter);
		
	}
	
	private OnClickListener listener =new OnClickListener(){
		public void onClick(View v) {
			if(v.getId()==R.id.UIimageViewleft1)
			{
				Intent intent=new Intent(TabHostActivity.this,MainActivity.class);
				startActivity(intent);
			}
			if(v.getId()==R.id.UiimageViewright1)
			{
				Intent intent=new Intent(TabHostActivity.this,WindowMessageActivity.class);
				startActivity(intent);
			}
			if(v.getId()==R.id.UIimageViewleft2)
			{
				Intent intent=new Intent(TabHostActivity.this,HomeActivity.class);
				startActivity(intent);
			}
			if(v.getId()==R.id.UIimageViewright2)
			{
				Intent intent=new Intent(TabHostActivity.this,TestPopuWindow.class);
				startActivity(intent);
			}
			if(v.getId()==R.id.UIimageViewCenter)
			{
				Intent intent=new Intent(TabHostActivity.this,TestmainActiity.class);
				startActivity(intent);
			}
		}
		
	};
}
