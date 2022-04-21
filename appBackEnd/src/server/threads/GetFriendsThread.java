package server.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import server.serverLocalDb.ServerDb;
import server.serverLocalDb.dataModels.UserModel;
import server.threads.builds.InBuild;
import server.threads.builds.OutBuild;

public class GetFriendsThread extends Thread  {
    Socket s;
    Map<String, String> requesteData = new LinkedHashMap<String, String>();
    Map<String, String> friendsData = new LinkedHashMap<String, String>();
    ServerDb serverDb = ServerDb.getInstance();

    public GetFriendsThread(Map<String, String>  requesteData, Socket s ){
        this.s = s;
        this.requesteData = requesteData;
    }

    public void run(){
        while(true){
            String key = "user";
            List<UserModel> usersList = new ArrayList<UserModel>();
            if(requesteData.isEmpty() != true){

                
                Iterator<Map.Entry<String, String>> iterator = requesteData.entrySet().iterator();
                Map.Entry<String, String> actualValue = iterator.next();
                Map.Entry<String, String> expectedValue = new AbstractMap.SimpleEntry<String, String>("event", "getfriends");

                if(expectedValue.equals(actualValue)){

                    usersList = serverDb.getFriends(Integer.parseInt(requesteData.get("id")));
                    if(usersList != null){
                        for(int i = 0; i < usersList.size(); i++){
                            friendsData.put(key + String.valueOf(i), String.valueOf(usersList.get(i)));
                        }
                        new OutBuild(friendsData, s).start();
                        
                    }
                    
                }


            }
        }
        

    }
}
