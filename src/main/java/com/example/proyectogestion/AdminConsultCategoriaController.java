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

public class AdminConsultCategoriaController implements Initializable {

    @FXML
    private Button btnAtras;

    @FXML
    private TableColumn clmCategoria;

    @FXML
    private TableView<CategoriaModel> tblCategoria;

    @FXML
    private TextField txtNombre;

    private Stage stage;
    private Scene scene;
    private Parent root;

    Conexion conexion = new Conexion();
    ObservableList<CategoriaModel> listaCategorias;
    final private String SELECT_ALL = "SELECT NOMCAT FROM categorias;";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listaCategorias = FXCollections.observableArrayList();
        this.clmCategoria.setCellValueFactory(new PropertyValueFactory("nombre"));

        llenarTabla();

        FilteredList<CategoriaModel> filteredData = new FilteredList<>(listaCategorias, b -> true);
        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(productoModel -> {

                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(productoModel.getNombre().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<CategoriaModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblCategoria.comparatorProperty());
        tblCategoria.setItems(sortedData);
    }

    public void llenarTabla(){
        conexion.establecerConexion();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conexion.getConnection().createStatement();
            resultSet = statement.executeQuery(SELECT_ALL);

            while (resultSet.next()){
                listaCategorias.add(convertir(resultSet));
            }
            tblCategoria.setItems(listaCategorias);
        } catch (Exception e) {
            System.out.println("Ocurrio un error en el SQL");
            e.printStackTrace();
        } finally {
            PreparedStateCerrar.cerrarStatement(statement);
            PreparedStateCerrar.cerrarResult(resultSet);
        }
        conexion.cerrarConexion();
    }

    public CategoriaModel convertir(ResultSet resultSet) throws SQLException {
        String nombre = resultSet.getString("NOMCAT");
        CategoriaModel CategoriaAux = new CategoriaModel(nombre);
        return CategoriaAux;
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
