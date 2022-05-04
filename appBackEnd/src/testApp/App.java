package testApp;

import java.util.Map;


import application.subView.friendView.FriendList;
import application.subView.msgView.MsgWidget;
import client.Client;
import client.clientLocalDb.ClientDb;
import client.clientLocalDb.clientModels.MsgDataModel;
import client.threads.GetFriendsClientThread;
import client.threads.GetMsgsClientThread;
import client.threads.SendFriendClientThread;
import client.threads.SendMsgClientThread;
import javafx.application.Application;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    public Stage primaryStage;
    public VBox main, motherBox,conversationBox, converstionMsg, otherMain;
    ClientDb client = ClientDb.getInstance();

    Map<String, String> genericFriends;
    Map<String, String> genericMsg;
    Boolean requestFriends;
    Button addFriend, sendMsg;
    TextField friendName, sendedMsg;
    HBox addingFriend, bigBox, sendingMsgBox;


    Client clientS = Client.getInstance();


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //bigBox = new HBox();
        //***************************************************
        //*****************FriendsList*********************
        //***************************************************
        motherBox = new VBox();
        addFriend = new Button("add friend");
        friendName = new TextField();
        addingFriend = new HBox();
        addingFriend.getChildren().addAll(friendName, addFriend);
        addingFriend.setSpacing(20);
        main = new VBox();
        //***************************************************
        //****************Conversation*********************
        //***************************************************


        //otherMain = new VBox();
        //conversationBox = new VBox();
        //converstionMsg = new VBox();
        //sendMsg = new Button("sendMsg");
        //sendedMsg = new TextField();
        //sendingMsgBox = new HBox();
        //sendingMsgBox.getChildren().addAll(sendedMsg,sendMsg );
        //sendingMsgBox.setSpacing(50);
        //new MsgWidget().start();
        //while(MsgWidget.isCalled){}
        //System.out.println(MsgWidget.isCalled);
        //converstionMsg.getChildren().clear();
        //converstionMsg.getChildren().add(otherMain);
        //conversationBox.getChildren().addAll(otherMain, sendingMsgBox);


        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------


        new GetFriendsClientThread(client.getId(), clientS.getSocket()).start();
        while(genericFriends == null){
            genericFriends = GetFriendsClientThread.genericFriendMap;
        }
        FriendList.friendsData = genericFriends;
        


        //------------------------------------------------------------------------
        //----------change this thread and add FriendList.oldMap/FriendList.friendsData-----------------------------
        //------------------------------------------------------------------------
        //new FriendList(main).start();
        //-------------------------------------------------------------------------
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        motherBox.getChildren().addAll(main, addingFriend);
        //bigBox.getChildren().addAll(motherBox, conversationBox );
        Scene scene = new Scene(motherBox,200,300);
        primaryStage.setScene(scene);
		primaryStage.setTitle("Test");
		primaryStage.show();



        // sendMsg.setOnMouseClicked(new EventHandler<Event>() {

        //     @Override
        //     public void handle(Event event) {
        //         GetMsgsClientThread.genericMsgMap = null;
        //         new SendMsgClientThread(new MsgDataModel(client.getId(), sendedMsg.getText()), GetMsgsClientThread.friendId);
        //         try {
        //             Thread.sleep(100);
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //         genericFriends = null;
        //         new GetMsgsClientThread(client.getId(), GetMsgsClientThread.friendId, clientS.getSocket()).start();
        //         while(genericFriends == null){
        //             genericFriends = GetFriendsClientThread.genericFriendMap;
        //         }
        //         MsgWidget.ConverstionData = genericMsg;
        //         primaryStage.close();
        //         MsgWidget.isCalled = true;
        //         try {
        //             Thread.sleep(100);
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //         primaryStage.show();
        //         event.consume();



        //     }

        // });



        addFriend.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {

                new SendFriendClientThread(friendName.getText(), client.getId()).start();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                FriendList.oldMap = genericFriends;
                genericFriends = null;
                GetFriendsClientThread.genericFriendMap = null;
                new GetFriendsClientThread(client.getId(), clientS.getSocket()).start();
                while(genericFriends == null){
                    genericFriends = GetFriendsClientThread.genericFriendMap;
                }
                
                FriendList.friendsData = genericFriends;
                //FriendList.finishDisplayFriends = false;
                primaryStage.close();
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                primaryStage.show();
                event.consume();

            }
            });


    }

}
