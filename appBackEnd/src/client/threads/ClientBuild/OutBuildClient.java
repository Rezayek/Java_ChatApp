package client.threads.ClientBuild;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;


public class OutBuildClient extends Thread{


    private Socket s;
    ObjectOutputStream outObj ;
    OutputStream out;
    private Map<String, String> givenMap;




    public OutBuildClient(Map<String, String> givenMap, Socket s ) {
        this.s = s;
        this.givenMap = givenMap;
         
    }


    public void run() {
        try {
            System.out.println(givenMap.get("name"));
            out = s.getOutputStream();
            outObj = new ObjectOutputStream(out);
            outObj.writeObject(givenMap);
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        

    }

    





    
    
}
