package server.threads;


import java.net.Socket;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import server.serverLocalDb.ServerDb;
import server.threads.builds.OutBuild;

public class LoginThread extends Thread  {
    Socket s;
    Map<String, String> loginData ;
    Map<String, String> loggedData = new LinkedHashMap<String, String>() ;
    ServerDb serverDb = ServerDb.getInstance();

    

    public LoginThread(Map<String, String>  loginData, Socket s ){
        this.loginData = loginData;
        this.s = s;
        
    }

    public void run(){
        int id = -1;
        
        if(loginData.isEmpty() != true){

            Iterator<Map.Entry<String, String>> iterator = loginData.entrySet().iterator();
            Map.Entry<String, String> actualValue = iterator.next();
            Map.Entry<String, String> expectedValue = new AbstractMap.SimpleEntry<String, String>("event", "login");

            if(expectedValue.equals(actualValue)){

                try {
                    id = serverDb.loginUser(loginData.get("email"), loginData.get("password"));
                } catch (SQLException e) {
                    
                    e.printStackTrace();
                }
                
            }


        }
        System.out.println("id ? :"+id);
        
        if(id != -1){
            loggedData.put("event", "islogged");
            loggedData.put("id", String.valueOf(id) );
            loggedData.put("logged", "true" );
            System.out.println(loggedData.get("id"));
            
            new OutBuild(loggedData, s).start();

            
        }
        
    }
}
