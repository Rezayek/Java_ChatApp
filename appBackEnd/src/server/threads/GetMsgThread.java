package server.threads;

import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import server.serverLocalDb.ServerDb;
import server.serverLocalDb.dataModels.MsgModel;
import server.threads.builds.OutBuild;

public class GetMsgThread extends Thread  {
    Socket s;
    Map<String, String> requesteData;
    Map<String, String> msgsData = new LinkedHashMap<String, String>();
    ServerDb serverDb = ServerDb.getInstance();

    public GetMsgThread(Map<String, String> requesteData, Socket s){
        this.s = s;
        this.requesteData = requesteData;
    }

    public void run(){

        
            List<MsgModel> msgsList = new ArrayList<MsgModel>();


                if(requesteData.isEmpty() != true){
                    Iterator<Map.Entry<String, String>> iterator = requesteData.entrySet().iterator();
                    Map.Entry<String, String> actualValue = iterator.next();
                    Map.Entry<String, String> expectedValue = new AbstractMap.SimpleEntry<String, String>("event", "msgs");

                    if(expectedValue.equals(actualValue)){
                        msgsList = serverDb.getMsgs(Integer.parseInt(requesteData.get("firstuser")), Integer.parseInt(requesteData.get("seconduser")));

                        if(msgsList.isEmpty() != true){
                            msgsData.put("result", "msgs");
                            for(int i = 0 ; i < msgsList.size(); i++){
                                msgsData.put(String.valueOf(msgsList.get(i).getSenderId())+"/"+i, msgsList.get(i).gettext());
                            }
                            new OutBuild(msgsData, s).start();
                            

                        }else{
                            new OutBuild(null, s).start();
                        }

                    
                    
                }

                

            }
        
        }

            
    }

