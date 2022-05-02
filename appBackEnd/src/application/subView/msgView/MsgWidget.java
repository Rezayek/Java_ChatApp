package application.subView.msgView;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import client.clientLocalDb.ClientDb;
import javafx.scene.layout.VBox;

public class MsgWidget extends Thread{
    public static VBox main;
    public static Map<String, String> ConverstionData;
    public static Boolean isCalled = true;
    ArrayList<MainBox> widgets; 
    ClientDb client = ClientDb.getInstance();
    public MsgWidget(){}

    public void run() {

        while (true){

            while(isCalled){
                if(ConverstionData == null){
                    main = new VBox();
                    isCalled = false;
                }else{
                    main = new VBox();
                    widgets = new ArrayList<MainBox>();
                    Set<String> keys = ConverstionData.keySet();
                    String id = String.valueOf(client.getId());
                    
                    for (String key : keys) {
                        if(key == id ){
                            widgets.add(new RightBox(ConverstionData.get(key)));
                        }else{
                            widgets.add(new LeftBox(ConverstionData.get(key)));
                        }
                    }
                    

                    
                    for(int i = 0 ; i< widgets.size(); i++){
                        main.getChildren().add(widgets.get(i).getBox());
                    }
                    isCalled = false;

                }
                

            }

        }
        
    }

}
