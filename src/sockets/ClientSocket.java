package sockets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import clases.Message;
import exceptions.NotConnectedServer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author somor
 */
public class ClientSocket {
    private static final Logger logMsg = Logger.getLogger("");
    static final String HOST = ResourceBundle.getBundle("clases.connection").getString("host");
    static final Integer PUERTO = Integer.parseInt(ResourceBundle.getBundle("clases.connection").getString("puerto"));
    //private persona per = null;
    private Message mensajeSalida;

    public ClientSocket(Message mensaje) throws ConnectException{
        Socket skCliente = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        this.mensajeSalida = null;
        try {
//We make the connection
            skCliente = new Socket(HOST, PUERTO);
            System.out.println("Escucho el puerto " + PUERTO);
//Initialize the oos and ois for your use 
            oos = new ObjectOutputStream(skCliente.getOutputStream());
            ois = new ObjectInputStream(skCliente.getInputStream());

//send to the server the message
            oos.writeObject(mensaje);
            //Read Server Message
            this.mensajeSalida = (Message) ois.readObject();
            
        }catch(ConnectException ex){
             logMsg.log(Level.INFO, "login incorrecto ");
        
            } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        
            }catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                //Close the Stream and the connection
                oos.close();
                ois.close();
                skCliente.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    }

    public Message vueltaMensaje() throws ConnectException{
        //Returns message Output
        return this.mensajeSalida;

    }

}
