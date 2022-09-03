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
//        Image myLogin = new Image(getClass().getResourceAsStream("/com/login.png"));
//        imgLogin.setImage(myLogin);
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
//                    PerfilControlador.usuario = txtUsuario.getText();
//                    PerfilControlador.password = txtPassword.getText();
                }
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
//        Stage stage = (Stage) BtnLoginAdmin.getScene().getWindow();
//        stage.close();

        if(admin_o_empleado == 1){
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
//            Parent root = loader.load();
//            AdminController controlador = loader.getController();
//            Scene scene = new Scene(root);
//            stage = new Stage();
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.setScene(scene);
//            stage.showAndWait();

//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
//            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
//            stage.setTitle("Stock Management");
//            stage.setScene(scene);
//            stage.show();

            root = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else if(admin_o_empleado == 2){
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("indexView.fxml"));
//            Parent root = loader.load();
//            //SidePanelControlador controlador = loader.getController();
//            Scene scene = new Scene(root);
//            stage = new Stage();
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.setScene(scene);
//            stage.showAndWait();

//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("indexView.fxml"));
//            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
//            stage.setTitle("Stock Management");
//            stage.setScene(scene);
//            stage.show();

            root = FXMLLoader.load(getClass().getResource("indexView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
    }
}
