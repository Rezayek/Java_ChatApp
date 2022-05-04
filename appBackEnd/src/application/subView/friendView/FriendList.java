package application.subView.friendView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import client.clientLocalDb.ClientDb;
import javafx.scene.layout.VBox;

public class FriendList  {
    public static Map<String, String> oldMap = new LinkedHashMap<String, String>();
    public static Boolean finishDisplayFriends = false;
    public static Map<String, String> friendsData ;
    public VBox main;
    ArrayList<FriendWidget> widgets; 
    
    ClientDb client = ClientDb.getInstance();
    public FriendList(VBox main){
        this.main = main;  
    }

    public void addFriends() {
        
                
                widgets = new ArrayList<FriendWidget>();
                if(oldMap.isEmpty() != true){    
                Set<String> keys = friendsData.keySet();
                for (String key : keys) {
                if(!key.equals("result")){
                    if(!oldMap.containsKey(key)){
                        widgets.add(new FriendWidget(friendsData.get(key), key));
                    }
                    
                }
                }

            
                for(int i = 0 ; i< widgets.size(); i++){
                   
                main.getChildren().add(widgets.get(i).getBox());
                System.out.println("here size : "+  widgets.size());
                }

            }
            else{
                Set<String> keys = friendsData.keySet();
                for (String key : keys) {
                if(!key.equals("result")){
                    widgets.add(new FriendWidget(friendsData.get(key), key));
                }
                }

            
                for(int i = 0 ; i< widgets.size(); i++){
                main.getChildren().add(widgets.get(i).getBox());
                }
            }
            
        }

           

    
           

        }
             
        
    

