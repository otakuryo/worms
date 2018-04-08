package com.mygdx.worms.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.worms.Main;
import com.mygdx.worms.quailshillstudio.articlepolygonclipping.PolygonClipping;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=1024;
		config.height=720;
		new LwjglApplication(new PolygonClipping(), config);
        //new LwjglApplication(new OrthographicCameraExample());
	}
}
