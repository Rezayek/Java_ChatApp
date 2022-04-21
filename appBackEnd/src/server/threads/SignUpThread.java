package server.threads;


import java.net.Socket;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import server.serverLocalDb.ServerDb;
import server.serverLocalDb.dataModels.UserModel;

public class SignUpThread extends Thread {
    Socket s;
    Map<String, String> signUpData = new LinkedHashMap<String, String>();
    ServerDb serverDb = ServerDb.getInstance();
    public SignUpThread(Map<String, String> signUpData){
        this.signUpData = signUpData;
        
    }
    public void run(){
        
            
            if(signUpData.isEmpty() != true){

                Iterator<Map.Entry<String, String>> iterator = signUpData.entrySet().iterator();
                Map.Entry<String, String> actualValue = iterator.next();
                Map.Entry<String, String> expectedValue = new AbstractMap.SimpleEntry<String, String>("event", "signup");

                if(expectedValue.equals(actualValue)){
                    UserModel user = new UserModel(00, null, null, null);
                    
                    user.setUserName(signUpData.get("name"));
                    user.setUserEmail(signUpData.get("email"));
                    user.setUserPassword(signUpData.get("password"));
                    System.out.println("i am here signing" + signUpData.get("password"));
                    serverDb.InsertUser(user);
                            
                }


            }
        
            
        
    }
}


