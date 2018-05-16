package com.mygdx.worms.quailshillstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.worms.quailshillstudio.AdapterScreen.AbstractScreen;
import com.mygdx.worms.quailshillstudio.model.UserData;
import com.mygdx.worms.quailshillstudio.polygonClippingUtils.GroundFixture;
import com.mygdx.worms.quailshillstudio.polygonClippingUtils.PolygonBox2DShape;
import com.mygdx.worms.quailshillstudio.polygonClippingUtils.WorldCollisions;
import com.mygdx.worms.serverUtils.Persona;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorldScreen  extends AbstractScreen {
    SpriteBatch batch;
    Texture guns;
    ShapeRenderer shapeRenderer;
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

    //maxPoint es el punto maximo de recorrido en la pantalla en el eje x,y
    private int maxPoint = 260;

    //tiempo
    float totalTime = 5 * 60;

    //worms
    HashMap<Integer,UserData> wUS = new HashMap<Integer, UserData>();
    //Body worm1;
    //ArrayList<UserData> us = new ArrayList<UserData>();

    void create_world(){
        float relation=1f;

        //dibujamos imagen
        batch = new SpriteBatch();
        guns = new Texture("hud.png");
        world = new World(new Vector2(0, -9.81f), false);
        world.setContactListener(new WorldCollisions(this));
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth()/relation,Gdx.graphics.getHeight()/relation);

        shapeRenderer = new ShapeRenderer();
        rotationSpeed = 0.5f;

        List<float[]> verts = new ArrayList<float[]>();
        float[] points = {107.2f,1.2f,176.2f,1.8f,184f,15.6f,169.3f,28.5f,173.5f,33.9f,171.7f,39f,178.6f,44.4f,190f,35.4f,199.3f,34.5f,200.8f,41.7f,180.7f,54.3f,165.7f,54f,167.5f,60.9f,172.6f,69f,162.7f,75.9f,138.4f,80.1f,131.2f,77.1f,128.2f,81f,120.1f,74.1f,121.3f,55.5f,118.6f,45.3f,107.2f,45.3f,105.4f,31.2f,107.8f,24.6f,101.5f,12.3f};
        float[] pointsb = {14.8f,0.8f,60.8f,1.2f,66f,10.4f,56.2f,19f,59f,22.6f,57.8f,26f,62.4f,29.6f,70f,23.6f,76.2f,23f,77.2f,27.8f,63.8f,36.2f,53.8f,36f,55f,40.6f,58.4f,46f,51.8f,50.6f,35.6f,53.4f,30.8f,51.4f,28.8f,54f,23.4f,49.4f,24.2f,37f,22.4f,30.2f,14.8f,30.2f,13.6f,20.8f,15.2f,16.4f,11f,8.2f};
        //float[] pointsb = {-60,-10,-60,-40f,60,-40f,60,-10};
        verts.add(points);
        verts.add(pointsb);
        GroundFixture grFix = new GroundFixture(verts);
        polyVerts.add(grFix);
        mustCreate = true;

        //UserData.createBall(UserData.BALL, new Vector2(0,0),world);
        //forma 1
        //us.add(new UserData());
        //us.get(0).createWorm(UserData.WORM, new Vector2(20, 40),world);

        //forma 2
        wUS.put(0,new UserData());
        wUS.get(0).createWorm(UserData.WORM, new Vector2(20, 40),world);

        //us.add(new UserData());
        //us.get(1).createWorm(UserData.WORM, new Vector2(30, 50),world);
        //createBall(UserData.BOMB, new Vector2(10,30));

    }

    void create_render(){

        //movimiento de camera
        handleInput(0);
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

            UserData.createBall(type,Gdx.input.getX(), Gdx.input.getY(),camera,world);
            //idE,typeE,posxE,posyE,lifeE,mustDestroyE,destroyedE,jumpE,countE,isFlaggedForDeleteE,posClickXE,posclickYE,typeArmE
            //id+","+username+","+type+","+"posx"+","+"posy"+","+"life"+","+mustDestroy+","+destroyed+","+jump+","+count+","+isFlaggedForDelete+","+"posClickX"+","+"posClicky"+","+typeArm;
            //new Persona(0).getDataServer("0","setData");
            //Vector3 box2Dpos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            //UserData.createBall(type, new Vector2(box2Dpos.x, box2Dpos.y),world);
        }

        //en esta parte eliminamos parte del mapa si es que colisiona
        for (int i = 0; i < world.getBodyCount(); i++) {
            Array<Body> bodies = new Array<Body>();
            world.getBodies(bodies);
            UserData data = ((UserData) bodies.get(i).getUserData());
            if (data != null && data.getType() == UserData.GROUND) {
                if ((data.mustDestroy || mustCreate) && !data.destroyed) {
                    world.destroyBody(bodies.get(i));
                    bodies.removeIndex(i);
                }
            }
            //Lo anyadimos a la cola de borrado
            if (data != null && data.getType() == UserData.BOMB) {
                if (data.isFlaggedForDelete) {
                    world.destroyBody(bodies.get(i));
                    bodies.get(i).setUserData(null);
                    //bodies.removeIndex(i);
                }
            }
        }

        if(mustCreate)
            createGround();

        //tiempo();
        box2dTimeStep(Gdx.graphics.getDeltaTime());

        //Dibujamos el HUD
        hudBase();
    }


    void hudBase(){
        batch.begin();
        batch.draw(guns,((camera.viewportWidth*13)/2)-(guns.getWidth()/2), (camera.viewportHeight*11));
        batch.end();
    }

    void tiempo(){
        float deltaTime = Gdx.graphics.getDeltaTime(); //You might prefer getRawDeltaTime()

        totalTime -= deltaTime; //if counting down

        int minutes = ((int)totalTime) / 60;
        int seconds = ((int)totalTime) % 60;

        System.out.println("time: "+minutes+":"+seconds);
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

    private void box2dTimeStep(float deltaTime) {
        float delta = Math.min(deltaTime, 0.25f);
        accu += delta;
        while (accu >= TIME_STEP) {
            world.step(TIME_STEP, speedIte, posIte);
            accu -= TIME_STEP;
        }
    }

    private void handleInput(int player) {
        if (Gdx.input.justTouched()){
            //System.out.println(Gdx.input.getX()+" - "+Gdx.input.getY());
        }

        //movimiento de la camara
        if (Gdx.input.isKeyPressed(Input.Keys.U)) {
            camera.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.J)) {
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

        //Movimiento del personaje
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            wUS.get(player).wormAngleUp();
        }
        //salto del player,
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            //UserData us = (UserData) worm1.getUserData();
            wUS.get(player).wormJump();
            //System.out.println("Grade up");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            System.out.println("Grade up");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            wUS.get(player).wormLeft();
            //System.out.println("A");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            wUS.get(player).wormRight();
            //System.out.println("D");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            System.out.println("Arm 1");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            System.out.println("Arm 2");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            System.out.println("Arm 3");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
            System.out.println("Arm 4");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_5)) {
            System.out.println("Arm 5");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_6)) {
            System.out.println("Arm 6");
        }

        //A tener en cuenta que si se rota, no se rota las direcciones de teclado XD
        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            camera.rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            camera.rotate(rotationSpeed, 0, 0, 1);
        }

        //limite de zoom de la camara :)
        camera.zoom = MathUtils.clamp(camera.zoom, 0.32f, 230/camera.viewportWidth);

        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        //limita la vision del mapa a 0,0 como punto minimo, siendo 0,0 la parte inferior izquiera de la ventana
        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, maxPoint - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, maxPoint - effectiveViewportHeight / 2f);
    }

    void enviarDatos(int player){
        //modificamos userdata y lo enviamos :)
        HashMap<Integer,UserData> server = new HashMap<Integer, UserData>();
        server.put(player,wUS.get(player));
        //wUS.get(player);
    }

    @Override
    public void buildStage() {
        create_world();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        create_render();
    }

    @Override
    public void resize(int width, int height) {
        //tamaño inicial de la camara
        camera.viewportWidth = 100f;
        camera.viewportHeight = 100f * height/width;
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
