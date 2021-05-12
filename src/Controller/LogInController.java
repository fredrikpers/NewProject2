package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.*;

import javafx.stage.StageStyle;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import Model.User;


import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class LogInController implements Initializable {

    @FXML
    private Button registerButton;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTestField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;

    private DatabaseHandler database;
    private User user;

    public LogInController() throws IOException {
        this.database = new DatabaseHandler();
        this.user = new User();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("images/logo.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);
    }

    /**
     * Login Button onclick
     * @param event ActionEvent
     * @throws IOException Exception
     * @throws InterruptedException Exception
     * Maybe move some of this to a model class called "login"?
     */
    public void loginButtonOnAction(ActionEvent event) throws IOException, InterruptedException {

        if(usernameTextField.getText().isBlank() || passwordTestField.getText().isBlank()){
            loginMessageLabel.setText("Please enter a username and password");
            loginMessageLabel.setTextFill(Color.RED);
            return;
        }

        if(database.userExistsLogIn(usernameTextField.getText())){
            user = database.getUser(usernameTextField.getText());
        }
        System.out.println(Arrays.toString(user.userDataToArray(user)));

        if(!validateLogin(user)) {
            loginMessageLabel.setText("Invalid login. Please try again!");
            loginMessageLabel.setTextFill(Color.RED);
            return;
        }

        loginMessageLabel.setText("Logged in correctly!");
        loginMessageLabel.setTextFill(Color.GREEN);

        //TimeUnit.SECONDS.sleep(3);
        createUserForm();
    }

    /**
     * Closes the window
     * @param event event
     * @author Fredrik Pettersson
     */
    public void cancelButton(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Registers onclick register Button
     * @author Fredrik Pettersson
     * @param event ActionEvent
     */
    public void registerButton(ActionEvent event){
        createAccountForm();
    }

    /**
     * Validates login
     * @param user user
     * @return true if password is OK else false
     * @throws IOException exception
     * Maybe move some of this to a model class called "login"?
     */
    public boolean validateLogin(User user) throws IOException {
        if(user.checkPassword(user, passwordTestField.getText()))
            return true;
        return false;
    }

    /**
     * Creates the AccountForm
     * @author Fredrik Pettersson
     */
    public void createAccountForm(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/View/register.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.DECORATED);
            registerStage.setScene(new Scene(root, 600, 600));
            registerStage.show();

        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * Creates the User profile Form
     * @author Fredrik Pettersson
     */
    public void createUserForm(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/student.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            StudentController controller = fxmlLoader.<StudentController>getController();
            controller.setStudent(user);

            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.DECORATED);
            registerStage.setScene(new Scene(root, 600, 600));
            registerStage.show();

        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }



}
