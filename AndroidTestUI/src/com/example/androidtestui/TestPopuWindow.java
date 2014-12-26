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
		// �����������֮���Բ�ȡ������ʽ��ʽ����ΪsetContentVIEW(main)����...
		t1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(TestPopuWindow.this, "����", Toast.LENGTH_LONG)
						.show();
			}
		});
		t2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(TestPopuWindow.this, "����", Toast.LENGTH_LONG)
						.show();
			}
		});
		t3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(TestPopuWindow.this, "����", Toast.LENGTH_LONG)
						.show();
			}
		});
	}

	// ��ʼ��popwindow
	public void initPopWindow() {
		if (null == popWin) {// (popwin�Զ��岼���ļ�,popwin���,popwin�߶�)(ע������ָ����λ������������������ֵ���˲���ΪWRAP_CONTENT)
			popWin = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		}
		if (popWin.isShowing()) {// ���ǵ�ǰ������ʾ,�򽫱�����ͷ�
			popWin.dismiss();
		}
	}

	// �Ա�����ΪAnchor,Drawdown���,��ʾ�����·�
	public void btn1Click(View v) {
		initPopWindow();
		// popWin.showAsDropDown(v);
		popWin.showAsDropDown(v,
				Math.abs(v.getWidth() - popWin.getWidth()) / 2, 0);
	}

	// �Ա���ΪAnchor,ƫ��100,-50
	public void btn2Click(View v) {
		initPopWindow();
		popWin.showAsDropDown(v, 100, -50);
	}

	// ����Ļ����Ϊ����,��ƫ��
	public void btn3Click(View v) {
		initPopWindow();
		popWin.showAtLocation(linear, Gravity.CENTER, 0, 0);
	}

	// ����Ļ����Ϊ����,ƫ��50,50(����״����)
	public void btn4Click(View v) {
		initPopWindow();
		// (������,��ֱ�̶ȶ�����ʽ��ʽ,xƫ��,yƫ��)
		popWin.showAtLocation(linear, Gravity.TOP | Gravity.LEFT, 50, 50);
	}

	// ����Ļ�·�һ��popwin�߶�
	public void btn5Click(View v) {
		initPopWindow();
		popWin.showAtLocation(linear, Gravity.BOTTOM | Gravity.CENTER, 0,
				view.getHeight());

	}

	// �����ȡGestuary���Ʊ������

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
