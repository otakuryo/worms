package com.mygdx.worms.quailshillstudio.utils;


import com.mygdx.worms.quailshillstudio.AdapterScreen.AbstractScreen;
import com.mygdx.worms.quailshillstudio.screens.SelectScreen;
import com.mygdx.worms.quailshillstudio.screens.MenuScreen;
import com.mygdx.worms.quailshillstudio.screens.ServerScreen;
import com.mygdx.worms.quailshillstudio.screens.WorldScreen;

public enum ScreenEnum {
	
	MAIN_MENU {
		public AbstractScreen getScreen(Object... params) {
			return new MenuScreen();
		}
	},
	
	SELECT {
		public AbstractScreen getScreen(Object... params) {
			return new SelectScreen((Integer) params[0]);
		}
	},

	SERVER {
		public AbstractScreen getScreen(Object... params) {
			//return new GameScreen((Integer) params[0]);
			return new ServerScreen();
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
