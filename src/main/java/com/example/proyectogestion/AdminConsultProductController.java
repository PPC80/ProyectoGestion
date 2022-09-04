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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminConsultProductController implements Initializable {

    @FXML
    private Button btnAtras;

    @FXML
    private TableColumn clmCodigo;

    @FXML
    private TableColumn clmDetalle;

    @FXML
    private TableColumn clmIva;

    @FXML
    private TableColumn clmNombre;

    @FXML
    private TableColumn clmStock;

    @FXML
    private TableColumn clmValCompra;

    @FXML
    private TableColumn clmValVenta;

    @FXML
    private TableColumn clmCategoria;

    @FXML
    private TableView<ProductoModel> tblProductos;

    @FXML
    private TextField txtNombre;

    private Stage stage;
    private Scene scene;
    private Parent root;

    Conexion conexion = new Conexion();
    ObservableList<ProductoModel> listaProductos;
    final private String SELECT_ALL = "SELECT pr.CODPROD, pr.NOMPROD, ca.NOMCAT, pr.DETPROD, pr.STOCK, pr.VALCOMPRA, pr.VALVENTA, pr.IVA FROM productos pr, categorias ca WHERE pr.IDCAT = ca.IDCAT;";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listaProductos = FXCollections.observableArrayList();
        this.clmCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        this.clmNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.clmCategoria.setCellValueFactory(new PropertyValueFactory("categoria"));
        this.clmDetalle.setCellValueFactory(new PropertyValueFactory("detalle"));
        this.clmStock.setCellValueFactory(new PropertyValueFactory("stock"));
        this.clmValCompra.setCellValueFactory(new PropertyValueFactory("valcompra"));
        this.clmValVenta.setCellValueFactory(new PropertyValueFactory("valventa"));
        this.clmIva.setCellValueFactory(new PropertyValueFactory("iva"));

        llenarTabla();

        FilteredList<ProductoModel> filteredData = new FilteredList<>(listaProductos, b -> true);
        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(productoModel -> {

                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(productoModel.getNombre().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if(productoModel.getCodigo().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if(productoModel.getDetalle().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if(productoModel.getCategoria().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<ProductoModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblProductos.comparatorProperty());
        tblProductos.setItems(sortedData);
    }

    public void llenarTabla(){
        conexion.establecerConexion();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conexion.getConnection().createStatement();
            resultSet = statement.executeQuery(SELECT_ALL);

            while (resultSet.next()){
                listaProductos.add(convertir(resultSet));
            }
            tblProductos.setItems(listaProductos);
        } catch (Exception e) {
            System.out.println("Ocurrio un error en el SQL");
            e.printStackTrace();
        } finally {
            PreparedStateCerrar.cerrarStatement(statement);
            PreparedStateCerrar.cerrarResult(resultSet);
        }
        conexion.cerrarConexion();
    }

    public ProductoModel convertir(ResultSet resultSet) throws SQLException {
        int codigoAux = resultSet.getInt("CODPROD");
        String codigoFormatted = String.format("%06d", codigoAux);
        int stockAux = resultSet.getInt("STOCK");
        double valCompraAux = resultSet.getDouble("VALCOMPRA");
        double valVentaAux = resultSet.getDouble("VALVENTA");
        String nombreAux = resultSet.getString("NOMPROD");
        String detalleAux = resultSet.getString("DETPROD");
        String categoriaAux = resultSet.getString("NOMCAT");
        String ivaAux = String.valueOf(resultSet.getInt("IVA"));
        String ivaFormatted = " ";
        if(ivaAux.equals("0")){
            ivaFormatted = "No";
        } else if(ivaAux.equals("1")){
            ivaFormatted = "Si";
        }
        ProductoModel productoAux = new ProductoModel(codigoFormatted, stockAux, valCompraAux, valVentaAux, nombreAux, detalleAux, categoriaAux, ivaFormatted);
        return productoAux;
    }

    @FXML
    void atras(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminGestionProductosView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
