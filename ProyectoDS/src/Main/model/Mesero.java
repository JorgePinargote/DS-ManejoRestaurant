/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

import Main.Help.CheckPedidos;
import Main.Help.infoEntrega;
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
        CheckPedidos check = new CheckPedidos(this.allPedidos);
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
    
    
    
    
    
    
}
