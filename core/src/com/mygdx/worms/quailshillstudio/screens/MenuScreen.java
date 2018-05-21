package com.mygdx.worms.quailshillstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.worms.quailshillstudio.AdapterScreen.AbstractScreen;
import com.mygdx.worms.quailshillstudio.utils.ConfigGen;
import com.mygdx.worms.quailshillstudio.utils.ScreenEnum;
import com.mygdx.worms.quailshillstudio.utils.UIFactory;

public class MenuScreen extends AbstractScreen {

    //private Texture txtrBg;

    public MenuScreen() {
        super();
        //txtrBg   = new Texture( Gdx.files.internal("img/main_menu_bg.png") );
    }

    @Override
    public void buildStage() {

        Skin uiSkin = new Skin(Gdx.files.internal(ConfigGen.fileSkin));

        // anyadimos el fondo
        //Image bg = new Image(txtrBg);
        //addActor(bg);
        ImageTextButton btnStart = new ImageTextButton("Start", uiSkin);
        btnStart.setPosition(getWidth() / 2-100, getHeight()/2 +200);
        btnStart.setSize(200,80);
        addActor(btnStart);

        ImageTextButton btnPractice = new ImageTextButton("Tutorial", uiSkin);
        btnStart.setPosition(getWidth() / 2-100, getHeight()/2 +100);
        btnStart.setSize(200,80);
        addActor(btnPractice);

        ImageTextButton btnExit = new ImageTextButton("Exit", uiSkin);
        btnExit.setPosition(getWidth() / 2 -100, getHeight()/2);
        btnExit.setSize(200,80);
        addActor(btnExit);

        btnStart.addListener( UIFactory.createListener(ScreenEnum.SELECT) );

        btnPractice.addListener( UIFactory.createListener(ScreenEnum.PRACTICE) );

        btnExit.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Gdx.app.exit();
                        return false;
                    }
                });
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}