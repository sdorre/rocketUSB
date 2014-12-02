package com.example.rocketlauncher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.View;

public class Joystick extends View {
	
	private float lX,lY, origX, origY;
	final private static int SIZE = 40;
	final private Paint paint = new Paint();
	
	public Joystick(Context x) {
		super(x);
		lX = 0;
		lY = 0;
		origX =0;
		origY =0;
		paint.setStyle(Style.FILL);
		paint.setColor(Color.BLUE);
	}
	
	public synchronized float getXLoc() {
		return lX;
	}

	public synchronized void setXLoc(float x) {
		lX = x;
	}

	public synchronized float getYLoc() {
		return lY;
	}

	public synchronized void setYLoc(float y) {
		lY = y;
	}
	
	public synchronized float getOrigX() {
		return origX;
	}

	public synchronized void setOrigX(float y) {
		origX = y;
	}
	
	public synchronized float getOrigY() {
		return origY;
	}

	public synchronized void setOrigY(float y) {
		origY = y;
	}

	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(lX, lY, SIZE, paint);
		canvas.drawCircle(origX, origY, SIZE, paint);
	}
}
