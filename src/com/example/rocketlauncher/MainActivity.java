package com.example.rocketlauncher;


import android.app.Activity;
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
	private Joystick joystick;
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
		touchZone.addView(joystick);
		
		calc = new Calcul(middleX, middleY);
		
		Log.i(TAG, "middle of the frame : x ="+middleX+" , y ="+middleY);
		
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
					Log.i(TAG, "touch up - go back origin !");
					break;
				}
				case MotionEvent.ACTION_MOVE:{
					int pointerIndex = event.getActionIndex();

					if (Math.abs(joystick.getXLoc() - event.getX(pointerIndex)) > MIN_DIST
							|| Math.abs(joystick.getYLoc() - event.getY(pointerIndex)) > MIN_DIST) {
						joystick.setXLoc(event.getX(pointerIndex));
						joystick.setYLoc(event.getY(pointerIndex));
						//Log.i(TAG, "moving - angle : "+Calcul.giveMyAngle(event.getX(pointerIndex), event.getY(pointerIndex)));
						Log.i(TAG, "moving - direction : "+calc.giveMeDirection(event.getX(pointerIndex), event.getY(pointerIndex)));
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
}

class Calcul{
	private static float Ox;
	private static float Oy;
	public enum Direction{
		RIGHT,
		LEFT,
		UP,
		UP_RIGHT,
		UP_LEFT,
		DOWN,
		DOWN_RIGHT,
		DOWN_LEFT,
		UNKNOWN
	}
	
	public Calcul(int x, int y){
		Ox=x;
		Oy=y;
	}
	
	public static double giveMyAngle(float x, float y){
		float mX = x-Ox;
		float mY = y-Oy;
		return (2*Math.atan(mY/(mX+(Math.sqrt(mX*mX + mY*mY)))));
	}
	
	public Direction giveMeDirection(float x, float y){
		double angle = giveMyAngle(x, y);
		if(isBetween(angle, -Math.PI, -5*Math.PI/6) || isBetween(angle, 5*Math.PI/6, Math.PI))
			return Direction.DOWN;
		else if(isBetween(angle, -5*Math.PI/6, -2*Math.PI/3))
			return Direction.DOWN_LEFT;
		else if(isBetween(angle, -2*Math.PI/3, -Math.PI/3))
			return Direction.LEFT;
		else if(isBetween(angle, -Math.PI/3, -Math.PI/6))
			return Direction.UP_LEFT;
		else if(isBetween(angle, -Math.PI/6, Math.PI/6))
			return Direction.UP;
		else if(isBetween(angle, Math.PI/6, Math.PI/3))
			return Direction.UP_RIGHT;
		else if(isBetween(angle, Math.PI/3, 2*Math.PI/3))
			return Direction.RIGHT;
		else if(isBetween(angle, 2*Math.PI/3, 5*Math.PI/6))
			return Direction.DOWN_RIGHT;
		else
			return null;
		
	}
	
	private boolean isBetween(double val, double min, double max){
		return (min <= val)&&(val <= max);
	}
}
