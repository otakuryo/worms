package com.mygdx.worms.quailshillstudio.polygonClippingUtils;

public class UserData {
	public static final int GROUND = 0;
	public static final int BOMB = 1;
	public static final int BALL = 2;
	public static final int HUB = 3;
	public static final int WORM = 4;
	public int X = 0;
	public int Y = 0;
	public int type;
	public boolean mustDestroy;
	public boolean destroyed;
	public boolean jump = false;

	public int count = 0;
	public boolean isFlaggedForDelete=false;

	public UserData(int type) {
		this.type = type;
		count=0;
	}

	public int getType() {
		return type;
	}
}
