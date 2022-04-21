package server.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import server.serverLocalDb.ServerDb;

public class AddFriendThread extends Thread  {
    Socket s;
    Map<String, String> friendData = new LinkedHashMap<String, String>();
    ObjectInputStream inObj ;
    InputStream in;
    ServerDb serverDb = ServerDb.getInstance();

    public AddFriendThread(Socket s){
        this.s = s;
    }


    public void run(){
        int id ;
        while (true){
            try {
                in = s.getInputStream();
                inObj = new ObjectInputStream(in);
                try {
                    friendData = (Map<String, String>) inObj.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(friendData.isEmpty() != true){

                    
                    Iterator<Map.Entry<String, String>> iterator = friendData.entrySet().iterator();
                    Map.Entry<String, String> actualValue = iterator.next();
                    Map.Entry<String, String> expectedValue = new AbstractMap.SimpleEntry<String, String>("event", "addfriend");

                    if(expectedValue.equals(actualValue)){
                        id = serverDb.checkUser(friendData.get("name"));
                        
                        if(id != -1){
                            serverDb.createConv(id, Integer.parseInt(friendData.get("id")));
                        }
                    }


                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
