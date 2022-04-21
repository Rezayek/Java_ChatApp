package client.threads;

import java.util.LinkedHashMap;
import java.util.Map;

import client.Client;
import client.clientLocalDb.clientModels.MsgDataModel;
import server.threads.builds.OutBuild;

public class SendMsgClientThread extends Thread {
    private MsgDataModel msg;
    private int sendedToId;
    Map<String, String> msgData = new LinkedHashMap<String, String>();
    Client client = Client.getInstance();

    public SendMsgClientThread(MsgDataModel msg, int sendedToId){
        this.msg = msg;
        this.sendedToId = sendedToId;
    }

    public void run(){
        msgData.put("event", "setmsg");
        msgData.put("send", String.valueOf(msg.getSenderId()));
        msgData.put("to", String.valueOf(sendedToId));
        msgData.put("text", msg.getText());
        new OutBuild(msgData, client.getSocket());

    }
    
}
