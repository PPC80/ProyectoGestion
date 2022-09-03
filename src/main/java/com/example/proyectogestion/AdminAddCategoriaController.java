package com.example.proyectogestion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;

public class AdminAddCategoriaController {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnRegistrar;

    @FXML
    private TextField txtNombre;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String nombre;
    private boolean retomarDatos = false;
    private Conexion conexion = new Conexion();
    private final String INSERT_CATEGORIA = "INSERT INTO categorias (NOMCAT) VALUES (?);";

    @FXML
    void registrar(MouseEvent event) throws IOException {
        boolean success = false;
        tomarDatos();
        if(retomarDatos){
            Alertas.error("Por favor llenar el campo con el formato adecuado");
        } else {
            conexion.establecerConexion();
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = conexion.getConnection().prepareStatement(INSERT_CATEGORIA);
                preparedStatement.setString(1, nombre);
                if (preparedStatement.executeUpdate() == 0) {
                    System.out.println("NO SE HA INSERTADO LA CATEGORIA CORRECTAMENTE");
                } else {
                    success = true;
                    System.out.println("CATEGORIA INSERTADA CORRECTAMENTE");
                    Alertas.info("Categoria registrada correctamente.");
                }
            } catch (Exception e) {
                System.out.println("ERROR CON LA SENTENCIA SQL...");
                Alertas.error("Datos mal ingresados o esa categoria ya se encuentra registrada...");
            } finally {
                PreparedStateCerrar.cerrarStatement(preparedStatement);
            }
            conexion.cerrarConexion();

            if(success){
                root = FXMLLoader.load(getClass().getResource("AdminGestionProductosView.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("AdminAddCategoriaView.fxml"));
            }
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void atras(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminGestionProductosView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void tomarDatos(){
        try{
            retomarDatos = false;
            nombre = txtNombre.getText();
        } catch(NumberFormatException e){
            retomarDatos = true;
        }
    }
}
