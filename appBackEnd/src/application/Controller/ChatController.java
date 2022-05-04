package application.Controller;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.models.FriendModel;
import application.subView.friendView.FriendList;
import client.Client;
import client.clientLocalDb.ClientDb;
import client.clientLocalDb.clientModels.MsgDataModel;
import client.threads.GetFriendsClientThread;
import client.threads.SendFriendClientThread;
import client.threads.SendMsgClientThread;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatController implements Initializable {

    @FXML
    private ImageView addBtn;

    @FXML
    private ImageView chatBtn;

    @FXML
    private Pane conversation;

    @FXML
    private TextField friendField;

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
    @FXML
    private TableView<FriendModel> friendList ;
    @FXML
    private TableColumn<FriendModel, String> friends ;
    
    public static Map<String, String> genericFriends;
    private List<String> friendsValue;
    private List<FriendModel> friendsListModels;
    private List<String> friendsId;
    ObservableList<FriendModel> friendsName ;
    

    ClientDb client = ClientDb.getInstance();
    Client clientS = Client.getInstance();

    @Override
	public void initialize(URL location, ResourceBundle resources) {
        GetFriendsClientThread tFriend = new GetFriendsClientThread(client.getId(), clientS.getSocket());
        tFriend.start();
        try {
            tFriend.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        genericFriends = GetFriendsClientThread.genericFriendMap;
        friendsValue = new ArrayList<String>(genericFriends.values());
        friendsId = new ArrayList<String>(genericFriends.keySet());
        friendsListModels = new ArrayList<FriendModel>();
        
        for(int i = 1;i < friendsValue.size(); i++){
            //System.out.println(friendsValue.get(i));
            friendsListModels.add( new FriendModel(friendsValue.get(i)) );
            
            
        } 
        for(int i = 0;i < friendsListModels.size(); i++){
            System.out.println(friendsListModels.get(i).getName());
        }
        
        
        friendsName = FXCollections.observableArrayList(friendsListModels);
        friends.setCellValueFactory(new PropertyValueFactory<FriendModel, String>("name"));
        friendList.setItems(friendsName);
        friendList.getColumns().add(friends);
        friendList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("selected");
            }
        });


        

        

        
		
	}
    
    @FXML
    void addFriendAction(MouseEvent event) {
        SendFriendClientThread sendThread = new SendFriendClientThread(friendField.getText(), client.getId());
        sendThread.start();
        try {
            sendThread.join();
        } catch (InterruptedException e) {
           
            e.printStackTrace();
        }
        GetFriendsClientThread tFriend = new GetFriendsClientThread(client.getId(), clientS.getSocket());
        tFriend.start();
        try {
            tFriend.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        genericFriends = GetFriendsClientThread.genericFriendMap;
        friendsValue = new ArrayList<String>(genericFriends.values());
        friendsId = new ArrayList<String>(genericFriends.keySet());
        friendsListModels = new ArrayList<FriendModel>();
        
        for(int i = 1;i < friendsValue.size(); i++){
            friendsListModels.add( new FriendModel(friendsValue.get(i)) );
            
            
        }         
        friendsName = FXCollections.observableArrayList(friendsListModels);
        //friends.setCellValueFactory(new PropertyValueFactory<FriendModel, String>("name"));
        friendList.setItems(friendsName);
        //friendList.getColumns().add(friends);
        friendList.refresh();

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
