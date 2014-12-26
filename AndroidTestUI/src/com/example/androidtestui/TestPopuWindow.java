package com.example.androidtestui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class TestPopuWindow extends Activity {
	private PopupWindow popWin;
	private View view;
	private LinearLayout linear;
	private TextView t1, t2, t3;
	private float x, y;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testpopmain);
		view = getLayoutInflater().inflate(R.layout.testpop, null);
		t1 = (TextView) view.findViewById(R.id.link_text);
		t2 = (TextView) view.findViewById(R.id.link_text2);
		t3 = (TextView) view.findViewById(R.id.link_text3);
		linear = (LinearLayout) findViewById(R.id.linear);
		// 这里监听事务之所以采取这种体式格式是因为setContentVIEW(main)所以...
		t1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(TestPopuWindow.this, "在线", Toast.LENGTH_LONG)
						.show();
			}
		});
		t2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(TestPopuWindow.this, "离线", Toast.LENGTH_LONG)
						.show();
			}
		});
		t3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(TestPopuWindow.this, "隐身", Toast.LENGTH_LONG)
						.show();
			}
		});
	}

	// 初始化popwindow
	public void initPopWindow() {
		if (null == popWin) {// (popwin自定义布局文件,popwin宽度,popwin高度)(注：若想指定地位则后两个参数必须给定值不克不及为WRAP_CONTENT)
			popWin = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		}
		if (popWin.isShowing()) {// 若是当前正在显示,则将被处理惩罚
			popWin.dismiss();
		}
	}

	// 以本身作为Anchor,Drawdown风格,显示在正下方
	public void btn1Click(View v) {
		initPopWindow();
		// popWin.showAsDropDown(v);
		popWin.showAsDropDown(v,
				Math.abs(v.getWidth() - popWin.getWidth()) / 2, 0);
	}

	// 以本身为Anchor,偏移100,-50
	public void btn2Click(View v) {
		initPopWindow();
		popWin.showAsDropDown(v, 100, -50);
	}

	// 以屏幕正中为参照,不偏移
	public void btn3Click(View v) {
		initPopWindow();
		popWin.showAtLocation(linear, Gravity.CENTER, 0, 0);
	}

	// 以屏幕左上为参照,偏移50,50(包含状况栏)
	public void btn4Click(View v) {
		initPopWindow();
		// (参照物,垂直程度对齐体式格式,x偏移,y偏移)
		popWin.showAtLocation(linear, Gravity.TOP | Gravity.LEFT, 50, 50);
	}

	// 距屏幕下方一个popwin高度
	public void btn5Click(View v) {
		initPopWindow();
		popWin.showAtLocation(linear, Gravity.BOTTOM | Gravity.CENTER, 0,
				view.getHeight());

	}

	// 这里采取Gestuary手势辨认最佳

	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getX() == x && event.getY() == y) {
			return true;
		} else {
			x = event.getX();
			y = event.getY();
		}
		initPopWindow();
		popWin.showAtLocation(linear, Gravity.TOP | Gravity.LEFT,
				(int) event.getX() - popWin.getWidth() / 2, (int) event.getY());
		System.out.println("view" + view.getHeight() + "  h/w  "
				+ view.getWidth());
		return true;
	}

}
