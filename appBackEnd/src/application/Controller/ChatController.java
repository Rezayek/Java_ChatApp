package application.Controller;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.models.FriendModel;
import application.models.MsgModel;
import application.subView.friendView.FriendList;
import client.Client;
import client.clientLocalDb.ClientDb;
import client.clientLocalDb.clientModels.MsgDataModel;
import client.threads.GetFriendsClientThread;
import client.threads.GetMsgsClientThread;
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
    private Pane pane;

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
    @FXML
    private TableView<MsgModel> conversation  ;  
    @FXML
    private TableColumn<MsgModel, String> friendRep  ;  
    @FXML
    private TableColumn<MsgModel, String> myRep  ; 
   
    
    
    public static String currentFriend;
    public static int currentFriendId;
    public static Map<String, String> genericFriends;
    public static Map<String, String> genericMsgs =  new LinkedHashMap<String, String>();
    private List<String> friendsValue;
    private List<FriendModel> friendsListModels;
    private List<String> friendsId;
    ObservableList<FriendModel> friendsName ;
    ObservableList<MsgModel> ConversationMsgs ;
    private List<MsgModel> mgsModelList = new ArrayList<MsgModel>();

    

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
        friends.setCellValueFactory(new PropertyValueFactory<FriendModel, String>("name"));
        friendList.getColumns().add(friends);
        if(!(genericFriends == null || genericFriends.isEmpty())){
            for(int i = 1;i < friendsValue.size(); i++){
                friendsListModels.add( new FriendModel(friendsValue.get(i)) );   
            } 
            
            friendsName = FXCollections.observableArrayList(friendsListModels);
            
            friendList.setItems(friendsName);

        }else{
            friendsListModels = new ArrayList<FriendModel>();
            genericFriends = null;
            friendsName = FXCollections.observableArrayList(friendsListModels);
            friendList.setItems(friendsName);

        }
        
        
        

        friendRep.setCellValueFactory(new PropertyValueFactory<MsgModel, String>("senderName"));
        myRep.setCellValueFactory(new PropertyValueFactory<MsgModel, String>("senderMsg"));
        conversation.getColumns().addAll(friendRep, myRep);

        friendList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                conversation.getItems().clear();
                
                ChatController.currentFriend = friendsValue.get(friendList.getSelectionModel().getSelectedIndex()+1);
                ChatController.currentFriendId = Integer.parseInt(friendsId.get(friendList.getSelectionModel().getSelectedIndex()+1));
                System.out.println(ChatController.currentFriend);
                System.out.println(ChatController.currentFriendId);
                GetMsgsClientThread getMsgs = new GetMsgsClientThread(client.getId(), ChatController.currentFriendId , clientS.getSocket());
                getMsgs.start();
                try {
                    getMsgs.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               
                genericMsgs = GetMsgsClientThread.genericMsgMap;
                System.out.println(genericMsgs);
                if(!(genericMsgs == null || genericMsgs.isEmpty())){ 
                    mgsModelList = new ArrayList<MsgModel>();
                    for (String key : genericMsgs.keySet()) {
                        System.out.println(key);
                        if(key != "result"){
                            if(key.contains(String.valueOf(client.getId())+"/")){
                                mgsModelList.add(new MsgModel("you : ", genericMsgs.get(key)));
                            }else if(key.contains(String.valueOf(currentFriendId+"/"))){
                                mgsModelList.add(new MsgModel(ChatController.currentFriend, genericMsgs.get(key)));
                            }
                        }
                    }
                    ConversationMsgs = FXCollections.observableArrayList(mgsModelList);
                    
                    conversation.setItems(ConversationMsgs);
                    
                }
                else{
                    mgsModelList = new ArrayList<MsgModel>();
                    genericMsgs = null;
                    ConversationMsgs = FXCollections.observableArrayList(mgsModelList);
                    conversation.setItems(ConversationMsgs);
                }
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
        friendList.setItems(friendsName);
        friendList.refresh();

    }

    @FXML public void logOut(InputEvent event) {
        client.logOut(client.getId());
        
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
        conversation.getItems().clear();
        ChatController.currentFriend = friendsValue.get(friendList.getSelectionModel().getSelectedIndex()+1);
        ChatController.currentFriendId = Integer.parseInt(friendsId.get(friendList.getSelectionModel().getSelectedIndex()+1));
        SendMsgClientThread sendingMsg = new SendMsgClientThread(new MsgDataModel(client.getId(), msgField.getText()), ChatController.currentFriendId);
        msgField.setText("");
        sendingMsg.start();
        try {
            sendingMsg.join();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        
         GetMsgsClientThread getMsgs = new GetMsgsClientThread(client.getId(), Integer.parseInt(friendsId.get(friendList.getSelectionModel().getSelectedIndex()+1)), clientS.getSocket());
         getMsgs.start();
         try {
             getMsgs.join();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         genericMsgs = new LinkedHashMap<String, String>();
         genericMsgs = GetMsgsClientThread.genericMsgMap;
         if(!(genericMsgs == null || genericMsgs.isEmpty())){
             mgsModelList = new ArrayList<MsgModel>();
             for (String key : genericMsgs.keySet()) {
                 if(key != "result"){
                     if(key.contains(String.valueOf(client.getId())+"/")){
                         mgsModelList.add(new MsgModel("you : ", genericMsgs.get(key)));
                     }else if(key.contains(String.valueOf(currentFriendId+"/"))){
                         mgsModelList.add(new MsgModel(ChatController.currentFriend, genericMsgs.get(key)));
                     }
                 }
             }
             ConversationMsgs = FXCollections.observableArrayList(mgsModelList);
             conversation.setItems(ConversationMsgs);
             conversation.refresh();
         }


    }
    
    
    
    
    


}
