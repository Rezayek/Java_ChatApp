package application.subView.msgView;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;

import javafx.scene.layout.HBox;


public class RightBox extends MainBox{

    RightBox(String title) {
        super(title);
        //TODO Auto-generated constructor stub
    }

    @Override
    Node getBox(){
        Label text = new Label(title);
        HBox rightBox = new HBox();
        rightBox.getChildren().addAll(text);
        rightBox.setSpacing(15);
        rightBox.setAlignment(Pos.BASELINE_RIGHT);
        rightBox.setPadding(new Insets(10,40,10,10));
        rightBox.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
              System.out.println("label event");
              event.consume();
            }
          });
        return rightBox;

    }

    
    
}
