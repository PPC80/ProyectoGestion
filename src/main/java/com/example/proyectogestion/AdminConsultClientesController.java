package com.example.proyectogestion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminConsultClientesController implements Initializable {

    @FXML
    private Button btnAtras;

    @FXML
    private TableColumn clmApellido;

    @FXML
    private TableColumn clmCedula;

    @FXML
    private TableColumn clmDireccion;

    @FXML
    private TableColumn clmEmail;

    @FXML
    private TableColumn clmNombre;

    @FXML
    private TableColumn clmTelefono;

    @FXML
    private TableView<ClienteModel> tblClientes;

    @FXML
    private TextField txtCliente;

    private Stage stage;
    private Scene scene;
    private Parent root;

    Conexion conexion = new Conexion();
    ObservableList<ClienteModel> listaClientes;
    private final String SELECT = "SELECT * from clientes;";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listaClientes = FXCollections.observableArrayList();
        this.clmCedula.setCellValueFactory(new PropertyValueFactory("cedula"));
        this.clmNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.clmApellido.setCellValueFactory(new PropertyValueFactory("apellido"));
        this.clmDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));
        this.clmTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        this.clmEmail.setCellValueFactory(new PropertyValueFactory("email"));

        llenarTabla();

        FilteredList<ClienteModel> filteredData = new FilteredList<>(listaClientes, b -> true);
        txtCliente.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(clienteModel -> {

                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(String.valueOf(clienteModel.getCedula()).toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if(clienteModel.getNombre().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if(clienteModel.getApellido().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if(clienteModel.getDireccion().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<ClienteModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblClientes.comparatorProperty());
        tblClientes.setItems(sortedData);
    }

    public void llenarTabla(){
        conexion.establecerConexion();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conexion.getConnection().createStatement();
            resultSet = statement.executeQuery(SELECT);

            while (resultSet.next()){
                listaClientes.add(convertir(resultSet));
            }
            tblClientes.setItems(listaClientes);
        } catch (Exception e) {
            System.out.println("Ocurrio un error en el SQL");
            e.printStackTrace();
        } finally {
            PreparedStateCerrar.cerrarStatement(statement);
            PreparedStateCerrar.cerrarResult(resultSet);
        }
        conexion.cerrarConexion();
    }

    public ClienteModel convertir(ResultSet resultSet) throws SQLException {
        int cedulaAux = resultSet.getInt("CICLI");
        int telefonoAux = resultSet.getInt("TLFCLI");
        String nombreAux = resultSet.getString("NOMCLI");
        String apellidoAux = resultSet.getString("APELCLI");
        String direccionAux = resultSet.getString("DIRCLI");
        String emailAux = resultSet.getString("MAILCLI");
        ClienteModel clienteAux = new ClienteModel(cedulaAux, telefonoAux, nombreAux, apellidoAux, direccionAux, emailAux);
        return clienteAux;
    }

    @FXML
    void atras(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ModuloVentasView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

