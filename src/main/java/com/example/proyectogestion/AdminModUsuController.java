package com.example.proyectogestion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminModUsuController  implements Initializable {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnModificar;

    @FXML
    private RadioButton rbtnAdmin;

    @FXML
    private RadioButton rbtnEmpleado;

    @FXML
    private TextField txtCedulaOld;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsuario;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Conexion conexion = new Conexion();
    private boolean retomarDatos = false;
    private int cedulaOld, idRol;
    private String usuario, password;
    private final String UPDATE = "UPDATE usuarios SET IDROL = ?, NOMUSU = ?, PASSUSU = ? WHERE CIEMPL = ?;";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        rbtnEmpleado.setToggleGroup(toggleGroup);
        rbtnAdmin.setToggleGroup(toggleGroup);
    }

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
                if(rbtnAdmin.isSelected()){
                    idRol = 1;
                } else if (rbtnEmpleado.isSelected()){
                    idRol = 2;
                }
                preparedStatement.setInt(1, idRol);
                preparedStatement.setString(2, usuario);
                preparedStatement.setString(3, password);
                preparedStatement.setInt(4, cedulaOld);

                if(preparedStatement.executeUpdate() == 0){
                    System.out.println("NO SE HA MODIFICADO CORRECTAMENTE");
                    Alertas.error("Usuario no existe");
                } else {
                    System.out.println("Usuario modificado correctamente");
                    Alertas.info("Usuario modificado correctamente.");
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
            usuario = txtUsuario.getText();
            password = txtPassword.getText();
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
