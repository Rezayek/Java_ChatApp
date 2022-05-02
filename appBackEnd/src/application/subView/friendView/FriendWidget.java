package application.subView.friendView;


import application.subView.msgView.MsgWidget;
import client.Client;
import client.clientLocalDb.ClientDb;

import client.threads.GetMsgsClientThread;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;



public class FriendWidget{


    private String friendName;
    private String friendId;
    ClientDb client = ClientDb.getInstance();
    Client clientS = Client.getInstance();
    FriendWidget(String FriendName, String friendId) {
        this.friendName = FriendName;
        this.friendId = friendId;

        
    }
    
    
    public HBox getBox(){


        Label text = new Label(friendName);
        HBox leftBox = new HBox();
        leftBox.getChildren().add(text);
        leftBox.setSpacing(15);
        leftBox.setAlignment(Pos.BASELINE_LEFT);
        leftBox.setPadding(new Insets(10,40,10,10));
        leftBox.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                GetMsgsClientThread.genericMsgMap = null;
                new GetMsgsClientThread(client.getId(), Integer.parseInt(friendId), clientS.getSocket()).start();
                while(GetMsgsClientThread.genericMsgMap == null){
                    MsgWidget.ConverstionData = GetMsgsClientThread.genericMsgMap;
                }
                //App.primaryStage.close();
                MsgWidget.isCalled = true;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }   
                //App.primaryStage.show();      
                event.consume();
            }
            });
        return leftBox;

    }
    
}
