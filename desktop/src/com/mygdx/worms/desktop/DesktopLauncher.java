package com.mygdx.worms.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.worms.quailshillstudio.MainGame;
import com.mygdx.worms.quailshillstudio.utils.ConfigGen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=ConfigGen.SCREEN_WIDTH;
		config.height=ConfigGen.SCREEN_HEIGHT;
		config.resizable = false;
		new LwjglApplication(new MainGame(), config);
	}
}
