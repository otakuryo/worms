package com.mygdx.worms.quailshillstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.worms.quailshillstudio.AdapterScreen.AbstractScreen;
import com.mygdx.worms.quailshillstudio.utils.ConfigGen;
import com.mygdx.worms.quailshillstudio.utils.ScreenEnum;
import com.mygdx.worms.quailshillstudio.utils.ScreenManager;
import com.mygdx.worms.quailshillstudio.utils.UIFactory;
import com.mygdx.worms.serverUtils.Cliente;
import com.mygdx.worms.serverUtils.Persona;
import com.mygdx.worms.serverUtils.Servidor;

public class SelectScreen extends AbstractScreen {

    //se crea los parametros del servidor
    Persona persona = new Persona(1);


	public SelectScreen() {
		super();
	}

	@Override
	public void buildStage() {

		Skin uiSkin = new Skin(Gdx.files.internal(ConfigGen.fileSkin));

		final TextField txtusername= new TextField("Username",uiSkin);
		final TextField txtserver= new TextField("127.0.0.1",uiSkin);

		ImageTextButton unirse = new ImageTextButton("Unirse", uiSkin);
		ImageTextButton crear = new ImageTextButton("Crear Server", uiSkin);
		ImageTextButton volver = new ImageTextButton("Volver", uiSkin);

        final SelectBox team = new SelectBox(uiSkin);
        team.setItems("Jordi Team","Fernando Team","Gerard Team");

        final Label error = new Label("Rellena todos los campos :(",uiSkin);
        error.setVisible(false);

		Table table = new Table();
		table.setPosition(getWidth() / 2, getHeight()/2 +50,Align.center);
		table.defaults().growX();

		table.add(txtusername);

		table.row().spaceTop(20);
		table.add(txtserver);

		table.row().spaceTop(20);
		table.add(team);

		table.row().spaceTop(20);
		table.add(unirse);

		table.row();
		table.add(crear);

		table.row().spaceTop(30);
		table.add(volver);

		table.row().spaceTop(100);
		table.add(error);

		addActor(table);

		//comprobamos que no hay campos vacios y nos unimos al servidor
        unirse.addListener(new ClickListener() {
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                if (!txtusername.getText().isEmpty() && !txtserver.getText().isEmpty() && !txtusername.getText().contains("srname")){
                    createClient();
                    persona.setNewPlayer(txtusername.getText(),team.getSelected().toString(),txtserver.getText());
                    //persona.sendData();
                    ScreenManager.getInstance().showScreen(ScreenEnum.SERVER, txtusername.getText(),txtserver.getText(),team.getSelected(),0);
                }else{
                    error.setVisible(true);
                }
                return false;
            }
        });

        //comprobamos que no hay campos vacios y creamos un servidor
        crear.addListener(new ClickListener() {
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                if (!txtusername.getText().isEmpty() && !txtserver.getText().isEmpty() && !txtusername.getText().contains("sername")){
                    createServer();
                    persona.setNewPlayer(txtusername.getText(),team.getSelected().toString(),txtserver.getText());
                    ScreenManager.getInstance().showScreen(ScreenEnum.SERVER, txtusername.getText(),txtserver.getText(),team.getSelected(),1);
                }else{
                    error.setVisible(true);
                }
                return false;
            }
        });

		volver.addListener( UIFactory.createListener( ScreenEnum.MAIN_MENU ) );

	}
	private void createServer(){
        Thread one = new Thread() {
            public void run() {
                Servidor.iniciarServer();
            }
        };

        one.start();
        //le decimos al hilo principal que duerma una fraccion de segundo, para darle tiempo que inicie el servidor
        try {
            Thread.sleep(100);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
    private void createClient(){
        Thread two = new Thread() {
            public void run() {
                new Cliente().run();
            }
        };

        two.start();
        //le decimos al hilo principal que duerma una fraccion de segundo, para darle tiempo que inicie el servidor
        try {
            Thread.sleep(100);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
}
