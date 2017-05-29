package com.hcm.coach;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityStart extends Activity {

	private int 			iBackground	= 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_start);
		
		Thread timer = new Thread() { // new thread
			public void run() {

				try {
					do {

						sleep(1000);

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								
								iBackground++;
								
								switch (iBackground % 3) {
								case 0:
									findViewById(R.id.rlBackground).setBackgroundResource(R.drawable.background_basketball_1);
									break;
								case 1:
									findViewById(R.id.rlBackground).setBackgroundResource(R.drawable.background_football_start_1);
									break;
								case 2:
									findViewById(R.id.rlBackground).setBackgroundResource(R.drawable.background_football_start_2);
									break;
								default:
									break;
								}

							}
						});

					} while (true);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
				}
			};
		};
		timer.start();
		
	}
	
	public void onBtnBasketBallClickListener(View v) {
		
	}
	
	public void onBtnFootBallClickListener(View v) {
		Intent intent = new Intent(ActivityStart.this, ActivityFootball.class);
		startActivity(intent);
	}
}
