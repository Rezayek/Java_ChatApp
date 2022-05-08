package client.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


import client.clientLocalDb.ClientDb;



public class LoginClientThread extends Thread {
    
    Socket s;
    Map<String, String> loginData = new LinkedHashMap<String, String>();
    Map<String, String> loggedData = new LinkedHashMap<String, String>();
    ObjectInputStream inObj ;
    ObjectOutputStream outObj ;
    InputStream in;
    OutputStream out;
    ClientDb client = ClientDb.getInstance();
    private String userEmail;
    private String userPassword;

    

    public LoginClientThread(Socket s, String userEmail, String userPassword){
        this.s = s;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public void run(){

        try {
            
            
            loginData.put("event", "login");
            loginData.put("email", userEmail);
            loginData.put("password", userPassword);
            out = s.getOutputStream();
            outObj = new ObjectOutputStream(out);
            outObj.writeObject(loginData);
            
            
        } catch (IOException e) {
            
            e.printStackTrace();
        }

        try {
            in = s.getInputStream();
            inObj = new ObjectInputStream(in);
            try {
                loggedData = (Map<String, String>) inObj.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(loggedData.isEmpty() != true){
                

                
                    
                Iterator<Map.Entry<String, String>> iterator = loggedData.entrySet().iterator();
                Map.Entry<String, String> actualValue = iterator.next();
                Map.Entry<String, String> expectedValue = new AbstractMap.SimpleEntry<String, String>("event", "islogged");

                if(expectedValue.equals(actualValue)){

                    client.setLoggedUser(Integer.parseInt(loggedData.get("id")), Boolean.parseBoolean(loggedData.get("logged")));
                    
                   
                }
            }
        } catch (IOException e1) {
            
            e1.printStackTrace();
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
           


            }

        

        
    }

