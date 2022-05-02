package application.subView.msgView;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


public class LeftBox extends MainBox {

    LeftBox(String title) {
        super(title);
        
    }

    @Override
    Node getBox(){

        Label text = new Label(title);
        HBox leftBox = new HBox();
        leftBox.getChildren().add(text);
        leftBox.setSpacing(15);
        leftBox.setAlignment(Pos.BASELINE_LEFT);
        leftBox.setPadding(new Insets(10,40,10,10));
        leftBox.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
              System.out.println("label event");
              event.consume();
            }
          });
        return leftBox;

    }

    
    
}
