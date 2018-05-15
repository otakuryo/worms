package com.mygdx.worms.quailshillstudio.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

import java.io.Serializable;

public class UserData implements Serializable {

    //necesita ser serializable por que sino da un error de io
    private static final long serialVersionUID = 1L;

    //1
	public static final int GROUND = 0;
	public static final int BOMB = 1;
	public static final int BALL = 2;
	public static final int WORM = 3;

	//2 3 4
	public Vector2 position;
	public int life = 100;

	//5 6 7
	public int type;
	public boolean mustDestroy;
	public boolean destroyed;
	private boolean jump = false;
	private Body worm1;

	//8
	//datos del jugador
    private String username;
    private String userIP;
    private String team;
    private String id;

    //9 10
    public int count = 0;
	public boolean isFlaggedForDelete=false;

	//al marcar como comenzar partida, comenzara la partida
	//public String comenzar = "comenzarpartida";
	public String comenzar = "-";

    //11 12 13
	//Donde y cuando se realizara el click del usuario :)
    private Vector3 clickUser;
    private int typeArm;

    String temp = type+","+"posx"+","+"posy"+","+"life"+","+mustDestroy+","+destroyed+","+jump+","+username+","+count+","+isFlaggedForDelete+","+"posClickX"+","+"posClicky"+","+typeArm;

	public UserData(){}

    public UserData(int type) {
        this.type = type;
        count=0;
    }

	public UserData(int type,String username,String userIP,String team) {
		this.type = type;
		this.username = username;
		this.userIP = userIP;
		this.team = team;
		this.id="0";
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
		//worm1.setLinearVelocity(worm1.getLinearVelocity().x,0);
		//worm1.applyForceToCenter(0, 320f, true);

        if (!jump) {
            jump=true;
            worm1.applyLinearImpulse(new Vector2(0, 8), worm1.getPosition(), true);

            //delay para volver a saltar
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    jump=false;
                }
            }, 2);
        }
	}
	public void wormLeft(){
	    if (!jump){
		    worm1.setLinearVelocity(worm1.getLinearVelocity().x,0);
		    worm1.applyForceToCenter(-20f, 0f, true);
	    }
	}

	public void wormRight() {
        if (!jump) {
            worm1.setLinearVelocity(worm1.getLinearVelocity().x, 0);
            worm1.applyForceToCenter(20f, 0f, true);
        }
	}

    public void wormAngleUp() {
        if (!jump) {
            worm1.setAngularVelocity(2);
        }
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

    public String getUsername() {return username;}

    public String getUserIP() {return userIP;}

    public String getTeam() {return team;}

    public Vector3 getClickUser() {return clickUser;}

    public void setClickUser(Vector3 clickUser) {this.clickUser = clickUser;}

    public int getTypeArm() {return typeArm;}

    public void setTypeArm(int typeArm) {this.typeArm = typeArm;}

    public void setStart(){comenzar="comenzarpartida";}
}
