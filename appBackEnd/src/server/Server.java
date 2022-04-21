package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.threads.AddFriendThread;
import server.threads.GetFriendsThread;
import server.threads.GetMsgThread;
import server.threads.LoginThread;
import server.threads.SetMsgThread;
import server.threads.SignUpThread;

public class Server {
    

    public static void main(String[] args) {
        int nbClient = 0;
        try {
            ServerSocket server = new ServerSocket(900);
            while (nbClient < 3 ){
                //Listen to incomming requests
                Socket ClientCon = server.accept();
                new LoginThread(ClientCon).start();
                new SignUpThread(ClientCon).start();
                new AddFriendThread(ClientCon).start();
                new GetFriendsThread(ClientCon).start();
                new SetMsgThread(ClientCon).start();
                new GetMsgThread(ClientCon).start();

            }
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }

    
    
}
