package server.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import server.serverLocalDb.ServerDb;
import server.serverLocalDb.dataModels.UserModel;

public class SignUpThread extends Thread {
    Socket s;
    Map<String, String> signUpData = new LinkedHashMap<String, String>();
    ObjectInputStream inObj ;
    InputStream in ;
    ServerDb serverDb = ServerDb.getInstance();
    public SignUpThread(Socket s){
        this.s = s;
    }
    public void run(){
        while (true){
            try {
                in = s.getInputStream();
                inObj = new ObjectInputStream(in);
                try {
                    signUpData = (Map<String, String>) inObj.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(signUpData.isEmpty() != true){

                    
                    Iterator<Map.Entry<String, String>> iterator = signUpData.entrySet().iterator();
                    Map.Entry<String, String> actualValue = iterator.next();
                    Map.Entry<String, String> expectedValue = new AbstractMap.SimpleEntry<String, String>("event", "signup");

                    if(expectedValue.equals(actualValue)){
                        UserModel user = new UserModel(00, null, null, null);
                        
                        user.setUserName(signUpData.get("name"));
                        user.setUserEmail(signUpData.get("email"));
                        user.setUserPassword(signUpData.get("password"));

                        serverDb.InsertUser(user);
                              
                    }


                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


