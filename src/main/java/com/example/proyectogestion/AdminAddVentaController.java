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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminAddVentaController implements Initializable {

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnConsumidor;

    @FXML
    private Button btnFactura;

    @FXML
    private TableColumn clmCantidadSelecc;

    @FXML
    private TableColumn clmCodigoBuscar;

    @FXML
    private TableColumn clmCodigoSelecc;

    @FXML
    private TableColumn clmDetalleBuscar;

    @FXML
    private TableColumn clmDetalleSelecc;

    @FXML
    private TableColumn clmNombreBuscar;

    @FXML
    private TableColumn clmNombreSelecc;

    @FXML
    private TableColumn clmValorTotalBuscar;

    @FXML
    private TableColumn clmValorTotalSelecc;

    @FXML
    private TableColumn clmValorUnitBuscar;

    @FXML
    private TableColumn clmValorUnitSelecc;

    @FXML
    private TableView<ProductoModel> tblBuscar;

    @FXML
    private TableView<ProductoModel> tblSelecc;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtNombre;

    private Stage stage;
    private Scene scene;
    private Parent root;

    Conexion conexion = new Conexion();
    private int cantidad;
    private double valtotalContador = 0;
    ObservableList<ProductoModel> listaProductosBusqueda;
    ObservableList<ProductoModel> listaProductosSeleccion;

    ArrayList<String> listaQueries = new ArrayList<>();

    final private String SELECT_ALL = "SELECT CODPROD, NOMPROD, DETPROD, VALVENTA, IVA FROM productos;";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listaProductosBusqueda = FXCollections.observableArrayList();
        listaProductosSeleccion = FXCollections.observableArrayList();
        this.clmCodigoBuscar.setCellValueFactory(new PropertyValueFactory("codigo"));
        this.clmNombreBuscar.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.clmDetalleBuscar.setCellValueFactory(new PropertyValueFactory("detalle"));
        this.clmValorUnitBuscar.setCellValueFactory(new PropertyValueFactory("valventa"));
        this.clmValorTotalBuscar.setCellValueFactory(new PropertyValueFactory("valtotal"));

        this.clmCodigoSelecc.setCellValueFactory(new PropertyValueFactory("codigo"));
        this.clmNombreSelecc.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.clmDetalleSelecc.setCellValueFactory(new PropertyValueFactory("detalle"));
        this.clmCantidadSelecc.setCellValueFactory(new PropertyValueFactory("cantidad"));
        this.clmValorUnitSelecc.setCellValueFactory(new PropertyValueFactory("valventa"));
        this.clmValorTotalSelecc.setCellValueFactory(new PropertyValueFactory("valtotal"));

        llenarTabla();

        FilteredList<ProductoModel> filteredData = new FilteredList<>(listaProductosBusqueda, b -> true);
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
                } else {
                    return false;
                }
            });
        });
        SortedList<ProductoModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblBuscar.comparatorProperty());
        tblBuscar.setItems(sortedData);
    }

    @FXML
    void agregar(MouseEvent event) {
        if(txtCantidad.getText().trim().isEmpty()){
            Alertas.error("Ingresar una cantidad.");
        } else {
            ProductoModel productoModel = tblBuscar.getSelectionModel().getSelectedItem();
            cantidad = Integer.parseInt(txtCantidad.getText());
            ProductoModel productosCompra = new ProductoModel(cantidad, productoModel.getValtotal(), productoModel.getValtotal(), productoModel.getNombre(), productoModel.getDetalle(), productoModel.getCodigo());

            listaQueries.add("INSERT INTO detfactura (NUMFAC, CODPROD, CANTIDAD, PRECIOTOTAL) VALUES (?," + productoModel.getCodigo() + "," + cantidad + "," + (productoModel.getValtotal() * cantidad) + ");");
            for(String s : listaQueries){
                System.out.println(s);
            }

            valtotalContador = valtotalContador + productosCompra.getValtotal();
            listaProductosSeleccion.add(productosCompra);
            tblSelecc.setItems(listaProductosSeleccion);
            txtCantidad.clear();
        }
    }

    @FXML
    void eliminar(MouseEvent event) {
        try{
            ObservableList<ProductoModel> tableItems = tblSelecc.getItems();
            ObservableList<ProductoModel> readOnlyItems = tblSelecc.getSelectionModel().getSelectedItems();

            readOnlyItems.stream().forEach((item) -> {
                tableItems.remove(item);
            });
        } catch (Exception e){
            System.out.println("Error raro que no se de que se trata ni de como arreglar");
        }
    }

    public void generarConsumidor(MouseEvent event) throws IOException {
        if(listaProductosSeleccion.isEmpty()){
            Alertas.error("Debe seleccionar al menos un producto para poder generar recibo.");
        } else {
            Optional<ButtonType> resultadoConfirm = Alertas.confirmation("¿Generar recibo sin datos?");
            if(resultadoConfirm.get() == ButtonType.OK){
                Alertas.info("Recibo generado correctamente.");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("AdminAddConsumidorView.fxml"));
                Parent tableViewParent = loader.load();

                Scene tableViewScene = new Scene(tableViewParent);

                AdminAddConsumidorController controller = loader.getController();
                controller.initData(listaProductosSeleccion, valtotalContador);

                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();
            } else if(resultadoConfirm.get() == ButtonType.CANCEL){

            }
        }
    }

    @FXML
    void generarFactura(MouseEvent event) throws IOException {
        if(listaProductosSeleccion.isEmpty()){
            Alertas.error("Debe seleccionar al menos un producto para poder generar factura.");
        } else {
            Optional<ButtonType> resultadoConfirm = Alertas.confirmation("¿Generar factura con datos?");
            if(resultadoConfirm.get() == ButtonType.OK){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("AdminAddFacturaView.fxml"));
                Parent tableViewParent = loader.load();

                Scene tableViewScene = new Scene(tableViewParent);

                AdminAddFacturaController controller = loader.getController();
                controller.initData(listaProductosSeleccion, valtotalContador, listaQueries);

                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();
            } else if(resultadoConfirm.get() == ButtonType.CANCEL){

            }
        }
    }

    public void llenarTabla(){
        conexion.establecerConexion();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conexion.getConnection().createStatement();
            resultSet = statement.executeQuery(SELECT_ALL);

            while (resultSet.next()){
                listaProductosBusqueda.add(convertir(resultSet));
            }
            tblBuscar.setItems(listaProductosBusqueda);
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
        double valVentaAux = resultSet.getDouble("VALVENTA");
        String nombreAux = resultSet.getString("NOMPROD");
        String detalleAux = resultSet.getString("DETPROD");
        int ivaAux = resultSet.getInt("IVA");
        double valTotal = valVentaAux;
        if(ivaAux == 1){
            valTotal = valVentaAux * 1.12;
        } else if(ivaAux == 0){
            valTotal = valVentaAux;
        }
        BigDecimal valTotalBigDec = new BigDecimal(valTotal).setScale(2, RoundingMode.HALF_UP);
        double valTotalFormatted = valTotalBigDec.doubleValue();
        ProductoModel productoAux = new ProductoModel(valVentaAux, valTotalFormatted, nombreAux, detalleAux, codigoFormatted);
        return productoAux;
    }

    @FXML
    void atras(MouseEvent event) throws IOException {
        Optional<ButtonType> resultadoConfirm = Alertas.confirmation("Se perderán todos los datos de esta venta. ¿Está seguro que desea salir del módulo de ventas?");
        if(resultadoConfirm.get() == ButtonType.OK){
            root = FXMLLoader.load(getClass().getResource("ModuloVentasView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if(resultadoConfirm.get() == ButtonType.CANCEL){

        }
    }
}
