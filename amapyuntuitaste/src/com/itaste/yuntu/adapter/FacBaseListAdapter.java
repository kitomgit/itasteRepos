package com.itaste.yuntu.adapter;

import java.io.Serializable;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itaste.yuntu.FacImageGalleryActivity;
import com.itaste.yuntu.R;
import com.itaste.yuntu.model.DtoImage;
import com.itaste.yuntu.model.FacInfoModel;
import com.loopj.android.image.SmartImageView;

public class FacBaseListAdapter extends BaseAdapter  implements OnItemClickListener {
	private Context context;
	private List<FacInfoModel> facinfs;
	public FacBaseListAdapter(Context context,List<FacInfoModel> facinfs) {
		this.context = context;
		this.facinfs = facinfs;
	}
	@Override
	public int getCount() {
		return facinfs.size();
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int position, View lay, ViewGroup parent) {
		 ListCacheViewHolder contentHolder;
		 final FacInfoModel facinfo = facinfs.get(position);
		 if(lay==null){
			 contentHolder = new ListCacheViewHolder();
			 LayoutInflater inflater = LayoutInflater.from(context);
			 lay = inflater.inflate(R.layout.tab_listview_item, null);
			//图片信息
			contentHolder.iv = (SmartImageView) lay.findViewById(R.id.facimagefirst);
			//文字信息
			//地址
			contentHolder.addressvalue = (TextView) lay.findViewById(R.id.addressvalue);
			//面积
			contentHolder.areavalue = (TextView) lay.findViewById(R.id.areavalue);
			//价格
			contentHolder.pricevalue = (TextView) lay.findViewById(R.id.pricevalue);
			//电话
			contentHolder.phonevalue = (TextView) lay.findViewById(R.id.phonevalue);
			//qq
			contentHolder.qqvalue =  (TextView) lay.findViewById(R.id.qqvalue);
			//weixin
			contentHolder.weixinvalue =  (TextView) lay.findViewById(R.id.weixinvalue);
			lay.setTag(contentHolder);//绑定数据
		 }else{
			 contentHolder = (ListCacheViewHolder) lay.getTag();
		 }
		 DtoImage fistImage = facinfo.getFistImage();
		 if(fistImage!=null){
			 contentHolder.iv.setImageUrl(fistImage.getPreurl());
			 contentHolder.iv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putSerializable("fac_images", (Serializable) facinfo.getImage());
					Intent intent = new Intent(context, FacImageGalleryActivity.class);
					intent.putExtras(bundle);
					context.startActivity(intent);
				}
			});
		 }
		 contentHolder.addressvalue.setText(facinfo.getAddress());
		 contentHolder.areavalue.setText(facinfo.getFac_area());
		 contentHolder.pricevalue.setText(facinfo.getFac_rent_orsale_price());
		 contentHolder.phonevalue.setText(facinfo.getFac_mobile());
		
		 View phonelay = (View)contentHolder.phonevalue.getParent();
		phonelay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				TextView phonetv = (TextView) view.findViewById(R.id.phonevalue);
				String phone = phonetv.getText().toString();
				if(TextUtils.isEmpty(phone)){
					phone="114";
				}
				Toast.makeText(context,phone,Toast.LENGTH_LONG).show();
				Intent callphoneIntent= new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));
				context.startActivity(callphoneIntent);
			}
		});
		phonelay.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					v.setBackgroundResource(R.drawable.tab_top_btn_blue);
					v.getBackground().setAlpha(100);
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					v.setBackgroundResource(R.drawable.muwen_item_bg);
					v.getBackground().setAlpha(225);
				}
				return false;
			}
		});
		 contentHolder.qqvalue.setText(facinfo.getQq_code());
		 View qqlay = (View) contentHolder.qqvalue.getParent();
		 qqlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView qq = (TextView)v.findViewById(R.id.qqvalue);
				String qqcode = qq.getText().toString();
				if(TextUtils.isEmpty(qqcode)){
					Toast.makeText(context,"木有qq!",Toast.LENGTH_LONG).show();
				}
				Toast.makeText(context,qqcode,Toast.LENGTH_LONG).show();
				//启动qq临时聊天窗口
				String url="mqqwpa://im/chat?chat_type=wpa&uin="+qqcode;  
				context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
				 
				
			}
		});
		 qqlay.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if(event.getAction()==MotionEvent.ACTION_DOWN){
						v.setBackgroundResource(R.drawable.tab_top_btn_blue);
						v.getBackground().setAlpha(100);
					}else if(event.getAction()==MotionEvent.ACTION_UP){
						v.setBackgroundResource(R.drawable.muwen_item_bg);
						v.getBackground().setAlpha(225);
					}
					return false;
				}
			});
		 contentHolder.weixinvalue.setText(facinfo.getWeixin_code());
		 View weixinlay = (View)contentHolder.weixinvalue;
		 weixinlay.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					TextView qq = (TextView)v.findViewById(R.id.weixinvalue);
					String qqcode = qq.getText().toString();
					if(TextUtils.isEmpty(qqcode)){
						Toast.makeText(context,"木有qq!",Toast.LENGTH_LONG).show();
					}
					Toast.makeText(context,qqcode,Toast.LENGTH_LONG).show();
					//启动weixin临时聊天窗口
					Intent intent = new Intent();
					ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
					intent.setAction(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_LAUNCHER);
					intent.addCategory("android.intent.category.MULTIWINDOW_LAUNCHER");
					//intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setComponent(cmp);
					context.startActivity(intent);
					 
					
				}
			});
		 weixinlay.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if(event.getAction()==MotionEvent.ACTION_DOWN){
							v.setBackgroundResource(R.drawable.tab_top_btn_blue);
							v.getBackground().setAlpha(150);
						}else if(event.getAction()==MotionEvent.ACTION_UP){
							v.setBackgroundResource(R.drawable.muwen_item_bg);
							v.getBackground().setAlpha(225);
						}
						return false;
					}
				});
		System.out.println("contentHolder====================:::"+contentHolder);
		return lay;
	}
	/**
	 * 用于listview中item的cache
	 * @author tom
	 *
	 */
	private class ListCacheViewHolder{
		SmartImageView iv;
		TextView addressvalue;
		TextView areavalue;
		TextView pricevalue;
		TextView phonevalue;
		TextView qqvalue;
		TextView weixinvalue;
		
	}
	//事件协议
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		
	}
	

}
