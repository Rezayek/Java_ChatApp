package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

import server.threads.AddFriendThread;
import server.threads.GetFriendsThread;
import server.threads.GetMsgThread;
import server.threads.builds.InBuild;
import server.threads.builds.OutBuild;


public class Server {
    
    

    public static void main(String[] args) {
        int nbClient = 0;
        Map<String, String> defaultmap = new LinkedHashMap<String, String>();
        try {
            try (ServerSocket server = new ServerSocket(9000)) {
                while (nbClient < 3 ){
                    //Listen to incomming requests
                    System.out.println("conected");
                    Socket ClientCon = server.accept();
                    
                    new InBuild(ClientCon).start();
                    

                }
            }
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }

    
    
}
