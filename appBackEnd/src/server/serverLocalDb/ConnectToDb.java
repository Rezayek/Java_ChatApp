package server.serverLocalDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDb {

    
    public static Connection con = null;
    private String url ="jdbc:mysql://localhost:3306/serverDb";
    private String user = "root";
    private String driver ="com.mysql.jdbc.Driver";

    public ConnectToDb(){
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e1) {
            
            e1.printStackTrace();
            
        }
        try {
            
            con = DriverManager.getConnection(url, user, "");
            if(con != null){
                
            }else if (con == null){
                
            }
            
            
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        
        
    }

}
    

