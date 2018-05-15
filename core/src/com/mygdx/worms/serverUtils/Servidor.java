package com.mygdx.worms.serverUtils;

import com.mygdx.worms.quailshillstudio.model.UserData;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.logging.*;
public class Servidor {
	static int PORT=10578;
	private static HashMap<Integer,UserData> players = new HashMap<Integer, UserData>();

    public Servidor() {

    }

    public static void main(String args[]) throws IOException {
        iniciarServer();
    }

    public static void iniciarServer(){
        //players.put(2,new UserData(UserData.WORM,"user","123","Team"));
        ServerSocket ss;
        System.out.print("S: Inicializando servidor... ");
        try {
            ss = new ServerSocket(PORT);
            System.out.println("\t[OK]");
            int idSession = 0;
            while (true) {
                Socket socket;
                socket = ss.accept();
                System.out.println("S: Nueva conexion entrante: "+socket);

                //si el datagrama es solo un texto, leemos los datos y enviamos la informacio solicitada
                //caso contrario creamos un servidorhilo
                (new ServidorHilo(socket, idSession)).start();
                idSession++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static HashMap<Integer, UserData> getPlayers() {
        return players;
    }

    //public void setPlayers(HashMap<Integer, UserData> players) {this.players = players;}


    static void addUserToPlayers(int id, UserData usuario){
        players.put(id,usuario);
    }

    static public void setStartGame(){
        UserData usTemp = players.get(0);
        usTemp.setStart();
        //usTemp.comenzar = "comenzarpartida";
        //players.put(0,usTemp);
        //players.get(0).comenzar = "comenzarpartida";
    }

    /*
    public static void capturePack(Socket socket) throws IOException, ClassNotFoundException {

        byte[] buffer = new byte[200];
        // Construimos el DatagramPacket para recibir peticiones
        DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);

        // Leemos una petición del DatagramSocket
        socket.receive(peticion);

        //transformamos los datos en texto y le quitamos los espacios inecesarios :)
        String tex = new String(peticion.getData()).trim();
        if (tex.equals("retrive")) {
            // Construimos el DatagramPacket para enviar la respuesta
            //primero convertimos el array en un objeto y luego en bytes
            final ByteArrayOutputStream baos = new ByteArrayOutputStream(2000);
            final ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(scoreStr);
            final byte[] dataStr = baos.toByteArray();
            DatagramPacket respuesta = new DatagramPacket(dataStr, dataStr.length, peticion.getAddress(), peticion.getPort());

            // Enviamos la respuesta con los datos
            socketUDP.send(respuesta);
            System.out.println("Enviando: "+scoreStr.size());
            scoreStr.clear();
        }else {
            //recogemos los datos, y lo transformamos en un objeto de tipo ScorePlayer
            //para luego añadirlo a los datos.
            final ByteArrayInputStream baos = new ByteArrayInputStream(peticion.getData());
            final ObjectInputStream oos = new ObjectInputStream(baos);
            scorePlayer = (ScorePlayer) oos.readObject();
            scoreStr.add(scorePlayer);

            //mostramos los datos por pantalla para verificar que los datos llegaron correctamente
            System.out.print("Score: " +scorePlayer.getScore());
            System.out.println(" de " + scorePlayer.getName());
            //formatText(new String(peticion.getData()));
        }
    }
    */
}