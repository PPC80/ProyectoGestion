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
import java.util.Date;
import java.util.ResourceBundle;

public class ConsultarFacturaController implements Initializable {

    @FXML
    private Button btnAtras;

    @FXML
    private TableColumn clmCantidad;

    @FXML
    private TableColumn clmCliente;

    @FXML
    private TableColumn clmCodigo;

    @FXML
    private TableColumn clmEmpleado;

    @FXML
    private TableColumn clmFecha;

    @FXML
    private TableColumn clmNumfac;

    @FXML
    private TableColumn clmValtotal;

    @FXML
    private TableView<FacturaModel> tblFacturas;

    @FXML
    private TextField txtNumfac;

    private Stage stage;
    private Scene scene;
    private Parent root;

    Conexion conexion = new Conexion();
    ObservableList<FacturaModel> listaFacturas;
    private final String SELECT = "SELECT cf.NUMFAC, cf.CIEMPL, cf.CICLI, df.CODPROD, df.CANTIDAD, df.PRECIOTOTAL, cf.FECHAEMI FROM cabfactura cf, detfactura df WHERE cf.NUMFAC = df.NUMFAC;";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listaFacturas = FXCollections.observableArrayList();
        this.clmNumfac.setCellValueFactory(new PropertyValueFactory("numfac"));
        this.clmEmpleado.setCellValueFactory(new PropertyValueFactory("ciempl"));
        this.clmCliente.setCellValueFactory(new PropertyValueFactory("cicli"));
        this.clmCodigo.setCellValueFactory(new PropertyValueFactory("codprod"));
        this.clmCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        this.clmValtotal.setCellValueFactory(new PropertyValueFactory("valtotal"));
        this.clmFecha.setCellValueFactory(new PropertyValueFactory("fechaemi"));

        llenarTabla();

        FilteredList<FacturaModel> filteredData = new FilteredList<>(listaFacturas, b -> true);
        txtNumfac.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(facturaModel -> {

                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(String.valueOf(facturaModel.getNumfac()).toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if(String.valueOf(facturaModel.getCiempl()).toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if(String.valueOf(facturaModel.getCicli()).toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if(String.valueOf(facturaModel.getCodprod()).toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else if(String.valueOf(facturaModel.getFechaemi()).toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<FacturaModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblFacturas.comparatorProperty());
        tblFacturas.setItems(sortedData);
    }

    public void llenarTabla(){
        conexion.establecerConexion();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conexion.getConnection().createStatement();
            resultSet = statement.executeQuery(SELECT);

            while (resultSet.next()){
                listaFacturas.add(convertir(resultSet));
            }
            tblFacturas.setItems(listaFacturas);
        } catch (Exception e) {
            System.out.println("Ocurrio un error en el SQL");
            e.printStackTrace();
        } finally {
            PreparedStateCerrar.cerrarStatement(statement);
            PreparedStateCerrar.cerrarResult(resultSet);
        }
        conexion.cerrarConexion();
    }

    public FacturaModel convertir(ResultSet resultSet) throws SQLException {
        int numfacAux = resultSet.getInt("NUMFAC");
        int ciemplAux = resultSet.getInt("CIEMPL");
        int cicliAux = resultSet.getInt("CICLI");
        int codprodAux = resultSet.getInt("CODPROD");
        int cantidadAux = resultSet.getInt("CANTIDAD");
        double preciototalAux = resultSet.getDouble("PRECIOTOTAL");
        Date fechaemiAux = resultSet.getDate(("FECHAEMI"));
        String codigoFormatted = String.format("%06d", codprodAux);
        String numfacFormatted = String.format("%07d", numfacAux);
        FacturaModel facturaAux = new FacturaModel(numfacFormatted, ciemplAux, cicliAux, codigoFormatted, cantidadAux, preciototalAux, fechaemiAux);
        return facturaAux;
    }

    @FXML
    void atras(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ModuloFacturacionView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

