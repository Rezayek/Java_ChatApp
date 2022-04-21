package client.clientLocalDb.clientModels;

public class MsgDataModel {
    
    private int senderId;
    private String text;

    public MsgDataModel(int senderId, String text){
        this.senderId = senderId;
        this.text = text;
    }

    public int getSenderId(){
        return senderId;
    }

    public void setSenderId(int senderId){
        this.senderId = senderId;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }
}
