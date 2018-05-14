package com.mygdx.worms.serverUtils;

import com.mygdx.worms.quailshillstudio.model.UserData;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.logging.*;

public class ServidorHilo extends Thread {
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private int idSessio;
    HashMap<Integer, String> scores = new HashMap<Integer, String>();
    HashMap<Integer, UserData> playersTemp = new HashMap<Integer, UserData>();

    public HashMap<Integer, String> getScores() {
		return scores;
	}
    
    public ServidorHilo(Socket socket, int id) {
        this.socket = socket;
        this.idSessio = id;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void desconnectar() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run() {
        String accion;
        try {
            accion = dis.readUTF();
            //falta editar un poco la direccion del usuario.
            System.out.println(accion+" - "+socket.getInetAddress().toString()+" - "+socket.getRemoteSocketAddress().toString());
            if (accion.contains("getData")){
                playersTemp = Servidor.getPlayers();
                System.out.println("El cliente con idSesion "+this.idSessio+" pidio la lista completa...");
                dos.writeUTF("Conteo de players: "+playersTemp.size());
            }else if(accion.isEmpty()){
                System.out.println("El cliente con idSesion "+this.idSessio+" no tiene datos :(");
                dos.writeUTF("Datos vacios :( ");
            }else {
                dos.writeUTF("Tus datos quedaron regitrado!");
                String[] tokens = accion.split(",");
				scores.put(this.idSessio, tokens[1]);
                System.out.println("El cliente con username "+tokens[1]+" obtuvo: "+tokens[0]+", ID: "+this.idSessio);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconnectar();
    }
}