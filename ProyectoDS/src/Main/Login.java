/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Main.model.Bebida;
import Main.model.Categoria;
import Main.model.Combo;
import Main.model.ConcretTrabajador;
import Main.model.FactoryTrabajador;
import Main.model.Plato;
import Main.model.Trabajador;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 *
 * @author Jorge Pinargote
 */
public class Login {
    private static Login instancia;
    private Trabajador trabajador;
    boolean correcto;
    
    
    private Login(String username, String pass){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
        correcto = false;
        
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from trabajador where username=" + "'" + username + "'");
                ResultSetMetaData rsMd = rst.getMetaData();
                rst.next();
                
                String contraseña = (String)rst.getObject(3); 
                String rol =(String)rst.getObject(2);
                String nombre = (String)rst.getObject(4);
                String apellido = (String)rst.getObject(5);
                
                
                if(pass.equals(contraseña)){
                    Categoria.cargarCategorias();
                    Bebida.cargarBebidas();
                    Plato.cargarPlatos();
                    Combo.cargarCombos();
                    FactoryTrabajador facTrabajador = new ConcretTrabajador();
                    trabajador = facTrabajador.CreateTrabajador(rol, correcto, nombre, apellido, apellido, rol, apellido, username);
                    correcto = true; 
                }

            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                
            }
    }
    
    public static Login getInstance(String username, String pass){
        if(instancia == null){
            instancia = new Login(username,pass);
            if(instancia.trabajador==null)instancia=null;
            return instancia;
        }else{
            return instancia;
        }
    }
    
    public static Login getInstance(){
        return instancia;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    
    public void logOut(){
        instancia = null;
        trabajador = null;
    }
    
    
    
    
}
