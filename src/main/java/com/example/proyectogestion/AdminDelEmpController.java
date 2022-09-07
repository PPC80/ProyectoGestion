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

public class AdminDelEmpController {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnEliminar;

    @FXML
    private TextField txtCedula;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Conexion conexion = new Conexion();
    private boolean retomarDatos = false;
    private int cedula;
    private final String DELETE = "DELETE usuarios.*, empleados.* FROM empleados LEFT JOIN usuarios ON empleados.CIEMPL = usuarios.CIEMPL WHERE empleados.CIEMPL = ?;";


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
                preparedStatement.setInt(1, cedula);

                Optional<ButtonType> resultadoConfirm = Alertas.confirmation("¿Está seguro que desea eliminar al empleado? Esta acción también eliminará al usuario del empleado.");
                if(resultadoConfirm.get() == ButtonType.OK){
                    if (preparedStatement.executeUpdate() == 0) {
                        System.out.println("NO SE HA ELIMINADO AL EMPLEADO CORRECTAMENTE");
                        Alertas.error("Empleado no se encuentra registrado.");
                    } else {
                        System.out.println("EMPLEADO ELIMINADO CORRECTAMENTE");
                        Alertas.info("Empleado eliminado correctamente.");
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
            cedula = Integer.parseInt(txtCedula.getText());
        } catch(NumberFormatException e){
            retomarDatos = true;
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
}
