package com.mygdx.worms.quailshillstudio.articlepolygonclipping;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.worms.quailshillstudio.polygonClippingUtils.GroundFixture;
import com.mygdx.worms.quailshillstudio.polygonClippingUtils.PolygonBox2DShape;
import com.mygdx.worms.quailshillstudio.polygonClippingUtils.UserData;
import com.mygdx.worms.quailshillstudio.polygonClippingUtils.WorldCollisions;

import java.util.ArrayList;
import java.util.List;

public class 	PolygonClipping extends ApplicationAdapter {
	SpriteBatch batch;
	World world;
	Box2DDebugRenderer renderer;
	OrthographicCamera camera;
	private List<GroundFixture> polyVerts = new ArrayList<GroundFixture>();
	private boolean mustCreate;
	private float accu;
	private static final float TIME_STEP = 1 / 60f;
	private static int speedIte = 6, posIte = 2;
	private int count = 0;
   
    private float rotationSpeed;

	@Override
	public void create () {
	    float relation=1f;
		batch = new SpriteBatch();
		world = new World(new Vector2(0, -9.81f), false);
		world.setContactListener(new WorldCollisions(this));
		renderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera(Gdx.graphics.getWidth()/relation,Gdx.graphics.getHeight()/relation);

        rotationSpeed = 0.5f;
		
		List<float[]> verts = new ArrayList<float[]>();
		float[] points = {100,10,-14,12f,9,-11f,60,-10,-10};
		float[] pointsb = {102,682,590,681,589,644,596,615,602,596,588,576,569,572,573,547,588,498,579,470,569,429,533,391,517,369,476,321,445,316,416,285,410,260,374,264,357,244,325,239,282,246,263,261,231,284,217,302,216,319,221,339,184,393,171,404,159,385,110,395,99,415,79,444,92,507,104,562,114,592,90,628};
        //float[] pointsb = {-60,-10,-60,-40f,60,-40f,60,-10};
		verts.add(points);
		verts.add(pointsb);
		GroundFixture grFix = new GroundFixture(verts);
		polyVerts.add(grFix);
		mustCreate = true;
		
		createBall(UserData.BALL, new Vector2(0,0));
        //createBall(UserData.BOMB, new Vector2(10,30));
        System.out.println("->"+batch.getColor().toString());
	}

	@Override
	public void render () {

	    //movimiento de camera
        handleInput();
        camera.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.render(world, camera.combined);

		//crea un objeto nuevo al pulsar
		if(Gdx.input.justTouched()){
			int type;
			count++;
			if(count %2 == 0){
				type = UserData.BALL;
			}else{
				type = UserData.BOMB;
			}
			Vector3 box2Dpos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			createBall(type, new Vector2(box2Dpos.x, box2Dpos.y));
		}
		
		for (int i = 0; i < world.getBodyCount(); i++) {
			Array<Body> bodies = new Array<Body>();
			world.getBodies(bodies );
			UserData data = ((UserData) bodies.get(i).getUserData());
			if (data != null && data.getType() == UserData.GROUND) {
				if ((data.mustDestroy || mustCreate) && !data.destroyed) {
					world.destroyBody(bodies.get(i));
					bodies.removeIndex(i);
				}
			}
		}
		
		if(mustCreate)
			createGround();

		box2dTimeStep(Gdx.graphics.getDeltaTime());
	}

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 30f;
        camera.viewportHeight = 30f * height/width;
        camera.update();
    }

    @Override
    public void dispose() {
        //mapSprite.getTexture().dispose();
        batch.dispose();
    }

	public void switchGround(List<PolygonBox2DShape> rs) {
		mustCreate = true;
		List<float[]> verts = new ArrayList<float[]>();
		for (int i = 0; i < rs.size(); i++) {
			verts.add(rs.get(i).verticesToLoop());
		}
		GroundFixture grFix = new GroundFixture(verts);
		polyVerts.add(grFix);
	}
	
	protected void createGround() {
		BodyDef groundDef = new BodyDef();
		groundDef.type = BodyDef.BodyType.StaticBody;
		groundDef.position.set(0, 0);

		for (int i = 0; i < polyVerts.size(); i++) {
			Body nground = world.createBody(groundDef);
			UserData usrData = new UserData(UserData.GROUND);
			nground.setUserData(usrData);

			List<Fixture> fixtures = new ArrayList<Fixture>();
			for (int y = 0; y < this.polyVerts.get(i).getVerts().size(); y++) {
				if (this.polyVerts.get(i).getVerts().get(y).length >= 6) {
					ChainShape shape = new ChainShape();
					shape.createLoop(this.polyVerts.get(i).getVerts()
							.get(y));
					FixtureDef fixtureDef = new FixtureDef();
					fixtureDef.shape = shape;
					fixtureDef.density = 1;
					fixtureDef.friction = .8f;
					fixtures.add(nground.createFixture(fixtureDef));
				}
			}
			polyVerts.get(i).setFixtures(fixtures);
		}
		this.mustCreate = false;
		polyVerts.clear();
	}
	
	public Body createBall(int type, Vector2 position) {
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
	
	private void box2dTimeStep(float deltaTime) {
		float delta = Math.min(deltaTime, 0.25f);
		accu += delta;
		while (accu >= TIME_STEP) {
			world.step(TIME_STEP, speedIte, posIte);
			accu -= TIME_STEP;
		}
	}

	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			camera.zoom += 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			camera.zoom -= 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.translate(-3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.translate(3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camera.translate(0, -3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.translate(0, 3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			camera.rotate(-rotationSpeed, 0, 0, 1);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			camera.rotate(rotationSpeed, 0, 0, 1);
		}

		//limite de zoom de la camara :)
		camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 500/camera.viewportWidth);

		float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
		float effectiveViewportHeight = camera.viewportHeight * camera.zoom;


		//limita la vision del mapa a 0,0 como punto minimo, siendo 0,0 la parte inferior izquiera de la ventana
		camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, 1000 - effectiveViewportWidth / 2f);
		camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, 1000 - effectiveViewportHeight / 2f);
	}
}
