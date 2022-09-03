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

public class AdminConsultProveedorController  implements Initializable {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnConsultar;

    @FXML
    private TableColumn clmDireccion;

    @FXML
    private TableColumn clmEmail;

    @FXML
    private TableColumn clmNombre;

    @FXML
    private TableColumn clmRuc;

    @FXML
    private TableColumn clmTelefono;

    @FXML
    private Label lblDireccion;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblRuc;

    @FXML
    private Label lblTelefono;

    @FXML
    private TableView<ProveedorModel> tblProveedores;

    @FXML
    private TextField txtNombre;

    private Stage stage;
    private Scene scene;
    private Parent root;

    Conexion conexion = new Conexion();
    ObservableList<ProveedorModel> listaProveedores;
    final private String SELECT_ALL = "SELECT * FROM proveedores;";
    final private String SELECT_CONSULT = "SELECT * FROM proveedores WHERE NOMPROV LIKE ?;";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaProveedores = FXCollections.observableArrayList();
        this.clmRuc.setCellValueFactory(new PropertyValueFactory("ruc"));
        this.clmNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.clmTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        this.clmDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));
        this.clmEmail.setCellValueFactory(new PropertyValueFactory("email"));

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
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                lblRuc.setText(resultSet.getString("RUCPROV"));
                lblNombre.setText(resultSet.getString("NOMPROV"));
                lblTelefono.setText((resultSet.getString("TLFPROV")));
                lblDireccion.setText(resultSet.getString("DIRPROV"));
                lblEmail.setText((resultSet.getString("MAILPROV")));
            } else {
                Alertas.error("Proveedor no registrado.");
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
                listaProveedores.add(convertir(resultSet));
            }
            tblProveedores.setItems(listaProveedores);
            tblProveedores.refresh();
        } catch (Exception e) {
            System.out.println("Ocurrio un error en el SQL");
            e.printStackTrace();
        } finally {
            PreparedStateCerrar.cerrarStatement(statement);
            PreparedStateCerrar.cerrarResult(resultSet);
        }
        conexion.cerrarConexion();
    }

    public ProveedorModel convertir(ResultSet resultSet) throws SQLException {
        String rucAux = resultSet.getString("RUCPROV");
        String nombreAux = resultSet.getString("NOMPROV");
        String telefonoAux = resultSet.getString("TLFPROV");
        String direccionAux = resultSet.getString("DIRPROV");
        String emailAux = resultSet.getString("MAILPROV");
        ProveedorModel proveedorAux = new ProveedorModel(rucAux, nombreAux, telefonoAux, direccionAux, emailAux);
        return proveedorAux;
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
