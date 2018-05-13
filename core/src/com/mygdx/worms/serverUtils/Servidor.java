package com.mygdx.worms.serverUtils;

import java.io.*;
import java.net.*;
import java.util.logging.*;
public class Servidor {
	static int PORT=10578;

    public Servidor() {
    }

    public static void main(String args[]) throws IOException {
        iniciarServer();
    }

    public static void iniciarServer(){
        ServerSocket ss;
        System.out.print("Inicializando servidor... ");
        try {
            ss = new ServerSocket(PORT);
            System.out.println("\t[OK]");
            int idSession = 0;
            while (true) {
                Socket socket;
                socket = ss.accept();
                System.out.println("Nueva conexion entrante: "+socket);
                (new ServidorHilo(socket, idSession)).start();
                idSession++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}