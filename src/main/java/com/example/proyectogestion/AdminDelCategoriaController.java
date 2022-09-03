package com.example.proyectogestion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class AdminDelCategoriaController {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnEliminar;

    @FXML
    private TextField txtNombre;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Conexion conexion = new Conexion();
    private boolean retomarDatos = false;
    private String nombre;
    private final String DELETE = "DELETE FROM categorias WHERE NOMCAT = ?;";


    @FXML
    void eliminar(MouseEvent event) {
        tomarDatos();
        if(retomarDatos){
            Alertas.error("Por favor llenar el campo con el formato adecuado");
        } else {
            conexion.establecerConexion();
            PreparedStatement preparedStatement = null;
            try{
                preparedStatement = conexion.getConnection().prepareStatement(DELETE);
                preparedStatement.setString(1, nombre);

                Optional<ButtonType> resultadoConfirm = Alertas.confirmation("¿Está seguro que desea eliminar la categoria?");
                if(resultadoConfirm.get() == ButtonType.OK){
                    if (preparedStatement.executeUpdate() == 0) {
                        System.out.println("NO SE HA ELIMINADO LA CATEGORIA CORRECTAMENTE");
                        Alertas.error("Categoria no se encuentra registrada.");
                    } else {
                        System.out.println("CATEGORIA ELIMINADA CORRECTAMENTE");
                        Alertas.info("Categoria eliminada correctamente.");
                    }
                } else if(resultadoConfirm.get() == ButtonType.CANCEL){

                }
            } catch (SQLException e){
                System.out.println("ERROR CON LA SENTENCIA SQL...");
                e.printStackTrace();
            } finally {
                PreparedStateCerrar.cerrarStatement(preparedStatement);
            }
            conexion.cerrarConexion();
        }
    }

    public void tomarDatos(){
        try{
            retomarDatos = false;
            nombre = txtNombre.getText();
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
