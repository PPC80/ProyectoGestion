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

public class AdminDelProveedorController {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnEliminar;

    @FXML
    private TextField txtRuc;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Conexion conexion = new Conexion();
    private boolean retomarDatos = false;
    private String ruc;
    private final String DELETE = "DELETE FROM proveedores WHERE RUCPROV = ?;";


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
                preparedStatement.setString(1, ruc);

                Optional<ButtonType> resultadoConfirm = Alertas.confirmation("¿Está seguro que desea eliminar al proveedor?");
                if(resultadoConfirm.get() == ButtonType.OK){
                    if (preparedStatement.executeUpdate() == 0) {
                        System.out.println("NO SE HA ELIMINADO AL PROVEEDOR CORRECTAMENTE");
                        Alertas.error("Proveedor no se encuentra registrado.");
                    } else {
                        System.out.println("PROVEEDOR ELIMINADO CORRECTAMENTE");
                        Alertas.info("Proveedor eliminado correctamente.");
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
            ruc = txtRuc.getText();
        } catch(NumberFormatException e){
            retomarDatos = true;
        }
    }

    @FXML
    void atras(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminGestionProveedoresView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
