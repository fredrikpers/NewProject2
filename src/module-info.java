module NewProject {

    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires poi;

    opens Startup;
    opens Controller;
    opens View;
    opens Model;

}