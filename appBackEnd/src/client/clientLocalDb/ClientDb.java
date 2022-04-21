package client.clientLocalDb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import client.clientLocalDb.clientModels.FriendDataModel;
import server.serverLocalDb.ConnectToDb;

public class ClientDb {
    ConnectToDbClient dbCon = new ConnectToDbClient();
    PreparedStatement statement ;

    //singleton--------------------------------------
    private static ClientDb instance;

    private ClientDb(){

    }

    public static ClientDb getInstance(){
        if(instance == null){
            instance = new ClientDb();
        }
        return instance;
    }
    //--------------------------------------------------



    public void setLoggedUser(int userId , boolean isLogged){
        String req = "insert into login(userId, isLogged) values(?, ?)";

        try {
            System.out.println("adding");
            statement = ConnectToDbClient.con.prepareStatement(req);
            statement.setInt(1, userId);
            statement.setBoolean(2, isLogged);
            statement.executeUpdate();
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }

    }


    public Boolean isLogged(){
        ResultSet res = null;
        try {
            statement = ConnectToDbClient.con.prepareStatement("Select userId from login where isLogged = "+ "true");
            res = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        if(res != null){
            return true;
        }else{
            return false;
        }
    }
    public int getId(){
        int id = -1;
        ResultSet res = null;
        try {
            statement = ConnectToDbClient.con.prepareStatement("Select userId from login where isLogged = "+ "true");
            res = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        if(res != null){
            try {
                while(res.next()){
                    id = res.getInt("userId");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return id;
        }else{
            return id;
        }
    }


    public void addFriend(List<FriendDataModel> friendList){
        String req = "insert into Friends values(?)";

        for(int i =0; i< friendList.size(); i++){
            ResultSet res = null;
            try {
                statement = ConnectToDbClient.con.prepareStatement("Select friendId from Friends where friendId = "+ friendList.get(i).getFriendId());
                res = statement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            } 
            if(res == null){
                try {
                    statement = ConnectToDbClient.con.prepareStatement(req);
                    //statement.setInt(1, user.getUserId());
                    statement.setInt(1, friendList.get(i).getFriendId());         
        
                    statement.executeUpdate();
                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                }
                
            }
        }

    }


}
