package client;

import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket socket;
    private static Client instance;

    private Client(){
        try {
            socket = new Socket("127.0.0.1", 9000);
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }

    public static Client getInstance(){
        if(instance == null){
            instance = new Client();
        }
        return instance;
    }

    public Socket getSocket(){
        return socket;
    }
}
