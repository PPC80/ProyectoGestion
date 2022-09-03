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

public class AdminGestionProductosController {

    @FXML
    private Button btnAgregarCat;

    @FXML
    private Button btnAgregarProd;

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnConsultarCat;

    @FXML
    private Button btnConsultarProd;

    @FXML
    private Button btnEliminarCat;

    @FXML
    private Button btnEliminarProd;

    @FXML
    private Button btnModicarProd;

    @FXML
    private Button btnModificarCat;

    @FXML
    private Button btnStock;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void gestionarStock(MouseEvent event) {

    }

    @FXML
    void AgregarCat(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminAddCategoriaView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void agregarProd(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminAddProductoView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void consultarCat(MouseEvent event) {

    }

    @FXML
    void consultarProd(MouseEvent event) {

    }

    @FXML
    void eliminarCat(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminDelCategoriaView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void eliminarProd(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminDelProductView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void modificarCat(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminModCategoriaView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void modificarProd(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminModProductoView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void atras(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminGestionProductosView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}