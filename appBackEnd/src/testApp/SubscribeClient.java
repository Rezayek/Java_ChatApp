package testApp;

import client.Client;
import client.clientLocalDb.clientModels.UserDataModel;
import client.threads.SignUpClientThread;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SubscribeClient extends Application {
    Client client = Client.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label title= new Label("Inscription Client");
		title.getStyleClass().add("title");

		Label idLabel = new Label("Id:                ");
		TextField idTextField = new TextField();
		HBox idBox = new HBox(idLabel,idTextField);
		idBox.setAlignment(Pos.CENTER);
		idBox.setSpacing(20);
		
		Label nomLabel = new Label("Nom:           ");
		TextField nomTextField = new TextField();
		HBox nomBox = new HBox(nomLabel,nomTextField);
		nomBox.setAlignment(Pos.CENTER);
		nomBox.setSpacing(20);
		
		Label prenomLabel = new Label("email:       ");
		TextField prenomTextField = new TextField();
		HBox prenomBox = new HBox(prenomLabel,prenomTextField);
		prenomBox.setAlignment(Pos.CENTER);
		prenomBox.setSpacing(20);
		
		Label telephoneLabel = new Label("Telephone:  ");
		TextField telephoneTextField = new TextField();
		HBox telephoneBox = new HBox(telephoneLabel,telephoneTextField);
		telephoneBox.setAlignment(Pos.CENTER);
		telephoneBox.setSpacing(20);
		
		Label cinLabel = new Label("Cin:");
		TextField cinTextField = new TextField();
		HBox cinBox = new HBox(cinLabel,cinTextField);
		cinBox.setAlignment(Pos.CENTER);
		cinBox.setSpacing(20);
		
		Button confirmButton = new Button ("Confirmer");
		confirmButton.setOnAction(e -> {
			new SignUpClientThread(client.getSocket(), new UserDataModel(nomTextField.getText(), prenomTextField.getText(), cinTextField.getText())).start();
			System.out.println("added");
		});
		
		
		Button returnButton = new Button ("Retourner");
		
		HBox bottomBox = new HBox(confirmButton,returnButton);
		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.setSpacing(20);
		

		VBox root = new VBox(title,idBox,nomBox,prenomBox,telephoneBox,cinBox,bottomBox);
		root.setSpacing(20);
		root.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root,300,350);
		primaryStage.setTitle("inscription Client");
		primaryStage.setScene(scene);
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
	
		//BUTTON ACTONS
		returnButton.setOnAction(e -> { 
			
		    primaryStage.close(); 
			//return to previous scene
			
		});
		primaryStage.show();
        
    }
    public static void main(String[] args) {
		launch(args);
	}
}
