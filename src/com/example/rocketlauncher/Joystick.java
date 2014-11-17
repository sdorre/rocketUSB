package com.example.rocketlauncher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.View;

public class Joystick extends View {
	
	private float lX,lY;
	final private static int SIZE = 60;
	final private Paint paint = new Paint();
	
	public Joystick(Context x) {
		super(x);
		lX = 0;
		lY = 0;
		paint.setStyle(Style.FILL);
		paint.setColor(Color.BLUE);
	}
	
	float getXLoc() {
		return lX;
	}

	void setXLoc(float x) {
		lX = x;
	}

	float getYLoc() {
		return lY;
	}

	void setYLoc(float y) {
		lY = y;
	}

	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(lX, lY, SIZE, paint);
	}
}
