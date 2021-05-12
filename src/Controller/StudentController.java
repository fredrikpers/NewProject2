package Controller;

import Model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    private ImageView brandingImageView;
    @FXML
    private Button cancelButton;
    @FXML
    private Button yourCoursesButton;
    @FXML
    private Label userLabel;

    private User student;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Platform.runLater(()->{
            File brandingFile = new File("images/logo.png");
            Image brandingImage = new Image(brandingFile.toURI().toString());
            brandingImageView.setImage(brandingImage);
            userLabel.setText("Welcome " + student.getUsername());
            System.out.println(student.getUsername());
        });
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
    public void setStudent(User user){
        this.student = user;
    }

    public void createCourseForm(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/course.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            CourseController controller = fxmlLoader.<CourseController>getController();
            controller.setStudent(student);

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
