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
			return new SelectScreen();
		}
	},

	SERVER {
		public AbstractScreen getScreen(Object... params) {
			//return new GameScreen((Integer) params[0]);
			//System.out.println(String.valueOf(params[0])); //username
			//System.out.println(String.valueOf(params[1])); //server ip
			//System.out.println(String.valueOf(params[2])); //team
			//System.out.println(String.valueOf(params[3])); //admin?
			return new ServerScreen((Integer)params[3],String.valueOf(params[1]));
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
