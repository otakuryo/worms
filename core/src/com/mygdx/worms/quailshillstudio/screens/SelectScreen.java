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

public class SelectScreen extends AbstractScreen {


	public SelectScreen() {
		super();
	}

	@Override
	public void buildStage() {

		Skin uiSkin = new Skin(Gdx.files.internal(ConfigGen.fileSkin));

		final TextField txtusername= new TextField("Username",uiSkin);
		final TextField txtserver= new TextField("IP Server",uiSkin);

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

        unirse.addListener(new ClickListener() {
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                if (!txtusername.getText().isEmpty() && !txtserver.getText().isEmpty() && !txtusername.getText().contains("srname")){
                    ScreenManager.getInstance().showScreen(ScreenEnum.SERVER, txtusername.getText(),txtserver.getText(),team.getSelected());
                }else{
                    error.setVisible(true);
                }
                return false;
            }
        });

        crear.addListener(new ClickListener() {
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                if (!txtusername.getText().isEmpty() && !txtserver.getText().isEmpty() && !txtusername.getText().contains("sername")){
                    ScreenManager.getInstance().showScreen(ScreenEnum.SERVER, txtusername.getText(),txtserver.getText());
                }else{
                    error.setVisible(true);
                }
                return false;
            }
        });

		volver.addListener( UIFactory.createListener( ScreenEnum.MAIN_MENU ) );

	}
}
