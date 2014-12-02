package com.example.rocketlauncher;

import com.example.android.libturretctl;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivityStatic extends Activity implements OnClickListener{

	private static final String TAG = "rocketLauncher";
	
	private ImageView padCenter;
	private ImageView padUp;
	private ImageView padDown;
	private ImageView padRight;
	private ImageView padLeft;
	
	private ImageButton rocketLaunch;
	
	private TurretCtl turret 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_button);
		
		padCenter = (ImageView) findViewById(R.id.padCenter);
		padCenter.setOnClickListener(this);
		
		padUp = (ImageView) findViewById(R.id.padUp);
		padUp.setOnClickListener(this);
		
		padDown = (ImageView) findViewById(R.id.padDown);
		padDown.setOnClickListener(this);
		
		padRight = (ImageView) findViewById(R.id.padRight);
		padRight.setOnClickListener(this);
		
		padLeft = (ImageView) findViewById(R.id.padLeft);
		padLeft.setOnClickListener(this);
		
		rocketLaunch = (ImageButton) findViewById(R.id.rocket);
		rocketLaunch.setOnClickListener(this);
		
		turret = new TurretCtl();
	    turret.initUsb();

	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.padCenter:
			Log.i(TAG, "STOP");
			turret.stop();
            Thread.sleep(2000)
			break;
            
		case R.id.padDown:
			Log.i(TAG, "MOVE DOWN");
			turret.moveDown();
            Thread.sleep(2000);
			break;
            
		case R.id.padLeft:
			Log.i(TAG, "MOVE LEFT");
			turret.moveLeft();
            Thread.sleep(2000);
			break;
			
		case R.id.padRight:
			Log.i(TAG, "MOVE RIGHT");
			turret.moveRight();
            Thread.sleep(2000);
			break;
            
		case R.id.padUp:
			Log.i(TAG, "MOVE UP");
			turret.moveUp();
            Thread.sleep(2000);
			break;
            
		case R.id.rocket:
			Log.i(TAG, "FIRE - missile launched !");
			turret.fire();
            Thread.sleep(2000);
			break;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		turret.freeUsb();
	}
	
	
}
