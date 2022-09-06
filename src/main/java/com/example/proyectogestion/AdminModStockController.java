package com.example.proyectogestion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminModStockController {

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnRegistrar;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtStock;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Conexion conexion = new Conexion();
    private boolean retomarDatos = false;
    private int stock, codigo;
    private final String UPDATE = "UPDATE productos SET STOCK = ? WHERE CODPROD = ?;";

    @FXML
    void registrar(MouseEvent event) {
        tomarDatos();
        if(retomarDatos){
            Alertas.error("Por favor llenar todos los campos con el formato adecuado");
        } else {
            conexion.establecerConexion();
            PreparedStatement preparedStatement = null;
            try{
                preparedStatement = conexion.getConnection().prepareStatement(UPDATE);
                preparedStatement.setInt(1, stock);
                preparedStatement.setInt(2, codigo);
                if(preparedStatement.executeUpdate() == 0){
                    System.out.println("NO SE HA MODIFICADO CORRECTAMENTE");
                    Alertas.error("Producto no existe");
                } else {
                    System.out.println("Stock modificado correctamente");
                    Alertas.info("Stock modificado correctamente.");
                }
            } catch (SQLException e) {
                System.out.println("Error con el SQL");
                e.printStackTrace();
            } finally {
                PreparedStateCerrar.cerrarStatement(preparedStatement);
            }
        }
    }

    public void tomarDatos(){
        try{
            retomarDatos = false;
            stock = Integer.parseInt(txtStock.getText());
            codigo = Integer.parseInt(txtCodigo.getText());
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

