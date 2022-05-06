package application.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import application.models.MsgModel;
import client.Client;
import client.clientLocalDb.ClientDb;
import client.threads.GetMsgsClientThread;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;

public class ConvThread extends Thread {

    public static Boolean isRunning;
    public static String currentFriend;
    public static int currentFriendId;
    public static TableView<MsgModel> conversation;
    public static Map<String, String> genericMsgs =  new LinkedHashMap<String, String>();
    ObservableList<MsgModel> ConversationMsgs ;
    private List<MsgModel> mgsModelList = new ArrayList<MsgModel>();
    ClientDb client = ClientDb.getInstance();
    Client clientS = Client.getInstance();

    public ConvThread(){
        
    }


    public void run(){

        while (isRunning){
            try {
                Thread.sleep(30);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
                GetMsgsClientThread getMsgs = new GetMsgsClientThread(client.getId(), currentFriendId , clientS.getSocket());
                getMsgs.start();
                try {
                    getMsgs.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               
                genericMsgs = GetMsgsClientThread.genericMsgMap;
                if(!(genericMsgs == null || genericMsgs.isEmpty())){ 
                    mgsModelList = new ArrayList<MsgModel>();
                    for (String key : genericMsgs.keySet()) {
                        if(key != "result"){
                            if(key.contains(String.valueOf(client.getId())+"/")){
                                mgsModelList.add(new MsgModel("you : ", genericMsgs.get(key)));
                            }else if(key.contains(String.valueOf(currentFriendId+"/"))){
                                mgsModelList.add(new MsgModel(currentFriend, genericMsgs.get(key)));
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


                conversation.refresh();
        }
    }


}
