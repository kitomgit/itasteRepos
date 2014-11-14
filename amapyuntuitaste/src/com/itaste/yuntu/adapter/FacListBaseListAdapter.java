package com.itaste.yuntu.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itaste.yuntu.R;
import com.itaste.yuntu.model.DtoImage;
import com.itaste.yuntu.model.FacInfoModel;
import com.loopj.android.image.SmartImageView;

public class FacListBaseListAdapter extends BaseAdapter  implements OnItemClickListener {
	private Context context;
	private List<FacInfoModel> facinfs;
	public FacListBaseListAdapter(Context context,List<FacInfoModel> facinfs) {
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
		 FacInfoModel facinfo = facinfs.get(position);
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
			lay.setTag(contentHolder);//绑定数据
		 }else{
			 contentHolder = (ListCacheViewHolder) lay.getTag();
		 }
		 DtoImage fistImage = facinfo.getFistImage();
		 if(fistImage!=null){
			 contentHolder.iv.setImageUrl(fistImage.getPreurl());
		 }
		 contentHolder.addressvalue.setText(facinfo.getAddress());
		 contentHolder.areavalue.setText(facinfo.getFac_area());
		 contentHolder.pricevalue.setText(facinfo.getFac_rent_orsale_price());
		 contentHolder.phonevalue.setText(facinfo.getFac_mobile());
		
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
	}
	//事件协议
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		
	}
	

}
