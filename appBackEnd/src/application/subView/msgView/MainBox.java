package application.subView.msgView;
import javafx.scene.Node;

public abstract class MainBox {
    String title;

    MainBox(String title){
        this.title = title;
    }

    abstract Node getBox();
}
