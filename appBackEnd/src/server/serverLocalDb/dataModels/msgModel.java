package server.serverLocalDb.dataModels;

public class MsgModel {
    private int msgId;
    private int convId;
    private int senderId;
    private String text;

    public MsgModel(int msgId, int convId, int senderId, String text){
        this.msgId = msgId;
        this.convId = convId;
        this.senderId = senderId;
        this.text = text;
    }

    public int getMsgId(){
        return msgId;
    }
    public void setMsgId(int msgId){
        this.msgId = msgId;
    }

    public int getConvId(){
        return convId;
    }
    public void setConvId(int convId){
        this.convId = convId;
    }

    public int getSenderId(){
        return senderId;
    }
    public void setSenderId(int senderId){
        this.senderId = senderId;
    }

    public String gettext(){
        return text;
    }
    public void setText(String text){
        this.text = text;
    }
    
}

