package com.mygdx.worms.serverUtils;

import com.mygdx.worms.quailshillstudio.model.UserData;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends Thread {
    private int idSessio;
    boolean start=true;

    public static Persona persona;
    //creando el cliente



    static HashMap<Integer, UserData> playersTemp = new HashMap<Integer, UserData>();

    public Cliente(){
        persona = new Persona(1);
        System.out.println("Iniciando Cliente...");
    }

    @Override
    public void run() {
            refresh();
    }

    public static void refresh(){
        HashMap<Integer, UserData> temp = persona.getDataServer("getData", "-");
        if (temp != null) playersTemp.putAll(temp);
    }
    public static HashMap<Integer, UserData> getPlayersTemp() {
        return playersTemp;
    }

    public static void setPlayersTemp(HashMap<Integer, UserData> playersTemp) {
        Cliente.playersTemp = playersTemp;
    }

}