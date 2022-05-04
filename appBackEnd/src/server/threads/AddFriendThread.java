package server.threads;


import java.net.Socket;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import server.serverLocalDb.ServerDb;
import server.threads.builds.InBuild;

public class AddFriendThread extends Thread  {
    Socket s;
    Map<String, String> friendData = new LinkedHashMap<String, String>();
    InBuild inBuild ;
    ServerDb serverDb = ServerDb.getInstance();

    public AddFriendThread(Map<String, String> friendData){
        this.friendData = friendData;
    }


    public void run(){
        int id ;
        
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

    }

