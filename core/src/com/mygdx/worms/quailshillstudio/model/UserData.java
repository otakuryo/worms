package com.mygdx.worms.quailshillstudio.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

import java.io.Serializable;

public class UserData implements Serializable {

    //necesita ser serializable por que sino da un error de io
    private static final long serialVersionUID = 1L;
    World worldTemp;

    //1
	public static final int GROUND = 0;
	public static final int BOMB = 1;
	public static final int BALL = 2;
	public static final int WORM = 3;

	//2 3 4
    public float posClickX,posClickY;
    public float posX,posY;

	public int life = 100;

	//5 6 7
	public int typeObj; // worm
	public int type; //bombas
	public boolean mustDestroy;
	public boolean destroyed;
	public boolean jump = false;
	public Body worm1;

	//8
	//datos del jugador
    private String username;
    private String userIP;
    private String team;
    public int id;

    //9 10
    public int count = 0;
	public boolean isFlaggedForDelete=false;

	//al marcar como comenzar partida, comenzara la partida
	//public String comenzar = "comenzarpartida";
	public String comenzar = "-";

    //11 12 13
	//Donde y cuando se realizara el click del usuario :)
    private Vector3 clickUser;
    public int typeArm;

    String temp = type+","+"posx"+","+"posy"+","+"life"+","+mustDestroy+","+destroyed+","+jump+","+username+","+id+","+count+","+isFlaggedForDelete+","+"posClickX"+","+"posClicky"+","+typeArm;

    public UserData(){}

    public UserData(int type) {
        this.typeObj = type;
        count=0;
    }

	public UserData(int type,String username,String userIP,String team) {
		this.typeObj = type;
		this.username = username;
		this.userIP = userIP;
		this.team = team;
		count=0;
	}

	public int getType() {
		return typeObj;
	}

	public void wormJump(){
		//worm1.setLinearVelocity(worm1.getLinearVelocity().x,0);
		//worm1.applyForceToCenter(0, 320f, true);
		System.out.println("Grade up"+worm1.getLinearVelocity().x);

        if (!jump) {
            jump=true;
            worm1.applyLinearImpulse(new Vector2(worm1.getLinearVelocity().x, 10), worm1.getPosition(), true);

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

    public void wormAngleUpL() {
        if (!jump) {
            worm1.setAngularVelocity(2);
        }
    }

	public void wormAngleUpR() {
		if (!jump) {
			worm1.setAngularVelocity(-2);
		}
	}
    //public static Body createBall(int type, Vector2 position,World world) {
    public static Body createBall(int type,float posx,float posy,OrthographicCamera camera,World world) {
        System.out.println("->>"+posx+" -- "+posy);
        Vector3 box2Dpos = camera.unproject(new Vector3(posx, posy, 0));
        Vector2 position = new Vector2(box2Dpos.x, box2Dpos.y);
		System.out.println("->> 1");
		BodyDef defBall = new BodyDef();
		defBall.type = BodyDef.BodyType.DynamicBody;
		defBall.position.set(position);
		Body ball = world.createBody(defBall);
		ball.setUserData(new UserData(type));
		System.out.println("->> 2");

		FixtureDef fixDefBall = new FixtureDef();
		fixDefBall.density = .25f;
		fixDefBall.restitution = .75f;
		CircleShape rond = new CircleShape();
		rond.setRadius(1);
		System.out.println("->> 3");

		fixDefBall.shape = rond;
		ball.createFixture(fixDefBall);
		rond.dispose();
		System.out.println("->> 4");

		return ball;
	}
	public Body createWorm(int type, Vector2 position,World world,String username) {
		this.username=username;
		this.worldTemp = world;
		this.typeObj = type;
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

		Vector2 pos = ball.getWorldCenter();
		float angle = ball.getAngle(); //if you need rotation

		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.box(position.x,position.y,0,100,100,100);
		//shapeRenderer.circle(pos.x, pos.y,tri.getRadius());
		shapeRenderer.end();

		worm1 = ball;
		return ball;
	}

    public void searchAndCreateBall(UserData ud,OrthographicCamera camera,World world) {

    	if (ud.posClickX>0 && ud.posClickY>0){
			//Vector3 box2Dpos = camera.unproject(new Vector3(ud.posClickX, ud.posClickY-30, 0));
			Vector2 position = new Vector2(worm1.getPosition().x, worm1.getPosition().y+2.25f);

			BodyDef defBall = new BodyDef();
			defBall.type = BodyDef.BodyType.DynamicBody;
			defBall.position.set(position);
			Body ball = world.createBody(defBall);
			ball.setUserData(new UserData(ud.type));

			FixtureDef fixDefBall = new FixtureDef();
			fixDefBall.density = .25f;
			fixDefBall.restitution = .75f;
			CircleShape rond = new CircleShape();
			rond.setRadius(1);
			fixDefBall.shape = rond;
			ball.createFixture(fixDefBall);
			rond.dispose();
			ball.applyLinearImpulse(22,22,position.x,position.y,true);
			//ball.applyLinearImpulse(22,22,box2Dpos.x,box2Dpos.y,true);
			//ball.applyLinearImpulse(100,0,box2Dpos.x,box2Dpos.y,true);
			//ball.applyLinearImpulse(2,6,box2Dpos.x,box2Dpos.y,true);
			ud.posClickY=0;
			ud.posClickX=0;
    	}
    }

    public String getUsername() {return username;}

    public String getUserIP() {return userIP;}

    public String getTeam() {return team;}

    public Vector3 getClickUser() {return clickUser;}

    public void setClickUser(Vector3 clickUser) {this.clickUser = clickUser;}

    public int getTypeArm() {return typeArm;}

    public void setTypeArm(int typeArm) {this.typeArm = typeArm;}

    public void setStart(){comenzar="comenzarpartida";}

    public void modUserData(int idE,int typeE,float posXE,float posYE,int lifeE,boolean mustDestroyE,boolean destroyedE
			,boolean jumpE,int countE,boolean isFlaggedForDeleteE,float posClickXE,float posclickYE,int typeArmE){
		this.id=idE;
		this.type=typeE;

        this.posClickX = posClickXE;
        this.posClickY = posclickYE;

        this.posX = posXE;
        this.posY = posYE;

		this.life=lifeE;
		this.mustDestroy=mustDestroyE;
		this.destroyed=destroyedE;
		this.jump=jumpE;
		this.count=countE;
		this.isFlaggedForDelete=isFlaggedForDeleteE;

		this.typeArm=typeArmE;
	}
}
