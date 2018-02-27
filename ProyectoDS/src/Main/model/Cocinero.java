/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

import Main.Conexion;
import Main.Help.CheckPedidos;
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
    
    public void cargarPedidos( ){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from pedido where atendido = 0 ");
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
                    addPedido(pedido);
  
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
    
    public void atengerSiguiente(){
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

    public void setAtendiendo(Pedido atendiendo) {
        this.atendiendo = atendiendo;
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
    
    public boolean isNormalEmpty(){
        return this.pedidosNormal.isEmpty();
    }
    
    public boolean isPrioridadEmpty(){
        return this.pedidosPrioridad.isEmpty();
    }
    
    public void addPedido(Pedido pedido){
        if(pedido.isPreferencial()){
            pedidosPrioridad.add(pedido);
        }else{
            pedidosNormal.add(pedido);
        }
  
    }
    
    public void DeletePedido(int idPedido){
        DeletePedido(idPedido, this.pedidosNormal);
        DeletePedido(idPedido, this.pedidosPrioridad);
    }
    
    
    public void DeletePedido(int idPedido, ObservableList<Pedido> pedidos){
        Pedido eliminar = null;
        for(Pedido pedido : pedidos){
            if(pedido.getIdpedido() == idPedido)eliminar=pedido;
        }
        if(eliminar!=null)pedidos.remove(eliminar);
    }
    
    
    public void UpdatePedido(Pedido Actualizado){
        Actualizado.getMyDetalles();
        UpdatePedido(Actualizado, this.pedidosNormal);
        UpdatePedido(Actualizado, this.pedidosPrioridad);
    }
    
    public void UpdatePedido(Pedido Actualizado, ObservableList<Pedido> pedidos){
        Pedido Actualizar = null;
        for(Pedido pedido : pedidos){
            if(pedido.getIdpedido() == Actualizado.getIdpedido())Actualizar = pedido;
        }
        if(Actualizar!=null){
            pedidos.remove(Actualizar);
            pedidos.add(Actualizado);
        }
    }
    
    public void StarCheck(){
        CheckPedidos check = new CheckPedidos(this);
        Thread thread = new Thread(check);
        thread.start();
    }
    
    
   
    
    
}
