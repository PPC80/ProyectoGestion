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

public class AdminGestionEmpleadosController {

    @FXML
    private Button BtnAtras;

    @FXML
    private Button btnAgregarEmp;

    @FXML
    private Button btnAgregarUsu;

    @FXML
    private Button btnEliminarEmp;

    @FXML
    private Button btnEliminarUsu;

    @FXML
    private Button btnModEmp;

    @FXML
    private Button btnModUsu;

    @FXML
    private Button btnConsultar;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void AgregarEmp(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminAddEmpView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void AgregarUsu(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminAddUserView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void EliminarEmp(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminDelEmpView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void EliminarUsu(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminDelUserView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void ModEmp(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminModEmpView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void ModUsu(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminModUsuView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void consultar(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminConsultUserView.fxml"));
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

