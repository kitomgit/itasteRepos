package com.example.androidtestui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	//
	int mGalleryItemBackground;

	private Context myContext;
	private Integer images[] = { R.drawable.camera, R.drawable.x2football,
			R.drawable.classics2, R.drawable.gpsphone2, R.drawable.idaft2 };

	public ImageAdapter(Context c) {
		myContext = c;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return images.length;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 ImageView i = createReflectedImages(myContext,images[position]); 
		 //120, 100
	        i.setLayoutParams(new CoverFlow.LayoutParams(170, 200));  
	        i.setScaleType(ImageView.ScaleType.CENTER_INSIDE);  
	          
	        // 设置的抗锯齿   
	        BitmapDrawable drawable = (BitmapDrawable) i.getDrawable();  
	        drawable.setAntiAlias(true);  
		return i;
	}

	// ？？？
	public float getScale(boolean focused, int offset) {
		return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
	}

	
	// 设置镜像图片
	public ImageView createReflectedImages(Context mContext, int imageId) {
		Bitmap originalImage = BitmapFactory.decodeResource(
				mContext.getResources(), imageId);

		// 定义变量
		final int reflect = 4;
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		Matrix matrix = new Matrix();
		matrix.postScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
				height / 2, width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		
		Canvas canvas=new Canvas(bitmapWithReflection);
		canvas.drawBitmap(originalImage, 0, 0, null);
		
		Paint defaltPaint=new Paint();
		 canvas.drawRect(0, height, width, height + reflect, defaltPaint);  
		  
	        canvas.drawBitmap(reflectionImage, 0, height + reflect, null);  
	  
	        Paint paint = new Paint();  
	        LinearGradient shader = new LinearGradient(0, originalImage  
	                .getHeight(), 0, bitmapWithReflection.getHeight()  
	                + reflect, 0x70ffffff, 0x00ffffff, TileMode.MIRROR);  
	  
	        paint.setShader(shader);  
	  
	        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));  
	  
	        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()  
	                + reflect, paint);  
	  
	        ImageView imageView = new ImageView(mContext);  
	        imageView.setImageBitmap(bitmapWithReflection);  
		return imageView;
	}
}
















