module com.lertos.javafxitemgenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.lertos.javafxitemgenerator to javafx.fxml;
    exports com.lertos.javafxitemgenerator;
}