package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import server.threads.builds.InBuild;


public class Server {
    
    

    public static void main(String[] args) {
        int nbClient = 0;
        try {
            try (ServerSocket server = new ServerSocket(9000)) {
                while (nbClient < 3 ){
                    
                    Socket ClientCon = server.accept();
                    
                    new InBuild(ClientCon).start();
                    nbClient++;

                }
            }
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }

    
    
}
