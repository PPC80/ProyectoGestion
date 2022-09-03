package com.example.proyectogestion;

import java.sql.*;

public class Conexion {
    private Connection connection;
    private String jdbc = "jdbc:mysql://localhost:3306/proyectogestion";
    private String usuario="root";
    private String contrasenia = "";

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public void establecerConexion() {
        try {
            connection = DriverManager.getConnection(jdbc, usuario ,contrasenia);
            //System.out.println("> +Conexion con la BDD establecida correctamente");
        } catch (SQLException sql) {
            System.out.println("No se logro la conexion...");
            sql.printStackTrace();
        }
    }

    public void cerrarConexion (){
        if (connection != null){
            try {
                connection.close();
                //System.out.println("> -Conexion con la BDD se ha cerrado correctamente");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
