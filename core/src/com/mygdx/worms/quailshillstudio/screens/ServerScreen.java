package com.mygdx.worms.quailshillstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.worms.quailshillstudio.AdapterScreen.AbstractScreen;
import com.mygdx.worms.quailshillstudio.utils.ConfigGen;
import com.mygdx.worms.quailshillstudio.utils.ScreenEnum;
import com.mygdx.worms.quailshillstudio.utils.ScreenManager;
import com.mygdx.worms.quailshillstudio.utils.UIFactory;
import com.mygdx.worms.serverUtils.Persona;
import javafx.scene.control.SkinBase;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class ServerScreen extends AbstractScreen {

	//private Texture txtrBg;
    private Table table,tableNet;
    private Skin uiSkin;
    private int admin;

    //creando el cliente
    Persona persona = new Persona(1);

	public ServerScreen(int admin) {
		super();
		this.admin=admin;
		//txtrBg   = new Texture( Gdx.files.internal("img/level_select_bg.png") );
	}

	@Override
	public void buildStage() {
	    //skin de los botones
        uiSkin = new Skin(Gdx.files.internal(ConfigGen.fileSkin));

	    //datos de prueba
        final ArrayList<String> conexiones = new ArrayList<String>();
        conexiones.add("Agustin - Fernando Team");
        conexiones.add("Rio - Fernando Team");
        conexiones.add("Roger - Jordi Team");
        conexiones.add("Issam - Gerard Team");
        conexiones.add("Issam - Gerard Team");
        conexiones.add("Issam - Gerard Team");
        conexiones.add("Issam - Gerard Team");
        conexiones.add("Issam - Gerard Team");
        conexiones.add("Issam - Gerard Team");
        conexiones.add("Issam - Gerard Team");
        conexiones.add("Issam - Gerard Team");
        conexiones.add("Issam - Gerard Team");
        conexiones.add("Issam - Gerard Team");
        conexiones.add("Issam - Gerard Team");


		// Anyadimos la imagen de fondo
		//Image bg = new Image(txtrBg);
		//addActor(bg);

        //Comprobamos la red y escribimos un cuadro de informacion con las ips.
        infoNetwork();
        //fin de informacion

        //creando los datos principales
        final Label conectados = new Label("Sala de espera",uiSkin);

        ImageTextButton crear = new ImageTextButton("Iniciar", uiSkin);
        ImageTextButton volver = new ImageTextButton("Salir de la sala", uiSkin);

        conectados.setPosition(getWidth() /2,650,Align.center);
        addActor(conectados);

        table = new Table();
        table.setPosition(getWidth() / 2, getHeight()/2+50,Align.center);

        updateTable(conexiones);

        crear.setPosition(getWidth()-60,40,Align.center);
        if (admin==1) addActor(crear);

        volver.setPosition(80,40,Align.center);
        addActor(volver);

        volver.addListener( UIFactory.createListener( ScreenEnum.SELECT ) );
        if (admin==1) crear.addListener(UIFactory.createListener(ScreenEnum.GAME, 1));
	}
	void infoNetwork(){
	    //creamos la cabecera
        tableNet = new Table();
        tableNet.setPosition(80,getHeight()-70,Align.top);

        final Label network = new Label("Datos de ip",uiSkin);
        tableNet.add(network).align(Align.left).spaceBottom(10);
        tableNet.row();

        //buscamos y añadimos los datos
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface ni : Collections.list(interfaces)){
                for(InetAddress address : Collections.list(ni.getInetAddresses()))
                {
                    if(address instanceof Inet4Address){
                        tableNet.add(new Label("IP "+address.getHostAddress(),uiSkin)).align(Align.left);
                        tableNet.row();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        //añadimos a la pantalla
        addActor(tableNet);
    }

	private void updateTable(ArrayList<String> conexiones){
        table.clearChildren();
        for (String con : conexiones) {
            table.add(new Label(con, uiSkin));
            table.row().spaceTop(10);
        }
        addActor(table);
    }

	@Override
	public void dispose() {
		super.dispose();
		//txtrBg.dispose();
	}
}
