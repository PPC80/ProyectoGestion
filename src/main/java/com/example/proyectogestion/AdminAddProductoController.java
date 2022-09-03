package com.example.proyectogestion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminAddProductoController implements Initializable {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnRegistrar;

    @FXML
    private TextField txtCategoria;

    @FXML
    private TextField txtDetalle;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtStock;

    @FXML
    private TextField txtValCompra;

    @FXML
    private TextField txtValVenta;

    @FXML
    private RadioButton rbtnNo;

    @FXML
    private RadioButton rbtnSi;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String nombre, detalle, nomcat;
    private int stock, idcat, iva;
    private double valcompra, valventa;
    private boolean retomarDatos = false;
    private Conexion conexion = new Conexion();
    private final String INSERT_PRODUCTO = "INSERT INTO productos (IDCAT, NOMPROD, DETPROD, STOCK, VALCOMPRA, VALVENTA, IVA) VALUES (?,?,?,?,?,?,?);";
    private final String CONSULTA_CATEGORIA = "SELECT IDCAT FROM categorias WHERE NOMCAT = ?;";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        rbtnSi.setToggleGroup(toggleGroup);
        rbtnNo.setToggleGroup(toggleGroup);
    }

    @FXML
    void registrar(MouseEvent event) throws IOException {
        boolean success = false;
        tomarDatos();
        if(retomarDatos){
            Alertas.error("Por favor llenar todos los campos con el formato adecuado");
        } else {
            conexion.establecerConexion();
            PreparedStatement preparedStatement = null;
            PreparedStatement preparedStatement2 = null;
            ResultSet resultSet = null;
            try {
                preparedStatement2 = conexion.getConnection().prepareStatement(CONSULTA_CATEGORIA);
                preparedStatement2.setString(1, nomcat);
                resultSet = preparedStatement2.executeQuery();
                if(resultSet.next()){
                    idcat = resultSet.getInt("IDCAT");

                    preparedStatement = conexion.getConnection().prepareStatement(INSERT_PRODUCTO);
                    preparedStatement.setInt(1, idcat);
                    preparedStatement.setString(2, nombre);
                    preparedStatement.setString(3, detalle);
                    preparedStatement.setInt(4, stock);
                    preparedStatement.setDouble(5, valcompra);
                    preparedStatement.setDouble(6, valventa);
                    if(rbtnSi.isSelected()){
                        iva = 1;
                    } else if(rbtnNo.isSelected()){
                        iva = 0;
                    }
                    preparedStatement.setInt(7, iva);
                    if (preparedStatement.executeUpdate() == 0) {
                        System.out.println("NO SE HA INSERTADO AL PRODUCTO CORRECTAMENTE");
                    } else {
                        success = true;
                        System.out.println("PRODUCTO INSERTADO CORRECTAMENTE");
                        Alertas.info("Producto registrado correctamente.");
                    }
                } else {
                    Alertas.error("Categoria no existe.");
                }
            } catch (Exception e) {
                System.out.println("ERROR CON LA SENTENCIA SQL...");
                Alertas.error("Datos mal ingresados, o ese producto ya se encuentra registrado...");
            } finally {
                PreparedStateCerrar.cerrarStatement(preparedStatement);
                PreparedStateCerrar.cerrarStatement(preparedStatement2);
                PreparedStateCerrar.cerrarResult(resultSet);
            }
            conexion.cerrarConexion();

            if(success){
                root = FXMLLoader.load(getClass().getResource("AdminGestionProductosView.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("AdminAddProductoView.fxml"));
            }
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void atras(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminGestionProductosView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void tomarDatos(){
        try{
            retomarDatos = false;
            nomcat = txtCategoria.getText();
            nombre = txtNombre.getText();
            detalle = txtDetalle.getText();
            stock = Integer.parseInt(txtStock.getText());
            valcompra = Double.parseDouble(txtValCompra.getText());
            valventa = Double.parseDouble(txtValVenta.getText());
        } catch(NumberFormatException e){
            retomarDatos = true;
        }
    }
}

