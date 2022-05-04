package client.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;




import server.threads.builds.OutBuild;

public class GetFriendsClientThread extends Thread {
    public static Map<String, String> genericFriendMap  = new LinkedHashMap<String, String>();

    Map<String, String> friendsData = new LinkedHashMap<String, String>();
    Map<String, String> resultsData = new LinkedHashMap<String, String>();
    private int userId;
    ObjectInputStream inObj ;
    InputStream in;
    Socket s;




    public GetFriendsClientThread(int userId, Socket s ){
        this.userId = userId;
        this.s = s;
    }

    

    
    public void run(){
        
            
            friendsData.put("event", "getfriends");
            friendsData.put("id",String.valueOf(userId)); 
            new OutBuild(friendsData, s).start();
            try {

                in = s.getInputStream();
                inObj = new ObjectInputStream(in);
                resultsData = (Map<String, String>) inObj.readObject();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }  
            if(resultsData.isEmpty() != true){

                Iterator<Map.Entry<String, String>> iterator = resultsData.entrySet().iterator();
                Map.Entry<String, String> actualValue = iterator.next();
                Map.Entry<String, String> expectedValue = new AbstractMap.SimpleEntry<String, String>("result", "getfriends");

                if(expectedValue.equals(actualValue)){
                    genericFriendMap = resultsData;
                }


            }

        
        }

        
}
