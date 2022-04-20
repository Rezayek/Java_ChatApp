package server.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import server.serverLocalDb.ServerDb;

public class SetMsgThread {
    Socket s;
    Map<String, String> msgData = new LinkedHashMap<String, String>();
    ObjectInputStream inObj ;
    InputStream in ;
    ServerDb serverDb = ServerDb.getInstance();


    public SetMsgThread(Socket s){
        this.s = s;
    }

    public void run() {

        while (true){
            try {
                in = s.getInputStream();
                inObj = new ObjectInputStream(in);
                try {
                    msgData = (Map<String, String>) inObj.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(msgData.isEmpty() != true){

                    
                    Iterator<Map.Entry<String, String>> iterator = msgData.entrySet().iterator();
                    Map.Entry<String, String> actualValue = iterator.next();
                    Map.Entry<String, String> expectedValue = new AbstractMap.SimpleEntry<String, String>("event", "setmsg");
                    if(expectedValue.equals(actualValue)){
                        serverDb.insertMsg(Integer.parseInt(msgData.get("send")), Integer.parseInt(msgData.get("to")), msgData.get("text"));
                    }
                
                }


            } catch (IOException e) {  
                e.printStackTrace();
            }
        }
    }
}
