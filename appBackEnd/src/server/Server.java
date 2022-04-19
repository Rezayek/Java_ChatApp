package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    

    public static void main(String[] args) {
        int nbClient = 0;
        try {
            ServerSocket server = new ServerSocket(900);
            while (nbClient < 3 ){
                //Listen to incomming requests
                Socket ClientCon = server.accept();
            }
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }

    
    
}
