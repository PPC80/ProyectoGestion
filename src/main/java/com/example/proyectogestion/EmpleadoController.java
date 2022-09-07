package com.example.proyectogestion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class EmpleadoController {

    @FXML
    private Button btnFacturacion;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnVentas;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void abrirFacturacion(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ModuloFacturacionView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void abrirVentas(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ModuloVentasView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void salir(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("loginView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
