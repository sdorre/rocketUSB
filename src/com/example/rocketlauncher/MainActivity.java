package com.example.rocketlauncher;


import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	private static final String TAG = "rocketLauncher";
	private static final int MIN_DIST = 2;
	
	private Calcul calc;
	
	private FrameLayout touchZone;
	private ImageButton rocketLaunch;
	public Joystick joystick;
	private int middleX, middleY;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		touchZone = (FrameLayout) findViewById(R.id.touchZone);
		rocketLaunch = (ImageButton) findViewById(R.id.rocket);
		joystick = new Joystick(this);
		
		middleX = getWindowManager().getDefaultDisplay().getWidth()/2;
		middleY = getWindowManager().getDefaultDisplay().getHeight()/3;
		joystick.setXLoc(middleX);
		joystick.setYLoc(middleY);
		joystick.setOrigX(middleX);
		joystick.setOrigY(middleY);
		touchZone.addView(joystick);
		
		calc = new Calcul(middleX, middleY);
		
		Log.i(TAG, "middle of the frame : x ="+middleX+" , y ="+middleY);
		

		/*
		Thread thread = new Thread(new Runnable(){

			@Override
			public void run() {
				while(true){
					Log.i(TAG, "DIRECTION : "+calc.giveMeDirection(joystick.getX(),joystick.getY()));
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}
			
		});
		*/
		
		
		TimerTask task = new TimerTask(){

			@Override
			public void run() {
				sendEvent();
			}
			
		};
		final Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(task, 0, 1000);
		
		
		touchZone.setOnTouchListener(new OnTouchListener(){
			public boolean onTouch(View v, MotionEvent event) {
				
				switch(event.getActionMasked()){
				
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN:{
					int pointerIndex = event.getActionIndex();

					joystick.setXLoc(event.getX(pointerIndex));
					joystick.setYLoc(event.getY(pointerIndex));
					joystick.invalidate();
					Log.i(TAG, "new touch !");
					
					break;
				}
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:{

					joystick.setXLoc(middleX);
					joystick.setYLoc(middleY);
					joystick.invalidate();
					//Log.i(TAG, "touch up - go back origin !");
					break;
				}
				case MotionEvent.ACTION_MOVE:{
					int pointerIndex = event.getActionIndex();

					if (Math.abs(joystick.getXLoc() - event.getX(pointerIndex)) > MIN_DIST
							|| Math.abs(joystick.getYLoc() - event.getY(pointerIndex)) > MIN_DIST) {
						joystick.setXLoc(event.getX(pointerIndex));
						joystick.setYLoc(event.getY(pointerIndex));
						//Log.i(TAG, "moving - angle : "+Calcul.giveMyAngle(event.getX(pointerIndex), event.getY(pointerIndex)));
						//Log.i(TAG, "moving - direction : "+calc.giveMeDirection(event.getX(pointerIndex), event.getY(pointerIndex)));
						joystick.invalidate();
					}
					break;
				}
				default:
					Log.i(TAG, "unhandled event");
				}
				
				return true;
			}
		});
		
		
		rocketLaunch.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i(TAG, "FIRE - missile launched !");
			}
		});
		
	}
	
	public void sendEvent(){
		Log.i(TAG, "X : "+joystick.getX()+" , y = "+joystick.getY());
		Log.i(TAG, "DIRECTION : "+calc.giveMeDirection(joystick.getX(),joystick.getY()));
	}
}
