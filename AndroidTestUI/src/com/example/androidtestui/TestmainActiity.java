package com.example.androidtestui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestmainActiity extends Activity{
	Button bt;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testseting);
		 bt=(Button)findViewById(R.id.sample_button);
		 bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TestSetingActivity.prepare(TestmainActiity.this,R.id.inner_content);
				startActivity(new Intent(TestmainActiity.this,
						TestSetingActivity.class));
				overridePendingTransition(0, 0);
				
			}
		});
		
		


	}

}
