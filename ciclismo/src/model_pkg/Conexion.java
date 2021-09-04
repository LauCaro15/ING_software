/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author lccan
 */
public class Conexion {
    Connection connection;
    String sql;
    PreparedStatement ps;
    ResultSet rs;

    public Conexion() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ruta_ciclistas","root","");
            if(connection != null){
                System.out.println("Conexion exitosa");
            }
        }catch(ClassNotFoundException | SQLException e){
            System.err.println(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
    
    public boolean agregarUsuario(String nombre, String usuario, String contra) {
        try{
            sql = "INSERT INTO Usuario(nombre,usuario,contraseña) VALUES(?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, usuario);
            ps.setString(3, contra);
            ps.executeUpdate();
            return true;
        }catch(SQLException ex){
            System.err.println("Error de conexión:" + ex.getMessage());
            return false;
        }
    }
    
    public int ingresarID(String usuario, String pass) {
        sql = "SELECT codigo_id FROM tb_ciclista WHERE ciclista_id=? AND ciclista_password=?";
        try{
            ps = connection.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            while(rs.next()) {
                return rs.getInt("codigo_id");
            }
        }catch(SQLException ex){
            System.err.println("Error de conexión:" + ex.getMessage());
            return 0;
        }
        return 0;
    }
}
