package com.mygdx.worms.quailshillstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.mygdx.worms.quailshillstudio.AdapterScreen.AbstractScreen;
import com.mygdx.worms.quailshillstudio.utils.ConfigGen;
import com.mygdx.worms.quailshillstudio.utils.ScreenEnum;
import com.mygdx.worms.quailshillstudio.utils.UIFactory;
import javafx.scene.control.SkinBase;

import java.util.ArrayList;

public class ServerScreen extends AbstractScreen {

	//private Texture txtrBg;
    private Table table;
    private Skin uiSkin;

	public ServerScreen() {
		super();
		//txtrBg   = new Texture( Gdx.files.internal("img/level_select_bg.png") );
	}

	@Override
	public void buildStage() {

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


		// Adding actors
		//Image bg = new Image(txtrBg);
		//addActor(bg);

        uiSkin = new Skin(Gdx.files.internal(ConfigGen.fileSkin));

        final Label conectados = new Label("Conectados",uiSkin);
        conectados.setPosition(getWidth() /2,650,Align.center);
        addActor(conectados);

        table = new Table();
        table.setPosition(getWidth() / 2, getHeight()/2+100,Align.center);

        updateTable(conexiones);

        /*
		ImageButton btnBack = UIFactory.createButton(txtrBack);
		btnBack.setPosition(getWidth() - 60f, 40f, Align.center);
		addActor(btnBack);

		ImageButton btnLevel1 = UIFactory.createButton(txtrLevel1);
		btnLevel1.setPosition(getWidth()/2 - 60f, getHeight()/2, Align.center);
		addActor(btnLevel1);

		ImageButton btnLevel2 = UIFactory.createButton(txtrLevel2);
		btnLevel2.setPosition(getWidth()/2 + 60f, getHeight()/2, Align.center);
		addActor(btnLevel2);
		
		btnBack.addListener( UIFactory.createListener( ScreenEnum.MAIN_MENU ) );
		btnLevel1.addListener( UIFactory.createListener(ScreenEnum.GAME, 1) );
		btnLevel2.addListener( UIFactory.createListener(ScreenEnum.GAME, 2) );
		*/
	}

	void updateTable(ArrayList<String> conexiones){
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
