package client.threads.ClientBuild;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

import client.threads.GetFriendsClientThread;
import client.threads.GetMsgsClientThread;
import server.threads.AddFriendThread;
import server.threads.GetFriendsThread;
import server.threads.GetMsgThread;
import server.threads.LoginThread;
import server.threads.SetMsgThread;
import server.threads.SignUpThread;

public class InBuildClient extends Thread {
    
    
    private Socket s;
    ObjectInputStream inObj ;
    ObjectOutputStream outObj ;
    InputStream in;
    OutputStream out;
    int userId;
    int secondUserId;
    private Map<String, String> defaultMap = new LinkedHashMap<String, String>();

    public InBuildClient(Socket s, Map<String, String> defaultMap, int userId, int secondUserId) {
        this.s = s;
        this.defaultMap = defaultMap;
        this.userId = userId;
        this.secondUserId = secondUserId;
        
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
            //in main app we can use genericmaps to get access to the given maps
            new GetFriendsClientThread(userId, defaultMap);
            new GetMsgsClientThread(userId, secondUserId, defaultMap);
            
            
            
        }
    }

    public Map<String, String> getMap(){
        return defaultMap;
    }
}
