package com.mygdx.worms.quailshillstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.worms.quailshillstudio.AdapterScreen.AbstractScreen;
import com.mygdx.worms.quailshillstudio.model.UserData;
import com.mygdx.worms.quailshillstudio.polygonClippingUtils.GroundFixture;
import com.mygdx.worms.quailshillstudio.polygonClippingUtils.PolygonBox2DShape;
import com.mygdx.worms.quailshillstudio.polygonClippingUtils.WorldCollisions;
import com.mygdx.worms.quailshillstudio.utils.ConfigGen;
import com.mygdx.worms.quailshillstudio.utils.ScreenEnum;
import com.mygdx.worms.quailshillstudio.utils.ScreenManager;
import com.mygdx.worms.quailshillstudio.utils.UIFactory;
import com.mygdx.worms.serverUtils.Persona;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorldScreen  extends AbstractScreen {

    //altura y ancho nativos
    int genW, genH;

    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    BitmapFont font;

    Texture help;
    boolean showHelp;
    FreeTypeFontGenerator generator;
    //BitmapFont font = new BitmapFont();

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

        //font = new BitmapFont();
        //dibujamos imagen
        batch = new SpriteBatch();
        help = new Texture("helpKeys.png");
        world = new World(new Vector2(0, -9.81f), false);
        world.setContactListener(new WorldCollisions(this));
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth()/relation,Gdx.graphics.getHeight()/relation);

        shapeRenderer = new ShapeRenderer();

        List<float[]> verts = new ArrayList<float[]>();
        float[] points = {107.2f,1.2f,176.2f,1.8f,184f,15.6f,169.3f,28.5f,173.5f,33.9f,171.7f,39f,178.6f,44.4f,190f,35.4f,199.3f,34.5f,200.8f,  51.7f,180.7f,54.3f,165.7f,54f,167.5f,60.9f,172.6f,69f,162.7f,75.9f,138.4f,80.1f,131.2f,77.1f,128.2f,81f,120.1f,74.1f,121.3f,55.5f,118.6f,45.3f,107.2f,45.3f,105.4f,31.2f,107.8f,24.6f,101.5f,12.3f};
        float[] pointsb = {14.8f,0.8f,60.8f,1.2f,66f,10.4f,56.2f,19f,59f,22.6f,57.8f,26f,62.4f,29.6f,70f,23.6f,76.2f,23f,77.2f,27.8f,63.8f,36.2f,103.8f,36f,100f,45.6f,58.4f,46f,51.8f,50.6f,35.6f,53.4f,30.8f,51.4f,28.8f,54f,23.4f,49.4f,24.2f,37f,22.4f,30.2f,14.8f,30.2f,13.6f,20.8f,15.2f,16.4f,11f,8.2f};
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
        wUS.get(0).createWorm(UserData.WORM, new Vector2(115, 48),world,"Fantasma");


        wUS.put(1,new UserData());
        wUS.get(1).createWorm(UserData.WORM, new Vector2(70, 48),world,"TU");
        //us.add(new UserData());
        //wUS.get(0).createWorm(UserData.WORM, new Vector2(20, 35),world,"ryo");
        //us.get(1).createWorm(UserData.WORM, new Vector2(30, 50),world);
        //createBall(UserData.BOMB, new Vector2(10,30));

        //font.setColor(Color.WHITE);

        //creamos la fuente para mostrarlo
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/leadcoat.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 26;
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        parameter.shadowColor = Color.BLACK;
        font = generator.generateFont(parameter);
    }

    void create_render(){
        int player = 1;

        //movimiento de camera
        handleInput(player);
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render(world, camera.combined);

        //UserData.createBall(type,Gdx.input.getX(), Gdx.input.getY(),camera,world);

        //comprobamos si alguien lanzo un proyectil
        wUS.get(player).searchAndCreateBall(wUS.get(player),camera,world);

        //si el jugador se encuentra fuera del rango, se elimina del mapa :(
        if (wUS.get(player).worm1.getPosition().y<0){
            //System.out.println(wUS.get(player).worm1.getPosition().x+" - "+wUS.get(player).worm1.getPosition().y);
            System.out.println("El jugador: "+wUS.get(player).getUsername()+" murio ahogado :( "+wUS.get(player).life);
            wUS.get(player).life=-10;
            wUS.get(player).mustDestroy=true;
        }
        //crea un objeto nuevo al pulsar
        if(Gdx.input.justTouched() && wUS.get(player).life>0){
            int type;
            count++;
            if(count %2 == 0){
                type = UserData.BOMB;
            }else{
                type = UserData.BOMB;
            }

            wUS.get(player).posClickX = Gdx.input.getX();
            wUS.get(player).posClickY = Gdx.input.getY();
            wUS.get(player).type = type;

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
            if (data != null && (data.getType() == UserData.BOMB || data.getType() == UserData.WORM && data.life<0)) {
                if (data.isFlaggedForDelete) {
                    world.destroyBody(bodies.get(i));
                    bodies.get(i).setUserData(null);
                    System.out.println("destruido...");
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

        //dibuajdo de rotacion - beta, falla la rotacion :(

        //dibujando la posicion de lanzamiento + animacion
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.identity();
        shapeRenderer.translate(80, 80, 0);
        shapeRenderer.rotate(0, 0, 10, wUS.get(player).worm1.getAngle() * 20f);
        shapeRenderer.rect(-12, -12, 24, 24);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.identity();
        shapeRenderer.translate(80, 80, 0);
        shapeRenderer.rotate(0, 0, 1,wUS.get(player).angleArm);
        shapeRenderer.circle(wUS.get(player).forceArm,wUS.get(player).forceArm,10);
        shapeRenderer.end();

    }
    void drawLifeBar(int pLife,int heigh,int width){
        if (pLife>0) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.valueOf("#0aa52e"));
            shapeRenderer.identity();
            shapeRenderer.rect(width, heigh, pLife * 3, 25);
            shapeRenderer.end();
        }else {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.valueOf("#FF4C4C"));
            shapeRenderer.identity();
            shapeRenderer.rect(width, heigh, (-1*pLife), 25);
            shapeRenderer.end();
        }
    }

    int textspace=30;

    void hudBase(){

        //dibujamos el nombre del usuario
        for (int i = 0; i < wUS.size(); i++) {
            drawLifeBar(wUS.get(i).life,genH-(28*(i+2)),35);
            batch.begin();
            font.draw(batch,wUS.get(i).getUsername(),40,genH-(textspace*(i+1)));
            batch.end();
        }
        if (showHelp) {
            batch.begin();
            batch.draw(help, ConfigGen.SCREEN_WIDTH / 5, ConfigGen.SCREEN_HEIGHT / 6);
            batch.end();
        }
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
        if (Gdx.input.isKeyPressed(Input.Keys.COMMA)) {
            camera.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            camera.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-2, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(2, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -2, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, 2, 0);
        }

        //Movimiento del personaje
        //salto del player,
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))  wUS.get(player).wormJump();


        //problemas con la gravedad :(
        //if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)) wUS.get(player).wormLeft();
        //if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.S)) wUS.get(player).wormRight();

        if (Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.S)) {
            wUS.get(player).wormAngleUpL();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.S)) {
            wUS.get(player).wormAngleUpR();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            wUS.get(player).wormJump();
        }

        //movimiento del proyectil
        //nagulo de lanzamiento
        if (Gdx.input.isKeyPressed(Input.Keys.Y)) {
            wUS.get(player).angleArm+=1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            if (wUS.get(player).angleArm>-50) {
                wUS.get(player).angleArm -= 1;
            }
        }
        //fuerza de proyectil
        if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            if (wUS.get(player).forceArm<50) {
                wUS.get(player).forceArm += 1;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.G)) {
            if (wUS.get(player).forceArm>20) {
                wUS.get(player).forceArm -= 1;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            showHelp = !showHelp;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            System.out.println("Saliendo");
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
        genW = width;
        genH = height;
        //tamaño inicial de la camara
        camera.viewportWidth = 100f;
        camera.viewportHeight = 100f * height/width;
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        generator.dispose();
        shapeRenderer.dispose();
    }

    void enviarDatos(int id,float posx,float posy,float posClickX,float posClicky){
        //modificamos añdimos los parametros y lo enviamos :)
        System.out.println("Enviando datos, ID: "+id);
        new Persona(id).getDataServer(id+","+wUS.get(id).type+","+posx+","+posy+","+wUS.get(id).life+","+wUS.get(id).mustDestroy+","+wUS.get(id).destroyed+","+wUS.get(id).jump+","+count+","+wUS.get(id).isFlaggedForDelete+","+posClickX+","+posClicky+","+wUS.get(id).typeArm+","+wUS.get(id).angleArm+","+wUS.get(id).forceArm,"setData");
    }
}
