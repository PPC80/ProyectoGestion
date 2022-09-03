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

public class AdminModEmpController{

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnModificar;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCedulaNew;

    @FXML
    private TextField txtCedulaOld;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombre;


    private Stage stage;
    private Scene scene;
    private Parent root;

    private Conexion conexion = new Conexion();
    private boolean retomarDatos = false;
    private int cedulaOld, cedulaNew;
    private String nombres, apeliidos, email;
    private final String UPDATE = "UPDATE empleados em LEFT JOIN usuarios us ON em.CIEMPL = us.CIEMPL SET em.CIEMPL = ?, em.NOMEMPL = ?, em.APELEMPL = ?, em.MAILEMPL = ?, us.CIEMPL = ? WHERE em.CIEMPL = ?;";

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
                preparedStatement.setInt(1, cedulaNew);
                preparedStatement.setString(2, nombres);
                preparedStatement.setString(3, apeliidos);
                preparedStatement.setString(4, email);
                preparedStatement.setInt(5, cedulaNew);
                preparedStatement.setInt(6, cedulaOld);
                if(preparedStatement.executeUpdate() == 0){
                    System.out.println("NO SE HA MODIFICADO CORRECTAMENTE");
                    Alertas.error("Empleado no existe");
                } else {
                    System.out.println("Empleado modificado correctamente");
                    Alertas.info("Empleado modificado correctamente.");
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
            cedulaOld = Integer.parseInt(txtCedulaOld.getText());
            cedulaNew = Integer.parseInt(txtCedulaNew.getText());
            nombres = txtNombre.getText();
            apeliidos = txtApellido.getText();
            email = txtEmail.getText();
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

