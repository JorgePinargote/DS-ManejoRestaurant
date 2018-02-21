/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

import Main.Conexion;
import Main.Login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

/**
 *
 * @author Israel
 */
public class Pedido {
    
    private IntegerProperty idpedido;
    private BooleanProperty atendiendo;
    private Date horaIngreso;
    private DoubleProperty tiempolisto;
    private BooleanProperty preferencial;
    private BooleanProperty listo;
    private DoubleProperty precio;
    
    private Mesero mesero;
    private ObservableList<detallePedido> itemsPedidos;

    public Pedido() {
        this.atendiendo = new SimpleBooleanProperty(false);
        this.tiempolisto = new SimpleDoubleProperty(0);
        this.horaIngreso = new Date(System.currentTimeMillis()) ;
        this.preferencial =new SimpleBooleanProperty(false);
        this.listo = new SimpleBooleanProperty(false);
        itemsPedidos = FXCollections.observableArrayList(); 
        this.precio = new SimpleDoubleProperty(0);
        this.idpedido = new SimpleIntegerProperty(0);
        
        Login login = Login.getInstance();
        if(login.getTrabajador() instanceof Mesero) mesero = (Mesero)login.getTrabajador();
        
        listo.addListener((v,oldValue,newValue)-> mesero.SendMessage(this));
    }
    
    public Pedido(int idpedido,boolean preferencial,boolean atendido ,boolean listo,Date horaingreso, Double precio,Double tiempolisto){
        this.atendiendo = new SimpleBooleanProperty(atendido);
        this.tiempolisto = new SimpleDoubleProperty(tiempolisto);
        this.horaIngreso = horaingreso;
        this.preferencial =new SimpleBooleanProperty(preferencial);
        this.listo = new SimpleBooleanProperty(listo);
        itemsPedidos = FXCollections.observableArrayList(); 
        this.precio = new SimpleDoubleProperty(precio);
        this.idpedido = new SimpleIntegerProperty(idpedido);
        
        
        Login login = Login.getInstance();
        if(login.getTrabajador() instanceof Mesero) mesero = (Mesero)login.getTrabajador();
        
        this.listo.addListener((v,oldValue,newValue)-> mesero.SendMessage(this));
    
    }
    

    public boolean isAtendiendo() {
        return atendiendo.get();
    }

    public void setAtendiendo(boolean atendiendo) {
        this.atendiendo.set(atendiendo);
    }
    
    public BooleanProperty getAtendiendoProperty(){
        return this.atendiendo;
    }
    
    public Date getHoraIngreso(){
        return horaIngreso;
    }

    public void setHoraIngreso(Date horaIngreso){
        this.horaIngreso = horaIngreso;
    }

    public boolean isPreferencial() {
        return preferencial.get();
    }

    public void setPreferencial(boolean preferencial) {
        this.preferencial.set(preferencial);
    }
    
    public BooleanProperty getPreferencialProperty(){
        return this.atendiendo;
    }

    public boolean isListo() {
        return listo.get();
    }

    public void setListo(boolean listo) {
        this.listo.set(listo);
    }
    
    public BooleanProperty getlistoProperty(){
        return this.listo;
    }

    public double getTiempolisto() {
        return tiempolisto.get();
    }

    public void setIdpedido(int idpedido) {
        this.idpedido.set(idpedido);
    }

    public int getIdpedido() {
        return idpedido.get();
    }
    
    
    
    public void addDetalle(detallePedido detalle){
        this.itemsPedidos.add(detalle);
    }
    
    
    public void CalcularTiempoListo(){
        double maxtime = Double.MIN_VALUE;
        for (detallePedido detalle : itemsPedidos) {
            double tiempo = detalle.item.getTiempoDePreparacion();
            if(tiempo>maxtime){
                maxtime = tiempo;
            }
        }
        double totaltime = maxtime + (this.itemsPedidos.size() - 1)*3;
        this.tiempolisto.set(totaltime);
    }
    
    public void CalcularPrecio(){
        double precio = 0;
        for (detallePedido detalle : itemsPedidos) {
            precio = precio + detalle.getPrecio();
        }
        this.precio.set(precio);
    }

    public double getPrecio() {
        return precio.get();
    }
    
    
    public void sendDetalles(){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                for(detallePedido detalle : itemsPedidos){
                    PreparedStatement pst = 
                        conectar.prepareStatement("insert into detallepedido(id_pedido,id_menuItem,cantidad,Observaciones,preciototal) VALUES(?,?,?,?,?)");
                        pst.setInt(1,this.idpedido.get());
                        pst.setInt(2,detalle.getItem().getIdMenu());
                        pst.setInt(3,detalle.getCantidad());
                        pst.setString(4, detalle.getObservaciones());
                        pst.setDouble(5,detalle.getPrecio());
                        
                        int res = pst.executeUpdate();
                        
                        if(res>0){
                            System.out.println("detalle Enviado");
                        }else{
                            System.out.println("Error al Enviar detalle");
                        }
                }
                        
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    
    }
    
    public void sendDetalles(ObservableList<detallePedido> itemsPedidos){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                for(detallePedido detalle : itemsPedidos){
                    PreparedStatement pst = 
                        conectar.prepareStatement("insert into detallepedido(id_pedido,id_menuItem,cantidad,Observaciones,preciototal) VALUES(?,?,?,?,?)");
                        pst.setInt(1,this.idpedido.get());
                        pst.setInt(2,detalle.getItem().getIdMenu());
                        pst.setInt(3,detalle.getCantidad());
                        pst.setString(4, detalle.getObservaciones());
                        pst.setDouble(5,detalle.getPrecio());
                        
                        int res = pst.executeUpdate();
                        
                        if(res>0){
                            System.out.println("detalle Enviado");
                        }else{
                            System.out.println("Error al Enviar detalle");
                        }
                }
                        
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    
    }
    
    
    
    
    public void getMyDetalles(){
        this.itemsPedidos.clear();
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from detallepedido where id_pedido = " + this.idpedido.get());
                ResultSetMetaData rsMd = rst.getMetaData();
                
                while(rst.next()){
                    int iddetalle = (int)rst.getObject(1);
                    int iditem = (int)rst.getObject(3);
                    int cantidad =  (int)rst.getObject(4);
                    String observaciones =  (String)rst.getObject(5);
                    Double precio =  (double)rst.getObject(6);
                    
                    MenuElemento item = MenuElemento.getItemById(iditem);
                    this.itemsPedidos.add(new detallePedido(iddetalle,item,observaciones,cantidad)); 
                }
                
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    }
    
    public void deletePedido(){
        deleteDetalles();
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                for(detallePedido detalle : itemsPedidos){
                    PreparedStatement pst = 
                        conectar.prepareStatement("Delete from detallepedido Where id_detalle = ?");
                        pst.setInt(1,this.idpedido.get());
                        
                        int res = pst.executeUpdate();
                        
                        if(res>0){
                            System.out.println("detalle eliminado");
                        }else{
                            System.out.println("Error al eliminar detalle");
                        }
                }
                        
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    }
    
    public void deleteDetalles(){
        detallePedido.deleteDetalles(itemsPedidos);
    }
    
    public void deleteDetalles(ObservableList<detallePedido> itemsPedidos){
        detallePedido.deleteDetalles(itemsPedidos);
    }
    
    public ObservableList<detallePedido> getItemsPedidos() {
        return itemsPedidos;
    }
    
    
   public static void ChangeDetalles(Pedido pedido, ObservableList<detallePedido> itemsPedidos){
       if(pedido!=null){
       itemsPedidos.clear();
        pedido.getMyDetalles();
        itemsPedidos.addAll(pedido.getItemsPedidos());
       }
        
   }
   
   public void update(){
       CalcularPrecio();
       CalcularTiempoListo();
       
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                    PreparedStatement pst = 
                        conectar.prepareStatement("Update pedido set tiempo_estimado = ? , preciopedido = ? where id_pedido = ?");
                        pst.setDouble(1,this.getTiempolisto());
                        pst.setDouble(2,this.getPrecio());
                        pst.setInt(3,this.getIdpedido());
                        
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
   
   
   
   public static Pedido getPedidoByid(int id){
       Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from pedido where id_pedido = " + id);
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
                    return pedido;
                }
                
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }   
            
            return null;
   }
   
   public void ActualizarEstado(Pedido pedido){
       this.atendiendo.set(pedido.isAtendiendo());
       this.listo.set(pedido.isListo());
   }
   
   
    
   public int getMyCuenta(){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select id_cuenta from pedido where id_pedido = " + this.idpedido.get());
                ResultSetMetaData rsMd = rst.getMetaData();
                
                while(rst.next()){
                    int idcuenta= (int)rst.getObject(1);
                    return idcuenta;
                }
                
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }   
            
            return -1;
   }
    
   public IntegerProperty getIdProperty(){
       return this.idpedido;
   }
   
    public DoubleProperty getTiempoProperty(){
       return this.tiempolisto;
   }
    
   
   
}
