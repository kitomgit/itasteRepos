package com.example.androidtestui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class WindowMessageActivity extends Activity{
	Button DialogBtn;
	WindowManager myWindowManager=null;
	WindowManager.LayoutParams mLayoutParams;
	
	//参考资料定义两个变量
	 private float touchX;
	 private float touchY;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manager);
		DialogBtn=new Button(this);
		
		/*DialogBtn.setHeight(500);
		DialogBtn.setWidth(200);*/
		
		DialogBtn.setText("悬浮窗口");
		DialogBtn.setGravity(Gravity.CENTER);
		
		myWindowManager =(WindowManager)getSystemService(Context.WINDOW_SERVICE);
		mLayoutParams =new WindowManager.LayoutParams();
		
		mLayoutParams.format=1;
		mLayoutParams.flags=40;
		mLayoutParams.type=2003;
		
		mLayoutParams.x=0;
		mLayoutParams.y=0;
		mLayoutParams.width=150;
		mLayoutParams.height=150;
		
		mLayoutParams.gravity=Gravity.LEFT|Gravity.TOP;
		mLayoutParams.alpha=0.6f;
		
		myWindowManager.addView(DialogBtn, mLayoutParams);
		
		DialogBtn.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				touchX=event.getRawX();
				touchY=event.getRawY();
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					updateViewPosition();
					break;
				case MotionEvent.ACTION_MOVE:
					updateViewPosition();
					break;
				case MotionEvent.ACTION_UP:
					updateViewPosition();
					break;
				};
				return false;
			}
		});
		DialogBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//myWindowManager.removeView(DialogBtn);
				Toast.makeText(WindowMessageActivity.this, "点击", 1).show();
			}
		});
		
	}
	 public void updateViewPosition() {  
	       mLayoutParams.x = (int) (touchX - DialogBtn.getWidth() / 2);  
	       mLayoutParams.y = (int) (touchY - DialogBtn.getHeight() / 2);  
	       myWindowManager.updateViewLayout(DialogBtn, mLayoutParams);           
}
}
























