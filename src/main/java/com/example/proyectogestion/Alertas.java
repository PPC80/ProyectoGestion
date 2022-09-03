package com.example.proyectogestion;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alertas {

    public static void info(String string){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Información");
        alert.setContentText(string);
        alert.showAndWait();
    }

    public static Optional<ButtonType> confirmation(String string){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmar");
        alert.setContentText(string);
        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado;
    }

    public static void error(String string){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(string);
        alert.showAndWait();
    }
}
