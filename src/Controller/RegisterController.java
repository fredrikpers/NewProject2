package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Model.CourseList;
import Model.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    DatabaseHandler database;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private Label registerMessageLabel;
    @FXML
    private Button registerButton;

    /**
     * RegisterController construcytor
     * @throws IOException Exception
     * @author Fredrik Pettersson
     */
    public RegisterController() throws IOException {
        this.database = new DatabaseHandler();
    }


    /**
     * Initializes the picture on the View
     * @param url url of the picture
     * @param resourceBundle resourceBundle
     * @author Fredrik Pettersson
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("images/logo.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);
    }

    /**
     * Register button onClick
     * @throws IOException exception
     * @author Fredrik Pettersson
     * Maybe move all logic to a new class "register" ?
     */
    public void registerNewUser() throws IOException {

        if(usernameTextField.getText().isBlank() || passwordTextField.getText().isBlank() || emailTextField.getText().isBlank()){
            registerMessageLabel.setText("Please enter all information needed");
            registerMessageLabel.setTextFill(Color.RED);
            return;
        }
        if(database.userExists(usernameTextField.getText())){
            registerMessageLabel.setText("User already exists");
            registerMessageLabel.setTextFill(Color.RED);
            return;
        }
        User user = new User();
        user.setUsername(usernameTextField.getText());

        if(!user.validatePassword(passwordTextField.getText())){
            registerMessageLabel.setText("password is not valid");
            registerMessageLabel.setTextFill(Color.RED);
            return;
        }
        if(!user.validateEmail(emailTextField.getText())){
            registerMessageLabel.setText("email is not valid");
            registerMessageLabel.setTextFill(Color.RED);
            return;
        }
        createUser(user);
        addUserToDatabase(user);
        registerMessageLabel.setText("User added!");
        registerMessageLabel.setTextFill(Color.GREEN);

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
     * Creates new user
     * @param user user
     * @author Fredrik Pettersson
     */
    public void createUser(User user){
        user.setPassword(passwordTextField.getText());
        user.setEmail(emailTextField.getText());
    }

    /**
     * Adds user to DB
     * @param user user
     * @author Fredrik Pettersson
     * Maybe move this to "register" in model?
     */
    public void addUserToDatabase(User user) {
        try {
            database.addUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
