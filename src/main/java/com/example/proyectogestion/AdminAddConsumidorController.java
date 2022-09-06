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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminAddConsumidorController implements Initializable {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnCrear;

    @FXML
    private Label lblTotal;

    @FXML
    private TableColumn clmCantidad;

    @FXML
    private TableColumn clmDescripcion;

    @FXML
    private TableColumn clmPrecio;

    @FXML
    private TableColumn clmTotal;

    @FXML
    private TableView<ProductoModel> tblProductos;

    @FXML
    private TextField txtFecha;

    @FXML
    private TextField txtNumfac;

    private Stage stage;
    private Scene scene;
    private Parent root;

    Conexion conexion = new Conexion();
    static String empleado;
    private int numfac;
    private String numfacFormatted;
    ObservableList<ProductoModel> listaProductos;

    final private String INSERT = "INSERT INTO cabfactura (CIEMPL, FECHAEMI) VALUES ((SELECT CIEMPL FROM usuarios WHERE NOMUSU = ?), curdate());";
    final private String SELECT_ID = "SELECT last_insert_id();";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listaProductos = FXCollections.observableArrayList();
        this.clmCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        this.clmDescripcion.setCellValueFactory(new PropertyValueFactory("detalle"));
        this.clmPrecio.setCellValueFactory(new PropertyValueFactory("valventa"));
        this.clmTotal.setCellValueFactory(new PropertyValueFactory("valtotal"));

        conexion.establecerConexion();
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conexion.getConnection().prepareStatement(INSERT);
            preparedStatement.setString(1, empleado);
            if (preparedStatement.executeUpdate() == 0) {
                System.out.println("NO SE HA INSERTADO LA CABFACTURA CORRECTAMENTE");
            } else {
                System.out.println("CABFACTURA INSERTADA CORRECTAMENTE");
            }
            statement = conexion.getConnection().createStatement();
            resultSet = statement.executeQuery(SELECT_ID);
            while(resultSet.next()){
                numfac = resultSet.getInt("last_insert_id()");
                numfacFormatted = String.format("%07d", numfac);
            }
        } catch (Exception e) {
            System.out.println("Ocurrio un error en el SQL");
            e.printStackTrace();
        } finally {
            PreparedStateCerrar.cerrarStatement(preparedStatement);
            PreparedStateCerrar.cerrarStatement(statement);
            PreparedStateCerrar.cerrarResult(resultSet);
        }
        conexion.cerrarConexion();

        txtFecha.setText(String.valueOf(java.time.LocalDate.now()));
        txtNumfac.setText(numfacFormatted);
    }

    public void initData(ObservableList<ProductoModel> lista, double valTotal){
        listaProductos = lista;
        tblProductos.setItems(listaProductos);
        BigDecimal valTotalBigDec = new BigDecimal(valTotal).setScale(2, RoundingMode.HALF_UP);
        double valTotalFormatted = valTotalBigDec.doubleValue();
        lblTotal.setText(String.valueOf(valTotalFormatted));

    }

    public static String getEmpleado() {
        return empleado;
    }

    public static void setEmpleado(String empleado) {
        AdminAddConsumidorController.empleado = empleado;
    }

    @FXML
    void atras(MouseEvent event) throws IOException {
        Optional<ButtonType> resultadoConfirm = Alertas.confirmation("¿Regresar al módulo de ventas?");
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