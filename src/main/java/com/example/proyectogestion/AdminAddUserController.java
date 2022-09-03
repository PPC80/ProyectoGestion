package com.example.proyectogestion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminAddUserController implements Initializable {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnRegistrar;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtCedula;

    @FXML
    private RadioButton rbtnAdmin;

    @FXML
    private RadioButton rbtnEmpleado;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private String usuario, password;
    private int cedula, idrol;
    private boolean retomarDatos = false;
    private Conexion conexion = new Conexion();
    private final String INSERT_USUARIO = "INSERT INTO usuarios (CIEMPL, IDROL, NOMUSU, PASSUSU) VALUES (?,?,?,?);";
    private final String BUSCAR_CEDULA = "SELECT CIEMPL FROM empleados WHERE CIEMPL = ?;";
    private final String BUSCAR_CEDULA_USU = "SELECT CIEMPL FROM usuarios WHERE CIEMPL = ?;";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        rbtnEmpleado.setToggleGroup(toggleGroup);
        rbtnAdmin.setToggleGroup(toggleGroup);
    }

    @FXML
    void registrar(MouseEvent event) throws IOException {
        boolean success = false;
        boolean usu_no_existe = false;
        tomarDatos();
        conexion.establecerConexion();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        //Verifica si el empleado se encuentra o no registrado
        try {
            preparedStatement = conexion.getConnection().prepareStatement(BUSCAR_CEDULA);
            preparedStatement.setInt(1, cedula);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                System.out.println("Cedula encontrada");
            } else {
                System.out.println("Cedula no econtrada");
                Alertas.error("Debe primero registrar al empleado.");
                retomarDatos = true;
                usu_no_existe = true;
            }
        } catch (Exception e) {
            System.out.println("ERROR CON LA SENTENCIA SQL...");
        } finally {
            PreparedStateCerrar.cerrarStatement(preparedStatement);
            PreparedStateCerrar.cerrarResult(resultSet);
        }

        //Verifica si el usuario ya existe
        try {
            preparedStatement = conexion.getConnection().prepareStatement(BUSCAR_CEDULA_USU);
            preparedStatement.setInt(1, cedula);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                System.out.println("Usuario ya existe");
                Alertas.error("Ese empleado ya tiene un usuario registrado");
                retomarDatos = true;
                usu_no_existe = true;
            } else {
                System.out.println("Usuario no existe, puede registrar");
            }
        } catch (Exception e) {
            System.out.println("ERROR CON LA SENTENCIA SQL...");
        } finally {
            PreparedStateCerrar.cerrarStatement(preparedStatement);
            PreparedStateCerrar.cerrarResult(resultSet);
        }

        //Si el empleado se encuentra registrado, permite la creacion del usuario
        if(retomarDatos && !usu_no_existe){
            Alertas.error("Por favor llenar todos los campos con el formato adecuado");
        } else if (!retomarDatos && !usu_no_existe){
            try {
                preparedStatement2 = conexion.getConnection().prepareStatement(INSERT_USUARIO);
                preparedStatement2.setInt(1, cedula);
                if(rbtnEmpleado.isSelected()){
                    idrol = 2;
                } else if (rbtnAdmin.isSelected()){
                    idrol = 1;
                }
                preparedStatement2.setInt(2, idrol);
                preparedStatement2.setString(3, usuario);
                preparedStatement2.setString(4, password);
                if (preparedStatement2.executeUpdate() == 0) {
                    System.out.println("NO SE HA INSERTADO AL USUARIO CORRECTAMENTE");
                } else {
                    success = true;
                    System.out.println("USUARIO INSERTADO CORRECTAMENTE");
                    Alertas.info("Usuario registrado correctamente.");
                }
            } catch (Exception e) {
                System.out.println("ERROR CON LA SENTENCIA SQL...");
                e.printStackTrace();
                Alertas.error("Usuario no registrado...");
            } finally {
                PreparedStateCerrar.cerrarStatement(preparedStatement2);
            }
            conexion.cerrarConexion();

            if(success){
                root = FXMLLoader.load(getClass().getResource("AdminGestionEmpleadosView.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("AdminAddUserView.fxml"));
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
            cedula = Integer.parseInt(txtCedula.getText());
            usuario = txtUsuario.getText();
            password = txtPassword.getText();
        } catch(NumberFormatException e){
            retomarDatos = true;
        }
    }
}

