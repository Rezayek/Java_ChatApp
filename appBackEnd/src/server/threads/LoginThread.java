package server.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import server.serverLocalDb.ServerDb;

public class LoginThread extends Thread  {
    Socket s;
    Map<String, String> loginData = new LinkedHashMap<String, String>();
    Map<String, String> loggedData = new LinkedHashMap<String, String>();
    ObjectInputStream inObj ;
    ObjectOutputStream outObj ;
    InputStream in;
    OutputStream out;
    ServerDb serverDb = ServerDb.getInstance();

    public LoginThread(Socket s){
        this.s = s;
    }

    public void run(){
        int id = -1;
        while (true){
            try {
                in = s.getInputStream();
                inObj = new ObjectInputStream(in);
                try {
                    loginData = (Map<String, String>) inObj.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(loginData.isEmpty() != true){

                    
                    Iterator<Map.Entry<String, String>> iterator = loginData.entrySet().iterator();
                    Map.Entry<String, String> actualValue = iterator.next();
                    Map.Entry<String, String> expectedValue = new AbstractMap.SimpleEntry<String, String>("event", "login");

                    if(expectedValue.equals(actualValue)){

                        id = serverDb.loginUser(loginData.get("email"), loginData.get("password"));
                    }


                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            
            try {
                out = s.getOutputStream();
                outObj = new ObjectOutputStream(out);
                if(id != -1){
                    loggedData.put("id", String.valueOf(id) );
                    loggedData.put("logged", "true" );
                    outObj.writeObject(loggedData);
                    outObj.flush();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
