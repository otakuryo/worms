package com.mygdx.worms.quailshillstudio.utils;


import com.mygdx.worms.quailshillstudio.AdapterScreen.AbstractScreen;
import com.mygdx.worms.quailshillstudio.screens.GameScreen;
import com.mygdx.worms.quailshillstudio.screens.LevelSelectScreen;
import com.mygdx.worms.quailshillstudio.screens.MenuScreen;
import com.mygdx.worms.quailshillstudio.screens.WorldScreen;

public enum ScreenEnum {
	
	MAIN_MENU {
		public AbstractScreen getScreen(Object... params) {
			return new MenuScreen();
		}
	},
	
	LEVEL_SELECT {
		public AbstractScreen getScreen(Object... params) {
			return new LevelSelectScreen();
		}
	},
	
	GAME {
		public AbstractScreen getScreen(Object... params) {
			//return new GameScreen((Integer) params[0]);
			return new WorldScreen();
		}
	};
	
	public abstract AbstractScreen getScreen(Object... params);
}
