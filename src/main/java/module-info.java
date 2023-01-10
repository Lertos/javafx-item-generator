module com.lertos.javafxitemgenerator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lertos.javafxitemgenerator to javafx.fxml;
    exports com.lertos.javafxitemgenerator;
}