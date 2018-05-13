package com.mygdx.worms.quailshillstudio.utils;


import com.mygdx.worms.quailshillstudio.AdapterScreen.AbstractScreen;
import com.mygdx.worms.quailshillstudio.screens.SelectScreen;
import com.mygdx.worms.quailshillstudio.screens.MenuScreen;
import com.mygdx.worms.quailshillstudio.screens.ServerScreen;
import com.mygdx.worms.quailshillstudio.screens.WorldScreen;

public enum ScreenEnum {
	
	MAIN_MENU {
		public AbstractScreen getScreen(Object... params) {
			return new ServerScreen();
		}
	},
	
	SELECT {
		public AbstractScreen getScreen(Object... params) {
			return new SelectScreen();
		}
	},

	SERVER {
		public AbstractScreen getScreen(Object... params) {
			//return new GameScreen((Integer) params[0]);
			System.out.println(String.valueOf(params[0]));
			System.out.println(String.valueOf(params[1]));
			System.out.println(String.valueOf(params[2]));
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
