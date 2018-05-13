package com.mygdx.worms.quailshillstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.mygdx.worms.quailshillstudio.AdapterScreen.AbstractScreen;
import com.mygdx.worms.quailshillstudio.utils.ConfigGen;
import com.mygdx.worms.quailshillstudio.utils.ScreenEnum;
import com.mygdx.worms.quailshillstudio.utils.UIFactory;
import javafx.scene.control.SkinBase;

public class SelectScreen extends AbstractScreen {

	
	public SelectScreen(int type) {
		super();
		System.out.println(type);
	}

	@Override
	public void buildStage() {

		Skin uiSkin = new Skin(Gdx.files.internal(ConfigGen.fileSkin));

		Table table = new Table();
		table.setPosition(getWidth() / 2, getHeight()/2 +100,Align.center);
		table.defaults().growX();

		table.add(new TextField("Username",uiSkin));

		table.row().spaceTop(20);
		table.add(new TextField("IP SERVER",uiSkin));

		table.row().spaceTop(20);
		table.add(new ImageTextButton("Unirse", uiSkin));

		table.row();
		table.add(new ImageTextButton("Crear Server", uiSkin));

		table.row();
		table.add(new ImageTextButton("Salir", uiSkin));

		addActor(table);


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

		ImageTextButton btnStart = new ImageTextButton("Extra Enemies", uiSkin);
		btnStart.setPosition(200,200);
		btnStart.setSize(200,80);
		addActor(btnStart);

		TextField usernameTextField = new TextField("",uiSkin);
		usernameTextField.setPosition(24,73);
		usernameTextField.setSize(88, 14);
		addActor(usernameTextField);

		btnBack.addListener( UIFactory.createListener( ScreenEnum.MAIN_MENU ) );
		btnLevel1.addListener( UIFactory.createListener(ScreenEnum.SERVER, 1) );
		btnLevel2.addListener( UIFactory.createListener(ScreenEnum.SERVER, 2) );
		 */

	}

	@Override
	public void dispose() {
		super.dispose();
		//txtrBg.dispose();
		//txtrBack.dispose();
		//txtrLevel1.dispose();
		//txtrLevel2.dispose();
	}
}
