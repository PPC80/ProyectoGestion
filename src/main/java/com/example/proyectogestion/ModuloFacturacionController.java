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

public class ModuloFacturacionController {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConsultar;

    private Stage stage;
    private Scene scene;
    private Parent root;

    static int idrol;

    @FXML
    void abrirCancelar(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("CancelarFacturaView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void abrirConsultar(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ConsultarFacturaView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static int getIdrol() {
        return idrol;
    }

    public static void setIdrol(int idrol) {
        ModuloFacturacionController.idrol = idrol;
    }

    @FXML
    void atras(MouseEvent event) throws IOException {
        if(idrol == 1){
            root = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
        } else if(idrol == 2){
            root = FXMLLoader.load(getClass().getResource("EmpleadoView.fxml"));
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
