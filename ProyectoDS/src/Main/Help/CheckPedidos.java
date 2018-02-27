/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Help;

import Main.Conexion;
import Main.model.Cocinero;
import Main.model.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.Date;
import javafx.application.Platform;
import javafx.collections.ObservableList;

/**
 *
 * @author Jorge Pinargote
 */
public class CheckPedidos  implements Runnable {
    Cocinero cocinero;
    Date lastAddCheck;
    Date lastUpdCheck;
    Date lastDelCheck;
    
    public CheckPedidos(Cocinero cocinero){
      this.cocinero = cocinero;    
      lastAddCheck = new Date(System.currentTimeMillis());
      lastDelCheck = new Date(System.currentTimeMillis());
      lastUpdCheck = new Date(System.currentTimeMillis());
    }
    
    @Override
    public void run() {
        while(true){
          
          Platform.runLater(new Runnable(){
                  @Override
                  public void run(){
                      CheckUpdate();
                      CheckAdd();
                      CheckDelete();

                   }
           });
          
                try{
                    Thread.sleep(10000);
                }
                    catch(InterruptedException ex){
                }
          
        }
        
    }
    
    
    public void CheckAdd(){
         Conexion conexion = Conexion.getInstance();
         Connection conectar = conexion.getConexion();
         Timestamp time = new Timestamp(lastAddCheck.getTime());

        try{
            String sql = "Select * from log_pedido where tipo = ? and fecha > ?"; 
            PreparedStatement ps = conectar.prepareStatement(sql);
            ps.setString(1,"add");
            ps.setTimestamp(2,time);

            ResultSet rst = ps.executeQuery();
            ResultSetMetaData rsMd = rst.getMetaData();

            while(rst.next()){
                Pedido pedido = Pedido.getPedidoByid((int)rst.getObject(2));
                cocinero.addPedido(pedido);
            }

        }catch(Exception ex){
            System.out.println("consulta no se realizo");
            ex.printStackTrace();
        }
        
        lastAddCheck = new Date(System.currentTimeMillis());
        
    }
    
    public void CheckDelete(){
         Conexion conexion = Conexion.getInstance();
         Connection conectar = conexion.getConexion();
         Timestamp time = new Timestamp(lastDelCheck.getTime());

        try{
            String sql = "Select * from log_pedido where tipo = ? and fecha > ?"; 
            PreparedStatement ps = conectar.prepareStatement(sql);
            ps.setString(1,"delete");
            ps.setTimestamp(2,time);

            ResultSet rst = ps.executeQuery();
            ResultSetMetaData rsMd = rst.getMetaData();

            while(rst.next()){
                
                cocinero.DeletePedido((int)rst.getObject(2));
            }

        }catch(Exception ex){
            System.out.println("consulta no se realizo");
            ex.printStackTrace();
        }
        
        lastDelCheck = new Date(System.currentTimeMillis());
    }
    
    public void CheckUpdate(){
         Conexion conexion = Conexion.getInstance();
         Connection conectar = conexion.getConexion();
         Timestamp time = new Timestamp(lastUpdCheck.getTime());

        try{
            String sql = "Select * from log_pedido where tipo = ? and fecha > ?"; 
            PreparedStatement ps = conectar.prepareStatement(sql);
            ps.setString(1,"update");
            ps.setTimestamp(2,time);

            ResultSet rst = ps.executeQuery();
            ResultSetMetaData rsMd = rst.getMetaData();

            while(rst.next()){
                Pedido pedido = Pedido.getPedidoByid((int)rst.getObject(2));
                cocinero.UpdatePedido(pedido);
            }

        }catch(Exception ex){
            System.out.println("consulta no se realizo");
            ex.printStackTrace();
        }
        
        lastUpdCheck = new Date(System.currentTimeMillis());
    }
    
    
    
    

}
