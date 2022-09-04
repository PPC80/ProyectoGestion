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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminModProductoController implements Initializable {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnModificar;

    @FXML
    private RadioButton rbtnNo;

    @FXML
    private RadioButton rbtnSi;

    @FXML
    private TextField txtCategoria;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtDetalle;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtValCompra;

    @FXML
    private TextField txtValVenta;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Conexion conexion = new Conexion();
    private boolean retomarDatos = false;
    private int codigo, idcat, iva;
    private double valcompra, valventa;
    private String nombre, detalle, categoria;
    private final String UPDATE = "UPDATE productos SET IDCAT = ?, NOMPROD = ?, DETPROD = ?, VALCOMPRA = ?, VALVENTA = ?, IVA = ? WHERE CODPROD = ?;";
    private final String CONSULTA_CATEGORIA = "SELECT IDCAT FROM categorias WHERE NOMCAT = ?;";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        rbtnSi.setToggleGroup(toggleGroup);
        rbtnNo.setToggleGroup(toggleGroup);
    }

    @FXML
    void modificar(MouseEvent event) {
        tomarDatos();
        if(retomarDatos){
            Alertas.error("Por favor llenar todos los campos con el formato adecuado");
        } else {
            conexion.establecerConexion();
            PreparedStatement preparedStatement = null;
            PreparedStatement preparedStatement2 = null;
            ResultSet resultSet = null;
            try{
                preparedStatement2 = conexion.getConnection().prepareStatement(CONSULTA_CATEGORIA);
                preparedStatement2.setString(1, categoria);
                resultSet = preparedStatement2.executeQuery();
                if(resultSet.next()){
                    idcat = resultSet.getInt("IDCAT");

                    preparedStatement = conexion.getConnection().prepareStatement(UPDATE);
                    preparedStatement.setInt(1, idcat);
                    preparedStatement.setString(2, nombre);
                    preparedStatement.setString(3, detalle);
                    preparedStatement.setDouble(4, valcompra);
                    preparedStatement.setDouble(5, valventa);
                    if(rbtnSi.isSelected()){
                        iva = 1;
                    } else if(rbtnNo.isSelected()){
                        iva = 0;
                    }
                    preparedStatement.setInt(6, iva);
                    preparedStatement.setInt(7, codigo);
                    if(preparedStatement.executeUpdate() == 0){
                        System.out.println("NO SE HA MODIFICADO CORRECTAMENTE");
                        Alertas.error("Producto no existe");
                    } else {
                        System.out.println("Producto modificado correctamente");
                        Alertas.info("Producto modificado correctamente.");
                    }
                } else {
                    Alertas.error("Categoria no existe.");
                }
            } catch (SQLException e) {
                System.out.println("Error con el SQL");
                Alertas.error("Datos mal ingresados, o ese producto ya se encuentra registrado...");
            } finally {
                PreparedStateCerrar.cerrarStatement(preparedStatement);
                PreparedStateCerrar.cerrarStatement(preparedStatement2);
                PreparedStateCerrar.cerrarResult(resultSet);
            }
            conexion.cerrarConexion();
        }
    }

    public void tomarDatos(){
        try{
            retomarDatos = false;
            codigo = Integer.parseInt(txtCodigo.getText());
            categoria = txtCategoria.getText();
            nombre = txtNombre.getText();
            detalle = txtDetalle.getText();
            valcompra = Double.parseDouble(txtValCompra.getText());
            valventa = Double.parseDouble(txtValVenta.getText());
        } catch(NumberFormatException e){
            retomarDatos = true;
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
}

