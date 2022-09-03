package com.example.proyectogestion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PreparedStateCerrar {

    public static void cerrarStatement (PreparedStatement preparedStatement){
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                //System.out.println("PreparedStatement cerrado correctamente");
            } catch (SQLException e) {
                System.out.println("Ocurrió un error al cerrar el PreparedStatement");
                e.printStackTrace();
            }
        }
    }

    public static void cerrarStatement (Statement statement){
        if (statement != null) {
            try {
                statement.close();
                //System.out.println("Statement cerrado correctamente");
            } catch (SQLException e) {
                System.out.println("Ocurrió un error al cerrar el PreparedStatement");
                e.printStackTrace();
            }
        }
    }

    public static void cerrarResult (ResultSet resultSet){
        if (resultSet != null) {
            try {
                resultSet.close();
                //System.out.println("ResultSet cerrado correctamente");
            } catch (Exception e) {
                System.out.println("Ocurrió un error al cerrar el ResultSet");
                e.printStackTrace();
            }
        }
    }
}
