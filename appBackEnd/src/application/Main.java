package application;
	
import java.sql.SQLException;

import client.clientLocalDb.ClientDb;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	ClientDb client  = ClientDb.getInstance(); 
	@Override
	public void start(Stage primaryStage) throws SQLException {
		if(client.isLogged()){
			System.out.println(client.isLogged());
			try {
				Parent root=FXMLLoader.load(getClass().getResource("View/chatLayout.fxml"));
				primaryStage.setTitle("Messenger");
				primaryStage.setScene(new Scene(root));
				primaryStage.setResizable(false);
	
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}

		}else{
			try {
				Parent root=FXMLLoader.load(getClass().getResource("View/loginLayout.fxml"));
				primaryStage.setTitle("Messenger");
				primaryStage.setScene(new Scene(root));
				primaryStage.setResizable(false);
	
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}

		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}




