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
    HashMap<Integer, UserData> playersTemp = new HashMap<Integer, UserData>();

    
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
            System.out.println("s:"+accion+" - "+socket.getInetAddress().toString());
            if(accion.isEmpty()){
                System.out.println("S: El cliente con idSesion "+this.idSessio+" no tiene datos :(");
                dos.writeUTF("S: Datos vacios :( ");
            }else if (accion.contains("getData")){
                sendListPlayers();
            }else if (accion.contains("setData")){
                updateListPlayers(accion);
            }else{
                addPlayer(accion);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconnectar();
    }
    private void addPlayer(String accion) throws IOException {
        dos.writeUTF("S: Tus datos quedaron regitrado!");
        String[] tokens = accion.split(",");
        System.out.println("S: El cliente con username "+tokens[0]+" team: "+tokens[1]+", ID: "+this.idSessio);
        Servidor.addUserToPlayers(this.idSessio,new UserData(UserData.WORM,tokens[0],socket.getInetAddress().toString(),tokens[1],this.idSessio));
    }
    private void sendListPlayers() throws IOException {

        // Construimos el DatagramPacket para enviar la respuesta
        //primero convertimos el array en un objeto y luego en bytes
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(2000);
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(Servidor.getPlayers());
        final byte[] dataStr = baos.toByteArray();
        //DatagramPacket respuesta = new DatagramPacket(dataStr, dataStr.length, socket.getInetAddress(), socket.getPort());
        System.out.println("S: El cliente con idSesion "+this.idSessio+" pidio la lista completa... "+dataStr.length);

        dos.write(dataStr);

        //playersTemp = Servidor.getPlayers();
        //dos.writeUTF("Conteo de players: "+playersTemp.size());
    }
    private void updateListPlayers(String accion) throws IOException {
        //14 campos
        //id+username+type+"posx"+"posy"+"life"+mustDestroy+destroyed+jump+count+isFlaggedForDelete+"posClickX"+"posClicky"+typeArm;
        //idE,usernameE,typeE,posxE,posyE,lifeE,mustDestroyE,destroyedE,jumpE,countE,isFlaggedForDeleteE,posClickXE,posclickYE,typeArmE

        dos.writeUTF("S: Los datos fueron modificados!");
        String[] tokens = accion.split(",");
        System.out.println("S: El cliente con username "+tokens[0]+" team: "+tokens[1]+", ID: "+this.idSessio+" total: "+tokens.length);
        Servidor.modPlayers(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]),Float.parseFloat(tokens[2]),Float.parseFloat(tokens[3]),Integer.parseInt(tokens[4]),Boolean.parseBoolean(tokens[5]),Boolean.parseBoolean(tokens[6]),
                Boolean.parseBoolean(tokens[7]),Integer.parseInt(tokens[8]),Boolean.parseBoolean(tokens[9]),Float.parseFloat(tokens[10]),Float.parseFloat(tokens[11]),Integer.parseInt(tokens[12]),Float.parseFloat(tokens[12]),Float.parseFloat(tokens[13]));
    }
}