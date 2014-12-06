package com.itaste.yuntu.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.itaste.yuntu.R;

public class WhiteDialog extends Dialog{
private TextView title;//标题
private TextView content;//消息内容
private Button ensure;//确定
private Button cancel;//取消
public interface WhiteDialogClickListener{
	void onClick(Dialog dialog,View v);
	
};

	public WhiteDialog(Context context, int theme) {
		super(context, theme);
		
	}

	public WhiteDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public WhiteDialog(Context context) {
		super(context,R.style.whitedialog);
		 View view = LayoutInflater.from(context).inflate(R.layout.white_dialog, null); 
		 this.title = (TextView) view.findViewById(R.id.title);
		 this.content = (TextView) view.findViewById(R.id.content);
		 this.ensure = (Button) view.findViewById(R.id.ensure);
		 this.cancel = (Button) view.findViewById(R.id.cancel);
		 this.setContentView(view);
	}
	//设置标题
	public WhiteDialog setTitle(String title){
		if(TextUtils.isEmpty(title)){title="";}
		this.title.setText(title);
		return this;
	}
	//设置显示内容
	public WhiteDialog setContent(String content){
		if(TextUtils.isEmpty(content)){content="";}
		this.content.setText(content);
		return this;
	}
	//设置确定处理的方法
	public WhiteDialog setOnEnsureHandler(final WhiteDialogClickListener ensureHandler){
		
		this.ensure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(ensureHandler!=null)
				ensureHandler.onClick(WhiteDialog.this, v);
				
			}
		});
		return this;
	}
	//设置取消处理的方法
	public WhiteDialog setOnCancelHandler(final WhiteDialogClickListener cancelHandler){
		this.cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(cancelHandler!=null)
				cancelHandler.onClick(WhiteDialog.this, v);
				
			}
		});
		return this;
	}
	//快速创建
	public static WhiteDialog fastBuild(
								Context context
								,String title
								,String content
								,WhiteDialogClickListener ensureHandler
								,WhiteDialogClickListener cancelHandler){
		return new WhiteDialog(context)
							.setTitle(title)
							.setContent(content)
							.setOnEnsureHandler(ensureHandler)
							.setOnCancelHandler(cancelHandler);
	}
}
