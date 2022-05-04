package server.threads;


import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import server.serverLocalDb.ServerDb;
import server.serverLocalDb.dataModels.UserModel;
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
        
            List<UserModel> usersList = new ArrayList<UserModel>();
            if(requesteData.isEmpty() != true){

                
                Iterator<Map.Entry<String, String>> iterator = requesteData.entrySet().iterator();
                Map.Entry<String, String> actualValue = iterator.next();
                Map.Entry<String, String> expectedValue = new AbstractMap.SimpleEntry<String, String>("event", "getfriends");
                
                if(expectedValue.equals(actualValue)){

                    usersList = serverDb.getFriends(Integer.parseInt(requesteData.get("id")));
                    
                    if(usersList.isEmpty() != true){
                        friendsData.put("result", "getfriends");
                        for(int i = 0; i < usersList.size(); i++){
                            UserModel userName = serverDb.getUser(usersList.get(i).getUserId());
                            friendsData.put(String.valueOf(userName.getUserId()),userName.getUserName());
                            
                        }
                        new OutBuild(friendsData, s).start();
                        
                    }
                    
                


            }
        
        

    }

        }
            
}
