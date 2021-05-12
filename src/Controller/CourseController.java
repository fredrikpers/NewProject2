package Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Model.CourseList;
import Model.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CourseController implements Initializable {

    @FXML
    private ImageView brandingImageView;
    @FXML
    private ListView<String> courseAvailableList;
    @FXML
    private Button addCoursesButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label registerMessage;

    private CourseList list;
    private ArrayList allCourses;
    private ObservableList<String> selectedCourses;
    private DatabaseHandler database;
    private User student;

    public CourseController() throws IOException {
        this.list = new CourseList();
        this.database = new DatabaseHandler();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("images/logo.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);
        getAvailableCourses();
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

    public void getAvailableCourses(){
        allCourses = list.allAvailableCourses();
        courseAvailableList.getItems().addAll(allCourses);
    }

    public void setStudent(User user){
        this.student = user;
    }

    public void selectedCourses() throws IOException {
        selectedCourses = courseAvailableList.getSelectionModel().getSelectedItems();

        for(String m: selectedCourses){
            if(!database.courseAlreadyAdded(student, m)) {
                database.addCourse(student, m);
                registerMessage.setText("Course added!");
                registerMessage.setTextFill(Color.GREEN);
            }else{
                registerMessage.setText("Course already added");
                registerMessage.setTextFill(Color.RED);
            }
        }
    }
}
