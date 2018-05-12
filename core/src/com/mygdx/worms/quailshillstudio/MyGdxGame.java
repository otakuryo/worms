package com.mygdx.worms.quailshillstudio;

import com.badlogic.gdx.Game;
import com.mygdx.worms.quailshillstudio.utils.ScreenEnum;
import com.mygdx.worms.quailshillstudio.utils.ScreenManager;

public class MyGdxGame extends Game {
	
	@Override
	public void create () {
		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU );
	}
}
