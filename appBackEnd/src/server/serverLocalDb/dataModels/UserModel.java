package server.serverLocalDb.dataModels;

public class UserModel {
    private int userId;
    private String userName;
    private String userEmail;
    private String userPassword;

    public UserModel(int userId, String userName, String userEmail, String userPassword){
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;

    }

    public int getUserId(){
        return userId;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }

    public String getUserName(){
        return userName;
    }
    public void settUserName(String userName){
        this.userName = userName;
    }

    public String getUserEmail(){
        return userEmail;
    }
    public void settUserEmail(String userEmail){
        this.userEmail = userEmail;
    }


    public String getUserPassword(){
        return userPassword;
    }
    public void settUserPassword(String userPassword){
        this.userPassword = userPassword;
    }

    

}
