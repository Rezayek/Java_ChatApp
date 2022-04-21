package server.threads;


import java.util.AbstractMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import server.serverLocalDb.ServerDb;
import server.threads.builds.InBuild;

public class SetMsgThread extends Thread  {
    Map<String, String> msgData = new LinkedHashMap<String, String>();
    InBuild inBuild ;
    ServerDb serverDb = ServerDb.getInstance();


    public SetMsgThread(Map<String, String> msgData){
        this.msgData = msgData;
    }

    public void run() {

        while (true){
                if(msgData.isEmpty() != true){

                    
                    Iterator<Map.Entry<String, String>> iterator = msgData.entrySet().iterator();
                    Map.Entry<String, String> actualValue = iterator.next();
                    Map.Entry<String, String> expectedValue = new AbstractMap.SimpleEntry<String, String>("event", "setmsg");
                    if(expectedValue.equals(actualValue)){
                        serverDb.insertMsg(Integer.parseInt(msgData.get("send")), Integer.parseInt(msgData.get("to")), msgData.get("text"));
                    }
                
                }


            }
        }
    }

