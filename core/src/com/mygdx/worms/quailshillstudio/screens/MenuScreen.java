package com.mygdx.worms.quailshillstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.mygdx.worms.quailshillstudio.AdapterScreen.AbstractScreen;
import com.mygdx.worms.quailshillstudio.utils.ConfigGen;
import com.mygdx.worms.quailshillstudio.utils.ScreenEnum;
import com.mygdx.worms.quailshillstudio.utils.UIFactory;

public class MenuScreen extends AbstractScreen {

    //private Texture txtrBg;
    //private Texture txtrPlay;
    //private Texture txtrExit;

    public MenuScreen() {
        super();
        //txtrBg   = new Texture( Gdx.files.internal("img/main_menu_bg.png") );
        //txtrPlay = new Texture( Gdx.files.internal("img/btn_play.png") );
        //txtrExit = new Texture( Gdx.files.internal("img/btn_exit.png") );
    }

    @Override
    public void buildStage() {

        Skin uiSkin = new Skin(Gdx.files.internal(ConfigGen.fileSkin));

        // Adding actors
        //Image bg = new Image(txtrBg);
        //addActor(bg);
        ImageTextButton btnStart = new ImageTextButton("Start", uiSkin);
        btnStart.setPosition(getWidth() / 2-100, getHeight()/2 +100);
        btnStart.setSize(200,80);
        addActor(btnStart);

        ImageTextButton btnExit = new ImageTextButton("Exit", uiSkin);
        btnExit.setPosition(getWidth() / 2 -100, getHeight()/2);
        btnExit.setSize(200,80);
        addActor(btnExit);


        //ImageButton btnPlay = UIFactory.createButton(txtrPlay);
        //btnPlay.setPosition(getWidth() / 2, getHeight()/2 + 120.f, Align.center);
        //addActor(btnPlay);

        //ImageButton btnExit = UIFactory.createButton(txtrExit);
        //btnExit.setPosition(getWidth() / 2, getHeight()/2 + 60.f, Align.center);
        //addActor(btnExit);

        // Setting listeners
        btnStart.addListener( UIFactory.createListener(ScreenEnum.SELECT) );

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
        //txtrBg.dispose();
        //txtrPlay.dispose();
        //btnExit.dispose();
    }
}