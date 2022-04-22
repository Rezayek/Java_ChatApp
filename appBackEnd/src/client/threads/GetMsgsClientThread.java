package client.threads;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


import client.Client;
import client.threads.ClientBuild.genericMaps.GenericMsgsMap;
import server.threads.builds.OutBuild;

public class GetMsgsClientThread extends Thread {
    Map<String, String> requesteData = new LinkedHashMap<String, String>();
    Map<String, String> resultsData = new LinkedHashMap<String, String>();
    GenericMsgsMap msgs = GenericMsgsMap.getInstance();
    Client client = Client.getInstance();
    private int userId;
    private int secondUserId;
    public GetMsgsClientThread(int userId, int secondUserId, Map<String, String> resultsData){
        this.userId = userId;
        this.secondUserId = secondUserId;
        this.resultsData = resultsData;
    }

    
    public void run(){

        requesteData.put("event", "msgs");
        requesteData.put("firstuser",String.valueOf(userId));
        requesteData.put("seconduser",String.valueOf(secondUserId));
        new OutBuild(requesteData, client.getSocket()).start();


        if(resultsData.isEmpty() != true){

            Iterator<Map.Entry<String, String>> iterator = resultsData.entrySet().iterator();
            Map.Entry<String, String> actualValue = iterator.next();
            Map.Entry<String, String> expectedValue = new AbstractMap.SimpleEntry<String, String>("result", "msgs");

            if(expectedValue.equals(actualValue)){

                msgs.setGenericMap(resultsData);
                
            }


        }

    }
}
