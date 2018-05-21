package com.mygdx.worms.quailshillstudio.utils;


import com.mygdx.worms.quailshillstudio.AdapterScreen.AbstractScreen;
import com.mygdx.worms.quailshillstudio.screens.SelectScreen;
import com.mygdx.worms.quailshillstudio.screens.MenuScreen;
import com.mygdx.worms.quailshillstudio.screens.ServerScreen;
import com.mygdx.worms.quailshillstudio.screens.WorldScreen;
import com.mygdx.worms.serverUtils.Persona;
import com.mygdx.worms.serverUtils.Servidor;

public enum ScreenEnum {
	
	MAIN_MENU {
		public AbstractScreen getScreen(Object... params) {
			return new WorldScreen("ariel");
		}
	},
	
	SELECT {
		public AbstractScreen getScreen(Object... params) {
			return new SelectScreen();
		}
	},

	SERVER {
		public AbstractScreen getScreen(Object... params) {
			//System.out.println(String.valueOf(params[0])); //username
			//System.out.println(String.valueOf(params[1])); //server ip
			//System.out.println(String.valueOf(params[2])); //team
			//System.out.println(String.valueOf(params[3])); //admin?
			return new ServerScreen((Integer)params[3]);
		}
	},

	GAME {
		public AbstractScreen getScreen(Object... params) {
			if ((Boolean) params[0]){
                Servidor.setStartGame();
            }
			return new WorldScreen("ariel");
		}
	};
	
	public abstract AbstractScreen getScreen(Object... params);
}
