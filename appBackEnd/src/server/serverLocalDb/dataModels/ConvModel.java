package server.serverLocalDb.dataModels;

public class ConvModel {
    private int convId;
    private int firstUserId;
    private int secondUserId;

    public ConvModel(int convId, int firstUserId, int secondUserId){
        this.convId = convId;
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
    }

    public int getConvId(){
        return convId;
    }
    public void setConvId(int convId){
        this.convId = convId;
    }

    public int getFirstUserId(){
        return firstUserId;
    }
    public void setFirstUserId(int firstUserId){
        this.firstUserId = firstUserId;
    }

    public int getSecondUserId(){
        return secondUserId;
    }
    public void setSecondUserId(int secondUserId){
        this.secondUserId = secondUserId;
    }



}    
