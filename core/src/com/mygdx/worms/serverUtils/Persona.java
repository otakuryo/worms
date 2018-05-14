package com.mygdx.worms.serverUtils;

import com.mygdx.worms.quailshillstudio.utils.ConfigGen;

import java.io.*;
import java.net.Socket;
public class Persona{
    protected Socket sk;
    protected DataOutputStream dos;
    protected DataInputStream dis;
    private int id;
    private String username;
    private String team;
    private String IP="127.0.0.1";
    public Persona(int id) {
        this.id = id;
    }

    public void setMesageScore(String username,String team,String ipExt) {
		this.username = username;
		this.team = team;
		IP=ipExt;
		sendData();
	}
	//cambiarlo por el paquete hashmap :)
    public String setMesageScore(String username,String team) {
		this.username = username;
		this.team = team;
		return sendData();
	}
    private String sendData() {
        try {
        	sk = new Socket(IP, ConfigGen.PORT);
            dos = new DataOutputStream(sk.getOutputStream());
            dis = new DataInputStream(sk.getInputStream());
            System.out.println(id + " envia datos");
            dos.writeUTF(username+","+team);
            String respuesta;
            respuesta = dis.readUTF();
            System.out.println(id + " Servidor devuelve: " + respuesta);
            dis.close();
            dos.close();
            sk.close();
            return respuesta;
		} catch (Exception e) {
			System.out.println("Error al enviar datos :(");
			return "error";
		}
    }
}