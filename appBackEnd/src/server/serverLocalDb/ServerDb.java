package server.serverLocalDb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.serverLocalDb.dataModels.MsgModel;
import server.serverLocalDb.dataModels.UserModel;

public class ServerDb {
    ConnectToDb dbCon = new ConnectToDb();
    PreparedStatement statement ;

    //singleton--------------------------------------
    private static ServerDb instance;

    private ServerDb(){

    }

    public static ServerDb getInstance(){
        if(instance == null){
            instance = new ServerDb();
        }
        return instance;
    }
    //--------------------------------------------------
    
    public void InsertUser(UserModel user){
        String req = "insert into userServer(userName, userEmail, userPassword) values(?, ?, ?)";

        try {
            statement = ConnectToDb.con.prepareStatement(req);
            //statement.setInt(1, user.getUserId());
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getUserEmail());
            statement.setString(3, user.getUserPassword());

            statement.executeUpdate();
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public UserModel getUser(int userId){
        UserModel user = null;
        try {
            statement = ConnectToDb.con.prepareStatement("Select userId from userServer where userId = ?");
            statement.setInt(1, userId);
            ResultSet res = statement.executeQuery();
            while (res.next()){
                user = new UserModel(
                    res.getInt("userId"), 
                    res.getString("userName"), 
                    res.getString("userEmail"), 
                    res.getString("userPassword")
                    );
                //user.setUserId(res.getInt("userId"));
                //user.settUserName(res.getString("userName"));
                //user.settUserEmail(res.getString("userEmail"));
                //user.settUserPassword(res.getString("userPassword"));
            }
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }

        return user;

    }

    public List<UserModel> getFriends(int userId){
        List<UserModel> usersList = new ArrayList<UserModel>();
        try {
            statement = ConnectToDb.con.prepareStatement("Select * from conversation where firstUserId = ? or secondUserId = ?");
            statement.setInt(1,userId);
            statement.setInt(2, userId);
            ResultSet res = statement.executeQuery();

            while (res.next()){
                if(res.getInt("firstUserId") == userId){
                    usersList.add(getUser(res.getInt("secondUserId")));
                }else{
                    usersList.add(getUser(res.getInt("firstUserId")));
                }

            }
        } catch (SQLException e) {
            
            e.printStackTrace();
        }

        return usersList;
    }

    public int checkUser(String name){
        ResultSet res = null;
        int id = -1;
        try {
            statement = ConnectToDb.con.prepareStatement("Select userId from userServer where userName = ?"); 
            statement.setString(1, name );
            res = statement.executeQuery();
            while(res.next()){
                id = res.getInt("userId");
            }
            
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
        if(res != null){
            return id;
        }else{
            return id;
        }
    }
    public Boolean checkUserEmail(String email) throws SQLException{
        ResultSet res = null;
        try {
            statement = ConnectToDb.con.prepareStatement("Select userEmail from userServer where userEmail = ?"); 
            statement.setString(1, email );
            res = statement.executeQuery();            
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
        if(res.isFirst()){
            return true;
        }else{
            return false;
        }
    }

    public int loginUser(String email, String password) throws SQLException{
        ResultSet res = null;
        try {
            statement = ConnectToDb.con.prepareStatement("Select userId from userServer where userEmail = ? and userPassword = ?"); 
            statement.setString(1, email);
            statement.setString(2, password);

            res = statement.executeQuery();
            
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
        if(res.isFirst()){
            int id =-1;
            try {
                while(res.next()){
                    id = res.getInt("userId");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return id;
        }else{
            return -1;
        }
    }


    public void createConv(int firstUserId, int secondUserId){
        String req = "insert into Conversation(firstUserId, secondUserId) values(?, ?)";
        try {
            statement = ConnectToDb.con.prepareStatement(req);
            statement.setInt(1, firstUserId);
            statement.setInt(2, secondUserId);

            statement.executeUpdate();

        } catch (SQLException e) {
            
            e.printStackTrace();
        }

    }

    public int getConvId(int firstUserId, int secondUserId){
        int convId = -1;
        try {
            statement = ConnectToDb.con.prepareStatement("Select ConvId from conversation where firstUserId = ? and secondUserId = ? or firstUserId = ? and secondUserId = ?");
            statement.setInt(1, firstUserId  );
            statement.setInt(2, secondUserId);
            statement.setInt(3, secondUserId);
            statement.setInt(4, firstUserId);
            ResultSet res = statement.executeQuery();
            while (res.next()){
                convId= res.getInt("ConvId");
            }

        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return convId;

    }
    

    public void insertMsg(int senderId, int secondUserId, String text){
        String req = "insert into messages(ConvId, senderId, msg) values(?,?,?)";

        int convId = getConvId(senderId, secondUserId);

        try {
            statement = ConnectToDb.con.prepareStatement(req);
            statement.setInt(1, convId);
            statement.setInt(2, senderId);
            statement.setString(3, text);

            statement.executeUpdate();

        } catch (SQLException e) {
            
            e.printStackTrace();
        }

    }

    public List<MsgModel> getMsgs(int firstUserId, int secondUserId){
        List<MsgModel> msgsList = new ArrayList<MsgModel>();
        int convId = getConvId(firstUserId, secondUserId);

        try {
            statement = ConnectToDb.con.prepareStatement("select * from messages where convId = ?");
            statement.setInt(1, convId);
            ResultSet res = statement.executeQuery();

            while (res.next()){
                MsgModel msg  = new MsgModel(
                    res.getInt("msgId"), 
                    res.getInt("ConvId"), 
                    res.getInt("senderId"), 
                    res.getString("msg")
                    );
                msgsList.add(msg);
            }

        } catch (SQLException e) {
            
            e.printStackTrace();
        }

        return msgsList;

    }

    





}
