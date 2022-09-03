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

public class AdminAddEmpController {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnRegistrar;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombre;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private int cedula;private String nombres, apeliidos, email;
    private boolean retomarDatos = false;
    private Conexion conexion = new Conexion();
    private final String INSERT_EMPLEADO = "INSERT INTO empleados VALUES (?,?,?,?);";

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
                preparedStatement = conexion.getConnection().prepareStatement(INSERT_EMPLEADO);
                preparedStatement.setInt(1, cedula);
                preparedStatement.setString(2, nombres);
                preparedStatement.setString(3, apeliidos);
                preparedStatement.setString(4, email);
                if (preparedStatement.executeUpdate() == 0) {
                    System.out.println("NO SE HA INSERTADO AL EMPLEADO CORRECTAMENTE");
                } else {
                    success = true;
                    System.out.println("EMPLEADO INSERTADO CORRECTAMENTE");
                    Alertas.info("Empleado registrado correctamente.");
                }
            } catch (Exception e) {
                System.out.println("ERROR CON LA SENTENCIA SQL...");
                Alertas.error("Datos mal ingresados o ese empleado ya se encuentra registrado...");
            } finally {
                PreparedStateCerrar.cerrarStatement(preparedStatement);
            }
            conexion.cerrarConexion();

            if(success){
                root = FXMLLoader.load(getClass().getResource("AdminGestionEmpleadosView.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("AdminAddEmpView.fxml"));
            }
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void atras(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminGestionEmpleadosView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void tomarDatos(){
        try{
            retomarDatos = false;
            nombres = txtNombre.getText();
            apeliidos = txtApellido.getText();
            email = txtEmail.getText();
            cedula = Integer.parseInt(txtCedula.getText());
        } catch(NumberFormatException e){
            retomarDatos = true;
        }
    }
}
