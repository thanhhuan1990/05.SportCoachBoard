package com.hcm.coach;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hcm.coach.customview.PlayerView;
import com.hcm.coach.util.AdmobUtil;
import com.hcm.coach.util.ConsoleLogger;

public class ActivityFootball extends Activity  {

	private TextView 				tvTeam1;
	private TextView 				tvTeam2;
	private ImageView 				ivChangeTeam;

	private RelativeLayout 			rlStadium;

	private LinearLayout 			llPopupSetting;
	private Spinner 				spNumber;
	private Button 					btnStart;

	private int 					_xDelta = 0;
	private int 					_yDelta = 0;

	private ArrayList<PlayerView> 	arPlayer = new ArrayList<PlayerView>();

	private int 					iScreenWidth = 0;
	private int 					iScreenHeight = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ConsoleLogger.logEnterFunction();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_football);

		AdmobUtil.initAdView(this);

		spNumber = (Spinner) findViewById(R.id.spNumber);
		spNumber.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.item_number, getResources().getStringArray(R.array.Number_Of_Football_Player)));

		rlStadium = (RelativeLayout) findViewById(R.id.rlStadium);

		ViewTreeObserver viewTreeObserver = rlStadium.getViewTreeObserver();
		if (viewTreeObserver.isAlive()) {
			viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					rlStadium.getViewTreeObserver().removeOnGlobalLayoutListener(this);

					iScreenWidth = rlStadium.getWidth();
					iScreenHeight = rlStadium.getHeight();
					ConsoleLogger.log("Screen Width: " + iScreenWidth + ", Screen Height: " + iScreenHeight);

				}
			});
		}

		ConsoleLogger.logLeaveFunction();
	}

	public void startGame(View v) {
		ConsoleLogger.logEnterFunction();
		
		((LinearLayout) findViewById(R.id.llPopupSetting)).setVisibility(View.GONE);

		((TextView) findViewById(R.id.tvTeam1)).setText(((EditText) findViewById(R.id.etTeam1)).getText().toString());
		((TextView) findViewById(R.id.tvTeam2)).setText(((EditText) findViewById(R.id.etTeam2)).getText().toString());
		((TextView) findViewById(R.id.tvCoach1)).setText(((EditText) findViewById(R.id.etCoach1)).getText().toString());
		((TextView) findViewById(R.id.tvCoach2)).setText(((EditText) findViewById(R.id.etCoach2)).getText().toString());

		int iNumberOfPlayer = Integer.parseInt(getResources().getStringArray(R.array.Number_Of_Football_Player)[spNumber.getSelectedItemPosition()]);

		for (int i = 1; i <= iNumberOfPlayer; i++) {

			int size = iScreenHeight / (iNumberOfPlayer + 5);
			int marginLeft = 0;
			int marginTop = 0;
			if (i == 1) {
				marginLeft = iScreenWidth / 10;
				marginTop = iScreenHeight / 2 - size / 2;
			} else {
				marginLeft = iScreenWidth / 3;
				marginTop = size * i + 5;
			}

			final PlayerView newPlayer = new PlayerView(getApplicationContext(), 1, i + "", i, R.drawable.icon_player, size, marginLeft, marginTop);
			RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams(size, size);
			layoutParam.setMargins(marginLeft, marginTop, 0, 0);
			newPlayer.setLayoutParams(layoutParam);
			newPlayer.setOnTouchListener(new GestureHelper(getApplicationContext(), newPlayer));

			rlStadium.addView(newPlayer);

			arPlayer.add(newPlayer);

		}
		
		ConsoleLogger.logLeaveFunction();
	}

	private class GestureHelper implements OnTouchListener {

		private final GestureDetector mGestureDetector;

		private PlayerView player;

		public GestureHelper(Context context, PlayerView player) {
			mGestureDetector = new GestureDetector(context, new GestureListener(this));
			this.player = player;
		}

		public void onClick() {
			findViewById(R.id.llInformation).setVisibility(View.VISIBLE);
			EditText etNumber = (EditText) findViewById(R.id.etNumber);
			etNumber.setText(player.number + "");
			EditText etName = (EditText) findViewById(R.id.etName);
			etName.setText(player.name);

			Button btnFinsih = (Button) findViewById(R.id.btnFinish);
			btnFinsih.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					player.setName(((EditText) findViewById(R.id.etName)).getText().toString());
					player.setNumber(Integer.parseInt(((EditText) findViewById(R.id.etNumber)).getText().toString()));
					findViewById(R.id.llInformation).setVisibility(View.GONE);
				}
			});
		};

		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			final int X = (int) motionEvent.getRawX();
			final int Y = (int) motionEvent.getRawY();
			switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_MOVE:
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
				layoutParams.leftMargin = X - _xDelta;
				layoutParams.topMargin = Y - _yDelta;
				layoutParams.rightMargin = 0;
				layoutParams.bottomMargin = 0;
				view.setLayoutParams(layoutParams);

				((PlayerView) view).marginLeft = X - _xDelta;
				((PlayerView) view).marginTop = Y - _yDelta;
				break;
			}

			return mGestureDetector.onTouchEvent(motionEvent);
		}

		private final class GestureListener extends SimpleOnGestureListener {

			private GestureHelper mHelper;

			public GestureListener(GestureHelper helper) {
				mHelper = helper;
			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				mHelper.onClick();
				return true;
			}
		}

	}
}
