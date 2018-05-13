package com.mygdx.worms.serverUtils;

import java.io.*;
import java.net.Socket;
public class Persona{
    protected Socket sk;
    protected DataOutputStream dos;
    protected DataInputStream dis;
    private int id;
    String mesage;
    int score;
    String IP="127.0.0.1";
	static int PORT=10578;
    public Persona(int id) {
        this.id = id;
    }
    public void setMesageScore(String mesage,int score,String ipExt) {
		this.mesage = mesage;
		this.score=score;
		IP=ipExt;
		sendData();
	}
    public void setMesageScore(String mesage,int score) {
		this.mesage = mesage;
		this.score=score;
		sendData();
	}
    public void sendData() {
        try {
        	sk = new Socket(IP, PORT);
            dos = new DataOutputStream(sk.getOutputStream());
            dis = new DataInputStream(sk.getInputStream());
            System.out.println(id + " envia datos");
            dos.writeUTF(score+","+mesage);
            String respuesta="";
            respuesta = dis.readUTF();
            System.out.println(id + " Servidor devuelve: " + respuesta);
            dis.close();
            dos.close();
            sk.close();
		} catch (Exception e) {
			System.out.println("Error al enviar datos :(");
		}
    }
}