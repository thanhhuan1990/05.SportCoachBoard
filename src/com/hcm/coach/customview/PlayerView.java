package com.hcm.coach.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hcm.coach.R;

public class PlayerView extends RelativeLayout {

	private ImageView 	ivPlayer;
	private TextView 	tvNumber;
    private TextView 	tvName;
    
	public int 			team;
	public String 		name;
	public int 			number;
	public int 			background;
	public int 			size;
	public int 			marginLeft;
	public int 			marginTop;

    public PlayerView(Context context, int team, String name, int number, int background, int size, int marginLeft, int marginTop) {
        super(context);
        this.team 		= team;
		this.name 		= name;
		this.number 	= number;
		this.background	= background;
		this.size		= size;
		this.marginLeft = marginLeft;
		this.marginTop	= marginTop;
		
        init();
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
    
	public void setBackground(int id) {
		this.background	= id;
    	this.ivPlayer.setImageResource(id);
    }
    
    public void setNumber(int number) {
    	this.number = number;
    	this.tvNumber.setText(number + "");
    }
    
    public void setName(String name) {
    	this.name = name;
    	this.tvName.setText(name);
    }
    
    private void init() {
        inflate(getContext(), R.layout.player, this);
        
        this.ivPlayer	= (ImageView) findViewById(R.id.ivPlayer);
        this.ivPlayer.setImageResource(background);
        this.tvNumber	= (TextView) findViewById(R.id.tvNumber);
        this.tvNumber.setText(String.valueOf(number));
        this.tvName		= (TextView) findViewById(R.id.tvName);
        this.tvName.setText(name);
    }
}
