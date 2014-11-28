package com.itaste.yuntu;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.itaste.yuntu.model.DtoImage;
import com.loopj.android.image.SmartImageView;

public class FacImageGalleryActivity extends Activity {
	private SmartImageView bgiv;  
	private List<DtoImage> images; 
	private LinearLayout iv_gallery;
	@SuppressWarnings("unchecked")
	@Override  
	protected void onCreate(Bundle savedInstanceState)  
	{  
	    super.onCreate(savedInstanceState);  
	    requestWindowFeature(Window.FEATURE_NO_TITLE);  
	    setContentView(R.layout.fac_image_gallery);  
	    images = (List<DtoImage>) getIntent().getExtras().get("fac_images");
	    bgiv = (SmartImageView) findViewById(R.id.id_content);  
	    iv_gallery = (LinearLayout) findViewById(R.id.id_gallery); 
	    if(images!=null&&!images.isEmpty()){
	    	initGallery();
	    }
	}
	private void initGallery() {
		DtoImage image;
	for (int i=0;i<images.size();i++) {
		image = images.get(i);
		final SmartImageView siv = new SmartImageView(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(5, 3, 5, 3);
		lp.width=150;
		siv.setImageUrl(image.getPreurl());
		siv.setLayoutParams(lp);
		iv_gallery.addView(siv);
		siv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				bgiv.setImageDrawable(siv.getDrawable());
				bgiv.setScaleType(ImageView.ScaleType.FIT_CENTER);
			}
		});
		if (i==0){
			bgiv.setImageUrl(image.getPreurl());
			bgiv.setScaleType(ImageView.ScaleType.FIT_CENTER);
		}
	}
  }
	@Override 
	public boolean onTouchEvent(MotionEvent event){
	if(event.getAction()==MotionEvent.ACTION_DOWN){
		this.finish();
	} 
	return true; 
	} 
}
/*
private MyHorizontalScrollView mHorizontalScrollView;  
private FacImageGalleryAdapter mAdapter;  
private ImageView mImg;  
private List<DtoImage> images; 

@SuppressWarnings("unchecked")
@Override  
protected void onCreate(Bundle savedInstanceState)  
{  
    super.onCreate(savedInstanceState);  
    requestWindowFeature(Window.FEATURE_NO_TITLE);  
    setContentView(R.layout.fac_image_gallery);  
    
    mImg = (ImageView) findViewById(R.id.id_content);  
    mHorizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.id_horizontalScrollView);  
    images = (List<DtoImage>) getIntent().getExtras().get("fac_images");
    mAdapter = new FacImageGalleryAdapter(this, images);  
    //添加滚动回调  
    mHorizontalScrollView  
            .setCurrentImageChangeListener(new CurrentImageChangeListener()  
            {  
				@Override  
                public void onCurrentImgChanged(int position,  
                        View viewIndicator)  
                {  

                 SmartImageView siv =(SmartImageView) viewIndicator.findViewById(R.id.itemImage);
                 mImg.setImageDrawable(siv.getDrawable());
                }  
            });  
    //添加点击回调  
    mHorizontalScrollView.setOnItemClickListener(new OnItemClickListener()  
    {  

		@Override  
        public void onClick(View view, int position)  
        {  
        	SmartImageView siv =(SmartImageView) view.findViewById(R.id.itemImage);
        	mImg.setImageDrawable(siv.getDrawable());
        	//mImg.setImageBitmap(FacImageGalleryActivity.this.drawableToBitmap(siv));
           // view.setBackgroundColor(Color.parseColor("#AA024DA4"));  
        }  
    });  
    //设置适配器  
    mHorizontalScrollView.initDatas(mAdapter);  
}  */
