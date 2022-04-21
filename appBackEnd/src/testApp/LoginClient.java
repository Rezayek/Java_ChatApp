package testApp;

import client.Client;
import client.threads.LoginClientThread;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginClient extends Application implements EventHandler<ActionEvent>{

    

    Client client = Client.getInstance();
    @Override
    public void handle(ActionEvent arg0) {
       
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextField txtlogin= new TextField(); 
			TextField txtPassword= new TextField(); 
			GridPane root = new GridPane();
			primaryStage.setTitle("Login client"); 
			Label login= new Label("Login:");
			Label password = new Label("Password:");
			Label Noaccount =new Label("No Account ?");
			
			
			Button Confirm = new Button("Confirm");  
		    
		    Confirm.setOnAction(e->{
                System.out.println(txtlogin.getText());
		    		new LoginClientThread(client.getSocket(), txtlogin.getText(), txtPassword.getText()).start();
                    System.out.println("logged");
                    
		    });
		    
		    
		    
		    Button Quit = new Button("Quit");
		    Quit.setOnAction(e->{primaryStage.close();});
		    Button Subscribe = new Button("Subscribe"); 
		     Subscribe.setOnAction(e->{ new SubscribeClient();
		     primaryStage.close();});
		    
			root.add(login, 0, 0);
			root.add(password, 0,1 );
			root.add(Confirm, 0, 2);
			root.add(Quit, 1, 2);
			root.setHgap(10); 
			root.setVgap(10);
			
			root.add(txtlogin, 1, 0);
			root.add(txtPassword, 1, 1);
			root.add(Noaccount, 1, 3);
			root.add(Subscribe, 1, 4);
					
			
			Scene scene = new Scene(root,350,250);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
        
    }

    public static void main(String[] args) {
		launch(args);
	}
	
}
