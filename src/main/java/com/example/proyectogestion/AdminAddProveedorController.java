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

public class AdminAddProveedorController {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnRegistrar;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtRuc;

    @FXML
    private TextField txtTelefono;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String nombre, direccion, email, telefono, ruc;
    private boolean retomarDatos = false;
    private Conexion conexion = new Conexion();
    private final String INSERT_PROVEEDOR = "INSERT INTO proveedores VALUES (?,?,?,?,?);";

    @FXML
    void registrar(MouseEvent event) throws IOException {
        boolean success = false;
        tomarDatos();
        if(retomarDatos){
            Alertas.error("Por favor llenar todos los campos con el formato adecuado");
        } else {
            conexion.establecerConexion();
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = conexion.getConnection().prepareStatement(INSERT_PROVEEDOR);
                preparedStatement.setString(1, ruc);
                preparedStatement.setString(2, nombre);
                preparedStatement.setString(3, telefono);
                preparedStatement.setString(4, direccion);
                preparedStatement.setString(5, email);
                if (preparedStatement.executeUpdate() == 0) {
                    System.out.println("NO SE HA INSERTADO AL PROVEEDOR CORRECTAMENTE");
                } else {
                    success = true;
                    System.out.println("PROVEEDOR INSERTADO CORRECTAMENTE");
                    Alertas.info("Proveedor registrado correctamente.");
                }
            } catch (Exception e) {
                System.out.println("ERROR CON LA SENTENCIA SQL...");
                Alertas.error("Datos mal ingresados, o ese proveedor ya se encuentra registrado...");
            } finally {
                PreparedStateCerrar.cerrarStatement(preparedStatement);
            }
            conexion.cerrarConexion();

            if(success){
                root = FXMLLoader.load(getClass().getResource("AdminGestionProveedoresView.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("AdminAddProveedorView.fxml"));
            }
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
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

    public void tomarDatos(){
        try{
            retomarDatos = false;
            ruc = txtRuc.getText();
            nombre = txtNombre.getText();
            telefono = txtTelefono.getText();
            direccion = txtDireccion.getText();
            email = txtEmail.getText();
        } catch(NumberFormatException e){
            e.printStackTrace();
            retomarDatos = true;
        }
    }
}
