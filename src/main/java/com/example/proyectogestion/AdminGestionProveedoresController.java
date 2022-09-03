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

public class AdminGestionProveedoresController {

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnConsultProv;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void agregar(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminAddProveedorView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void consultar(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminConsultProveedorView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void eliminar(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminDelProveedorView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void modificar(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminModProveedorView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void atras(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

