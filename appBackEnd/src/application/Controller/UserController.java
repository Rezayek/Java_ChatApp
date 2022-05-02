package application.Controller;

import client.Client;
import client.clientLocalDb.ClientDb;
import client.clientLocalDb.clientModels.UserDataModel;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;

import client.threads.LoginClientThread;
import client.threads.SignUpClientThread;


public class UserController {
    @FXML
    public AnchorPane signInPage;
    @FXML
    public AnchorPane signUpPage;
    @FXML
    public Button btnSignUp;
    @FXML
    public Button getStarted;
    @FXML
    public ImageView btnBack;
    @FXML
    public TextField regUsername;
    @FXML
    public TextField regPsw;
    @FXML
    public TextField regEmail;
    @FXML
    public TextField regFirstName;
    @FXML
    public TextField regPhoneNum;
    @FXML
    public RadioButton male;
    @FXML
    public RadioButton female;
    @FXML
    public Label controlRegLabel;
    @FXML
    public Label success;
    @FXML
    public Label goBack;
    @FXML
    public TextField userName;
    @FXML
    public TextField passWord;
    @FXML
    public Label loginNotifier;
    @FXML
    public Label nameExists;
    @FXML
    public Label checkEmail;
    @FXML
    public Button loginBtn;
    public static String username, password, gender;
    

    Client client = Client.getInstance();
    ClientDb clientDb = ClientDb.getInstance();
    @FXML public void onMouseClickedCancelBtn(InputEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
  //sign Up
  //username,email,password, phone number, gender: are required
  //validate the non existance of the user who is trying to signup with the method checkUser 
  //after validation in case of success a new account for the new user will be created and message of success will be displayed
    public void registration() {
        if (		(!regUsername.getText().equalsIgnoreCase(""))
                && (!regPsw.getText().equalsIgnoreCase(""))
                && (!regEmail.getText().equalsIgnoreCase(""))
                && (!regFirstName.getText().equalsIgnoreCase(""))
                && (!regPhoneNum.getText().equalsIgnoreCase(""))
                && ((male.isSelected() || female.isSelected())) )
            { 
            System.out.println(regFirstName.getText());
            
                new SignUpClientThread(client.getSocket(), new UserDataModel(regFirstName.getText(), regEmail.getText(), regPsw.getText())).start();          
        
                goBack.setOpacity(1);
                success.setOpacity(1);
                makeDefault();
            if (controlRegLabel.getOpacity() == 1) {
                controlRegLabel.setOpacity(0);
            }
            if (nameExists.getOpacity() == 1) {
                nameExists.setOpacity(0);
            } else {
                nameExists.setOpacity(1);
                setOpacity(success, goBack, controlRegLabel);
            }
        
            } else {
            controlRegLabel.setOpacity(1);
            setOpacity(success, goBack, nameExists, checkEmail);
        }
    }


    private void setOpacity(Label a, Label b, Label c, Label d) {
        if(a.getOpacity() == 1 || b.getOpacity() == 1 || c.getOpacity() == 1 || d.getOpacity() == 1) {
            a.setOpacity(0);
            b.setOpacity(0);
            c.setOpacity(0);
            d.setOpacity(0);
        }
    }


    private void setOpacity(Label controlRegLabel, Label checkEmail, Label nameExists) {
        controlRegLabel.setOpacity(0);
        checkEmail.setOpacity(0);
        nameExists.setOpacity(0);
    }
//_________________________________________________________________________________________________________________________________________________________________________________________________________
//reset signup input
    private void makeDefault() {
    	regUsername.setText("");
        regPsw.setText("");
        regEmail.setText("");
        regFirstName.setText("");
        regPhoneNum.setText("");
        male.setSelected(true);
        setOpacity(controlRegLabel, checkEmail, nameExists);
    }
    
//______________________________________________________________________________________________________________
//login validation  : 
//if user's username and password exists in the User table than the chat account will be opened and the user will be added in Array list of the login users 
//else error message will be displayed

    public void login() throws SQLException, InterruptedException {
        username = userName.getText();
        password = passWord.getText();
        new LoginClientThread(client.getSocket(), username, password).start();
        
        changeWindow();
            
    }
//_________________________________________________________________________________________________________
//open messenger window after login 
    
    public void changeWindow() {
    	Stage stage = new Stage(); 
        Parent root = null;
        
        
        try {
            root = FXMLLoader.load(this.getClass().getResource("../View/chatLayout.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("main");
            stage.setOnCloseRequest(event -> {
                System.exit(0);
            });
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
 //__________________________________________________________________________________________________________
 //redirection from loginPage to signUpPage or from signupPage to loginPage 

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource().equals(btnSignUp)) {
            new FadeTransition(Duration.millis(3000),signUpPage).play();
            signUpPage.toFront();
        }
        if (event.getSource().equals(getStarted)) {
            new FadeTransition(Duration.millis(3000),signInPage).play();
            signInPage.toFront();
        }
        loginNotifier.setOpacity(0);
        userName.setText("");
        passWord.setText("");
    }
    
//__________________________________________________________________________________________________
//redirection to loginPage using the button btnBack
    @FXML
    private void handleMouseEvent(MouseEvent event) {
        if (event.getSource() == btnBack) {

            new FadeTransition(Duration.millis(3000),btnBack).play();
            signInPage.toFront();
        }
        regUsername.setText("");
        regPsw.setText("");
        regEmail.setText("");
    }
}