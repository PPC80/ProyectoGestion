module com.example.proyectogestion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.proyectogestion to javafx.fxml;
    exports com.example.proyectogestion;
}