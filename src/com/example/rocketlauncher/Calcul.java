package com.example.rocketlauncher;

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
		if(isBetween(angle, -Math.PI, -3*Math.PI/4) || isBetween(angle, 3*Math.PI/4, Math.PI))
			return Direction.DOWN;
		else if(isBetween(angle, -3*Math.PI/4, -Math.PI/4))
			return Direction.LEFT;
		else if(isBetween(angle, -Math.PI/4, Math.PI/4))
			return Direction.UP;
		else if(isBetween(angle, Math.PI/4, 3*Math.PI/4))
			return Direction.RIGHT;

		else
			return Direction.UNKNOWN;
		
	}
	
	private boolean isBetween(double val, double min, double max){
		return (min <= val)&&(val <= max);
	}
}
