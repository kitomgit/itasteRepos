package com.itaste.yuntu.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itaste.yuntu.R;
import com.itaste.yuntu.model.DtoImage;
import com.loopj.android.image.SmartImageView;

public class FacImageGalleryAdapter extends BaseAdapter
{

	private Context mContext;
	private LayoutInflater mInflater;
	private List<DtoImage> mDatas;

	public FacImageGalleryAdapter(Context context, List<DtoImage> mDatas)
	{
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mDatas = mDatas;
	}

	public int getCount()
	{
		return mDatas.size();
	}

	public Object getItem(int position)
	{
		return mDatas.get(position);
	}

	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.fac_image_item, parent, false);
			viewHolder.mImg = (SmartImageView) convertView.findViewById(R.id.itemImage);
			viewHolder.mText = (TextView) convertView.findViewById(R.id.itemTitle);
			convertView.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.mImg.setImageUrl(mDatas.get(position).getPreurl());
		//viewHolder.mImg.setBackgroundResource(R.drawable.ic_launcher);
		//viewHolder.mImg.setLayoutParams(new Gallery.LayoutParams(150, 180));
		viewHolder.mImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
		viewHolder.mText.setText("FAC INFO");

		return convertView;
	}

	private class ViewHolder
	{
		SmartImageView mImg;
		TextView mText;
	}

}
