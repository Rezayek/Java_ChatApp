package application.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ChatController implements Initializable{
	@FXML
    ImageView chatBtn;
	@FXML
    ImageView usersBtn;
	@FXML
    ImageView profileBtn;
	@FXML
    ImageView settingsBtn;
	@FXML
    ImageView signoutBtn;
	

    @FXML
    public void handlechatBtnClicked() {
        System.out.println( "Button chat clicked");
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
