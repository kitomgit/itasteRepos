package com.itaste.yuntu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Gallery;
import android.widget.ImageView;

public class FacImageGallery extends Activity {
	@SuppressWarnings("deprecation")
	private Gallery pregallery;
	private ImageView bgiv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fac_image_gallery);
		pregallery = (Gallery) this.findViewById(R.id.pregallery);
		bgiv = (ImageView) this.findViewById(R.id.bgiv);
		
	}
}
