package server.threads.builds;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

import server.threads.AddFriendThread;
import server.threads.GetFriendsThread;
import server.threads.GetMsgThread;
import server.threads.LoginThread;
import server.threads.SetMsgThread;
import server.threads.SignUpThread;

public class InBuild extends Thread {
    
    
    private Socket s;
    ObjectInputStream inObj ;
    ObjectOutputStream outObj ;
    InputStream in;
    OutputStream out;
    private Map<String, String> defaultMap = new LinkedHashMap<String, String>();

    public InBuild(Socket s) {
        this.s = s;
        
    }

    

    public void run(){
        while(true){
            try {
                in = s.getInputStream();
                inObj = new ObjectInputStream(in);
                defaultMap = (Map<String, String>) inObj.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            new LoginThread(defaultMap, s).start();
            new SignUpThread(defaultMap).start();
            new SetMsgThread(defaultMap).start();
            new AddFriendThread(defaultMap).start();
            new GetFriendsThread(defaultMap, s).start();
            new GetMsgThread(defaultMap, s).start();
            
            
            
        }
    }

    public Map<String, String> getMap(){
        return defaultMap;
    }
}
