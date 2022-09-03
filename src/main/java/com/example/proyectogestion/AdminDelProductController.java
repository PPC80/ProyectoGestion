package com.example.proyectogestion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class AdminDelProductController {

    @FXML
    private TextField txtCodigo;

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnEliminar;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Conexion conexion = new Conexion();
    private boolean retomarDatos = false;
    private int codigo;
    private final String DELETE = "DELETE FROM productos WHERE CODPROD = ?;";

    @FXML
    void eliminar(MouseEvent event) {
        tomarDatos();
        if(retomarDatos){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Por favor llenar el campo con el formato adecuado");
            alert.showAndWait();
        } else {
            conexion.establecerConexion();
            PreparedStatement preparedStatement = null;
            try{
                preparedStatement = conexion.getConnection().prepareStatement(DELETE);
                preparedStatement.setInt(1, codigo);

                Optional<ButtonType> resultadoConfirm = Alertas.confirmation("¿Está seguro que desea eliminar el producto?");
                if(resultadoConfirm.get() == ButtonType.OK){
                    if (preparedStatement.executeUpdate() == 0) {
                        System.out.println("NO SE HA ELIMINADO AL PRODUCTO CORRECTAMENTE");
                        Alertas.error("Producto no se encuentra registrado.");
                    } else {
                        System.out.println("PRODUCTO ELIMINADO CORRECTAMENTE");
                        Alertas.info("Producto eliminado correctamente.");
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
            codigo = Integer.parseInt(txtCodigo.getText());
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
