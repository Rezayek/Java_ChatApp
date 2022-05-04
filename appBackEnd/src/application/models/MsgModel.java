package application.models;

public class MsgModel {

    private String senderName;
    private String senderMsg;

    public MsgModel(String senderName, String senderMsg){
        this.senderName = senderName;
        this.senderMsg = senderMsg;
    }
    
    public String getSenderName(){
        return senderName;
    }
    public void setSenderName(String senderName){
        this.senderName =  senderName;
    }
    public String getSenderMsg(){
        return senderMsg;
    }
    public void setSenderMsg(String senderMsg){
        this.senderMsg  = senderMsg;
    }
}
