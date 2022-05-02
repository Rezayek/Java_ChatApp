package client.clientLocalDb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import client.clientLocalDb.clientModels.FriendDataModel;


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
        if(checkUser(userId)){
            
            try {
                statement = ConnectToDbClient.con.prepareStatement("update login set isLogged = ? where userId  = ?");
                statement.setBoolean(1, true);
                statement.setInt(2, userId);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else{
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
        

    }


    //return true if there is user in login state
    public Boolean isLogged() throws SQLException{
        ResultSet res = null;
        try {
            statement = ConnectToDbClient.con.prepareStatement("Select userId from login where isLogged = ?");
            statement.setBoolean(1, true);
            res = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        if(res.next()){
            return true;
        }else{
            return false;
        }
    }

    public void logOut(int userId){
        try {
            System.out.println("log out");
            statement = ConnectToDbClient.con.prepareStatement("update login set isLogged = ? where userId  = ?");
            statement.setBoolean(1, false);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Boolean checkUser(int userId){
        ResultSet res = null;
        try {
            statement = ConnectToDbClient.con.prepareStatement("Select userId from login where userId = ? ");
            statement.setInt(1, userId);
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

    //get the logged user id
    public int getId(){
        int id = -1;
        ResultSet res = null;
        try {
            statement = ConnectToDbClient.con.prepareStatement("Select userId from login where isLogged = ? ");
            statement.setBoolean(1, true);
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
