package client.threads;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

import client.clientLocalDb.ClientDb;
import client.clientLocalDb.clientModels.UserDataModel;

public class SignUpClientThread extends Thread {
    Socket s;
    Map<String, String> signUpData = new LinkedHashMap<String, String>();
    ObjectOutputStream outObj ;
    OutputStream out;
    ClientDb client = ClientDb.getInstance();
    private UserDataModel user;

    

    public SignUpClientThread(Socket s, UserDataModel user){
        this.s = s;
        this.user = user;
    }


    public void run(){

        try {
            signUpData.put("event", "signup");
            signUpData.put("name", user.getUserName());
            signUpData.put("email", user.getUserEmail());
            signUpData.put("password", user.getUserPassword());
            out = s.getOutputStream();
            outObj = new ObjectOutputStream(out);
            outObj.writeObject(signUpData);
            




        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
