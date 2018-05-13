package com.mygdx.worms.serverUtils;

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
        String accion = "";
        try {
            accion = dis.readUTF();
            if(accion.isEmpty()){
                System.out.println("El cliente con idSesion "+this.idSessio+" no tiene datos :(");
                dos.writeUTF("Datos vacios :(");
            }else {
                dos.writeUTF("Tu escore quedo regitrado!");
                String[] tokens = accion.split(",");
				scores.put(Integer.parseInt(tokens[0]), tokens[1]);
                System.out.println("El cliente con idSesion "+tokens[1]+" obtuvo: "+tokens[0]+"$");
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconnectar();
    }
}