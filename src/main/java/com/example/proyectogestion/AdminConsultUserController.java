package com.example.proyectogestion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminConsultUserController implements Initializable {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnConsultar;

    @FXML
    private TableColumn clmApellido;

    @FXML
    private TableColumn clmCedula;

    @FXML
    private TableColumn clmEmail;

    @FXML
    private TableColumn clmNombre;

    @FXML
    private TableColumn clmPassword;

    @FXML
    private TableColumn clmUsuario;

    @FXML
    private TableView<EmpleadoModel> tblEmpleados;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtNombre;

    @FXML
    private Label lblApellido;

    @FXML
    private Label lblCedula;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblUsuario;

    private Stage stage;
    private Scene scene;
    private Parent root;

    Conexion conexion = new Conexion();
    ObservableList<EmpleadoModel> listaEmpleados;
    final private String SELECT_ALL = "SELECT emp.CIEMPL, emp.NOMEMPL, emp.APELEMPL, emp.MAILEMPL, usu.NOMUSU, usu.PASSUSU FROM empleados as emp LEFT JOIN usuarios as usu ON emp.CIEMPL = usu.CIEMPL;";
    final private String SELECT_CONSULT = "SELECT emp.CIEMPL, emp.NOMEMPL, emp.APELEMPL, emp.MAILEMPL, usu.NOMUSU, usu.PASSUSU FROM empleados as emp LEFT JOIN usuarios as usu ON emp.CIEMPL = usu.CIEMPL WHERE emp.NOMEMPL LIKE ? AND APELEMPL LIKE ?;";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaEmpleados = FXCollections.observableArrayList();
        this.clmCedula.setCellValueFactory(new PropertyValueFactory("cedula"));
        this.clmNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.clmApellido.setCellValueFactory(new PropertyValueFactory("apellido"));
        this.clmEmail.setCellValueFactory(new PropertyValueFactory("email"));
        this.clmUsuario.setCellValueFactory(new PropertyValueFactory("usuario"));
        this.clmPassword.setCellValueFactory(new PropertyValueFactory("password"));

        llenarTabla();
    }

    @FXML
    void consultar(MouseEvent event) {
        conexion.establecerConexion();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = conexion.getConnection().prepareStatement(SELECT_CONSULT);
            preparedStatement.setString(1, txtNombre.getText() + "%");
            preparedStatement.setString(2, txtApellido.getText() + "%");
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                lblCedula.setText(String.valueOf(resultSet.getInt("CIEMPL")));
                lblNombre.setText(resultSet.getString("NOMEMPL"));
                lblApellido.setText((resultSet.getString("APELEMPL")));
                lblEmail.setText(resultSet.getString("MAILEMPL"));
                lblUsuario.setText((resultSet.getString("NOMUSU")));
                lblPassword.setText((resultSet.getString("PASSUSU")));
            } else {
                Alertas.error("Empleado no registrado.");
            }
        } catch (Exception e){
            System.out.println("Ocurrio un error en el SQL");
            e.printStackTrace();
        } finally {
            PreparedStateCerrar.cerrarStatement(preparedStatement);
            PreparedStateCerrar.cerrarResult(resultSet);
        }
        conexion.cerrarConexion();
    }

    public void llenarTabla(){
        conexion.establecerConexion();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conexion.getConnection().createStatement();
            resultSet = statement.executeQuery(SELECT_ALL);

            while (resultSet.next()){
                listaEmpleados.add(convertir(resultSet));
            }
            tblEmpleados.setItems(listaEmpleados);
            tblEmpleados.refresh();
        } catch (Exception e) {
            System.out.println("Ocurrio un error en el SQL");
            e.printStackTrace();
        } finally {
            PreparedStateCerrar.cerrarStatement(statement);
            PreparedStateCerrar.cerrarResult(resultSet);
        }
        conexion.cerrarConexion();
    }

    public EmpleadoModel convertir(ResultSet resultSet) throws SQLException {
        int cedulaAux = resultSet.getInt("CIEMPL");
        String nombreAux = resultSet.getString("NOMEMPL");
        String apellidosAux = resultSet.getString("APELEMPL");
        String emailAux = resultSet.getString("MAILEMPL");
        String usuarioAux = resultSet.getString("NOMUSU");
        String passwordAux = resultSet.getString("PASSUSU");
        EmpleadoModel empleadoAux = new EmpleadoModel(cedulaAux, nombreAux, apellidosAux, emailAux, usuarioAux, passwordAux);
        return empleadoAux;
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
