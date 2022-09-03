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
import java.sql.SQLException;

public class AdminModCategoriaController {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnModificar;

    @FXML
    private TextField txtNombreNew;

    @FXML
    private TextField txtNombreOld;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Conexion conexion = new Conexion();
    private boolean retomarDatos = false;
    private String nombreOld, nombreNew;
    private final String UPDATE = "UPDATE categorias SET NOMCAT = ? WHERE NOMCAT = ?;";

    @FXML
    void modificar(MouseEvent event) {
        tomarDatos();
        if(retomarDatos){
            Alertas.error("Por favor llenar todos los campos con el formato adecuado");
        } else {
            conexion.establecerConexion();
            PreparedStatement preparedStatement = null;
            try{
                preparedStatement = conexion.getConnection().prepareStatement(UPDATE);
                preparedStatement.setString(1, nombreNew);
                preparedStatement.setString(2, nombreOld);
                if(preparedStatement.executeUpdate() == 0){
                    System.out.println("NO SE HA MODIFICADO CORRECTAMENTE");
                    Alertas.error("Categoría no existe");
                } else {
                    System.out.println("Categoría modificada correctamente");
                    Alertas.info("Categoría modificada correctamente.");
                }
            } catch (SQLException e) {
                System.out.println("Error con el SQL");
                e.printStackTrace();
            } finally {
                PreparedStateCerrar.cerrarStatement(preparedStatement);
            }
        }
    }

    public void tomarDatos(){
        try{
            retomarDatos = false;
            nombreOld = txtNombreOld.getText();
            nombreNew = txtNombreNew.getText();
        } catch(NumberFormatException e){
            retomarDatos = true;
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
}
