package com.example.proyectogestion;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.net.URL;

public class FxmlLoader extends FXMLLoader{

    private Pane view;

    public Pane getPage(String archivo){
        try{
            URL archivoURL = HelloApplication.class.getResource("/com/example/programanotas/" + archivo + ".fxml");
            if(archivoURL == null){
                throw new FileNotFoundException("No se pudo encontrar el archivo FXML...");
            }

            view = new FxmlLoader().load(archivoURL);

        } catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }
}
