package com.example.proyectogestion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button BtnLoginAdmin;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsuario;

    private Conexion conexion = new Conexion();

    private final String SELECT = "SELECT * FROM usuarios WHERE NOMUSU = ? AND PASSUSU = ?;";

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    void verificarLogin(ActionEvent event) throws IOException {

        conexion.establecerConexion();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int admin_o_empleado = 0;
        try {
            preparedStatement = conexion.getConnection().prepareStatement(SELECT);
            preparedStatement.setString(1, txtUsuario.getText());
            preparedStatement.setString(2, txtPassword.getText());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if(Objects.equals(resultSet.getString("IDROL"), "1")){
                    admin_o_empleado = 1;
                } else if(Objects.equals(resultSet.getString("IDROL"), "2")){
                    admin_o_empleado = 2;
                }
                AdminAddFacturaController.setEmpleado(resultSet.getString(4));
                AdminAddConsumidorController.setEmpleado(resultSet.getString(4));
                ModuloFacturacionController.setIdrol(resultSet.getInt("IDROL"));
                ModuloVentasController.setIdrol(resultSet.getInt("IDROL"));
                System.out.println("Bienvenido: " + resultSet.getString(4));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Datos de login incorrectos.");
                alert.showAndWait();
                System.out.println("Login incorrecto");
            }
        } catch (Exception e) {
            System.out.println("Ocurrio un error en el SQL");
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (Exception e) {
                    System.out.println("Ocurrió un error");
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception e) {
                    System.out.println("ocurrio un error");
                    e.printStackTrace();
                }
            }
        }
        conexion.cerrarConexion();

        if(admin_o_empleado == 1){
            root = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else if(admin_o_empleado == 2){
            root = FXMLLoader.load(getClass().getResource("EmpleadoView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
    }
}
