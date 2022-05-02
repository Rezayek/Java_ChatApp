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

public class GetMsgsClientThread extends Thread {

    public static Map<String, String> genericMsgMap;
    public static int friendId;
    Map<String, String> requesteData = new LinkedHashMap<String, String>();
    Map<String, String> resultsData = new LinkedHashMap<String, String>();
    private int userId;
    private int secondUserId;
    ObjectInputStream inObj ;
    InputStream in;
    Socket s;

    public GetMsgsClientThread(int userId, int secondUserId, Socket s){
        this.userId = userId;
        this.secondUserId = secondUserId;
        this.s = s;
        GetMsgsClientThread.friendId = secondUserId;
    }
    public void run(){

        
            requesteData.put("event", "msgs");
            requesteData.put("firstuser",String.valueOf(userId));
            requesteData.put("seconduser",String.valueOf(secondUserId));
            new OutBuild(requesteData, s).start();
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
                Map.Entry<String, String> expectedValue = new AbstractMap.SimpleEntry<String, String>("result", "msgs");

                if(expectedValue.equals(actualValue)){

                    genericMsgMap = resultsData;
                    
                }


            }

        
        }

        
}
