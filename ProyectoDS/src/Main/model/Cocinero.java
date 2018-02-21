/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

import Main.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Queue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Israel
 */
public class Cocinero extends Trabajador {
    private ObservableList<Pedido> pedidosNormal;
    private ObservableList<Pedido> pedidosPrioridad;
    private Pedido atendiendo;
    
    public Cocinero(String rol, boolean eliminado, String nombre, String apellido, String direccion, String cedula, String telefono, String username) {
        super(rol, eliminado, nombre, apellido, direccion, cedula, telefono, username);
        pedidosNormal= FXCollections.observableArrayList(); 
        pedidosPrioridad = FXCollections.observableArrayList(); 
    }
    
    public void cargarPedidos(int tipo){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from pedido where atendido = 0 and preferencial = " + tipo);
                ResultSetMetaData rsMd = rst.getMetaData();
                
                while(rst.next()){
                    int idpedido = (int)rst.getObject(1);
                    boolean preferencial = (boolean)rst.getObject(2);
                    boolean atendido = (boolean)rst.getObject(3);
                    boolean listo = (boolean)rst.getObject(4);
                    Timestamp ingreso = (Timestamp)rst.getObject(5);
                    Date horaingreso = new Date(ingreso.getTime());
                    Double tiempoestimado = (Double)rst.getObject(6);
                    Double precio = (Double)rst.getObject(9);
                    
                    Pedido pedido = new Pedido(idpedido,preferencial,atendido,listo,horaingreso,precio,tiempoestimado);
                    
                    
                    if(tipo==0){
                        pedidosNormal.add(pedido);
                    }else{
                        pedidosPrioridad.add(pedido);
                    }
  
                }
                
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    }
    
    public Pedido getSiguiente(){
        if(pedidosPrioridad.size()> 0){
            return pedidosPrioridad.remove(0);
        }
        if(pedidosNormal.size()>0) return pedidosNormal.remove(0);
        return null;
    }
    
    public void atenger(){
        atendiendo = getSiguiente();
        if(atendiendo!=null){
            Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                    PreparedStatement pst = 
                        conectar.prepareStatement("Update pedido set atendido = ? where id_pedido = ?");
                        pst.setBoolean(1,true);
                        pst.setInt(2,atendiendo.getIdpedido());
                    
                        int res = pst.executeUpdate();
                        
                        if(res>0){
                            System.out.println("pedido actualizado");
                        }else{
                            System.out.println("Error al actualizar");
                        }
                        
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
        
        }
    
    }
    
    public void setListo(){
        if(atendiendo!=null){
            Conexion conexion = Conexion.getInstance();
            Connection conectar = conexion.getConexion();
            try{
                    PreparedStatement pst = 
                        conectar.prepareStatement("Update pedido set listo = ? where id_pedido = ?");
                        pst.setBoolean(1,true);
                        pst.setInt(2,atendiendo.getIdpedido());
                    
                        int res = pst.executeUpdate();
                        
                        if(res>0){
                            System.out.println("pedido actualizado");
                        }else{
                            System.out.println("Error al actualizar");
                        }
                        
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
            
            if(atendiendo.isPreferencial()){
                pedidosPrioridad.remove(atendiendo);
            }else{
                pedidosNormal.remove(atendiendo);
            }
        
        }
    
    }

    public ObservableList<Pedido> getPedidosNormal() {
        return pedidosNormal;
    }

    public ObservableList<Pedido> getPedidosPrioridad() {
        return pedidosPrioridad;
    }

    public Pedido getAtendiendo() {
        return atendiendo;
    }
    
    
    
    
}
