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

public class AdminModProveedorController {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnModificar;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtRucNew;

    @FXML
    private TextField txtRucOld;

    @FXML
    private TextField txtTelefono;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Conexion conexion = new Conexion();
    private boolean retomarDatos = false;
    private String rucOld, rucNew, nombre, telefono, direccion, email;
    private final String UPDATE = "UPDATE proveedores SET RUCPROV = ?, NOMPROV = ?, TLFPROV = ?, DIRPROV = ?, MAILPROV = ? WHERE RUCPROV = ?;";

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
                preparedStatement.setString(1, rucNew);
                preparedStatement.setString(2, nombre);
                preparedStatement.setString(3, telefono);
                preparedStatement.setString(4, direccion);
                preparedStatement.setString(5, email);
                preparedStatement.setString(6, rucOld);
                if(preparedStatement.executeUpdate() == 0){
                    System.out.println("NO SE HA MODIFICADO CORRECTAMENTE");
                    Alertas.error("Proveedor no existe");
                } else {
                    System.out.println("Proveedor modificado correctamente");
                    Alertas.info("Proveedor modificado correctamente.");
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
            rucOld = txtRucOld.getText();
            rucNew = txtRucNew.getText();
            nombre = txtNombre.getText();
            telefono = txtTelefono.getText();
            direccion = txtDireccion.getText();
            email = txtEmail.getText();
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

