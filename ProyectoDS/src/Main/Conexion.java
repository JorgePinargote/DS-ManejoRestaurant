/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Jorge Pinargote
 */
public class Conexion {
    private Connection conexion;
    private static Conexion instancia;
    
    
    private Conexion(){
        String host = "localhost";
        String user = "root";
        String namedb = "sares";
        String pass ="";
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/"+ namedb, user,pass );
            System.out.println("Conexion exitosa");
            
        }catch(Exception e){
            System.out.println("Conexion no se realiza");
            e.printStackTrace();
        }
        
        
    }
    
    public static Conexion getInstance(){
        if(instancia == null){
            instancia = new Conexion();
            return instancia;
        }else{
            return instancia;
        }
    }
    
    public Connection getConexion(){
        return conexion;
    }
    
    
    
    
    
    
    
    
}
