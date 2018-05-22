package com.mygdx.worms.quailshillstudio.utils;


import com.mygdx.worms.quailshillstudio.AdapterScreen.AbstractScreen;
import com.mygdx.worms.quailshillstudio.model.UserData;
import com.mygdx.worms.quailshillstudio.screens.*;
import com.mygdx.worms.serverUtils.Persona;
import com.mygdx.worms.serverUtils.Servidor;

import java.util.HashMap;

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
	PRACTICE {
		public AbstractScreen getScreen(Object... params) {
			return new WorldPracticeScreen("TU");
		}
	},

	SERVER {
		public AbstractScreen getScreen(Object... params) {
			//System.out.println(String.valueOf(params[0])); //username
			//System.out.println(String.valueOf(params[1])); //server ip
			//System.out.println(String.valueOf(params[2])); //team
			//System.out.println(String.valueOf(params[3])); //admin?
			return new ServerScreen(String.valueOf(params[0]),(Integer)params[3]);
		}
	},

	GAME {
		public AbstractScreen getScreen(Object... params) {
            HashMap <Integer,UserData> t = (HashMap <Integer,UserData>) params[3];
            if ((Boolean) params[0]){
                Servidor.setStartGame();
                System.out.println("ADMIN ++> "+t.size());
            }
            System.out.println("++> "+t.size());
			return new WorldScreen(String.valueOf(params[1]),(Integer)params[2],t);
		}
	};
	
	public abstract AbstractScreen getScreen(Object... params);
}
