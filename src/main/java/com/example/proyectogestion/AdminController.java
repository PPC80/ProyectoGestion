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

public class AdminController {

    @FXML
    private Button btnEstadisticas;

    @FXML
    private Button btnFacturacion;

    @FXML
    private Button btnGestion;

    @FXML
    private Button btnProductos;

    @FXML
    private Button btnProveedores;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnVentas;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void abrirGestion(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminGestionEmpleadosView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void abrirProv(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminGestionProveedoresView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void abrirProd(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminGestionProductosView.fxml"));
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
    void abrirFacturacion(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ModuloFacturacionView.fxml"));
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

