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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminAddFacturaController implements Initializable {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnGenerarFactura;

    @FXML
    private TableColumn clmCantidad;

    @FXML
    private TableColumn clmDescripcion;

    @FXML
    private TableColumn clmPrecio;

    @FXML
    private TableColumn clmTotal;

    @FXML
    private Label lblTotal;

    @FXML
    private TableView<ProductoModel> tblProductos;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFecha;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtNumfac;

    @FXML
    private TextField txtTefefono;

    private Stage stage;
    private Scene scene;
    private Parent root;

    Conexion conexion = new Conexion();
    private int cedula, telefono;
    private String nombre, apeliido, direccion, email;
    private boolean retomarDatos = false;
    static String empleado;
    private int numfac;
    private String numfacFormatted;
    ObservableList<ProductoModel> listaProductos;
    ArrayList<String> listaQueriesBDD = new ArrayList<>();

    final private String INSERT = "INSERT INTO cabfactura (CIEMPL, FECHAEMI) VALUES ((SELECT CIEMPL FROM usuarios WHERE NOMUSU = ?), curdate());";
    final private String SELECT_ID = "SELECT last_insert_id();";
    final private String UPDATE = "UPDATE cabfactura SET CICLI = ? WHERE NUMFAC = ?;";
    final private String INSERT_CLIENTE = "INSERT INTO clientes VALUES (?,?,?,?,?,?);";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listaProductos = FXCollections.observableArrayList();
        this.clmCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        this.clmDescripcion.setCellValueFactory(new PropertyValueFactory("detalle"));
        this.clmPrecio.setCellValueFactory(new PropertyValueFactory("valventa"));
        this.clmTotal.setCellValueFactory(new PropertyValueFactory("valtotal"));

        conexion.establecerConexion();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Statement statement = null;
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

    public void initData(ObservableList<ProductoModel> lista, double valTotal, ArrayList<String> listaQueries){
        listaQueriesBDD = listaQueries;

        //Añadir detalles de factura a BDD
        conexion.establecerConexion();
        PreparedStatement preparedStatementBDD = null;
        try {
            for(String s : listaQueriesBDD){
                preparedStatementBDD = conexion.getConnection().prepareStatement(s);
                preparedStatementBDD.setString(1, numfacFormatted);
                if (preparedStatementBDD.executeUpdate() == 0) {
                    System.out.println("NO SE HA INSERTADO LA DETFACTURA CORRECTAMENTE");
                } else {
                    System.out.println("DETFACTURA INSERTADA CORRECTAMENTE");
                }
            }
        } catch (Exception e) {
            System.out.println("Ocurrio un error en el SQL");
            e.printStackTrace();
        } finally {
            PreparedStateCerrar.cerrarStatement(preparedStatementBDD);
        }
        conexion.cerrarConexion();


        listaProductos = lista;
        tblProductos.setItems(listaProductos);
        BigDecimal valTotalBigDec = new BigDecimal(valTotal).setScale(2, RoundingMode.HALF_UP);
        double valTotalFormatted = valTotalBigDec.doubleValue();
        lblTotal.setText(String.valueOf(valTotalFormatted));
    }

    @FXML
    void generarFactura(MouseEvent event) throws IOException {
        tomarDatos();
        if(retomarDatos){
            Alertas.error("Por favor llenar todos los campos con el formato adecuado");
        } else {
            conexion.establecerConexion();
            PreparedStatement preparedStatement = null;
            PreparedStatement preparedStatement2 = null;
            try {
                preparedStatement = conexion.getConnection().prepareStatement(INSERT_CLIENTE);
                preparedStatement.setInt(1, cedula);
                preparedStatement.setString(2, nombre);
                preparedStatement.setString(3, apeliido);
                preparedStatement.setString(4, direccion);
                preparedStatement.setInt(5, telefono);
                preparedStatement.setString(6, email);
                if (preparedStatement.executeUpdate() == 0) {
                    System.out.println("NO SE HA INSERTADO AL CLIENTE CORRECTAMENTE");
                } else {
                    System.out.println("CLIENTE INSERTADO CORRECTAMENTE");
                }
                preparedStatement2 = conexion.getConnection().prepareStatement(UPDATE);
                preparedStatement2.setInt(1, cedula);
                preparedStatement2.setString(2, numfacFormatted);
                if (preparedStatement2.executeUpdate() == 0) {
                    System.out.println("NO SE HA MODIFICADO LA CABFACTURA CORRECTAMENTE");
                } else {
                    System.out.println("CABFACTURA MODIFICADA CORRECTAMENTE");
                    Alertas.info("Factura generada correctamente.");
                    root = FXMLLoader.load(getClass().getResource("ModuloVentasView.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            } catch (Exception e) {
                System.out.println("ERROR CON LA SENTENCIA SQL... CLIENTE PUEDE QUE YA ESTE REGISTRADO");
                e.printStackTrace();
            } finally {
                PreparedStateCerrar.cerrarStatement(preparedStatement);
                PreparedStateCerrar.cerrarStatement(preparedStatement2);
            }
            conexion.cerrarConexion();
        }
    }

    public void tomarDatos(){
        try{
            retomarDatos = false;
            nombre = txtNombre.getText();
            apeliido = txtApellido.getText();
            direccion = txtDireccion.getText();
            email = txtEmail.getText();
            cedula = Integer.parseInt(txtCedula.getText());
            telefono = Integer.parseInt(txtTefefono.getText());
        } catch(NumberFormatException e){
            retomarDatos = true;
        }
    }

    public static String getEmpleado() {
        return empleado;
    }

    public static void setEmpleado(String empleado) {
        AdminAddFacturaController.empleado = empleado;
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

