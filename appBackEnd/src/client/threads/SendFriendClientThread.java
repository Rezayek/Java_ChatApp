package client.threads;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import client.Client;
import server.threads.builds.OutBuild;

public class SendFriendClientThread extends Thread {
    
    private String friendName;
    private int userId;
    Map<String, String> friendData = new LinkedHashMap<String, String>();
    Client client = Client.getInstance();


    public SendFriendClientThread(String  friendName, int userId){
        this.friendName = friendName;
        this.userId = userId;
    }

    public void run() {
        friendData.put("event", "addfriend");
        friendData.put("name", friendName);
        friendData.put("id", String.valueOf(userId));
        System.out.println(friendData.get("name"));
        new OutBuild(friendData, client.getSocket()).start();
    }

}
