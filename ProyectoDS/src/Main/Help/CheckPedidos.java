/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Help;

import Main.Conexion;
import Main.model.MenuElemento;
import Main.model.Pedido;
import Main.model.detallePedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import javafx.application.Platform;
import javafx.collections.ObservableList;

/**
 *
 * @author Jorge Pinargote
 */
public class CheckPedidos implements Runnable {
    
    private ObservableList<Pedido> allPedidos;
    Date lastCheck;

    public CheckPedidos(ObservableList<Pedido> allPedidos) {
        this.allPedidos = allPedidos;
        lastCheck = new Date(System.currentTimeMillis());
    }
    
    @Override
    public void run() {
        while(true){
          
          Platform.runLater(new Runnable(){
                  @Override
                  public void run(){
                      if(allPedidos.size()> 0){
                            Conexion conexion = Conexion.getInstance();
                            Connection conectar = conexion.getConexion();
                            Timestamp time = new Timestamp(lastCheck.getTime());

                            try{
                                  String sql = "Select * from log_pedido where tipo = ? and fecha > ?"; 
                                  PreparedStatement ps = conectar.prepareStatement(sql);
                                  ps.setString(1,"update");
                                  ps.setTimestamp(2,time);

                                  ResultSet rst = ps.executeQuery();
                                  ResultSetMetaData rsMd = rst.getMetaData();

                                  while(rst.next()){
                                      Pedido pedido = Pedido.getPedidoByid((int)rst.getObject(2));
                                      actualizar(pedido);
                                  }

                            }catch(Exception ex){
                                  System.out.println("consulta no se realizo");
                                  ex.printStackTrace();
                            }


                        }
                      
                      lastCheck = new Date(System.currentTimeMillis());
                   }
              });
                
          try{
               Thread.sleep(10000);
          }
          catch(InterruptedException ex){

          }
        
        }
        
    }
    
    
    public void actualizar(Pedido pedido){
        for(int i=0;i<allPedidos.size();i++){
            if(allPedidos.get(i).getIdpedido()== pedido.getIdpedido()){
                allPedidos.get(i).ActualizarEstado(pedido);
            }
        }
    
    }
    
}
