package application.Controller;


import java.util.LinkedHashMap;
import java.util.Map;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import client.Client;
import client.clientLocalDb.ClientDb;
import client.clientLocalDb.clientModels.MsgDataModel;
import client.threads.SendFriendClientThread;
import client.threads.SendMsgClientThread;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatController {

    @FXML
    private ImageView addBtn;

    @FXML
    private ImageView chatBtn;

    @FXML
    private Pane conversation;

    @FXML
    private TextField friendField;

    @FXML
    private VBox friendsList;

    @FXML
    private TextField msgField;

    @FXML
    private ImageView profileBtn;

    @FXML
    private ImageView sendMsg;

    @FXML
    private ImageView settingsBtn;

    @FXML
    private ImageView signoutBtn;

    @FXML
    private ImageView usersBtn;
    @FXML
    private VBox converstionWidget;
    @FXML
    private ImageView logOutBtn;
    @FXML
    private Label defaultFriend;

    ClientDb client = ClientDb.getInstance();



    Client clientS = Client.getInstance();


	public void initialize(URL location, ResourceBundle resources) {
		//new FriendList(friendsList).start();
        //new MsgWidget(converstionWidget).start();
		
	}
    
    @FXML
    void addFriendAction(MouseEvent event) {
        new SendFriendClientThread(friendField.getText(), client.getId()).start();
    }

    @FXML public void logOut(InputEvent event) {
        client.logOut(client.getId());
        System.out.println(client.getId());
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        Parent root = null;
        try {
            root = FXMLLoader.load(this.getClass().getResource("../View/loginLayout.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    void sendMasgToFriend(MouseEvent event) {
        new SendMsgClientThread(new MsgDataModel(client.getId(), msgField.getText()), 2).start();
        msgField.setText("");


    }
    
    
    
    


}
