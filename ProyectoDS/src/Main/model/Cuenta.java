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
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Israel
 */
public class Cuenta {
    
    private int idcuenta;
    private double saldo;
    private double valortotal;
    Date fecha;
    private ObservableList<Pedido> Pedidos;
    
    //carga las cuentas existentes 
    public Cuenta(int idcuenta, double saldo, double valortotal, Date fecha) {
        this.idcuenta = idcuenta;
        this.saldo = saldo;
        this.valortotal = valortotal;
        this.fecha = fecha;
        this.Pedidos = FXCollections.observableArrayList();
    }
    
    //crear nueva cuenta
    public Cuenta(){
        this.idcuenta = 0;
        this.Pedidos = FXCollections.observableArrayList();
    }
    
     public int sendPedido(Pedido pedido){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
        pedido.CalcularTiempoListo();
        pedido.CalcularPrecio();
        int clave = 0;
            try{
                PreparedStatement pst = 
                        conectar.prepareStatement("insert into pedido(preferencial,atendido,listo,tiempo_estimado,id_trabajador,id_cuenta,preciopedido) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS );
                        pst.setBoolean(1, pedido.isPreferencial());
                        pst.setBoolean(2, pedido.isAtendiendo());
                        pst.setBoolean(3, pedido.isListo());
                        
                        /*Timestamp timestamp = new Timestamp(pedido.getHoraIngreso().getTime());
                        pst.setTimestamp(4, timestamp);*/
                        
                        pst.setDouble(4,pedido.getTiempolisto());
                        
                        Login login = Login.getInstance();
                        pst.setString(5,login.getTrabajador().getUserName());
                        System.out.println(login.getTrabajador().getUserName());
                        
                        pst.setInt(6,this.idcuenta);
                        
                        pst.setDouble(7,pedido.getPrecio());
                        
                        int res = pst.executeUpdate();
                        
                        if(res>0){
                            System.out.println("Pedido Enviado");
                        }else{
                            System.out.println("Error al Enviar pedido");
                        }
                        
                        ResultSet rs = pst.getGeneratedKeys(); 
                        
                        if(rs.next()){ 
                            clave = rs.getInt(1);
                        }else{
                            System.out.println("error obteniendo id");
                            return -1;
                        }
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
            
            return clave;
    }
     
     
    public void getMyPedidos(){
        this.Pedidos.clear();
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from pedido where id_cuenta = " + this.idcuenta);
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
                    this.Pedidos.add(pedido); 
                }
                
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    
    
    }
     
    public Cuenta getCuenta(){
        return this;
    }
    
    public void addPedido(Pedido pedido){
        this.Pedidos.add(pedido);
    }

    public int getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(int idcuenta) {
        this.idcuenta = idcuenta;
    }

    public ObservableList<Pedido> getPedidos() {
        return Pedidos;
    }
    
    public static void changePedidos(Cuenta cuenta,ObservableList<Pedido> pedidos){
        if(cuenta!=null){
            pedidos.clear();
            cuenta.getMyPedidos();
            pedidos.addAll(cuenta.getPedidos());
        }
        
    }
    
    public static int getMymesa(int idcuenta){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select id_mesa from cuenta where id_cuenta = " + idcuenta);
                ResultSetMetaData rsMd = rst.getMetaData();
                
                while(rst.next()){
                    int idmesa= (int)rst.getObject(1);
                    return idmesa;
                }
                
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }   
            
            return -1;
    }
    
    
}
