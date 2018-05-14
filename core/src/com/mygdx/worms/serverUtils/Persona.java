package com.mygdx.worms.serverUtils;

import com.mygdx.worms.quailshillstudio.model.UserData;
import com.mygdx.worms.quailshillstudio.utils.ConfigGen;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Persona{
    protected Socket sk;
    protected DataOutputStream dos;
    protected DataInputStream dis;
    private int id;
    private String username = "getData";
    private String team = "none";
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
    public void setMesageScore(String username,String team) {
		this.username = username;
		this.team = team;
	}
	public HashMap<Integer,UserData> getDataServer(String dataExt){
        this.username = dataExt;
        return sendData();
    }
    private HashMap<Integer,UserData> sendData() {
        try {
            //enviamos los datos
        	sk = new Socket(IP, ConfigGen.PORT);
            dos = new DataOutputStream(sk.getOutputStream());
            System.out.println(id + " envia datos "+username+team);
            dos.writeUTF(username+","+team);

            //recibimos los datos
            InputStream is = sk.getInputStream();
            //recogemos los datos, y lo transformamos en un objeto de tipo Hashmap
            //para luego añadirlo a los datos.
            //ObjectInputStream ois = new ObjectInputStream(is);

            dis = new DataInputStream(sk.getInputStream());
            byte[] respuesta = new byte[dis.available()];
            dis.readFully(respuesta);
            //String respuesta;

            final ObjectInputStream oos = new ObjectInputStream(dis);

            //respuesta = dis.readUTF();
            System.out.println(id + " Servidor devuelve: " + respuesta.length+" bytes");

            //cerramos la conexion
            //dis.close();
            //dos.close();
            //sk.close();

            //retornamos una respuesta
            return (HashMap<Integer,UserData>) oos.readObject();
		} catch (Exception e) {
            System.out.println(e.getMessage());
			System.out.println("Error al enviar datos :(");
			return null;
		}
    }
}