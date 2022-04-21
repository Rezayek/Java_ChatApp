package client.clientLocalDb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectToDbClient {

    public static Connection con = null;
    private String url ="jdbc:mysql://localhost:3306/";
    private String user = "root";
    private String driver ="com.mysql.jdbc.Driver";

    public ConnectToDbClient(){
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
