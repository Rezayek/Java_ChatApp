package client.clientLocalDb.clientModels;

public class FriendDataModel {
    private int friendId;
    //private String friendName;

    public FriendDataModel(int friendId,String friendName){
        this.friendId  = friendId;
        //this.friendName = friendName;
    }

    public int getFriendId(){
        return friendId;
    }

    public void setFriendId(int friendId){
        this.friendId = friendId;
    }

    //public String getFriendName(){
    //    return friendName;
    //}

    //public void setFriendName(String friendName){
    //    this.friendName = friendName;
    //}
}
