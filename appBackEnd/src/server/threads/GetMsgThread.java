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
import server.serverLocalDb.dataModels.MsgModel;

public class GetMsgThread {
    Socket s;
    Map<String, String> requesteData = new LinkedHashMap<String, String>();
    Map<String, String> msgsData = new LinkedHashMap<String, String>();
    ObjectInputStream inObj ;
    ObjectOutputStream outObj ;
    InputStream in;
    OutputStream out;
    ServerDb serverDb = ServerDb.getInstance();

    public GetMsgThread(Socket s){
        this.s = s;
    }

    public void run(){

        while (true){
            List<MsgModel> msgsList = new ArrayList<MsgModel>();
            try {
                in = s.getInputStream();
                inObj = new ObjectInputStream(in);
                try {
                    requesteData = (Map<String, String>) inObj.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if(requesteData.isEmpty() != true){
                    Iterator<Map.Entry<String, String>> iterator = requesteData.entrySet().iterator();
                    Map.Entry<String, String> actualValue = iterator.next();
                    Map.Entry<String, String> expectedValue = new AbstractMap.SimpleEntry<String, String>("event", "msgs");

                    if(expectedValue.equals(actualValue)){
                        msgsList = serverDb.getMsgs(Integer.parseInt(requesteData.get("firstuser")), Integer.parseInt(requesteData.get("seconduser")));

                        if(msgsList != null){
                            for(int i = 0 ; i < msgsList.size(); i++){
                                msgsData.put(String.valueOf(msgsList.get(i).getSenderId()), msgsList.get(i).gettext());
                            }
                            out = s.getOutputStream();
                            outObj = new ObjectOutputStream(out);
                            outObj.writeObject(msgsData);
                            outObj.flush();

                        }

                    
                    }
                }

                

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
