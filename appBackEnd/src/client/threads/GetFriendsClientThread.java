package client.threads;

import java.net.Socket;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import client.Client;
import client.threads.ClientBuild.genericMaps.GenericFriendsMap;
import server.threads.builds.OutBuild;

public class GetFriendsClientThread extends Thread {
    Map<String, String> friendsData = new LinkedHashMap<String, String>();
    Map<String, String> resultsData = new LinkedHashMap<String, String>();
    GenericFriendsMap friends = GenericFriendsMap.getInstance();
    Client client = Client.getInstance();
    private int userId;

    public GetFriendsClientThread(int userId, Map<String, String> resultsData){
        this.userId = userId;
        this.resultsData = resultsData;
    }

    
    public void run(){

        friendsData.put("event", "getfriends");
        friendsData.put("id",String.valueOf(userId));
        new OutBuild(friendsData, client.getSocket());


        if(resultsData.isEmpty() != true){

            Iterator<Map.Entry<String, String>> iterator = resultsData.entrySet().iterator();
            Map.Entry<String, String> actualValue = iterator.next();
            Map.Entry<String, String> expectedValue = new AbstractMap.SimpleEntry<String, String>("result", "getfriends");

            if(expectedValue.equals(actualValue)){

                friends.setGenericMap(resultsData);
                
            }


        }

    }
}
