/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

import Main.Conexion;
import Main.Help.CheckEstadoPedidos;
import Main.Help.infoEntrega;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Jorge Pinargote
 */
public class Mesero extends Trabajador{
    private ObservableList<Pedido> allPedidos;
    private ObservableList<infoEntrega> pedidosListos;
    
        
    public Mesero(String rol, boolean eliminado, String nombre, String apellido, String direccion, String cedula, String telefono, String username) {
        super(rol, eliminado, nombre, apellido, direccion, cedula, telefono, username);
        this.allPedidos = FXCollections.observableArrayList();
        this.pedidosListos = FXCollections.observableArrayList();
        
        CheckEstadoPedidos check = new CheckEstadoPedidos(this.allPedidos);
        Thread thread = new Thread(check);
        thread.start();
    }

    
    public void addPedido(Pedido pedido){
        this.allPedidos.add(pedido);
    }
    
    public void SendMessage(Pedido pedido){
        if(pedido!=null){
            if(pedido.isListo()){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Informacion");
                alert.setHeaderText("Pedidos");
                alert.setContentText("Pedido Listo, id: " + pedido.getIdpedido());

                alert.showAndWait();

                allPedidos.remove(pedido);
                pedidosListos.add(new infoEntrega(pedido));
            }
        }
        
        
    }

    public ObservableList<infoEntrega> getPedidosListos() {
        return pedidosListos;
    }
    
    public void getMyPedidos(){
        this.allPedidos.clear();
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from pedido where id_trabajador = " + this.getUserName() + " and atendido = 0");
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
                    this.allPedidos.add(pedido); 
                }
                
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }

        
    
    }
    
    
    
    
    
    
    
}
