package com.mygdx.worms.quailshillstudio.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.worms.quailshillstudio.polygonClippingUtils.GroundFixture;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.List;

public class UserData {
	public static final int GROUND = 0;
	public static final int BOMB = 1;
	public static final int BALL = 2;
	public static final int WORM = 3;
	public int X = 0;
	public int Y = 0;
	public int type;
	public boolean mustDestroy;
	public boolean destroyed;
	public boolean jump = false;
	Body worm1;

	public int count = 0;
	public boolean isFlaggedForDelete=false;

	public UserData(){}
	public UserData(int type) {
		this.type = type;
		count=0;
	}

	public int getType() {
		return type;
	}

	public Body createWorm(int type, Vector2 position,World world) {
		this.type = type;
		count=0;
		BodyDef defBall = new BodyDef();
		defBall.type = BodyDef.BodyType.DynamicBody;
		defBall.position.set(position);
		Body ball = world.createBody(defBall);
		ball.setUserData(this);

		FixtureDef fixDefBall = new FixtureDef();
		fixDefBall.density = .25f;
		fixDefBall.restitution = .25f;
		fixDefBall.friction=1f;
		PolygonShape tri = new PolygonShape();
		tri.setAsBox(1,1);
		//CircleShape tri = new CircleShape();
		//tri.setRadius(1);

		fixDefBall.shape = tri;
		ball.createFixture(fixDefBall);
		tri.dispose();

		worm1 = ball;
		return ball;
	}

	public void wormJump(){
		worm1.setLinearVelocity(worm1.getLinearVelocity().x,0);
		worm1.applyForceToCenter(0, 320f, true);
	}
	public void wormLeft(){
		worm1.setLinearVelocity(worm1.getLinearVelocity().x,0);
		worm1.applyForceToCenter(-20f, 0f, true);
	}

	public void wormRight() {
		worm1.setLinearVelocity(worm1.getLinearVelocity().x,0);
		worm1.applyForceToCenter(20f, 0f, true);
	}

	public static Body createBall(int type, Vector2 position,World world) {
		BodyDef defBall = new BodyDef();
		defBall.type = BodyDef.BodyType.DynamicBody;
		defBall.position.set(position);
		Body ball = world.createBody(defBall);
		ball.setUserData(new UserData(type));

		FixtureDef fixDefBall = new FixtureDef();
		fixDefBall.density = .25f;
		fixDefBall.restitution = .75f;
		CircleShape rond = new CircleShape();
		rond.setRadius(1);

		fixDefBall.shape = rond;
		ball.createFixture(fixDefBall);
		rond.dispose();

		return ball;
	}

}
