package com.mygdx.worms.quailshillstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.worms.quailshillstudio.AdapterScreen.AbstractScreen;
import com.mygdx.worms.quailshillstudio.model.UserData;
import com.mygdx.worms.quailshillstudio.utils.ConfigGen;
import com.mygdx.worms.quailshillstudio.utils.ScreenEnum;
import com.mygdx.worms.quailshillstudio.utils.ScreenManager;
import com.mygdx.worms.quailshillstudio.utils.UIFactory;
import com.mygdx.worms.serverUtils.Cliente;
import com.mygdx.worms.serverUtils.Persona;
import com.mygdx.worms.serverUtils.Servidor;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

public class ServerScreen extends AbstractScreen {

	//private Texture txtrBg;
    private Table table,tableNet;
    private Skin uiSkin;
    private int admin;
    private String username;

    private boolean started = false;

    //creando el cliente
    //public Persona persona = new Persona(1);

	public ServerScreen(String username,int admin) {
		super();
		this.admin=admin;
		this.username=username;
		//txtrBg   = new Texture( Gdx.files.internal("img/level_select_bg.png") );
	}

	@Override
	public void buildStage() {
	    //skin de los botones
        uiSkin = new Skin(Gdx.files.internal(ConfigGen.fileSkin));

        final HashMap<Integer,UserData> testData = new HashMap<Integer, UserData>();
        testData.put(0,new UserData(UserData.WORM,"SERVER","Cargando","Cargando",100));


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

        //updateTable(conexiones);
        updateTableB(testData);

        crear.setPosition(getWidth()-60,40,Align.center);
        if (admin==1) addActor(crear);

        volver.setPosition(80,40,Align.center);
        addActor(volver);

        volver.addListener( UIFactory.createListener( ScreenEnum.SELECT ) );
        if (admin==1) crear.addListener(UIFactory.createListener(ScreenEnum.GAME, true,username,admin,Servidor.getPlayers()));
	}
	private void infoNetwork(){
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

    private void updateTableB(final HashMap<Integer,UserData> player){
	    //ID: pair.getKey(), Valor: pair.getValue()

        table.clearChildren();
        //player.clear();
        for (Object o : player.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            UserData ud = (UserData) pair.getValue();

            table.add(new Label(pair.getKey()+" - "+ud.getUsername() + " - " + ud.getTeam()+" - "+ud.getUserIP()+" - "+ud.comenzar, uiSkin));
            table.row().spaceTop(10);
            //it.remove(); // avoids a ConcurrentModificationException
        }
        addActor(table);
        if (player != null && player.get(0).comenzar.contains("comenzarpartida")) {
            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, false, username, admin, Cliente.getPlayersTemp());
        }

    }



    private int sec;
    @Override
    public void render(float delta) {
        super.render(delta);
        sec++;
        //if (sec%10==0){
            //getAndUpdateData();
            if (admin==1){
                //System.out.println("s: Actualizando base de datos");
                updateTableB(Servidor.getPlayers());
            }else {
                //System.out.println("c: Actualizando base de datos");
                Cliente.refresh();
                HashMap<Integer, UserData> temp = Cliente.getPlayersTemp();
                //HashMap<Integer, UserData> temp = persona.getDataServer("getData", "-");
                if (temp != null) updateTableB(temp);
            }
        //}
    }
}
